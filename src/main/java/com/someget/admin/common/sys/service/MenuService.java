package com.someget.admin.common.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.someget.admin.common.sys.dal.entity.Menu;
import com.someget.admin.common.sys.dal.entity.vo.ShowMenu;
import com.someget.admin.common.sys.dal.entity.vo.ZtreeVO;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
public interface MenuService extends IService<Menu> {

    List<Menu> selectAllMenus(Map<String, Object> map);

    List<ZtreeVO> showTreeMenus();

    List<ShowMenu> getShowMenuByUser(Long id);

    void saveOrUpdateMenu(Menu menu);

    int getCountByPermission(String permission);

    int getCountByName(String name);

}
