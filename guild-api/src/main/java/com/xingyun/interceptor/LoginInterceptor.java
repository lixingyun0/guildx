package com.xingyun.interceptor;


import com.alibaba.fastjson.JSON;

import com.xingyun.annotation.Unimpeded;
import com.xingyun.common.RedisConstant;
import com.xingyun.common.Result;
import com.xingyun.component.LoginSession;
import com.xingyun.dto.response.LoginDTO;
import com.xingyun.enums.ResultCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class LoginInterceptor implements AsyncHandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("baseLog");

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 如果有当前注解 , 则不需要校验token
        Unimpeded unimpeded = handlerMethod.getMethodAnnotation(Unimpeded.class);

        if (unimpeded != null) {
            LoginSession.getContext().remove();
            return true;
        }
        if (request.getRequestURI().contains("/v2/api-docs")
                || request.getRequestURI().contains("swagger")) {
            return true;
        }
        // 从请求头部获取 登录 token
        String token = getToken(request);
        // 小程序
        if (StringUtils.isNotBlank(token)) {
            LoginDTO loginDto = (LoginDTO) redisTemplate.opsForValue().get(RedisConstant.USER_TOKEN + token);
//            LoginDTO loginDto = new LoginDTO();
//            loginDto.setId(10086L);
            if (loginDto == null) {
                //退出登录
                logout(response);
                return false;
            }

            // 放入ThreadLocal 中
            LoginSession.getContext().set(loginDto);
            return true;
        }

        //退出登录
        logout(response);
        return false;

    }

    private void logout(HttpServletResponse response) throws IOException {
        //gson.toJson(new DataResponse(ResponseCodeEnum.LOGIN_OUT))
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(JSON.toJSONString(Result.forFail(ResultCodeEnum.NEED_LOGIN)));
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null || token.length() == 0) {
            token = request.getParameter("token");
        }
        return token;
    }

}
