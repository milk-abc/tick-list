package com.water76016.ourtask.service;

import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.entity.Tourist;

import javax.servlet.http.HttpServletResponse;


/**
 * @program: our-task
 * @description: 游客操作服务接口
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
public interface TouristService {
    /**
     * 游客进行注册操作
     *
     * @param tourist the tourist
     * @return the rest result
     */
    RestResult register(Tourist tourist);

    /**
     * 游客进行登录操作
     *
     * @param tourist  the tourist
     * @param response the response
     * @return the rest result
     */
    RestResult login(Tourist tourist, HttpServletResponse response);
}
