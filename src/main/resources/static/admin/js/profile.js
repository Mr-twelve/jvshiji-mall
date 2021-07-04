// $(function () {
//     //修改个人信息
//     $('#updateUserNameButton').click(function () {
//         $("#nameUpdate").attr("disabled",true);
//         var userName = $('#loginUserName').val();
//         var nickName = $('#nickName').val();
//         if (validUserNameForUpdate(userName, nickName)) {
//             //ajax提交数据
//             var params = $("#userNameForm").serialize();
//             $.ajax({
//                 type: "POST",
//                 url: "/admin/profile/name",
//                 data: params,
//                 success: function (r) {
//                     $("#updateUserNameButton").attr("disabled",false);
//                     console.log(r);
//                     if (r == 'success') {
//                         alert('修改成功');
//                     } else {
//                         alert('修改失败');
//                     }
//                 }
//             });
//         }else{
//             $("#updateUserNameButton").attr("disabled",false);
//         }
//
//     });
//修改密码
$('#updatePasswordButton').click(function () {
    $("#passwordUpdate").attr("disabled", true);
    var originalPassword = $('#originalPassword').val();
    var newPassword = $('#newPassword').val();
    if (validPasswordForUpdate(originalPassword, newPassword)) {
        var params = $("#userPasswordForm").serialize();
        $.ajax({
            type: "POST",
            url: "/admin/profile/password",
            data: params,
            success: function (r) {
                $("#updatePasswordButton").attr("disabled", false);
                console.log(r);
                if (r == 'success') {
                    alert('修改成功');
                    window.location.href = '/admin/login';
                } else {
                    alert('修改失败');
                }
            }
        });
    } else {
        $("#updatePasswordButton").attr("disabled", false);
    }

});

// })

/**
 * 名称验证
 */
function validUserNameForUpdate(userName, nickName) {

    if (isNull(userName) || userName.trim().length < 1) {
        layer.msg('请输入登陆名称', function () {
        });
        return false;
    }
    if (isNull(nickName) || nickName.trim().length < 1) {
        layer.msg('昵称不能为空', function () {
        });
        return false;
    }
    if (!validUserName(userName)) {
        layer.msg('请输入符合规范的登录名', function () {
        });
        return false;
    }
    if (!validCN_ENString2_18(nickName)) {
        layer.msg('请输入符合规范的昵称哦~', function () {
        });
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        layer.msg('请输入原密码~', function () {
        });
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        layer.msg('新密码不能为空', function () {
        });
        return false;
    }
    if (!validPassword(newPassword)) {
        layer.msg('请输入符合规范的密码', function () {
        });
        return false;
    }
    return true;
}

function nameUpdate() {
    console.log("xxx")
    var userName = $('#loginUserName').val();
    var nickName = $('#nickName').val();
    console.log(userName)
    console.log(nickName)
    if (validUserNameForUpdate(userName, nickName)) {
        //ajax提交数据
        var params = {
            'loginUserName': userName,
            'nickName': nickName
        };
        $.ajax({
            type: "POST",
            url: "/admin/profile/name",
            data: params,
            success: function (r) {
                $("#updateUserNameButton").attr("disabled", false);
                console.log(r);
                if (r == 'success') {
                    layer.msg('修改成功~', function () {
                    });
                } else {
                    layer.msg('修改失败~', function () {
                    });
                }
            }
        });
    } else {
        $("#updateUserNameButton").attr("disabled", false);
    }
}

function passwordUpdate() {
    var originalPassword = $('#originalPassword').val();
    var newPassword = $('#newPassword').val();
    if (validPasswordForUpdate(originalPassword, newPassword)) {
        var params = {
            'originalPassword':originalPassword,
            'newPassword':newPassword
        };
        $.ajax({
            type: "POST",
            url: "/admin/profile/password",
            data: params,
            success: function (r) {
                $("#updatePasswordButton").attr("disabled", false);
                console.log(r);
                if (r == 'success') {
                    layer.msg('修改成功~', function () {
                    });
                    setTimeout(function (){
                        window.location.href = '/admin/login';
                    },1000);
                } else {
                    layer.msg('修改失败~', function () {
                    });
                }
            }
        });
    } else {
        $("#updatePasswordButton").attr("disabled", false);
    }
}
