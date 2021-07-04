package com.shier.mall.service.impl;

import com.shier.mall.controller.vo.SearchGoodsVO;
import com.shier.mall.dao.GoodsMapper;
import com.shier.mall.entity.Goods;
import com.shier.mall.service.GoodsService;
import com.shier.mall.util.BeanUtil;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.PageResult;
import com.shier.mall.util.ResultPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/18
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public int addGoods(Goods goods) {
        return goodsMapper.addGoods(goods);
    }

    @Override
    public PageResult searchGoods(PageQueryUtil pageUtil) {
        List<Goods> goodsList = goodsMapper.findGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalGoodsBySearch(pageUtil);
        List<SearchGoodsVO> searchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            searchGoodsVOS = BeanUtil.copyList(goodsList, SearchGoodsVO.class);
            for (SearchGoodsVO searchGoodsVOSitem : searchGoodsVOS) {
                String goodsName = searchGoodsVOSitem.getGoodsName();
                String goodsIntro = searchGoodsVOSitem.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    searchGoodsVOSitem.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    searchGoodsVOSitem.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(searchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Goods getGoodsById(Long goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public ResultPageQuery getNewBeeMallGoodsPage(PageQueryUtil pageUtil) {
        List<Goods> goodsList = goodsMapper.findGoodsList(pageUtil);
        int total = goodsMapper.getTotalGoods(pageUtil);
        return new ResultPageQuery().getSuccess(total,goodsList);
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }
}
