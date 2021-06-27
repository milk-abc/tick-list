package com.water76016.ourtask.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author github:water76016
 * @since 2020-08-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签id")
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "标签名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @ApiModelProperty(value = "是否还在使用")
    private Integer run;

    @ApiModelProperty(value = "所含清单总数")
    @TableField(exist = false)
    Integer taskCount;
}
