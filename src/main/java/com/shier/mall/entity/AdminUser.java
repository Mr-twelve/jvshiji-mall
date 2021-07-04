package com.shier.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Demo class
 *
 * FieldTypeComment
 * admin_user_idint(11) NOT NULL管理员id
 * login_user_namevarchar(50) NOT NULL管理员登陆名称
 * login_passwordvarchar(50) NOT NULL管理员登陆密码
 * nick_namevarchar(50) NOT NULL管理员显示昵称
 * lockedtinyint(4) NULL是否锁定 0未锁定 1已锁定无法登陆
 * @author shier
 * @date 2021/3/11
 */
@ApiModel("管理员用户")
public class AdminUser {
    @ApiModelProperty("管理员ID")
    private Integer adminUserId;
    @ApiModelProperty("管理员用户名")
    private String loginUserName;
    @ApiModelProperty("管理员密码")
    private String loginPassword;
    @ApiModelProperty("管理员昵称")
    private String nickName;
    @ApiModelProperty(value = "管理员账号状态",hidden = true)
    private Byte locked;

    @Override
    public String toString() {
        return "AdminUser{" +
                "adminUserId=" + adminUserId +
                ", loginUserName='" + loginUserName + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", nickName='" + nickName + '\'' +
                ", locked=" + locked +
                '}';
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Byte getLocked() {
        return locked;
    }

    public void setLocked(Byte locked) {
        this.locked = locked;
    }
}
