package com.rengu.tools7.mapper;



import com.rengu.tools7.entity.RoleEntiey;
import com.rengu.tools7.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Eason岳
 * @date 2020/5/7
 */

@Component
@Mapper
public interface UserMapper {
    /**
     * 根据公司id查询所以
     * @param companyId
     * @return
     */
    List<UserEntity> findAll(String companyId);

    List<RoleEntiey> findRolesByUserId(String id);
    int insertUser(UserEntity user);
    int insertUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    UserEntity loadUserByUsername(String s);

    int save(UserEntity user);

    void update(UserEntity user);

    void delete(String id);
}
