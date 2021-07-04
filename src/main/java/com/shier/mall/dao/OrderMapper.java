package com.shier.mall.dao;

import com.shier.mall.entity.Order;
import com.shier.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/8
 */
@Mapper
public interface OrderMapper {

    int insertSelective(Order record);

    Order selectByOrderNo(String orderNo);

    int getTotalOrders(PageQueryUtil pageUtil);

    List<Order> findOrderList(PageQueryUtil pageUtil);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int updateByPrimaryKeySelective(Order record);

    Order selectByPrimaryKey(Long id);

    List<Order> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkDone(@Param("orderIds") List<Long> asList);

    int checkOut(@Param("orderIds") List<Long> orderIds);
}
