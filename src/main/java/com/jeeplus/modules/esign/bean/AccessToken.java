package com.jeeplus.modules.esign.bean;

public class AccessToken {
    private String accessToken;
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getexpiresIn() {
        return expiresIn;
    }

    public void setexpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public AccessToken(String accessToken,String expiresIn){
        super();
        this.accessToken = accessToken;
        this.expiresIn = Long.valueOf(expiresIn);
    }

    /**
     * 判断token是否过期
     * @return
     */
    public boolean isExpired(){
        return System.currentTimeMillis()>=expiresIn;
    }
}
