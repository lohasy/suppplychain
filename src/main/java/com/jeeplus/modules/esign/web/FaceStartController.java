package com.jeeplus.modules.esign.web;

import com.jeeplus.modules.esign.bean.ServerResponse;
import com.jeeplus.modules.esign.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "${adminPath}/facetart")
public class FaceStartController {
    @Autowired
    private FaceService faceService;


    @RequestMapping(value = {"faceUrl", ""})
    @ResponseBody
    public Object getFaceUrl(){
        return ServerResponse.success(faceService.getFaceUrl());
    }
}
