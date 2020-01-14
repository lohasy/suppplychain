package com.jeeplus.modules.esign.bean;

/**
 * 签署流程创建 参数
 */
public class CerateSingFlowsParm {

    private class ConfigInfo {
        private String noticeDeveloperUrl; //回调通知地址 ,默认取项目配置通知地址
        private String noticeType; //通知方式，逗号分割，1-短信，2-邮件 。默认值1，请务必请选择一个通知方式，否则客户将接收不到流程的签署通知和审批通知，如果流程需要审批，将导致审批无法完成；如果客户需要不通知，可以设置noticeType=""
        private String redirectUrl; //重定向地址
        private String signPlatform;  //签署平台，逗号分割，1-开放服务h5，2-支付宝签 ，默认值1

        public String getNoticeDeveloperUrl() {
            return noticeDeveloperUrl;
        }

        public void setNoticeDeveloperUrl(String noticeDeveloperUrl) {
            this.noticeDeveloperUrl = noticeDeveloperUrl;
        }

        public String getNoticeType() {
            return noticeType;
        }

        public void setNoticeType(String noticeType) {
            this.noticeType = noticeType;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getSignPlatform() {
            return signPlatform;
        }

        public void setSignPlatform(String signPlatform) {
            this.signPlatform = signPlatform;
        }
    }
    private boolean autoArchive; //是否自动归档，默认false；
    private String businessScene; //文件主题
    private long contractValidity; //文件有效截止日期,毫秒
    private long contractRemind; //文文件到期前，提前多少小时回调提醒续签，小时
    private long signValidity;//签署有效截止日期,毫秒
    private String initiatorAccountId;//发起人账户id，即发起本次签署的操作人个人账号id；如不传，默认由对接平台发起
    private String initiatorAuthorizedAccountId;//发起方主体id，如存在个人代机构发起签约，则需传入机构id；如不传，则默认是对接平台
    private ConfigInfo configInfo; //任务配置信息

    public ConfigInfo getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    public boolean isAutoArchive() {
        return autoArchive;
    }

    public void setAutoArchive(boolean autoArchive) {
        this.autoArchive = autoArchive;
    }

    public String getBusinessScene() {
        return businessScene;
    }

    public void setBusinessScene(String businessScene) {
        this.businessScene = businessScene;
    }

    public long getContractValidity() {
        return contractValidity;
    }

    public void setContractValidity(long contractValidity) {
        this.contractValidity = contractValidity;
    }

    public long getContractRemind() {
        return contractRemind;
    }

    public void setContractRemind(long contractRemind) {
        this.contractRemind = contractRemind;
    }

    public long getSignValidity() {
        return signValidity;
    }

    public void setSignValidity(long signValidity) {
        this.signValidity = signValidity;
    }

    public String getInitiatorAccountId() {
        return initiatorAccountId;
    }

    public void setInitiatorAccountId(String initiatorAccountId) {
        this.initiatorAccountId = initiatorAccountId;
    }

    public String getInitiatorAuthorizedAccountId() {
        return initiatorAuthorizedAccountId;
    }

    public void setInitiatorAuthorizedAccountId(String initiatorAuthorizedAccountId) {
        this.initiatorAuthorizedAccountId = initiatorAuthorizedAccountId;
    }
}