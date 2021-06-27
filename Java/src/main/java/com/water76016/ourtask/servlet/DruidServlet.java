package com.water76016.ourtask.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @program: our-task
 * @description: druid连接池Servlet类
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
@WebServlet(urlPatterns="/druid/*",
        initParams={
                // IP白名单(没有配置或者为空，则允许所有访问)
                @WebInitParam(name="allow",value=""),
                // IP黑名单 (deny优先于allow)
                @WebInitParam(name="deny",value=""),
                // 登录druid管理页面用户名
                @WebInitParam(name="loginUsername",value="admin"),
                // 登录druid管理页面密码
                @WebInitParam(name="loginPassword",value="admin")
        })
public class DruidServlet extends StatViewServlet {

}