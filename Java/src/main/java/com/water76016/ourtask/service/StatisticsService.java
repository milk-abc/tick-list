package com.water76016.ourtask.service;

import java.util.List;
import java.util.TreeMap;

/**
 * @program: our-task
 * @description: 统计服务类
 * @author: water76016
 * @create: 2021-09-04 13:38
 **/
public interface StatisticsService {
    /**
     * 查询某个用户，最近七天分类和标签的创建情况
     *
     * @param userId the user id
     * @return the list
     */
    List<TreeMap<String, String>> countCategoryAndLabelForDay(Integer userId);
}
