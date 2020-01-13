package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;

/**
 * 核心企业与用户关系
 */
public class Core_user extends DataEntity<Core_user> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5375445503398689467L;

	
	/**
	 * 核心企业
	 */
	private Core_enterprise coreEnterpriseId;
	
	/**
	 * 用户
	 */
	private User userId;
	

	public Core_enterprise getCoreEnterpriseId() {
		return coreEnterpriseId;
	}

	public void setCoreEnterpriseId(Core_enterprise coreEnterpriseId) {
		this.coreEnterpriseId = coreEnterpriseId;
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
