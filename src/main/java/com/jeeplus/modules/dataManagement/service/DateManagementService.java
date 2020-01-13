package com.jeeplus.modules.dataManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.dao.Bill_infoDao;

@Service
@Transactional(readOnly = true)
public class DateManagementService extends CrudService<Bill_infoDao, Bill_info>{

	@Autowired
	Bill_infoDao Bill_infoDao;
	public List<Bill_info> getnum(Bill_info bill_info){
		return Bill_infoDao.getnum(bill_info);
	}

}
