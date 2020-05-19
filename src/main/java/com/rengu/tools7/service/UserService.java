package com.rengu.tools7.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rengu.tools7.entity.RoleEntiey;
import com.rengu.tools7.entity.UserEntity;
import com.rengu.tools7.enums.SystemErrorEnum;
import com.rengu.tools7.exception.SystemException;

import com.rengu.tools7.mapper.RoleUserMapper;
import com.rengu.tools7.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Eason岳
 * @date 2020/5/7
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleUserMapper roleUserMapper ;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查询用户
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userMapper.loadUserByUsername(s);
        System.out.println(user);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!");
        }
        user.setRoles(userMapper.findRolesByUserId(user.getId()));
        return user;
    }

//    //新增用户
//    public User addUser(User user,List<Role> roles){
//        User user1=new User();
//        user1.setId(4);
//        user1.setUsername(user.getUsername());
//        user1.setPassword(passwordEncoder.encode(user.getPassword()));
//        user1.setEnabled(true);
//        user1.setLocked(false);
////        List<Role> list=new ArrayList<>();
////        list.add((Role) user.getRoles());
//        user1.setRoles(roles);
//
//        userMapper.insertUser(user);
//        //userMapper.insertUserRole(user1.getId())
//        //userMapper.
//        return user1;
//    }

    /**
     * 根据用户id和角色id进行更新用户角色
     * @param user
     * @param roleId
     */
    public void save(UserEntity user,String roleId)  {
        if (roleId ==null){
            throw new RuntimeException("roleId为空！");
        }
    if (user ==null) {
    throw new SystemException(SystemErrorEnum.USERENTITY_ISEMPTY);
    }
    user.setId(UUID.randomUUID().toString());
    user.setEnabled(true);
    user.setLocked(false);
   // RoleEntiey byId = roleMapper.findById(roleId);
    user.setRoles(new ArrayList<RoleEntiey>());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userMapper.save(user);
    roleUserMapper .saveUserRole(user.getId(),roleId);
}

    /**
     * 查询所有的用户列表
     * @param conpanyid 公司id
     * @return 用户列表
     * companyId
     */
    public PageInfo/*List<UserEntity> */findAll(int page,int size,String conpanyid) {
        PageHelper.startPage(page,size);
        List<UserEntity> all = userMapper.findAll(conpanyid);

        for (UserEntity userEntity : all) {
            userEntity.setRoles(new ArrayList<RoleEntiey>());
        }
        return new PageInfo(all);
    }

    /**
     * 根据参数修改指定用户
     * @param user User对象
     */
    public void update(UserEntity user) {
        if (user ==null){
            throw  new SystemException(SystemErrorEnum.USERENTITY_ISEMPTY);
        }
        user.setEnabled(true);
        user.setLocked(false);
        user.setCompanyId("1");
        user.setRoles(new ArrayList<RoleEntiey>());
        user.setToken("111");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.update(user);
    }

    /**
     * 根据id删除
     * @param userId
     */
    public void delete(String userId) {
        userMapper.delete(userId);
        roleUserMapper.deleteUserRole(userId);

    }
}
