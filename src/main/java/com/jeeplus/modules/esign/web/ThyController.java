package com.jeeplus.modules.esign.web;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.bean.signflow.ConfigInfo;
import com.jeeplus.modules.esign.bean.signflow.FlowAddFile;
import com.jeeplus.modules.esign.bean.signflow.SignFlowStart;
import com.jeeplus.modules.esign.bean.signflow.Signfield;
import com.jeeplus.modules.esign.dao.ThyDao;
import com.jeeplus.modules.esign.exception.DefineException;
import com.jeeplus.modules.esign.util.ESignFlowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping(value="${adminPath}/thy")
public class ThyController extends BaseController {

    @Autowired
    ThyDao thyDao;

    @RequestMapping("aaa")
    @ResponseBody
    public ServerResponse test01(@RequestParam("id") String id, HttpServletResponse response) throws UnsupportedEncodingException, DefineException {

        ConfigInfo cfgInfo = new ConfigInfo(null, "1,2", null, null);
        SignFlowStart sfs = new SignFlowStart(null, "测试签署流程开启", null, null, null, null, null, cfgInfo);

        String str = "{\n" +
                "    \"code\": 0,\n" +
                "    \"message\": \"成功\",\n" +
                "    \"data\": {\n" +
                "        \"flowId\": \"81c74f10925147a5a7a76e77f8e99c47\"\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONObject responseData = (JSONObject) jsonObject.get("data");
        String flowId = responseData.getString("flowId");
        ServerResponse serverResponse = JSONObject.parseObject(str, ServerResponse.class);
//        JsonNode resesponse = JSONObject.parseObject(str , JsonNode.class);
//        String path = resesponse.findPath(flowId).asText();
        thyDao.updateProcessId("1","5");
        return serverResponse;
    }

    /**
     * @description 创建签署流程
     */
    @RequestMapping(value = "createSignFlow", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse createSignFlow(@RequestParam("contract_id")String contract_id,
                                         @RequestBody SignFlowStart signFlowStart){
        ServerResponse serverResponse = null;
        try {
            JSONObject jsonObject = ESignFlowUtils.createSignFlow(signFlowStart);
            serverResponse = JSONObject.parseObject(jsonObject.toString(), ServerResponse.class);
            JSONObject responseData = (JSONObject) jsonObject.get("data");
            String flowId = responseData.getString("flowId");
            thyDao.updateProcessId(contract_id,flowId);
        }catch (Exception e){
            return ServerResponse.fail(-1,"创建签署流程异常");
        }
        return serverResponse;
    }


    /**
     * @description 流程文档添加
     */
    @RequestMapping(value = "addFlowDoc", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addFlowDoc(@RequestParam("flowId")String flowId,
                                     @RequestBody List<FlowAddFile> files){
        ServerResponse serverResponse = null;
        try {
            JSONObject jsonObject = ESignFlowUtils.addFlowDoc(flowId,files);
            serverResponse = JSONObject.parseObject(jsonObject.toString(), ServerResponse.class);
        }catch (Exception e){
            return ServerResponse.fail(-1,"流程文档添加系统异常");
        }
        return serverResponse;
    }

    /**
     * @description 添加签署方手动盖章签署区
     */
    @RequestMapping(value = "addSignerHandSignArea", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSignerHandSignArea(@RequestParam("flowId")String flowId,
                                                @RequestBody List<Signfield> signfieldList){
        ServerResponse serverResponse = null;
        try {
            JSONObject jsonObject = ESignFlowUtils.addSignerHandSignArea(flowId,signfieldList);
            serverResponse = JSONObject.parseObject(jsonObject.toString(), ServerResponse.class);
        }catch (Exception e){
            return ServerResponse.fail(-1,"添加签署方手动盖章签署区异常");
        }
        return serverResponse;
    }

}
