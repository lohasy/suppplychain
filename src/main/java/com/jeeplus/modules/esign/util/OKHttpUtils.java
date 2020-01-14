package com.jeeplus.modules.esign.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * 基于OKHttp构建的请求工具类
 */
public class OKHttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(OKHttpUtils.class);

    public static OkHttpClient getClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30L, TimeUnit.SECONDS)//连接时间
                .readTimeout(90L, TimeUnit.SECONDS) //超时时间
                .build();
    }

    public static JSONObject postJson(String url, JSONObject body) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30L, TimeUnit.SECONDS)//连接时间
                .readTimeout(90L, TimeUnit.SECONDS) //超时时间
                .build();

        String bodyStr = body.toString();
        JSONObject responseObj = null;
        String requestBatchNo = IdUtils.generateCouponBatchNo();
        logger.info("OKHttpUtils请求编号【{}】发送POST请求url={}，body={}", requestBatchNo, url, bodyStr);
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), bodyStr);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = httpClient.newCall(request).execute();
            String result = Objects.requireNonNull(response.body().string());
            logger.info("OKHttpUtils接收请求编号【{}】返回内容={}", requestBatchNo, result);
            responseObj = JSONObject.parseObject(result);
        } catch (Exception e) {
            logger.error("接收请求编号【{}】报错，错误信息{}", requestBatchNo, e);
        }
        return responseObj;
    }

    public static String postXML(String url, String body) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30L, TimeUnit.SECONDS)//连接时间
                .readTimeout(90L, TimeUnit.SECONDS) //超时时间
                .build();

        String result = null;
        String requestBatchNo = IdUtils.generateCouponBatchNo();
        logger.info("OKHttpUtils请求编号【{}】发送POST-XML请求url={}，body={}", requestBatchNo, url, body);
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml;charset=utf-8"), body);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = httpClient.newCall(request).execute();
            result = Objects.requireNonNull(response.body().string());
            logger.info("OKHttpUtils接收请求编号【{}】返回内容={}", requestBatchNo, result);
        } catch (Exception e) {
            logger.error("接收请求编号【{}】报错，错误信息{}", requestBatchNo, e);
        }
        return result;
    }

    public static JSONObject postForm(String url, FormBody formBody) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(90L, TimeUnit.SECONDS)
                .build();

        JSONObject responseObj = null;
        String requestBatchNo = IdUtils.generateCouponBatchNo();
        logger.info("OKHttpUtils请求编号【{}】发送POST-FORM请求url={}，body={}", requestBatchNo, url, getRequestParams(formBody));
        try {
            Request request = new Request.Builder().url(url).post(formBody).build();
            Response response = httpClient.newCall(request).execute();
            String result = Objects.requireNonNull(response.body()).string();
            logger.info("OKHttpUtils接收请求编号【{}】返回内容={}", requestBatchNo, result);
            responseObj = JSONObject.parseObject(result);
        } catch (Exception e) {
            logger.error("接收请求编号【{}】报错，错误信息{}", requestBatchNo, e);
        }
        return responseObj;
    }

    public static JSONObject getRequest(String url, Map<String, Object> params) {
        OkHttpClient httpClient = new OkHttpClient();

        JSONObject responseObj = null;
        String requestBatchNo = IdUtils.generateCouponBatchNo();
        logger.info("OKHttpUtils请求编号【{}】发送GET请求url={}，params={}", requestBatchNo, url, params.toString());
        try {
            url = attachParamsToUrl(url, params);
            Request request = new Request.Builder().url(url).get().build();
            Response response = httpClient.newCall(request).execute();
            String result = Objects.requireNonNull(response.body()).string();
            logger.info("OKHttpUtils接收请求编号【{}】返回内容={}", requestBatchNo, result);
            responseObj = JSONObject.parseObject(result);
        } catch (Exception e) {
            logger.error("OKHttpUtils接收请求编号【{}】报错，错误信息{}", requestBatchNo, e);
        }
        return responseObj;
    }

    public static JSONObject postJsonAddHeader(String url, HashMap<String, String> headers, JSONObject body) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30L, TimeUnit.SECONDS)//连接时间
                .readTimeout(90L, TimeUnit.SECONDS) //超时时间
                .build();
        JSONObject responseObj = null;
        String requestBatchNo = IdUtils.generateCouponBatchNo();
        String bodyStr = body.toString();
        logger.info("OKHttpUtils请求编号【{}】发送POST请求url={}，header={}，body={}", requestBatchNo, url, headers, bodyStr);
        try {
            RequestBody requestBody = RequestBody.
                    create(MediaType.parse("application/json;charset=utf-8"), bodyStr);
            Request request = new Request.Builder().url(url)
                    .headers(Headers.of(headers))
                    .post(requestBody).build();
            Response response = httpClient.newCall(request).execute();
            String result = Objects.requireNonNull(response.body()).string();
            logger.info("接收请求编号【{}】返回内容={}", requestBatchNo, result);
            responseObj = JSONObject.parseObject(result);
        } catch (Exception e) {
              logger.error("接收请求编号【{}】报错，错误信息{}", requestBatchNo, e);
        }
        return responseObj;
    }

    /**
     * 参数关联到Url上
     */
    public static String attachParamsToUrl(String url, Map<String, Object> params) {
        Iterator<String> keys = params.keySet().iterator();
        Iterator<Object> values = params.values().iterator();
        if (params.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?");

            for (int i = 0; i < params.size(); i++) {
                stringBuffer.append(keys.next() + "=" + values.next());
                if (i != params.size() - 1) {
                    stringBuffer.append("&");
                }
            }

            return url + stringBuffer.toString();
        }
        return url;
    }

    public static JSONObject getRestfulAddHeader(String url,HashMap<String,String> header){
        OkHttpClient httpClient = getClient();
        JSONObject responseObj = null;
        String requestBatchNo = IdUtils.generateCouponBatchNo();
        logger.info("请求编号【{}】发送GET请求url={}，header={}，body={}", requestBatchNo, url, header);
        try {
            Request request = new Request.Builder().url(url).headers(Headers.of(header))
                    .build();
            Response response = httpClient.newCall(request).execute();
            String result = Objects.requireNonNull(response.body()).string();
            logger.info("接收请求编号【{}】返回内容={}", requestBatchNo, result);
            responseObj = JSONObject.parseObject(result);
        } catch (Exception e) {
              logger.error("接收请求编号【{}】报错，错误信息{}", requestBatchNo, e);
        }
        return responseObj;
    }

    private static String getRequestParams(FormBody formBody) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < formBody.size(); i++) {
            stringBuilder.append(formBody.encodedName(i) + "=" + formBody.encodedValue(i) + ",");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());

        return stringBuilder.toString();
    }

}
