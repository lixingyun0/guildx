package com.xingyun.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRes {

    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("是否新注册用户（新注册用户需要跳到信息设置页）")
    private Boolean newFlag =false;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("交易密码")
    private Boolean payPasswordFlag;

    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String inviteCode;
}
