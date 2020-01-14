package com.jeeplus.modules.esign.util;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.signflow.FlowAddFile;
import com.jeeplus.modules.esign.bean.signflow.SignFlowStart;
import com.jeeplus.modules.esign.comm.HttpHelper;
import com.jeeplus.modules.esign.comm.JSONHelper;
import com.jeeplus.modules.esign.constant.ConfigConstant;
import com.jeeplus.modules.esign.enums.RequestType;
import com.jeeplus.modules.esign.exception.DefineException;

import java.util.List;

public class ESignFlowUtils {
    private static final String BASE_URL = "https://smlopenapi.esign.cn";
    private static final String APPID = "4438793080";
    private static final String APPSECRET = "46748dcae0a380f46e75e0e9b7b284e3";

    /**
     * @description 创建签署流程
     */
    public static JSONObject createSignFlow(SignFlowStart signFlowStart) throws DefineException {
        String param = SignParamUtil.createSignFlowParam(signFlowStart);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.createFlows_URL(), param);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @description 流程文档添加
     */
    public static JSONObject addFlowDoc(String flowId, List<FlowAddFile> files) throws DefineException {

        String param = SignParamUtil.addFlowDocParam(files);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.aboutDocument_URL(flowId, null),
                param);
//        JSONHelper.castDataJson(json, Object.class);
        return json;
    }

    /**
     * @description 流程文档删除
     */
    public static JSONObject delFlowDoc(String flowId, String fileIds) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.DELETE, ConfigConstant.aboutDocument_URL(flowId, fileIds),
                null);
//        JSONHelper.castDataJson(json, Object.class);
        return json;
    }

    /**
     * @description 添加签署方手动盖章签署区
     */
    public static JSONObject addSignerHandSignArea(String flowId, List<String> fileIds, List<String> signerAccountIds,
                                                   List<String> authorizedAccountIds) throws DefineException {

        String param = SignParamUtil.addSignerHandSignAreaParam(fileIds, signerAccountIds, authorizedAccountIds);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.addHandSignfieldsForPerson_URL(flowId),
                param);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @description 删除签署区
     */
//    public static JSONObject delSignArea(String flowId, String signfieldIds) throws DefineException {
//        JSONObject json = HttpHelper.doCommHttp(RequestType.DELETE,
//                ConfigConstant.deleteSignfields_URL(flowId, signfieldIds), null);
//        return JSONHelper.castDataJson(json, JSONObject.class);
//    }

    public static JSONObject startSignFlow(String flowId) throws DefineException {
        return HttpHelper.doCommHttp(RequestType.PUT, ConfigConstant.startFlows_URL(flowId), null);
    }



}
