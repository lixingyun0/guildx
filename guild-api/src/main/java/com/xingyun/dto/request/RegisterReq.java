package com.xingyun.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterReq {

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "短信验证码",required = true)
    @NotBlank
    private String verificationCode;

    @ApiModelProperty(value = "图形验证码",required = true)
    @NotBlank
    private String captcha;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank
    private String password;
    /**
     * 邀请码
     */
    @ApiModelProperty(value = "邀请码",required = false)
    private String inviteCode;
}
