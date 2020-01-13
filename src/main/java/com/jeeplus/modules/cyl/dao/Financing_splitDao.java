package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Financing_split;

@MyBatisDao
public interface Financing_splitDao extends CrudDao<Financing_split> {

	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Financing_split financing_split);
	
	/**
	 * 根据单据批量删除
	 * @param financing_split
	 * @return
	 */
	public Long deleteByBill(Financing_split financing_split);
	
}
