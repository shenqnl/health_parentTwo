package com.itheima.dao;

import com.itheima.pojo.User;
/**
 * @author shenhuamin
 * @date 2022/9/1
 */
public interface UserDao {
    /**
     * 通过用户名查询用户信息(角色，权限查出来)
     * @param username
     * @return
     */
    User findByUsername(String username);
}
