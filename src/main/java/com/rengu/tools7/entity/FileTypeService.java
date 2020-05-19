package com.rengu.tools7.entity;

import com.rengu.tools7.mapper.ClassifiedManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileTypeService {
    @Autowired
    private ClassifiedManagementMapper classifiedManagementMapper;
    public int addFileType(String type) {
        tb_ClassifiedManagementEntity tb_classifiedManagementEntity=new tb_ClassifiedManagementEntity();
        tb_classifiedManagementEntity.setId(UUID.randomUUID().toString());
        tb_classifiedManagementEntity.setType(type);
        return classifiedManagementMapper.save(tb_classifiedManagementEntity);
    }

    public int dropFileType(String id) {
         return  classifiedManagementMapper.delete(id);
    }

    public void updateFileType(tb_ClassifiedManagementEntity tb_classifiedManagementEntity) {
        classifiedManagementMapper.update(tb_classifiedManagementEntity);
    }
}
