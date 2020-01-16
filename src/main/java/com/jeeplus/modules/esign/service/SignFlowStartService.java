package com.jeeplus.modules.esign.service;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.signflow.ServerResponseResult;
import com.jeeplus.modules.esign.exception.DefineException;

/**
 * @author Jax
 */
public interface SignFlowStartService {

    /**
     * 签署流程开启
     * @param flowId
     * @return
     * @throws DefineException
     */
    ServerResponseResult signFlowStart(String flowId) throws DefineException;

    /**
     * e签宝接口地址回调
     * @param json
     * @return
     */
    int callBackSave(JSONObject json);

    /**
     * 保存文档
     * @param flowId
     * @return
     * @throws DefineException
     */
    String saveFlowDoc(String flowId) throws DefineException;

    /**
     * 获取签署地址的url
     * @param flowId
     * @param accountId
     * @param organizeId
     * @return
     */
    ServerResponseResult getSignUrl(String flowId, String accountId, String organizeId);

}
