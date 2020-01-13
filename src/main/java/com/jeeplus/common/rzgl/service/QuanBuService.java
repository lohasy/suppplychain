package com.jeeplus.common.rzgl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Financing_info;
import com.jeeplus.modules.cyl.dao.Financing_infoDao;

@Service
public class QuanBuService extends CrudService<Financing_infoDao, Financing_info>{
	
	@Autowired
	Financing_infoDao financing_infodao;
	
	public Financing_info getBillid(String bill_id) {
		Financing_info billid = financing_infodao.getBillid(bill_id);
		return billid;
	}
	@Transactional(readOnly = false)
	public  void updateState(String bill_id) {
		financing_infodao.updateFinancingState(bill_id);
	}
}
