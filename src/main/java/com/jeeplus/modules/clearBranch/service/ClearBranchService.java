package com.jeeplus.modules.clearBranch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Clear_branch_info;
import com.jeeplus.modules.cyl.dao.Clear_branch_infoDao;

/**
 * 清分业务
 * @author tuowei
 *
 */
@Service
@Transactional(readOnly = true)
public class ClearBranchService extends CrudService<Clear_branch_infoDao, Clear_branch_info>{

	@Autowired
	Clear_branch_infoDao clear_branch_infoDao;
}
