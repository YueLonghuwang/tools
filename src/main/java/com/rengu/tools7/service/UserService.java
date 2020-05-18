package com.rengu.tools7.service;

import com.rengu.tools7.entity.User;
import com.rengu.tools7.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zeng
 * @date 2020/2/24 22:42
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!");
        }
        user.setRoles(userMapper.findRolesByUserId(user.getId()));
        return user;
    }

    public User addUser(User user,Integer roleId){
        User user1=new User();
        //user1.setId(4);
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.addUser(user1);
        userMapper.insertUserRole(user1.getId(),roleId);
        return user1;
    }

    public List<User> findUserByLimit(){
        String limits="1";
        List<User> u= userMapper.findUserByLimit(limits);
        for (User u_user:u
             ) {
            if (u_user.getRoles()==null){
                return u;
            }
            if (u_user.getToken()==null){
                return u;
            }
        }
        return u;
    }

}
