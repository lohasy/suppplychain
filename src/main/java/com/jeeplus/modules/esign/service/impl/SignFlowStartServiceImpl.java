package com.jeeplus.modules.esign.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.signflow.ServerResponseResult;
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
    public ServerResponseResult signFlowStart(String flowId) throws DefineException {
        JSONObject json = ESignFlowUtils.startSignFlow(flowId);
        Object obj = json.get("data");
        String message = json.getString("message");
        int code = json.getIntValue("code");
        if (code != 0 && obj == null) {
            return ServerResponseResult.createByErrorMessage(message);
        }
        return ServerResponseResult.createBySuccess(message);
    }

    @Override
    public int callBackSave(JSONObject json) {
        Map<String, Object> resultMap = JSONObject.toJavaObject(json, Map.class);
        String signTime = (String) resultMap.get("signTime");
        Integer signResult = (Integer) resultMap.get("signResult");
        String resultDescription = (String) resultMap.get("resultDescription");
        String accountId = (String) resultMap.get("accountId");
        String flowId = (String) resultMap.get("flowId");
        return signFlowStartDao.callBackSave(signTime, signResult.toString(), resultDescription, accountId, flowId);
    }

    @Override
    public void saveFlowDoc(String flowId) throws DefineException {
        ESignFlowUtils.downloadFlowDoc(flowId);
    }

}
