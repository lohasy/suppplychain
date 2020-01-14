package com.jeeplus.modules.esign.util;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.AccessToken;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author lohas
 */

public class EsignUtil {
    private static final String GET_TOKEN_URL = "https://smlopenapi.esign.cn/v1/oauth2/access_token?appId=APPID&secret=APPSECRET&grantType=client_credentials";
    private static final String APPID = "4438793080";
    private static final String APPSECRET = "46748dcae0a380f46e75e0e9b7b284e3";
    /**
     * 存储token和过期时间
     */
    private static AccessToken at;

    /**
     * 获取token
     */
    private static void getToken(){
        try{
            String url = GET_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
            JSONObject jsonStr=new JSONObject();
            JSONObject jsonResult = OKHttpUtils.getRequest(url, jsonStr);
            JSONObject json = jsonResult.getJSONObject("data");
            String token = json.getString("token");
            String expireIn = json.getString("expiresIn");
            //创建token，并存储起来
            at = new AccessToken(token,expireIn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 向外暴露的获取token的方法
     * @return
     */
    public static String getAccessToken(){
        if(at == null||at.isExpired()){
            getToken();
        }
        return at.getAccessToken();
    }

    /**
     * 调用e签宝的token接口
     * @param url
     * @return
     */
    private static String getTokenStr(String url) {
        try{
            URL urlObj = new URL(url);
            URLConnection conn = urlObj.openConnection();
            InputStream inputStream = conn.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(b)) != -1){
                sb.append(new String(b,0,len));
            }
            return  sb.toString();
        }catch (Exception e){
        e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        String accessToken = getAccessToken();
        System.out.println(accessToken);
        System.out.println(getAccessToken());
    }
}
