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
import com.jeeplus.modules.sys.utils.UserUtils;
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
//        ServerResponse serverResponse = null;
        contract_id = "588d4c4c902246149e82cccceaf61fcf"; //暂时写死
        String flowId ="";
        try {
            signFlowStart.setAutoArchive(true); //设置自动归档，归档后的签署文件才能下载
            if (null == signFlowStart.getBusinessScene()){
                signFlowStart.setBusinessScene("默认文件主题");
            }
            if(null == signFlowStart.getConfigInfo()){
                ConfigInfo configInfo  = new ConfigInfo();
                configInfo.setNoticeType("1"); //1-短信，2-邮件
                signFlowStart.setConfigInfo(configInfo);
            }
            if(null == signFlowStart.getConfigInfo().getNoticeType()){
                signFlowStart.getConfigInfo().setNoticeType("1");
            }

            JSONObject jsonObject = ESignFlowUtils.createSignFlow(signFlowStart);
//            serverResponse = JSONObject.parseObject(jsonObject.toString(), ServerResponse.class);
//            JSONObject responseData = (JSONObject) jsonObject.get("data");
             flowId = jsonObject.getString("flowId");
            thyDao.updateProcessId(contract_id,flowId);
        }catch (Exception e){
            return ServerResponse.fail(-1,"创建签署流程异常");
        }
        return ServerResponse.success("流程创建成功",flowId);
    }


    /**
     * @description 流程文档添加
     */
    @RequestMapping(value = "addFlowDoc", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addFlowDoc(@RequestParam(value = "flowId")String flowId,
                                     @RequestBody List<FlowAddFile> files){
//        ServerResponse serverResponse = null;
        String msg = "";
        try {
            if(null == flowId || "".equals(flowId.trim())){
                return ServerResponse.fail(-1,"流程ID为不能空");
            }
            if (null == files || files.isEmpty()) {
                return ServerResponse.fail(-1,"文档为空");
            }
            JSONObject jsonObject = ESignFlowUtils.addFlowDoc(flowId,files);
            msg = jsonObject.toString();
//            serverResponse = JSONObject.parseObject(jsonObject.toString(), ServerResponse.class);
        }catch (Exception e){
            return ServerResponse.fail(-1,"流程文档添加系统异常");
        }
        return ServerResponse.success(msg);
    }

    /**
     * @description 添加签署方手动盖章签署区
     */
    @RequestMapping(value = "addSignerHandSignArea", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSignerHandSignArea(@RequestParam(value = "flowId")String flowId,
                                                @RequestBody List<Signfield> signfieldList){
//        ServerResponse serverResponse = null;
        String msg ="";
        try {
            if(null == flowId || "".equals(flowId.trim())){
                return ServerResponse.fail(-1,"流程ID为不能空");
            }
            if (null == signfieldList || signfieldList.isEmpty()) {
                return ServerResponse.fail(-1,"签章信息为空");
            }
            String userid = UserUtils.getUser().getId();
            String splierId = UserUtils.getUser().getSupplier().getId();
            String uesing = thyDao.getUserEsignIdByUserId(userid);
            String spsing = thyDao.getUserEsignIdByUserId(splierId);
            for(Signfield file : signfieldList){
                if(null == file.getFileId() || "".equals(file.getFileId().trim()) ||
                        null == file.getSignerAccountId() ||
                        "".equals(file.getSignerAccountId().trim())){
                    return ServerResponse.fail(-1,"文件或签章人id缺失");
                }
                file.setSignerAccountId(uesing);
                file.setAuthorizedAccountId(spsing);
            }
            JSONObject jsonObject = ESignFlowUtils.addSignerHandSignArea(flowId,signfieldList);
//            serverResponse = JSONObject.parseObject(jsonObject.toString(), ServerResponse.class);

            msg = jsonObject.toString();
        }catch (Exception e){
            return ServerResponse.fail(-1,"添加签署方手动盖章签署区异常");
        }
        return ServerResponse.success(msg,"成功");
    }

}
