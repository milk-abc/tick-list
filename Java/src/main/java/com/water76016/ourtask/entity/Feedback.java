package com.water76016.ourtask.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author github:water76016
 * @since 2020-09-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "反馈id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "所属用户id")
    private Integer userId;

    @ApiModelProperty(value = "反馈标题")
    private String title;

    @ApiModelProperty(value = "反馈描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
