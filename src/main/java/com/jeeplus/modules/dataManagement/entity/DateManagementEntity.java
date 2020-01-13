package com.jeeplus.modules.dataManagement.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

public class DateManagementEntity extends DataEntity<DateManagementEntity>{

	private static final long serialVersionUID = 1L;

	private Integer dataNum;
	private String dataName;
	private Date addTime;
	private String addPeople;
	private Date updateTime;
	private String deleteState;
	private String fileStorage;

	public Integer getDataNum() {
		return dataNum;
	}

	public void setDataNum(Integer dataNum) {
		this.dataNum = dataNum;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddPeople() {
		return addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

	public String getFileStorage() {
		return fileStorage;
	}

	public void setFileStorage(String fileStorage) {
		this.fileStorage = fileStorage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
