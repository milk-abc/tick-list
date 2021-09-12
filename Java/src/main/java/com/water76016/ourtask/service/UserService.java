package com.water76016.ourtask.service;

import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author github:water76016
 * @since 2020-07-21
 */
public interface UserService extends IService<User> {
    /**
     * 获取用户头像的URL
     * @param id
     * @return
     */
    RestResult getHeadPortrait(Integer id);

    /**
     * 更新用户头像的URL
     * @param id
     * @param url
     * @return
     */
    boolean updateImageUrl(Integer id, String url);
}
