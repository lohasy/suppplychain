package com.jeeplus.modules.bank.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.bank.service.BankService;
import com.jeeplus.modules.clearBranch.service.ClearBranchService;
import com.jeeplus.modules.cyl.bean.Account_detailed;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Clear_branch_info;
import com.jeeplus.modules.cyl.bean.Contract_info;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.cyl.bean.Financing_info;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Supplier_user;
import com.jeeplus.modules.cyl.dao.Account_detailedDao;
import com.jeeplus.modules.cyl.dao.Bill_infoDao;
import com.jeeplus.modules.cyl.dao.Contract_infoDao;
import com.jeeplus.modules.cyl.dao.Enterprise_paramsDao;
import com.jeeplus.modules.cyl.dao.Supplier_userDao;
import com.jeeplus.modules.iim.dao.MailDao;
import com.jeeplus.modules.iim.entity.Mail;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.sys.entity.User;

import fangfangrj.RandomUtil;
import fangfangrj.Utils;

/**
 * 银行controller
 * @author tuowei
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/bank")
public class BankController extends BaseController{

	@Autowired
	private BankService bankService;
	
	@Autowired
	private GysService gysservice;
	
	@Autowired
	private ClearBranchService clearBranchService;
	
	@Autowired
	private Bill_infoDao bill_infoDao;
	
	@Autowired
	private Enterprise_paramsDao enterprise_paramsDao;
	
	@Autowired
	private Account_detailedDao account_detailedDao;
	
	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private MailDao mailDao;
	
	@Autowired
	private Supplier_userDao supplierUserDao;
	
	@Autowired
	private Contract_infoDao contractInfoDao;
	
	/**
	 * 银行管理
	 * @return
	 */
	@RequestMapping(value = {"bankList", ""})
	public String bankList(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, Model model){
		//查询全部融资信息
		String state = request.getParameter("state");//获取融资状态
		if(!Utils.isEmpty(state)){
			financing_info.setState(state);
		}
		Page<Financing_info> page = bankService.findFinancing(new Page<Financing_info>(request, response), financing_info);
		model.addAttribute("page", page);
		return "modules/bank/bankListFinancing";
	}
	
	/**
	 * 跳转到融资配置页面
	 * @param financing_info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(Financing_info financing_info, Model model) {
		financing_info = bankService.get(financing_info);
		model.addAttribute("financing_info", financing_info);
		return "modules/bank/bankFinancingForm";
	}
	
	/**
	 * 保存配置
	 * @param dict
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(Financing_info financing_info, Model model, RedirectAttributes redirectAttributes) {
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			bankService.bankupdate(financing_info);
			addMessage(redirectAttributes, "保存配置信息成功！");
		}else {
			addMessage(redirectAttributes, "缺少融资ID标识，无法保存配置！");
		}
		return "redirect:" + adminPath + "/bank/?repage&state=4";
	}
	
	/**
	 * 审核-跳转到融资详情页面
	 * @param financing_info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toFinancingDetail")
	public String toFinancingDetail(Financing_info financing_info, Model model,HttpServletRequest request) {
		String flag = request.getParameter("flag");
		if(!Utils.isEmpty(flag)){
			model.addAttribute("flag", flag);
		}
		financing_info = bankService.get(financing_info);
		Bill_info billInfo = financing_info.getBillId();
		if(financing_info != null) {
			request.getSession().setAttribute("rzid", financing_info.getId());
		}
		model.addAttribute("financing_info", financing_info);
		model.addAttribute("billInfo", billInfo);
		return "modules/bank/financingDetail";
	}
	
	/**
	 * 审核-跳转到供应商详情页面
	 * @param financing_info
	 * @param model
 	 * @return
	 */
	@RequestMapping(value = "toSupplierDetail")
	public String toSupplierDetail(Financing_info financing_info, Model model) {
		financing_info = bankService.get(financing_info);
		Bill_info billInfo = null;
		Supplier_enterprise supplier_enterprise = null;
		Enterprise_params enterprise_params = null;
		if(financing_info != null) {
			billInfo = financing_info.getBillId();
		}
		if(billInfo != null) {
			supplier_enterprise = billInfo.getSupplierEnterpriseId();
			if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
				supplier_enterprise.getParamsId().setAllQuota("0");
			}
			supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
		}
		if(supplier_enterprise != null) {
			enterprise_params = supplier_enterprise.getParamsId();
		}
		
		//查询征信报告查询授权书
		Contract_info ht = new Contract_info();
		ht.setFinancingId(financing_info);
		List<Contract_info> htLists = contractInfoDao.findList(ht);
		List<Contract_info> ht1 = null;
		if(htLists != null && htLists.size() > 0) {
			ht1 = new LinkedList<Contract_info>();
			for(Contract_info ci : htLists) {
				if("2".equals(ci.getType())) {
					ht1.add(ci);
				}
			}
		}
		
		model.addAttribute("financing_info", financing_info);
		model.addAttribute("billInfo", billInfo);
		model.addAttribute("supplier_enterprise", supplier_enterprise);
		model.addAttribute("enterprise_params", enterprise_params);
		model.addAttribute("zxContract", ht1);
		return "modules/bank/supplierDetail";
	}
	
	/**
	 * 审核/复核
	 * @param dict
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value = "examine")
	public String examine(Financing_info financing_info, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IllegalStateException, IOException {
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			MailCompose mailCompose = new MailCompose();
			Mail mail = new Mail();
			mailCompose.setMail(mail);
			mail.setId(IdGen.uuid());
			if("5".equals(financing_info.getState()) || "6".equals(financing_info.getState())){
				bankService.stateupdate(financing_info);
				//更新对应单据状态
				financing_info = bankService.get(financing_info.getId());
				if(financing_info != null && financing_info.getBillId() != null && !Utils.isEmpty(financing_info.getBillId().getId())) {
					if("5".equals(financing_info.getState())) {
						financing_info.getBillId().setState("7");
						mail.setTitle("银行审核融资未通过！");
						mail.setOverview("银行审核融资未通过！");
						mail.setContent("银行审核融资未通过！");
					}else if("6".equals(financing_info.getState())){
						financing_info.getBillId().setState("8");
						mail.setTitle("银行审核融资通过，待银行复核！");
						mail.setOverview("银行审核融资通过，待银行复核！");
						mail.setContent("银行审核融资通过，待银行复核！");
					}
					bill_infoDao.update(financing_info.getBillId());
					
					//发送邮件消息
					if(financing_info.getBillId().getSupplierEnterpriseId() != null) {
						mailDao.insert(mail);
						mailCompose.setStatus("1");
						mailCompose.setReadstatus("1");
						List<User> receiverList = new LinkedList<User>();
						Supplier_user su = new Supplier_user();
						su.setSupplierEnterpriseId(financing_info.getBillId().getSupplierEnterpriseId());
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
				}
				addMessage(redirectAttributes, "审核成功！");
				return "redirect:" + adminPath + "/bank/?repage&state=4";
			}else{
				bankService.stateupdate(financing_info);
				//更新对应单据状态
				financing_info = bankService.get(financing_info.getId());
				if(financing_info != null && financing_info.getBillId() != null && !Utils.isEmpty(financing_info.getBillId().getId())) {
					if("7".equals(financing_info.getState())) {
						financing_info.getBillId().setState("9");
						mail.setTitle("银行复核融资未通过！");
						mail.setOverview("银行复核融资未通过！");
						mail.setContent("银行复核融资未通过！");
					}else if("8".equals(financing_info.getState())) {
						financing_info.getBillId().setState("10");
						mail.setTitle("银行复核融资通过，待银行放款！");
						mail.setOverview("银行复核融资通过，待银行放款！");
						mail.setContent("银行复核融资通过，待银行放款！");
					}
					bill_infoDao.update(financing_info.getBillId());
					
					//发送邮件消息
					if(financing_info.getBillId().getSupplierEnterpriseId() != null) {
						mailDao.insert(mail);
						mailCompose.setStatus("1");
						mailCompose.setReadstatus("1");
						List<User> receiverList = new LinkedList<User>();
						Supplier_user su = new Supplier_user();
						su.setSupplierEnterpriseId(financing_info.getBillId().getSupplierEnterpriseId());
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
				}
				addMessage(redirectAttributes, "复核成功！");
				return "redirect:" + adminPath + "/bank/?repage&state=6";
			}
		}else {
			addMessage(redirectAttributes, "缺少融资ID标识，无法操作！");
			return "redirect:" + adminPath + "/bank/?repage&state=4";
		}
	}
	
	/**
	 * 放款/清分
	 * @param dict
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value = "fangqing")
	public String fangqing(Financing_info financing_info, Clear_branch_info clear_branch_info, @RequestParam("uploadFile") MultipartFile[] uploadfile, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IllegalStateException, IOException {
		String realPath = Global.USERFILES_BASE_URL
        		+ "" + "/rzgl/images/" ;
		
		MailCompose mailCompose = new MailCompose();
		Mail mail = new Mail();
		mailCompose.setMail(mail);
		mail.setId(IdGen.uuid());
	
		if("9".equals(financing_info.getState())){
			if(uploadfile.length > 0 && !Utils.isEmpty(uploadfile[0].getOriginalFilename())) {
				String businessfile = uploadfile[0].getOriginalFilename();
				businessfile = businessfile.substring(0, businessfile.indexOf(".")) + RandomUtil.generateString(20) + businessfile.substring(businessfile.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile[0].transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessfile));
				financing_info.setLoanUrl(request.getContextPath() + realPath + businessfile);
			}
			bankService.stateupdate(financing_info);
			financing_info = bankService.get(financing_info.getId());
			if(financing_info != null && financing_info.getBillId() != null && financing_info.getBillId().getSupplierEnterpriseId() != null && financing_info.getBillId().getSupplierEnterpriseId().getParamsId() != null) {
				//更新对应单据状态
				financing_info.getBillId().setState("11");
				bill_infoDao.update(financing_info.getBillId());
				
				//更新供应商融资账户余额
				Enterprise_params params = financing_info.getBillId().getSupplierEnterpriseId().getParamsId();
				if(params != null && !Utils.isEmpty(params.getId())) {
					params.setLoanBalance(financing_info.getLoanAmount());
					enterprise_paramsDao.update(params);
				}
				
				//记录交易记录
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				Account_detailed account = new Account_detailed();
				account.setId(uuid);
				account.setNum(uuid.substring(0, 20));
				account.setAmount(financing_info.getLoanAmount());
				account.setTime(new Date());
				account.setTransactionType("0");
				account.setExplain("供应商接收融资！");
				account.setType("0");
				account.setFinancingId(financing_info);
				account.setSupplierEnterpriseId(financing_info.getBillId().getSupplierEnterpriseId());
				account_detailedDao.insert(account);
				
				//发送邮件消息
				mail.setTitle("融资通过，银行已放款！");
				mail.setOverview("融资通过，银行已放款！");
				mail.setContent("融资通过，银行已放款！");
				if(financing_info.getBillId().getSupplierEnterpriseId() != null) {
					mailDao.insert(mail);
					mailCompose.setStatus("1");
					mailCompose.setReadstatus("1");
					List<User> receiverList = new LinkedList<User>();
					Supplier_user su = new Supplier_user();
					su.setSupplierEnterpriseId(financing_info.getBillId().getSupplierEnterpriseId());
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
			}
			addMessage(redirectAttributes, "放款成功！");
			return "redirect:" + adminPath + "/bank/?repage&state=8";
		}else{
			if(clear_branch_info != null) {
				String businessfile1 = uploadfile[0].getOriginalFilename();
				businessfile1 = businessfile1.substring(0, businessfile1.indexOf(".")) + RandomUtil.generateString(20) + businessfile1.substring(businessfile1.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile[0].transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessfile1));
				clear_branch_info.setPlatformServiceUrl(request.getContextPath() + realPath + businessfile1);
			
				String businessfile2 = uploadfile[1].getOriginalFilename();
				businessfile2 = businessfile2.substring(0, businessfile2.indexOf(".")) + RandomUtil.generateString(20) + businessfile2.substring(businessfile2.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile[1].transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessfile2));
				clear_branch_info.setCoreServiceUrl(request.getContextPath() + realPath + businessfile2);
			
				String businessfile3 = uploadfile[2].getOriginalFilename();
				businessfile3 = businessfile3.substring(0, businessfile3.indexOf(".")) + RandomUtil.generateString(20) + businessfile3.substring(businessfile3.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile[2].transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessfile3));
				clear_branch_info.setSupplierTailUrl(request.getContextPath() + realPath + businessfile3);
			
				String businessfile4 = uploadfile[3].getOriginalFilename();
				businessfile4 = businessfile4.substring(0, businessfile4.indexOf(".")) + RandomUtil.generateString(20) + businessfile4.substring(businessfile4.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile[3].transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessfile4));
				clear_branch_info.setBankMoneyUrl(request.getContextPath() + realPath + businessfile4);
				
				clear_branch_info.setFinancingId(financing_info);
				bankService.stateupdate(financing_info);
				clearBranchService.save(clear_branch_info);
				
				financing_info = bankService.get(financing_info.getId());
				if(financing_info != null && financing_info.getBillId() != null && financing_info.getBillId().getSupplierEnterpriseId() != null && financing_info.getBillId().getSupplierEnterpriseId().getParamsId() != null) {
					//更新对应单据状态
					financing_info.getBillId().setState("13");
					bill_infoDao.update(financing_info.getBillId());
					
					//更新供应商回款账户余额
					Enterprise_params params = financing_info.getBillId().getSupplierEnterpriseId().getParamsId();
					if(params != null && !Utils.isEmpty(params.getId())) {
						params.setReturnBalance(clear_branch_info.getSupplierTailMoney());
						enterprise_paramsDao.update(params);
					}
					
					//记录平台服务费交易记录
					String uuid1 = UUID.randomUUID().toString().replaceAll("-", "");
					Account_detailed account1 = new Account_detailed();
					account1.setId(uuid1);
					account1.setNum(uuid1.substring(0, 20));
					account1.setAmount(clear_branch_info.getPlatformServiceCharge());
					account1.setTime(new Date());
					account1.setTransactionType("0");
					account1.setExplain("清分后平台服务费！");
					account1.setType("0");
					account1.setFinancingId(financing_info);
					account_detailedDao.insert(account1);
					
					//记录核心企业服务费交易记录
					String uuid2 = UUID.randomUUID().toString().replaceAll("-", "");
					Account_detailed account2 = new Account_detailed();
					account2.setId(uuid2);
					account2.setNum(uuid2.substring(0, 20));
					account2.setAmount(clear_branch_info.getCoreServiceCharge());
					account2.setTime(new Date());
					account2.setTransactionType("0");
					account2.setExplain("清分后核心企业服务费！");
					account2.setType("0");
					account2.setFinancingId(financing_info);
					account2.setCoreEnterpriseId(financing_info.getBillId().getCoreEnterpriseId());
					account_detailedDao.insert(account2);
					
					//记录供应商尾款交易记录
					String uuid3 = UUID.randomUUID().toString().replaceAll("-", "");
					Account_detailed account3 = new Account_detailed();
					account3.setId(uuid3);
					account3.setNum(uuid3.substring(0, 20));
					account3.setAmount(clear_branch_info.getSupplierTailMoney());
					account3.setTime(new Date());
					account3.setTransactionType("0");
					account3.setExplain("清分后供应商尾款！");
					account3.setType("0");
					account3.setFinancingId(financing_info);
					account3.setSupplierEnterpriseId(financing_info.getBillId().getSupplierEnterpriseId());
					account_detailedDao.insert(account3);
					
					//记录银行所得款交易记录
					String uuid4 = UUID.randomUUID().toString().replaceAll("-", "");
					Account_detailed account4 = new Account_detailed();
					account4.setId(uuid4);
					account4.setNum(uuid4.substring(0, 20));
					account4.setAmount(clear_branch_info.getBankMoney());
					account4.setTime(new Date());
					account4.setTransactionType("0");
					account4.setExplain("清分后银行所得款！");
					account4.setType("0");
					account4.setFinancingId(financing_info);
					account_detailedDao.insert(account4);
					
					//发送邮件消息
					mail.setTitle("银行已清分！");
					mail.setOverview("银行已清分！");
					mail.setContent("银行已清分！");
					if(financing_info.getBillId().getSupplierEnterpriseId() != null) {
						mailDao.insert(mail);
						mailCompose.setStatus("1");
						mailCompose.setReadstatus("1");
						List<User> receiverList = new LinkedList<User>();
						Supplier_user su = new Supplier_user();
						su.setSupplierEnterpriseId(financing_info.getBillId().getSupplierEnterpriseId());
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
				}
			}
			
			addMessage(redirectAttributes, "清分成功！");
			return "redirect:" + adminPath + "/bank/?repage&state=10";
		}
	}
	
	/**
	 * 跳转到复核页面
	 * @param financing_info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toFuhe")
	public String toFuhe(Financing_info financing_info, Model model) {
		financing_info = bankService.get(financing_info);
		model.addAttribute("financing_info", financing_info);
		return "modules/bank/fuhe";
	}
	
	/**
	 * 跳转到放款页面
	 * @param financing_info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toFangkuan")
	public String toFangkuan(Financing_info financing_info, Model model) {
		financing_info = bankService.get(financing_info);
		model.addAttribute("financing_info", financing_info);
		return "modules/bank/fangkuan";
	}
	
	/**
	 * 跳转到清分页面
	 * @param financing_info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toQingfen")
	public String toQingfen(Financing_info financing_info, Model model) {
		financing_info = bankService.get(financing_info);
		model.addAttribute("financing_info", financing_info);
		return "modules/bank/qingfen";
	}
}
