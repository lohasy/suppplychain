package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Core_enterprise;
@MyBatisDao
public interface Core_enterpriseDao extends CrudDao<Core_enterprise> {
    
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Core_enterprise core_enterprise);
	
}