package com.shier.mall.service;

import com.shier.mall.controller.vo.SearchPageCategoryVO;
import com.shier.mall.entity.Category;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/14
 */
public interface CategoryService {
    /**
     * 按categoryLevel,parentId获取分类列表
     * @param categoryLevel
     * @param parentId
     * @return
     */
    List<Category> getCategoryList(Byte categoryLevel, Long parentId);

    /**
     * 添加分类
     * @param category
     * @return
     */
    int addCategory(Category category);

    /**
     * 修改分类
     * @param category
     * @return
     */
    int setCategory(Category category);

    /**
     * 删除分类
     * @param ids
     * @return
     */
    int deleteCategory(Integer[] ids);

    /**
     * 获取所有分类供首页调用
     * @return
     */
    List<Category> getAllCategoryList();

    /**
     * 返回分类数据(搜索页调用)
     *
     * @param categoryId
     * @return
     */
    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);
}
