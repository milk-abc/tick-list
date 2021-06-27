package com.water76016.ourtask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water76016.ourtask.entity.TaskLabel;
import com.water76016.ourtask.mapper.TaskLabelMapper;
import com.water76016.ourtask.service.TaskLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author github:water76016
 * @since 2020-08-11
 */
@Service
public class TaskLabelServiceImpl extends ServiceImpl<TaskLabelMapper, TaskLabel> implements TaskLabelService {
    /**
     * 根据清单id，查询当前清单所属的标签id列表
     */
    @Override
    public List<Integer> getLableListByTaskId(Integer taskId){
        QueryWrapper<TaskLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_id", taskId);
        List<TaskLabel> taskLabelList = list(queryWrapper);
        List<Integer> integerList = new ArrayList<>();
        for (TaskLabel taskLabel : taskLabelList){
            integerList.add(taskLabel.getLabelId());
        }
        return integerList;
    }

    @Override
    public List<Integer> getTaskIdListByLabelIdList(List<Integer> labelIdList) {
        QueryWrapper<TaskLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("label_id", labelIdList);
        List<TaskLabel> taskLabelList = list(queryWrapper);
        List<Integer> result = new ArrayList<>();
        for (TaskLabel taskLabel : taskLabelList){
            result.add(taskLabel.getTaskId());
        }
        return result;
    }

    @Override
    public int countTaskByLabelId(int labelId) {
        QueryWrapper<TaskLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("label_id", labelId);
        int count = count(queryWrapper);
        return count;
    }

}
