package com.jeeplus.modules.esign.util;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.AccessToken;
import com.jeeplus.modules.esign.bean.EsignResultDto;
import com.jeeplus.modules.esign.bean.FaceUrlDto;
import com.jeeplus.modules.esign.bean.UserEsignFaceDto;
import com.jeeplus.modules.esign.comm.LocalCacheHelper;
import com.jeeplus.modules.esign.constant.CacheKeyConstant;
import com.jeeplus.modules.esign.constant.ConfigConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


/**
 * @author lohas
 */

public class EsignUtil {

    private static final Logger logger = LoggerFactory.getLogger(EsignUtil.class);

    //todo 上线前改成生产的地址以及密钥
    private static final String BASE_URL = "https://smlopenapi.esign.cn";
  /*  private static final String APPID = "4438793080";
    private static final String APPSECRET = "46748dcae0a380f46e75e0e9b7b284e3";*/

    private static final String APPID = "4438793380";
    private static final String APPSECRET = "03e018808dbf566714b8f7a33b182707";
    private static HashMap<String, String> headCommonMap = new HashMap<>();






    private static final String GET_TOKEN_URL = "/v1/oauth2/access_token?appId=APPID&secret=APPSECRET&grantType=client_credentials";
    /**
     * 存储token和过期时间
     */
    private static volatile AccessToken at;

    /**
     * 获取token
     */
    public static String getToken(){
        try{
            String url = ConfigConstant.host +GET_TOKEN_URL.replace("APPID",ConfigConstant.PROJECT_ID).replace("APPSECRET",ConfigConstant.PROJECT_SECRET);
            JSONObject jsonStr=new JSONObject();
            JSONObject jsonResult = OKHttpUtils.getRequest(url, jsonStr);
            JSONObject json = jsonResult.getJSONObject("data");
            String token = json.getString("token");
            String expireIn = json.getString("expiresIn");
            String refreshToken = json.getString("refreshToken");
            LocalCacheHelper.put(CacheKeyConstant.REFRESH_TOKEN,refreshToken);
            //创建token，并存储起来
            return token;
        }catch (Exception e){
            logger.error("获取e签宝accesstoken失败"+e.getMessage());
        }
        return null;
    }

    /**
     * 向外暴露的获取token的方法
     * @return
     */
    public static String getAccessToken(){
        if(null==LocalCacheHelper.get(CacheKeyConstant.TOKEN)){
            String token = getToken();
            LocalCacheHelper.put(CacheKeyConstant.TOKEN,token);
        }
        return LocalCacheHelper.get(CacheKeyConstant.TOKEN).toString();
    }

    /**
     * 创建e签宝个人账户
     * @param jsonObject
     * @return
     */
    public static JSONObject createEsignAccount(JSONObject jsonObject) {
        String url = BASE_URL + "/v1/accounts/createByThirdPartyUserId";
        headCommonMap.put("X-Tsign-Open-App-Id",APPID);
        headCommonMap.put("X-Tsign-Open-Token",getAccessToken());
        headCommonMap.put("Content-Type","application/json");
        logger.info(headCommonMap.toString());
        JSONObject result = OKHttpUtils.postJsonAddHeader(url, headCommonMap, jsonObject);
        return result;
    }

    /**
     * 创建e签宝企业账户
     * @param jsonObject
     * @return
     */
    public static JSONObject createEsignComponyAccount(JSONObject jsonObject){
        String url = BASE_URL + "/v1/organizations/createByThirdPartyUserId";
        headCommonMap.put("X-Tsign-Open-App-Id",APPID);
        headCommonMap.put("X-Tsign-Open-Token",getAccessToken());
        headCommonMap.put("Content-Type","application/json");
        logger.info(headCommonMap.toString());
        JSONObject result = OKHttpUtils.postJsonAddHeader(url, headCommonMap, jsonObject);
        return result;
    }

    /**
     * 根据账户id获取签章
     * @param accountId 账户id
     * @return
     */
    public static JSONObject queryEsignSealsByAccoundId(String accountId) {
        String url = BASE_URL + "/v1/accounts/accountId/seals";
        url = url.replace("accountId", accountId);
        headCommonMap.put("X-Tsign-Open-App-Id", APPID);
        headCommonMap.put("X-Tsign-Open-Token", getAccessToken());
        headCommonMap.put("Content-Type", "application/json");
        logger.info(headCommonMap.toString());
        JSONObject jsonResult = OKHttpUtils.getRestfulAddHeader(url, headCommonMap);
        return jsonResult;
    }

    public static FaceUrlDto getFaceUrl(UserEsignFaceDto userEsignFaceDto){
        String token=getAccessToken();
        String s = JSONObject.toJSONString(userEsignFaceDto);
        JSONObject jsonObject = JSONObject.parseObject(s);
        HashMap map=new HashMap<String,String>();
        map.put("X-Tsign-Open-App-Id",APPID);
        map.put("X-Tsign-Open-Token",token);
        JSONObject jsonResult = OKHttpUtils.postJsonAddHeader(BASE_URL + "/v2/identity/auth/web/" + userEsignFaceDto.getAccountId() + "/orgIdentityUrl", map, jsonObject);
        EsignResultDto esignResultDto = JSONObject.parseObject(jsonResult.toJSONString(), EsignResultDto.class);
        FaceUrlDto faceUrlDto = JSONObject.parseObject(esignResultDto.getData(), FaceUrlDto.class);
        return faceUrlDto;

    }

    public static void main(String[] args) {
        String accessToken = getAccessToken();
        System.out.println(accessToken);
        System.out.println(getAccessToken());
    }
    //todo 定时任务刷新token
    public static void refreshAccessToken() {
        logger.info("开始刷新accessToken,当前token={}",LocalCacheHelper.get(CacheKeyConstant.TOKEN));
        JSONObject jsonStr=new JSONObject();
        JSONObject jsonResult = OKHttpUtils.getRequest(ConfigConstant.refreshToken_URL(ConfigConstant.PROJECT_ID,String.valueOf(LocalCacheHelper.get(CacheKeyConstant.REFRESH_TOKEN))), jsonStr);
        JSONObject json = jsonResult.getJSONObject("data");
        String token = json.getString("token");
        String refreshToken = json.getString("refreshToken");
        LocalCacheHelper.put(CacheKeyConstant.REFRESH_TOKEN,refreshToken);
        LocalCacheHelper.put(CacheKeyConstant.TOKEN,token);
        logger.info("刷新accessToken完毕,当前token={}",LocalCacheHelper.get(CacheKeyConstant.TOKEN));
    }
}
