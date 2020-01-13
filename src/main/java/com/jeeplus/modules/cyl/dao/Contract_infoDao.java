package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Contract_info;

@MyBatisDao
public interface Contract_infoDao extends CrudDao<Contract_info> {

	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Contract_info contract_info);
	
}
