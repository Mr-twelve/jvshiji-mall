layui.use('table', function(){
    var table = layui.table;
    var type = $("#indexConfigType")[0].value
    console.log(type)
    //第一个实例
    table.render({
        elem: '#indexconfig'
        , id: 'indexconfig'   //表格唯一标识
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , height: 'full-110'    //高度设定
        , loading: true  //切换分页时加载条开启
        , url: '/admin/indexConfig/list' //数据接口
        , page: true //开启分页
        , skin: 'line ' //行边框风格
        , where: {
            configType: type
        }
        , cols: [[ //表头
            {type: 'numbers'}    //开启标号
            , {type: 'checkbox'}  //开启复选按钮
            , {field: 'configId', title: 'id', hide: true, sort: true}
            , {field: 'configName',style:"height:50px",title: '配置项名称'}
            , {field: 'redirectUrl', title: '跳转链接', sort: true}
            , {field: 'configRank', title: '排序值', sort: true}
            , {field: 'goodsId', title: '商品编号', sort: true}
            , {field: 'createTime', title: '创建时间', sort: true}
        ]]
    });
    //监听表格复选框选择
    table.on('checkbox(indexconfig)', function (obj) {

    });

});


var isup = null //判断添加和修改
function addIndexConfig(msg) {
    if (msg == "新增配置") {
        isup = false
    }
    if (msg == "修改配置") {
        isup = true
    }

    layer.open({
        type: 1,
        title: msg,
        anim: 1,//动画从上方弹出
        skin: 'layui-layer-rim', //加上边框
        area: ['498px', '375px'], //宽高
        resize: false,//不许拉伸
        shade: 0.3,//遮层      下面是弹窗自定义的H5页面
        content: '<div id="window" style="background-color: white;text-align: center;">\n' +
            '            <div class="layui-form layui-form-pane" style="margin: 30px 30px 0 30px">\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">显示字符</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input type="text" id="configName" name="configName" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">跳转链接</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input type="text" id="redirectUrl" name="redirectUrl" value="##" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">商品编号</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input value="0" type="number" id="goodsId" name="goodsId" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">排序值</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input value="0" type="number" id="configRank" name="configRank" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item layui-inline">\n' +
            '                    <div class="layui-input-block" style="margin: 18px 0;">\n' +
            '                        <button onclick="submitIndexConfig()" class="layui-btn">立即提交</button>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n'
    });

}

//修改轮播图
function setIndexConfig() {
    var table = layui.table;
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length != 1) {
        layer.msg('听话~😘只能选一条哦~', function () {
        });
        return true;
    }
    console.log(data[0])
    addIndexConfig('修改配置')
    $("#configName").attr("value", data[0].configName);
    $("#redirectUrl").attr("value", data[0].redirectUrl);
    $("#configRank").attr("value", data[0].configRank);
    $("#goodsId").attr("value", data[0].goodsId);
}

//添加添加分类信息
function submitIndexConfig() {
    if ($('#configName').val() == "" || $('#configName').val() == null || $('#configName').val() == undefined) {
        layer.msg('👊打你奥！请输入显示字符', function(){});
        return false
    }
    if ($('#redirectUrl').val() == "" || $('#redirectUrl').val() == null || $('#redirectUrl').val() == undefined) {
        layer.msg('👊打你奥！请输入跳转链接', function(){});
        return false
    }
    if ($('#goodsId').val() == "" || $('#goodsId').val() == null || $('#goodsId').val() == undefined) {
        layer.msg('👊打你奥！请输入商品编号', function(){});
        return false
    }
    if ($('#configRank').val() == "" || $('#configRank').val() == null || $('#configRank').val() == undefined) {
        layer.msg('👊打你奥！请输入排序值', function(){});
        return false
    }
    var table = layui.table;
    var type = $("#indexConfigType")[0].value
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    if(isup){   //修改分类
        var url = "/admin/indexConfig/updata"
        var configId = data[0].configId
    }else { //添加分类
        var url = "/admin/indexConfig/add"
        var configId = null
    }
    param={
        'configId':configId,
        'configName' : $('#configName').val(),
        'redirectUrl' : $('#redirectUrl').val(),
        'goodsId' :$('#goodsId').val(),
        'configRank' :$('#configRank').val(),
        'configType':type,
        'isDeleted':0
    }
    console.log(param)

    $.ajax({
        type:'POST',
        url:url,
        contentType:'application/json',
        data:JSON.stringify(param),
        success:function (res) {
            console.log(res)
            if(res.code == 0){
                layer.closeAll();
                checkarrid = []
                table.reload('indexconfig',
                    {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    })
                layer.msg('✌成功', function(){});
            }else{
                layer.msg('很抱歉，出问题啦，请刷新后重试', function(){});
            }
        }
    })

}

/**
 * 批量删除轮播图
 */
function deleteIndexConfig() { //获取选中数据
    var table = layui.table;
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    var configId = [];
    data.forEach((value) => {
        configId.push(value.configId)
    })

    if (data.length > 0) {
        layer.confirm('是否确定删除这' + data.length + '条配置数据？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            console.log(configId)
            $.ajax({
                type: "POST",
                url: "/admin/indexConfig/delete",
                contentType: "application/json",
                data: JSON.stringify(configId),
                success: function (res) {
                    configId = []
                    console.log(res)
                    table.reload('indexconfig',
                        {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        })
                    layer.closeAll();
                }
            })
        }, function () {

        });
    }
}