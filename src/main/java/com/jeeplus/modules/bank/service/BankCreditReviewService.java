package com.jeeplus.modules.bank.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.dao.Supplier_enterpriseDao;

@Service
@Transactional  
public class BankCreditReviewService  extends CrudService<Supplier_enterpriseDao, Supplier_enterprise>{

}
