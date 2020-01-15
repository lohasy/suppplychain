package com.jeeplus.modules.esign.service;

import com.jeeplus.modules.esign.bean.FaceResultDto;

public interface FaceService {
    Object getFaceUrl();

    Object faceResult(FaceResultDto faceResultDto);

    Object getAccessToken();
}
