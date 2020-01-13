package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Supplier_shareholder;

@MyBatisDao
public interface Supplier_shareholderDao extends CrudDao<Supplier_shareholder> {

	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Supplier_shareholder supplier_shareholder);
	
	/**
	 * 根据供应商删除股东
	 * @param supplier_id
	 */
	public void deleteBySupplier(String supplier_id);
	
}
