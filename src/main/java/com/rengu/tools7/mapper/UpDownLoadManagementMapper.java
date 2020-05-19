package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.tb_UpDownLoadManagementEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UpDownLoadManagementMapper {
    List<tb_UpDownLoadManagementEntity> findAll(String id);

    int delete(String id);

    tb_UpDownLoadManagementEntity findById(String id);

    //保存
    int save(tb_UpDownLoadManagementEntity tbToolsStorageEntity);

    void update(tb_UpDownLoadManagementEntity tbToolsStorageEntity);
}
