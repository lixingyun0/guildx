package com.xingyun.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ModifyPasswordReq {

    @ApiModelProperty(value = "验证码",required = true)
    @NotBlank
    private String verifyCode;

    @ApiModelProperty(value = "新密码",required = true)
    @NotBlank
    private  String newPassword;
}
