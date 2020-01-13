package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Clear_branch_info;

@MyBatisDao
public interface Clear_branch_infoDao extends CrudDao<Clear_branch_info> {

	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Clear_branch_info clear_branch_info);
	
}
