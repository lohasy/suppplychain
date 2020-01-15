package com.jeeplus.modules.esign.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.dao.SignFlowStartDao;
import com.jeeplus.modules.esign.exception.DefineException;
import com.jeeplus.modules.esign.service.SignFlowStartService;
import com.jeeplus.modules.esign.util.ESignFlowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SignFlowStartServiceImpl implements SignFlowStartService {

    @Autowired
    private SignFlowStartDao signFlowStartDao;

    @Override
    public ServerResponse signFlowStart(String flowId) throws DefineException {
        JSONObject json = ESignFlowUtils.startSignFlow(flowId);
        Object obj = json.get("data");
        String message = json.getString("message");
        int code = json.getIntValue("code");
        if (code != 0 && obj == null) {
            return ServerResponse.createByErrorCodeMessage(code, message);
        }
        return ServerResponse.createBySuccessMessage(message);
    }

    @Override
    public void callBackSave(JSONObject json) {
        Map<String, String> resultMap = JSONObject.toJavaObject(json, Map.class);
        String signTime = resultMap.get("signTime");
        String signResult = resultMap.get("signResult");
        String resultDescription = resultMap.get("resultDescription");
        String accountId = resultMap.get("accountId");
        String flowId = resultMap.get("flowId");
        signFlowStartDao.callBackSave(signTime, signResult, resultDescription, accountId, flowId);
    }
}
