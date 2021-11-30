package com.xingyun.util;

import com.alibaba.fastjson.JSONObject;
import com.xingyun.common.BusinessException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;

/**
 * @author 最爱吃小鱼
 */
public class HttpUtil {

    private static  OkHttpClient CLIENT;

    private static final Logger log = LoggerFactory.getLogger("baseLog");
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TEXT = MediaType.parse("text/html; charset=UTF-8");
    private static final MediaType STREAM = MediaType.parse("application/octet-stream");

    private static Authenticator httpAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            String credential = Credentials.basic("202104122248269621", "46565901");
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        }
    };



    static {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(createSSLSocketFactory(),trustManager());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        CLIENT = builder.build();

    }

    public static OkHttpClient getCLIENT() {
        return CLIENT;
    }


    private static X509TrustManager trustManager(){
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            trustManagerFactory.init((KeyStore) null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        return trustManager;
    }



    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }


    /**
     * 发起连接请求，post json内容
     *
     * @param url
     * @param params
     * @return
     */
    public static String postJSON(String url, Object params) {
        return postJSON(url, JSONObject.toJSONString(params), null);
    }

    public static String postJSON(String url, Object params, Map<String, String> header) {
        return postJSON(url, JSONObject.toJSONString(params), header);
    }

    public static String postJSON(String url, String content) {
        return postJSON(url, content, null);
    }


    /**
     * 发起 post JSON格式请求
     * @param url
     * @param content JSON 格式内容
     * @return
     */
    public static String postJSON(String url, String content, Map<String, String> header) {
        return post(url, content, JSON, header);
    }


    /**
     * 发起 post JSON格式请求
     * @param url
     * @param content JSON 格式内容
     * @return
     */
    public static String postJSON(String url, String content, Map<String, String> header,Map<String,String> proxy) {
        return post(url, content, JSON, header);
    }

    public static String postText(String url, String content, Map<String, String> header) {
        return post(url, content, TEXT, header);
    }

    /**
     * 发起 post 请求
     */
    public static String post(String url, String content, MediaType mediaType, Map<String, String> header) {
        log.info("连接URL={}", url);

        try {

            RequestBody requestBody = RequestBody.create(mediaType, content);

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .post(requestBody);
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            Request request = requestBuilder.build();

            Response response = CLIENT.newCall(request).execute();

            String bodyStr = response.body().string();
            log.info("============================================");
            if (header != null) {
                log.info("请求Header:{}", JSONObject.toJSONString(header));
            }
            log.info("请求JSON:{}", content);
            log.info("返回JSON:{}", bodyStr);
            log.info("============================================");

            return bodyStr;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("调用API[url=%s]出错, %s", url, e.getMessage());
        }
    }

    /**
     * 发起连接请求，post json内容，返回文件流
     *
     * @param url
     * @param params
     * @return
     */
    public static InputStream getInputStreamForPostJSON(String url, Object params) {

        String content = JSONObject.toJSONString(params);
        log.info("连接URL={}", url);
        try {

            RequestBody requestBody = RequestBody.create(JSON, content);

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = CLIENT.newCall(request).execute();

            log.info("============================================");
            log.info("请求JSON:{}", content);
            log.info("============================================");

            InputStream byteStream = response.body().byteStream();

            // 转换成OSS可识别的InputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int len;
            byte[] buf = new byte[1024];
            while ((len = byteStream.read(buf, 0, 1024)) != -1) {
                byteArrayOutputStream.write(buf, 0, len);
            }
            byteArrayOutputStream.flush();
            buf = byteArrayOutputStream.toByteArray();
            String str = new String(buf);
            if (str.contains("errcode")){
                byteArrayOutputStream.close();
                return null;
            } else {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
                byteArrayOutputStream.close();
                return byteArrayInputStream;
            }
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("调用API[url=%s]出错, %s", url, e.getMessage());
        }
    }

    /**
     * 发起连接请求，post表单提交
     *
     * @param url
     * @param params
     * @return
     */
    public static String postForm(String url, Map<String, String> params) {
        log.info("连接URL={}", url);
        try {
            FormBody.Builder builder = new FormBody.Builder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
            Request request = new Request.Builder().url(url).post(builder.build()).build();
            Response response = CLIENT.newCall(request).execute();

            String bodyStr = response.body().string();
            log.info("============================================");
            log.info("请求JSON:{}", JSONObject.toJSONString(params));
            log.info("返回JSON:{}", bodyStr);
            log.info("============================================");

            return bodyStr;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("调用API[url=%s]出错, %s", url, e.getMessage());
        }
    }

    /**
     * 发起get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Object ... params) {
        //log.info("连接URL={}", url);
        try {
            Request request = new Request.Builder().url(String.format(url, params)).get().build();
            Response response = CLIENT.newCall(request).execute();

            String bodyStr = response.body().string();
            log.info("============================================");
            log.info("请求JSON:{}", JSONObject.toJSONString(params));
            log.info("返回JSON:{}", bodyStr);
            log.info("============================================");

            return bodyStr;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            throw new BusinessException("调用API[url=%s]出错, %s", url, e.getMessage());
        }
    }

    public static String logo(String url, Object ... params) {
        log.info("连接URL={}", url);
        try {
            Request request = new Request.Builder().url(String.format(url, params)).get().build();
            Response response = CLIENT.newCall(request).execute();

            //String bodyStr = response.body().string();
            String header = response.header("Content-Type");


            return header;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("调用API[url=%s]出错, %s", url, e.getMessage());
        }
    }

    /**
     * 发起get请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static String get(String url, Map<String, String> header ,Object ... params) {
        //log.info("连接URL={}", url);
        try {

            Request.Builder requestBuilder = new Request.Builder().url(String.format(url, params)).get();
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            Request request = requestBuilder.build();
            Response response = CLIENT.newCall(request).execute();

            String bodyStr = response.body().string();
          /*  log.info("============================================");
            log.info("请求JSON:{}", JSONObject.toJSONString(params));
            log.info("返回JSON:{}", bodyStr);
            log.info("============================================");*/

            return bodyStr;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("调用API[url=%s]出错, %s", url, e.getMessage());
        }
    }


    /**
     * 请求上传xml字符串文件
     *
     * @param url
     * @param xmlStr
     * @return
     */
    public static String postXmlStr(String url, String xmlStr) {
        log.info("连接URL={}", url);
        try {

            RequestBody fileBody = RequestBody.create(STREAM, xmlStr);
            RequestBody requestBody = new MultipartBody.Builder().addPart(fileBody).build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = CLIENT.newCall(request).execute();

            String bodyStr = response.body().string();
            log.info("============================================");
            log.info("请求XML:{}", xmlStr);
            log.info("返回JSON:{}", bodyStr);
            log.info("============================================");

            return bodyStr;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("调用API[url=%s]出错, %s", url, e.getMessage());
        }
    }

}
