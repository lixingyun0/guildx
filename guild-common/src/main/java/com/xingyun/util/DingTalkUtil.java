package com.xingyun.util;

import com.xingyun.util.HttpUtil;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DingTalkUtil {


    public static void sendMsgToDingTalk(String content){
        Long timestamp = System.currentTimeMillis();

        String secret = "SEC9da5437ff9f583d0c1c1a00751c512d9ca9743e4fda138696253dfedaabefe8a";
        String sign = getSign(timestamp,secret);

        //发送丁丁消息


        //https://oapi.dingtalk.com/robot/send?access_token=0c7731662b8b8f778bb2c66dcbbbc0ec1e8c12e8fa5ed901c6f0c8a836b4fbe4
        String url = "https://oapi.dingtalk.com/robot/send?access_token=0c7731662b8b8f778bb2c66dcbbbc0ec1e8c12e8fa5ed901c6f0c8a836b4fbe4" +
                "&timestamp=%s&sign=%s";

        String text = "{\n" +
                "    \"at\": {\n" +
                "        \"atMobiles\":[\n" +
                "            \"17682348063\"\n" +
                "        ],\n" +
                "        \"atUserIds\":[\n" +
                "          \n" +
                "        ],\n" +
                "        \"isAtAll\": false\n" +
                "    },\n" +
                "    \"text\": {\n" +
                "        \"content\":\"%s\"\n" +
                "    },\n" +
                "    \"msgtype\":\"text\"\n" +
                "}";

        HttpUtil.postJSON(String.format(url,timestamp,sign),String.format(text,content));
    }

    private static String getSign(long timestamp,String secret){

        String stringToSign = timestamp + "\n" + secret;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
