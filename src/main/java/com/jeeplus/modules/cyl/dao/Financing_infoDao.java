package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Financing_info;

import java.util.List;
@MyBatisDao
public interface Financing_infoDao extends CrudDao<Financing_info>{

	/**
	 * 获取数目
	 * @param financing_info
	 * @return
	 */
	public Long findCount(Financing_info financing_info);
	
	/**
	 * 获取融资列表
	 * @param financing_info
	 * @return
	 */
	public List<Financing_info> findFinancingList(Financing_info financing_info);
	
	/**
	 * 融资更新
	 * @param financing_info
	 */
	public void bankupdate(Financing_info financing_info);
	
	/**
	 * 状态更新
	 * @param financing_info
	 */
	public void stateupdate(Financing_info financing_info);

	/**
	 * 获取单据
	 * @param bill_id
	 * @return
	 */
	public Financing_info getBillid(String bill_id);

	/**
	 * 更新融资状态
	 * @param bill_id
	 */
	public void updateFinancingState(String bill_id);


}
