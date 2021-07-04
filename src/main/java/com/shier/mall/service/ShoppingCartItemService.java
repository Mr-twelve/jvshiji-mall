package com.shier.mall.service;

import com.shier.mall.controller.vo.ShoppingCartItemVO;
import com.shier.mall.entity.ShoppingCartItem;

import java.util.List;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
public interface ShoppingCartItemService {
    /**
     * 保存商品至购物车中
     *
     * @param shoppingCartItem
     * @return
     */
    String saveCartItem(ShoppingCartItem shoppingCartItem);

    /**
     * 修改购物车中的属性
     *
     * @param shoppingCartItem
     * @return
     */
    String updateNewBeeMallCartItem(ShoppingCartItem shoppingCartItem);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param userId
     * @return
     */
    List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId);

    /**
     * 删除购物车中的商品
     *
     * @param shoppingCartItemId
     * @returns
     */
    Boolean deleteById(Long shoppingCartItemId);
}
