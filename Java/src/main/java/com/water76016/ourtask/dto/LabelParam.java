package com.water76016.ourtask.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Label param.
 *
 * @program: our -task
 * @description: 针对label控制器所创建的传输对象
 * @author: water76016
 * @create: 2020 -09-24 16:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelParam {
    /**
     * The Id.
     */
    @ApiModelProperty("分类id")
    Integer id;

    /**
     * The Name.
     */
    @ApiModelProperty("标签名称")
    String name;

    /**
     * The Count.
     */
    @ApiModelProperty("所含清单总数")
    Integer count;

}
