package com.jeeplus.common.rzgl.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.controller.PdfServer;
import com.jeeplus.common.controller.PdfUitl;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.rzgl.service.QuanBuService;
import com.jeeplus.common.rzgl.service.RzglService;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Contract_info;
import com.jeeplus.modules.cyl.bean.Core_enterprise;
import com.jeeplus.modules.cyl.bean.Financing_info;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Supplier_user;
import com.jeeplus.modules.cyl.dao.Contract_infoDao;
import com.jeeplus.modules.cyl.dao.Supplier_userDao;
import com.jeeplus.modules.iim.dao.MailDao;
import com.jeeplus.modules.iim.entity.Mail;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

import fangfangrj.RandomUtil;
import fangfangrj.Utils;

/**
 * @author Administrator
 * 融资管理全部
 */
@Controller
@RequestMapping(value="${adminPath}/rzglall")
public class QuanBuController extends BaseController{
	
	@Autowired
	private QuanBuService quanbuservice;
	
	@Autowired
	private RzglService rzglservice;
	
	@Autowired
	private PdfServer pdfserver;
	
	@Autowired
	private GysService gysservice;
	
	@Autowired
	private Contract_infoDao contractInfoDao;
	
	@Autowired
	private Supplier_userDao supplierUserDao;
	
	@Autowired
	private MailDao mailDao;
	
	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@ModelAttribute("financing_info")
	public Financing_info get(@RequestParam(required=false) String id) {
		Financing_info entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = quanbuservice.get(id);
		}
		if (entity == null){
			entity = new Financing_info();
		}
		return entity;
	}
	
	
	/**
	 * 
	 * 融资全部页面
	 * @param Financing_Info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Financing_info> page = quanbuservice.findPage(new Page<Financing_info>(request, response), financing_info); 
		model.addAttribute("state", financing_info.getState());
		model.addAttribute("page", page);
		return "platform/financingList";
	}
	
	
	/**
	 * 核心企业---融资管理---融资列表
	 * @param financing_Info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"hxqylist", ""})
	public String rzlblist(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			if(financing_info.getBillId() == null) {
				financing_info.setBillId(new Bill_info());
			}
			if(financing_info.getBillId().getCoreEnterpriseId() == null) {
				financing_info.getBillId().setCoreEnterpriseId(new Core_enterprise());
			}
			financing_info.getBillId().getCoreEnterpriseId().setId(currentUser.getCore().getId());
		}
		Page<Financing_info> page = quanbuservice.findPage(new Page<Financing_info>(request, response), financing_info); 
		model.addAttribute("page", page);
		return "platform/hxqyFinancingManager";
	}
	
	
	/**
	 * 供应商---融资管理---融资列表（供应商作为核心企业）
	 * @param financing_Info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"gysHxqylist", ""})
	public String gysHxqylist(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(financing_info.getBillId() == null) {
				financing_info.setBillId(new Bill_info());
			}
			if(financing_info.getBillId().getSupplierParentId() == null) {
				financing_info.getBillId().setSupplierParentId(new Supplier_enterprise());
			}
			financing_info.getBillId().getSupplierParentId().setId(currentUser.getSupplier().getId());
		}
		Page<Financing_info> page = quanbuservice.findPage(new Page<Financing_info>(request, response), financing_info); 
		model.addAttribute("page", page);
		return "platform/gysHxqyFinancingManager";
	}
	
	
	/**
	 * 供应商---融资管理---融资列表
	 * @param financing_Info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"gyslist", ""})
	public String gyslist(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(financing_info.getBillId() == null) {
				financing_info.setBillId(new Bill_info());
			}
			if(financing_info.getBillId().getSupplierEnterpriseId() == null) {
				financing_info.getBillId().setSupplierEnterpriseId(new Supplier_enterprise());
			}
			financing_info.getBillId().getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			Supplier_enterprise supplier_enterprise = gysservice.get(currentUser.getSupplier().getId());
			if(supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getId())) {
				if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
					supplier_enterprise.getParamsId().setAllQuota("0");
				}
				supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
			}
			String ableFinancingQuota = "0";
			if(gysservice.getAllWaitFinancingAmount(supplier_enterprise).get("waitFinancingAmount") != null) {
				ableFinancingQuota = gysservice.getAllWaitFinancingAmount(supplier_enterprise).get("waitFinancingAmount").toString();
			}
			if(!Utils.isEmpty(ableFinancingQuota) && supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getParamsId().getAvailableQuota())) {
				if(Float.valueOf(ableFinancingQuota).floatValue() > Float.valueOf(supplier_enterprise.getParamsId().getAvailableQuota()).floatValue()) {
					ableFinancingQuota = supplier_enterprise.getParamsId().getAvailableQuota();
				}
			}
			model.addAttribute("supplier_enterprise", supplier_enterprise);
			model.addAttribute("ableFinancingQuota", ableFinancingQuota);
		}
		Page<Financing_info> page = quanbuservice.findPage(new Page<Financing_info>(request, response), financing_info); 
		model.addAttribute("page", page);
		return "platform/gysFinancingManager";
	}
	
	
	/**
	 * 融资申请
	 * @param financing_info
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"financing-apply", ""})
	public String financingApply(@RequestParam("file")MultipartFile[] uploadfile, Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if(financing_info.getBillId() == null || Utils.isEmpty(financing_info.getBillId().getId())) {
			addMessage(redirectAttributes, "缺失单据信息，无法申请！");
			return "redirect:" + adminPath + "/yfzk/gyslist?repage";
		}
		
		//判断单据对应融资是否存在
		List<Financing_info> list = quanbuservice.findList(financing_info);
		if(list != null && list.size() > 0) {
			addMessage(redirectAttributes, "融资单据已存在，无法申请！");
			return "redirect:" + adminPath + "/yfzk/gyslist?repage";
		}
		
		//更新单据
		Bill_info bill = rzglservice.get(financing_info.getBillId().getId());
		if(bill == null || bill.getSupplierEnterpriseId() == null || !"5".equals(bill.getSupplierEnterpriseId().getState())){
			addMessage(redirectAttributes, "您所在的供应商企业暂不具备融资申请资格！");
			return "redirect:" + adminPath + "/yfzk/gyslist?repage";
		}
		
		//更新单据
		bill.setState("2");
		rzglservice.save(bill);
		
		//生成融资
		financing_info.setBillId(bill);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		financing_info.setNum(uuid.substring(0, 20));
		financing_info.setGenerationDate(new Date());
		financing_info.setState("0");
		quanbuservice.save(financing_info);
		
		//存储合同
		String realPath = Global.USERFILES_BASE_URL
        		+ "" + "/rzht/files/" ;
		try {
			if(uploadfile.length > 0 && !Utils.isEmpty(uploadfile[0].getOriginalFilename())) {
				String businessimage = uploadfile[0].getOriginalFilename();
				businessimage = businessimage.substring(0, businessimage.indexOf(".")) + RandomUtil.generateString(20) + businessimage.substring(businessimage.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile[0].transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessimage));
				
				Contract_info ht = new Contract_info();
				String uid = UUID.randomUUID().toString().replaceAll("-", "");
				ht.setId(uid);
				ht.setFinancingId(financing_info);
				ht.setName(uploadfile[0].getOriginalFilename());
				ht.setUrl(request.getContextPath() + realPath + businessimage);
				ht.setType("2");
				contractInfoDao.insert(ht);
			}
			if(uploadfile.length > 1 && !Utils.isEmpty(uploadfile[1].getOriginalFilename())) {
				String LegalCardPositive = uploadfile[1].getOriginalFilename();
				LegalCardPositive = LegalCardPositive.substring(0, LegalCardPositive.indexOf(".")) + RandomUtil.generateString(20) + LegalCardPositive.substring(LegalCardPositive.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile[1].transferTo(new File(Global.getUserfilesBaseDir() + realPath + LegalCardPositive));
				
				Contract_info ht = new Contract_info();
				String uid = UUID.randomUUID().toString().replaceAll("-", "");
				ht.setId(uid);
				ht.setFinancingId(financing_info);
				ht.setName(uploadfile[1].getOriginalFilename());
				ht.setUrl(request.getContextPath() + realPath + LegalCardPositive);
				ht.setType("3");
				contractInfoDao.insert(ht);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			addMessage(redirectAttributes, "融资申请失败！，失败信息："+ e.getMessage());
			return "redirect:" + adminPath + "/yfzk/gyslist?repage";
		}
		
		//向负责人发送邮件消息
		if(bill != null && bill.getSupplierEnterpriseId() != null && !Utils.isEmpty(bill.getSupplierEnterpriseId().getId())) {
			MailCompose mailCompose = new MailCompose();
			Mail mail = new Mail();
			mailCompose.setMail(mail);
			mail.setId(IdGen.uuid());
			mail.setTitle("融资申请待负责人审核！");
			mail.setOverview("融资申请待负责人审核！");
			mail.setContent("融资申请待负责人审核！");
			mailDao.insert(mail);
			mailCompose.setStatus("1");
			mailCompose.setReadstatus("1");
			List<User> receiverList = new LinkedList<User>();
			Supplier_user su = new Supplier_user();
			su.setSupplierEnterpriseId(bill.getSupplierEnterpriseId());
			List<Supplier_user> suList = supplierUserDao.findList(su);
			if(suList != null && suList.size() > 0) {
				for(Supplier_user entity : suList) {
					if(entity != null && entity.getUserId() != null && !Utils.isEmpty(entity.getUserId().getId())) {
						User u = userDao.get(entity.getUserId().getId());
						Role role = new Role();
						role.getSqlMap().put("dsf", BaseService.dataScopeFilter(u, "o", "u"));
						List<Role> rList = roleDao.findList(role);
						String roleName = "";
						if(rList != null && rList.size() == 1) {
							for(Role r : rList) {
								roleName = r.getName();
							}
						}
						if(roleName.contains("供应商") && roleName.contains("负责人")) {
							receiverList.add(entity.getUserId());
						}
					}
				}
			}
			mailCompose.setReceiverList(receiverList);
			mailComposeService.sendMail(mailCompose);
		}
		
		addMessage(redirectAttributes, "融资申请成功，等待负责人审核！");
		return "redirect:" + adminPath + "/yfzk/gyslist?repage";
	}
	
	
	/**
	 * 融资管理--核心企业还款
	 * @return
	 */
	@RequestMapping("/rzglhk")
	public String rzglhk(Financing_info financing_info, Model model) {
		Financing_info financing = null;
		Bill_info bill_info = null;
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			financing = quanbuservice.get(financing_info.getId());
			bill_info = rzglservice.get(financing.getBillId());
		}
		model.addAttribute("financing", financing);
		model.addAttribute("bill", bill_info);
		return "platform/hxqyFinancingRepayment";
	}
	
	
	/**
	 * 融资管理--供应商还款
	 * @return
	 */
	@RequestMapping("/gys-rzglhk")
	public String gysrzglhk(Financing_info financing_info, Model model) {
		Financing_info financing = null;
		Bill_info bill_info = null;
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			financing = quanbuservice.get(financing_info.getId());
			bill_info = rzglservice.get(financing.getBillId());
		}
		model.addAttribute("financing", financing);
		model.addAttribute("bill", bill_info);
		return "platform/gysFinancingRepayment";
	}
	
	
	/**
	 * 跳转至融资审核界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"to-financingExamine", ""})
	public String toFinancingExamine(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			financing_info = quanbuservice.get(financing_info.getId());
			
			Contract_info ht = new Contract_info();
			ht.setFinancingId(financing_info);
			List<Contract_info> htLists = contractInfoDao.findList(ht);
			//征信报告查询授权书
			List<Contract_info> ht1 = null;
			//商务合同
			List<Contract_info> ht2 = null;
			if(htLists != null && htLists.size() > 0) {
				ht1 = new LinkedList<Contract_info>();
				ht2 = new LinkedList<Contract_info>();
				for(Contract_info ci : htLists) {
					if("2".equals(ci.getType())) {
						ht1.add(ci);
					}
					if("3".equals(ci.getType())) {
						ht2.add(ci);
					}
				}
			}
			model.addAttribute("zxContract", ht1);
			model.addAttribute("swContract", ht2);
		}
		model.addAttribute("financing_info", financing_info);
		return "platform/gysFinancingExamine";
	}
	
	
	/**
	 * 融资审核
	 * @param financing_info
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"financing-examine", ""})
	public String financingExamine(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			Financing_info f = quanbuservice.get(financing_info.getId());
			if(Utils.isEmpty(financing_info.getState())) {
				financing_info.setState("2");
			}
			f.setState(financing_info.getState());
			quanbuservice.save(f);
			
			//更新单据状态
			if(f.getBillId() != null && !Utils.isEmpty(f.getBillId().getId())) {
				if("2".equals(financing_info.getState())) {
					f.getBillId().setState("4");
				}else {
					f.getBillId().setState("6");
					
					//发送邮件消息
					MailCompose mailCompose = new MailCompose();
					Mail mail = new Mail();
					mailCompose.setMail(mail);
					mail.setId(IdGen.uuid());
					mail.setTitle("新融资申请，待银行审核！");
					mail.setOverview("新融资申请，待银行审核！");
					mail.setContent("新融资申请，待银行审核！");
					mailDao.insert(mail);
					mailCompose.setStatus("1");
					mailCompose.setReadstatus("1");
					List<User> receiverList = new LinkedList<User>();
					List<User> userList = userDao.findUserListByRoleName("银行");
					if(userList != null && userList.size() > 0) {
						List<String> userIdList = new LinkedList<String>();
						for(User u : userList) {
							if(!userIdList.contains(u.getId())) {
								receiverList.add(u);
								userIdList.add(u.getId());
							}
						}
					}
					mailCompose.setReceiverList(receiverList);
					mailComposeService.sendMail(mailCompose);
				}
				rzglservice.save(f.getBillId());
			}
			addMessage(redirectAttributes, "融资审核成功！");
		}else {
			addMessage(redirectAttributes, "缺少融资ID标识，无法审核！");
		}
		return "redirect:" + adminPath + "/rzglall/gyslist?repage";
	}
	
	
	/**
	 * 跳转至融资签约界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"to-financingContract", ""})
	public String toFinancingContract(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			financing_info = quanbuservice.get(financing_info.getId());
			
			Contract_info ht = new Contract_info();
			ht.setFinancingId(financing_info);
			List<Contract_info> htLists = contractInfoDao.findList(ht);
			//征信报告查询授权书
			List<Contract_info> ht1 = null;
			//商务合同
			List<Contract_info> ht2 = null;
			if(htLists != null && htLists.size() > 0) {
				ht1 = new LinkedList<Contract_info>();
				ht2 = new LinkedList<Contract_info>();
				for(Contract_info ci : htLists) {
					if("2".equals(ci.getType())) {
						ht1.add(ci);
					}
					if("3".equals(ci.getType())) {
						ht2.add(ci);
					}
				}
			}
			model.addAttribute("zxContract", ht1);
			model.addAttribute("swContract", ht2);
		}
		model.addAttribute("financing_info", financing_info);
		return "platform/gysFinancingContract";
	}
	
	
	/**
	 * 融资签约
	 * @param financing_info
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"financing-contract", ""})
	public String financingContract(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if(financing_info != null && !Utils.isEmpty(financing_info.getId())) {
			Financing_info f = quanbuservice.get(financing_info.getId());
			f.setState("4");
			quanbuservice.save(f);
			
			//更新单据状态
			if(f.getBillId() != null && !Utils.isEmpty(f.getBillId().getId())) {
				f.getBillId().setState("6");
				rzglservice.save(f.getBillId());
			}
			addMessage(redirectAttributes, "融资签约成功！");
		}else {
			addMessage(redirectAttributes, "缺少融资ID标识，无法签约！");
		}
		return "redirect:" + adminPath + "/rzglall/gyslist?repage";
	}
	
	
	/**
	 * 跳转详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("redirect-info")
	public String ridInfo(@RequestParam(value="id",required=true) String id, Model model, HttpSession session){
		if(!Utils.isEmpty(id)) {
			Financing_info financing_info = quanbuservice.get(id);
			if(financing_info.getBillId() != null) {
				Bill_info bill_info = rzglservice.get(financing_info.getBillId().getId());
				session.setAttribute("rzid", id);
				model.addAttribute("bill_info", bill_info);
			}
			model.addAttribute("financing_info", financing_info);
		}
		return "platform/financingDetails";
	}
	
	
	/**
	 * 点击详情页上面标签调用
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/redirect")
	public String redirJect(HttpSession session, Model model, @RequestParam(value="type") String type, HttpServletRequest request, HttpServletResponse response) {
		String rzid = (String) session.getAttribute("rzid");
		Financing_info financing_info = quanbuservice.get(rzid);
		Bill_info bill_info = null;
		if(financing_info.getBillId() != null) {
			bill_info = rzglservice.get(financing_info.getBillId().getId());
		}
		if("0".equals(type)) {
			model.addAttribute("bill_info", bill_info);
			model.addAttribute("financing_info", financing_info);
			return "platform/financingDetails";
		}
		else if("1".equals(type)) {
			model.addAttribute("financing_info", financing_info);
			if(null != bill_info) {
				Supplier_enterprise supplier_enterprise = bill_info.getSupplierEnterpriseId();
				if(supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getId())) {
					if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
						supplier_enterprise.getParamsId().setAllQuota("0");
					}
					supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
				}
				model.addAttribute("supplier_enterprise", supplier_enterprise);
			}
			return "platform/gysDetails";
		}
		else if("2".equals(type) || "4".equals(type)) {
			model.addAttribute("financing_info", financing_info);
			if(null != bill_info) {
				Supplier_enterprise supplier_enterprise = bill_info.getSupplierEnterpriseId();
				if(supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getId())) {
					if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
						supplier_enterprise.getParamsId().setAllQuota("0");
					}
					supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
				}
				model.addAttribute("supplier_enterprise", supplier_enterprise);
			}
			model.addAttribute("bill_info", bill_info);
			String realPath = Global.USERFILES_BASE_URL
	        		+ "" + "/download/";
	    	//原来得模板文件
	    	String temname="bill.doc";
	    	//新生成得文件
	    	String newname="new";
	    	
	    	String time = Calendar.getInstance().getTimeInMillis()+"";
		 		Map<String, Object> map = pdfserver.getMap(bill_info);
			 	FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
			PdfUitl.readwriteWord(Global.getUserfilesBaseDir() + realPath+temname,map,Global.getUserfilesBaseDir() + realPath +newname+time+".doc");
			PdfUitl.WordToHtml(Global.getUserfilesBaseDir() + realPath +newname+time+".doc",Global.getUserfilesBaseDir() + realPath 
        		+ "hetong.html");
			model.addAttribute("htm", request.getContextPath() + realPath + "hetong.html");
			PdfUitl.delAllFile(Global.getUserfilesBaseDir()+ realPath + newname+time+".doc");
			if("2".equals(type)) {
				return "platform/accountsContract";
			}else {
				return "modules/bank/accountsContract";
			}
		}
		else {
			model.addAttribute("financing_info", financing_info);
			if(null != bill_info) {
				Supplier_enterprise supplier_enterprise = bill_info.getSupplierEnterpriseId();
				if(supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getId())) {
					if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
						supplier_enterprise.getParamsId().setAllQuota("0");
					}
					supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
				}
				model.addAttribute("supplier_enterprise", supplier_enterprise);
			}
			model.addAttribute("bill_info", bill_info);
			String realPath = Global.USERFILES_BASE_URL
	        		+ "" + "/download/";
	    	//原来得模板文件
	    	String temname="rongzi.doc";
	    	//新生成得文件
	    	String newname="new";
	    	
	    	String time = Calendar.getInstance().getTimeInMillis()+"";
		 		Map<String, Object> map = pdfserver.getRzMap(bill_info);
			 	FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
			PdfUitl.readwriteWord(Global.getUserfilesBaseDir() + realPath+temname,map,Global.getUserfilesBaseDir() + realPath +newname+time+".doc");
			PdfUitl.WordToHtml(Global.getUserfilesBaseDir() + realPath +newname+time+".doc",Global.getUserfilesBaseDir() + realPath 
        		+ "hetong.html");
			model.addAttribute("htm", request.getContextPath() + realPath + "hetong.html");
			PdfUitl.delAllFile(Global.getUserfilesBaseDir()+ realPath + newname + time + ".doc");
			
			if("3".equals(type)) {
				return "platform/financingContract";
			}else {
				return "modules/bank/financingContract";
			}
		}
	}
	
	
	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public void exportFile(Financing_info financing_info, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "融资"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Financing_info> page = quanbuservice.findPage(new Page<Financing_info>(request, response, -1), financing_info);
    		new ExportExcel("融资", Financing_info.class).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出融资信息失败！失败信息："+e.getMessage());
		}
    }
}
