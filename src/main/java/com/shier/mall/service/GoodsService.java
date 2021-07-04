package com.shier.mall.service;

import com.shier.mall.entity.Goods;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.PageResult;
import com.shier.mall.util.ResultPageQuery;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/18
 */
public interface GoodsService {

    Goods getGoodsById(Long goodsId);

    /**
     * 向数据库添加商品
     * @param goods
     * @return
     */
    int addGoods(Goods goods);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchGoods(PageQueryUtil pageUtil);


    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    ResultPageQuery getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);
}
