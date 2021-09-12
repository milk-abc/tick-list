package com.water76016.ourtask.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Select condition.
 *
 * @program: our -task
 * @description: 对清单列表进行查询的传输对象
 * @author: water76016
 * @create: 2020 -09-24 16:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectCondition {

    /**
     * The Name.
     */
    @ApiModelProperty("查询名称")
    String name;

    /**
     * The Category id.
     */
    @ApiModelProperty("查询分类id")
    Integer categoryId;

    /**
     * The Label id list.
     */
    @ApiModelProperty("查询标签id列表")
    List<Integer> labelIdList;
}
