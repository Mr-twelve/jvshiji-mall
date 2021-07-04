package com.shier.mall.controller.admin;

import com.shier.mall.common.IndexConfigTypeEnum;
import com.shier.mall.entity.IndexConfig;
import com.shier.mall.service.impl.IndexConfigServiceImpl;
import com.shier.mall.util.PageQueryUtil;
import com.shier.mall.util.Result;
import com.shier.mall.util.ResultPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/19
 */
@Controller
@RequestMapping("admin")
public class IndexConfigController {

    @Autowired
    IndexConfigServiceImpl indexConfigServiceImpl;

    @GetMapping("/indexConfigs")
    public String indexConfigsPage(HttpServletRequest request, @RequestParam("configType") int configType) {
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);

        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            return "error/error_5xx";
        }

        request.setAttribute("path", indexConfigTypeEnum.getName());
        request.setAttribute("configType", configType);
        return "admin/index_config";
    }

    /**
     * 根据Type获取分页数据
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/indexConfig/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultPageQuery getListIndexConfig(@RequestParam Map<String, Object> param) {
        PageQueryUtil pageQueryUtil = new PageQueryUtil(param);
        int totalIndexConfig = indexConfigServiceImpl.getTotalIndexConfig(pageQueryUtil);
        List<IndexConfig> indexConfigs = indexConfigServiceImpl.getIndexConfig(pageQueryUtil);
        return new ResultPageQuery(0, "成功获取数据", totalIndexConfig, indexConfigs);
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/indexConfig/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteIndexConfig(@RequestBody List<Long> ids) {
        if (ids.size() < 1) {
            return new Result().getError("数据错误！");
        }
        Long[] idlist = ids.toArray(new Long[ids.size()]);
        if (indexConfigServiceImpl.deleteIndexConfig(idlist)) {
            return new Result().getSuccess("删除成功");
        } else {
            return new Result().getError("删除错误！");
        }
    }

    /**
     * 添加
     * @param indexConfig
     * @return
     */
    @RequestMapping(value = "/indexConfig/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addIndexConfig(@RequestBody IndexConfig indexConfig) {
        if(indexConfigServiceImpl.saveIndexConfig(indexConfig)>0){
            return new Result().getSuccess("添加成功");
        }else {
            return new Result().getError("添加失败");
        }
    }

    /**
     * 修改
     * @param indexConfig
     * @return
     */
    @RequestMapping(value = "/indexConfig/updata", method = RequestMethod.POST)
    @ResponseBody
    public Result updataIndexConfig(@RequestBody IndexConfig indexConfig) {
        if(indexConfigServiceImpl.updataIndexConfig(indexConfig)>0){
            return new Result().getSuccess("更新成功");
        }else {
            return new Result().getError("更新失败");
        }
    }
}
