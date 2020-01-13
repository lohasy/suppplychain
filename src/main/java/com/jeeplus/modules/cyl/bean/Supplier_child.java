package com.jeeplus.modules.cyl.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;

/**
 * 供应商（相当于核心企业）与下级供应商关系
 * @author LGT
 */
public class Supplier_child extends DataEntity<Supplier_child> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7690648403574430165L;
   
	
	/**
	 * 子级供应商
	 */
	private Supplier_enterprise supplierChildId;
	
	/**
	 * 供应商（相当于核心企业）
	 */
	private Supplier_enterprise supplierEnterpriseId;
	
	/**
	 * 导入时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private Date exportTime;
	
	/**
	 * 邀请时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private Date invitationTime;
	
	/**
	 * 注册时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private Date registerTime;
	
	/**
	 * 状态（0：未邀请；1：已邀请；2：已注册）
	 */
	private String state;
	
	/**
	 * 邀请码
	 */
	private String invitationCode;
	

	public Supplier_enterprise getSupplierEnterpriseId() {
		return supplierEnterpriseId;
	}

	public void setSupplierEnterpriseId(Supplier_enterprise supplierEnterpriseId) {
		this.supplierEnterpriseId = supplierEnterpriseId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExportTime() {
		return exportTime;
	}

	public void setExportTime(Date exportTime) {
		this.exportTime = exportTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInvitationTime() {
		return invitationTime;
	}

	public void setInvitationTime(Date invitationTime) {
		this.invitationTime = invitationTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public Supplier_enterprise getSupplierChildId() {
		return supplierChildId;
	}

	public void setSupplierChildId(Supplier_enterprise supplierChildId) {
		this.supplierChildId = supplierChildId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}