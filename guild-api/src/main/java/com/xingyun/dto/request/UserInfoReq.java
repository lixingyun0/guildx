package com.xingyun.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoReq {

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("昵称")
    private String nickname;
}
