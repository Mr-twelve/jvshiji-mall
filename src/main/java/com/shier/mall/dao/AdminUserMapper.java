package com.shier.mall.dao;

import com.shier.mall.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/11
 */
@Mapper
public interface AdminUserMapper {
    AdminUser login(@Param("loginUserName") String loginUserName,@Param("loginPassword") String loginPassword);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer adminUserId);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}
