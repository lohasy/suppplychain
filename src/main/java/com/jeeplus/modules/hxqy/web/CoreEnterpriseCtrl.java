package com.jeeplus.modules.hxqy.web;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.paramservice.EnterpriseParamService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.Core_enterprise;
import com.jeeplus.modules.cyl.bean.Core_supplier;
import com.jeeplus.modules.cyl.bean.Core_user;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.cyl.dao.Core_userDao;
import com.jeeplus.modules.cyl.dao.Enterprise_paramsDao;
import com.jeeplus.modules.cyl.service.CoreSupplierService;
import com.jeeplus.modules.hxqy.service.CoreEnterpriseService;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import fangfangrj.Utils;

/**
 * 核心企业控制类
 * @author LGT
 */

@Controller
@RequestMapping(value="${adminPath}/CoreEnterpriseCtrl")
public class CoreEnterpriseCtrl extends BaseController {

	@Autowired
	private CoreEnterpriseService coreEnterpriseService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private GysService gysservice;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private Core_userDao coreUserDao;
	
	@Autowired
	private EnterpriseParamService enterpriseParamService;
	
	@Autowired
	private Enterprise_paramsDao enterprise_paramsdao;
	
	@Autowired
	private CoreSupplierService coresupplierservice;
	
	
	@ModelAttribute("core_enterprise")
	public Core_enterprise get(@RequestParam(required=false) String id) {
		Core_enterprise entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = coreEnterpriseService.get(id);
		}
		if (entity == null){
			entity = new Core_enterprise();
		}
		return entity;
	}
	
	
	/**
	 * 跳转新手指导页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toHelpPage")
	public String toHelpPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/help/help";
	}
	
	
	/**
	 * 核心企业查看供应商
	 * @param core_supplier
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getcore_supplier")
	public String getcore_supplier(Core_supplier core_supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Core_supplier> page = coresupplierservice.findPage(new Page<Core_supplier>(request, response), core_supplier); 
		model.addAttribute("page", page);
		List<Map<String, Object>> selectState = gysservice.selectState();
		for(int i = 0; i < selectState.size(); i++) {
			model.addAttribute("sta"+i, selectState.get(i).get("cou"));
		}
		model.addAttribute("str", "0");
		return "platform/hxqygysManager";
	}
	
	
	/**
	 * 返回所有核心企业集合
	 */
	@RequestMapping(value="get-hxqy-list")
	@ResponseBody
    public void getHxqyList(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
    	List<Core_enterprise> list = coreEnterpriseService.findList(core_enterprise);
    	renderString(response, list);
 	}
	
	
	/**
	 * 平台---查看员工列表
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"userlist", ""})
	public String userlist(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
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
		model.addAttribute("coreid",user.getCore().getId());
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
	public String editUser(User user, Model model, @RequestParam(value="coreid", required=false) String coreid) {
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
		model.addAttribute("coreid", coreid);
		model.addAttribute("url", "CoreEnterpriseCtrl/saveUser");
		return "platform/editStaffInfo";
	}
	
	
	/**
	 * 平台---保存员工
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveUser")
	public String saveUser(User user, Model model, Core_user core_user, RedirectAttributes redirectAttributes) {
		if(user.getCore() != null && !Utils.isEmpty(user.getCore().getId())) {
			List<Role> rLt = new ArrayList<Role>();
			rLt.add(user.getRole());
			user.setRoleList(rLt);
			
			//保存员工
			if (Utils.isEmpty(user.getId()) && !"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
				addMessage(redirectAttributes, "添加员工'" + user.getLoginName() + "'失败，用户名已存在！");
				return "redirect:" + adminPath + "/CoreEnterpriseCtrl/userlist?core.id=" + user.getCore().getId();
			}
			
			if (Utils.isEmpty(user.getId()) || Utils.isEmpty(user.getPassword())) {
				user.setPassword(SystemService.entryptPassword("123456"));
			}
			systemService.saveUser(user);
			
			//存储员工与企业关系
			Core_user coreUser = new Core_user();
			coreUser.setUserId(user);
			List<Core_user> cuList = coreUserDao.findList(coreUser);
			if(cuList != null && cuList.size() > 0) {
				for(int i = cuList.size() - 1; i >= 0; i--) {
					coreUserDao.delete(cuList.get(i));
				}
			}
			if(user.getCore() != null && !Utils.isEmpty(user.getCore().getId())) {
				Core_enterprise core = new Core_enterprise();
				core.setId(user.getCore().getId());
				coreUser.setCoreEnterpriseId(core);
				coreUserDao.insert(coreUser);
			}
			
			addMessage(redirectAttributes, "保存员工'" + user.getLoginName() + "'成功！");
		}else {
			addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'失败！");
		}
		return "redirect:" + adminPath + "/CoreEnterpriseCtrl/userlist?core.id=" + user.getCore().getId();
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
	 * 平台----查看参数
	 * @return
	 */
	@RequestMapping("/showInfo")
	public String showInfo(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		Enterprise_params enterprise_params = null;
		if(core_enterprise.getParamsId() != null && !Utils.isEmpty(core_enterprise.getParamsId().getId())) {
			enterprise_params = enterpriseParamService.get(core_enterprise.getParamsId().getId());
			if(enterprise_params == null) {
				enterprise_params = new Enterprise_params();
				enterprise_params.setId(core_enterprise.getParamsId().getId());
			}
			if(!Utils.isEmpty(enterprise_params.getRemindRepayment())) {
				String ments[] = enterprise_params.getRemindRepayment().split(",");
				if(ments != null) {
					if(ments.length > 0) {
						model.addAttribute("repayment_remind_day1",ments[0]);
					}
					if(ments.length > 1) {
						model.addAttribute("repayment_remind_day2",ments[1]);
					}
					if(ments.length > 2) {
						model.addAttribute("repayment_remind_day3",ments[2]);
					}
					if(ments.length > 3) {
						model.addAttribute("repayment_remind_hour",ments[3]);
					}
				}
			}
		}
		model.addAttribute("enterprise_params", enterprise_params);
		return "platform/hxqyConfigParams";
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
		return "redirect:" + adminPath + "/CoreEnterpriseCtrl/platform-index?repage";
	}
	
	
	/**
	 * 平台--批量删除核心企业
	 * @param request
	 * @param response
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequestMapping(value="delete")
    public String delete(HttpServletRequest request,HttpServletResponse response,@RequestParam("ids") String delitems,Model model,RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(delitems)) {
			addMessage(redirectAttributes, "没有找到id，删除失败！");
		}else {
			String idArray[] = delitems.split(",");
			Core_enterprise core_enterprise = null;
			for(String id : idArray){
				core_enterprise = coreEnterpriseService.get(id);
				coreEnterpriseService.deleteCoreByJoin(core_enterprise);
			}
			addMessage(redirectAttributes, "删除成功！");
		}
		return "redirect:" + adminPath + "/CoreEnterpriseCtrl/platform-index?repage";
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
		String coreId = null;
		if(Utils.isEmpty(delitems)) {
			addMessage(redirectAttributes, "没有找到员工ID标识，删除失败！");
		}else {
			String idArray[] =delitems.split(",");
			for(String id : idArray){
				User user = new User();
				user = userDao.get(id);
				if(Utils.isEmpty(coreId) && user.getCore() != null) {
					coreId = user.getCore().getId();
				}
				if (User.isAdmin(user.getId())){
					addMessage(redirectAttributes, "删除员工失败，不允许删除超级管理员角色用户！");
					return "redirect:" + adminPath + "/CoreEnterpriseCtrl/userlist?core.id=" + (Utils.isEmpty(coreId) ? "" : coreId);
				}else{
					systemService.deleteUser(user);
				}
			}
			addMessage(redirectAttributes, "删除员工成功！");
		}
		return "redirect:" + adminPath + "/CoreEnterpriseCtrl/userlist?core.id=" + (Utils.isEmpty(coreId) ? "" : coreId);
 	}
	
	
	/**
	 * 返回平台---核心企业管理
	 * @return
	 */
	@RequestMapping(value = {"platform-index", ""})
	public String gysList(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Core_enterprise> page = coreEnterpriseService.find(new Page<Core_enterprise>(request, response), core_enterprise);
		if(page != null && page.getList() != null && page.getList().size() > 0) {
			for (Core_enterprise item : page.getList()) {
				if(item != null && !Utils.isEmpty(item.getId()) && item.getParamsId() != null) {
					if(Utils.isEmpty(item.getParamsId().getAllQuota())) {
						item.getParamsId().setAllQuota("0");
					}
					item.getParamsId().setAvailableQuota(coreEnterpriseService.getAvailableQuota(item));
				}
			}
		}
		model.addAttribute("page", page);
		return "platform/hxqyManager";
	}
	
	
	/**
	 * 平台---添加、编辑核心企业
	 * @param uploadfile
	 * @param core_enterprise
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectattributes
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "enterpriseEidt")
	public String enterpriseEidt(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectattributes){
		String str = request.getParameter("str");
		Enterprise_params enterprise_params = new Enterprise_params();
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		enterprise_params.setId(uuid);
		core_enterprise.setParamsId(enterprise_params);
		coreEnterpriseService.save(core_enterprise);
		if(core_enterprise != null) {
			String leaderUserPhone = core_enterprise.getChargePhone();
			String leaderUserPwd = "123456";
			if(!Utils.isEmpty(leaderUserPhone)) {
				User leaderUser = new User();
				leaderUser.setLoginName(leaderUserPhone);
				leaderUser = userDao.getByLoginName(leaderUser);
				if(leaderUser == null || Utils.isEmpty(leaderUser.getId())) {
					leaderUser = new User();
					leaderUser.setLoginName(leaderUserPhone);
					leaderUser.setPassword(leaderUserPwd);
					leaderUser.setMobile(leaderUserPhone);
					leaderUser.setName(core_enterprise.getChargeName());
					leaderUser.setEmail(core_enterprise.getChargeEmail());
					
					// 密码MD5加密
					leaderUser.setPassword(SystemService.entryptPassword(leaderUser.getPassword()));
					
					//将用户与角色进行绑定
					List<Role> ros = roleDao.findList(new Role());
					if(ros != null && ros.size() > 0) {
						for(Role r : ros) {
							if(r.getName().indexOf("核心企业") != -1 && r.getName().indexOf("负责人") != -1) {
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
						
						//将用户与核心企业进行绑定
						Core_user su = new Core_user();
						su.setUserId(leaderUser);
						su.setCoreEnterpriseId(core_enterprise);
						coreUserDao.insert(su);
					}
				}
			}
		}
		addMessage(redirectattributes, "编辑核心企业信息'" + core_enterprise.getName() + "'成功！");
		if("1".equals(str)) {
			return "redirect:" + adminPath + "/CoreEnterpriseCtrl/hxqy-company-info";
		}else {
			return "redirect:" + adminPath + "/CoreEnterpriseCtrl/platform-edit?id=" + core_enterprise.getId();
		}
	}
	
	
	/**
	 * 平台---新增和编辑核心企业
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "platform-edit")
	public String enterpriseEidtAndQuery(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response, Model model){
		String str = request.getParameter("str");
		if(core_enterprise != null && !Utils.isEmpty(core_enterprise.getId())){
			core_enterprise = coreEnterpriseService.get(core_enterprise.getId());
			request.setAttribute("core_enterprise", core_enterprise);
		}
		model.addAttribute("str", str);
		return "platform/hxqyEditInfo";
	}
	
	
	/**
	 * 平台----核心企业详情页
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "platform-info")
	public String enterpriseInfo(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response, Model model){
		if(!Utils.isEmpty(id)){
			Core_enterprise core_enterprise = coreEnterpriseService.get(id);
			model.addAttribute("core_enterprise", core_enterprise);
		}
		return "platform/hxqyShowInfo";
	}
	
	
	/**
	 * 核心企业主页
	 * @return
	 */
	@RequestMapping("/hxqy-home")
	public String hxqyHome(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(core_enterprise != null && !Utils.isEmpty(core_enterprise.getId())) {
			core_enterprise = coreEnterpriseService.get(core_enterprise.getId());
			String availableQuota = coreEnterpriseService.getAvailableQuota(core_enterprise);
			if(core_enterprise.getParamsId() != null) {
				core_enterprise.getParamsId().setAvailableQuota(availableQuota);
				if(Utils.isEmpty(core_enterprise.getParamsId().getAllQuota())) {
					core_enterprise.getParamsId().setAllQuota("0");
				}
			}
			model.addAttribute("core_enterprise", core_enterprise);
			model.addAttribute("profit", coreEnterpriseService.getProfit(core_enterprise));
		}
		return "platform/hxqyHome";
	}
	
	
	/**
	 * 获取供应商签约统计
	 * @param core_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSupplierStatistics")
	public void getSupplierStatistics(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(core_enterprise != null && !Utils.isEmpty(core_enterprise.getId())) {
			renderString(response, coreEnterpriseService.getAllSupplierListByCore(core_enterprise));
		}else {
			renderString(response, null);
		}
	}
	
	
	/**
	 * 获取供应商融资统计
	 * @param core_enterprise
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getFinancingStatistics")
	public void getFinancingStatistics(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response) {
		if(core_enterprise != null && !Utils.isEmpty(core_enterprise.getId())) {
			renderString(response, coreEnterpriseService.getAllFinancingListByCore(core_enterprise));
		}else {
			renderString(response, null);
		}
	}
	
	
	/**
	 * 核心企业---我的公司---基本资料
	 * @return
	 */
	@RequestMapping("/hxqy-company-info")
	public String hxqyComapnyInfo(Core_enterprise core_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			core_enterprise = coreEnterpriseService.get(currentUser.getCore().getId());
			model.addAttribute("core_enterprise", core_enterprise);
		}
		return "platform/hxqyCompanyInfo";
	}
}
