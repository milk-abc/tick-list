package com.water76016.ourtask.service;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @program: our-task
 * @description: redis操作服务类
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
public interface RedisService {

    /**
     * 保存属性
     *
     * @param key   the key
     * @param value the value
     * @param time  the time
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 获取属性
     *
     * @param key the key
     * @return the object
     */
    Object get(String key);

    /**
     * 删除属性
     *
     * @param key the key
     * @return the boolean
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     *
     * @param keys the keys
     * @return the long
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     *
     * @param key  the key
     * @param time the time
     * @return the boolean
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     *
     * @param key the key
     * @return the expire
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     *
     * @param key the key
     * @return the boolean
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     *
     * @param key   the key
     * @param delta the delta
     * @return the long
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     *
     * @param key   the key
     * @param delta the delta
     * @return the long
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the object
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key     the key
     * @param hashKey the hash key
     * @param value   the value
     * @param time    the time
     * @return the boolean
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key     the key
     * @param hashKey the hash key
     * @param value   the value
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     *
     * @param key the key
     * @return the map
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     *
     * @param key  the key
     * @param map  the map
     * @param time the time
     * @return the boolean
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     *
     * @param key the key
     * @param map the map
     */
    void hSetAll(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     *
     * @param key     the key
     * @param hashKey the hash key
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the boolean
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     *
     * @param key     the key
     * @param hashKey the hash key
     * @param delta   the delta
     * @return the long
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     *
     * @param key     the key
     * @param hashKey the hash key
     * @param delta   the delta
     * @return the long
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     *
     * @param key the key
     * @return the set
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     *
     * @param key    the key
     * @param values the values
     * @return the long
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     *
     * @param key    the key
     * @param time   the time
     * @param values the values
     * @return the long
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     *
     * @param key the key
     * @return the long
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     *
     * @param key    the key
     * @param values the values
     * @return the long
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     *
     * @param key the key
     * @return the long
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     *
     * @param key   the key
     * @param index the index
     * @return the object
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     *
     * @param key   the key
     * @param value the value
     * @param time  the time
     * @return the long
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     *
     * @param key    the key
     * @param values the values
     * @return the long
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     *
     * @param key    the key
     * @param time   the time
     * @param values the values
     * @return the long
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     *
     * @param key   the key
     * @param count the count
     * @param value the value
     * @return the long
     */
    Long lRemove(String key, long count, Object value);
}