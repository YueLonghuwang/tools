package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.tb_Toolsevaluate;
import org.apache.ibatis.annotations.Mapper;


//评价表
@Mapper
public interface ToolsevaluateMapper {
    int delete(String poolId, String userId);

    tb_Toolsevaluate findById(String poolId, String userId);

    //保存
    int save(tb_Toolsevaluate userEntity);

    void update(tb_Toolsevaluate user);

    float statisticsEvaluateStart(String pool_id);

    int Pdelete(String pool_id);
}
