package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Supplier_user;

@MyBatisDao
public interface Supplier_userDao extends CrudDao<Supplier_user> {
   
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Supplier_user supplier_user);
	
	/**
	 * 根据供应商删除关系
	 * @param core_id
	 */
	public void deleteBySupplier(String supplier_id);
	
	/**
	 * 根据用户删除关系
	 * @param user_id
	 */
	public void deleteByUser(String user_id);
	
}