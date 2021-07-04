package com.shier.mall.dao;

import com.shier.mall.entity.Carousel;
import com.shier.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/11
 */
@Mapper
public interface CarouselMapper {

    /**
     * 后端：轮播图分页获取
     * @param pageQueryUtil
     * @return
     */
    List<Carousel> carouserlList(PageQueryUtil pageQueryUtil);

    /**
     * 后端：轮播图总数
     * @return
     */
    int getCount();

    /**
     * 后端：根据ID删除轮播图
     * @param arrCarouselId
     * @return
     */
    int deleteCarousel(Integer[] arrCarouselId);

    /**
     * 后端：添加轮播图
     * @param carousel
     * @return
     */
    int addCarousel(Carousel carousel);

    /**
     * 后端：更新轮播图信息
     * @param carouselId
     * @param redirectUrl
     * @param carouselRank
     * @return
     */
    int updataCarousel(@Param("carouselId") Integer carouselId,@Param("redirectUrl") String redirectUrl, @Param("carouselRank")Integer carouselRank);

    /**
     * 根据排序值获得固定数量轮播图：供首页调用
     * @param num
     * @return
     */
    List<Carousel> indexCarousel(int num);


}
