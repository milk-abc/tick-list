package com.water76016.ourtask.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author github:water76016
 * @since 2020-08-11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "清单标签id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "清单id")
    private Integer taskId;

    @ApiModelProperty(value = "标签id")
    private Integer labelId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
