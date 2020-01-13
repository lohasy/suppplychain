package com.jeeplus.common.rzgl.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Account_detailed;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Core_enterprise;
import com.jeeplus.modules.cyl.bean.Financing_info;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Voucher_info;
import com.jeeplus.modules.cyl.dao.Account_detailedDao;
import com.jeeplus.modules.cyl.dao.Bill_infoDao;
import com.jeeplus.modules.cyl.dao.Voucher_infoDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

@Service
public class RzglService extends CrudService<Bill_infoDao, Bill_info>{
	@Autowired
	Bill_infoDao Bill_infoDao;
	@Autowired
	Account_detailedDao account_detaileddao;
	@Autowired
	private Voucher_infoDao voucher_infodao;
	public List<Bill_info> getnum(Bill_info bill_info){
		return Bill_infoDao.getnum(bill_info);
	}
	public String getUUid() {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
	    return uuid;
	}
	@Transactional(readOnly = false)
	public int updateState(String id) {
		return Bill_infoDao.updateBillState(id);
	}
	
	
	/**
	 * 核心企业--还款业务
	 * @param voucher_info
	 * @param bill_info
	 * @param loanamount
	 * @param parameter
	 */
	@Transactional(readOnly = false)
	public void insetBean(Voucher_info voucher_info, Bill_info bill_info, String loanamount, String parameter) {
		String infouuid = getUUid();
		voucher_info.setId(infouuid);
		voucher_info.setBillId(bill_info);
		voucher_info.setType("3");
		voucher_infodao.insert(voucher_info);
		
		//交易记录ID
		String pzid = getUUid();
		//交易流水号num
		String pznum =getUUid();
		//开始交易记录
		Account_detailed account_detailed = new Account_detailed();
		account_detailed.setId(pzid);
		account_detailed.setNum(pznum.substring(0, 20));
		account_detailed.setAmount(loanamount);
		account_detailed.setTime(new Date());
		account_detailed.setTransactionType("1");
		account_detailed.setExplain("核心企业还款");
		account_detailed.setType("1");
		Financing_info financing_info = new Financing_info();
		financing_info.setId(parameter);;
		account_detailed.setFinancingId(financing_info);
		User currentUser = UserUtils.getUser();
		Core_enterprise core_enterprise = new Core_enterprise();
		if(null != currentUser.getCore()) {
			core_enterprise.setId(currentUser.getCore().getId());
		}
		account_detailed.setCoreEnterpriseId(core_enterprise);
		account_detaileddao.insert(account_detailed);
	}
	
	
	/**
	 * 供应商--还款业务
	 * @param voucher_info
	 * @param bill_info
	 * @param loanamount
	 * @param parameter
	 */
	@Transactional(readOnly = false)
	public void supplierRepayMent(Voucher_info voucher_info, Bill_info bill_info, String loanamount, String parameter) {
		String infouuid = getUUid();
		voucher_info.setId(infouuid);
		voucher_info.setBillId(bill_info);
		voucher_info.setType("3");
		voucher_infodao.insert(voucher_info);
		
		//交易记录ID
		String pzid = getUUid();
		//交易流水号num
		String pznum =getUUid();
		//开始交易记录
		Account_detailed account_detailed = new Account_detailed();
		account_detailed.setId(pzid);
		account_detailed.setNum(pznum.substring(0, 20));
		account_detailed.setAmount(loanamount);
		account_detailed.setTime(new Date());
		account_detailed.setTransactionType("1");
		account_detailed.setExplain("供应商还款");
		account_detailed.setType("1");
		Financing_info financing_info = new Financing_info();
		financing_info.setId(parameter);;
		account_detailed.setFinancingId(financing_info);
		User currentUser = UserUtils.getUser();
		Supplier_enterprise supplier_enterprise = new Supplier_enterprise();
		if(null != currentUser.getSupplier()) {
			supplier_enterprise.setId(currentUser.getSupplier().getId());
		}
		account_detailed.setSupplierEnterpriseId(supplier_enterprise);
		account_detaileddao.insert(account_detailed);
	}
	
}
