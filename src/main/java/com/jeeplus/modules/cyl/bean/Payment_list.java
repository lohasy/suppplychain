package com.jeeplus.modules.cyl.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
/**
 * 付款清单
 */
public class Payment_list extends DataEntity<Payment_list> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7370413059039584027L;
	
	/**
	 * 付款编号
	 */
	@ExcelField(title="付款编号", align=2, sort=1)
	private String num;
	
	/**
	 * 付款金额
	 */
	@ExcelField(title="付款金额", align=2, sort=2)
	private String amount;
	
	/**
	 * 付款内容
	 */
	@ExcelField(title="付款内容", align=2, sort=3)
	private String content;
	
	/**
	 * 付款时间
	 */
	@ExcelField(title="付款时间", align=2, sort=4)
	private Date paymentTime;
	
	/**
	 * 发票日期
	 */
	@ExcelField(title="发票日期", align=2, sort=5)
	private Date invoiceDate;
	
	/**
	 * 发票代码
	 */
	@ExcelField(title="发票代码", align=2, sort=6)
	private String invoiceCode;
	
	/**
	 * 发票号码
	 */
	@ExcelField(title="发票号码", align=2, sort=7)
	private String invoiceNum;
	
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
	@ExcelField(title="供应商名称", align=2, sort=8)
	private String supplierName;
	
	/**
	 * 单据合同号
	 */
	@ExcelField(title="合同号", align=2, sort=9)
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
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
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

	public String getBillContractNum() {
		if(billId != null) {
			billContractNum = billId.getContractNum();
		}
		return billContractNum;
	}

	public void setBillContractNum(String billContractNum) {
		this.billContractNum = billContractNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
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
