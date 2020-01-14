package test;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.util.EsignUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EsinApiTest {
    /**
     * 个人用户ed838597a4684873803ce15dc8bf57df
     * 企业用户63aea2f2e6284376a029c8dc8b2f951e
     * 个人签章2801fc62-b6b3-4ca6-bf29-3ac965f43553
     */
    @Test
    public void testAccount(){
        String strAccount = "{\n" +
                "\t\"email\": \"1184315279@qq.com\",\n" +
                "\t\"idNumber\": \"340825199306144219\",\n" +
                "\t\"idType\": \"\",\n" +
                "\t\"mobile\": \"19815634672\",\n" +
                "\t\"name\": \"余明星\",\n" +
                "\t\"thirdPartyUserId\": \"yu01\"\n" +
                "}";
        JSONObject params = JSONObject.parseObject(strAccount);
        JSONObject esignAccount = EsignUtil.createEsignAccount(params);
        System.out.println(esignAccount);
    }
    @Test
    public void testCompanyAccount(){
        String str = "{\n" +
                "\t\"creator\": \"ed838597a4684873803ce15dc8bf57df\",\n" +
                "\t\"name\": \"余明星有限公司\",\n" +
                "\t\"thirdPartyUserId\": \"6001\"\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONObject esignComponyAccount = EsignUtil.createEsignComponyAccount(jsonObject);
        System.out.println(esignComponyAccount);
    }

    @Test
    public void testQuerySeals(){
        JSONObject sealIf = EsignUtil.queryEsignSealsByAccoundId("ed838597a4684873803ce15dc8bf57df");
        System.out.println(sealIf);
    }

    @Test
    public void testDao(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        System.out.println(context);
    }
}
