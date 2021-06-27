package com.water76016.ourtask.common;

/**
 * @program: our-task
 * @description: 封装API的错误码
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
public interface IErrorCode {
    /**
     * 得到状态码
     * @return:状态码
     */
    int getCode();

    /**
     * 获取返回消息
     * @return:message返回消息
     */
    String getMessage();
}
