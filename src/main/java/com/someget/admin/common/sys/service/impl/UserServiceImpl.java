package com.someget.admin.common.sys.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.someget.admin.common.realm.AuthRealm;
import com.someget.admin.common.sys.dal.entity.Role;
import com.someget.admin.common.sys.dal.entity.User;
import com.someget.admin.common.sys.dal.mapper.UserMapper;
import com.someget.admin.common.sys.service.UserService;
import com.someget.admin.common.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Service("userService")
@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final ConcurrentHashMap<Long, User> userMap = new ConcurrentHashMap<>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /* 这里caching不能添加put 因为添加了总会执行该方法
     * @see com.mysiteforme.service.UserService#findUserByLoginName(java.lang.String)
     */
    @Cacheable(value = "user", key = "'user_name_'+#name",unless = "#result == null")
    @Override
    public User findUserByLoginName(String name) {
        //  Auto-generated method stub
        Map<String,Object> map = Maps.newHashMap();
        map.put("loginName", name);
        User u = baseMapper.selectUserByMap(map);
        return u;
    }

    /* 这里caching不能添加put 因为添加了总会执行该方法
     * @see com.mysiteforme.service.UserService#findUserByLoginName(java.lang.String)
     */
    @Cacheable(value = "user", key = "'user_name_base_'+#name",unless = "#result == null")
    @Override
    public User findBaseUserByLoginName(String name) {
        //  Auto-generated method stub
        Map<String,Object> map = Maps.newHashMap();
        map.put("loginName", name);
        User u = baseMapper.selectBaseUserByMap(map);
        return u;
    }

    @Override
    public User findUserBymobile(String mobile) {
        Map<String,Object>map = new HashMap<>();
        map.put("phone",mobile);
        List<User> list = baseMapper.selectByMap(map);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public User findUserByNickName(String nickName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nick_name",nickName);
        List<User> list = baseMapper.selectList(wrapper);
//		Map<String,Object> map = new HashMap<>();
//		map.put("nickName",nickName);
//		List<User> list = baseMapper.selectByMap(map);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Cacheable(value = "user", key="'user_id_'+T(String).valueOf(#id)",unless = "#result == null")
    @Override
    public User findUserById(Long id) {
        //  Auto-generated method stub
        User result = userMap.get(id);
        if (result != null) {
            return result;
        }
        // 没查到再查库
        Map<String,Object> map = Maps.newHashMap();
        map.put("id", id);
        result = baseMapper.selectUserByMap(map);
        if (result != null) {
            userMap.put(id, result);
        }
        return result;
    }

    @Override
    @Caching(put = {
            @CachePut(value = "user", key = "'user_id_'+T(String).valueOf(#result.id)",condition = "#result.id != null and #result.id != 0"),
            @CachePut(value = "user", key = "'user_name_'+#user.loginName", condition = "#user.loginName !=null and #user.loginName != ''"),
            @CachePut(value = "user", key = "'user_name_base_'+#user.loginName", condition = "#user.loginName !=null and #user.loginName != ''"),
            @CachePut(value = "user", key = "'user_email_'+#user.email", condition = "#user.email != null and #user.email != ''"),
            @CachePut(value = "user", key = "'user_phone_'+#user.phone", condition = "#user.phone != null and #user.phone != ''")
    })
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public User saveUser(User user) {
        ToolUtil.entryptPassword(user);
        user.setLocked(false);
        baseMapper.insert(user);
        //保存用户角色关系
        if(user.getRoleLists()!=null && !user.getRoleLists().isEmpty()){
            this.saveUserRoles(user.getId(),user.getRoleLists());
        }
        return findUserById(user.getId());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "user", key = "'user_id_'+T(String).valueOf(#user.id)",condition = "#user.id != null and #user.id != 0"),
            @CacheEvict(value = "user", key = "'user_name_'+#user.loginName", condition = "#user.loginName !=null and #user.loginName != ''"),
            @CacheEvict(value = "user", key = "'user_name_base_'+#user.loginName", condition = "#user.loginName !=null and #user.loginName != ''"),
            @CacheEvict(value = "user", key = "'user_email_'+#user.email", condition = "#user.email != null and #user.email != ''"),
            @CacheEvict(value = "user", key = "'user_phone_'+#user.phone", condition = "#user.phone != null and #user.phone != ''" ),
    })
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public User updateUser(User user) {
        baseMapper.updateById(user);
        userMap.remove(user.getId());
        redisTemplate.delete(String.format("allMenus::user_menu_%d", user.getId()));
        if(user.getRoleLists()!=null && !user.getRoleLists().isEmpty()){
            //先解除用户跟角色的关系
            this.dropUserRolesByUserId(user.getId());
            this.saveUserRoles(user.getId(),user.getRoleLists());
        }
        return user;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveUserRoles(Long id, Set<Role> roleSet) {
        baseMapper.saveUserRoles(id,roleSet);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void dropUserRolesByUserId(Long id) {
        baseMapper.dropUserRolesByUserId(id);
    }

    @Override
    public int userCount(String param) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",param)
                .or().eq("email",param)
                .or().eq("phone",param)
                .last("del_flag = 0");
        int count = Convert.toInt(baseMapper.selectCount(wrapper));
        return count;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    @Caching(evict = {
            @CacheEvict(value = "user", key = "'user_id_'+T(String).valueOf(#user.id)",condition = "#user.id != null and #user.id != 0"),
            @CacheEvict(value = "user", key = "'user_name_'+#user.loginName", condition = "#user.loginName !=null and #user.loginName != ''"),
            @CacheEvict(value = "user", key = "'user_name_base_'+#user.loginName", condition = "#user.loginName !=null and #user.loginName != ''"),
            @CacheEvict(value = "user", key = "'user_email_'+#user.email", condition = "#user.email != null and #user.email != ''"),
            @CacheEvict(value = "user", key = "'user_phone_'+#user.phone", condition = "#user.phone != null and #user.phone != ''" )
    })
    public void deleteUser(User user) {
        user.setDelFlag(true);
        user.updateById();
    }

    /**
     * 查询用户拥有的每个菜单具体数量
     * @return
     */
    @Override
    public Map selectUserMenuCount() {
        return baseMapper.selectUserMenuCount();
    }

    @Override
    public List<User> pageListData(long roleId, Integer serviceId, Page<User> pageparam) {
        return baseMapper.pageListData(roleId, serviceId, pageparam);
    }

    @Override
    public User findById(Long id) {
        User user = baseMapper.selectById(id);
        return user;
    }

    @Override
    public User getCurrentUser() {
        long start = System.currentTimeMillis();
        AuthRealm.ShiroUser shiroUser = (AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        log.info("获取realm耗时{}ms", System.currentTimeMillis() - start);
        if(shiroUser == null) {
            return null;
        }
        start = System.currentTimeMillis();
        User user = findUserById(shiroUser.getId());
        log.info("获取user耗时{}ms", System.currentTimeMillis() - start);
        return user;
    }

    @Override
    public void logout() {
        userMap.remove(getCurrentUser().getId());
    }

    @Override
    public List<User> batchUserByPk(List<Long> userIds) {
        return baseMapper.selectBatchIds(userIds);
    }

}
