package com.itheima.security;

import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 认证与授权的
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名查询用户信息(角色，权限查出来)
        com.itheima.pojo.User loginUser = userService.findByUsername(username);
        // 如果没有找用户 则返回null
        if(null == loginUser){
            return null;
        }
        // 找用户，授权
        // 获取用户的角色列表
        Set<Role> roles = loginUser.getRoles();
        // 登陆用户的权限集合
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roles)) {
            // 遍历角色，
            for (Role role : roles) {
                // 授予角色
                authorityList.add(new SimpleGrantedAuthority(role.getKeyword()));
                // 获取角色下的权限
                Set<Permission> permissions = role.getPermissions();
                // 遍历角色下的权限，授予权限
                if(!CollectionUtils.isEmpty(permissions)){
                    for (Permission permission : permissions) {
                        authorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }
        // 返回User(security)
        User user = new User(username,loginUser.getPassword(), authorityList);
        return user;
    }
}
