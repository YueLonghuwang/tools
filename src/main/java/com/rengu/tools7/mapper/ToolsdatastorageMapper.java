package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.tb_Toolsdatastorage;

import java.util.List;

public interface ToolsdatastorageMapper {
    List<tb_Toolsdatastorage> findAll();

    int delete(String id);

    tb_Toolsdatastorage findById(String id);

    //保存
    int save(tb_Toolsdatastorage userEntity);

    void update(tb_Toolsdatastorage user);

    Integer calculateFileSize(String fileId);

    /**
     * 根据文件id统计成果总字节数
     * @param fileId
     * @return
     */
    Integer StatisticalLine(String fileId);

    /**
     * 修改成果状态码
     * @param id
     * @param state
     */
    void fruitstate(String id, String state);

    /**
     * 根据用户id获取私有成果
     * @param state
     * @param userId
     * @return
     */
    List<tb_Toolsdatastorage> findPrivateFruit(String state, String userId, String fileId);

    /**
     * 获取共有成果
     * @param state
     * @return
     */
    List<tb_Toolsdatastorage> findPublicFruit(String state, String fileId);

    tb_Toolsdatastorage findByMD5(String md5);
}
