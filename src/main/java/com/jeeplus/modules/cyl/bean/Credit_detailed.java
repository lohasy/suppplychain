package com.jeeplus.modules.cyl.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
/**
 * 授信记录明细
 */
public class Credit_detailed extends DataEntity<Credit_detailed> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4061711054828392716L;

	
	/**
	 * 总额度
	 */
	private String allQuota;
	
	/**
	 * 可用额度
	 */
	private String availableQuota;
	
	/**
	 * 利率
	 */
	private String interestRate;
	
	/**
	 * 融资比例
	 */
	private String financingRatio;
	
	/**
	 * 授信人
	 */
	private User userId;
	
	/**
	 * 授信时间
	 */
	private Date time;
	
	/**
	 * 企业参数
	 */
	private Enterprise_params paramsId;
	
	/**
	 * 检索起始日期
	 */
	private String beginDate;
	
	/**
	 * 检索结束日期
	 */
	private String endDate;

	
	public String getAllQuota() {
		return allQuota;
	}

	public void setAllQuota(String allQuota) {
		this.allQuota = allQuota;
	}

	public String getAvailableQuota() {
		return availableQuota;
	}

	public void setAvailableQuota(String availableQuota) {
		this.availableQuota = availableQuota;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getFinancingRatio() {
		return financingRatio;
	}

	public void setFinancingRatio(String financingRatio) {
		this.financingRatio = financingRatio;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Enterprise_params getParamsId() {
		return paramsId;
	}

	public void setParamsId(Enterprise_params paramsId) {
		this.paramsId = paramsId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
