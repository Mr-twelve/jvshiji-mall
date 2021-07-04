layui.use(['element','form','layedit',"jquery","layer"], function () {
    var $ = layui.jquery, layer = layui.layer;
    var layedit = layui.layedit;
    layedit.set({
        uploadImage: {
            url: '/layeditupload' //接口url
            ,type: 'post' //默认post
        }
    });
    var index = layedit.build('goodsDetailContent'); //建立编辑器

    var element = layui.element;
    var form = layui.form;
    var  layer = layui.layer;

    /**
     * layui监听下拉框
     */
    form.on('select(category1)', function (data) {
        console.log(data.value)
        getcategorylist(2,data.value)
    });
    form.on('select(category2)', function (data) {
        console.log(data.value)
        getcategorylist(3,data.value)
    });

    /**
     * layui监听表单提交
     */
    form.on('submit(upgoods)', function(data){
        console.log("2323")
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        var goods=data.field

        goods.goodsDetailContent=layedit.getContent(index)  //获取富文本内容

        console.log(goods) //当前容器的全部表单字段，名值对形式：{name: value}
        // addgoods(goods)

    });
});


//传送数据到后端保存商品信息
function upgoods(goods) {
    var file = $('#checkImage')[0].files[0]
    console.log(file)
    $.ajax({
        type: "post",
        url: "/admin/goodsupimg",
        contentType: false,
        processData: false,
        data: $('#checkImage')[0].files[0],
        success: function (res) {
            console.log(res)
            layer.msg(res.code, function () {
            })
        }
    })
    // $.ajax({
    //     type:"POST",
    //     url:"/admin/goodsedit/addgoods",
    //     dataType: "json",
    //     data: goods,
    //     contentType: false,
    //     processData: false,
    //     success: function (data) {
    //         console.log(data)
    //         if (data.code == 0) {
    //             layer.msg('成功', function () {
    //             });
    //         } else {
    //             layer.msg('很抱歉，出问题啦，请刷新后重试', function () {
    //             });
    //         }
    //     }
    // })
}

//判断传入数据是否为空
// function isnull(data) {
//     if (data == "" || data == null || data == undefined){
//         return true
//     }else {
//         return false
//     }
// }

//页面加载完Dom调用查询三级分类
var flag = 1
$(function ($) {
    getcategorylist(1, 0)
});

/**
 * 动态获取三级联动分类的内容
 * @param categoryLevel
 * @param parentId
 */
function getcategorylist(categoryLevel, parentId) {
    $.ajax({
        type: "GET",
        url: '/admin/category/checkboxlist?categoryLevel=' + categoryLevel + '&parentId=' + parentId,
        contentType: "application/json",
    })
        .then(function (data) {
            console.log(data)
            categoryList1 = data.data.categoryList1
            categoryList2 = data.data.categoryList2
            categoryList3 = data.data.categoryList3

            if (categoryLevel <= 1){
                var length1 = categoryList1.length;
                var leveloneSelect = '';
                for (var i = 0; i < length1; i++) {
                    leveloneSelect += '<option value=\"' + categoryList1[i].categoryId + '\">' + categoryList1[i].categoryName + '</option>';
                }
                $("#category1").empty(); //清楚下拉框内容
                $("#category1").append(leveloneSelect);  //插入自定义内容
            }
            if (categoryLevel <= 2){
                var length2 = categoryList2.length;
                var leveltwoSelect = '';
                for (var i = 0; i < length2; i++) {
                    leveltwoSelect += '<option value=\"' + categoryList2[i].categoryId + '\">' + categoryList2[i].categoryName + '</option>';
                }
                $("#category2").empty(); //清楚下拉框内容
                $("#category2").append(leveltwoSelect);  //插入自定义内容
            }
            if (categoryLevel <= 3){
                var length3 = categoryList3.length;
                var levelthreeSelect = '';
                for (var i = 0; i < length3; i++) {
                    levelthreeSelect += '<option value=\"' + categoryList3[i].categoryId + '\">' + categoryList3[i].categoryName + '</option>';
                }
                $("#category3").empty(); //清楚下拉框内容
                $("#category3").append(levelthreeSelect);  //插入自定义内容
            }
            layui.form.render("select");    //重新渲染select!!!不渲染会出不来效果，渲染不能使用ID选择器！！！
        });
}






