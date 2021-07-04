package com.shier.mall.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 分页信息
 *
 * @author shier
 * @date 2021/3/12
 */
public class PageQueryUtil extends LinkedHashMap<String,Object> {
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public PageQueryUtil(Map<String, Object> params) {
        this.putAll(params);
        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
