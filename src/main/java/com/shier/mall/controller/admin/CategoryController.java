package com.shier.mall.controller.admin;

import com.shier.mall.entity.Category;
import com.shier.mall.service.impl.CategoryServiceImpl;
import com.shier.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/13
 */

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryServiceimpl;

    /**
     * 分类管理路由
     *
     * @param request
     * @param categoryLevel
     * @param parentId
     * @return
     */
    @GetMapping("/category")
    public String categoryPage(HttpServletRequest request, @RequestParam("categoryLevel") Byte categoryLevel, @RequestParam("parentId") Long parentId) {
        request.setAttribute("path", "category");
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            return "error/error_5xx";
        }
        request.setAttribute("categoryLevel", categoryLevel);
        request.setAttribute("parentId", parentId);
        return "admin/category";
    }

    /**
     * 获取分类列表
     *
     * @param categoryLevel
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    @ResponseBody
    public Result categoryList(@RequestParam("categoryLevel") Byte categoryLevel, @RequestParam("parentId") Long parentId) {
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            return new Result().getError("参数错误");
        }
        List<Category> categoryList = categoryServiceimpl.getCategoryList(categoryLevel, parentId);
        return new Result().getSuccess(categoryList);
    }

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/category/addcategory", method = RequestMethod.POST)
    @ResponseBody
    public Result addCategory(@RequestBody Category category) {
        if (categoryServiceimpl.addCategory(category) > 0) {
            return new Result().getSuccess("添加成功");
        } else {
            return new Result().getError("添加失败");
        }
    }

    /**
     * 修改分类信息
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/category/setcategory", method = RequestMethod.POST)
    @ResponseBody
    public Result setCategory(@RequestBody Category category) {
        if (categoryServiceimpl.setCategory(category) > 0) {
            return new Result().getSuccess("修改成功");
        } else {
            return new Result().getError("修改失败");
        }
    }

    /**
     * 删除分类
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/category/deletecategory", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCategory(@RequestBody Integer[] ids) {
        if (categoryServiceimpl.deleteCategory(ids) > 0) {
            return new Result().getSuccess("删除成功");
        } else {
            return new Result().getError("删除失败");
        }
    }

    /**
     * 获取三级联动分类
     *
     * @param categoryLevel
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/category/checkboxlist",method = RequestMethod.GET)
    @ResponseBody
    public Result checkBoxCategory(@RequestParam("categoryLevel") Byte categoryLevel, @RequestParam("parentId") Long parentId) {
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            return new Result().getError("参数错误");
        }
        List<Category> categoryList1 = null;
        List<Category> categoryList2 = null;
        List<Category> categoryList3 = null;
        if (categoryLevel == 1) {
            categoryList1 = categoryServiceimpl.getCategoryList(categoryLevel, parentId);
            Long categoryList2Id = categoryList1.get(0).getCategoryId();
            categoryList2 = categoryServiceimpl.getCategoryList((byte) 2, categoryList2Id);
            Long categoryList3Id = categoryList2.get(0).getCategoryId();
            categoryList3 = categoryServiceimpl.getCategoryList((byte) 3, categoryList3Id);
        }
        if (categoryLevel == 2) {
            categoryList2 = categoryServiceimpl.getCategoryList((byte) 2, parentId);
            Long categoryList3Id = categoryList2.get(0).getCategoryId();
            categoryList3 = categoryServiceimpl.getCategoryList((byte) 3, categoryList3Id);
        }
        if (categoryLevel == 3) {
            categoryList3 = categoryServiceimpl.getCategoryList((byte) 3, parentId);
        }


        Map categoryResult = new HashMap();
        categoryResult.put("categoryList1",categoryList1);
        categoryResult.put("categoryList2",categoryList2);
        categoryResult.put("categoryList3",categoryList3);
        return new Result().getSuccess(categoryResult);
    }
}
