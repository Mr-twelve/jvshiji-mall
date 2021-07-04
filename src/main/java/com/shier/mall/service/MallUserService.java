package com.shier.mall.service;

import com.shier.mall.controller.vo.MallUserVO;
import com.shier.mall.entity.MallUser;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.PageResult;
import com.shier.mall.util.ResultPageQuery;

import javax.servlet.http.HttpSession;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
public interface MallUserService {

    /**
     * 用户注册
     * @param loginName
     * @param password
     * @return
     */
    public String register(String loginName, String password);

    /**
     * 用户登录
     * @param loginName
     * @param passwordMD5
     * @param httpSession
     * @return
     */
    String login(String loginName, String passwordMD5, HttpSession httpSession);

    /**
     * 用户信息修改并返回最新的用户信息
     *
     * @param mallUser
     * @return
     */
    MallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    ResultPageQuery getMallUsersPage(PageQueryUtil pageUtil);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Integer[] ids, int lockStatus);
}
