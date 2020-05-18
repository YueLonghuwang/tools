package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.Role;
import com.rengu.tools7.entity.User;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zeng
 * @date 2020/2/24 22:41
 */

@Component
@Mapper
public interface UserMapper {
    User loadUserByUsername(String s);
    List<Role> findRolesByUserId(Integer id);

    int addUser(User user);

    int insertUserRole(@Param("userId")int userId, @Param("roleId")int roleId);

    List<User> findUserByLimit(String limits);
}
