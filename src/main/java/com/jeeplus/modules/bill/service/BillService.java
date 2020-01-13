package com.jeeplus.modules.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.dao.Bill_infoDao;

@Service
public class BillService extends CrudService<Bill_infoDao, Bill_info>{

	@Autowired
	Bill_infoDao Bill_infoDao;
}
