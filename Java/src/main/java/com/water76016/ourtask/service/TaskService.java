package com.water76016.ourtask.service;

import com.water76016.ourtask.dto.CategoryParam;
import com.water76016.ourtask.dto.Statistics;
import com.water76016.ourtask.entity.Category;
import com.water76016.ourtask.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 清单Service
 * </p>
 *
 * @author github :water76016
 * @since 2020 -07-21
 */
public interface TaskService extends IService<Task> {
    /**
     * 根据分类id，找到所含的清单总数
     *
     * @param categoryId the category id
     * @return the integer
     */
    Integer countTask(Integer categoryId);

    /**
     * 查询某个用户，最近七天的完成情况
     *
     * @param userId the user id
     * @return the list
     */
    List<TreeMap<String, String>> countTaskForDay(Integer userId);

    /**
     * 查询某个用户，最近七周的完成情况
     *
     * @param userId the user id
     * @return the list
     */
    List<TreeMap<String, String>> countTaskForWeek(Integer userId);

    /**
     * 查询某个用户的统计情况
     *
     * @param userId the user id
     * @return the statistics
     */
    Statistics getStatistics(Integer userId);


    /**
     * 根据分类列表，查询分类传输对象CategoryParam列表
     * @param categoryList
     * @return
     */
    List<CategoryParam> getCategoryParamList(List<Map<String, Object>> categoryList);

    /**
     *查询用户在当天的每个分类清单的完成情况
     * @param userId
     * @return
     */
    List<Map<String, String>> countTodayForCategory(Integer userId);

}
