package com.water76016.ourtask.service.impl;

import com.water76016.ourtask.entity.User;
import com.water76016.ourtask.mapper.UserMapper;
import com.water76016.ourtask.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author github:water76016
 * @since 2020-07-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
