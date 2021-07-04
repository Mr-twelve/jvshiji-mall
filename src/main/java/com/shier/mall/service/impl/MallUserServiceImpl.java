package com.shier.mall.service.impl;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.MallUserVO;
import com.shier.mall.dao.MallUserMapper;
import com.shier.mall.entity.MallUser;
import com.shier.mall.service.MallUserService;
import com.shier.mall.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
@Service
public class MallUserServiceImpl implements MallUserService {

    @Autowired
    MallUserMapper mallUserMapper;

    @Override
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return "用户名已存在";
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) > 0) {
            return "SUCCESS";
        }
        return "database error";
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return "用户已被禁止登录";
            }
            //昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            MallUserVO MallUserVO = new MallUserVO();
            BeanUtil.copyProperties(user, MallUserVO);
            //设置用户session 设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, MallUserVO);
            return "SUCCESS";
        }
        return "登录失败";
    }

    @Override
    public MallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        MallUserVO userTemp = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallUser userFromDB = mallUserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (!StringUtils.isEmpty(mallUser.getNickName())) {
                userFromDB.setNickName(MallUtils.cleanString(mallUser.getNickName()));
            }
            if (!StringUtils.isEmpty(mallUser.getAddress())) {
                userFromDB.setAddress(MallUtils.cleanString(mallUser.getAddress()));
            }
            if (!StringUtils.isEmpty(mallUser.getIntroduceSign())) {
                userFromDB.setIntroduceSign(MallUtils.cleanString(mallUser.getIntroduceSign()));
            }
            if (mallUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                MallUserVO mallUserVO = new MallUserVO();
                userFromDB = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, mallUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, mallUserVO);
                return mallUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }

    @Override
    public ResultPageQuery getMallUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        return new ResultPageQuery().getSuccess(total,mallUsers);
    }
}
