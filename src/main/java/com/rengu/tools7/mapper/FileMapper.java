package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface FileMapper {


    FileEntity findByMD5(String md5);

    FileEntity findById(String id);

    void add(FileEntity fileEntity);

    void state(String id, String state);

    List<Map<String,Object>> findAll();

    List<Map<String,Object>> KfindAll();

    void delete(String id);

    List<FileEntity> findByName(String name);

    List<FileEntity> findByNameAndScope(String fileName, String scope);

    List<Map<String,Object>> findByIds(String id);

    void addEvaluate(String id, Float averageScore);

    void downloadStatistics(String componentfileId);


}
