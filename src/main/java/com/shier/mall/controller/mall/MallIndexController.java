package com.shier.mall.controller.mall;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.vo.IndexConfigGoodsVO;
import com.shier.mall.entity.Carousel;
import com.shier.mall.entity.Category;
import com.shier.mall.service.CarouselService;
import com.shier.mall.service.CategoryService;
import com.shier.mall.service.IndexConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/8
 */
@RequestMapping("/jvshijimall")
@Controller
public class MallIndexController {

    @Autowired
    CarouselService carouselService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    IndexConfigService indexConfigService;

    @GetMapping({ "/","/index","/index.html"})
    public String index(HttpServletRequest request){
        List<Carousel> carouselList = carouselService.indexCarousel(Constants.INDEX_CAROUSEL_NUMBER);
        List<Category> allCategoryList = categoryService.getAllCategoryList();
        List<IndexConfigGoodsVO> hotGoodses = indexConfigService.getIndexConfigGoods(3, Constants.INDEX_GOODS_HOT_NUMBER);
        List<IndexConfigGoodsVO> newGoodses = indexConfigService.getIndexConfigGoods(4, Constants.INDEX_GOODS_NEW_NUMBER);
        List<IndexConfigGoodsVO> recommendGoodses = indexConfigService.getIndexConfigGoods(5, Constants.INDEX_GOODS_RECOMMOND_NUMBER);

        request.setAttribute("carouselList",carouselList);//分类数据
        request.setAttribute("categoryList",allCategoryList);//轮播图
        request.setAttribute("hotGoodses", hotGoodses);//热销商品
        request.setAttribute("newGoodses", newGoodses);//新品
        request.setAttribute("recommendGoodses", recommendGoodses);//推荐商品

        return "mall/index";
    }
}
