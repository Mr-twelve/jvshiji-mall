package com.shier.mall.interceptor;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.MallUserVO;
import com.shier.mall.dao.ShoppingCartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * newbee-mall购物车数量处理
 *
 */
@Component
public class CartNumberInterceptor implements HandlerInterceptor {

    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //购物车中的数量会更改，但是在这些接口中并没有对session中的数据做修改，这里统一处理一下
        if (null != request.getSession() && null != request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)) {
            //如果当前为登陆状态，就查询数据库并设置购物车中的数量值
            MallUserVO mallUserVO = (MallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
            //设置购物车中的数量
            mallUserVO.setShopCartItemCount(shoppingCartItemMapper.selectCountByUserId(mallUserVO.getUserId()));
            request.getSession().setAttribute(Constants.MALL_USER_SESSION_KEY, mallUserVO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
