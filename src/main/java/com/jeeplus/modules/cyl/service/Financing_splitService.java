package com.jeeplus.modules.cyl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Financing_split;
import com.jeeplus.modules.cyl.dao.Financing_splitDao;

/**
 * 融资拆分Service
 * @author LGT
 */
@Service
@Transactional(readOnly = true)
public class Financing_splitService extends CrudService<Financing_splitDao, Financing_split> {

	public Financing_split get(String id) {
		return dao.get(id);
	}
	
	public Page<Financing_split> find(Page<Financing_split> page, Financing_split financing_split) {
		financing_split.setPage(page);
		page.setList(dao.findList(financing_split));
		return page;
	}
	
	public Long findCount(Financing_split financing_split) {
		return dao.findCount(financing_split);
	}
	
	@Transactional(readOnly = false)
	public void insert(Financing_split financing_split) {
		dao.insert(financing_split);
	}
	
	@Transactional(readOnly = false)
	public void update(Financing_split financing_split) {
		dao.update(financing_split);
	}
	
	@Transactional(readOnly = false)
	public void delete(Financing_split financing_split) {
		dao.delete(financing_split);
	}
	
	/**
	 * 根据单据批量删除
	 * @param financing_split
	 */
	@Transactional(readOnly = false)
	public void deleteByBill(Financing_split financing_split) {
		dao.deleteByBill(financing_split);
	}
	
}
