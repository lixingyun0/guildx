package com.xingyun.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * OSS图片客户端
 */
public class OSSClientUtil {
    private static Logger logger = LoggerFactory.getLogger("baseLog");
    public static final String ENDPOINT = "http://oss-cn-hongkong.aliyuncs.com";
    public static final String ACCESSKEY_ID = "LTAI5tH53YDrRGZ1PrJu5ySN";
    public static final String ACCESSKEY_SECRET = "hKXCOrYPRHf6P7B2F8c7X1Fl0afXZc";

    public static final String URL_ENDPOINT = "oss-cn-hongkong.aliyuncs.com/";

    // 使用默认配置创建OSSClient实例，如有自定义配置需求请参考：
    //https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.673.NRFtl5
    private static final OSSClient client = new OSSClient(ENDPOINT, ACCESSKEY_ID, ACCESSKEY_SECRET);

    public static String uploadFile2OSS(String bucketName, String filePath, byte[] fileData) {
        return uploadFile2OSS(bucketName, filePath, new ByteArrayInputStream(fileData));
    }

    public static String uploadFile2OSS(String bucketName, String filePath, InputStream inputStream) {
        try {
            client.putObject(bucketName, filePath, inputStream);
        } catch (OSSException e) {
            logger.warn("OSS文件上传失败！{}", filePath, e);
            return "";
        } catch (ClientException e) {
            logger.warn("OSS文件上传失败！{}", filePath, e);
            return "";
        }
        return "https://" + bucketName + "." + URL_ENDPOINT + filePath;
    }


    public static boolean isPhotoExist(String bucketName, String fileName) {
        return client.doesObjectExist(bucketName, fileName);
    }

    public static String getPhotoUrlByFileName(String bucketName, String fileName) {
        //https://eatjoys-photo.oss-cn-hangzhou.aliyuncs.com/haha.jpg
        return "https://" + bucketName + "." + URL_ENDPOINT + fileName;
    }

    public static OSSClient getClient() {
        return client;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("/Users/xingyun/Desktop/1.jpeg"));

        String filetechdata = OSSClientUtil.uploadFile2OSS("filetechdata", "zzzz.jpeg", inputStream);

        System.out.println(filetechdata);
    }
}
