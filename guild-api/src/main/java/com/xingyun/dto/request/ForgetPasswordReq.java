package com.xingyun.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ForgetPasswordReq {

    @ApiModelProperty(value = "验证码",required = true)
    @NotBlank
    private String verifyCode;

    @ApiModelProperty(value = "新密码",required = true)
    @NotBlank
    private String newPassword;

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank
    private String phone;
}
