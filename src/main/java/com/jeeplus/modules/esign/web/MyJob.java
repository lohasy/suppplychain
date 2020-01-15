package com.jeeplus.modules.esign.web;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyJob {

    @Scheduled(cron = "0/5 * * * * ?")
    public void refreshToken(){
        System.out.println("哈哈");
    }
}
