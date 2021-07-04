package com.shier.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/14
 *
 *
FieldTypeComment
category_id bigint(20) NOT NULL分类id
category_level tinyint(4) NOT NULL分类级别(1-一级分类 2-二级分类 3-三级分类)
parent_id bigint(20) NOT NULL父分类id
category_name varchar(50) NOT NULL分类名称
category_rank int(11) NOT NULL排序值(字段越大越靠前)
is_deleted tinyint(4) NOT NULL删除标识字段(0-未删除 1-已删除)
create_time datetime NOT NULL创建时间
create_user int(11) NOT NULL创建者id
update_time datetime NOT NULL修改时间
update_user int(11) NULL修改者id
 *
 *
 *
 */
@ApiModel("分类表")
public class Category {
    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("分类级别")
    private Byte categoryLevel;

    @ApiModelProperty("父分类id")
    private Long parentId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("排序值")
    private Integer categoryRank;

    @ApiModelProperty("删除标识字段")
    private Byte isDeleted;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("创建者id")
    private Integer createUser;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("修改者id")
    private Integer updateUser;

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryLevel=" + categoryLevel +
                ", parentId=" + parentId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryRank=" + categoryRank +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Byte categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryRank() {
        return categoryRank;
    }

    public void setCategoryRank(Integer categoryRank) {
        this.categoryRank = categoryRank;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}
