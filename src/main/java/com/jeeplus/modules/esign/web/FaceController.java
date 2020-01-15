package com.jeeplus.modules.esign.web;

import com.jeeplus.modules.esign.bean.FaceResultDto;
import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.service.FaceService;
import com.jeeplus.modules.sys.service.SystemConfigService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="face")
public class FaceController {
    @Autowired
    private FaceService faceService;


    @RequestMapping(value = {"faceUrl", ""})
    @ResponseBody
    public Object getFaceUrl(){
        return ServerResponse.success(faceService.getFaceUrl());
    }

    @RequestMapping(value = {"faceResult", ""},method = RequestMethod.POST)
    @ResponseBody
//    @RequiresRoles("super")
    public Object getFaceResult(@RequestBody FaceResultDto faceResultDto){
        return ServerResponse.success(faceService.faceResult(faceResultDto));
    }


}
