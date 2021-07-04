package com.shier.mall.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 前端分页返回结果
 *
 * @author shier
 * @date 2021/3/12
 */

@ApiModel("前端分页返回结果")
public class ResultPageQuery<T> implements Serializable {

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("描述信息")
    private String msg;

    @ApiModelProperty("数据总数")
    private int count;

    @ApiModelProperty("封装数据")
    private T data;

    public ResultPageQuery() {
    }

    public ResultPageQuery(int code, String msg, int count, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public ResultPageQuery getSuccess( int count,T data) {
        return new ResultPageQuery(0, "", count, data);
    }

    public ResultPageQuery getError(String msg) {
        return new ResultPageQuery(-1, msg, 0, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
