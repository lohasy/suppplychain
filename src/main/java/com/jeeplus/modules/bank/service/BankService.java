package com.jeeplus.modules.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Financing_info;
import com.jeeplus.modules.cyl.dao.Financing_infoDao;

/**
 * 银行业务service
 * @author xiadongye
 *
 */
@Service
@Transactional(readOnly = true)
public class BankService extends CrudService<Financing_infoDao, Financing_info>{

	@Autowired
	private Financing_infoDao financing_infoDao;

	/**
	 * 查询待银行审核融资列表
	 * @param page
	 * @param financing_infoDao
	 * @return
	 */
	public Page<Financing_info> findFinancing(Page<Financing_info> page, Financing_info financing_info) {
		financing_info.setPage(page);
		page.setList(financing_infoDao.findFinancingList(financing_info));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void bankupdate(Financing_info financing_info){
		financing_infoDao.bankupdate(financing_info);
	}
	
	@Transactional(readOnly = false)
	public void stateupdate(Financing_info financing_info){
		financing_infoDao.stateupdate(financing_info);
	}
}
