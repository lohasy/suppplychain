package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
/**
 * 供应商与用户关系
 */
public class Supplier_user extends DataEntity<Supplier_user> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8226523036244580462L;

	
	/**
	 * 供应商
	 */
	private Supplier_enterprise supplierEnterpriseId;
	
	/**
	 * 用户
	 */
	private User userId;
	

	public Supplier_enterprise getSupplierEnterpriseId() {
		return supplierEnterpriseId;
	}

	public void setSupplierEnterpriseId(Supplier_enterprise supplierEnterpriseId) {
		this.supplierEnterpriseId = supplierEnterpriseId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
