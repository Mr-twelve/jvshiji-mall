package com.shier.mall.service;

import com.shier.mall.entity.Carousel;
import com.shier.mall.util.PageQueryUtil;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/11
 */
public interface CarouselService {

    /**
     * 分页获取轮播图列表
     * @param pageQueryUtil
     * @return
     */
    List<Carousel> getCarouselList(PageQueryUtil pageQueryUtil);

    /**
     * 获取轮播图总数
     * @return
     */
    int getCarouselCount();

    /**
     * 按ID删除轮播图
     */
    int deleteCarousel(Integer[] arrCarouselId);

    /**
     * 添加轮播图
     * @param carousel
     * @return
     */
    int addCarousel(Carousel carousel);

    /**
     * 更新轮播图信息
     * @param carouselId
     * @param redirectUrl
     * @param carouselRank
     * @return
     */
    int updataCarousel(Integer carouselId, String redirectUrl, Integer carouselRank);

    /**
     * 根据排序值获得固定数量轮播图：供首页调用
     * @param num
     * @return
     */
    List<Carousel> indexCarousel(int num);
}
