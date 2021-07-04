package com.shier.mall.controller.mall;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.MallUserVO;
import com.shier.mall.entity.MallUser;
import com.shier.mall.service.MallUserService;
import com.shier.mall.util.MD5Util;
import com.shier.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/21
 */
@RequestMapping("/jvshijimall")
@Controller
public class MallPersonalController {

    @Autowired
    MallUserService mallUserService;

    /**
     * 登陆页面跳转
     *
     * @return
     */
    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mall/login";
    }

    /**
     * 注册页面跳转
     *
     * @return
     */
    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    /**
     * 用户登出
     * @param httpSession
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        return "mall/login";
    }

    /**
     * 用户注册
     * @param loginName
     * @param verifyCode
     * @param password
     * @param httpSession
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName) ||
                StringUtils.isEmpty(verifyCode) ||
                StringUtils.isEmpty(password)) {
            return new Result().getError("信息错误");
        }
        if(!httpSession.getAttribute("captcha").equals(verifyCode)){
            return new Result().getError("验证码错误");
        }

        String register = mallUserService.register(loginName,password);
        if ("SUCCESS".equals(register)){
            return new Result().getSuccess("注册成功");
        }else {
            return new Result().getError(register);
        }
    }

    /**
     * 用户登录
     * @param loginName
     * @param verifyCode
     * @param password
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession){
        if (StringUtils.isEmpty(loginName) ||
                StringUtils.isEmpty(verifyCode) ||
                StringUtils.isEmpty(password)) {
            return new Result().getError("信息错误");
        }
//        if(!httpSession.getAttribute("captcha").equals(verifyCode)){
//            return new Result().getError("验证码错误");
//        }

        String loginResult = mallUserService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        //登录成功
        if ("SUCCESS".equals(loginResult)) {
            return new Result().getSuccess("登录成功");
        }
        //登录失败
        return new Result().getSuccess(loginResult);

    }

    /**
     * 用户信息更新
     * @param mallUser
     * @param httpSession
     * @return
     */
    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        MallUserVO mallUserTemp = mallUserService.updateUserInfo(mallUser,httpSession);
        if (mallUserTemp == null) {
            Result result = new Result().getError("修改失败");
            return result;
        } else {
            //返回成功
            Result result = new Result().getSuccess("修改成功");
            return result;
        }
    }

    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "personal");
        return "mall/personal";
    }

    @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }
}
