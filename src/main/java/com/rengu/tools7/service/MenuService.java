package com.rengu.tools7.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rengu.tools7.entity.Menu;
import com.rengu.tools7.entity.RoleEntiey;
import com.rengu.tools7.enums.SystemErrorEnum;
import com.rengu.tools7.exception.SystemException;
import com.rengu.tools7.mapper.MenuMapper;
import com.rengu.tools7.mapper.MenuRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eason岳
 * @date 2020/5/7
 */
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;
    public PageInfo findAllMenusWithRoles(int page,int size){
        PageHelper.startPage(page,size);
        List<Menu> menus = menuMapper.findAllMenusWithRoles();
        return new PageInfo(menus);
    }

    public void upadateMenu(String roleId, String[] menuIds) {
        if (roleId==null){
            throw  new SystemException(SystemErrorEnum.ROLE_NOT_FOUND);
        }

        menuRoleMapper.deleteByRoleId(roleId);
        for (String menuId : menuIds) {
            if (menuId==null){
                throw  new SystemException(SystemErrorEnum.MENUID_IS_NULL);
            }
            menuRoleMapper.save(menuId,roleId);
        }

    }

    public List<Menu> findMenuByRole(String roleId) {
        List<Menu> menuByRole = menuRoleMapper.findMenuByRole(roleId);
        for (Menu menu : menuByRole) {
            menu.setRoles(new ArrayList<RoleEntiey>());
        }
        return menuByRole;
    }

    public void addMenu(String roleId, String menuId) {
        menuRoleMapper.save(menuId,roleId);
    }
}
