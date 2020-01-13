package com.jeeplus.modules.cyl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Core_supplier;
import com.jeeplus.modules.cyl.dao.Core_supplierDao;

/**
 * 核心企业与供应商关系业务类
 * @author LGT
 */
@Service
@Transactional(readOnly = true)
public class CoreSupplierService extends CrudService<Core_supplierDao, Core_supplier> {

	public Core_supplier get(String id) {
		return dao.get(id);
	}
	
	public Page<Core_supplier> find(Page<Core_supplier> page, Core_supplier core_supplier) {
		core_supplier.setPage(page);
		page.setList(dao.findList(core_supplier));
		return page;
	}
	
	public Long findCount(Core_supplier core_supplier) {
		return dao.findCount(core_supplier);
	}
	
	@Transactional(readOnly = false)
	public void insert(Core_supplier core_supplier) {
		dao.insert(core_supplier);
	}
	
	@Transactional(readOnly = false)
	public void update(Core_supplier core_supplier) {
		dao.update(core_supplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(Core_supplier core_supplier) {
		dao.delete(core_supplier);
	}
	
}
