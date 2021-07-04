package com.shier.mall.service.impl;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.ShoppingCartItemVO;
import com.shier.mall.dao.GoodsMapper;
import com.shier.mall.dao.ShoppingCartItemMapper;
import com.shier.mall.entity.Goods;
import com.shier.mall.entity.ShoppingCartItem;
import com.shier.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
@Service
public class ShoppingCartItemServiceImpl implements com.shier.mall.service.ShoppingCartItemService {


    @Autowired
    ShoppingCartItemMapper shoppingCartItemMapper;

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 添加购物车
     * @param shoppingCartItem
     * @return
     */
    @Override
    public String saveCartItem(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItem temp = shoppingCartItemMapper.selectByUserIdAndGoodsId(shoppingCartItem.getUserId(), shoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            //todo count = tempCount + 1
            temp.setGoodsCount(shoppingCartItem.getGoodsCount());
            return updateNewBeeMallCartItem(temp);
        }
        //查询商品
        Goods newBeeMallGoods = goodsMapper.selectByPrimaryKey(shoppingCartItem.getGoodsId());
        //商品为空
        if (newBeeMallGoods == null) {
            return "商品不存在!";
        }
        int totalItem = shoppingCartItemMapper.selectCountByUserId(shoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (shoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return "超出单个商品的最大购买数量！";
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return "超出购物车最大容量！";
        }
        //保存记录
        if (shoppingCartItemMapper.insertSelective(shoppingCartItem) > 0) {
            return "SUCCESS";
        }
        return "database error";
    }

    /**
     * 更新购物车
     * @param shoppingCartItem
     * @return
     */
    @Override
    public String updateNewBeeMallCartItem(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItem shoppingCartItemUpdate = shoppingCartItemMapper.selectByPrimaryKey(shoppingCartItem.getCartItemId());
        if (shoppingCartItemUpdate == null) {
            return "未查询到记录！";
        }
        //超出单个商品的最大数量
        if (shoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return "超出单个商品的最大购买数量！";
        }
        //todo 数量相同不会进行修改
        //todo userId不同不能修改
        shoppingCartItemUpdate.setGoodsCount(shoppingCartItem.getGoodsCount());
        shoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (shoppingCartItemMapper.updateByPrimaryKeySelective(shoppingCartItemUpdate) > 0) {
            return "SUCCESS";
        }
        return "database error";
    }

    /**
     * 获取购物车数据
     * @param userId
     * @return
     */
    @Override
    public List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId) {
        List<ShoppingCartItemVO> shoppingCartItemVOS = new ArrayList<>();
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.selectByUserId(userId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(shoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> goodsIds = shoppingCartItems.stream().map(ShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<Goods> goods = goodsMapper.selectByPrimaryKeys(goodsIds);
            Map<Long, Goods> goodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(goods)) {
                goodsMap = goods.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
                ShoppingCartItemVO shoppingCartItemVO = new ShoppingCartItemVO();
                BeanUtil.copyProperties(shoppingCartItem, shoppingCartItemVO);
                if (goodsMap.containsKey(shoppingCartItem.getGoodsId())) {
                    Goods goodsTemp = goodsMap.get(shoppingCartItem.getGoodsId());
                    shoppingCartItemVO.setGoodsCoverImg(goodsTemp.getGoodsCoverImg());
                    String goodsName = goodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    shoppingCartItemVO.setGoodsName(goodsName);
                    shoppingCartItemVO.setSellingPrice(goodsTemp.getSellingPrice());
                    shoppingCartItemVOS.add(shoppingCartItemVO);
                }
            }
        }
        return shoppingCartItemVOS;
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId) {
        //todo userId不同不能删除
        return shoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }
}
