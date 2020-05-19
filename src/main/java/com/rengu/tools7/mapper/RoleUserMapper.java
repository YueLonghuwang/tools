package com.rengu.tools7.mapper;


import com.rengu.tools7.entity.RoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleUserMapper {

    void deleteUserRole(String userid);

    void saveUserRole(@Param("userId") String userI, @Param("roleId") String roleId);

   RoleUser findById(String userid);
}
