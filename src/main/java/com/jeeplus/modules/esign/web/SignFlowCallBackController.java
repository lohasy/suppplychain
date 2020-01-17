package com.jeeplus.modules.esign.web;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.sms.SMSUtils;
import com.jeeplus.modules.esign.bean.signflow.ServerResponseResult;
import com.jeeplus.modules.esign.exception.DefineException;
import com.jeeplus.modules.esign.service.SignFlowStartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jax
 */
@Controller
@RequestMapping(value = "signResult")
public class SignFlowCallBackController {

    @Autowired
    private SignFlowStartService signFlowStartService;

    protected static Logger logger = LoggerFactory.getLogger(SMSUtils.class);

    /**
     * 签署流程发起
     */
    @RequestMapping(value = {"/singStart"}, method = RequestMethod.GET)
    public ServerResponseResult signFlowStart(@RequestParam String flowId) {
        try {
            return signFlowStartService.signFlowStart(flowId);
        } catch (DefineException e) {
            logger.info("请求失败：" + e.getMessage());
            return ServerResponseResult.createByErrorMessage("请求失败！");
        }
    }


    @RequestMapping(value = {"/callBackSave"}, method = RequestMethod.POST)
    @ResponseBody
    /**
     * 回调结果保存
     * @author 飞飞
     */
    public ServerResponseResult callBackSave(@RequestBody JSONObject json) {
        try {
            int i = signFlowStartService.callBackSave(json);
            if (i == 1) {
                logger.info("回调成功！");
                return ServerResponseResult.createBySuccessMessage("回调成功！");
            }
            logger.info("回调已更新！数据库更新记录为0");
            return ServerResponseResult.createBySuccessMessage("已更新！");
        } catch (Exception e) {
            logger.info("回调失败！请检查流程问题！");
            return ServerResponseResult.createByErrorMessage("回调失败！");
        }
    }

    /**
     * 合同文件保存
     */
    @RequestMapping(value = {"/saveFlowDoc"}, method = RequestMethod.GET)
    public String saveFlowDoc(@RequestParam String flowId) {
        try {
            return signFlowStartService.saveFlowDoc(flowId);
        } catch (DefineException e) {
            return e.getMessage();
        }
    }

    /**
     * 获取签署地址
     */
    @RequestMapping(value = {"/getSignUrl"}, method = RequestMethod.GET)
    @ResponseBody
    public ServerResponseResult getSignUrl( JSONObject json) {
        try {
            Assert.notNull(json, "json is cannot be null !");
            return signFlowStartService.getSignUrl((String) json.get("flowId"),(String) json.get("accountId"),(String) json.get("organizeId"));
        } catch (Exception e) {
            return ServerResponseResult.createByErrorMessage("回调失败！");
        }
    }


}
