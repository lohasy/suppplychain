package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Core_user;

@MyBatisDao
public interface Core_userDao extends CrudDao<Core_user> {
    
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Core_user core_user);

	/**
	 * 根据核心企业删除关系
	 * @param core_id
	 */
	public void deleteCore(String core_id);
	
	/**
	 * 根据用户删除关系
	 * @param user_id
	 */
	public void deleteByUser(String user_id);
	
}