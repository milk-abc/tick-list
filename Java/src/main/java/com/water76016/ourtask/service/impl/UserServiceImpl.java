package com.water76016.ourtask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.common.RestResult;
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

    /**
     * 获取用户头像的URL
     *
     * @param id
     * @return
     */
    @Override
    public RestResult getHeadPortrait(Integer id) {
        //先检查该用户id是否存在
        User user = getById(id);
        if (user == null){
            return RestResult.error("该用户不存在");
        }
        //用户的头像字段里面存储头像的URL
        String url = user.getHeadPortrait();
        return RestResult.success(url);
    }

    /**
     * 更新用户头像的URL
     *
     * @param id
     * @param url
     * @return
     */
    @Override
    public boolean updateImageUrl(Integer id, String url) {
        User user = User.builder().id(id).headPortrait(url).build();
        boolean flag = updateById(user);
        return flag;
    }
}
