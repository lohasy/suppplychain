package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 融资拆分实体
 * @author LGT
 */
public class Financing_split  extends DataEntity<Financing_split> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8683170039014880721L;

	
	/**
	 * 拆分金额
	 */
	private String amount;
	
	/**
	 * 下级供应商
	 */
	private Supplier_enterprise supplierChildId;
	
	/**
	 * 所属单据
	 */
	private Bill_info billId;
	

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Supplier_enterprise getSupplierChildId() {
		return supplierChildId;
	}

	public void setSupplierChildId(Supplier_enterprise supplierChildId) {
		this.supplierChildId = supplierChildId;
	}

	public Bill_info getBillId() {
		return billId;
	}

	public void setBillId(Bill_info billId) {
		this.billId = billId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
