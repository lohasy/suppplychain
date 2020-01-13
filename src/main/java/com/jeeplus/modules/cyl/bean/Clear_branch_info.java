package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
/**
 * 清分信息
 */
public class Clear_branch_info extends DataEntity<Clear_branch_info> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2950741866268866843L;

	
	/**
	 * 平台服务费
	 */
	private String platformServiceCharge;
	
	/**
	 * 核心企业服务费
	 */
	private String coreServiceCharge;
	
	/**
	 * 供应商尾款
	 */
	private String supplierTailMoney;
	
	/**
	 * 银行所得款
	 */
	private String bankMoney;
	
	/**
	 * 平台服务费凭证
	 */
	private String platformServiceUrl;
	
	/**
	 * 核心企业服务费凭证
	 */
	private String coreServiceUrl;
	
	/**
	 * 供应商尾款凭证
	 */
	private String supplierTailUrl;
	
	/**
	 * 银行所得款凭证
	 */
	private String bankMoneyUrl;
	
	/**
	 * 所属融资
	 */
	private Financing_info financingId;
	

	public String getPlatformServiceCharge() {
		return platformServiceCharge;
	}

	public void setPlatformServiceCharge(String platformServiceCharge) {
		this.platformServiceCharge = platformServiceCharge;
	}

	public String getCoreServiceCharge() {
		return coreServiceCharge;
	}

	public void setCoreServiceCharge(String coreServiceCharge) {
		this.coreServiceCharge = coreServiceCharge;
	}

	public String getSupplierTailMoney() {
		return supplierTailMoney;
	}

	public void setSupplierTailMoney(String supplierTailMoney) {
		this.supplierTailMoney = supplierTailMoney;
	}

	public String getBankMoney() {
		return bankMoney;
	}

	public void setBankMoney(String bankMoney) {
		this.bankMoney = bankMoney;
	}

	public String getPlatformServiceUrl() {
		return platformServiceUrl;
	}

	public void setPlatformServiceUrl(String platformServiceUrl) {
		this.platformServiceUrl = platformServiceUrl;
	}

	public String getCoreServiceUrl() {
		return coreServiceUrl;
	}

	public void setCoreServiceUrl(String coreServiceUrl) {
		this.coreServiceUrl = coreServiceUrl;
	}

	public String getSupplierTailUrl() {
		return supplierTailUrl;
	}

	public void setSupplierTailUrl(String supplierTailUrl) {
		this.supplierTailUrl = supplierTailUrl;
	}

	public String getBankMoneyUrl() {
		return bankMoneyUrl;
	}

	public void setBankMoneyUrl(String bankMoneyUrl) {
		this.bankMoneyUrl = bankMoneyUrl;
	}

	public Financing_info getFinancingId() {
		return financingId;
	}

	public void setFinancingId(Financing_info financingId) {
		this.financingId = financingId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
