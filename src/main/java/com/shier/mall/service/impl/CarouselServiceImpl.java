package com.shier.mall.service.impl;

import com.shier.mall.dao.CarouselMapper;
import com.shier.mall.entity.Carousel;
import com.shier.mall.service.CarouselService;
import com.shier.mall.util.PageQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/11
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    CarouselMapper carouselMapper;

    @Override
    public int deleteCarousel(Integer[] arrCarouselId) {
        return carouselMapper.deleteCarousel(arrCarouselId);
    }

    @Override
    public List<Carousel> getCarouselList(PageQueryUtil pageQueryUtil) {
        return carouselMapper.carouserlList(pageQueryUtil);
    }

    @Override
    public int getCarouselCount() {
        return carouselMapper.getCount();
    }

    @Override
    public int addCarousel(Carousel carousel) {
        return carouselMapper.addCarousel(carousel);
    }

    @Override
    public int updataCarousel(Integer carouselId, String redirectUrl, Integer carouselRank) {
        return carouselMapper.updataCarousel(carouselId,redirectUrl,carouselRank);
    }

    @Override
    public List<Carousel> indexCarousel(int num) {
        return carouselMapper.indexCarousel(num);
    }
}
