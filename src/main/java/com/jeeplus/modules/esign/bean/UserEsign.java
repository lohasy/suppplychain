package com.jeeplus.modules.esign.bean;

import java.util.Date;

/**
 * 用户e签宝对应表
 */

public class UserEsign {
	private static final long serialVersionUID = 4061711054828392716L;
	private Integer id;
	private String userId;
	private Integer esignType;
	private String esignId;
	private String seelId;
	private String realNameStatus;
	private Date createdTime;
	private Date updatedTime;
	private Integer valid;


	public UserEsign(Integer id, String userId, Integer esignType, String esignId, String seelId, String realNameStatus, Date createdTime, Date updatedTime, Integer valid) {
		this.id = id;
		this.userId = userId;
		this.esignType = esignType;
		this.esignId = esignId;
		this.seelId = seelId;
		this.realNameStatus = realNameStatus;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.valid = valid;
	}

	public UserEsign() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getEsignType() {
		return esignType;
	}

	public void setEsignType(Integer esignType) {
		this.esignType = esignType;
	}

	public String getEsignId() {
		return esignId;
	}

	public void setEsignId(String esignId) {
		this.esignId = esignId;
	}

	public String getSeelId() {
		return seelId;
	}

	public void setSeelId(String seelId) {
		this.seelId = seelId;
	}

	public String getRealNameStatus() {
		return realNameStatus;
	}

	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
}
