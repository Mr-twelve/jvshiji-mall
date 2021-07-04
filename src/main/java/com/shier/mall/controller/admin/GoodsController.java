package com.shier.mall.controller.admin;

import com.shier.mall.common.Constants;
import com.shier.mall.controller.common.UploadController;
import com.shier.mall.entity.Goods;
import com.shier.mall.service.GoodsService;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.Result;
import com.shier.mall.util.ResultPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/14
 */
@Controller
@RequestMapping("/admin")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @GetMapping("/goodsedit")
    public String goods(HttpServletRequest request){
        request.setAttribute("path","goodsedit");
        return "admin/goods_edit";
    }


    @RequestMapping(value = "/goodsupimg")
    @ResponseBody
    public Result goodsupimg(@RequestBody MultipartFile file) {
        Result upLoad = new UploadController().upload(file);
            return upLoad;
    }

    @RequestMapping(value = "/goodsedit/addgoods",method = RequestMethod.POST)
    @ResponseBody
    public Result addgoods(@RequestBody Goods goods){
        if(goodsService.addGoods(goods)>0){
            return new Result().getSuccess("添加成功");
        }else {
            return new Result().getError("添加失败");
        }
    }


    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "goods");
        return "admin/goods";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultPageQuery list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return new ResultPageQuery().getError("参数异常");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return goodsService.getNewBeeMallGoodsPage(pageUtil);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return new Result().getError("参数异常");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return new Result().getError("状态异常");
        }
        if (goodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return new Result().getSuccess("成功");
        } else {
            return new Result().getError("修改失败");
        }
    }
}
