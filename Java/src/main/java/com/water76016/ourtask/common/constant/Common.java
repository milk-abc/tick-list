package com.water76016.ourtask.common.constant;


import java.util.Arrays;
import java.util.List;

/**
 * @program: our-task
 * @description: 常量类
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
public class Common {
    /**
     *记录图表v-charts的每张图记录数,从0开始，意味着每张图7个坐标点
     * */
    public static final Integer V_CHARTS_NUMBER = 6;

    /**
     * 记录允许上传的文件类型
     */
    public static final List<String> ALLOW_PICTURE_TYPE = Arrays.asList(".jpg", ".jpeg", ".png");

    /**
     * Redis里面验证码的键
     */
    public static final String SECURITY_CODE = "securityCode:";

}
