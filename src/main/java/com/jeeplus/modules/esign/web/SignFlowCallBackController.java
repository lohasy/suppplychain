package com.jeeplus.modules.esign.web;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.service.SignFlowStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Jax
 */
@Controller
@RequestMapping(value = "signResult")
public class SignFlowCallBackController {
    @Autowired
    private SignFlowStartService signFlowStartService;
    /**
     * 回调结果保存
     */
    @RequestMapping(value = {"callBackSave", ""}, method = RequestMethod.POST)
    public void callBackSave(@RequestParam JSONObject json) {
        signFlowStartService.callBackSave(json);
    }

}
