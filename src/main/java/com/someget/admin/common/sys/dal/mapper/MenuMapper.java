package com.someget.admin.common.sys.dal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.someget.admin.common.sys.dal.entity.Menu;
import com.someget.admin.common.sys.dal.entity.vo.ShowMenu;

import java.util.List;
import java.util.Map;

/**
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> showAllMenusList(Map map);

    List<Menu> getMenus(Map map);

    List<ShowMenu> selectShowMenuByUser(Map<String, Object> map);
}