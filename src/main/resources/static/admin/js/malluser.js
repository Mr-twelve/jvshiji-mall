layui.use('table', function () {
    var table = layui.table;
    //第一个实例
    table.render({
        elem: '#indexconfig'
        , id: 'indexconfig'   //表格唯一标识
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , height: 'full-110'    //高度设定
        , loading: true  //切换分页时加载条开启
        , url: '/admin/users/list' //数据接口
        , page: true //开启分页
        , skin: 'line ' //行边框风格
        , cols: [[ //表头
            {type: 'numbers'}    //开启标号
            , {type: 'checkbox'}  //开启复选按钮
            , {field: 'nickName', title: '昵称'}
            , {field: 'loginName', title: '登录名', sort: true}
            , {
                field: 'lockedFlag', title: '锁定状态', sort: true, templet: function (res) {
                    if (res.lockedFlag == '0') {
                        return "<button type='button' class='layui-btn layui-btn-fluid'>正常</button>";
                    } else {
                        return "<button type='button' class='layui-btn layui-btn-fluid layui-btn-danger'>锁定</button>";
                    }
                }
            }
            , {field: 'createTime', title: '创建时间', sort: true}
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



