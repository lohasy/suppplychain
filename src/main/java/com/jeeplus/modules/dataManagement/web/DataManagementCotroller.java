package com.jeeplus.modules.dataManagement.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.rzgl.service.QuanBuService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Core_enterprise;
import com.jeeplus.modules.cyl.bean.Financing_info;
import com.jeeplus.modules.dataManagement.service.DateManagementService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.test.entity.tree.TestTree;

import fangfangrj.Utils; 
/**
 * @author Administrator
 * 融资管理全部
 */
@Controller
@RequestMapping(value="${adminPath}/dataManagement")
public class DataManagementCotroller extends BaseController{

	@Autowired
	private DateManagementService dateManagementService;
	
	@Autowired
	private QuanBuService quanbuservice;
	
	
	@ModelAttribute("bill_info")
	public Bill_info get(@RequestParam(required=false) String id) {
		Bill_info entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dateManagementService.get(id);
		}
		if (entity == null){
			entity = new Bill_info();
		}
		return entity;
	}
	/**
	 * 机构列表页面
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
		Page<Bill_info> page = dateManagementService.findPage(new Page<Bill_info>(request, response), bill_info); 
		model.addAttribute("page", page);
		return "modules/dateManagement/dateManagement";
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
			addMessage(redirectAttributes, "导出融资失败！失败信息："+e.getMessage());
		}
    }
	
	
	 
}
