package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Enterprise_params;

@MyBatisDao
public interface Enterprise_paramsDao extends CrudDao<Enterprise_params>{
	
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Enterprise_params enterprise_params);
	
}