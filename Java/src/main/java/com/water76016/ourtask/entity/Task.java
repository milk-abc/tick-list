package com.water76016.ourtask.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "清单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "所属清单id")
    private Integer categoryId;

    @ApiModelProperty(value = "任务名")
    private String name;

    @ApiModelProperty(value = "任务描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "是否完成")
    private Integer run;

    @ApiModelProperty(value = "所属标签列表")
    @TableField(exist = false)
    List<Integer> labelList;

}
