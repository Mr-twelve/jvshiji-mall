layui.use('table', function () {
    var table = layui.table;
    //第一个实例
    table.render({
        elem: '#goods'
        , id: 'goods'   //表格唯一标识
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , height: 'full-110'    //高度设定
        , loading: true  //切换分页时加载条开启
        , url: '/admin/goods/list' //数据接口
        , page: true //开启分页
        , skin: 'line ' //行边框风格
        , limit: 20
        , cols: [[ //表头
            {type: 'numbers'}    //开启标号
            , {type: 'checkbox'}  //开启复选按钮
            , {field: 'goodsId', title: '商品编号', sort: true}
            , {field: 'goodsName', title: '商品名称', sort: true}
            , {field: 'goodsIntro', title: '商品简介', sort: true}
            , {
                field: 'goodsCoverImg', title: '商品图片', sort: true, templet: function (res) {
                    return "<div ><img style='height: 80px;aspect-ratio: auto 80 / 80;width: 80px;' src='" + res.goodsCoverImg + "'/></div>";
                }
            }
            , {field: 'stockNum', title: '商品库存', sort: true}
            , {field: 'sellingPrice', title: '商品售价', sort: true}
            , {field: 'goodsSellStatus', title: '上架状态', sort: true,templet:function (res) {
                    if (res.goodsSellStatus == '0') {
                        return "<button type='button' class='layui-btn layui-btn-fluid'>上架中</button>";
                    } else {
                        return "<button type='button' class='layui-btn layui-btn-fluid layui-btn-danger'>已下架</button>";
                    }
                }}
            , {field: 'createTime', title: '创建时间', sort: true}

        ]]
    });
    //监听表格复选框选择
    table.on('checkbox(goods)', function (obj) {

    });
});

function addgoods() {
    window.location.href = '/admin/goodsedit'
}

function putUpGoods() {
    var table = layui.table;
    var checkStatus = table.checkStatus('goods'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length < 1) {
        layer.msg('请选择数据进行修改', function () {});
        return true;
    }
    var ids = [];
    data.forEach((value) => {
        ids.push(value.goodsId)
    })
    console.log(ids)
    $.ajax({
        type: "PUT",
        url: "/admin/goods/status/0",
        contentType: "application/json",
        data: JSON.stringify(ids),
        success: function (r) {
            if (r.code == 0) {
                table.reload('goods',
                    {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    })
                layer.closeAll();
                layer.msg('成功', function () {
                });
            } else {
                layer.msg(res.msg, function () {})
            }
        }
    });
}
function putDownGoods() {
    var table = layui.table;
    var checkStatus = table.checkStatus('goods'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length < 1) {
        layer.msg('请选择数据进行修改', function () {});
        return true;
    }
    var ids = [];
    data.forEach((value) => {
        ids.push(value.goodsId)
    })
    console.log(ids)
    $.ajax({
        type: "PUT",
        url: "/admin/goods/status/1",
        contentType: "application/json",
        data: JSON.stringify(ids),
        success: function (r) {
            if (r.code == 0) {
                table.reload('goods',
                    {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    })
                layer.closeAll();
                layer.msg('成功', function () {
                });
            } else {
                layer.msg(res.msg, function () {})
            }
        }
    });
}








