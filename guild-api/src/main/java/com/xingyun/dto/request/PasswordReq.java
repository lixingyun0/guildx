package com.xingyun.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordReq {

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank
    private String password;
}
