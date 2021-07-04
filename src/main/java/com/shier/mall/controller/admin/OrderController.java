package com.shier.mall.controller.admin;

import com.shier.mall.controller.vo.OrderItemVO;
import com.shier.mall.entity.Order;
import com.shier.mall.service.OrderService;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.Result;
import com.shier.mall.util.ResultPageQuery;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/12
 */
@Controller
@RequestMapping("/admin")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/orders")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "orders");
        return "admin/order";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/orders/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultPageQuery list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return new ResultPageQuery().getError("参数异常");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return orderService.getOrdersPage(pageUtil);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Order order) {
        if (Objects.isNull(order.getTotalPrice())
                || Objects.isNull(order.getOrderId())
                || order.getOrderId() < 1
                || order.getTotalPrice() < 1
                || StringUtils.isEmpty(order.getUserAddress())) {
            return new Result().getError("参数异常");
        }
        String result = orderService.updateOrderInfo(order);
        if ("success".equals(result)) {
            return new Result().getSuccess("成功");
        } else {
            return new Result().getError(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/order-items/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        List<OrderItemVO> orderItems = orderService.getOrderItems(id);
        if (!CollectionUtils.isEmpty(orderItems)) {
            return new Result().getSuccess(orderItems);
        }
        return new Result().getError("获取失败");
    }

    /**
     * 配货
     */
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    public Result checkDone(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new Result().getError("参数异常");
        }
        String result = orderService.checkDone(ids);
        if ("success".equals(result)) {
            return new Result().getSuccess("成功");
        } else {
            return new Result().getError(result);
        }
    }

    /**
     * 出库
     */
    @RequestMapping(value = "/orders/checkOut", method = RequestMethod.POST)
    @ResponseBody
    public Result checkOut(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new Result().getError("参数异常");
        }
        String result = orderService.checkOut(ids);
        if ("success".equals(result)) {
            return new Result().getSuccess("成功");
        } else {
            return new Result().getError(result);
        }
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/orders/close", method = RequestMethod.POST)
    @ResponseBody
    public Result closeOrder(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new Result().getError("参数异常");
        }
        String result = orderService.closeOrder(ids);
        if ("success".equals(result)) {
            return new Result().getSuccess("成功");
        } else {
            return new Result().getError(result);
        }
    }

}
