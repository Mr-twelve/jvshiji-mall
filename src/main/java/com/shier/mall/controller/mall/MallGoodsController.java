package com.shier.mall.controller.mall;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.GoodsDetailVO;
import com.shier.mall.controller.vo.SearchPageCategoryVO;
import com.shier.mall.entity.Goods;
import com.shier.mall.service.CategoryService;
import com.shier.mall.service.GoodsService;
import com.shier.mall.util.BeanUtil;
import com.shier.mall.util.PageQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
@Controller("/jvshijimall")
public class MallGoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 商品搜索功能
     * @param params
     * @param request
     * @return
     */
    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = categoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", goodsService.searchGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            return "error/error_5xx";
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        if (goods == null) {
            return "error/error_404";
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        request.setAttribute("goodsDetail", goodsDetailVO);
        return "mall/detail";
    }
}
