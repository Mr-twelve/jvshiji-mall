package com.shier.mall.controller.admin;

import com.shier.mall.entity.AdminUser;
import com.shier.mall.service.impl.AdminUserServiceImpl;
import com.shier.mall.util.Result;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/9
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminUserServiceImpl adminUserService;

    /**
     * 测试界面路由
     * @return
     */
    @GetMapping("/test")
    public String test(){
        return "my/test";
    }




    /**
     * 登录界面路由
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }


    /**
     * 后台主页面路由
     * @param request
     * @return
     */
    @GetMapping("/index")
    public String admin(HttpServletRequest request){
        request.setAttribute("path", "index");
        return "admin/index";
    }

    /**
     * 管理端登录
     * @param person
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> person,
                        HttpSession session) throws Exception {
        if(!session.getAttribute("captcha").equals(person.get("verifyCode"))){
            return new Result().getError("验证码错误");
        }
        AdminUser adminUser = adminUserService.login(person.get("username"),person.get("password"));
        if (adminUser != null){
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            //session过期时间设置为7200秒 即两小时
            //session.setMaxInactiveInterval(60 * 60 * 2);
            return new Result().getSuccess("index");
        }else {
            return new Result().getError("登录失败");
        }
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (org.springframework.util.StringUtils.isEmpty(originalPassword) || org.springframework.util.StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        if (org.springframework.util.StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(loginUserId, loginUserName, nickName)) {
            return "success";
        } else {
            return "修改失败";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }

}
