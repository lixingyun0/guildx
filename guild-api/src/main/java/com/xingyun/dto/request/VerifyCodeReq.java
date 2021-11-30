package com.xingyun.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VerifyCodeReq {

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank
    private String phone;

}
