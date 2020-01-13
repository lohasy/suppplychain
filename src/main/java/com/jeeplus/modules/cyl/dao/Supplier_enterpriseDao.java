package com.jeeplus.modules.cyl.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
@MyBatisDao
public interface Supplier_enterpriseDao extends CrudDao<Supplier_enterprise> {
	
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Supplier_enterprise supplier_enterprise);
	
	/**
	 * 查询状态统计
	 * @return
	 */
	public List<Map<String,Object>> selectState();
	
	/**
	 * 根据名称获取供应商
	 * @param name
	 * @return
	 */
	public Supplier_enterprise getName(String name);
}