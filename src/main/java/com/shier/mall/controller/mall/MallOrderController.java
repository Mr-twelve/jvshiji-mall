package com.shier.mall.controller.mall;

import com.shier.mall.common.Constants;
import com.shier.mall.common.MallException;
import com.shier.mall.controller.vo.MallUserVO;
import com.shier.mall.controller.vo.OrderDetailVO;
import com.shier.mall.controller.vo.ShoppingCartItemVO;
import com.shier.mall.entity.Order;
import com.shier.mall.service.OrderService;
import com.shier.mall.service.ShoppingCartItemService;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.Result;
import com.shier.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
@RequestMapping("/jvshijimall")
@Controller
public class MallOrderController {

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    @Autowired
    private OrderService orderService;

    /**
     * 提交订单
     * @param httpSession
     * @return
     */
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {
        //取到用户信息
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        //根据用户ID查找购物车信息
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartItemService.getMyShoppingCartItems(user.getUserId());
        if (StringUtils.isEmpty(user.getAddress().trim())) {
            //无收货地址
            MallException.fail("地址不能为空！");
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            MallException.fail("购物车数据异常！");
        }
        //保存订单并返回订单号
        String saveOrderResult = orderService.saveOrder(user, myShoppingCartItems);
        //跳转到订单详情页
        return "redirect:/jvshijimall/orders/" + saveOrderResult;
    }

    /**
     * 订单详情跳转处理
     * @param request
     * @param orderNo
     * @param httpSession
     * @return
     */
    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        OrderDetailVO orderDetailVO = orderService.getOrderDetailByOrderNo(orderNo, user.getUserId());
        if (orderDetailVO == null) {
            return "error/error_5xx";
        }
        request.setAttribute("orderDetailVO", orderDetailVO);
        return "mall/order-detail";
    }


    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", orderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return "mall/my-orders";
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String cancelOrderResult = orderService.cancelOrder(orderNo, user.getUserId());
        if ("success".equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String finishOrderResult = orderService.finishOrder(orderNo, user.getUserId());
        if ("success".equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Order order = orderService.getOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", order.getTotalPrice());
        return "mall/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Order order = orderService.getOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", order.getTotalPrice());
        if (payType == 1) {
            return "mall/alipay";
        } else {
            return "mall/wxpay";
        }
    }

    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = orderService.paySuccess(orderNo, payType);
        if ("success".equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }
}