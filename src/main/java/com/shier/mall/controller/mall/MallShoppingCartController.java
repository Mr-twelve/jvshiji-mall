package com.shier.mall.controller.mall;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.MallUserVO;
import com.shier.mall.controller.vo.ShoppingCartItemVO;
import com.shier.mall.entity.ShoppingCartItem;
import com.shier.mall.service.ShoppingCartItemService;
import com.shier.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车
 *
 * @author shierS
 * @date 2021/4/7
 */
@RequestMapping("/jvshijimall")
@Controller
public class MallShoppingCartController {

    @Autowired
    ShoppingCartItemService shoppingCartItemService;


    /**
     * 将商品放入购物车
     * @param shoppingCartItem 接收的参数为 goodsId 字段和 goodsCount 字段，使用 @RequestBody 注解将其转换为 ShoppingCartItem 对象参数。
     * @param httpSession
     * @return
     */
    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveShoppingCartItem(@RequestBody ShoppingCartItem shoppingCartItem, HttpSession httpSession){
        MallUserVO user =(MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        shoppingCartItem.setUserId(user.getUserId());//添加操作用户的Id
        String saveResult = shoppingCartItemService.saveCartItem(shoppingCartItem);
        //添加成功
        if ("SUCCESS".equals(saveResult)){
            return new Result().getSuccess("SUCCESS");
        }
        //添加失败
        return new Result().getError(saveResult);
    }

    /**
     * 购物车页面渲染
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartItemService.getMyShoppingCartItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(ShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                return "error/error_5xx";
            }
            //总价
            for (ShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/cart";
    }

    /**
     * 购物车信息删除
     * @param newBeeMallShoppingCartItemId 要删除商品ID
     * @param httpSession
     * @return
     */
    @DeleteMapping("/shop-cart/{newBeeMallShoppingCartItemId}")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("newBeeMallShoppingCartItemId") Long newBeeMallShoppingCartItemId,
                                                   HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = shoppingCartItemService.deleteById(newBeeMallShoppingCartItemId);
        //删除成功
        if (deleteResult) {
            return new Result().getSuccess("删除成功");
        }
        //删除失败
        return new Result().getError("删除失败");
    }

    /**
     * 修改购物车商品数量
     * @param shoppingCartItem 商品ID
     * @param httpSession
     * @return
     */
    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@RequestBody ShoppingCartItem shoppingCartItem,
                                                   HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        shoppingCartItem.setUserId(user.getUserId());
        //todo 判断数量
        String updateResult = shoppingCartItemService.updateNewBeeMallCartItem(shoppingCartItem);
        //修改成功
        if ("SUCCESS".equals(updateResult)) {
            return new Result().getSuccess("修改成功");
        }
        //修改失败
        return new Result().getError("修改失败");
    }

    /**
     * 下单页面
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartItemService.getMyShoppingCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (ShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/order-settle";
    }
}
