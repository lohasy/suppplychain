package com.jeeplus.common.paramservice;

import org.springframework.stereotype.Service;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.cyl.dao.Enterprise_paramsDao;

@Service
public class EnterpriseParamService extends CrudService<Enterprise_paramsDao, Enterprise_params>{

}
