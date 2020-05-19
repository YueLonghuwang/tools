package com.rengu.tools7.mapper;





import com.rengu.tools7.entity.tb_ToolsBaseinfoEntity;

import java.util.List;

public interface ToolsBaseinfoMapper {

    List<tb_ToolsBaseinfoEntity> findAll();

    int delete(String id);

    tb_ToolsBaseinfoEntity findById(String id);

    //保存
    int save(tb_ToolsBaseinfoEntity tbToolsBaseinfoEntity);

    void update(tb_ToolsBaseinfoEntity tbToolsBaseinfoEntity);
}
