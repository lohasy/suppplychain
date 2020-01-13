package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Supplier_child;

@MyBatisDao
public interface Supplier_childDao extends CrudDao<Supplier_child> {
    
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Supplier_child supplier_child);
	
	/**
	 * 根据核心企业删除关系
	 * @param core_id
	 */
	public void deleteByCore(String core_id);
	
	/**
	 * 根据供应商删除关系
	 * @param supplier_id
	 */
	public void deleteBySupplier(String supplier_id);
	
}
