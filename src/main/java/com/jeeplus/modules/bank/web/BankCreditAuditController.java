package com.jeeplus.modules.bank.web;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.bank.service.BankCreditAuditService;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Supplier_user;
import com.jeeplus.modules.cyl.dao.Contract_infoDao;
import com.jeeplus.modules.cyl.dao.Credit_detailedDao;
import com.jeeplus.modules.cyl.dao.Enterprise_paramsDao;
import com.jeeplus.modules.cyl.dao.Financing_infoDao;
import com.jeeplus.modules.cyl.dao.Supplier_enterpriseDao;
import com.jeeplus.modules.cyl.dao.Supplier_userDao;
import com.jeeplus.modules.iim.dao.MailDao;
import com.jeeplus.modules.iim.entity.Mail;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

import fangfangrj.Utils;

import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Contract_info;
import com.jeeplus.modules.cyl.bean.Credit_detailed;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.cyl.bean.Financing_info;

@Controller
@RequestMapping(value = "${adminPath}/BankCreditAudit")
public class BankCreditAuditController extends BaseController {
	
	@Autowired
	private  BankCreditAuditService bankCreditAuditService;
	
	@Autowired
	private GysService gysservice;
	
	@Autowired
	private Credit_detailedDao credit_detailedDao;
	
	@Autowired
	private Supplier_enterpriseDao supplier_enterpriseDao;
	
	@Autowired
	private Enterprise_paramsDao enterprise_paramsDao;
	
	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private MailDao mailDao;
	
	@Autowired
	private Supplier_userDao supplierUserDao;
	
	@Autowired
	private Contract_infoDao contractInfoDao;
	
	@Autowired
	private Financing_infoDao financing_infoDao;

	
	/**
	 * 平台----供应商列表
	 */
	@RequestMapping(value = { "gys-index", "" })
	public String gyslist(Supplier_enterprise supplier_enterprise, HttpServletRequest request,String state,
			HttpServletResponse response, Model model) {
		supplier_enterprise.setState(state);        // 列表中只显示待审核
		Page<Supplier_enterprise> page = bankCreditAuditService
				.findPage(new Page<Supplier_enterprise>(request, response), supplier_enterprise);		
		model.addAttribute("page", page);
		return "modules/bank/creditAudit";
	}
	
	@RequestMapping(value = "form")
	public String form(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("id", id);
		return "modules/bank/creditAudit/examine";
	}
	
	/**
	 * 授信审核----银行审核通过
	 */
	@RequestMapping(value = "save2")
	@ResponseBody
	public String save2(String supplier_enterpriseid, Enterprise_params enterprise_params, Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {	
		Map<String,String> map = new HashMap<String,String>();
		try{
			if (enterprise_params != null && !"".equals(supplier_enterpriseid)) {
				Supplier_enterprise supplier_enterpriseInDb = bankCreditAuditService.get(supplier_enterpriseid);
				String state = supplier_enterprise.getState();
				MailCompose mailCompose = new MailCompose();
				Mail mail = new Mail();
				mailCompose.setMail(mail);
				mail.setId(IdGen.uuid());
				if (state != null) {
					switch (state) {
						case "审核通过":
							state = "5";
							mail.setTitle("银行授信审核通过！");
							mail.setOverview("银行授信审核通过！");
							mail.setContent("银行授信审核通过！");
							break;
						case "审核不通过":
							state = "6";
							mail.setTitle("银行授信审核未通过！");
							mail.setOverview("银行授信审核未通过！");
							mail.setContent("银行授信审核未通过！");
							break;
					}
				}
				supplier_enterpriseInDb.setState(state);
				if (state == "5") {
					Enterprise_params params = bankCreditAuditService.saveEnterprise_params(enterprise_params);
					supplier_enterpriseInDb.setParamsId(params);
					supplier_enterpriseInDb.setRemarks(supplier_enterprise.getRemarks());
					
					//新增授信记录
					if(params != null && !Utils.isEmpty(params.getId())) {
						String uuid = UUID.randomUUID().toString().replaceAll("-", "");
						Credit_detailed credit_detailed = new Credit_detailed();
						credit_detailed.setParamsId(params);
						credit_detailed.setAllQuota(params.getAllQuota());
						credit_detailed.setAvailableQuota(params.getAvailableQuota());
						credit_detailed.setFinancingRatio(params.getFinancingRatio());
						credit_detailed.setInterestRate(params.getInterestRate());
						credit_detailed.setTime(new Date());
						credit_detailed.setUserId(UserUtils.getUser());
						credit_detailed.setId(uuid);
						credit_detailedDao.insert(credit_detailed);
					}
				}
				bankCreditAuditService.save(supplier_enterpriseInDb);
				
				//发送邮件消息
				mailDao.insert(mail);
				mailCompose.setStatus("1");
				mailCompose.setReadstatus("1");
				List<User> receiverList = new LinkedList<User>();
				Supplier_user su = new Supplier_user();
				su.setSupplierEnterpriseId(supplier_enterpriseInDb);
				List<Supplier_user> suList = supplierUserDao.findList(su);
				if(suList != null && suList.size() > 0) {
					for(Supplier_user entity : suList) {
						if(entity != null && entity.getUserId() != null && !Utils.isEmpty(entity.getUserId().getId())) {
							receiverList.add(entity.getUserId());
						}
					}
				}
				mailCompose.setReceiverList(receiverList);
				mailComposeService.sendMail(mailCompose);
			}		 
			map.put("result", "0");
		}catch(Exception e){
			map.put("result", "1");
			e.printStackTrace();
		}
		String json=JSON.toJSONString(map);				
		return json;
	}
	
	/**
	 * 授信审核----银行审核不通过
	 */
	@RequestMapping(value = "save3")
	@ResponseBody
	public String save3(Supplier_enterprise supplier_enterprise,String remarks, HttpServletRequest request, HttpServletResponse response,
			Model model) {		
		Map<String,String> map = new HashMap<String,String>();
		try{
			if (supplier_enterprise != null && !"".equals(supplier_enterprise.getId())) {
				Supplier_enterprise supplier_enterpriseInDb2 = bankCreditAuditService.get(supplier_enterprise);
				String state = supplier_enterprise.getState();				
				if (state != null && state.equals("审核不通过")) {
					state="6";
					supplier_enterpriseInDb2.setState(state);
					supplier_enterpriseInDb2.setRemarks(remarks);
					bankCreditAuditService.save(supplier_enterpriseInDb2);	
				}
				
				//发送邮件消息
				MailCompose mailCompose = new MailCompose();
				Mail mail = new Mail();
				mailCompose.setMail(mail);
				mail.setId(IdGen.uuid());
				mail.setTitle("银行授信审核未通过！");
				mail.setOverview("银行授信审核未通过！");
				mail.setContent("银行授信审核未通过！");
				mailDao.insert(mail);
				mailCompose.setStatus("1");
				mailCompose.setReadstatus("1");
				List<User> receiverList = new LinkedList<User>();
				Supplier_user su = new Supplier_user();
				su.setSupplierEnterpriseId(supplier_enterpriseInDb2);
				List<Supplier_user> suList = supplierUserDao.findList(su);
				if(suList != null && suList.size() > 0) {
					for(Supplier_user entity : suList) {
						if(entity != null && entity.getUserId() != null && !Utils.isEmpty(entity.getUserId().getId())) {
							receiverList.add(entity.getUserId());
						}
					}
				}
				mailCompose.setReceiverList(receiverList);
				mailComposeService.sendMail(mailCompose);
			}
			map.put("result", "0");
		}catch(Exception e){
			map.put("result", "1");
			e.printStackTrace();
		}
		String json=JSON.toJSONString(map);
		return json;
	}
	
	/**
	 * 授信审核----供应商详情
	 */
	@RequestMapping(value = "gys-info")
	public String gysAuditDetails(String id, HttpServletRequest request, HttpServletResponse response, Model model) {		
		Supplier_enterprise	 supplier_enterprise = bankCreditAuditService.get(id);
		if(supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getParamsId().getId())) {
			if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
				supplier_enterprise.getParamsId().setAllQuota("0");
			}
			supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
			
			//查询所有征信报告授权书
			Financing_info financing = new Financing_info();
			Bill_info bill = new Bill_info();
			bill.setSupplierEnterpriseId(supplier_enterprise);
			financing.setBillId(bill);
			List<Financing_info> financingList = financing_infoDao.findList(financing);
			if(financingList != null && financingList.size() > 0) {
				List<Contract_info> zxContracts = null;
				Contract_info ht = new Contract_info();
				for(Financing_info f : financingList) {
					ht.setFinancingId(f);
					List<Contract_info> htLists = contractInfoDao.findList(ht);
					if(htLists != null && htLists.size() > 0) {
						zxContracts = new LinkedList<Contract_info>();
						for(Contract_info ci : htLists) {
							if("2".equals(ci.getType())) {
								zxContracts.add(ci);
							}
						}
					}
				}
				model.addAttribute("zxContract", zxContracts);
			}
			
		}
		model.addAttribute("supplier_enterprise", supplier_enterprise);
		return "modules/bank/creditAudit/gysAuditDetails";
	}
	
	
	/**
	 * 跳转至重新配置授信界面
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdateAudit")
	public String toUpdateAudit(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		Supplier_enterprise supplier_enterprise = null;
		if(!Utils.isEmpty(id)) {
			supplier_enterprise = supplier_enterpriseDao.get(id);
		}
		model.addAttribute("supplier_enterprise", supplier_enterprise);
		return "modules/bank/creditAudit/updateAudit";
	}
	
	
	/**
	 * 重新配置授信
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "updateAudit")
	public String updateAudit(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(supplier_enterprise != null && supplier_enterprise.getParamsId() != null) {
			Enterprise_params entity = supplier_enterprise.getParamsId();
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			if(Utils.isEmpty(entity.getId())) {
				entity.setId(uuid);
				enterprise_paramsDao.insert(entity);
			}else {
				entity = enterprise_paramsDao.get(entity.getId());
				entity.setAllQuota(supplier_enterprise.getParamsId().getAllQuota());
				entity.setAvailableQuota(supplier_enterprise.getParamsId().getAvailableQuota());
				entity.setFinancingRatio(supplier_enterprise.getParamsId().getFinancingRatio());
				entity.setInterestRate(supplier_enterprise.getParamsId().getInterestRate());
				entity.setLoanAccount(supplier_enterprise.getParamsId().getLoanAccount());
				entity.setLoanName(supplier_enterprise.getParamsId().getLoanName());
				entity.setLoanOpenBank(supplier_enterprise.getParamsId().getLoanOpenBank());
				entity.setReturnAccount(supplier_enterprise.getParamsId().getReturnAccount());
				entity.setReturnName(supplier_enterprise.getParamsId().getReturnName());
				entity.setReturnOpenBank(supplier_enterprise.getParamsId().getReturnOpenBank());
				enterprise_paramsDao.update(entity);
			}
			if(!Utils.isEmpty(supplier_enterprise.getId())) {
				supplier_enterprise = supplier_enterpriseDao.get(supplier_enterprise.getId());
				if(supplier_enterprise != null && (supplier_enterprise.getParamsId() == null || Utils.isEmpty(supplier_enterprise.getParamsId().getId()))) {
					supplier_enterprise.setParamsId(entity);
					supplier_enterpriseDao.update(supplier_enterprise);
				}
			}
			
			//新增授信记录
			if(entity != null && !Utils.isEmpty(entity.getId())) {
				String uid = UUID.randomUUID().toString().replaceAll("-", "");
				Credit_detailed credit_detailed = new Credit_detailed();
				credit_detailed.setParamsId(entity);
				credit_detailed.setAllQuota(entity.getAllQuota());
				credit_detailed.setAvailableQuota(entity.getAvailableQuota());
				credit_detailed.setFinancingRatio(entity.getFinancingRatio());
				credit_detailed.setInterestRate(entity.getInterestRate());
				credit_detailed.setTime(new Date());
				credit_detailed.setUserId(UserUtils.getUser());
				credit_detailed.setId(uid);
				credit_detailedDao.insert(credit_detailed);
			}
			
			addMessage(redirectAttributes, "配置授信成功！");
		}else {
			addMessage(redirectAttributes, "缺少授信数据，无法授信！");
		}
		return "redirect:" + adminPath + "/BankCreditAudit/gys-index/?repage";
	}
	
	
	/**
	 * 跳转至授信参考界面
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toCreditReferenceView")
	public String toCreditReferenceView(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
			supplier_enterprise = bankCreditAuditService.get(supplier_enterprise.getId());
			if(supplier_enterprise.getParamsId() != null) {
				if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
					supplier_enterprise.getParamsId().setAllQuota("0");
				}
				if(Utils.isEmpty(supplier_enterprise.getParamsId().getAvailableQuota())) {
					supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
				}
			}
		}
		model.addAttribute("supplier_enterprise", supplier_enterprise);
		return "modules/bank/creditAudit/toCreditReferenceView";
	}
	
	
	/**
	 * 获取一年之内交易合同统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getTransactionContractStatistics")
	@ResponseBody
	public void getTransactionContractStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryTransactionContractByOneYear(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取一年之内付款清单统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getPaymentListStatistics")
	@ResponseBody
	public void getPaymentListStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryPaymentListByOneYear(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取一年之内入库记录统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getYyrkzlysRecordStatistics")
	@ResponseBody
	public void getYyrkzlysRecordStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryYyrkzlysRecordByOneYear(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取一年之内应收账款统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getBillInfoStatistics")
	@ResponseBody
	public void getBillInfoStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryBillInfoByOneYear(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取一年之内授信记录统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getCreditDetailedStatistics")
	@ResponseBody
	public void getCreditDetailedStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryCreditDetailedByOneYear(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取近三年订单数据统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getNearlyThreeBillStatistics")
	@ResponseBody
	public void getNearlyThreeBillStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryNearlyThreeBillInfos(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取近三年付款数据统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getNearlyThreePaymentStatistics")
	@ResponseBody
	public void getNearlyThreePaymentStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryNearlyThreePayments(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取近三年账期数据统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getNearlyThreeAccountDayStatistics")
	@ResponseBody
	public void getNearlyThreeAccountDayStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryNearlyThreeAccountDays(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取近三年合同数据统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getNearlyThreeContractStatistics")
	@ResponseBody
	public void getNearlyThreeContractStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryNearlyThreeContractInfos(supplier_enterprise));
		}
	}
	
	
	/**
	 * 获取近三年入库数据统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getNearlyThreeYyrkzlysStatistics")
	@ResponseBody
	public void getNearlyThreeYyrkzlysStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, bankCreditAuditService.queryNearlyThreeYyrkzlysInfos(supplier_enterprise));
		}
	}
	
}
