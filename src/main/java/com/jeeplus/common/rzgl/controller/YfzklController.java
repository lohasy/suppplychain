package com.jeeplus.common.rzgl.controller;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.rzgl.service.QuanBuService;
import com.jeeplus.common.rzgl.service.RzglService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.*;
import com.jeeplus.modules.cyl.dao.Supplier_userDao;
import com.jeeplus.modules.cyl.dao.Voucher_infoDao;
import com.jeeplus.modules.iim.dao.MailDao;
import com.jeeplus.modules.iim.entity.Mail;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import fangfangrj.RandomUtil;
import fangfangrj.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
/**
 * @author Administrator
 *	操作员融资管理
 */
@Controller
@RequestMapping(value="${adminPath}/yfzk")
public class YfzklController extends BaseController{

	@Autowired
	private RzglService rzglservice;
	
	@Autowired
	private QuanBuService quanbuservice;
	
	@Autowired
	private Voucher_infoDao voucher_infodao;
	
	@Autowired
	private GysService gysservice;
	
	@Autowired
	private Supplier_userDao supplierUserDao;
	
	@Autowired
	private MailDao mailDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MailComposeService mailComposeService;
	
	
	@ModelAttribute("bill_info")
	public Bill_info get(@RequestParam(required=false) String id) {
		Bill_info entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rzglservice.get(id);
		}
		if (entity == null){
			entity = new Bill_info();
		}
		return entity;
	}
	
	
	/**
	 * 
	 * 核心企业----应付帐款管理
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			if(bill_info.getCoreEnterpriseId() == null) {
				bill_info.setCoreEnterpriseId(new Core_enterprise());
			}
			bill_info.getCoreEnterpriseId().setId(currentUser.getCore().getId());
		}
		Page<Bill_info> page = rzglservice.findPage(new Page<Bill_info>(request, response), bill_info); 
		model.addAttribute("page", page);
		return "platform/hxqyBillManager";
	}
	
	/**
	 * 
	 * 供应商----应付帐款管理（供应商作为核心企业）
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"gysYfzkList", ""})
	public String gysYfzkList(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(bill_info.getSupplierParentId() == null) {
				bill_info.setSupplierParentId(new Supplier_enterprise());
			}
			bill_info.getSupplierParentId().setId(currentUser.getSupplier().getId());
		}
		Page<Bill_info> page = rzglservice.findPage(new Page<Bill_info>(request, response), bill_info); 
		model.addAttribute("page", page);
		return "platform/gysYfzkBillManager";
	}
	
	
	/**
	 * 核心企业----跳转至批量上传应付账款界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"batch-uploadBill", ""})
	public String batchUploadBillInfo(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			if(bill_info.getCoreEnterpriseId() == null) {
				bill_info.setCoreEnterpriseId(new Core_enterprise());
			}
			bill_info.getCoreEnterpriseId().setId(currentUser.getCore().getId());
		}
		model.addAttribute("bill_info", bill_info);
		return "platform/hxqyBillUploads";
	}
	
	/**
	 * 供应商 ---跳转至批量上传应付账款界面（供应商作为核心企业）
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"gysYfzkBatchUploadBillInfo", ""})
	public String gysYfzkBatchUploadBillInfo(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(bill_info.getSupplierParentId() == null) {
				bill_info.setSupplierParentId(new Supplier_enterprise());
			}
			bill_info.getSupplierParentId().setId(currentUser.getSupplier().getId());
		}
		model.addAttribute("bill_info", bill_info);
		return "platform/gysYfzkBillUploads";
	}
	
	
	/**
	 * 供应商---应收账款管理
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"gyslist", ""})
	public String gyslist(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(bill_info.getSupplierEnterpriseId() == null) {
				bill_info.setSupplierEnterpriseId(new Supplier_enterprise());
			}
			bill_info.getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			Supplier_enterprise supplier_enterprise = gysservice.get(currentUser.getSupplier().getId());
			if(supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getId())) {
				if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
					supplier_enterprise.getParamsId().setAllQuota("0");
				}
				supplier_enterprise.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(supplier_enterprise));
			}
			model.addAttribute("supplier_enterprise", supplier_enterprise);
			String ableFinancingQuota = "0";
			if(gysservice.getAllWaitFinancingAmount(supplier_enterprise).get("waitFinancingAmount") != null) {
				ableFinancingQuota = gysservice.getAllWaitFinancingAmount(supplier_enterprise).get("waitFinancingAmount").toString();
			}
			if(!Utils.isEmpty(ableFinancingQuota) && supplier_enterprise != null && supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getParamsId().getAvailableQuota())) {
				if(Float.parseFloat(ableFinancingQuota) > Float.parseFloat(supplier_enterprise.getParamsId().getAvailableQuota())) {
					ableFinancingQuota = supplier_enterprise.getParamsId().getAvailableQuota();
				}
			}
			model.addAttribute("ableFinancingQuota", ableFinancingQuota);
		}
		Page<Bill_info> page = rzglservice.findPage(new Page<Bill_info>(request, response), bill_info); 
		model.addAttribute("page", page);
		return "platform/gysBillManager";
	}
	
	
	/**
	 * 
	 * 跳转单据--核心企业还款页面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("hxqyBillHk")
	public String hxqyBillHk(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		Bill_info bill = null;
		Financing_info financing = null;
		if(bill_info != null && !Utils.isEmpty(bill_info.getId())) {
			bill = rzglservice.get(bill_info.getId());
			financing = quanbuservice.getBillid(bill_info.getId());
		}
		model.addAttribute("financing", financing);
		model.addAttribute("bill", bill);
		return "platform/hxqyBillRepayment";
	}
	
	
	/**
	 * 融资还款
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("hxqyBillHkSave")
	public String hxqyBillHkSave(@RequestParam("file") MultipartFile uploadfile, Bill_info bill_info, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		String str = request.getParameter("str");
		if(bill_info == null || bill_info.getVoucher_info() == null) {
			addMessage(redirectAttributes, "单据信息或凭证信息不存在！");
			if("1".equals(str)) {
				return "redirect:" + adminPath + "/yfzk/list/?repage";
			}else if("2".equals(str)){
				return "redirect:" + adminPath + "/rzglall/hxqylist?repage";
			}else {
				return "redirect:" + adminPath + "/rzglall/gyslist?repage";
			}
		}
		Voucher_info voucher_info = bill_info.getVoucher_info();
		//还款金额
		String loanamount = request.getParameter("loanamount");
		if(uploadfile == null || Utils.isEmpty(uploadfile.getOriginalFilename())) {
			addMessage(redirectAttributes, "请先上传凭证！");
			if("1".equals(str)) {
				return "redirect:" + adminPath + "/yfzk/list/?repage";
			}else if("2".equals(str)){
				return "redirect:" + adminPath + "/rzglall/hxqylist?repage";
			}else {
				return "redirect:" + adminPath + "/rzglall/gyslist?repage";
			}
		}
		if(!Utils.isEmpty(loanamount)) {
			//融资ID凭证表需要
			String parameter = request.getParameter("rzid");
			if(!Utils.isEmpty(parameter)) {
				String realPath = Global.USERFILES_BASE_URL
		        		+ "" + "/rzhk/images/" ;
				String url = uploadfile.getOriginalFilename();
				url = url.substring(0, url.indexOf(".")) + RandomUtil.generateString(20) + url.substring(url.indexOf("."));
				FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
				uploadfile.transferTo(new File(Global.getUserfilesBaseDir() + realPath + url));
				voucher_info.setUrl(request.getContextPath() + realPath + url);
				if(!"1".equals(str) && !"2".equals(str)) {
					rzglservice.supplierRepayMent(voucher_info, bill_info, loanamount, parameter);
				}else {
					rzglservice.insetBean(voucher_info, bill_info, loanamount, parameter);
				}
				
				//更新融资以及单据状态
				quanbuservice.updateState(bill_info.getId());
				rzglservice.updateState(bill_info.getId());
				addMessage(redirectAttributes, "还款成功！");
				
				//发送邮件消息
				MailCompose mailCompose = new MailCompose();
				Mail mail = new Mail();
				mailCompose.setMail(mail);
				mail.setId(IdGen.uuid());
				mail.setTitle("融资已还款，待银行清分！");
				mail.setOverview("融资已还款，待银行清分！");
				mail.setContent("融资已还款，待银行清分！");
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
			}else {
				addMessage(redirectAttributes, "缺少融资ID标识，无法还款！");
			}
		}else {
			addMessage(redirectAttributes, "还款金额不可以为空！");
		}
		if("1".equals(str)) {
			return "redirect:" + adminPath + "/yfzk/list/?repage";
		}else if("2".equals(str)){
			return "redirect:" + adminPath + "/rzglall/hxqylist?repage";
		}else {
			return "redirect:" + adminPath + "/rzglall/gyslist?repage";
		}
	}
	
	
	/**
	 * 跳转至融资申请界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"to-financingApply", ""})
	public String toFinancingApply(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(bill_info != null && !Utils.isEmpty(bill_info.getId())) {
			bill_info = rzglservice.get(bill_info.getId());
			if(bill_info != null && bill_info.getSupplierEnterpriseId() != null && bill_info.getSupplierEnterpriseId().getParamsId() != null) {
				bill_info.getSupplierEnterpriseId().getParamsId().setAvailableQuota(gysservice.getAvailableQuota(bill_info.getSupplierEnterpriseId()));
			}
		}
		model.addAttribute("bill_info", bill_info);
		return "platform/gysFinancingApply";
	}
	
	
	/**
	 * 跳转至融资申请---查看核心凭证界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"to-showCoreVoucher", ""})
	public String toShowCoreVoucher(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(bill_info != null && !Utils.isEmpty(bill_info.getId())) {
			Voucher_info v = new Voucher_info();
			v.setBillId(bill_info);
			v.setType("0");
			model.addAttribute("coreVoucherInfos", voucher_infodao.findList(v));
		}
		return "platform/gysShowCoreVoucher";
	}
	
	
	/**
	 * 跳转至融资申请---查看供应商凭证界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"to-showSupplierVoucher", ""})
	public String toShowSupplierVoucher(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(bill_info != null && !Utils.isEmpty(bill_info.getId())) {
			Voucher_info v = new Voucher_info();
			v.setBillId(bill_info);
			v.setType("1");
			model.addAttribute("supplierVoucherInfos", voucher_infodao.findList(v));
		}
		return "platform/gysShowSupplierVoucher";
	}
	
	
	/**
	 * 跳转至核心企业上传凭证界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"to-hxqyUploadVoucher", ""})
	public String toHxqyUploadVoucher(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(bill_info != null && !Utils.isEmpty(bill_info.getId())) {
			bill_info = rzglservice.get(bill_info.getId());
		}
		model.addAttribute("bill_info", bill_info);
		model.addAttribute("voucher_type", "0");
		return "platform/hxqyBillUploadVoucher";
	}
	
	
	/**
	 * 跳转至供应商上传凭证界面
	 * @param bill_info
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"to-gysUploadVoucher", ""})
	public String toGysUploadVoucher(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(bill_info != null && !Utils.isEmpty(bill_info.getId())) {
			bill_info = rzglservice.get(bill_info.getId());
		}
		model.addAttribute("bill_info", bill_info);
		model.addAttribute("voucher_type", "1");
		return "platform/hxqyBillUploadVoucher";
	}
	
	
	/**
	 * 上传提交凭证
	 * @param voucher_info
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"submit-voucher", ""})
	public String submitVoucher(@RequestParam(value="urls") String urls, Voucher_info voucher_info, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(voucher_info.getBillId() != null && !Utils.isEmpty(voucher_info.getBillId().getId()) && !Utils.isEmpty(voucher_info.getType())) {
			if(!Utils.isEmpty(urls)) {
				String[] urlArray = urls.split(",");
				for(String url : urlArray) {
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					voucher_info.setId(uuid);
					voucher_info.setUrl(url);
					voucher_infodao.insert(voucher_info);
				}
			}
			
			addMessage(redirectAttributes, "提交凭证成功！");
			if("0".equals(voucher_info.getType())) {
				return "redirect:" + adminPath + "/yfzk/list?repage";
			}else if("1".equals(voucher_info.getType())) {
				return "redirect:" + adminPath + "/yfzk/to-financingApply?repage&id="+ voucher_info.getBillId().getId();
			}
		}
		return null;
	}
	
	
	/**
	 * 跳转单据详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("redirect-info")
	public String ridInfo(@RequestParam(value="id", required=true) String id,Model model){
		if(!Utils.isEmpty(id)) {
			Bill_info bill_info = rzglservice.get(id);
			model.addAttribute("bill_info", bill_info);
			Voucher_info voucher_info = new Voucher_info();
			voucher_info.setBillId(bill_info);
			List<Voucher_info> findList = voucher_infodao.findList(voucher_info);
			model.addAttribute("findlist", findList);
		}
		return "platform/billDetails";
	}
	
	
	/**
	 * 异步上传凭证
	 * @param uploadfile
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="web-uploadVoucher")
	@ResponseBody
	public void webUploadVoucher(@RequestParam("file")MultipartFile uploadfile, HttpServletRequest request, HttpServletResponse response, Model model) {
		String realPath = Global.USERFILES_BASE_URL + "" + "/voucher/images/" ;
		
		if(uploadfile != null && !Utils.isEmpty(uploadfile.getOriginalFilename())) {
			String businessimage = uploadfile.getOriginalFilename();
			businessimage = businessimage.substring(0, businessimage.indexOf(".")) + RandomUtil.generateString(20) + businessimage.substring(businessimage.indexOf("."));
			FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
			try {
				uploadfile.transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessimage));
				renderString(response, request.getContextPath() + realPath + businessimage);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				renderString(response, e.getMessage());
			}
		}
	}
	
	
	/**
	 * 异步删除凭证
	 * @param voucher_info
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="web-removeVoucher")
	@ResponseBody
	public void webRemoveVoucher(Voucher_info voucher_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(voucher_info != null && !Utils.isEmpty(voucher_info.getUrl())) {
			String absolutePath = request.getServletContext().getRealPath(voucher_info.getUrl().substring(voucher_info.getUrl().indexOf("/userfiles")));
			File file = new File(absolutePath);
			if(file.exists()) {
				file.delete();
			}
			//判断是否删除凭证信息
			if(!Utils.isEmpty(voucher_info.getId())) {
				voucher_infodao.delete(voucher_infodao.get(voucher_info.getId()));
			}
			renderString(response, "success");
		}else {
			renderString(response, "凭证地址无效！");
		}
	}
	
	
	/**
	 * 导出excel单据文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public void exportFile(Bill_info bill_info, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "单据" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Bill_info> page = rzglservice.findPage(new Page<Bill_info>(request, response, -1), bill_info);
    		new ExportExcel("单据", Bill_info.class).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出单据失败！失败信息："+e.getMessage());
		}
    }
    
    
    /**
	 * 下载应付账款模板
	 */
    @RequestMapping(value = "import-template")
    public void importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "应付账款模板.xlsx";
    		List<Bill_info> list = Lists.newArrayList(); 
    		new ExportExcel("应付账款", Bill_info.class, 1).setDataList(list).write(response, fileName).dispose();
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载应付账款模板失败！失败信息："+e.getMessage());
		}
    }
    
    
    /**
     * 批量提交单据
     * @return
     */
    @RequestMapping(value="batch-submit")
    public String batchSubmit(@RequestParam("ids") String ids, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
    	if(!Utils.isEmpty(ids)) {
    		String[] idArray = ids.split(",");
    		Bill_info bill = null;
    		for(String id : idArray){
    			bill = rzglservice.get(id);
    			bill.setState("1");
    			rzglservice.save(bill);
    			
    			//发送邮件消息
    			MailCompose mailCompose = new MailCompose();
    			Mail mail = new Mail();
    			mailCompose.setMail(mail);
    			mail.setId(IdGen.uuid());
    			mail.setTitle("有新的单据，待申请融资！");
    			mail.setOverview("有新的单据，待申请融资！");
    			mail.setContent("有新的单据，待申请融资！");
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
    						receiverList.add(entity.getUserId());
    					}
    				}
    			}
    			mailCompose.setReceiverList(receiverList);
    			mailComposeService.sendMail(mailCompose);
    		}
    		
    		addMessage(redirectAttributes, "提交成功！");
    	}
    	return "redirect:" + adminPath + "/yfzk/list?repage";
    }
    
    
    /**
     * 批量导入Excel单据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, Bill_info bill_info, RedirectAttributes redirectAttributes) {
		try {
			if(file == null || bill_info == null) {
				addMessage(redirectAttributes, "缺少Excel文件或单据信息不全，无法导入！");
				return "redirect:" + adminPath + "/yfzk/list?repage";
			}
			int successNum = 0;
			int failureNum = 0;
			String num="";
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			Bill_info bill = new Bill_info();
			if(ei != null) {
				List<Bill_info> list = ei.getDataList(Bill_info.class);
				if(list != null) {
					for (Bill_info entiy : list){
						try{
							List<Bill_info> getnum = rzglservice.getnum(entiy);
							if(getnum != null && getnum.size()>0) {
								num+="单据编号为："+ entiy.getNum() +"已存在！";
							}else {
								Supplier_enterprise name = gysservice.getName(entiy.getSupplierEnterpriseId().getName());
								if(null==name) {
									num+="无法找到名称为："+ entiy.getSupplierEnterpriseId().getName() +"的供应商！";
								}else {
									//判断合同号是否存在
									bill.setContractNum(entiy.getContractNum());
									List<Bill_info> lst = rzglservice.findList(bill);
									if(lst != null && lst.size() > 0) {
										num += "合同号："+ entiy.getContractNum() +"已存在！";
									}else {
										//存储单据
										entiy.setSupplierEnterpriseId(name);
										//原核心企业
										entiy.setCoreEnterpriseId(bill_info.getCoreEnterpriseId());
										//上级供应商（核心企业）
										entiy.setSupplierParentId(bill_info.getSupplierParentId());
										entiy.setState("0");
										rzglservice.save(entiy);
										successNum++;
									}
								}
							}
						}catch(ConstraintViolationException ex){
							failureNum++;
						}catch (Exception ex) {
							failureNum++;
							continue;
						}
					}
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条应付账款记录！");
			}
			addMessage(redirectAttributes, "已成功导入" + successNum + "条应付账款记录" + failureMsg + "   " + num);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入应付账款失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/yfzk/list?repage";
    }
}
