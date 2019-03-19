package com.taotao.mapper;

import com.taotao.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    User selectByPhone(String phone);

    User selectByEmail(String email);

    User selectByUserNameAndPassword(@Param("username")String username, @Param("password")String password);
}