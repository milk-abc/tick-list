package com.water76016.ourtask.controller;

import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.TreeMap;

/**
 * @program: our-task
 * @description: 统计接口
 * @author: water76016
 * @create: 2021-09-04 13:00
 **/
@Api(value = "统计接口", tags = {"统计操作"})
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    @ApiOperation("获取最近一周分类和标签的创建情况")
    @GetMapping("/countCategoryAndLabelForDay/{userId}")
    public RestResult countCategoryAndLabelForDay(@PathVariable("userId") @ApiParam("用户id") Integer userId){
        if (userId == null){
            return RestResult.errorParams("用户id不能为空");
        }
        List<TreeMap<String, String>> result = statisticsService.countCategoryAndLabelForDay(userId);
        return RestResult.success(result);
    }
}
