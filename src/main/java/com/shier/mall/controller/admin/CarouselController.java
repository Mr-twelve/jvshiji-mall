package com.shier.mall.controller.admin;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.common.UploadController;
import com.shier.mall.entity.Carousel;
import com.shier.mall.service.impl.CarouselServiceImpl;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.Result;
import com.shier.mall.util.ResultPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 后台轮播图控制器
 *
 * @author shier
 * @date 2021/3/12
 */
@Controller
@RequestMapping("/admin")
public class CarouselController {

    @Autowired
    CarouselServiceImpl carouselServiceImpl;

    /**
     * 后台轮播图路由
     *
     * @param request
     * @return
     */
    @GetMapping("/carousel")
    public String carousel(HttpServletRequest request) {
        request.setAttribute("path", "carousel");
        return "admin/carousel";
    }

    /**
     * 轮播图列表查询
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/carousel/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultPageQuery CarouselList(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return new ResultPageQuery().getError("参数异常");
        }
        int count = carouselServiceImpl.getCarouselCount();
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        List<Carousel> carouselList = carouselServiceImpl.getCarouselList(pageQueryUtil);
        return new ResultPageQuery().getSuccess(count, carouselList);
    }

    /**
     * 删除轮播图
     */
    @RequestMapping(value = "/carousel/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCarousel(@RequestBody List<Integer> arrId) {
        Integer[] arrCarouselId = arrId.toArray(new Integer[arrId.size()]);
        if (carouselServiceImpl.deleteCarousel(arrCarouselId) > 0) {
            return new Result().getSuccess("删除成功");
        }
        return new Result().getError("传输失败");

    }

    /**
     * 添加、修改
     */
    @RequestMapping("/carousel/addCarousel")
    @ResponseBody
    public Result add(MultipartFile file, String redirectUrl, Integer carouselRank,Integer carouselId) {
        if(carouselId != -1){   //判断从传入id，为-1时添加轮播图，否则修改轮播图
            //更新调用
            if(carouselId!=null){
               if(carouselServiceImpl.updataCarousel(carouselId,redirectUrl, carouselRank) > 0){
                   return new Result().getSuccess("修改成功");
               }
            }else {
                return new Result().getError("参数不合法");
            }
        }
        Result upLoad = new UploadController().upload(file);
        if (upLoad.getCode() != 0) {
            return new Result().getError("上传文件失败");
        }
        String carouselUrl = upLoad.getMsg();
        Carousel carousel = new Carousel();
        carousel.setCarouselUrl(carouselUrl);
        carousel.setRedirectUrl(redirectUrl);
        carousel.setCarouselRank(carouselRank);
        Byte isDelete = 0;
        carousel.setIsDeleted(isDelete);
        if (carouselServiceImpl.addCarousel(carousel)>0){
            return new Result().getSuccess("添加成功");
        }else {
            return new Result().getError("添加失败");
        }
    }
}
