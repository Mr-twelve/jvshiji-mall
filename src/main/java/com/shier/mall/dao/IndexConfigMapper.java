package com.shier.mall.dao;

import com.shier.mall.entity.IndexConfig;
import com.shier.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/19
 */
@Mapper
public interface IndexConfigMapper {

    /**
     * 添加首页配置
     * @param indexConfig
     * @return
     */
    int addIndexConfig(IndexConfig indexConfig);

    /**
     * 修改首页配置
     * @param indexConfig
     * @return
     */
    int updataIndexConfig(IndexConfig indexConfig);

    /**
     * 根据ID删除一组数据
     * @param ids
     * @return
     */
    int deleteIndexConfig(Long[] ids);

    /**
     * 分页时获取Type的总数
     * @param pageQueryUtil
     * @return
     */
    int getIndexConfigTotall(PageQueryUtil pageQueryUtil);

    /**
     * 根据type获取分页数据列表
     * @param pageQueryUtil
     * @return
     */
    List<IndexConfig> getIndexConfigByType(PageQueryUtil pageQueryUtil);

    /**
     * 供首页调取，根据type获取指定数量
     * @param configType
     * @param number
     * @return
     */
    List<IndexConfig> getIndexConfigByTypeAndNum(@Param("configType") int configType, @Param("number") int number);



}
