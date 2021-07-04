package com.shier.mall.dao;

import com.shier.mall.entity.ShoppingCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
@Mapper
public interface ShoppingCartItemMapper {

    int deleteBatch(List<Long> ids);

    ShoppingCartItem selectByUserIdAndGoodsId(@Param("userId") Long UserId, @Param("goodsId") Long goodsId);

    ShoppingCartItem selectByPrimaryKey(Long cartItemId);

    int updateByPrimaryKeySelective(ShoppingCartItem record);

    int selectCountByUserId(Long userId);

    int insertSelective(ShoppingCartItem recod);

    List<ShoppingCartItem> selectByUserId(@Param("userId") Long userId, @Param("number") int number);

    int deleteByPrimaryKey(Long cartItemId);
}
