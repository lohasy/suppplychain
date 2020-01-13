package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
/**
 * 合同信息
 */
public class Contract_info extends DataEntity<Contract_info> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222314663154133338L;

	
	/**
	 * 合同名称
	 */
	private String name;
	
	/**
	 * 合同地址
	 */
	private String url;
	
	/**
	 * 所属融资
	 */
	private Financing_info financingId;
	
	/**
	 * 合同类型（0：应收账款转让协议；1：融资合同；2：征信报告查询授权书；3：商务合同）
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

	public Financing_info getFinancingId() {
		return financingId;
	}

	public void setFinancingId(Financing_info financingId) {
		this.financingId = financingId;
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
