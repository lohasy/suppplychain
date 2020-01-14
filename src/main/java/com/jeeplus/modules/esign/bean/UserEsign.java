package com.jeeplus.modules.esign.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.sys.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
