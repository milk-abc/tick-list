package com.water76016.ourtask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.entity.Category;
import com.water76016.ourtask.mapper.CategoryMapper;
import com.water76016.ourtask.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.water76016.ourtask.service.RedisService;
import com.water76016.ourtask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    TaskService taskService;

    /**
     * 用来获取当前用户下所有的分类
     * */
    @Override
    public List<Map<String, Object>> list(Integer userId) {
        List<Map<String, Object>> result = new ArrayList<>();
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        result = listMaps(queryWrapper);
        return result;
    }

    /**
     * 用来添加一个新的分类
     * */
    @Override
    public boolean save(Category entity) {
        //添加一个实体，会自动把数据库中的id传值回来
        boolean flag = super.save(entity);
        if (flag == false){
            return flag;
        }
        return flag;
    }

    /**
     * 用来对一个分类进行更新
     * */
    @Override
    public boolean updateById(Category entity) {
        boolean flag = super.updateById(entity);
        if (flag == false){
            return false;
        }
        return flag;
    }

    @Override
    public boolean removeById(Integer id, Integer userId) {
        removeById(id);
        return true;
    }
}
