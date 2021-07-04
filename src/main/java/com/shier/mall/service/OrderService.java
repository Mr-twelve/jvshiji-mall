package com.shier.mall.service;



import com.shier.mall.controller.vo.MallUserVO;
import com.shier.mall.controller.vo.OrderDetailVO;
import com.shier.mall.controller.vo.OrderItemVO;
import com.shier.mall.controller.vo.ShoppingCartItemVO;
import com.shier.mall.entity.Order;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.PageResult;
import com.shier.mall.util.ResultPageQuery;

import java.util.List;

public interface OrderService {



    /**
     * 保存订单
     *
     * @param user 用户信息
     * @param myShoppingCartItems 用户购物车信息
     * @return 成功：订单号
     */
    String saveOrder(MallUserVO user, List<ShoppingCartItemVO> myShoppingCartItems);


    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);



    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);



    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);


    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    ResultPageQuery getOrdersPage(PageQueryUtil pageUtil);


    List<OrderItemVO> getOrderItems(Long id);
    /**
     * 订单信息修改
     *
     * @param order
     * @return
     */
    String updateOrderInfo(Order order);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);
    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);
}
