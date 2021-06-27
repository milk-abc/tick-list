package com.water76016.ourtask.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.entity.User;
import com.water76016.ourtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @program: our-task
 * @description: 验证用户登录服务实现接口
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这是在进行单个查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userService.getOne(queryWrapper);
        if (user == null){
            throw new UsernameNotFoundException("登录用户" + username + "不存在");
        }
        //将数据库的roles解析为UserDetails的权限集
        String roles = user.getRoles();
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
        return user;
    }
}
