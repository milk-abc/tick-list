package com.water76016.ourtask.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author github:water76016
 * @since 2020-07-21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "清单id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @TableLogic
    @ApiModelProperty(value = "是否被删除")
    private Integer run;

    @ApiModelProperty(value = "所含清单总数")
    @TableField(exist = false)
    Integer taskCount;
}
