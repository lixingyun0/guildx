package com.xingyun.dto.response;

import lombok.Data;

@Data
public class LoginDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 手机
     */
    private String phone;

    private String email;

    /**
     * 昵称
     */
    private String nickname;



    /**
     * 头像
     */
    private String avatar;

    private String token;

    private Boolean payPasswordFlag;
}
