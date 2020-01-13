package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Account_detailed;

@MyBatisDao
public interface Account_detailedDao extends CrudDao<Account_detailed> {
	
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Account_detailed account_detailed);
	
}