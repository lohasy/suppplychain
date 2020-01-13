package com.jeeplus.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 * 身份证信息识别工具类（基于阿里云接口api）
 * @author LGT
 */
public class IdCardDistinguishUtil {

	private static final String HOST = "http://dm-51.data.aliyun.com";
	private static final String PATH = "/rest/160601/ocr/ocr_idcard.json";
	private static final String APPCODE = "1518491ba07a4860941f1b816a9ad4e4";
	
	
	/*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    
    /**
     * 接口读取身份证信息
     * @param url（需要绝对地址，不能是相对地址）
     * @return
     */
    public static JSONObject readIdCardInfoByApi(String url, String type) {
    	if(url == null || "".equals(url)) {
    		return null;
    	}
    	Boolean is_old_format = false;
    	JSONObject configObj = new JSONObject();
        configObj.put("side", (type == null || type == ""? "face" : type));
        String config_str = configObj.toString();
        
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APPCODE);

        Map<String, String> querys = new HashMap<String, String>();
        
        // 对图像进行base64编码
        String imgBase64 = "";
        try {
            File file = new File(url);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        JSONObject requestObj = new JSONObject();
        try {
            if(is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if(config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            }else{
                requestObj.put("image", imgBase64);
                if(config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();
        
        try {
            HttpResponse response = HttpUtils.doPost(HOST, PATH, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if(stat != 200){
                System.out.println("阿里云身份证识别Http code: " + stat);
                System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return null;
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            if(is_old_format) {
                JSONArray outputArray = res_obj.getJSONArray("outputs");
                String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                JSONObject out = JSON.parseObject(output);
                return out;
            }else{
                System.out.println(res_obj.toJSONString());
                return res_obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
