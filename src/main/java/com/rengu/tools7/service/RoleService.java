package com.rengu.tools7.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rengu.tools7.entity.RoleEntiey;
import com.rengu.tools7.enums.SystemErrorEnum;
import com.rengu.tools7.exception.SystemException;
import com.rengu.tools7.mapper.RoleMapper;
import com.rengu.tools7.mapper.RoleUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
/**
 * @author 徐子钦
 * @date 2020/5/19
 *
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleUserMapper roleUserMapper;

    /**
     * 查询所有角色
     * @param page
     * @param size
     * @param compalyid 企业id
     * @return
     */
    public PageInfo  findAll(int page,int size,String compalyid) {
        PageHelper.startPage(page,size);
        List<RoleEntiey> all = roleMapper.findAll(compalyid);
        return new PageInfo(all);
    }

    /**
     * 根据id删除角色
     * @param id  角色id
     * @return
     */
    public int delete(String id) {
        if (id==null){
          throw new SystemException(SystemErrorEnum.ROLEENTITY_ID_ISEMPTY);
        }
        return roleMapper.delete(id);
    }

    /**
     * 保存或者更新角色
     * @param roleEntiey
     */
    public void save(RoleEntiey roleEntiey) {
        if (roleEntiey==null){
            throw new SystemException(SystemErrorEnum.ROLE_NOT_FOUND);
        }
        roleEntiey.setId(UUID.randomUUID().toString());
        roleMapper.save(roleEntiey);
    }

    /**
     * 根据用户id查询角色
     * @param userid
     * @return
     */
    public List<RoleEntiey> findByUserId(String userid) {
        if (userid==null){
            throw new SystemException(SystemErrorEnum.USERENTITY_ID_ISEMPTY);
        }
        return roleMapper.findByUserId(userid);
    }

    /**
     * 对用户的角色进行更新
     * @param userid
     * @param roleIds
     */
    public void changeRole(String userid, /*String[] roleIds*/String roleIds) {

//        if (roleIds.length==0){
//            throw new SystemException(SystemErrorEnum.ROLEIDARRAY_ISNULL);
//        }
        if (userid==null){
            throw new SystemException(SystemErrorEnum.USERENTITY_ID_ISEMPTY);
        }
        //1.根据用户id删除中间表数据
        roleUserMapper.deleteUserRole(userid);

    //    2.循环所有的角色id，向中间表中添加数据
        if (roleIds==null){
             throw  new RuntimeException("数组为空");
        }
//        for (String roleId : roleIds) {
            roleUserMapper.saveUserRole(userid,roleIds);
//    }

    }

    /**
     * 更新角色
     * @param roleEntiey
     */
    public void update(RoleEntiey roleEntiey) {
        if (roleEntiey==null){
            throw new SystemException(SystemErrorEnum.ROLE_NOT_FOUND);
        }
        roleMapper.update(roleEntiey);
    }

    /**
     * 根据角色id查询角色
     * @param id
     * @return
     */
    public RoleEntiey findById(String id) {
        if (id==null){
             throw new SystemException(SystemErrorEnum.ROLEENTITY_ID_ISEMPTY);
        }
       return roleMapper.findById(id);
    }
}
