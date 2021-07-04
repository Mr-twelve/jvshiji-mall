package com.shier.mall.service.impl;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.SearchPageCategoryVO;
import com.shier.mall.dao.CategoryMapper;
import com.shier.mall.entity.Category;
import com.shier.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/14
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryList(Byte categoryLevel, Long parentId) {
        return categoryMapper.getCategoryList(categoryLevel,parentId);
    }

    @Override
    public int addCategory(Category category) {
        return categoryMapper.addCategory(category);
    }

    @Override
    public int setCategory(Category category) {
        return categoryMapper.setCategory(category);
    }

    @Override
    public int deleteCategory(Integer[] ids) {
        return categoryMapper.deleteCategory(ids);
    }

    @Override
    public List<Category> getAllCategoryList() {
        return categoryMapper.getAllCategoryList();
    }

    @Override
    public SearchPageCategoryVO getCategoriesForSearch(Long categoryId) {
        SearchPageCategoryVO searchPageCategoryVO = new SearchPageCategoryVO();
        Category thirdLevelGoodsCategory = categoryMapper.selectByPrimaryKey(categoryId);
        if (thirdLevelGoodsCategory != null && thirdLevelGoodsCategory.getCategoryLevel() == (byte) 3) {
            //获取当前三级分类的二级分类
            Category secondLevelGoodsCategory = categoryMapper.selectByPrimaryKey(thirdLevelGoodsCategory.getParentId());
            if (secondLevelGoodsCategory != null && secondLevelGoodsCategory.getCategoryLevel() == (byte) 2) {
                //获取当前二级分类下的三级分类List
                List<Category> thirdLevelCategories = categoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelGoodsCategory.getCategoryId()), 3, Constants.SEARCH_CATEGORY_NUMBER);
                searchPageCategoryVO.setCurrentCategoryName(thirdLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setSecondLevelCategoryName(secondLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                return searchPageCategoryVO;
            }
        }
        return null;
    }
}
