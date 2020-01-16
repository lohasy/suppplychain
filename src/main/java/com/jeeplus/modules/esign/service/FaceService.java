package com.jeeplus.modules.esign.service;

import com.jeeplus.modules.esign.bean.FaceResultDto;

public interface FaceService {
    //todo 获取人脸地址
    Object getFaceUrl();
    //todo 获取人脸识别结果
    Object faceResult(FaceResultDto faceResultDto);
    //todo 获取accesstokn e签宝
    Object getAccessToken();
}
