package com.shier.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Demo class
 *   `goods_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
 *   `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '商品名',
 *   `goods_intro` varchar(200) NOT NULL DEFAULT '' COMMENT '商品简介',
 *   `goods_category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联分类id',
 *   `goods_cover_img` varchar(200) NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
 *   `goods_carousel` varchar(500) NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
 *   `goods_detail_content` text NOT NULL COMMENT '商品详情',
 *   `original_price` int(11) NOT NULL DEFAULT '1' COMMENT '商品价格',
 *   `selling_price` int(11) NOT NULL DEFAULT '1' COMMENT '商品实际售价',
 *   `stock_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存数量',
 *   `tag` varchar(20) NOT NULL DEFAULT '' COMMENT '商品标签',
 *   `goods_sell_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品上架状态 0-下架 1-上架',
 *   `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '添加者主键id',
 *   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
 *   `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者主键id',
 *   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
 * @author shier
 * @date 2021/3/15
 */
@ApiModel("商品表")
public class Goods {

    @ApiModelProperty("商品表主键id")
    private Long goodsId;

    @ApiModelProperty("商品名")
    private String goodsName;

    @ApiModelProperty("商品简介")
    private String goodsIntro;

    @ApiModelProperty("关联分类Id")
    private Long goodsCategoryId;

    @ApiModelProperty("商品主图")
    private String goodsCoverImg;

    @ApiModelProperty("商品轮播图")
    private String goodsCarousel;

    @ApiModelProperty("商品价格")
    private Integer originalPrice;

    @ApiModelProperty("商品实际售价")
    private Integer sellingPrice;

    @ApiModelProperty("商品库存数量")
    private Integer stockNum;

    @ApiModelProperty("商品标签")
    private String tag;

    @ApiModelProperty("商品上架状态0-下架 1-上架")
    private Byte goodsSellStatus;

    @ApiModelProperty("添加者主键id")
    private Integer createUser;

    @ApiModelProperty("商品添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("修改者主键id")
    private Integer updateUser;

    @ApiModelProperty("商品修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("商品详情")
    private String goodsDetailContent;

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsIntro='" + goodsIntro + '\'' +
                ", goodsCategoryId=" + goodsCategoryId +
                ", goodsCoverImg='" + goodsCoverImg + '\'' +
                ", goodsCarousel='" + goodsCarousel + '\'' +
                ", originalPrice=" + originalPrice +
                ", sellingPrice=" + sellingPrice +
                ", stockNum=" + stockNum +
                ", tag='" + tag + '\'' +
                ", goodsSellStatus=" + goodsSellStatus +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                ", goodsDetailContent='" + goodsDetailContent + '\'' +
                '}';
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public Long getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }

    public String getGoodsCarousel() {
        return goodsCarousel;
    }

    public void setGoodsCarousel(String goodsCarousel) {
        this.goodsCarousel = goodsCarousel;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Byte getGoodsSellStatus() {
        return goodsSellStatus;
    }

    public void setGoodsSellStatus(Byte goodsSellStatus) {
        this.goodsSellStatus = goodsSellStatus;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGoodsDetailContent() {
        return goodsDetailContent;
    }

    public void setGoodsDetailContent(String goodsDetailContent) {
        this.goodsDetailContent = goodsDetailContent;
    }
}
