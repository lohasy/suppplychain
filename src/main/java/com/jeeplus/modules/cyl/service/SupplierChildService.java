package com.jeeplus.modules.cyl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Supplier_child;
import com.jeeplus.modules.cyl.dao.Supplier_childDao;

/**
 * 供应商与子级供应商关系业务类
 * @author LGT
 */
@Service
@Transactional(readOnly = true)
public class SupplierChildService extends CrudService<Supplier_childDao, Supplier_child> {

	public Supplier_child get(String id) {
		return dao.get(id);
	}
	
	public Page<Supplier_child> find(Page<Supplier_child> page, Supplier_child supplier_child) {
		supplier_child.setPage(page);
		page.setList(dao.findList(supplier_child));
		return page;
	}
	
	public Long findCount(Supplier_child supplier_child) {
		return dao.findCount(supplier_child);
	}
	
	@Transactional(readOnly = false)
	public void insert(Supplier_child supplier_child) {
		dao.insert(supplier_child);
	}
	
	@Transactional(readOnly = false)
	public void update(Supplier_child supplier_child) {
		dao.update(supplier_child);
	}
	
	@Transactional(readOnly = false)
	public void delete(Supplier_child supplier_child) {
		dao.delete(supplier_child);
	}
	
}