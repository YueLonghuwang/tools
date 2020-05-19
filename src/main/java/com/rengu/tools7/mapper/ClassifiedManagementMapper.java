package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.tb_ClassifiedManagementEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassifiedManagementMapper {
    List<tb_ClassifiedManagementEntity> findAll(String id);

    int delete(String id);

    tb_ClassifiedManagementEntity findById(String id);

    //保存
    int save(tb_ClassifiedManagementEntity classifiedManagementEntity);

    void update(tb_ClassifiedManagementEntity classifiedManagementEntity);
}
