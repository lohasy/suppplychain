package com.jeeplus.modules.cyl.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
/**
 * 交易合同
 */
public class Transaction_contract extends DataEntity<Transaction_contract> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220964494014414574L;

	
	/**
	 * 合同编号
	 */
	@ExcelField(title="合同编号", align=2, sort=1)
	private String num;
	
	/**
	 * 合同金额
	 */
	@ExcelField(title="合同金额", align=2, sort=2)
	private String amount;
	
	/**
	 * 合同内容
	 */
	@ExcelField(title="合同内容", align=2, sort=3)
	private String content;
	
	/**
	 * 合同生效时间
	 */
	@ExcelField(title="合同生效时间", align=2, sort=4)
	private Date startTime;
	
	/**
	 * 合同结束时间
	 */
	@ExcelField(title="合同结束时间", align=2, sort=5)
	private Date endTime;
	
	/**
	 * 合同甲方
	 */
	@ExcelField(title="合同甲方", align=2, sort=6)
	private String partyJia;
	
	/**
	 * 合同乙方
	 */
	@ExcelField(title="合同乙方", align=2, sort=7)
	private String partyYi;
	
	/**
	 * 合同丙方
	 */
	@ExcelField(title="合同丙方", align=2, sort=8)
	private String partyBing;
	
	/**
	 * 核心企业
	 */
	private Core_enterprise coreEnterpriseId;
	
	/**
	 * 供应商
	 */
	private Supplier_enterprise supplierEnterpriseId;
	
	/**
	 * 上级供应商（相当于核心企业）
	 */
	private Supplier_enterprise supplierParentId;
	
	/**
	 * 所属单据
	 */
	private Bill_info billId;
	
	/**
	 * 供应商名称
	 */
	@ExcelField(title="供应商名称", align=2, sort=9)
	private String supplierName;
	
	/**
	 * 单据合同号
	 */
	@ExcelField(title="合同号", align=2, sort=10)
	private String billContractNum;
	
	/**
	 * 检索起始日期
	 */
	private String searchStartDate;
	
	/**
	 * 检索结束日期
	 */
	private String searchEndDate;
	

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPartyJia() {
		return partyJia;
	}

	public void setPartyJia(String partyJia) {
		this.partyJia = partyJia;
	}

	public String getPartyYi() {
		return partyYi;
	}

	public void setPartyYi(String partyYi) {
		this.partyYi = partyYi;
	}

	public String getPartyBing() {
		return partyBing;
	}

	public void setPartyBing(String partyBing) {
		this.partyBing = partyBing;
	}

	public Supplier_enterprise getSupplierEnterpriseId() {
		return supplierEnterpriseId;
	}

	public void setSupplierEnterpriseId(Supplier_enterprise supplierEnterpriseId) {
		this.supplierEnterpriseId = supplierEnterpriseId;
	}

	public Bill_info getBillId() {
		return billId;
	}

	public void setBillId(Bill_info billId) {
		this.billId = billId;
	}

	public Core_enterprise getCoreEnterpriseId() {
		return coreEnterpriseId;
	}

	public void setCoreEnterpriseId(Core_enterprise coreEnterpriseId) {
		this.coreEnterpriseId = coreEnterpriseId;
	}

	public String getBillContractNum() {
		if(billId != null) {
			billContractNum = billId.getContractNum();
		}
		return billContractNum;
	}

	public void setBillContractNum(String billContractNum) {
		this.billContractNum = billContractNum;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public Supplier_enterprise getSupplierParentId() {
		return supplierParentId;
	}

	public void setSupplierParentId(Supplier_enterprise supplierParentId) {
		this.supplierParentId = supplierParentId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
