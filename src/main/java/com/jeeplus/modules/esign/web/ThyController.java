package com.jeeplus.modules.esign.web;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.bean.signflow.*;
import com.jeeplus.modules.esign.constant.ConfigConstant;
import com.jeeplus.modules.esign.dao.ThyDao;
import com.jeeplus.modules.esign.exception.DefineException;
import com.jeeplus.modules.esign.service.SignFlowStartService;
import com.jeeplus.modules.esign.util.ESignFlowUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/thy")
public class ThyController extends BaseController {

    @Autowired
    ThyDao thyDao;
    @Autowired
    private SignFlowStartService signFlowStartService;

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
        thyDao.updateProcessId("1", "5");
        return serverResponse;
    }

    /**
     * @description 1.创建签署流程
     */
    @RequestMapping(value = "createSignFlow", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse createSignFlow() {
        SignFlowStart signFlowStart = new SignFlowStart(true, "负责人在线签约", null, null, null, null, null,
                new ConfigInfo(ConfigConstant.CALL_BACK_URL, "1", "https://www.baidu.com", null));

        String file1 = "588d4c4c902246149e82cccceaf61fcf";
        String file2 = "55d9a994a69641a9bc0256214bdfd29b";
        String file3 = "b323016ff68b43028906f89128282f2f";
        String flowId = "";
        ServerResponse createSignFlow = getCreateSignFlow(signFlowStart);


        try {
            if (createSignFlow.getCode() != 0) {
                return createSignFlow;
            } else {
                flowId = String.valueOf(createSignFlow.getData());
                List<FlowAddFile> files = new ArrayList<>();
                files.add(new FlowAddFile(0, file1, "e签宝协议", null));
                files.add(new FlowAddFile(0, file2, "数字证书用户授权协议", null));
                files.add(new FlowAddFile(0, file3, "用户注册服务协议", null));
                ServerResponse addFlowDoc = getAddFlowDoc(flowId, files);

                if (addFlowDoc.getCode() != 0) {
                    return addFlowDoc;
                } else {
                    List<Signfield> signfieldList = new ArrayList<>();
                    signfieldList.add(new Signfield(file1, 0, new PosBean("8", 321.1536f, 261.62103f, null, true), null, 1, "", "1"));
                    signfieldList.add(new Signfield(file2, 0, new PosBean("1", 195.16225f, 163.92105f, null, true), null, 1, "", "1"));
                    signfieldList.add(new Signfield(file3, 0, new PosBean("1", 158.72531f, 431.05658f, null, true), null, 1, "", "1"));
                    ServerResponse getAddSignerHandSignArea = getAddSignerHandSignArea(flowId, signfieldList);
                    ServerResponseResult serverResponseResult = signFlowStartService.signFlowStart(flowId);
                    if (getAddSignerHandSignArea.getCode() != 0) {
                        return getAddSignerHandSignArea;
                    }


                    String userid = UserUtils.getUser().getId();
                    String splierId = UserUtils.getUser().getSupplier().getId();
                    String uesing = thyDao.getUserEsignIdByUserId(userid);
                    String spsing = thyDao.getUserEsignIdByUserId(splierId);
                    ServerResponseResult getSignUrlRet = signFlowStartService.getSignUrl(flowId,uesing,spsing);
                    ServerResponse getSignUrl = new ServerResponse();
                    getSignUrl.setCode(getSignUrlRet.getStatus());
                    getSignUrl.setMsg(getSignUrlRet.getMsg());
                    getSignUrl.setData(getSignUrlRet.getData());

                    return getSignUrl;
//                    else {
//                        getAddSignerHandSignArea.setCode(serverResponseResult.getStatus());
//                        getAddSignerHandSignArea.setMsg(serverResponseResult.getMsg());
//                        getAddSignerHandSignArea.setData(serverResponseResult.getData());
//                        return getAddSignerHandSignArea;
//                    }
                }
            }
        } catch (Exception e) {
            return ServerResponse.fail(-1, "创建签章失败");
        }

    }

    /**
     * 1.创建流程
     *
     * @param signFlowStart
     * @return
     */
    private ServerResponse getCreateSignFlow(@RequestBody SignFlowStart signFlowStart) {
        String contract_id;
        //暂时写死
        contract_id = "588d4c4c902246149e82cccceaf61fcf";
        String flowId = "";
        try {
            signFlowStart.setAutoArchive(true); //设置自动归档，归档后的签署文件才能下载
            if (null == signFlowStart.getBusinessScene()) {
                signFlowStart.setBusinessScene("默认文件主题");
            }
            if (null == signFlowStart.getConfigInfo()) {
                ConfigInfo configInfo = new ConfigInfo();
                configInfo.setNoticeType(""); //1-短信，2-邮件 ，“”不通知
                signFlowStart.setConfigInfo(configInfo);
            }
            if (null == signFlowStart.getConfigInfo().getNoticeType()) {
                signFlowStart.getConfigInfo().setNoticeType(""); //1-短信，2-邮件 ，“”不通知
            }
            JSONObject jsonObject = ESignFlowUtils.createSignFlow(signFlowStart);
            flowId = jsonObject.getString("flowId");
            thyDao.updateProcessId(contract_id, flowId);
        } catch (Exception e) {
            return ServerResponse.fail(-1, "创建签署流程异常");
        }
        return ServerResponse.success(flowId, "流程创建成功");
    }


    /**
     * @description 2.流程文档添加
     */
    @RequestMapping(value = "addFlowDoc", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addFlowDoc(@RequestParam(value = "flowId") String flowId,
                                     @RequestBody List<FlowAddFile> files) {
        return getAddFlowDoc(flowId, files);
    }

    /**
     * 流程文档添加
     *
     * @param flowId
     * @param files
     * @return
     */
    private ServerResponse getAddFlowDoc(@RequestParam("flowId") String flowId, @RequestBody List<FlowAddFile> files) {
        String msg = "";
        try {
            if (null == flowId || "".equals(flowId.trim())) {
                return ServerResponse.fail(-1, "流程ID为不能空");
            }
            if (null == files || files.isEmpty()) {
                return ServerResponse.fail(-1, "文档为空");
            }
            JSONObject jsonObject = ESignFlowUtils.addFlowDoc(flowId, files);
            msg = jsonObject.toString();
        } catch (Exception e) {
            return ServerResponse.fail(-1, "流程文档添加系统异常");
        }
        return ServerResponse.success(msg);
    }

    /**
     * @description 3.添加签署方手动盖章签署区
     */
    @RequestMapping(value = "addSignerHandSignArea", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSignerHandSignArea(@RequestParam(value = "flowId") String flowId,
                                                @RequestBody List<Signfield> signfieldList) {
        return getAddSignerHandSignArea(flowId, signfieldList);
    }

    /**
     * 添加签署方手动盖章签署区
     *
     * @param flowId
     * @param signfieldList
     * @return
     */
    private ServerResponse getAddSignerHandSignArea(String flowId, List<Signfield> signfieldList) {
        String msg = "";
        try {
            if (null == flowId || "".equals(flowId.trim())) {
                return ServerResponse.fail(-1, "流程ID为不能空");
            }
            if (null == signfieldList || signfieldList.isEmpty()) {
                return ServerResponse.fail(-1, "签章信息为空");
            }
            String userid = UserUtils.getUser().getId();
            String splierId = UserUtils.getUser().getSupplier().getId();
            String uesing = thyDao.getUserEsignIdByUserId(userid);
            String spsing = thyDao.getUserEsignIdByUserId(splierId);
            for (Signfield file : signfieldList) {
                if (StringUtils.isBlank(uesing) || StringUtils.isBlank(spsing)) {
                    return ServerResponse.fail(-1, "文件或签章人id缺失");
                }
                file.setSignerAccountId(uesing);
                file.setAuthorizedAccountId(spsing);
            }
            JSONObject jsonObject = ESignFlowUtils.addSignerHandSignArea(flowId, signfieldList);

            msg = jsonObject.toString();
        } catch (Exception e) {
            return ServerResponse.fail(-1, "添加签署方手动盖章签署区异常");
        }
        return ServerResponse.success(flowId, "成功");
    }

}
