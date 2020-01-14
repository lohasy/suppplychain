package com.jeeplus.modules.esign.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.service.SignFlowStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.json.JsonObject;

/**
 * 签署流程发起（e签宝）
 *
 * @author lgf
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/flowstart")
public class SignFlowStartController {

    @Autowired
    private SignFlowStartService signFlowStartService;
    /**
     * 签署流程发起
     */
    @RequestMapping("singStart")
    public ServerResponse signFlowStart(@RequestParam String flowId) {
        try {
            return signFlowStartService.signFlowStart(flowId);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    /**
     * 签署流程发起
     */
    @RequestMapping("callBackSave")
    public void callBackSave(@RequestParam JSONObject json) {
          signFlowStartService.callBackSave(json);
    }

    /**
     * 合同文件保存
     */
    @RequestMapping("saveContract")
    public ServerResponse saveContract(@RequestParam String flowId) {
        try {
            return signFlowStartService.signFlowStart(flowId);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

}
