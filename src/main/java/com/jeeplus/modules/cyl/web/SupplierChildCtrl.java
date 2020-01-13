package com.jeeplus.modules.cyl.web;

import com.jeeplus.common.constant.CxConst;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.sms.SMSUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.Supplier_child;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.service.SupplierChildService;
import com.jeeplus.modules.sys.entity.SystemConfig;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemConfigService;
import com.jeeplus.modules.sys.utils.UserUtils;
import fangfangrj.RandomUtil;
import fangfangrj.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 供应商与子级供应商关系控制层
 * @author LGT
 */
@Controller
@RequestMapping(value="${adminPath}/supplierChildCtrl")
public class SupplierChildCtrl extends BaseController {

	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private SupplierChildService service;
	
	@Autowired
	private GysService gysService;
	
	
	@ModelAttribute("supplier_child")
	public Supplier_child get(@RequestParam(required=false) String id) {
		Supplier_child entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = service.get(id);
		}
		if (entity == null){
			entity = new Supplier_child();
		}
		return entity;
	}
	
	
	/**
	 * 供应商----邀请子级供应商列表
	 */
	@RequestMapping(value = {"invitation-gys", ""})
	public String invitationGys(Supplier_child core_supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		long yiYaoQingCount = 0;
		long yiZhuCeCount = 0;
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(core_supplier.getSupplierEnterpriseId() == null) {
				core_supplier.setSupplierEnterpriseId(new Supplier_enterprise());
			}
			core_supplier.getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			
			//获取统计条数
			Supplier_child cs = new Supplier_child();
			cs.setSupplierEnterpriseId(new Supplier_enterprise());
			cs.getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			cs.setState("1");
			yiYaoQingCount = service.findCount(cs);
			cs.setState("2");
			yiZhuCeCount = service.findCount(cs);
		}
		Page<Supplier_child> page = service.findPage(new Page<Supplier_child>(request, response), core_supplier);
		model.addAttribute("page", page);
		model.addAttribute("invitationCount", yiYaoQingCount);
		model.addAttribute("registerCount", yiZhuCeCount);
		return "platform/gysInvitationGys";
	}
	
	
	/**
	 * 供应商---添加子级供应商
	 * @param core_supplier
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"hxqy-addGys", ""})
	public String hxqyAddGys(Supplier_child core_supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "platform/gysAddGys";
	}
	
	
	/**
	 * 供应商---保存供应商
	 * @param core_supplier
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"hxqy-saveGys", ""})
	public String hxqySaveGys(Supplier_child core_supplier, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(core_supplier.getSupplierChildId() != null) {
			core_supplier.getSupplierChildId().setState("0");
		}
		gysService.save(core_supplier.getSupplierChildId());
		core_supplier.setExportTime(new Date());
		core_supplier.setState("0");
		service.insert(core_supplier);
		addMessage(redirectAttributes, "添加供应商成功！");
		return "redirect:" + adminPath + "/supplierChildCtrl/invitation-gys?repage";
	}
	
	
	/**
	 * 批量邀请供应商
	 * @param core_supplier
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"batch-invitationGys", ""})
	public String batchInvitationGys(Supplier_child core_supplier, @RequestParam("ids") String ids, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(ids)) {
			addMessage(model, "无id集合，邀请失败！");
		}else {
			SystemConfig config = systemConfigService.get("1");//获取短信配置的用户名和密码
			String idArray[] = ids.split(",");
			if(core_supplier.getSupplierChildId() == null) {
				core_supplier.setSupplierChildId(new Supplier_enterprise());
			}
			Supplier_child dt = null;
			for(String id : idArray){
				core_supplier.getSupplierChildId().setId(id);
				List<Supplier_child> list = service.findList(core_supplier);
				if(list != null && list.size() > 0) {
					dt = list.get(0);
					if("0".equals(dt.getState()) || "1".equals(dt.getState())) {
						dt.setState("1");
						dt.setInvitationTime(new Date());
						String messageCode = RandomUtil.generateString(6);
						dt.setInvitationCode(messageCode);
						service.update(dt);
						
						//发送短信邀请
						try {
							//邀请码需要参数传递进来，进行字符串拼接（第三个参数：接收方手机号，第四个参数：模板取值，第五个参数：模板编号）
							String content = "{\"code\":\""+ messageCode +"\",\"enterprise\":\""+ dt.getSupplierEnterpriseId().getName() +"\"" +
//									",\"url\":\""+ CxConst.PROD_URL +"\"" +
									"}";
							String result = SMSUtils.send(config.getSmsName(), config.getSmsPassword(), dt.getSupplierChildId().getAgencyPhone(), content, CxConst.SMS_TEMPLATE_CODE);
							if (result != null && result.indexOf("\"stat\":\"100\"") == -1) {
								addMessage(redirectAttributes, "短信发送失败，错误代码："+ result +"，请联系管理员！");
							}else{
								addMessage(redirectAttributes, "邀请成功！");
							}
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				}
			}
		}
		return "redirect:" + adminPath + "/supplierChildCtrl/invitation-gys";
	}
	
	
	/**
	 * 供应商---供应商列表
	 */
	@RequestMapping(value = {"hxqyGys-list", ""})
	public String hxqyGysList(Supplier_child core_supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		long state1 = 0;
		long state2 = 0;
		long state3 = 0;
		long state4 = 0;
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getSupplier() != null && !Utils.isEmpty(currentUser.getSupplier().getId())) {
			if(core_supplier.getSupplierEnterpriseId() == null) {
				core_supplier.setSupplierEnterpriseId(new Supplier_enterprise());
			}
			core_supplier.getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			
			//获取统计数据
			Supplier_child cs = new Supplier_child();
			cs.setSupplierEnterpriseId(new Supplier_enterprise());
			cs.getSupplierEnterpriseId().setId(currentUser.getSupplier().getId());
			cs.setSupplierChildId(new Supplier_enterprise());
			cs.getSupplierChildId().setState("0");
			state1 = service.findCount(cs);
			cs.getSupplierChildId().setState("1");
			state2 = service.findCount(cs);
			cs.getSupplierChildId().setState("3");
			state3 = service.findCount(cs);
			cs.getSupplierChildId().setState("4");
			state4 = service.findCount(cs);
		}
		Page<Supplier_child> page = service.findPage(new Page<Supplier_child>(request, response), core_supplier);
		if(page != null && page.getList() != null && page.getList().size() > 0) {
			for (Supplier_child item : page.getList()) {
				if(item != null && item.getSupplierChildId() != null &&
						item.getSupplierChildId().getParamsId() != null && !Utils.isEmpty(item.getSupplierChildId().getId())) {
					if(Utils.isEmpty(item.getSupplierChildId().getParamsId().getAllQuota())) {
						item.getSupplierChildId().getParamsId().setAllQuota("0");
					}
					item.getSupplierChildId().getParamsId().setAvailableQuota(gysService.getAvailableQuota(item.getSupplierChildId()));
				}
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("waitSubmit", state1);
		model.addAttribute("waitExamine", state2);
		model.addAttribute("waitContract", state3);
		model.addAttribute("haveContract", state4);
		return "platform/gysGysList";
	}
	
}
