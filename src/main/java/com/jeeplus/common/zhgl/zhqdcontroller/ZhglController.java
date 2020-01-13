package com.jeeplus.common.zhgl.zhqdcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.Account_detailed;
import com.jeeplus.modules.cyl.bean.Core_enterprise;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.hxqy.service.CoreEnterpriseService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

import fangfangrj.Utils;

/**
 * @author Administrator
 *賬戶管理
 */
@Controller
@RequestMapping(value="${adminPath}/zhgl")
public class ZhglController extends BaseController{
	
	@Autowired
	private ZhglService zbglservice;
	
	@Autowired
	private CoreEnterpriseService coreEnterpriseService;
	
	@Autowired
	private GysService gysservice;
	
	@ModelAttribute("account_detailed")
	public Account_detailed get(@RequestParam(required=false) String id) {
		Account_detailed entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zbglservice.get(id);
		}
		if (entity == null){
			entity = new Account_detailed();
		}
		return entity;
	}
	
	
	/**
	 * 核心企业---账户详情
	 * @param account_detailed
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"hxqy-index", ""})
	public String hxqy(Account_detailed account_detailed, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		Core_enterprise core = null;
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			if(account_detailed.getCoreEnterpriseId() == null) {
				account_detailed.setCoreEnterpriseId(new Core_enterprise());
			}
			account_detailed.getCoreEnterpriseId().setId(currentUser.getCore().getId());
			core = coreEnterpriseService.get(currentUser.getCore().getId());
			if(core != null && !Utils.isEmpty(core.getId())) {
				model.addAttribute("profit", coreEnterpriseService.getProfit(core));
			}
		}
		Page<Account_detailed> page = zbglservice.findPage(new Page<Account_detailed>(request, response), account_detailed); 
		model.addAttribute("page", page);
		model.addAttribute("coreEnterpriseId", core);
		return "platform/hxqyAccount";
	}
	
	
	/**
	 * 供应商---账户详情
	 * @param account_detailed
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"gyszhgl-index", ""})
	public String gyszhgl(Account_detailed account_detailed, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(account_detailed.getSupplierEnterpriseId() == null) {
				account_detailed.setSupplierEnterpriseId(new Supplier_enterprise());
			}
			account_detailed.getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			Supplier_enterprise supplier_enterprise = gysservice.get(currentUser.getSupplier().getId());
			model.addAttribute("supplier_enterprise", supplier_enterprise);
		}
		Page<Account_detailed> page = zbglservice.findPage(new Page<Account_detailed>(request, response), account_detailed); 
		model.addAttribute("page", page);
		return "platform/gysAccount";
	}
}
