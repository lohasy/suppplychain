package com.jeeplus.modules.bank.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jeeplus.common.paramservice.EnterpriseParamService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.bank.service.BankCreditReviewService;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;

@Controller
@RequestMapping(value = "${adminPath}/BankCreditReview")
public class BankCreditReviewController extends BaseController{
	
	@Autowired	
	BankCreditReviewService bankCreditReviewService;
	
	@Autowired
	EnterpriseParamService enterpriseParamService;
	
	
	/**
	 * 平台----供应商列表
	 */
	@RequestMapping(value = { "gys-index", "" })
	public String gyslist(Supplier_enterprise supplier_enterprise, HttpServletRequest request, String state, HttpServletResponse response, Model model) {
		supplier_enterprise.setState(state);      // 初始化页面上只显示待复核
		Page<Supplier_enterprise> page = bankCreditReviewService.findPage(new Page<Supplier_enterprise>(request, response), supplier_enterprise);		
		model.addAttribute("page", page);
		return "modules/bank/creditReview";
	}

	
	@RequestMapping(value = "form")
	public String form(String id,Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		supplier_enterprise = bankCreditReviewService.get(id);
		Enterprise_params enterprise_params = supplier_enterprise.getParamsId();
		model.addAttribute("supplier_enterprise", supplier_enterprise);	
		model.addAttribute("enterprise_params", enterprise_params);		
		return "modules/bank/creditAudit/review";
	}

	/**
	 * 授信复核--银行复核通过
	 */
	@RequestMapping(value = "save2")
	public String save2(String supplier_enterpriseid, Supplier_enterprise supplier_enterprise, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (supplier_enterprise != null && !"".equals(supplier_enterpriseid)) {
			Supplier_enterprise supplier_enterpriseInDb = bankCreditReviewService.get(supplier_enterpriseid);
			String state = supplier_enterprise.getState();
			if (state != null) {
				switch (state) {
				case "复核通过":
					state = "9";
					break;
				case "复核不通过":
					state = "8";
					break;
				}
			}
			supplier_enterpriseInDb.setState(state);	
			supplier_enterpriseInDb.setRemarks(supplier_enterprise.getRemarks());
			bankCreditReviewService.save(supplier_enterpriseInDb);
		}		 
		return "redirect:" + adminPath + "/BankCreditReview/gys-index";
	}
	
	
}
