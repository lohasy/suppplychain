package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 协议管理
 */
public class Protocol_info extends DataEntity<Protocol_info> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2161008137435740810L;

	
	/**
	 * 协议名称
	 */
	private String name;
	
	/**
	 * 协议地址
	 */
	private String url;
	
	/**
	 * 协议模板
	 */
	private String template;
	
	/**
	 * 所属融资
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
	 * 上级供应商（相当于核心企业）
	 */
	private Supplier_enterprise supplierParentId;
	
	/**
	 * 协议类型（0：核心企业协议；1：供应商协议；2：平台协议；3：其他协议）
	 */
	private String type;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
