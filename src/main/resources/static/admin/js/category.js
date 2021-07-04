var categoryLevel = $("#categoryLevel").val();
var parentId = parseInt($("#parentId").val());
var backparentId = 0;

var levelone = ''
var leveltwo = ''


layui.use('table', function () {
    console.log('table:level----' + categoryLevel)
    console.log('table:parentId----' + parentId)

    var table = layui.table;
    //第一个实例
    table.render({
        elem: '#category'
        , id: 'category'   //表格唯一标识
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , height: 'full-110'    //高度设定
        , loading: true  //切换分页时加载条开启
        , url: '/admin/category/list'
        , page: false //开启分页
        , skin: 'line ' //行边框风格
        , where: {
            categoryLevel: categoryLevel,
            parentId: parentId
        }
        , unresize: true
        , cols: [[ //表头
            , {type: 'checkbox', unresize: true,width: '10%'}  //开启复选按钮
            , {field: 'categoryId', title: 'id', hide: true, sort: true, unresize: true}
            , {field: 'categoryName', title: '分类名称', width: '54%', unresize: true}
            , {field: 'categoryRank', title: '排序值', sort: true, width: '20%', unresize: true}
            , {field: 'createTime', title: '创建时间', sort: true, width: '20%', unresize: true}
        ]],
        done: function(res, curr, count){
            console.log(res)
            if(parseInt(categoryLevel)== 1){
                $("#tiptext").text('当前分类路径:')
            }
            if(parseInt(categoryLevel)== 2){
                $("#tiptext").text('当前分类路径: ['+levelone+"]")
            }
            if(parseInt(categoryLevel)== 3){
                $("#tiptext").text('当前分类路径: ['+levelone +"]--->[" + leveltwo+"]");
            }
        }
    });
    //监听表格复选框选择
    table.on('checkbox(category)', function (obj) {

    });

});


//table重载数据
function tableread(categoryLevel,parentId) {
    var table = layui.table
    table.reload('category', {
        where: {
            categoryLevel: categoryLevel,
            parentId: parentId
        },

    })
}


//进入下级
function gonext() {
    if(parseInt(categoryLevel)+1 > 3){
        layer.msg('💔别为难我了~', function () {
        });
        return true;
    }
    var table = layui.table
    var data = table.checkStatus('category').data //获取选中行的数据
    console.log(data)
    if (data.length != 1) {
        layer.msg('听话~😘只能选一条哦~', function () {
        });
        return true;
    }
    //获取所属父ID
    categoryLevel = parseInt(categoryLevel)+1  //级数+1
    parentId = data[0].categoryId

    if(parseInt(categoryLevel)== 2){
        backparentId = data[0].categoryId;
        levelone =  data[0].categoryName;
    }
    if(parseInt(categoryLevel)== 3){
        leveltwo =  data[0].categoryName;
    }

    tableread(categoryLevel,parentId)
}

//返回上级
function goreturn() {

   if (parseInt(categoryLevel) == 3){
       categoryLevel = parseInt(categoryLevel)-1  //级数-1
       tableread(categoryLevel,backparentId)
       console.log("==============3")
       return false
   }else if (parseInt(categoryLevel) == 2){
        categoryLevel = 1  //级数-1
        parentId = 0
        console.log("==============2")
        tableread(categoryLevel,parentId)
        return false
    }else {
       layer.msg('😭😭😭臣妾做不到了~', function () {
       });
       return true;
   }

}

//新增、修改分类弹窗
var isup = null
function addCategory(msg) {
    if(msg=="新增分类"){
        isup = false
    }
    if(msg=="修改分类"){
        isup = true
    }

    layer.open({
        type: 1,
        title: msg,
        anim: 1,//动画从上方弹出
        skin: 'layui-layer-rim', //加上边框
        area: ['498px', '250px'], //宽高
        resize: false,//不许拉伸
        shade: 0.3,//遮层      下面是弹窗自定义的H5页面
        content: '<div id="window" style="background-color: white;text-align: center;">\n' +
            '            <div class="layui-form layui-form-pane" style="margin: 30px 30px 0 30px">\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">分类名称</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input type="text" id="categoryName" name="categoryName" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">排序值</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input value="0" type="number" id="categoryRank" name="categoryRank" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item layui-inline">\n' +
            '                    <div class="layui-input-block" style="margin: 10px 0 0 0;">\n' +
            '                        <button onclick="submitAddCategory()" class="layui-btn">立即提交</button>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            ' <input class="layui-hide" id="checkImage" type="file" name="file" onchange="viewImage()" />\n'


    });
}

//添加添加分类信息
function submitAddCategory(msg) {
    if ($('#categoryName').val() == "" || $('#categoryName').val() == null || $('#categoryName').val() == undefined) {
        layer.msg('👊打你奥！请输入分类名称', function(){});
        return false
    }
    if ($('#categoryRank').val() == "" || $('#categoryRank').val() == null || $('#categoryRank').val() == undefined) {
        layer.msg('👊打你奥！请输入排序值', function(){});
        return false
    }
    var table = layui.table;
    var checkStatus = table.checkStatus('category'),
        data = checkStatus.data; //获取选中行的数据
    if(isup){   //修改分类
        var url = "/admin/category/setcategory"
        var categoryId = data[0].categoryId
    }else { //添加分类
        var url = "/admin/category/addcategory"
        var categoryId = 0
    }
    param={
        'categoryId':categoryId,
        'categoryLevel' : categoryLevel,
        'parentId' : parentId,
        'categoryName' :$('#categoryName').val(),
        'categoryRank' :$('#categoryRank').val()
    }
    console.log(param)

    $.ajax({
        type:'POST',
        url:url,
        contentType:'application/json',
        data:JSON.stringify(param),
        success:function (res) {
            if(res.code == 0){
                layer.closeAll();
                checkarrid = []
                tableread(categoryLevel,parentId)
                layer.msg('✌成功', function(){});
            }else{
                layer.msg('很抱歉，出问题啦，请刷新后重试', function(){});
            }
        }
    })

}

//修改轮播图
function setCategory() {
    var table = layui.table;
    var checkStatus = table.checkStatus('category'),
        data = checkStatus.data; //获取选中行的数据
    if(data.length!=1){
        layer.msg('请选择单条数据进行修改', function(){});
        return true;
    }
    console.log(data[0])
    addCategory('修改分类')

    $("#categoryName").attr("value", data[0].categoryName);
    $("#categoryRank").attr("value", data[0].categoryRank);
}

//删除分类
function deleteCategory() {
    var table = layui.table;
    var checkStatus = table.checkStatus('category'),
        data = checkStatus.data; //获取选中行的数据
    var arr = []
    if(data.length<1){
        layer.msg('💔想删哪条？', function () {});
        return false;
    }
    data.forEach((item,index)=>{
        console.log(item.categoryId)
        arr.push(item.categoryId)
    })
    layer.confirm('分类的删除会牵涉到多级分类的修改和商品数据的修改，请谨慎删除分类数据！！！！是否确定删除这' + data.length + '条分类？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            type:"POST",
            url:"/admin/category/deletecategory",
            contentType: "application/json",
            data:JSON.stringify(arr),
            success:function (res){
                if(res.code == 0){
                    layer.closeAll();
                    checkarrid = []
                    tableread(categoryLevel,parentId)
                    layer.msg('✌成功', function(){});
                }else{
                    layer.msg('很抱歉，出问题啦，请刷新后重试', function(){});
                }
            }
        })
    }, function () {})

}