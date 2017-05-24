package com.critc.plat.util.http;

import com.critc.plat.util.log.LogUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * http工具类
 *
 * @author 孔垂云
 * @date 2017-05-23
 */
public class HttpUtil {
    /**
     * 发送http的json请求
     *
     * @param url
     * @param json
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static String httpJsonPost(String url, String json) {
        InputStream is = null;
        BufferedReader br = null;
        String ret = "";
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //连接成功
            ret = getResponse(httpResponse, is, br);
            httpPost.releaseConnection();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }
        return ret;
    }

    /**
     * 发送http的json请求
     *
     * @param url     请求url
     * @param hashMap 参数列表
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static String httpParamsPost(String url, HashMap<String, String> hashMap) {
        InputStream is = null;
        BufferedReader br = null;
        String ret = "";
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);


            //方式二: 如果需要传递多个参数
            List<NameValuePair> parameters = new ArrayList<>();
            Iterator<Map.Entry<String, String>> iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //连接成功
            ret = getResponse(httpResponse, is, br);
            httpPost.releaseConnection();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }
        return ret;
    }

    /**
     * 请求url
     *
     * @param url url地址
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static String httpPGet(String url) {
        InputStream is = null;
        BufferedReader br = null;
        String ret = "";
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //连接成功
            ret = getResponse(httpResponse, is, br);
            httpGet.releaseConnection();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }
        return ret;
    }

    /**
     * 获取返回值
     *
     * @param httpResponse
     * @param is
     * @param br
     * @return
     * @throws Exception
     */
    private static String getResponse(HttpResponse httpResponse, InputStream is, BufferedReader br) throws Exception {
        StringBuilder sBuilder = null;
        if (200 == httpResponse.getStatusLine().getStatusCode()) {
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            br = new BufferedReader(new InputStreamReader(is));
            String tempStr;
            sBuilder = new StringBuilder();
            while ((tempStr = br.readLine()) != null) {
                sBuilder.append(tempStr);
            }
        }
        return sBuilder.toString();
    }
}
