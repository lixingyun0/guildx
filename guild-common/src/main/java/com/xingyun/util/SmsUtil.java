package com.xingyun.util;


import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.xingyun.common.BusinessException;
import com.xingyun.common.RedisConstant;
import com.xingyun.enums.VerifyCodeTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class SmsUtil {

    private static final String IN_TEMPLATE = "SMS_216685724";

    private static final String apiKey = "3cb5b4239b024368ad6dd6352ebec411";

    public static void sendCode(String code, String phone, VerifyCodeTypeEnum verifyCodeType){

        String url = "http://www.beelink8.com:6665/sms/sendByJson";

        Map<String,String> data = new HashMap<>();
        data.put("apiKey",apiKey);
        data.put("mobile",phone);
        data.put("content","your register code is " + code);

        String s = HttpUtil.postJSON(url, JSON.toJSONString(data));
        System.out.println(s);

    }

    public static String getCodeRedisKey(String username){
        return RedisConstant.VERIFY_CODE+username;
    }

    public static void main(String[] args) {
        sendCode("1314","+919090275237",VerifyCodeTypeEnum.LOGIN);
        sendCode("1315","919090275237",VerifyCodeTypeEnum.LOGIN);
    }

}
