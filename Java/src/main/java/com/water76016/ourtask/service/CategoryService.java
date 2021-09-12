package com.water76016.ourtask.service;

import com.water76016.ourtask.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author github:water76016
 * @since 2020-07-21
 */
public interface CategoryService extends IService<Category> {
    /**
     * 根据userID和分类id，删除一个分类
     * @param id
     * @param userId
     * @return
     */
    boolean removeById(Integer id, Integer userId);

    /**
     * 根据userId,返回所有清单的对象map列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> list(Integer userId);
}
