package com.jeeplus.modules.esign.service;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.signflow.ServerResponseResult;
import com.jeeplus.modules.esign.exception.DefineException;

/**
 * @author Jax
 */
public interface SignFlowStartService {

    /**
     *
     * @param flowId
     * @return
     * @throws DefineException
     */
    ServerResponseResult signFlowStart(String flowId) throws DefineException;

    /**
     * @param json
     * @return
     */
    int callBackSave(JSONObject json);

    String saveFlowDoc(String flowId) throws DefineException;

    /**
     * @param flowId
     * @param accountId
     * @param organizeId
     * @return
     */
    ServerResponseResult getSignUrl(String flowId, String accountId, String organizeId);

}
