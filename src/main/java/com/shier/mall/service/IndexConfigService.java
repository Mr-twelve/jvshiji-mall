package com.shier.mall.service;

import com.shier.mall.controller.vo.IndexConfigGoodsVO;
import com.shier.mall.entity.IndexConfig;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.Result;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/19
 */
public interface IndexConfigService {

    /**
     * 获取分页数据列表
     * @param pageQueryUtil
     * @return
     */
    List<IndexConfig> getIndexConfig(PageQueryUtil pageQueryUtil);

    int getTotalIndexConfig(PageQueryUtil pageQueryUtil);

    /**
     * 保存
     * @param indexConfig
     * @return
     */
    int saveIndexConfig(IndexConfig indexConfig);

    /**
     * 修改
     * @param indexConfig
     * @return
     */
    int updataIndexConfig(IndexConfig indexConfig);

    /**
     * 删除
     * @param ids
     * @return
     */
    Boolean deleteIndexConfig(Long[] ids);


    /**
     * 获取首页各类商品VO列表
     * @param configType
     * @param number
     * @return
     */
    List<IndexConfigGoodsVO> getIndexConfigGoods(int configType, int number);
}
