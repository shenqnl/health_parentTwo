package com.itheima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder enc;

    //模拟数据库中的用户数据
    public static Map<String, com.itheima.pojo.User> map = new HashMap<String, com.itheima.pojo.User>();

    static {
        com.itheima.pojo.User user1 = new com.itheima.pojo.User();
        user1.setUsername("admin");
        //user1.setPassword("admin");
        user1.setPassword("$2a$10$u/BcsUUqZNWUxdmDhbnoeeobJy6IBsL1Gn/S0dMxI2RbSgnMKJ.4a");

        com.itheima.pojo.User user2 = new com.itheima.pojo.User();
        user2.setUsername("zhangsan");
        //user2.setPassword("123");
        user2.setPassword("$2a$10$u/BcsUUqZNWUxdmDhbnoeeobJy6IBsL1Gn/S0dMxI2RbSgnMKJ.4a");

        map.put(user1.getUsername(), user1);
        map.put(user2.getUsername(), user2);
    }

    /**
     * 提供用户的信息
     * 用户名，密码，权限集合
     * @param username 前端传过来的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("enc===================" + enc);
        // 根据用户名查询数据库
        com.itheima.pojo.User loginUser = map.get(username);
        if(null == loginUser){
            return null;
        }
        // 如果用户存在, 如果用户不存在则返回null
        //String username,
        //String password,
        //Collection<? extends GrantedAuthority> authorities 权限集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 授权
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(authority);
        authorities.add(new SimpleGrantedAuthority("add")); // 权限
        authorities.add(new SimpleGrantedAuthority("ABC")); // 权限
        // 用户对象绑定了他的权限集合 【注意】：这里的user是security包的
        //  如果密码是明文,前面补{noop}
        // 如果使用了BcryptPasswordEncoder，则可以删除{noop}
        User user = new User(loginUser.getUsername(), loginUser.getPassword(),authorities);
        return user;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 加密
        //System.out.println(encoder.encode("1234"));
        //System.out.println(encoder.encode("1234"));
        //System.out.println(encoder.encode("1234"));
        //System.out.println(encoder.encode("1234"));

        // 校验
        // p1: 明文密码，
        // p2: 加密后的密码
        System.out.println(encoder.matches("1234","$2a$10$1GTDRBEphLh1DeDS50RsLeXNfR1zYSFsVcYTeuAnQLjQ1m1d5kBsC"));
        System.out.println(encoder.matches("1234","$2a$10$LLC4uYYFDw1GOtc7zeZJYOG87ZDfVR2zPh8/CS.B2dkKLAnaAsixi"));
        System.out.println(encoder.matches("1234","$2a$10$JpgtfZ7oRlTuNZEzr9PzleM4Dha1f4jsAwTHq4xqO2046PKJ9As3K"));
        System.out.println(encoder.matches("1234","$2a$10$cuTktBERxuVKR8XS83WqT.BmQumK0c9O87KNLgHKgW2JeIxUyAmlK"));
        System.out.println(encoder.matches("1234","$2a$10$u/BcsUUqZNWUxdmDhbnoeeobJy6IBsL1Gn/S0dMxI2RbSgnMKJ.4a"));
    }
}
