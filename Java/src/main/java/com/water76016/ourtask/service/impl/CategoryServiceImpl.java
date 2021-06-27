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
    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.key.category}")
    private String redisKeyCategory;
    @Value("${redis.expire.common}")
    private long expire;

    @Autowired
    RedisService redisService;

    @Autowired
    TaskService taskService;

    /**
     * 用来获取当前用户下所有的分类
     * */
    @Override
    public List<Map<String, Object>> list(Integer userId) {
        String categoryIdSetKey = redisDatabase + ":" + redisKeyCategory + ":set:" + userId;
        List<Map<String, Object>> result = new ArrayList<>();
        if (redisService.hasKey(categoryIdSetKey)){
            //先获取该用户下所有的清单id
            Set<Object> categoryIdSet = redisService.sMembers(categoryIdSetKey);
            String originalkey = redisDatabase + ":" + redisKeyCategory + ":hash:";
            for (Object id : categoryIdSet){
                String categoryKey = originalkey + id;
                Map<Object, Object> tempCateogry = redisService.hGetAll(categoryKey);
                Map<String, Object> category = new HashMap<>(10);
                for (Map.Entry<Object, Object> entrySet : tempCateogry.entrySet()){
                    category.put(entrySet.getKey().toString(), entrySet.getValue());
                }
                result.add(category);
            }
            return result;
        }
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        result = listMaps(queryWrapper);
        //把从MySQL中取出来的值，存入到redis里面
        for (Map<String, Object> category : result){
            saveRedis(category);
        }
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
        saveOrUpdateForRedis(entity);
        //把这个分类的id，添加到redis的用户分类id集合里面
        String categoryIdSetKey = redisDatabase + ":" + redisKeyCategory + ":set:" + entity.getUserId();
        redisService.sAdd(categoryIdSetKey, entity.getId().toString());
        redisService.expire(categoryIdSetKey, expire);
        //把这个分类下的清单数置为0
        String taskCountKey = redisDatabase + ":" + redisKeyCategory + ":string:" + entity.getId();
        redisService.incr(taskCountKey, 1);
        redisService.expire(taskCountKey, expire);
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
        saveOrUpdateForRedis(entity);
        return flag;
    }

    /**
     * 针对redis里面存储的category，进行添加或者更新
     * @param entity
     */
    public void saveOrUpdateForRedis(Category entity){
        String categoryKey = redisDatabase + ":" + redisKeyCategory + ":hash:" + entity.getId();
        redisService.hSet(categoryKey, "id", entity.getId().toString());
        redisService.hSet(categoryKey, "userId", entity.getUserId().toString());
        redisService.hSet(categoryKey, "name", entity.getName());
        redisService.hSet(categoryKey, "createTime", entity.getCreateTime().toString());
        redisService.hSet(categoryKey, "updateTime", entity.getUpdateTime().toString());
        redisService.expire(categoryKey, expire);
    }

    public void saveRedis(Map<String, Object> category){
        String categoryKey = redisDatabase + ":" + redisKeyCategory + ":hash:" + category.get("id");
        String taskCountKey = redisDatabase + ":" + redisKeyCategory + ":string:" + category.get("id");
        String categoryIdSetKey = redisDatabase + ":" + redisKeyCategory + ":set:" + category.get("user_id");
        for (Map.Entry<String, Object> entry : category.entrySet()){
            redisService.hSet(categoryKey, entry.getKey(), entry.getValue().toString());
        }
        //把每个分类下清单的数量也存到redis中去
        Integer categoryId = Integer.valueOf(category.get("id").toString());
        Integer countTask = taskService.countTask(categoryId);
        redisService.set(taskCountKey, countTask.toString());
        //把该用户的分类id集合也更新一下
        redisService.sAdd(categoryIdSetKey, category.get("id").toString());
        //设置键的过期时间
        redisService.expire(categoryKey, expire);
        redisService.expire(taskCountKey, expire);
        redisService.expire(categoryIdSetKey, expire);
    }

    @Override
    public boolean removeById(Integer id, Integer userId) {
        String categoryKey = redisDatabase + ":" + redisKeyCategory + ":hash:" + id;
        String categoryIdSetKey = redisDatabase + ":" + redisKeyCategory + ":set:" + userId;
        String taskCountKey = redisDatabase + ":" + redisKeyCategory + ":string:" + id;
        removeById(id);
        redisService.del(taskCountKey);
        redisService.sRemove(categoryIdSetKey, id.toString());
        redisService.del(categoryKey);
        return true;
    }
}
