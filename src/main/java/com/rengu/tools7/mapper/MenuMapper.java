package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Eason岳
 * @date 2020/5/7
 */

@Component
@Mapper
public interface MenuMapper {

    List<Menu> findAllMenusWithRoles();


}
