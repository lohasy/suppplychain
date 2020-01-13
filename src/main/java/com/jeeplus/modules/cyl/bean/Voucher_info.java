package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
/**
 * 凭证信息
 */
public class Voucher_info extends DataEntity<Voucher_info>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8934593448631532742L;
	
	
	/**
	 * 凭证名称
	 */
	private String name;
	
	/**
	 * 凭证地址
	 */
	private String url;
	
	/**
	 * 所属单据
	 */
	private Bill_info billId;
	
	/**
	 * 凭证类型（0：应付账款；1：应收账款；2：还款凭证）
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

	public Bill_info getBillId() {
		return billId;
	}

	public void setBillId(Bill_info billId) {
		this.billId = billId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
