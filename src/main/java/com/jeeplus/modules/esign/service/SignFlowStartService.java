package com.jeeplus.modules.esign.service;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.signflow.ServerResponseResult;
import com.jeeplus.modules.esign.exception.DefineException;

/**
 * @author Jax
 */
public interface SignFlowStartService {

    ServerResponseResult signFlowStart(String flowId) throws DefineException;

    /**
     * @param json
     * @return
     */
    int callBackSave(JSONObject json);

    void saveFlowDoc(String flowId) throws DefineException;

}
