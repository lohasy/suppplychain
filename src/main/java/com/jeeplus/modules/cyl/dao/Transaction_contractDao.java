package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Transaction_contract;

@MyBatisDao
public interface Transaction_contractDao extends CrudDao<Transaction_contract> {

	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Transaction_contract transaction_contract);
	
}
