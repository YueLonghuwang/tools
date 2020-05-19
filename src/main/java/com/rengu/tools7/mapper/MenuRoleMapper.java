package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuRoleMapper {
    List<Menu> findMenuByRole(String roleId);

    void deleteByRoleId(String rid);

    void save(@Param("mid") String mid, @Param("rid") String rid);
}
