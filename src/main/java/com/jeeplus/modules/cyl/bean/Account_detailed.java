package com.jeeplus.modules.cyl.bean;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 账户明细
 */
public class Account_detailed extends DataEntity<Account_detailed>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8690580744532511986L;

	
	/**
	 * 交易明细号
	 */
	private String num;

	/**
	 * 交易金额
	 */
	private String amount;

	/**
	 * 交易时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private Date time;

	/**
	 * 交易类型（0：收入；1：支出）
	 */
	private String transactionType;

	/**
	 * 交易说明
	 */
	private String explain;

	/**
	 * 账户类型（0：放款账户；1：回款账户）
	 */
	private String type;

	/**
	 * 对应融资
	 */
	private Financing_info financingId;

	/**
	 * 供应商
	 */
	private Supplier_enterprise supplierEnterpriseId;

	/**
	 * 核心企业
	 */
	private Core_enterprise coreEnterpriseId;
	
	/**
	 * 起始日期
	 */
	private String beginDate;
	
	/**
	 * 结束日期
	 */
	private String endDate;
	

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Financing_info getFinancingId() {
		return financingId;
	}

	public void setFinancingId(Financing_info financingId) {
		this.financingId = financingId;
	}

	public Supplier_enterprise getSupplierEnterpriseId() {
		return supplierEnterpriseId;
	}

	public void setSupplierEnterpriseId(Supplier_enterprise supplierEnterpriseId) {
		this.supplierEnterpriseId = supplierEnterpriseId;
	}

	public Core_enterprise getCoreEnterpriseId() {
		return coreEnterpriseId;
	}

	public void setCoreEnterpriseId(Core_enterprise coreEnterpriseId) {
		this.coreEnterpriseId = coreEnterpriseId;
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
