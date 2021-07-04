package com.shier.mall.controller.admin;

import com.shier.mall.entity.MallUser;
import com.shier.mall.service.MallUserService;
import com.shier.mall.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/12
 */
@Controller
@RequestMapping("admin")
public class UserController {
    @Resource
    private MallUserService mallUserService;

    @GetMapping("/users")
    public String usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        return "admin/malluser";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultPageQuery list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return new ResultPageQuery().getError("参数异常");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return mallUserService.getMallUsersPage(pageUtil);
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        if (ids.length < 1) {
            return new Result().getError("参数异常");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return new Result().getError("操作非法");
        }
        if (mallUserService.lockUsers(ids, lockStatus)) {
            return new Result().getSuccess("成功");
        } else {
            return new Result().getError("失败");
        }
    }
}
