package com.jeeplus.common.xieyicontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.xieyiservice.XieyiService;
import com.jeeplus.modules.cyl.bean.Protocol_info;

import fangfangrj.Utils;

@Controller
@RequestMapping(value="${adminPath}/xieyi")
public class XieyiController extends BaseController{
	@Autowired
	private XieyiService xieyiservice;
	
	@ModelAttribute("protocol_info")
	public Protocol_info get(@RequestParam(required=false) String id) {
		Protocol_info entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xieyiservice.get(id);
		}
		if (entity == null){
			entity = new Protocol_info();
		}
		return entity;
	} 
	
	
	@RequestMapping(value = {"list", ""})
	public String list(Protocol_info protocol_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Protocol_info> page = xieyiservice.findPage(new Page<Protocol_info>(request, response), protocol_info);
		model.addAttribute("page", page);
		return "platform/agreementList";
	}
	
	@RequestMapping("editXieyi")
	public String editXieyi(@RequestParam(value="id",required=false) String id, Model model,HttpServletRequest request) {
		if(!Utils.isEmpty(id)) {
			Protocol_info protocol_info = xieyiservice.get(id);
			model.addAttribute("protocol_info",protocol_info);
		}
		model.addAttribute("type", request.getParameter("type"));
		return "platform/editXieyi";
	}
	@RequestMapping("save")
	public String save(Protocol_info protocol_info, RedirectAttributes redirectAttributes) {
		xieyiservice.save(protocol_info);
		addMessage(redirectAttributes, "保存协议成功");
		return "redirect:" + adminPath + "/xieyi/list?repage";
	}
	
	/**
	 * 删除协议
	 * @param request
	 * @param response
	 * @param delitems
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String deleteuser(HttpServletRequest request,HttpServletResponse response,@RequestParam("ids") String delitems,Model model,RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(delitems)) {
			addMessage(redirectAttributes, "没有找到协议ID标识，删除失败！");
		}else {
			String idArray[] =delitems.split(",");
			for(String id : idArray){
				Protocol_info protocol_info = new Protocol_info();
				protocol_info.setId(id);
				xieyiservice.delete(protocol_info);
			}
			addMessage(redirectAttributes, "删除协议成功！");
		}
		return "redirect:" + adminPath + "/xieyi/list?repage";
 	}
}
