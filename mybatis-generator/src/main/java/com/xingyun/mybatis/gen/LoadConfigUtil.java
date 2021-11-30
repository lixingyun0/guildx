package com.xingyun.mybatis.gen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class LoadConfigUtil {


    static Properties loadConfig(String configFileName){
        InputStream resourceAsStream = LoadConfigUtil.class.getClassLoader().getResourceAsStream(configFileName);
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
