package com.mall.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * POST/GET请求工具类
 *
 * @author yuerfeng 14090408
 */
public class HttpUtils {
    private static int socketTimeout = 120000;
    private static int connectTimeout = 60000;

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout)
            .build();// 设置请求和传输超时时间
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String post(String url, Object data,Map<String,String>... headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            for (Map<String, String> header : headers) {
                for (String key : header.keySet()) {
                    post.addHeader(new BasicHeader(key,header.get(key)));
                }
            }
            StringEntity entity = new StringEntity(
                    JSON.toJSONString(data, SerializerFeature.WriteMapNullValue), "utf-8");
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(post);
            int returnCode = httpResponse.getStatusLine().getStatusCode();
            if (returnCode == 200) {
                String resData = IOUtils.toString(httpResponse.getEntity().getContent(),"UTF-8");
                return resData;
            } else {
                logger.error("POST请求失败,url=" + url + ",返回码：" + returnCode);
                throw new RuntimeException("POST请求失败,url=" + url + ",返回码：" + returnCode);
            }

        } catch (Exception e1) {
            logger.error("POST请求异常,url=" + url, e1);
            throw new RuntimeException("POST请求异常,url=" + url);
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e2) {

                }
            }
        }
    }

    private static String buildParams(Map<String, Object> params){
        StringBuffer parm = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                if (StringUtils.isEmpty(parm.toString())) {
                    parm.append("?").append(key).append("=")
                            .append(params.get(key));
                } else {
                    parm.append("&").append(key).append("=")
                            .append(params.get(key));
                }
            }
        }
        return parm.toString();
    }

    public static String get(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String parm = buildParams(params);
            HttpGet get = new HttpGet(url + parm);
            get.setConfig(requestConfig);
            HttpResponse httpResponse = httpClient.execute(get);
            int returnCode = httpResponse.getStatusLine().getStatusCode();
            if (returnCode == 200) {
                String resData = IOUtils.toString(httpResponse.getEntity().getContent(),"UTF-8");
                return resData;
            } else {
                logger.error("GET请求失败,url=" + url + ",返回码：" + returnCode);
            }
        } catch (Exception e1) {
            logger.error("GET请求异常,url=" + url, e1);
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e2) {

                }
            }
        }
        return null;
    }

    public static String get(String url, Map<String, Object> params,Map<String, String> header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String parm = buildParams(params);
            HttpGet get = new HttpGet(url + parm);
            get.setConfig(requestConfig);
            if (header != null && header.size() > 0) {
                for (Map.Entry<String,String> kv : header.entrySet()) {
                    get.addHeader(kv.getKey(),kv.getValue());
                }
            }
            HttpResponse httpResponse = httpClient.execute(get);
            int returnCode = httpResponse.getStatusLine().getStatusCode();
            if (returnCode == 200) {
                String resData = IOUtils.toString(httpResponse.getEntity().getContent(),"UTF-8");
                return resData;
            } else {
                logger.error("GET请求失败,url=" + url + ",返回码：" + returnCode);
            }
        } catch (Exception e1) {
            logger.error("GET请求异常,url=" + url, e1);
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e2) {

                }
            }
        }
        return null;
    }

    public static void main(String[] arg) {
        HttpUtils.get("https://www.baidu.com",null);
    }
}
