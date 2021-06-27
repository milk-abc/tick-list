package com.water76016.ourtask.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.common.constant.Common;
import com.water76016.ourtask.dto.CategoryParam;
import com.water76016.ourtask.dto.Statistics;
import com.water76016.ourtask.entity.Category;
import com.water76016.ourtask.entity.Task;
import com.water76016.ourtask.mapper.TaskMapper;
import com.water76016.ourtask.service.CategoryService;
import com.water76016.ourtask.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author github:water76016
 * @since 2020-07-21
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Autowired
    CategoryService categoryService;

    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.key.task}")
    private String redisKeyTask;
    @Value("${redis.key.label}")
    private String redisKeyLabel;
    @Value("${redis.expire.common}")
    private long expire;

    @Override
    public Integer countTask(Integer categoryId) {
        QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
        taskQueryWrapper.eq("category_id", categoryId);
        taskQueryWrapper.eq("run", 1);
        Integer countTask = count(taskQueryWrapper);
        return countTask;
    }

    @Override
    public List<TreeMap<String, String>> countTaskForDay(Integer userId) {
        List<TreeMap<String, String>> result = new ArrayList<>();
        //获取当前时间
        String localDate = LocalDate.now().toString();
        Date date = DateUtil.parse(localDate);
        for (int i = Common.V_CHARTS_NUMBER; i >= 0; i--){
            QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
            taskQueryWrapper.eq("user_id", userId);
            DateTime currentDay = DateUtil.offsetDay(date, -i);
            //一天的开始
            Date beginOfDay = DateUtil.beginOfDay(currentDay);
            //一天的结束
            Date endOfDay = DateUtil.endOfDay(currentDay);
            taskQueryWrapper.between("update_time", beginOfDay, endOfDay);
            taskQueryWrapper.ge("update_time", beginOfDay);
            Integer total = count(taskQueryWrapper);
            taskQueryWrapper.eq("run", 0);
            Integer count = count(taskQueryWrapper);
            String format = DateUtil.format(currentDay, "yyyy-MM-dd");
            if (i == 0){
                format = "今天";
            }
            TreeMap<String, String> treeMap = new TreeMap<>();
            treeMap.put("date", format);
            treeMap.put("day", String.valueOf(count));
            treeMap.put("total", String.valueOf(total));
            result.add(treeMap);
        }
        return result;
    }

    @Override
    public List<TreeMap<String, String>> countTaskForWeek(Integer userId) {
        List<TreeMap<String, String>> result = new ArrayList<>();
        //获取当前时间
        String localDate = LocalDate.now().toString();
        Date date = DateUtil.parse(localDate);
        Date beginWeek = DateUtil.beginOfWeek(date);
        for (int i = Common.V_CHARTS_NUMBER; i >= 0; i--){
            QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
            taskQueryWrapper.eq("user_id", userId);
            taskQueryWrapper.eq("run", 0);
            //一周的开始
            Date beginOfWeek = DateUtil.offsetWeek(beginWeek, -i);
            //一周的结束
            Date endOfWeek = DateUtil.offsetWeek(beginWeek, -i + 1);
            taskQueryWrapper.ge("update_time", beginOfWeek);
            taskQueryWrapper.lt("update_time", endOfWeek);
            Integer count = count(taskQueryWrapper);
            String format = DateUtil.format(beginOfWeek, "yyyy-MM-dd");
            if (i == 0){
                format = "本周";
            }
            TreeMap<String, String> treeMap = new TreeMap<>();
            treeMap.put("date", format);
            treeMap.put("week", String.valueOf(count));
            result.add(treeMap);
        }
        return result;
    }

    @Override
    public Statistics getStatistics(Integer userId) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("run", 0);
        //统计已完成清单
        Integer totalFinished = count(queryWrapper);
        //统计待完成清单
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("run", 1);
        Integer unFinished = count(queryWrapper);
        //统计上周完成清单
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("run", 0);
        DateTime lastWeek = DateUtil.lastWeek();
        queryWrapper.ge("update_time", lastWeek);
        Integer weekFinished = count(queryWrapper);
        //统计上个月完成清单
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("run", 0);
        DateTime lastMonth = DateUtil.lastMonth();
        queryWrapper.ge("update_time", lastMonth);
        Integer monthFinished = count(queryWrapper);

        return new Statistics(totalFinished, unFinished, weekFinished, monthFinished);
    }

    @Override
    public List<CategoryParam> getCategoryParamList(List<Map<String, Object>> categoryList) {
        List<CategoryParam> categoryParamList = new ArrayList<>();
        for(Map<String, Object> category : categoryList){
            Integer categoryId = Integer.valueOf(category.get("id").toString());
            QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
            taskQueryWrapper.eq("category_id", categoryId);
            Integer countTask = count(taskQueryWrapper);
            CategoryParam categoryParam = new CategoryParam(Integer.valueOf(category.get("id").toString()),
                    category.get("name").toString(), countTask);
            categoryParamList.add(categoryParam);
        }
        return categoryParamList;
    }

    @Override
    public List<Map<String, String>> countTodayForCategory(Integer userId) {
        List<Map<String, String>> result = new ArrayList<>();
        //先查询该用户下，所有的分类
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        Category entity = Category.builder().userId(userId).build();
        categoryQueryWrapper.setEntity(entity);
        List<Category> categoryList = categoryService.list(categoryQueryWrapper);
        //获取当前时间
        Date currentDate = DateUtil.date();
        Date startDate = DateUtil.beginOfDay(currentDate);
        //判断图表中，是否存在数据
        boolean flag = false;
        for (Category category : categoryList){
            QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
            taskQueryWrapper.eq("user_id", userId).eq("category_id", category.getId())
                    .eq("run", 0);
            taskQueryWrapper.ge("update_time", startDate).le("update_time", currentDate);
            Integer count = count(taskQueryWrapper);
            if (count > 0){
                flag = true;
            }
            Map<String, String> hashMap = new HashMap<>(10);
            hashMap.put("categoryName", category.getName());
            hashMap.put("count", String.valueOf(count));
            result.add(hashMap);
        }
        if (flag == false){
            return null;
        }
        return result;
    }
}
