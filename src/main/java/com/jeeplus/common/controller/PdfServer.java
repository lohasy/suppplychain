package com.jeeplus.common.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.rzgl.service.RzglService;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.dao.Bill_infoDao;

import fangfangrj.Utils;


@Service
public class PdfServer {

	
	@Autowired
	Bill_infoDao Bill_infoDao;
	@Autowired
	RzglService rzglservice;
	public Map<String,Object> getMap(Bill_info bill_info){
		Map<String, Object> map = new HashMap<String, Object>();
		Bill_info bill = rzglservice.get(bill_info);
		String id = "";
		String name = "";
		String num = "";
		String amount = "";
		String contractNum = "";
		String startDate = "";
		String endDate = "";
		String loanName = "";
		String loanBank = "";
		String loanAccount = "";
		String cityId = "";
		String agencyPhone = "";
		String agencyEmail = "";
		String agencyName = "";
		String largeSholderName = "";
		if(bill != null) {
		 	id = Utils.isEmpty(bill.getId()) ? "" : bill.getId();
		 	num = Utils.isEmpty(bill.getNum()) ? "" : bill.getNum();
		 	amount = Utils.isEmpty(bill.getAmount()) ? "" : bill.getAmount();
		 	contractNum = Utils.isEmpty(bill.getContractNum()) ? "" : bill.getContractNum();
		 	if(bill.getSupplierEnterpriseId() != null) {
		 		name = Utils.isEmpty(bill.getSupplierEnterpriseId().getName()) ? "" : bill.getSupplierEnterpriseId().getName();
		 		cityId = Utils.isEmpty(bill.getSupplierEnterpriseId().getBusinessAddress()) ? "" : bill.getSupplierEnterpriseId().getBusinessAddress();
		 		agencyPhone = Utils.isEmpty(bill.getSupplierEnterpriseId().getAgencyPhone()) ? "" : bill.getSupplierEnterpriseId().getAgencyPhone();
		 		agencyEmail = Utils.isEmpty(bill.getSupplierEnterpriseId().getAgencyEmail()) ? "" : bill.getSupplierEnterpriseId().getAgencyEmail();
		 		agencyName = Utils.isEmpty(bill.getSupplierEnterpriseId().getAgencyName()) ? "" : bill.getSupplierEnterpriseId().getAgencyName();
		 		largeSholderName = Utils.isEmpty(bill.getSupplierEnterpriseId().getLegalName()) ? "" : bill.getSupplierEnterpriseId().getLegalName();
		 	}
		 	if(bill.getSupplierEnterpriseId() != null && bill.getSupplierEnterpriseId().getParamsId() != null) {
		 		loanName = Utils.isEmpty(bill.getSupplierEnterpriseId().getParamsId().getLoanName()) ? "" : bill.getSupplierEnterpriseId().getParamsId().getLoanName();
		 		loanBank = Utils.isEmpty(bill.getSupplierEnterpriseId().getParamsId().getLoanOpenBank()) ? "" : bill.getSupplierEnterpriseId().getParamsId().getLoanOpenBank();
		 		loanAccount = Utils.isEmpty(bill.getSupplierEnterpriseId().getParamsId().getLoanAccount()) ? "" : bill.getSupplierEnterpriseId().getParamsId().getLoanAccount();
		 	}
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        if(bill.getStartDate() != null) {
	        	startDate = formatter.format(bill.getStartDate());
	        }
	        if(bill.getEndDate() != null) {
	        	endDate = formatter.format(bill.getEndDate());
	        }
		}
		map.put("{ID}", id);
        map.put("{NAME}", name);
        map.put("{LOANBANK}", loanBank);
        map.put("{NUM}", num);
        map.put("{AMOUNT}", amount);
        map.put("{ENDDATE}", endDate);
        map.put("{LOANNAME}", loanName);
        map.put("{LOANACCOUNT}", loanAccount);
        map.put("{CITYID}", cityId);
        map.put("{AGENCYPHONE}", agencyPhone);
        map.put("{AGENCYEMAIL}", agencyEmail);
        map.put("{AGENCYNAME}", agencyName);
        map.put("{STARTDATE}", startDate);
        map.put("{CONTRACTNUM}", contractNum);
        map.put("{LARGESTHOLDERNAME}", largeSholderName);
		return map;
	}
	
	
	public Map<String,Object> getRzMap(Bill_info bill_info){
		Map<String, Object> map = new HashMap<String, Object>();
		Bill_info bill = rzglservice.get(bill_info);
		String id = "";
		String name = "";
		String amount = "";
		String startDate = "";
		String endDate = "";
		String loanName = "";
		String loanBank = "";
		String loanAccount = "";
		String largeSholderName = "";
		if(bill != null) {
		 	amount = Utils.isEmpty(bill.getAmount()) ? "" : bill.getAmount();
		 	if(bill.getCoreEnterpriseId() != null) {
		 		name = Utils.isEmpty(bill.getCoreEnterpriseId().getName()) ? "" : bill.getCoreEnterpriseId().getName();
		 	}
		 	if(bill.getSupplierEnterpriseId() != null) {
		 		id = Utils.isEmpty(bill.getSupplierEnterpriseId().getName()) ? "" : bill.getSupplierEnterpriseId().getName();
		 		largeSholderName = Utils.isEmpty(bill.getSupplierEnterpriseId().getLegalName()) ? "" : bill.getSupplierEnterpriseId().getLegalName();
		 	}
		 	if(bill.getSupplierEnterpriseId() != null && bill.getSupplierEnterpriseId().getParamsId() != null) {
		 		loanName = Utils.isEmpty(bill.getSupplierEnterpriseId().getParamsId().getLoanName()) ? "" : bill.getSupplierEnterpriseId().getParamsId().getLoanName();
		 		loanBank = Utils.isEmpty(bill.getSupplierEnterpriseId().getParamsId().getLoanOpenBank()) ? "" : bill.getSupplierEnterpriseId().getParamsId().getLoanOpenBank();
		 		loanAccount = Utils.isEmpty(bill.getSupplierEnterpriseId().getParamsId().getLoanAccount()) ? "" : bill.getSupplierEnterpriseId().getParamsId().getLoanAccount();
		 	}
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        if(bill.getStartDate() != null) {
	        	startDate = formatter.format(bill.getStartDate());
	        }
	        if(bill.getEndDate() != null) {
	        	endDate = formatter.format(bill.getEndDate());
	        }
		}
		map.put("{ID}", id);
        map.put("{NAME}", name);
        map.put("{LOANBANK}", loanBank);
        map.put("{AMOUNT}", amount);
        map.put("{ENDDATE}", endDate);
        map.put("{LOANNAME}", loanName);
        map.put("{LOANACCOUNT}", loanAccount);
        map.put("{STARTDATE}", startDate);
        map.put("{LARGESTHOLDERNAME}", largeSholderName);
		return map;
	}
}
