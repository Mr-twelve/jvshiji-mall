layui.use('table', function () {
    var table = layui.table;
    //第一个实例
    table.render({
        elem: '#indexconfig'
        , id: 'indexconfig'   //表格唯一标识
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , height: 'full-110'    //高度设定
        , loading: true  //切换分页时加载条开启
        , url: '/admin/orders/list' //数据接口
        , page: true //开启分页
        , limit: 20
        , skin: 'line ' //行边框风格
        , cols: [[ //表头
            {type: 'numbers'}    //开启标号
            , {type: 'checkbox'}  //开启复选按钮
            , {field: 'orderNo', title: '订单号'}
            , {field: 'totalPrice', title: '订单总价'}
            , {
                field: 'payType', title: '支付方式', sort: true, templet: function (res) {
                    if (res.payType == '0') {
                        return "无";
                    }
                    if (res.payType == '1') {
                        return "支付宝";
                    }
                    if (res.payType == '2') {
                        return "微信";
                    }
                }
            }
            /*0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭*/
            , {
                field: 'orderStatus', title: '订单状态', sort: true, templet: function (res) {
                    switch (res.orderStatus) {
                        case -3:
                            return "商家关闭"
                        case -2:
                            return "超时关闭"
                        case -1:
                            return "手动关闭"
                        case 0:
                            return "待支付";
                        case 1:
                            return "已支付"
                        case 2:
                            return "配货完成";
                        case 3:
                            return "出库成功"
                        case 4:
                            return "交易成功";
                        default:
                            return "出错了"
                    }
                }
            }
            , {field: 'createTime', title: '创建时间', sort: true}
            , {
                title: '操作', templet: function (res) {
                    return "<button type='button' id='" + res.orderId + "' onclick='orderinfo(id)' class='layui-btn layui-btn-fluid'>查看订单信息</button>"
                }
            }
            , {
                title: '操作', templet: function (res) {
                    return "<button type='button'  id='" + res.userAddress + "' onclick='orderinfoaddress(id)'  class='layui-btn layui-btn-fluid layui-btn-warm'>查看收货人信息</button>"
                }
            }
        ]],
        done: function (res, curr, count) {
            console.log(res)
        }
    });

    //监听表格复选框选择
    table.on('checkbox(indexconfig)', function (obj) {

    });

});

function lockUser(lockStatus) {
    var lockStatus = lockStatus;
    var table = layui.table;
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    var checkarrid = [];
    data.forEach((value) => {
        checkarrid.push(value.userId)
    })
    console.log(checkarrid)
    console.log(lockStatus)
    if (data.length < 1) {
        layer.msg('请选择数据进行修改', function () {
        });
        return true;
    }

    $.ajax({
        type: "POST",
        url: "/admin/users/lock/" + lockStatus,
        contentType: "application/json",
        data: JSON.stringify(checkarrid),
        success: function (r) {
            if (r.code == 0) {
                table.reload('indexconfig',
                    {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    })
                layer.closeAll();
            } else {
                layer.msg('失败', function () {
                });
            }
        }
    });


}

function orderinfo(id) {
    $.ajax({
        type: 'GET',//方法类型
        url: '/admin/order-items/' + id,
        contentType: 'application/json',
        success: function (result) {
            console.log(result)
            if (result.code == 0) {
                var itemString = '';
                for (i = 0; i < result.data.length; i++) {
                    itemString += result.data[i].goodsName + ' x ' + result.data[i].goodsCount + ' 商品编号 ' + result.data[i].goodsId + ";<br>";
                }
                layer.alert(itemString, {
                    title: false,
                    shadeClose: true,
                    closeBtn: 0, //不显示关闭按钮
                    closeBtn: 0, //不显示关闭按钮
                    skin: 'layui-layer-molv' //样式类名
                });
            } else {
                layer.msg('失败', function () {
                });
            }
            ;
        },
        error: function () {
            layer.msg('出错了', function () {
            });
        }
    });

}

function orderinfoaddress(id) {
    layer.alert(id, {
        title: false,
        shadeClose: true,
        closeBtn: 0, //不显示关闭按钮
        closeBtn: 0, //不显示关闭按钮
        skin: 'layui-layer-molv' //样式类名
    });
}

function setOrder() {
    var table = layui.table;
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length != 1) {
        layer.msg('听话~😘只能选一条哦~', function () {
        });
        return true;
    }
    if (data[0].orderStatus < 0 || data[0].orderStatus > 2) {
        layer.msg('该订单不可以修改', function () {
        });
        return true;
    }
    console.log(data[0])

    layer.open({
        type: 1,
        title: "订单修改",
        anim: 1,//动画从上方弹出
        skin: 'layui-layer-rim', //加上边框
        area: ['498px', '260px'], //宽高
        resize: false,//不许拉伸
        shade: 0.3,//遮层      下面是弹窗自定义的H5页面
        content: '<div id="window" style="background-color: white;text-align: center;">\n' +
            '            <div class="layui-form layui-form-pane" style="margin: 10px 30px">\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">订单价格</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input value="' + data[0].totalPrice + '" type="number" id="totalPrice" name="redirectUrl" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item">\n' +
            '                    <label class="layui-form-label">收件人信息</label>\n' +
            '                    <div class="layui-input-block">\n' +
            '                        <input value="' + data[0].userAddress + '"  type="text" id="userAddress" name="title" autocomplete="off" class="layui-input">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="layui-form-item layui-inline">\n' +
            '                    <div class="layui-input-block" style="margin: 18px 0;">\n' +
            '                        <button onclick="submitsetorder(' + data[0].orderId + ')" class="layui-btn">立即提交</button>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            ' <input class="layui-hide" id="checkImage" type="file" name="file" onchange="viewImage()" />\n'


    });
}

function submitsetorder(id) {
    var table = layui.table;
    console.log(id)
    var totalPrice = $("#totalPrice").val()
    var userAddress = $("#userAddress").val()
    console.log(totalPrice)
    console.log(userAddress)
    if (isNull(totalPrice) || isNull(userAddress)) {
        layer.msg('请输入合法数据', function () {
        });
        return false;
    }
    var order = {
        'orderId': id,
        'totalPrice': totalPrice,
        'userAddress': userAddress
    }

    $.ajax({
        type: 'POST',//方法类型
        url: '/admin/orders/update',
        contentType: 'application/json',
        data: JSON.stringify(order),
        success: function (r) {
            console.log(r)
            if (r.code == 0) {
                table.reload('indexconfig',
                    {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    })
                layer.closeAll();
            } else {
                layer.msg('失败', function () {
                });
            }
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
}


function orderCheckDone() {
    var table = layui.table;
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length < 1) {
        layer.msg('请选择数据进行修改', function () {
        });
        return true;
    }
    for (i = 0; i < data.length; i++) {
        if (data[i].orderStatus != 1) {
            layer.msg(data[i].orderNo + '订单的状态不是支付成功无法执行配货完成操作', function () {
            });
            return true;
        }
    }
    if (data.length >= 100) {
        layer.msg('你选择了太多状态不是支付成功的订单，无法执行配货完成操作', function () {
        });
        return;
    }
    var checkarrid = [];
    data.forEach((value) => {
        checkarrid.push(value.orderId)
    })

    console.log(checkarrid)
    console.log(data[0])

    layer.confirm('确认要执行配货完成操作吗?', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "/admin/orders/checkDone",
            contentType: "application/json",
            data: JSON.stringify(checkarrid),
            success: function (r) {
                console.log(r)
                if (r.code == 0) {
                    table.reload('indexconfig',
                        {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        })
                    layer.closeAll();
                    layer.msg(r.msg, function () {
                    });
                } else {
                    layer.msg(r.msg, function () {
                    });
                }
            }
        });
    }, function(){

    });

}

function orderCheckOut() {
    var table = layui.table;
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length < 1) {
        layer.msg('请选择数据进行修改', function () {
        });
        return true;
    }
    for (i = 0; i < data.length; i++) {
        if (data[i].orderStatus != 2) {
            layer.msg(data[i].orderNo + '订单的状态不是配货完成无法执行出库操作', function () {
            });
            return true;
        }
    }
    if (data.length >= 100) {
        layer.msg('你选择了太多状态不是支付成功的订单，无法执行出库操作', function () {
        });
        return;
    }
    var checkarrid = [];
    data.forEach((value) => {
        checkarrid.push(value.orderId)
    })

    console.log(checkarrid)
    console.log(data[0])

    layer.confirm('确认要执行出库操作吗?', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "/admin/orders/checkOut",
            contentType: "application/json",
            data: JSON.stringify(checkarrid),
            success: function (r) {
                console.log(r)
                if (r.code == 0) {
                    table.reload('indexconfig',
                        {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        })
                    layer.closeAll();
                    layer.msg(r.msg, function () {
                    });
                } else {
                    layer.msg(r.msg, function () {
                    });
                }
            }
        });
    }, function(){

    });

}

function closeOrder() {
    var table = layui.table;
    var checkStatus = table.checkStatus('indexconfig'),
        data = checkStatus.data; //获取选中行的数据
    if (data.length < 1) {
        layer.msg('请选择数据进行修改', function () {
        });
        return true;
    }

    var checkarrid = [];
    data.forEach((value) => {
        checkarrid.push(value.orderId)
    })

    layer.confirm('确认要关闭订单?', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "/admin/orders/close",
            contentType: "application/json",
            data: JSON.stringify(checkarrid),
            success: function (r) {
                console.log(r)
                if (r.code == 0) {
                    table.reload('indexconfig',
                        {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        })
                    layer.closeAll();
                    layer.msg(r.msg, function () {
                    });
                } else {
                    layer.msg(r.msg, function () {
                    });
                }
            }
        });
    }, function(){

    });
}

