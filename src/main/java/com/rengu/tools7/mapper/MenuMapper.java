package com.rengu.tools7.mapper;

import com.rengu.tools7.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zeng
 * @date 2020/2/24 23:14
 */

@Component
@Mapper
public interface MenuMapper {

    List<Menu> findAllMenusWithRoles();


}
