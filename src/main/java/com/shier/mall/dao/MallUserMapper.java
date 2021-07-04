package com.shier.mall.dao;

import com.shier.mall.entity.MallUser;
import com.shier.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo class
 *
 * @author shierS
 * @date 2021/4/7
 */
@Mapper
public interface MallUserMapper {

    MallUser selectByLoginName(String loginName);

    int insertSelective(MallUser record);

    MallUser selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    MallUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(MallUser record);

    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);

    List<MallUser> findMallUserList(PageQueryUtil pageUtil);

    int getTotalMallUsers(PageQueryUtil pageUtil);
}
