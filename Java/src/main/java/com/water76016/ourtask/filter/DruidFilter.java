package com.water76016.ourtask.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @program: our-task
 * @description: druid连接池过滤器
 * @author: water76016
 * @create: 2020-11-26 19:29
 **/
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
        initParams={
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg, *.bmp, *.png,*.css,*.ico,/druid/*")//忽略资源
        }
)
public class DruidFilter extends WebStatFilter {
}