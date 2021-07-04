package com.shier.mall.dao;

import com.shier.mall.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/14
 */
@Mapper
public interface CategoryMapper {

    /**
     * 根据categoryLevel,parentId查询分类列表
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
     * 根据分类ID获取分类
     * @param categoryId
     * @return
     */
    Category selectByPrimaryKey(Long categoryId);

    List<Category> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

}
