package com.jeeplus.modules.cyl.bean;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import fangfangrj.Utils;
/**
 * 单据信息
 */
public class Bill_info extends DataEntity<Bill_info>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6127483021893901L;
	
	
	/**
	 * 单据号
	 */
	@ExcelField(title="单据编号", align=2, sort=1)
	private String num;
	
	/**
	 * 合同号
	 */
	@ExcelField(title="合同号", align=2, sort=2)
	private String contractNum;
	
	/**
	 * 单据金额
	 */
	@ExcelField(title="单据金额", align=2, sort=3)
	private String amount;
	
	/**
	 * 单据内容
	 */
	@ExcelField(title="单据内容", align=2, sort=4)
	private String content;
	
	/**
	 * 单据起始日期（格式：yyyy-MM-dd）
	 */
	@ExcelField(title="单据起始日期", align=2, sort=5)
	private Date startDate;
	
	/**
	 * 单据到期日期（格式：yyyy-MM-dd）
	 */
	@ExcelField(title="单据到期日期", align=2, sort=6)
	private Date endDate;
	
	/**
	 * 融资金额
	 */
	@ExcelField(title="融资金额", align=2, sort=7)
	private String financingAmount;
	
	/**
	 * 预计融资成本
	 */
	@ExcelField(title="预计融资成本", align=2, sort=8)
	private String planFinancingCost;
	
	/**
	 * 单据状态（0：待提交；1：待供应商融资；2：待负责人审核；3：待签约；4：负责人审核不通过；5：待银行配置；6：待银行审核；7：银行审核不通过；8：待银行复核；9：银行复核不通过；10：待银行放款；11：已放款；12：待银行清分；13：已完成）
	 */
	@ExcelField(title="单据状态", align=2, sort=9, dictType="")
	private String state;
	
	/**
	 * 供应商
	 */
	@ExcelField(title="供应商标识", align=2, sort=10, value="supplierEnterpriseId.id")
	private Supplier_enterprise supplierEnterpriseId;
	
	/**
	 * 核心企业
	 */
	private Core_enterprise coreEnterpriseId;
	
	/**
	 * 上级供应商（相当于核心企业）
	 */
	private Supplier_enterprise supplierParentId;
	
	/**
	 * 核心企业是否上传凭证
	 */
	private String hxqyIsUploadVoucher;
	
	/**
	 * 核心企业凭证数量
	 */
	private String hxqyVoucherCount;
	
	/**
	 * 供应商是否上传凭证
	 */
	private String gysIsUploadVoucher;
	
	/**
	 * 供应商凭证数量
	 */
	private String gysVoucherCount;
	
	/**
	 * 检索起始日期
	 */
	private String searchStartDate;
	
	/**
	 * 检索结束日期
	 */
	private String searchEndDate;
	
	/**
	 * 剩余融资天数（单据结束日 - 当前日期）
	 */
	private String dayDiffValue;
	
	/**
	 * 检索凭证
	 */
	private Voucher_info voucher_info;

	
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
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFinancingAmount() {
		return financingAmount;
	}

	public void setFinancingAmount(String financingAmount) {
		this.financingAmount = financingAmount;
	}

	public String getPlanFinancingCost() {
		return planFinancingCost;
	}

	public void setPlanFinancingCost(String planFinancingCost) {
		this.planFinancingCost = planFinancingCost;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
	
	public String getHxqyIsUploadVoucher() {
		return hxqyIsUploadVoucher;
	}

	public void setHxqyIsUploadVoucher(String hxqyIsUploadVoucher) {
		this.hxqyIsUploadVoucher = hxqyIsUploadVoucher;
	}

	public String getHxqyVoucherCount() {
		return hxqyVoucherCount;
	}

	public void setHxqyVoucherCount(String hxqyVoucherCount) {
		this.hxqyVoucherCount = hxqyVoucherCount;
	}

	public String getGysIsUploadVoucher() {
		return gysIsUploadVoucher;
	}

	public void setGysIsUploadVoucher(String gysIsUploadVoucher) {
		this.gysIsUploadVoucher = gysIsUploadVoucher;
	}

	public String getGysVoucherCount() {
		return gysVoucherCount;
	}

	public void setGysVoucherCount(String gysVoucherCount) {
		this.gysVoucherCount = gysVoucherCount;
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

	public String getDayDiffValue() {
		if(startDate != null && endDate != null) {
			Long sdl = Utils.getDateLongByDateStr(new Date());
			Long edl = Utils.getDateLongByDateStr(endDate);
			dayDiffValue = Long.toString(((edl - sdl) / (1000*3600*24)));
		}
		return dayDiffValue;
	}

	public void setDayDiffValue(String dayDiffValue) {
		this.dayDiffValue = dayDiffValue;
	}

	public Voucher_info getVoucher_info() {
		return voucher_info;
	}

	public void setVoucher_info(Voucher_info voucher_info) {
		this.voucher_info = voucher_info;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
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
