package com.jeeplus.modules.esign.service;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.exception.DefineException;

public interface SignFlowStartService {

    ServerResponse signFlowStart(String flowId) throws DefineException;

    void callBackSave(JSONObject json);

}
