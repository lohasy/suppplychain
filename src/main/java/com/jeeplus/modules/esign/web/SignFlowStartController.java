package com.jeeplus.modules.esign.web;

import com.jeeplus.modules.esign.bean.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签署流程发起（e签宝）
 *
 * @author lgf
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/flowstart")
public class SignFlowStartController {

    /**
     * 签署流程发起
     */
    @RequestMapping("singStart")
    public ServerResponse signFlowStart(@RequestParam String flowId) {
        try {

        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
