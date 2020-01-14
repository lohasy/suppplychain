package com.jeeplus.modules.esign.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class UserEsignFaceDto {
    private String accountId;
    private String agentAccountId;
    private String authType;
    @JSONField(name="contextInfo")
    private ContextInfoDto contextInfo;


}
