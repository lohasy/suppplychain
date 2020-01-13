package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Credit_detailed;

@MyBatisDao
public interface Credit_detailedDao extends CrudDao<Credit_detailed> {

	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Credit_detailed credit_detailed);
	
}
