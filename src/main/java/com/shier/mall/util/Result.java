package com.shier.mall.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 包装信息返回
 *
 * @author shier
 * @date 2021/3/9
 */
@ApiModel("包装信息返回")
public class Result<T> implements Serializable {

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("描述信息")
    private String msg;

    @ApiModelProperty("包装数据")
    private T data;

    public Result(){

    }



    public Result getSuccess(String msg){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }

    public Result getSuccess(T data){
        Result result = new Result();
        result.setCode(0);
        result.setData(data);
        return result;
    }

    public Result getError(String msg){
        Result result = new Result();
        result.setCode(-1);
        result.setMsg(msg);
        return result;
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
}
