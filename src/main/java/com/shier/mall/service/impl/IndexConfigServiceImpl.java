package com.shier.mall.service.impl;

import com.shier.mall.controller.vo.IndexConfigGoodsVO;
import com.shier.mall.dao.GoodsMapper;
import com.shier.mall.dao.IndexConfigMapper;
import com.shier.mall.entity.Goods;
import com.shier.mall.entity.IndexConfig;
import com.shier.mall.service.IndexConfigService;
import com.shier.mall.util.BeanUtil;
import com.shier.mall.util.PageQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/19
 */
@Service
public class IndexConfigServiceImpl implements IndexConfigService {

    @Autowired
    IndexConfigMapper indexConfigMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<IndexConfig> getIndexConfig(PageQueryUtil pageQueryUtil) {
        return indexConfigMapper.getIndexConfigByType(pageQueryUtil);
    }

    @Override
    public int getTotalIndexConfig(PageQueryUtil pageQueryUtil) {
        return indexConfigMapper.getIndexConfigTotall(pageQueryUtil);
    }

    @Override
    public int saveIndexConfig(IndexConfig indexConfig) {
        return indexConfigMapper.addIndexConfig(indexConfig);
    }

    @Override
    public int updataIndexConfig(IndexConfig indexConfig) {
        return indexConfigMapper.updataIndexConfig(indexConfig);
    }

    @Override
    public Boolean deleteIndexConfig(Long[] ids) {
        return indexConfigMapper.deleteIndexConfig(ids) > 0;
    }

    @Override
    public List<IndexConfigGoodsVO> getIndexConfigGoods(int configType, int number) {
        List<IndexConfigGoodsVO> indexConfigGoodsVOS = new ArrayList<>(number);
        List<IndexConfig> indexConfigs = indexConfigMapper.getIndexConfigByTypeAndNum(configType, number);
        if (!(CollectionUtils.isEmpty(indexConfigs))) {
            //取出所有id
            List<Long> ids = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
            List<Goods> goods = goodsMapper.selectByPrimaryKeys(ids);
            indexConfigGoodsVOS = BeanUtil.copyList(goods, IndexConfigGoodsVO.class);
            for (IndexConfigGoodsVO newBeeMallIndexConfigGoodsVO : indexConfigGoodsVOS) {
                String goodsName = newBeeMallIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = newBeeMallIndexConfigGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    newBeeMallIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    newBeeMallIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        return indexConfigGoodsVOS;
    }
}
