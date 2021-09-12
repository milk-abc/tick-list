package com.water76016.ourtask.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: our-task
 * @description: 登录传输对象
 * @author: water76016
 * @create: 2021-09-04 17:00
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginTo implements Serializable {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "登录验证码键")
    private String securityCodeKey;

    @ApiModelProperty(value = "登录验证码值")
    private String securityCodeValue;

}
