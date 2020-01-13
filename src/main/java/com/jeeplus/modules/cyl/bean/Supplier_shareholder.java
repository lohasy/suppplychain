package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
/**
 * 供应商股东信息
 */
public class Supplier_shareholder extends DataEntity<Supplier_shareholder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4943621530131161158L;

	
	/**
	 * 股东姓名
	 */
	private String name;
	
	/**
	 * 股东身份证号
	 */
	private String idNum;
	
	/**
	 * 股东占股比例
	 */
	private String ratio;
	
	/**
	 * 供应商
	 */
	private Supplier_enterprise supplierEnterpriseId;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public Supplier_enterprise getSupplierEnterpriseId() {
		return supplierEnterpriseId;
	}

	public void setSupplierEnterpriseId(Supplier_enterprise supplierEnterpriseId) {
		this.supplierEnterpriseId = supplierEnterpriseId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
