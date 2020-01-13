package com.jeeplus.common.sms;

import java.io.Serializable;

/**
 * Description: 缓存对象
 *
 * @author gjzuo
 * @version V1.0
 * @date 2019/12/24 14:35
 */
public class CacheEntity implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 7172649826282703560L;

    /**
     * 值
     */
    private Object value;

    /**
     * 保存的时间戳
     */
    private long gmtModify;

    /**
     * 过期时间
     */
    private int expire;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(long gmtModify) {
        this.gmtModify = gmtModify;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public CacheEntity(Object value, long gmtModify, int expire) {
        super();
        this.value = value;
        this.gmtModify = gmtModify;
        this.expire = expire;
    }

}
       
