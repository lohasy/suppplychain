package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Core_supplier;

@MyBatisDao
public interface Core_supplierDao extends CrudDao<Core_supplier> {
    
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Core_supplier core_supplier);
	
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