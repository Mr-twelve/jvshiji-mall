package com.shier.mall.controller.vo;

import com.shier.mall.entity.Category;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索页面分类数据VO
 */
public class SearchPageCategoryVO implements Serializable {

    private String firstLevelCategoryName;

    private List<Category> secondLevelCategoryList;

    private String secondLevelCategoryName;

    private List<Category> thirdLevelCategoryList;

    private String currentCategoryName;

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public List<Category> getSecondLevelCategoryList() {
        return secondLevelCategoryList;
    }

    public void setSecondLevelCategoryList(List<Category> secondLevelCategoryList) {
        this.secondLevelCategoryList = secondLevelCategoryList;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public List<Category> getThirdLevelCategoryList() {
        return thirdLevelCategoryList;
    }

    public void setThirdLevelCategoryList(List<Category> thirdLevelCategoryList) {
        this.thirdLevelCategoryList = thirdLevelCategoryList;
    }

    public String getCurrentCategoryName() {
        return currentCategoryName;
    }

    public void setCurrentCategoryName(String currentCategoryName) {
        this.currentCategoryName = currentCategoryName;
    }
}
