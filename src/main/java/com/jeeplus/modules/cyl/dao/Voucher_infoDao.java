package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Voucher_info;
@MyBatisDao
public interface Voucher_infoDao extends CrudDao<Voucher_info> {
	
	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Voucher_info voucher_info);
    
} 