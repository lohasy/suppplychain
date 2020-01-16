package com.jeeplus.common.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
功能:		企信通PHP HTTP接口 发送短信
修改日期:	2014-03-19
说明:		http://api.cnsms.cn/?ac=send&uid=用户账号&pwd=MD5位32密码&mobile=号码&content=内容
状态:
	100 发送成功
	101 验证失败
	102 短信不足
	103 操作失败
	104 非法字符
	105 内容过多
	106 号码过多
	107 频率过快
	108 号码内容空
	109 账号冻结
	110 禁止频繁单条发送
	111 系统暂定发送
	112 号码不正确
	120 系统升级
*/
public class SMSUtils {


    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(SMSUtils.class);

    //发送短信，uid，pwd，参数值请向企信通申请， tel：发送的手机号， content:发送的内容，template：模版id
    public static String send(String uid, String pwd, String tel, String content, String template) throws IOException {

        // 创建StringBuffer对象用来操作字符串
        StringBuffer sb = new StringBuffer("http://api.sms.cn/sms/?");

        // 向StringBuffer追加用户名
        sb.append("ac=send&uid=" + uid);//在此申请企信通uid，并进行配置用户名

        sb.append("&pwd=" + pwd);//在此申请企信通uid，并进行配置密码

        sb.append("&template=" + template);

        // 向StringBuffer追加手机号码
        sb.append("&mobile=" + tel);

        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&content=" + URLEncoder.encode(content, "utf8"));
        logger.info(sb.toString());
        // 创建url对象
        URL url = new URL(sb.toString());

        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");

        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        // 返回发送结果
        String inputline = in.readLine();
        JSONObject jsonObject = JSON.parseObject(inputline);
        int stat = jsonObject.getIntValue("stat");
        if (stat == 100) {
            LocalCache localCache = new LocalCache();
            localCache.putValue(tel + "-" + template, content, 600);
        }
        return inputline;
    }
}