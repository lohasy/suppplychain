package com.jeeplus.modules.cyl.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Yyrkzlys_record;

@MyBatisDao
public interface Yyrkzlys_recordDao extends CrudDao<Yyrkzlys_record> {

	/**
	 * 获取数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(Yyrkzlys_record yyrkzlys_record);
	
}
