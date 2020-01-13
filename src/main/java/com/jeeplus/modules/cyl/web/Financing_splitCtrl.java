package com.jeeplus.modules.cyl.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.rzgl.service.RzglService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Financing_split;
import com.jeeplus.modules.cyl.bean.Supplier_child;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.service.Financing_splitService;
import com.jeeplus.modules.cyl.service.SupplierChildService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

import fangfangrj.Utils;

/**
 * 融资拆分控制层
 * @author LGT
 */
@Controller
@RequestMapping(value="${adminPath}/financingSplitCtrl")
public class Financing_splitCtrl extends BaseController {

	@Autowired
	private Financing_splitService service;
	
	@Autowired
	private RzglService rzglservice;
	
	@Autowired
	private SupplierChildService supplierChildService;
	
	
	@ModelAttribute("financing_split")
	public Financing_split get(@RequestParam(required=false) String id) {
		Financing_split entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = service.get(id);
		}
		if (entity == null){
			entity = new Financing_split();
		}
		return entity;
	}
	
	/**
	 * 跳转至融资拆分页面
	 * @param financing_split
	 * @return
	 */
	@RequestMapping(value = {"toFinancingSplitPage", ""})
	public String toFinancingSplitPage(@RequestParam(value="billId", required=false) String billId, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			model.addAttribute("currentUser", currentUser.getSupplier().getId());
			
			//查询当前单据可拆分的下级供应商列表
			Supplier_child supplierChild = new Supplier_child();
			Supplier_enterprise supplier = new Supplier_enterprise();
			supplier.setId(currentUser.getSupplier().getId());
			supplierChild.setSupplierEnterpriseId(supplier);
			List<Supplier_child> supplierChildList = supplierChildService.findList(supplierChild);
			model.addAttribute("supplierChilds", supplierChildList);
		}
		Bill_info bill = rzglservice.get(billId);
		model.addAttribute("billInfo", bill);
		
		//根据单据查询历史拆分
		Financing_split financing_split = new Financing_split();
		financing_split.setBillId(bill);
		List<Financing_split> list = service.findList(financing_split);
		model.addAttribute("financingSplits", list);
		
		return "platform/gysFinancingSplitPage";
	}
	
	/**
	 * 供应商----融资拆分
	 * @param params
	 * @param financing_split
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"gysFinancingSplit", ""})
	public String gysFinancingSplit(@RequestParam(value="params", required=false) String params, Financing_split financing_split, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(!Utils.isEmpty(params)) {
			//先根据单据全部删除
			service.deleteByBill(financing_split);
			
			//批量新增
			String[] paramArr = params.split(",");
			if(paramArr != null && paramArr.length > 0) {
				for(String item : paramArr) {
					if(!Utils.isEmpty(item)) {
						String[] itemArr = item.split(";");
						if(itemArr != null && itemArr.length == 2) {
							String amount = itemArr[0];
							String gysId = itemArr[1];
							Supplier_enterprise supplierChildId = new Supplier_enterprise();
							supplierChildId.setId(gysId);
							financing_split.setId(null);
							financing_split.preInsert();
							financing_split.setAmount(amount);
							financing_split.setSupplierChildId(supplierChildId);
							service.insert(financing_split);
						}
					}
				}
			}
		}
		
		addMessage(redirectAttributes, "融资拆分成功！");
		return "redirect:" + adminPath + "/yfzk/gyslist";
	}
	
	/**
	 * 删除融资拆分
	 * @param financing_split
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"deleteFinancingSplit", ""})
	public String deleteFinancingSplit(Financing_split financing_split, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String billId = "";
		if(financing_split != null && financing_split.getId() != null) {
			financing_split = service.get(financing_split.getId());
			if(financing_split.getBillId() != null) {
				billId = financing_split.getBillId().getId();
			}
			service.delete(financing_split);
		}
		return "redirect:" + adminPath + "/financingSplitCtrl/toFinancingSplitPage?billId="+ billId;
	}
	
}
