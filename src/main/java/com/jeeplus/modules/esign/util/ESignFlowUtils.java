package com.jeeplus.modules.esign.util;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.bean.signflow.FlowAddFile;
import com.jeeplus.modules.esign.bean.signflow.SignFlowStart;
import com.jeeplus.modules.esign.bean.signflow.Signfield;
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
    public static JSONObject addSignerHandSignArea(String flowId, List<Signfield> signfieldList) throws DefineException {

        String param = SignParamUtil.addSignerHandSignAreaParam(signfieldList);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.addHandSignfieldsForPerson_URL(flowId),
                param);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    public static JSONObject startSignFlow(String flowId) throws DefineException {
        return HttpHelper.doCommHttp(RequestType.PUT, ConfigConstant.startFlows_URL(flowId), null);
    }

    /**
     * @param flowId  创建签署流程时返回的签署流程ID
     * @param fileIds 文档id列表,多个id使用英文“，”分隔
     * @throws DefineException
     * @description 流程文档下载
     * <p>
     * 说明：
     * <p>
     * 流程归档后，查询和下载签署后的文件
     * @author 宫清
     * @date 2019年7月21日 下午5:59:45
     */
    public static void downloadFlowDoc(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET, ConfigConstant.aboutDocument_URL(flowId, null), null);
//        JSONHelper.castDataJson(json, Object.class);
        final String url = String.valueOf(json.get("data"));
        JSONObject result = HttpHelper.doCommHttp(RequestType.GET, url, null);
    }


}
