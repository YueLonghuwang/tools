package com.rengu.tools7.mapper;


import com.rengu.tools7.entity.RoleEntiey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RoleEntiey> findAll(String compalyid);

    int delete(String id);

    void save(RoleEntiey roleEntiey);

    List<RoleEntiey> findByUserId(String userid);

    void update(RoleEntiey roleEntiey);

    RoleEntiey findById(String id);
}
