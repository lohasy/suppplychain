package com.jeeplus.modules.esign.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.exception.DefineException;
import com.jeeplus.modules.esign.service.SignFlowStartService;
import com.jeeplus.modules.esign.util.ESignFlowUtils;

public class SignFlowStartServiceImpl implements SignFlowStartService {
    @Override
    public ServerResponse signFlowStart(String flowId) throws DefineException {
        JSONObject json = ESignFlowUtils.startSignFlow(flowId);
        Object obj = json.get("data");
        String message = json.getString("message");
        int code = json.getIntValue("code");
        if (code != 0 && obj == null) {
            return ServerResponse.createByErrorCodeMessage(code,message);
        }
        return ServerResponse.createBySuccessMessage(message);
    }
}
