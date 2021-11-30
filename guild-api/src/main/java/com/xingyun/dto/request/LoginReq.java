package com.xingyun.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginReq {

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank
    private String password;

}
