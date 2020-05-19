package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.tb_VersionManagementEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VersionManagementMapper {
    List<tb_VersionManagementEntity> findAll(String id);

    int delete(String id);

    tb_VersionManagementEntity findById(String id);

    //保存
    int save(tb_VersionManagementEntity tbToolsStorageEntity);

    void update(tb_VersionManagementEntity tbToolsStorageEntity);
}
