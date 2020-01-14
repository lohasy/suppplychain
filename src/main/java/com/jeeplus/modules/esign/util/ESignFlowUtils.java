package com.jeeplus.modules.esign.util;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.comm.HttpHelper;
import com.jeeplus.modules.esign.comm.JSONHelper;
import com.jeeplus.modules.esign.constant.ConfigConstant;
import com.jeeplus.modules.esign.enums.RequestType;
import com.jeeplus.modules.esign.exception.DefineException;

public class ESignFlowUtils {
    private static final String BASE_URL = "https://smlopenapi.esign.cn";
    private static final String APPID = "4438793080";
    private static final String APPSECRET = "46748dcae0a380f46e75e0e9b7b284e3";

    public static JSONObject startSignFlow(String flowId) throws DefineException {
        return HttpHelper.doCommHttp(RequestType.PUT, ConfigConstant.startFlows_URL(flowId), null);
    }

}
