/**
 * 
 */
package com.jeeplus.modules.esign.bean;

/**带页面交互的实名与业务方通信数据模型
 * @author chenxi
 *
 */
public class ContextInfo {
	//发起方业务上下文标识:在异步通知时发送回发起方
     private String  contextId;
     //发起方接收实名认证状态变更通知的地址
     private String  notifyUrl;
     //实名发起来源
     private String  origin;
     //实名结束后页面跳转地址.如果origin=APP,则需在该url参数中以key为esignAppScheme设置APP Scheme参数
     private String  redirectUrl;
     //实名完成是否显示结果页,默认显示
     private Boolean  showResultPage;
	public String getContextId() {
		return contextId;
	}
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public Boolean getShowResultPage() {
		return showResultPage;
	}
	public void setShowResultPage(Boolean showResultPage) {
		this.showResultPage = showResultPage;
	}
	@Override
	public String toString() {
		return "ContextInfo [contextId=" + contextId + ", notifyUrl=" + notifyUrl + ", origin=" + origin
				+ ", redirectUrl=" + redirectUrl + ", showResultPage=" + showResultPage + "]";
	}
    
     
}
