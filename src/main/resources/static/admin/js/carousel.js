layui.use('table', function () {
    var table = layui.table;
    //第一个实例
    table.render({
        elem: '#carousel'
        , id: 'carousel'   //表格唯一标识
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , height: 'full-110'    //高度设定
        , loading: true  //切换分页时加载条开启
        , url: '/admin/carousel/list' //数据接口
        , page: true //开启分页
        , skin: 'line ' //行边框风格
        , cols: [[ //表头
            {type: 'numbers'}    //开启标号
            , {type: 'checkbox'}  //开启复选按钮
            , {field: 'carouselId', title: 'id', hide: true, sort: true}
            , {field: 'carouselUrl', title: '轮播图', width: 190.4, templet: '#imgtmp', style: "height:120px"}
            , {field: 'redirectUrl', title: '跳转链接',}
            , {field: 'carouselRank', title: '排序值', sort: true}
            // , {field: 'isDeleted', title: '签名', width: 177}
            , {field: 'createTime', title: '创建时间', sort: true}
            // , {field: 'createUser', title: '评分', width: 80, sort: true}
            // , {field: 'updateTime', title: '职业', width: 80}
            // , {field: 'updateUser', title: '财富', width: 135, sort: true}
        ]]
    });
    //监听表格复选框选择
    table.on('checkbox(carousel)', function (obj) {

    });
});

/**
 * 批量删除轮播图
 */
function deletearr() { //获取选中数据
    var table = layui.table;
    var checkStatus = table.checkStatus('carousel'),
        data = checkStatus.data; //获取选中行的数据
    var checkarrid = [];
    data.forEach((value) => {
        checkarrid.push(value.carouselId)
    })

    if (data.length > 0) {
        layer.confirm('是否确定删除这' + data.length + '条轮播图数据？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            console.log(checkarrid)
            $.ajax({
                type: "POST",
                url: "/admin/carousel/delete",
                contentType: "application/json",
                data: JSON.stringify(checkarrid),
                success: function (res) {
                    checkarrid = []
                    console.log(res)
                    table.reload('carousel',
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

var isup = null //判断添加和修改
function openPage(msg) {
    if (msg == "新增轮播图") {
        isup = false
    }
    if (msg == "修改轮播图") {
        isup = true
    }

    layer.open({
        type: 1,
        title: msg,
        anim: 1,//动画从上方弹出
        skin: 'layui-layer-rim', //加上边框
        area: ['498px', '500px'], //宽高
        resize: false,//不许拉伸
        shade: 0.3,//遮层      下面是弹窗自定义的H5页面
        content: '<div id="window" style="background-color: white;text-align: center;">\n' +
            '            <div class="layui-upload layui-inline">\n' +
            '                <div class="layui-upload-list">\n' +
            '                    <img onclick="checkImage()" src="/upload/uploadimg.png" class="layui-upload-img" id="upCarouselImg" style="width:240px;height:180px;margin: 20px;">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '            <div class="layui-form layui-form-pane" style="margin: 10px 30px">\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">跳转链接</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input type="text" id="redirectUrl" name="redirectUrl" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">排序值</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input value="0" type="number" id="carouselRank" name="title" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item layui-inline">\n' +
            '                    <div class="layui-input-block" style="margin: 18px 0;">\n' +
            '                        <button onclick="addCarousel()" class="layui-btn">立即提交</button>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            ' <input class="layui-hide" id="checkImage" type="file" name="file" onchange="viewImage()" />\n'


    });

}

/**
 * 新增轮播图信息提交
 */
function addCarousel() {
    var formData = new FormData();
    var table = layui.table;
    if (!isup && ($('#checkImage')[0].files[0] == "" || $('#checkImage')[0].files[0] == null || $('#checkImage')[0].files[0] == undefined)) {
        layer.msg('请上传图片', function () {
        });
        return false
    }
    if ($('#carouselRank').val() == "" || $('#carouselRank').val() == null || $('#carouselRank').val() == undefined) {
        layer.msg('请输入排序值', function () {
        });
        return false
    }
    if ($('#redirectUrl').val() == "" || $('#redirectUrl').val() == null || $('#redirectUrl').val() == undefined) {
        layer.msg('请输入跳转链接', function () {
        });
        return false
    }
    formData.append('file', $('#checkImage')[0].files[0]);  //添加图片信息的参数
    console.log($('#checkImage')[0].files[0])
    formData.append('carouselRank', $('#carouselRank').val());  //添加图片信息的参数
    formData.append('redirectUrl', $('#redirectUrl').val());  //添加图片信息的参数
    if (isup) {
        var checkStatus = table.checkStatus('carousel'),
            data = checkStatus.data[0]; //获取选中行的数据
        formData.append('carouselId', data.carouselId);
    } else {
        formData.append('carouselId', -1);
    }
    $.ajax({
        url: "/admin/carousel/addCarousel",
        contentType: false,
        processData: false,
        type: "POST",
        dataType: "json",
        data: formData,
        success: function (data) {
            console.log(data.code == 0)
            if (data.code == 0) {
                table.reload('carousel',
                    {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    })
                layer.closeAll();
                checkarrid = []

                layer.msg('成功', function () {
                });
            } else {
                layer.msg('很抱歉，出问题啦，请刷新后重试', function () {
                });
            }
        }
    })

}

//选择图片
function checkImage() {
    //调用隐藏input的上传图片按钮
    if (isup) {
        layer.msg('图片不能修改', function () {
        });
    } else {
        $('#checkImage').trigger('click')
    }
}


//图片预览
function viewImage() {
    //获取选择图片的信息
    var objUrl = $('#checkImage')[0].files[0];

    console.log(objUrl)
    // if (!(objUrl.name && /^(jpg|jpeg|png|gif)$/.test(objUrl.name))) {
    //     alert('只支持jpg、png、gif格式的文件！');
    //     return false;
    // }
    var imgpath = getObjectURL($('#checkImage')[0].files[0]);
    if (objUrl) {
        // 在这里修改图片的地址属性
        $("#upCarouselImg").attr("src", imgpath);
    }
}

//建立一個可存取到該file的url
function getObjectURL(file) {
    var url = null;
    // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}


//修改轮播图
function setCarousel() {
    var table = layui.table;
    var checkStatus = table.checkStatus('carousel'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length != 1) {
        layer.msg('请选择单条数据进行修改', function () {
        });
        return true;
    }
    console.log(data[0])
    openPage('修改轮播图')
    if (data[0].carouselUrl.indexOf('http') >= 0) {
        $("#upCarouselImg").attr("src", data[0].carouselUrl);
    } else {
        $("#upCarouselImg").attr("src", "" + data[0].carouselUrl);
    }
    $("#redirectUrl").attr("value", data[0].redirectUrl);
    $("#carouselRank").attr("value", data[0].carouselRank);
}






