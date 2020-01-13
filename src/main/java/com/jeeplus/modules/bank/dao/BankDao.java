package com.jeeplus.modules.bank.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Financing_info;

/**
 * 银行管理dao
 * @author xiadongye
 *
 */
@MyBatisDao
public interface BankDao extends CrudDao<Financing_info>{

}
