package com.jeeplus.common.gyscontroller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.constant.CxConst;
import com.jeeplus.common.paramservice.EnterpriseParamService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.sms.SMSUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.*;
import com.jeeplus.modules.cyl.dao.Contract_infoDao;
import com.jeeplus.modules.cyl.dao.Enterprise_paramsDao;
import com.jeeplus.modules.cyl.dao.Supplier_shareholderDao;
import com.jeeplus.modules.cyl.dao.Supplier_userDao;
import com.jeeplus.modules.cyl.service.CoreSupplierService;
import com.jeeplus.modules.cyl.service.SupplierChildService;
import com.jeeplus.modules.esign.bean.UserEsign;
import com.jeeplus.modules.esign.dao.UserEsignDao;
import com.jeeplus.modules.esign.util.EsignUtil;
import com.jeeplus.modules.iim.dao.MailDao;
import com.jeeplus.modules.iim.entity.Mail;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.SystemConfig;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemConfigService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import fangfangrj.RandomUtil;
import fangfangrj.Utils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value="${adminPath}/gys")
public class GysController extends BaseController{

	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private GysService gysservice;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private Supplier_userDao supplierUserDao;

	@Autowired
	private EnterpriseParamService enterpriseparamservice;
	
	@Autowired
	private Enterprise_paramsDao enterprise_paramsdao;
	
	@Autowired
	private Supplier_shareholderDao supplier_shareholderDao;
	
	@Autowired
	private Contract_infoDao contract_infoDao;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CoreSupplierService service;
	
	@Autowired
	private SupplierChildService scService;
	
	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private MailDao mailDao;

	@Autowired
	private UserEsignDao userEsignDao;
	
	
	@ModelAttribute("supplier_enterprise")
	public Supplier_enterprise get(@RequestParam(required=false) String id) {
		Supplier_enterprise entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gysservice.get(id);
		}
		if (entity == null){
			entity = new Supplier_enterprise();
		}
		return entity;
	}
	
	
	
	/**
	 * 返回所有供应商集合
	 */
	@RequestMapping(value="get-gys-list")
	@ResponseBody
    public void getGysList(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
    	List<Supplier_enterprise> list = gysservice.findList(supplier_enterprise);
    	renderString(response, list);
 	}
	
	
	/**
	 * 平台----供应商列表
	 */
	@RequestMapping(value = {"gys-index", ""})
	public String list(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Supplier_enterprise> page = gysservice.findPage(new Page<Supplier_enterprise>(request, response), supplier_enterprise); 
		if(page != null && page.getList() != null && page.getList().size() > 0) {
			for (Supplier_enterprise item : page.getList()) {
				if(item != null && item.getParamsId() != null && !Utils.isEmpty(item.getId())) {
					if(Utils.isEmpty(item.getParamsId().getAllQuota())) {
						item.getParamsId().setAllQuota("0");
					}
					item.getParamsId().setAvailableQuota(gysservice.getAvailableQuota(item));
				}
			}
		}
		model.addAttribute("page", page);
		List<Map<String, Object>> selectState = gysservice.selectState();
		for(int i = 0; i < selectState.size(); i++) {
			model.addAttribute("sta"+i, selectState.get(i).get("count"));
		}
		if(null!=supplier_enterprise.getCoresupplier()&&!Utils.isEmpty(supplier_enterprise.getCoresupplier().getCoreEnterpriseId().getId())) {
			model.addAttribute("str", "0");
		}
		return "platform/gysManager";
	}
	
	
	/**
	 * 平台---设置是否允许邀请下级供应商
	 */
	@RequestMapping(value = {"is-yxyqgys", ""})
	public String isYxyqgys(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
			String isFlag = supplier_enterprise.getIsYqgys();
			isFlag = Utils.isEmpty(isFlag) ? "0" : isFlag;
			supplier_enterprise = gysservice.get(supplier_enterprise.getId());
			if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
				supplier_enterprise.setIsYqgys(isFlag);
				gysservice.save(supplier_enterprise);
				addMessage(redirectAttributes, "设置成功！");
			}
		}
		return "redirect:" + adminPath + "/gys/gys-index/?repage";
	}
	
	
	/**
	 * 
	 * 供应商查看员工
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"userlist", ""})
	public String userliJst(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = new Page<User>(request, response);
		user.setPage(page);
		List<User> list = userDao.findList(user);
		if(list != null && list.size() > 0) {
			for(User su : list) {
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(su, "o", "u"));
				su.setRoleList(roleDao.findList(role));
			}
		}
		page.setList(list);
		model.addAttribute("suppl",user.getSupplier().getId());
        model.addAttribute("page", page);
		return "platform/seeStaffList";
	}
	
	
	/**
	 * 平台---添加、编辑员工
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/editUser")
	public String editUser(User user, Model model, @RequestParam(value="suppl", required=false) String suppl) {
		//判断用户ID是否为空，如果为空则是添加，不为空则是修改
		if (!Utils.isEmpty(user.getId())){
			User us = userDao.get(user.getId());
			if(us != null) {
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(us, "o", "u"));
				List<Role> rLt = roleDao.findList(role);
				if(rLt != null && rLt.size() > 0) {
					us.setRole(rLt.get(0));
				}
			}
			model.addAttribute("user", us);
		} 
		model.addAttribute("allRoles", systemService.findAllRole());
		model.addAttribute("suppl", suppl);
		model.addAttribute("url", "gys/saveUser");
		return "platform/editStaffInfo";
	}


	/**
	 * 查看，增加，编辑供应商
	 */
	@RequestMapping(value = "form")
	public String form(Supplier_enterprise supplier_enterprise, Model model,HttpServletRequest req) {
		String str = req.getParameter("str");
		//获取供应商股东集合
		if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
			Supplier_shareholder supplier_shareholder = new Supplier_shareholder();
			supplier_shareholder.setSupplierEnterpriseId(supplier_enterprise);
			model.addAttribute("shareHolders", supplier_shareholderDao.findList(supplier_shareholder));
		}
		model.addAttribute("supplier_enterprise", supplier_enterprise);
		model.addAttribute("str", str);
		return "platform/gysEditInfo";
	}
	
	
	/**
	 * 供应商审核
	 * @param supplier_enterprise
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "gysShenHe")
	public String gysShenHe(Supplier_enterprise supplier_enterprise, Model model) {
		//获取供应商股东集合
		if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
			Supplier_shareholder supplier_shareholder = new Supplier_shareholder();
			supplier_shareholder.setSupplierEnterpriseId(supplier_enterprise);
			model.addAttribute("shareHolders", supplier_shareholderDao.findList(supplier_shareholder));
		}
		model.addAttribute("supplier_enterprise", supplier_enterprise);
		return "platform/gysExamine";
	}


	/**
	 * 点击提交供应商审核
	 * @param supplier_enterprise
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "subgysShenHe")
	public String subgysShenHe(Supplier_enterprise supplier_enterprise, Model model,RedirectAttributes redirectAttributes) {
		gysservice.save(supplier_enterprise);
		MailCompose mailCompose = new MailCompose();
		Mail mail = new Mail();
		mailCompose.setMail(mail);
		mail.setId(IdGen.uuid());
		if("2".equals(supplier_enterprise.getState())) {
			mail.setTitle("平台审核未通过！");
			mail.setOverview("平台审核未通过！");
			mail.setContent("平台审核未通过！");
			addMessage(redirectAttributes, "平台审核未通过！");
		}else {
			supplier_enterprise = gysservice.get(supplier_enterprise.getId());
			if(supplier_enterprise != null) {
				//注册负责人用户并发送相应通知短信
				String leaderUserPhone = supplier_enterprise.getAgencyPhone();
				String leaderUserPwd = RandomUtil.generateLowerString(6);
				if(!Utils.isEmpty(leaderUserPhone)) {
					User leaderUser = new User();
					leaderUser.setLoginName(leaderUserPhone);
					leaderUser = userDao.getByLoginName(leaderUser);
					if(leaderUser == null || Utils.isEmpty(leaderUser.getId())) {
						leaderUser = new User();
						leaderUser.setLoginName(leaderUserPhone);
						leaderUser.setPassword(leaderUserPwd);
						leaderUser.setMobile(leaderUserPhone);
						leaderUser.setName(supplier_enterprise.getAgencyName());
						leaderUser.setEmail(supplier_enterprise.getAgencyEmail());

						// 密码MD5加密
						leaderUser.setPassword(SystemService.entryptPassword(leaderUser.getPassword()));

						//将用户与角色进行绑定
						List<Role> ros = roleDao.findList(new Role());
						if(ros != null && ros.size() > 0) {
							for(Role r : ros) {
								if(r.getName().indexOf("供应商") != -1 && r.getName().indexOf("负责人") != -1) {
									leaderUser.setRole(r);
									List<Role> lt = new ArrayList<Role>();
									lt.add(r);
									leaderUser.setRoleList(lt);
								}
							}
						}

						//注册用户
						if(leaderUser.getRole() != null && !Utils.isEmpty(leaderUser.getRole().getId())) {

							leaderUser.setCreateDate(new Date());
							leaderUser.setUpdateDate(new Date());
							systemService.saveUser(leaderUser);
							registEsign(supplier_enterprise, leaderUser);


							//将用户与供应商进行绑定
							Supplier_user su = new Supplier_user();
							su.setUserId(leaderUser);
							su.setSupplierEnterpriseId(supplier_enterprise);
							supplierUserDao.insert(su);
						}

						//发送短信
						try {
							SystemConfig config = systemConfigService.get("1");
							String content = "{\"supplier_enterprise\":\""+ supplier_enterprise.getName() +"\",\"username\":\""+ leaderUserPhone +"\",\"userpassword\":\""+ leaderUserPwd +"\"" +
//									",\"url\":\""+ CxConst.PROD_URL +"\"" +
									"}";
							SMSUtils.send(config.getSmsName(), config.getSmsPassword(), leaderUserPhone, content, CxConst.SMS_TEMPLATE_CODE_SUPPLIER);
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				}
			}

			mail.setTitle("平台审核通过！");
			mail.setOverview("平台审核通过！");
			mail.setContent("平台审核通过！");
			addMessage(redirectAttributes, "平台审核通过！");
		}

		//发送邮件消息
		mailDao.insert(mail);
		mailCompose.setStatus("1");
		mailCompose.setReadstatus("1");
		List<User> receiverList = new LinkedList<User>();
		Supplier_user su = new Supplier_user();
		su.setSupplierEnterpriseId(supplier_enterprise);
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

		return "redirect:" + adminPath + "/gys/gys-index/?repage";
	}

	public void registEsign(Supplier_enterprise supplier_enterprise, User leaderUser) {
		/**
		 * @Auther yumx添加==判断审核通过的用户
				* 是否供应商负责人，如果是则注册e签宝账户及企业账户
				* @Date 2020.1.14
				*/
		if(leaderUser.getRole().getId().equals("1457fff9841c490395fff6e59e75d5e1")){
			/**
			 * 注册个人用户
			 */
			JSONObject accountInfo = new JSONObject();
			accountInfo.put("email",supplier_enterprise.getAgencyEmail());
			accountInfo.put("thirdPartyUserId",leaderUser.getId());
			accountInfo.put("idNumber",supplier_enterprise.getAgencyIdCard());
			accountInfo.put("mobile",supplier_enterprise.getAgencyPhone());
			//类型默认是身份证
			accountInfo.put("idType","");
			accountInfo.put("name",leaderUser.getName());
			//注册负责人e签宝个人账户
			JSONObject esignAccount = EsignUtil.createEsignAccount(accountInfo);
			//获取个人账户accountId并保存
			JSONObject data = esignAccount.getJSONObject("data");
			String accountId = data.getString("accountId");

			UserEsign userEsign = new UserEsign();
			userEsign.setEsignId(accountId);
			userEsign.setUserId(leaderUser.getId());
			if(supplier_enterprise.getAgencyIdCard().equals(supplier_enterprise.getLegalIdCard())){
				userEsign.setEsignType(2);//法人
			}
			userEsign.setEsignType(3);//经办人
			try{
				Thread.sleep(2000);
			}catch (Exception e){
				logger.error(e.getMessage());
			}

			JSONObject sealsInfo = EsignUtil.queryEsignSealsByAccoundId(accountId);
			JSONObject dataInfo = sealsInfo.getJSONObject("data");
			JSONArray seals = dataInfo.getJSONArray("seals");
			String sealId = seals.getJSONObject(0).getString("sealId");


			userEsign.setSeelId(sealId);
			userEsignDao.insert(userEsign);
			/**
			 * 注册企业账户
			 */
			JSONObject companyInfo = new JSONObject();
			companyInfo.put("creator",accountId);
			//证件类型取得是组织机构代码
			companyInfo.put("idType","");
			companyInfo.put("idNumber",supplier_enterprise.getOrgCode());
			companyInfo.put("name",supplier_enterprise.getName());
			companyInfo.put("thirdPartyUserId",supplier_enterprise.getId());
			JSONObject esignComponyAccount = EsignUtil.createEsignComponyAccount(companyInfo);
			//获取企业用户orgId并保存
			JSONObject dataCompanyInfo = esignComponyAccount.getJSONObject("data");
			String orgId = dataCompanyInfo.getString("orgId");
			UserEsign userEsignCompany = new UserEsign();
			userEsignCompany.setEsignType(1);//企业
			try{
				Thread.sleep(2000);
			}catch (Exception e){
				logger.error(e.getMessage());
			}
			JSONObject sealsInfoCompany = EsignUtil.queryEsignSealsByAccoundId(orgId);
			JSONObject dataInfoCompany = sealsInfoCompany.getJSONObject("data");
			JSONArray sealsCompany = dataInfoCompany.getJSONArray("seals");
			String sealIdCompany = sealsCompany.getJSONObject(0).getString("sealId");
			userEsignCompany.setSeelId(sealIdCompany);
			userEsignCompany.setUserId(supplier_enterprise.getId());
			userEsignCompany.setEsignId(orgId);
			userEsignDao.insert(userEsignCompany);

		}
	}


	/**
	 * 供应商合同
	 * @param supplier_enterprise
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "gysHeTong")
	public String gysHeTong(Supplier_enterprise supplier_enterprise, Model model) {
		model.addAttribute("Supplier_enterprise", supplier_enterprise);
		return "platform/gysContractList";
	}
	
	
	/**
	 * 平台---保存员工
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveUser")
	public String saveUser(User user, Model model, Supplier_user supplier_user, RedirectAttributes redirectAttributes) {
		if(user.getSupplier() != null && !Utils.isEmpty(user.getSupplier().getId())) {
			List<Role> rLt = new ArrayList<Role>();
			rLt.add(user.getRole());
			user.setRoleList(rLt);
			
			//保存员工
			if (Utils.isEmpty(user.getId()) && !"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
				addMessage(redirectAttributes, "保存员工'" + user.getLoginName() + "'失败，登录名已存在！");
				return "redirect:" + adminPath + "/gys/userlist?supplier.id=" + user.getSupplier().getId();
			}
			
			if (Utils.isEmpty(user.getId()) || Utils.isEmpty(user.getPassword())) {
				user.setPassword(SystemService.entryptPassword("123456"));
			}
			systemService.saveUser(user);
			
			//存储员工与企业关系
			Supplier_user supplierUser = new Supplier_user();
			supplierUser.setUserId(user);
			List<Supplier_user> suList = supplierUserDao.findList(supplierUser);
			if(suList != null && suList.size() > 0) {
				for(int j = suList.size() - 1; j >= 0; j--) {
					supplierUserDao.delete(suList.get(j));
				}
			}
			if(user.getSupplier() != null && !Utils.isEmpty(user.getSupplier().getId())) {
				Supplier_enterprise supplier = new Supplier_enterprise();
				supplier.setId(user.getSupplier().getId());
				supplierUser.setSupplierEnterpriseId(supplier);
				supplierUserDao.insert(supplierUser);
			}
			
			addMessage(redirectAttributes, "保存员工'" + user.getLoginName() + "'成功！");
		}else {
			addMessage(redirectAttributes, "保存员工'" + user.getLoginName() + "'失败！");
		}
		return "redirect:" + adminPath + "/gys/userlist?supplier.id=" + user.getSupplier().getId();
	}
	
	
	/**
	 * 删除员工
	 * @param request
	 * @param response
	 * @param delitems
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "deleteuser")
	public String deleteuser(HttpServletRequest request,HttpServletResponse response,@RequestParam("ids") String delitems,Model model,RedirectAttributes redirectAttributes) {
		String supplierId = null;
		if(Utils.isEmpty(delitems)) {
			addMessage(redirectAttributes, "没有找到员工ID标识，删除失败！");
		}else {
			String idArray[] =delitems.split(",");
			for(String id : idArray){
				User user = new User();
				user = userDao.get(id);
				if(Utils.isEmpty(supplierId) && user.getSupplier() != null) {
					supplierId = user.getSupplier().getId();
				}
				if (User.isAdmin(user.getId())){
					addMessage(redirectAttributes, "删除员工失败，不允许删除超级管理员角色用户！");
					return "redirect:" + adminPath + "/gys/userlist?supplier.id=" + (Utils.isEmpty(supplierId) ? "" : supplierId);
				}else{
					systemService.deleteUser(user);
				}
			}
			addMessage(redirectAttributes, "删除员工成功！");
		}
		return "redirect:" + adminPath + "/gys/userlist?supplier.id=" + (Utils.isEmpty(supplierId) ? "" : supplierId);
 	}
	
	
	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	
	/**
	 * 跳转到邀请批量导入供应商
	 * @return
	 */
	@RequestMapping("/exportGys")
	public String exportGys() {
		return "platform/hxqyExportGys";
	}
	
	
	/**
	 * 下载供应商模板
	 */
    @RequestMapping(value = "import-template")
    public void importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "template_supplier.xlsx";
    		List<Supplier_enterprise> list = Lists.newArrayList(); 
    		new ExportExcel("供应商", Supplier_enterprise.class, 1).setDataList(list).write(response, fileName).dispose();
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载供应商模板失败！失败信息："+e.getMessage());
		}
    }
    
    
	/**
	 * 编辑提交
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectattributes
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "supplier_enterpriseEidt")
	public String supplier_enterpriseEidt(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectattributes){
		String str = request.getParameter("str");
		gysservice.save(supplier_enterprise);
		
		//存储供应商股东信息
		String guDongs = request.getParameter("guDongs");
		if(!Utils.isEmpty(guDongs)) {
			Supplier_shareholder supplier_shareholder = new Supplier_shareholder();
			supplier_shareholder.setSupplierEnterpriseId(supplier_enterprise);
			String[] guDongArr = guDongs.split(",");
			supplier_shareholderDao.deleteBySupplier(supplier_enterprise.getId());
			if(guDongArr != null && guDongArr.length > 0) {
				for (String item : guDongArr) {
					if(!Utils.isEmpty(item)) {
						String[] gds = item.split(" ");
						if(gds != null && gds.length == 3) {
							String uuid = UUID.randomUUID().toString().replaceAll("-", "");
							supplier_shareholder.setId(uuid);
							supplier_shareholder.setName(gds[0]);
							supplier_shareholder.setIdNum(gds[1]);
							supplier_shareholder.setRatio(gds[2]);
							supplier_shareholderDao.insert(supplier_shareholder);
						}
					}
				}
			}
		}
		
		addMessage(redirectattributes, "编辑信息'" + supplier_enterprise.getName() + "'成功");
		if("1".equals(str)) {
			return "redirect:" + adminPath + "/gys/gys-company-info";
		}else {
			return "redirect:" + adminPath + "/gys/gys-index";
		}
	}
	
	
	/**
	 * 查看参数
	 * @return
	 */
	@RequestMapping("/showParam")
	public String showParam(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		Enterprise_params enterprise_params = null;
		if(supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getParamsId().getId())) {
			enterprise_params = enterpriseparamservice.get(supplier_enterprise.getParamsId().getId());
			if(enterprise_params == null) {
				enterprise_params = new Enterprise_params();
				enterprise_params.setId(supplier_enterprise.getParamsId().getId());
			}
			if(!Utils.isEmpty(enterprise_params.getRemindRepayment())) {
				String ments[] = enterprise_params.getRemindRepayment().split(",");
				model.addAttribute("repayment_remind_day1",ments[0]);
				model.addAttribute("repayment_remind_day2",ments[1]);
				model.addAttribute("repayment_remind_day3",ments[2]);
				model.addAttribute("repayment_remind_hour",ments[3]);
			}
		}
		model.addAttribute("enterprise_params", enterprise_params);
		return "platform/gysConfigParams";
	}
	
	
	/**
	 * 平台---配置参数
	 * @return
	 */
	@RequestMapping("saveInfo")
	public String saveInfo(Enterprise_params enterprise_params, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(enterprise_params != null && !Utils.isEmpty(enterprise_params.getId())) {
			Enterprise_params enterprise = enterprise_paramsdao.get(enterprise_params.getId());
			if(enterprise == null) {
				enterprise_paramsdao.insert(enterprise_params);
			}else {
				enterprise_paramsdao.update(enterprise_params);
			}
			addMessage(redirectAttributes, "企业参数保存成功！");
		}else {
			addMessage(redirectAttributes, "缺少企业参数ID标识，保存参数失败！");
		}
		return "redirect:" + adminPath + "/gys/gys-index";
	}
	
	
	/**
	 * 平台----详情页
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "gys-info")
	public String enterpriseInfo(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response, Model model){
		if(!Utils.isEmpty(id)){
			Supplier_enterprise supplier_enterprise = gysservice.get(id);
			//获取供应商股东集合
			if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
				Supplier_shareholder supplier_shareholder = new Supplier_shareholder();
				supplier_shareholder.setSupplierEnterpriseId(supplier_enterprise);
				model.addAttribute("shareHolders", supplier_shareholderDao.findList(supplier_shareholder));
			}
			model.addAttribute("supplier_enterprise", supplier_enterprise);
		}
		return "platform/gysShowInfo";
	}
	
	
	/**
	 * 批量删除供应商
	 * @param delitems
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="deleteBatch")
    public String deleteBatch(@RequestParam(value="ids", required=false) String delitems, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(delitems)) {
			addMessage(redirectAttributes, "没有找到id，删除失败！");
		}else {
			String idArray[] = delitems.split(",");
			Supplier_enterprise supplier_enterprise = null;
			Core_enterprise core_enterprise = null;
			Supplier_enterprise supplier_parent = null;
			User currentUser = UserUtils.getUser();
			if(currentUser != null && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
				core_enterprise = currentUser.getCore();
			}
			if(currentUser != null && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
				supplier_parent = currentUser.getSupplier();
			}
			for(String id : idArray){
				//删除当前核心企业与供应商关系记录
				if(core_enterprise != null && !Utils.isEmpty(core_enterprise.getId())) {
					Core_supplier cs = new Core_supplier();
					supplier_enterprise = new Supplier_enterprise();
					supplier_enterprise.setId(id);
					cs.setCoreEnterpriseId(core_enterprise);
					cs.setSupplierEnterpriseId(supplier_enterprise);
					service.delete(cs);
				}
				
				//删除当前供应商与下级供应商关系记录
				if(supplier_parent != null && !Utils.isEmpty(supplier_parent.getId())) {
					Supplier_child sc = new Supplier_child();
					supplier_enterprise = new Supplier_enterprise();
					supplier_enterprise.setId(id);
					sc.setSupplierEnterpriseId(supplier_parent);
					sc.setSupplierChildId(supplier_enterprise);
					scService.delete(sc);
				}
				
				supplier_enterprise = gysservice.get(id);
				gysservice.deleteSupplierByJoin(supplier_enterprise);
			}
			addMessage(redirectAttributes, "删除成功！");
		}
		return "redirect:" + adminPath + "/coreSupplierCtrl/invitation-gys?repage";
	}
	
	
	/**
	 * 导入Excel数据
	 *
	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    	User currentUser = UserUtils.getUser();
		String coreid=currentUser.getCore().getId();
    	try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			
			List<Supplier_enterprise> list = ei.getDataList(Supplier_enterprise.class);
			for (Supplier_enterprise supplier_enterprise : list){
				try{
					supplier_enterprise.setState("0");
					gysservice.save(supplier_enterprise);
					
					Core_supplier core_supplier = new Core_supplier(); 
					
					Core_enterprise core_enterprise = new  Core_enterprise();
					core_enterprise.setId(coreid);
					core_supplier.setExportTime(new Date());
					core_supplier.setState("0");
					core_supplier.setSupplierEnterpriseId(supplier_enterprise);
					core_supplier.setCoreEnterpriseId(core_enterprise);
					successNum++;
					service.insert(core_supplier);
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条供应商记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条供应商记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入供应商失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/coreSupplierCtrl/invitation-gys?coreEnterpriseId.id="+coreid;
    }
    
    
    /**
	 * 供应商主页
	 * @return
	 */
	@RequestMapping(value = "gys-home")
	public String gysHome(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model){
		if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
			supplier_enterprise = gysservice.get(supplier_enterprise.getId());
			String availableQuota = gysservice.getAvailableQuota(supplier_enterprise);
			if(supplier_enterprise.getParamsId() != null) {
				supplier_enterprise.getParamsId().setAvailableQuota(availableQuota);
				if(Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())) {
					supplier_enterprise.getParamsId().setAllQuota("0");
				}
			}
			model.addAttribute("supplier_enterprise", supplier_enterprise);
			model.addAttribute("waitFinancingAmount", gysservice.getAllWaitFinancingAmount(supplier_enterprise));
		}
		return "platform/gysHome";
	}
	
	
	/**
	 * 获取供应商融资统计
	 * @param supplier_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getFinancingStatistics")
	public void getFinancingStatistics(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			renderString(response, null);
		}else {
			renderString(response, gysservice.getAllFinancingListBySupplier(supplier_enterprise));
		}
	}
	
	
	/**
	 * 供应商---我的公司---基本资料
	 * @return
	 */
	@RequestMapping(value = "gys-company-info")
	public String gysCompanyInfo(Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model){
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			supplier_enterprise = gysservice.get(currentUser.getSupplier().getId());
			//获取供应商股东集合
			if(supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
				Supplier_shareholder supplier_shareholder = new Supplier_shareholder();
				supplier_shareholder.setSupplierEnterpriseId(supplier_enterprise);
				model.addAttribute("shareHolders", supplier_shareholderDao.findList(supplier_shareholder));
			}
			model.addAttribute("supplier_enterprise", supplier_enterprise);
		}
		return "platform/gysCompanyInfo";
	}
    
    
    /**
	 * 供应商----我的公司----帐号管理
	 * @return
	 */
	@RequestMapping(value = "gys-mycompany-userManager")
	public String gysMycompanyUserManager(Supplier_user supplier_user, HttpServletRequest request, HttpServletResponse response, Model model){
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(supplier_user.getSupplierEnterpriseId() == null) {
				supplier_user.setSupplierEnterpriseId(new Supplier_enterprise());
			}
			supplier_user.getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			model.addAttribute("supplier_user", supplier_user);
		}
		Page<Supplier_user> page = new Page<Supplier_user>(request, response);
		supplier_user.setPage(page);
		List<Supplier_user> list = supplierUserDao.findList(supplier_user);
		if(list != null && list.size() > 0) {
			for(Supplier_user su : list) {
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(su.getUserId(), "o", "u"));
				su.getUserId().setRoleList(roleDao.findList(role));
			}
		}
		page.setList(list);
		model.addAttribute("page", page);
		return "platform/gysAccountManager";
	}
	
	
	/**
	 * 供应商----我的公司----下载合同
	 * @return
	 */
	@RequestMapping(value = "gys-mycompany-downloadContract")
	public String gysMycompanyDownloadContract(Contract_info contract_info, HttpServletRequest request, HttpServletResponse response, Model model){
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(contract_info.getFinancingId() == null) {
				contract_info.setFinancingId(new Financing_info());
			}
			if(contract_info.getFinancingId().getBillId() == null) {
				contract_info.getFinancingId().setBillId(new Bill_info());
			}
			if(contract_info.getFinancingId().getBillId().getSupplierEnterpriseId() == null) {
				contract_info.getFinancingId().getBillId().setSupplierEnterpriseId(new Supplier_enterprise());
			}
			contract_info.getFinancingId().getBillId().getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			model.addAttribute("contract_info", contract_info);
		}
		Page<Contract_info> page = new Page<Contract_info>(request, response);
		contract_info.setPage(page);
		page.setList(contract_infoDao.findList(contract_info));
		model.addAttribute("page", page);
		return "platform/gysDownloadContract";
	}
}
