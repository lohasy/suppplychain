package com.jeeplus.modules.esign.web;

import com.jeeplus.modules.esign.util.EsignUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("scheduledManager")
@Lazy(value=false)
public class Mysjob {

    public void refreshToken(){
        EsignUtil.refreshAccessToken();
    }
}

