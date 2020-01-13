package com.jeeplus.common.xieyicontroller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.controller.PdfUitl;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.xieyiservice.ZiLiaoService;
import com.jeeplus.modules.cyl.bean.Material_info;
import fangfangrj.RandomUtil;
import fangfangrj.Utils;

@Controller
@RequestMapping(value="${adminPath}/ziliao")
public class ZiLiaoController extends BaseController{
	@Autowired
	private ZiLiaoService ziliaoservice;
	
	@ModelAttribute("material_info")
	public Material_info get(@RequestParam(required=false) String id) {
		Material_info entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ziliaoservice.get(id);
		}
		if (entity == null){
			entity = new Material_info();
		}
		return entity;
	} 
	
	
	@RequestMapping(value = {"list", ""})
	public String list(Material_info material_info, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Material_info> page = ziliaoservice.findPage(new Page<Material_info>(request, response), material_info); 
		model.addAttribute("page", page);
		return "platform/dataList";
	}
	
	@RequestMapping(value ="ziliao-info")
	public String ziliaoinfo(@RequestParam(value="id" ,required=false) String id,HttpServletRequest request, HttpServletResponse response, Model model){
		if(!Utils.isEmpty(id)) {
			Material_info material_info = ziliaoservice.get(id);
			model.addAttribute("material_info",material_info);
		}
		return"platform/addZiLiao";
	}
	
	@RequestMapping("add")
	public String add(@RequestParam("file")  MultipartFile uploadfile,Material_info material_info,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectattributes) throws IllegalStateException, IOException {
		String realPath = Global.USERFILES_BASE_URL
        		+ "" + "/ziliao/" ;
		String zlname = uploadfile.getOriginalFilename();
		zlname = zlname.substring(0, zlname.indexOf(".")) + RandomUtil.generateString(20) + zlname.substring(zlname.indexOf("."));
		FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
		uploadfile.transferTo(new File(Global.getUserfilesBaseDir() + realPath + zlname));
		material_info.setUrl(realPath + zlname);
		material_info.setTemplate(zlname);
		ziliaoservice.save(material_info);
		addMessage(redirectattributes, "操作成功！");
		return "redirect:" + adminPath + "/ziliao/list?repage";
	}
	
	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("downfile")
	public String downfile(@RequestParam("id") String id,HttpServletResponse response) throws UnsupportedEncodingException {
		Material_info material_info = ziliaoservice.get(id);
    	PdfUitl.download(response,Global.getUserfilesBaseDir()+material_info.getUrl(),material_info.getTemplate());
    	return null;
	}
	
	@RequestMapping(value = "delete")
	public String deleteuser(HttpServletRequest request,HttpServletResponse response,@RequestParam("ids") String delitems,Model model,RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(delitems)) {
			addMessage(redirectAttributes, "没有找到资料ID标识，删除失败！");
		}else {
			String idArray[] =delitems.split(",");
			for(String id : idArray){
				Material_info material_info = new Material_info();
				material_info.setId(id);
				ziliaoservice.delete(material_info);
			}
			addMessage(redirectAttributes, "删除资料成功！");
		}
		return "redirect:" + adminPath + "/ziliao/list?repage";
 	}
}
