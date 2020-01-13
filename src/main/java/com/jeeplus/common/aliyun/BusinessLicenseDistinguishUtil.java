package com.jeeplus.common.aliyun;

import com.alibaba.fastjson.JSON;
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
 * 营业执照信息识别工具类（基于阿里云接口api）
 * @author LGT
 */
public class BusinessLicenseDistinguishUtil {

	private static final String HOST = "https://dm-58.data.aliyun.com";
	private static final String PATH = "/rest/160601/ocr/ocr_business_license.json";
	private static final String APPCODE = "1518491ba07a4860941f1b816a9ad4e4";
	
	
	/**
     * 接口读取身份证信息
     * @param url（需要绝对地址，不能是相对地址）
     * @return
     */
    public static JSONObject readBusinessLicenseInfoByApi(String url) {
    	if(url == null || "".equals(url)) {
    		return null;
    	}
    	
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APPCODE);
        headers.put("Content-Type", "application/json; charset=UTF-8");

        Map<String, String> querys = new HashMap<String, String>();
        
        // 对图像进行base64编码
        String imgBase64 = "";
        String bodys = "";
        try {
            File file = new File(url);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
            bodys = "{\"image\":\""+ imgBase64 +"\"}";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        try {
            HttpResponse response = HttpUtils.doPost(HOST, PATH, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if(stat != 200){
                System.out.println("阿里云营业执照识别Http code: " + stat);
                System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return null;
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            return res_obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
