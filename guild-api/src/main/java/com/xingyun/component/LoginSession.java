package com.xingyun.component;

import com.xingyun.dto.response.LoginDTO;

/**
 * @author liaowei
 * @description
 * @date 2018/5/4
 */
public class LoginSession {

    private static ThreadLocal<LoginDTO> context = new ThreadLocal<>();

    public static ThreadLocal<LoginDTO> getContext() {
        return context;
    }

    public static Long getUserId() {
        LoginDTO loginDto = context.get();
        if (loginDto == null) {
            return null;
        } else {
            return loginDto.getId();
        }
    }

    public static LoginDTO getUserInfo() {
        return context.get();
    }
}
