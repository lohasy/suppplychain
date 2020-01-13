package com.jeeplus.modules.cyl.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Bill_info;
@MyBatisDao
public interface Bill_infoDao extends CrudDao<Bill_info> {
	
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Bill_info bill_info);
	
	/**
	 * 获取编号
	 * @param bill_info
	 * @return
	 */
	public List<Bill_info> getnum(Bill_info bill_info);
	
	/**
	 * 更新单据状态
	 * @param bill_id
	 * @return
	 */
	public int updateBillState(String bill_id);
	
}