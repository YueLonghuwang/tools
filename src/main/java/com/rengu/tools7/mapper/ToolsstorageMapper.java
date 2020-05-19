package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.tb_ToolsStorageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ToolsstorageMapper {
    List<tb_ToolsStorageEntity> findAll(String id);

    int delete(String id);

    tb_ToolsStorageEntity findById(String id);

    //保存
    int save(tb_ToolsStorageEntity tbToolsStorageEntity);

    void update(tb_ToolsStorageEntity tbToolsStorageEntity);
}
