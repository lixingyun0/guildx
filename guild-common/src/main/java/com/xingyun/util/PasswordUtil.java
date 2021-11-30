package com.xingyun.util;

import cn.hutool.crypto.digest.DigestUtil;

import java.util.Date;

public class PasswordUtil {

    public static String digest(String password,String seed){
        String digestString = DigestUtil.md5Hex(password)+"|"+ seed;

        return DigestUtil.md5Hex(digestString);
    }

    public static void main(String[] args) {
        //String digest = digest("1234567890", "13f8e4699d494dc5a5319f23aad5509e");
        //System.out.println(digest);
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date().getTime());
    }
}
