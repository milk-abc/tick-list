package com.water76016.ourtask.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.common.constant.Common;
import com.water76016.ourtask.entity.Category;
import com.water76016.ourtask.entity.Label;
import com.water76016.ourtask.service.CategoryService;
import com.water76016.ourtask.service.LabelService;
import com.water76016.ourtask.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * @program: our-task
 * @description: 统计服务实现类
 * @author: water76016
 * @create: 2021-09-04 13:39
 **/
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    LabelService labelService;


    /**
     * 查询某个用户，最近七天分类和标签的创建情况
     *
     * @param userId the user id
     * @return the list
     */
    @Override
    public List<TreeMap<String, String>> countCategoryAndLabelForDay(Integer userId) {
        List<TreeMap<String, String>> result = new ArrayList<>();
        //获取当前时间
        String localDate = LocalDate.now().toString();
        Date date = DateUtil.parse(localDate);
        for (int i = Common.V_CHARTS_NUMBER; i >= 0; i--){
            DateTime currentDay = DateUtil.offsetDay(date, -i);
            //一天的开始
            Date beginOfDay = DateUtil.beginOfDay(currentDay);
            //一天的结束
            Date endOfDay = DateUtil.endOfDay(currentDay);

            QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
            categoryQueryWrapper.eq("user_id", userId);
            categoryQueryWrapper.eq("run", 1);
            categoryQueryWrapper.between("create_time", beginOfDay, endOfDay);
            Integer categoryDay = categoryService.count(categoryQueryWrapper);

            QueryWrapper<Label> labelQueryWrapper = new QueryWrapper<>();
            labelQueryWrapper.eq("user_id", userId);
            labelQueryWrapper.eq("run", 1);
            labelQueryWrapper.between("create_time", beginOfDay, endOfDay);
            Integer labelDay = labelService.count(labelQueryWrapper);
            String format = DateUtil.format(currentDay, "yyyy-MM-dd");
            if (i == 0){
                format = "今天";
            }
            TreeMap<String, String> treeMap = new TreeMap<>();
            treeMap.put("date", format);
            treeMap.put("categoryDay", String.valueOf(categoryDay));
            treeMap.put("labelDay", String.valueOf(labelDay));
            result.add(treeMap);
        }
        return result;
    }
}
