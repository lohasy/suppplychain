package com.jeeplus.common.controller;

import com.jeeplus.common.sms.CacheEntity;
import com.jeeplus.common.sms.LocalCache;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 获取短信缓存
 *
 * @author gjzuo
 * @version V1.0
 * @date 2019/12/24 14:51
 */
@RestController
public class CacheController {
    /**
     * 获取短信发送的验证码
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/smscache")
    public ConcurrentHashMap<String, CacheEntity> getCache(){
        String loginName = UserUtils.getUser().getLoginName();
        if("admin".equals(loginName)){
            return LocalCache.cache;
        }
        return null;


    }
}
