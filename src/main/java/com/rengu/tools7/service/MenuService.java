package com.rengu.tools7.service;

import com.rengu.tools7.entity.Menu;
import com.rengu.tools7.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zeng
 * @date 2020/2/24 23:13
 */
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

    public List<Menu> findAllMenusWithRoles(){
        List<Menu> menus = menuMapper.findAllMenusWithRoles();
        return menus;
    };

}
