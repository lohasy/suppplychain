package com.jeeplus.modules.sys.web;


import com.google.common.collect.Lists;
import com.jeeplus.common.aliyun.BusinessLicenseDistinguishUtil;
import com.jeeplus.common.aliyun.IdCardDistinguishUtil;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.*;
import com.jeeplus.modules.cyl.dao.Supplier_enterpriseDao;
import com.jeeplus.modules.cyl.dao.Supplier_shareholderDao;
import com.jeeplus.modules.cyl.dao.Supplier_userDao;
import com.jeeplus.modules.cyl.service.CoreSupplierService;
import com.jeeplus.modules.cyl.service.SupplierChildService;
import com.jeeplus.modules.sys.dao.AreaDao;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.*;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemConfigService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.CodeUtil;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.tools.utils.TwoDimensionCode;
import fangfangrj.RandomUtil;
import fangfangrj.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 用户Controller
 * @author jeeplus
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/register")
public class RegisterController extends BaseController {


	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private CoreSupplierService service;
	
	@Autowired
	private SupplierChildService scService;
	
	@Autowired
	private GysService gysservice;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private Supplier_enterpriseDao supplier_enterpriseDao;
	
	@Autowired
	private Supplier_userDao supplierUserDao;
	
	@Autowired
	private Supplier_shareholderDao supplier_shareholderDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AreaDao areaDao;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}
	
	
	
	@RequestMapping(value = {"index",""})
	public String register(User user, Model model) {
		return "modules/sys/register";
	}

	@RequestMapping(value = "registerUser")
	public String registerUser(  HttpServletRequest request,HttpServletResponse response, boolean mobileLogin, String randomCode, String roleName, User user, Model model, RedirectAttributes redirectAttributes) {
		
	
		//验证手机号是否已经注册
		
		if(userDao.findUniqueByProperty("mobile", user.getMobile()) != null){
			// 如果是手机登录，则返回JSON字符串
			if (mobileLogin){
				AjaxJson j = new AjaxJson();
				j.setSuccess(false);
				j.setErrorCode("1");
				j.setMsg("手机号已经被使用！");
		        return renderString(response, j.getJsonStr());
			}else{
				addMessage(model, "手机号已经被使用!");
				return register(user, model);
			}
		}
		
		//验证用户是否已经注册
		
		if(userDao.findUniqueByProperty("login_name", user.getLoginName()) != null){
			// 如果是手机登录，则返回JSON字符串
			if (mobileLogin){
				AjaxJson j = new AjaxJson();
				j.setSuccess(false);
				j.setErrorCode("2");
				j.setMsg("用户名已经被注册！");
		        return renderString(response, j.getJsonStr());
			}else{
				addMessage(model, "用户名已经被注册!");
				return register(user, model);
			}
		}
		
		//验证短信内容
		if(!randomCode.equals(request.getSession().getServletContext().getAttribute(user.getMobile()))){
			// 如果是手机登录，则返回JSON字符串
			if (mobileLogin){
				AjaxJson j = new AjaxJson();
				j.setSuccess(false);
				j.setErrorCode("3");
				j.setMsg("手机验证码不正确!");
		        return renderString(response, j.getJsonStr());
			}else{
				addMessage(model, "手机验证码不正确!");
				return register(user, model);
			}
		}
		
		
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		Role role = systemService.getRoleByEnname(roleName);
		String officeCode = "1000";
		if(roleName.equals("patient")){
			officeCode = "1001";
		}
		Office office = officeService.getByCode(officeCode);
		// 密码MD5加密
		user.setPassword(SystemService.entryptPassword(user.getPassword()));
		if (systemService.getUserByLoginName(user.getLoginName()) != null){
			addMessage(model, "注册用户'" + user.getLoginName() + "'失败，用户名已存在");
			return register(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		roleList.add(role);
		user.setRoleList(roleList);
		//保存机构
		user.setCompany(office);
		user.setOffice(office);
		//生成用户二维码，使用登录名
		String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
		+ user.getId() + "/qrcode/";
		FileUtils.createDirectory(realPath);
		String name= user.getId()+".png"; //encoderImgId此处二维码的图片名
		String filePath = realPath + name;  //存放路径
		TwoDimensionCode.encoderQRCode(user.getLoginName(), filePath, "png");//执行生成二维码
		user.setQrCode(request.getContextPath()+Global.USERFILES_BASE_URL
			+  user.getId()  + "/qrcode/"+name);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		request.getSession().getServletContext().removeAttribute(user.getMobile());//清除验证码
		
		// 如果是手机登录，则返回JSON字符串
		if (mobileLogin){
			AjaxJson j = new AjaxJson();
			j.setSuccess(true);
			j.setMsg("注册用户'" + user.getLoginName() + "'成功");
	        return renderString(response, j);
		}
		
		
		addMessage(redirectAttributes, "注册用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/login";
	}
	
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @param mobile
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "getRegisterCode")
	@ResponseBody
	public AjaxJson getRegisterCode(HttpServletRequest request,HttpServletResponse response, String mobile,
			Model model, RedirectAttributes redirectAttributes) {
		
		SystemConfig config = systemConfigService.get("1");
		
		AjaxJson j = new AjaxJson();
		
		//验证手机号是否已经注册
		if(userDao.findUniqueByProperty("mobile", mobile) != null){
			
				j.setSuccess(false);
				j.setErrorCode("1");
				j.setMsg("手机号已经被使用！");
		        return j;
		}
		
		String randomCode = String.valueOf((int) (Math.random() * 9000 + 1000));
		try {
			String result = UserUtils.sendRandomCode(config.getSmsName(),config.getSmsPassword(), mobile, randomCode);
			if (!result.equals("100")) {
				j.setSuccess(false);
				j.setErrorCode("2");
				j.setMsg("短信发送失败，错误代码："+result+"，请联系管理员。");
			}else{
				j.setSuccess(true);
				j.setErrorCode("-1");
				j.setMsg("短信发送成功!");
				request.getSession().getServletContext().setAttribute(mobile, randomCode);
			}
		} catch (IOException e) {
			j.setSuccess(false);
			j.setErrorCode("3");
			j.setMsg("因未知原因导致短信发送失败，请联系管理员。");
		}
		return j;
	}
	
	
	/**
	 * web端ajax验证手机验证码是否正确
	 */
	@ResponseBody
	@RequestMapping(value = "validateMobileCode")
	public boolean validateMobileCode(HttpServletRequest request,
			String mobile, String randomCode) {
		if (randomCode.equals(request.getSession().getServletContext().getAttribute(mobile))) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 回写随机验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "get-validateCode")
	public void getCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			CodeUtil.getCode(request, response);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据邀请码获取企业信息
	 * @param code
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "get-companyInfo-code")
	@ResponseBody
	public void getCompanyInfoByInvitationCode(@RequestParam(value="invitationCode", required =false) String code, User user, HttpServletRequest request, HttpServletResponse response) {
		if(!Utils.isEmpty(code)) {
			//校验邀请码是否存在
			Core_supplier cs = new Core_supplier();
			cs.setInvitationCode(code);
			List<Core_supplier> list = service.findList(cs);
			
			Supplier_child sc = new Supplier_child();
			sc.setInvitationCode(code);
			List<Supplier_child> scList = scService.findList(sc);
			
			if((list == null || list.size() == 0) && (scList == null || scList.size() == 0)) {
				renderString(response, "邀请码不存在！");
			}else {
				if(list != null && list.size() > 0) {
					cs = list.get(0);
					if(cs.getSupplierEnterpriseId() != null) {
						renderString(response, "ok+" + cs.getSupplierEnterpriseId().getName());
					}else {
						renderString(response, "供应商不存在！");
					}
				}else {
					sc = scList.get(0);
					if(sc.getSupplierChildId() != null) {
						renderString(response, "ok+" + sc.getSupplierChildId().getName());
					}else {
						renderString(response, "供应商不存在！");
					}
				}
			}
		}else {
			renderString(response, "邀请码不能为空！");
		}
	}
	
	
	/**
	 * 获取手机校验码
	 * @param mobile
	 * @param vCode
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "get-mobileCode")
	@ResponseBody
	public void getMobileValidateCode(@RequestParam(value="validateCode", required =false) String vCode, User user, HttpServletRequest request, HttpServletResponse response) {
		if(user != null && !fangfangrj.Utils.isEmpty(user.getMobile()) && !fangfangrj.Utils.isEmpty(vCode)) {
			//校验验证码
			if(request.getSession().getAttribute("code") != null) {
				if(!vCode.equalsIgnoreCase(request.getSession().getAttribute("code").toString())) {
					renderString(response, "003");
				}else {
					//验证手机号是否已经注册
					User uTmp = new User();
					uTmp.setMobile(user.getMobile());
					List<User> uList = userDao.findList(uTmp);
					if(uList != null && uList.size() > 0){
						renderString(response, "006");
					}else {
						SystemConfig config = systemConfigService.get("1");
						String randomCode = String.valueOf((int) (Math.random() * 9000 + 1000));
						try {
							String result = UserUtils.sendRandomCode(config.getSmsName(), config.getSmsPassword(), user.getMobile(), randomCode);
							if (result != null && result.indexOf("\"stat\":\"100\"") == -1) {
								renderString(response, "短信发送失败，错误代码："+ result +"，请联系管理员。");
							}else{
								renderString(response, "000");
								request.getSession().getServletContext().setAttribute(user.getMobile(), randomCode);
							}
						} catch (IOException e) {
							renderString(response, e.getMessage());
						}
					}
				}
			}
		}else {
			renderString(response, "007");
		}
	}
	
	
	/**
	 * 获取手机验证码
	 * @param mobile
	 * @param vCode
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "get-mobile-code")
	@ResponseBody
	public void getMobileCode(User user, HttpServletRequest request, HttpServletResponse response) {
		if(user != null && !fangfangrj.Utils.isEmpty(user.getMobile())) {
			//校验验证码
			SystemConfig config = systemConfigService.get("1");
			String randomCode = String.valueOf((int) (Math.random() * 9000 + 1000));
			try {
				String result = UserUtils.sendRandomCode(config.getSmsName(), config.getSmsPassword(), user.getMobile(), randomCode);
				if (result != null && result.indexOf("\"stat\":\"100\"") == -1) {
					renderString(response, "短信发送失败，错误代码："+ result +"，请联系管理员。");
				}else{
					renderString(response, "000");
					request.getSession().getServletContext().setAttribute(user.getMobile(), randomCode);
				}
			} catch (IOException e) {
				renderString(response, e.getMessage());
			}
		}else {
			renderString(response, "007");
		}
	}
	
	
	/**
	 * 检验用户名是否已注册
	 * @param mobile
	 * @param vCode
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "validate-loginName")
	@ResponseBody
	public void validateLoginName(User user, HttpServletRequest request, HttpServletResponse response) {
		if(user == null || Utils.isEmpty(user.getLoginName())) {
			renderString(response, "用户登录名为空，无法验证！");
		}else {
			List<User> list = userDao.findList(user);
			if(list != null && list.size() > 0){
				renderString(response, "用户登录名已存在！");
			}else {
				renderString(response, "用户登录名可以注册！");
			}
		}
	}
	
	
	/**
	 * 检验用户手机号是否已注册
	 * @param mobile
	 * @param vCode
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "validate-loginPhone")
	@ResponseBody
	public void validateLoginPhone(User user, HttpServletRequest request, HttpServletResponse response) {
		if(user == null || Utils.isEmpty(user.getMobile())) {
			renderString(response, "用户手机号为空，无法验证！");
		}else {
			List<User> list = userDao.findList(user);
			if(list != null && list.size() > 0){
				renderString(response, "用户手机号已存在！");
			}else {
				renderString(response, "用户手机号可以注册！");
			}
		}
	}
	
	
	/**
	 * 异步文件上传
	 * @param mobile
	 * @param vCode
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "async-uploadFile")
	@ResponseBody
	public void asyncUploadFile(@RequestParam("uploadfile") MultipartFile uploadfile, HttpServletRequest request, HttpServletResponse response) {
		String realPath = Global.USERFILES_BASE_URL + "" + "/qygl/images/";
		Map<String, Object> data = new HashMap<String, Object>();
		
		if(uploadfile != null && !Utils.isEmpty(uploadfile.getOriginalFilename())) {
			String businessimage = uploadfile.getOriginalFilename();
			businessimage = businessimage.substring(0, businessimage.indexOf(".")) + RandomUtil.generateString(20) + businessimage.substring(businessimage.indexOf("."));
			FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
			try {
				uploadfile.transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessimage));
				String url = request.getContextPath() + realPath + businessimage;
				String absoluteUrl = request.getServletContext().getRealPath(url.substring(url.indexOf("/userfiles")));
				data.put("msg", "OK");
				data.put("data", absoluteUrl);
				data.put("url", url);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				data.put("msg", e.getMessage());
				data.put("data", "");
				data.put("url", "");
			}
		}else {
			data.put("msg", "文件为空，无法上传！");
			data.put("data", "");
			data.put("url", "");
		}
		renderString(response, data);
	}
	
	
	/**
	 * 身份证信息识别
	 * @param mobile
	 * @param vCode
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "idCard-distinguish")
	@ResponseBody
	public void idCardDistinguish(@RequestParam("picture_legal") MultipartFile uploadfile, HttpServletRequest request, HttpServletResponse response) {
		String realPath = Global.USERFILES_BASE_URL + "" + "/qygl/images/";
		Map<String, Object> data = new HashMap<String, Object>();
		String type = request.getParameter("type");
		
		if(uploadfile != null && !Utils.isEmpty(uploadfile.getOriginalFilename())) {
			String businessimage = uploadfile.getOriginalFilename();
			businessimage = businessimage.substring(0, businessimage.indexOf(".")) + RandomUtil.generateString(20) + businessimage.substring(businessimage.indexOf("."));
			FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
			try {
				uploadfile.transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessimage));
				String url = request.getContextPath() + realPath + businessimage;
				String absoluteUrl = request.getServletContext().getRealPath(url.substring(url.indexOf("/userfiles")));
				data.put("msg", "OK");
				data.put("data", IdCardDistinguishUtil.readIdCardInfoByApi(absoluteUrl, type));
				data.put("url", url);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				data.put("msg", e.getMessage());
				data.put("data", "");
				data.put("url", "");
			}
		}else {
			data.put("msg", "身份证文件为空，无法识别！");
			data.put("data", "");
			data.put("url", "");
		}
		renderString(response, data);
	}
	
	
	/**
	 * 营业执照信息识别
	 * @param mobile
	 * @param vCode
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "businessLicense-distinguish")
	@ResponseBody
	public void BusinessLicenseDistinguish(@RequestParam("picture_license") MultipartFile uploadfile, HttpServletRequest request, HttpServletResponse response) {
		String realPath = Global.USERFILES_BASE_URL + "" + "/qygl/images/";
		Map<String, Object> data = new HashMap<String, Object>();
		
		if(uploadfile != null && !Utils.isEmpty(uploadfile.getOriginalFilename())) {
			String businessimage = uploadfile.getOriginalFilename();
			businessimage = businessimage.substring(0, businessimage.indexOf(".")) + RandomUtil.generateString(20) + businessimage.substring(businessimage.indexOf("."));
			FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
			try {
				uploadfile.transferTo(new File(Global.getUserfilesBaseDir() + realPath + businessimage));
				String url = request.getContextPath() + realPath + businessimage;
				String absoluteUrl = request.getServletContext().getRealPath(url.substring(url.indexOf("/userfiles")));
				data.put("msg", "OK");
				data.put("data", BusinessLicenseDistinguishUtil.readBusinessLicenseInfoByApi(absoluteUrl));
				data.put("url", url);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				data.put("msg", e.getMessage());
				data.put("data", "");
				data.put("url", "");
			}
		}else {
			data.put("msg", "营业执照文件为空，无法识别！");
			data.put("data", "");
			data.put("url", "");
		}
		renderString(response, data);
	}
	
	
	/**
	 * 注册供应商用户
	 * @param code
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "register-supplierUser")
	public String RegisterSupplierUser(@RequestParam(value="invitationCode", required =false) String code, @RequestParam(value="validateCode", required =false) String vCode, User user, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(!Utils.isEmpty(code) && !Utils.isEmpty(vCode) && user != null && !Utils.isEmpty(user.getMobile())) {
			//检验手机验证码是否一致
			if (!vCode.equals(request.getSession().getServletContext().getAttribute(user.getMobile()))) {
				model.addAttribute("message", "手机验证码输入错误！");
				model.addAttribute("invitationCode", code);
				model.addAttribute("validateCode", vCode);
				model.addAttribute("user", user);
				return "modules/index/supplierRegister";
			}
			
			//校验邀请码是否存在
			Core_supplier cs = new Core_supplier();
			cs.setInvitationCode(code);
			List<Core_supplier> list = service.findList(cs);
			
			Supplier_child sc = new Supplier_child();
			sc.setInvitationCode(code);
			List<Supplier_child> scList = scService.findList(sc);
			
			if((list == null || list.size() == 0) && (scList == null || scList.size() == 0)) {
				model.addAttribute("message", "邀请码不存在！");
				model.addAttribute("invitationCode", code);
				model.addAttribute("validateCode", vCode);
				model.addAttribute("user", user);
				return "modules/index/supplierRegister";
			}else {
				if(list != null && list.size() > 0) {
					cs = list.get(0);
				}else {
					sc = scList.get(0);
				}
				
				//验证手机号是否已经注册
				User uTmp = new User();
				uTmp.setMobile(user.getMobile());
				List<User> uList = userDao.findList(uTmp);
				if(uList != null && uList.size() > 0){
					model.addAttribute("message", "手机号码已注册！");
					model.addAttribute("invitationCode", code);
					model.addAttribute("validateCode", vCode);
					model.addAttribute("user", user);
					return "modules/index/supplierRegister";
				}
				
				//验证用户是否已经注册
				uTmp.setMobile(null);
				uTmp.setLoginName(user.getLoginName());
				List<User> uListLoginName = userDao.findList(uTmp);
				if(uListLoginName != null && uListLoginName.size() > 0){
					model.addAttribute("message", "用户名已注册！");
					model.addAttribute("invitationCode", code);
					model.addAttribute("validateCode", vCode);
					model.addAttribute("user", user);
					return "modules/index/supplierRegister";
				}
				
				// 密码MD5加密
				user.setPassword(SystemService.entryptPassword(user.getPassword()));
				
				//将用户与角色进行绑定
				List<Role> ros = roleDao.findList(new Role());
				if(ros != null && ros.size() > 0) {
					for(Role r : ros) {
						if(r.getName().indexOf("供应商") != -1 && r.getName().indexOf("操作员") != -1) {
							user.setRole(r);
							List<Role> lt = new ArrayList<Role>();
							lt.add(r);
							user.setRoleList(lt);
						}
					}
				}else {
					model.addAttribute("message", "请先完善角色列表，与管理员联系！");
					model.addAttribute("invitationCode", code);
					model.addAttribute("validateCode", vCode);
					model.addAttribute("user", user);
					return "modules/index/supplierRegister";
				}
				
				//注册用户
				if(user.getRole() != null && !Utils.isEmpty(user.getRole().getId())) {
					user.setCreateDate(new Date());
					user.setUpdateDate(new Date());
					systemService.saveUser(user);
					
					//将用户与供应商进行绑定
					if(list != null && list.size() > 0) {
						Supplier_user su = new Supplier_user();
						su.setUserId(user);
						su.setSupplierEnterpriseId(cs.getSupplierEnterpriseId());
						supplierUserDao.insert(su);
						
						//更新供应商注册时间
						cs.setRegisterTime(new Date());
						cs.setState("2");
						if(cs.getSupplierEnterpriseId() != null) {
							cs.getSupplierEnterpriseId().setCreateDate(new Date());
							supplier_enterpriseDao.update(cs.getSupplierEnterpriseId());
						}
						service.update(cs);
					}else {
						Supplier_user su = new Supplier_user();
						su.setUserId(user);
						su.setSupplierEnterpriseId(sc.getSupplierChildId());
						supplierUserDao.insert(su);
						
						//更新供应商注册时间
						sc.setRegisterTime(new Date());
						sc.setState("2");
						if(sc.getSupplierChildId() != null) {
							sc.getSupplierChildId().setCreateDate(new Date());
							supplier_enterpriseDao.update(sc.getSupplierChildId());
						}
						scService.update(sc);
					}
					
					// 清除当前用户缓存
					if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
						UserUtils.clearCache();
					}
					request.getSession().getServletContext().removeAttribute(user.getMobile());
					
					model.addAttribute("message", "恭喜您注册成功！");
					model.addAttribute("invitationCode", code);
					model.addAttribute("validateCode", vCode);
					model.addAttribute("user", user);
					return "modules/index/supplierRegister";
				}else {
					model.addAttribute("message", "请先完善供应商角色，与管理员联系！");
					model.addAttribute("invitationCode", code);
					model.addAttribute("validateCode", vCode);
					model.addAttribute("user", user);
					return "modules/index/supplierRegister";
				}
			}
		}else {
			model.addAttribute("message", "注册信息不完善，请详细录入！");
			model.addAttribute("invitationCode", code);
			model.addAttribute("validateCode", vCode);
			model.addAttribute("user", user);
			return "modules/index/supplierRegister";
		}
	}
	
	
	/**
	 * 跳转到供应商注册成功界面
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "to-supplierRegisterSuccess")
	public String toSupplierRegisterSuccess(User user, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		Supplier_user su = new Supplier_user();
		if(user != null && !Utils.isEmpty(user.getId())) {
			user = userDao.get(user.getId());
			su.setUserId(user);
			List<Supplier_user> list = supplierUserDao.findList(su);
			if(list != null && list.size() > 0) {
				su = list.get(0);
			}
		}
		model.addAttribute("supplier_user", su);
		return "modules/index/supplierRegisterSuccess";
	}
	
	
	/**
	 * 跳转到供应商提交资料界面
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "to-supplierSubmitData")
	public String toSupplierSubmitData(User user, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		Supplier_user su = new Supplier_user();
		if(user != null && !Utils.isEmpty(user.getId())) {
			user = userDao.get(user.getId());
			su.setUserId(user);
			List<Supplier_user> list = supplierUserDao.findList(su);
			if(list != null && list.size() > 0) {
				su = list.get(0);
				
				//获取供应商股东集合
				if(su != null && su.getSupplierEnterpriseId() != null && !Utils.isEmpty(su.getSupplierEnterpriseId().getId())) {
					Supplier_shareholder supplier_shareholder = new Supplier_shareholder();
					supplier_shareholder.setSupplierEnterpriseId(su.getSupplierEnterpriseId());
					model.addAttribute("shareHolders", supplier_shareholderDao.findList(supplier_shareholder));
				}
			}
		}
		model.addAttribute("supplier_user", su);
		return "modules/index/supplierSubmitData";
	}
	
	
	/**
	 * 跳转到供应商签约界面
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "to-supplierContract")
	public String toSupplierContract(User user, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		Supplier_user su = new Supplier_user();
		if(user != null && !Utils.isEmpty(user.getId())) {
			user = userDao.get(user.getId());
			su.setUserId(user);
			List<Supplier_user> list = supplierUserDao.findList(su);
			if(list != null && list.size() > 0) {
				su = list.get(0);
			}
		}
		model.addAttribute("supplier_user", su);
		return "modules/index/supplierContract";
	}
	
	
	/**
	 * 供应商进行签约
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "having-supplierContract")
	public String havingSupplierContract(@RequestParam(value="validateCode", required =false) String vCode, Supplier_user supplier_user, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!Utils.isEmpty(vCode) && supplier_user != null && supplier_user.getSupplierEnterpriseId() != null && !Utils.isEmpty(supplier_user.getSupplierEnterpriseId().getId()) && !Utils.isEmpty(supplier_user.getSupplierEnterpriseId().getAgencyPhone())) {
			//检验手机验证码是否一致
			if (!vCode.equals(request.getSession().getServletContext().getAttribute(supplier_user.getSupplierEnterpriseId().getAgencyPhone()))) {
				model.addAttribute("message", "签约授权码输入错误！");
				model.addAttribute("supplier_user", supplier_user);
				return "modules/index/supplierContract";
			}
			
			supplier_user.setSupplierEnterpriseId(supplier_enterpriseDao.get(supplier_user.getSupplierEnterpriseId().getId()));
			supplier_user.getSupplierEnterpriseId().setState("4");
			supplier_enterpriseDao.update(supplier_user.getSupplierEnterpriseId());
			request.getSession().getServletContext().removeAttribute(supplier_user.getSupplierEnterpriseId().getAgencyPhone());
			model.addAttribute("message", "签约成功！");
		}else {
			model.addAttribute("message", "供应商无效，签约失败！");
		}
		model.addAttribute("supplier_user", supplier_user);
		return "modules/index/supplierContract";
	}
	
	
	/**
	 * 异步获取省市区域
	 * @param area
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "aynsc-getCityArea")
	@ResponseBody
	public void aynscGetCityArea(Area area, HttpServletRequest request, HttpServletResponse response) {
		renderString(response, areaDao.findList(area));
	}
	
	
	/**
	 * 供应商提交资料
	 * @param supplier_user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "submitDataBySupplier")
	public String submitDataBySupplier(@RequestParam(value="validateCode", required =false) String vCode, Supplier_user supplier_user, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!Utils.isEmpty(vCode) && supplier_user != null && supplier_user.getSupplierEnterpriseId() != null && !Utils.isEmpty(supplier_user.getSupplierEnterpriseId().getId()) && !Utils.isEmpty(supplier_user.getSupplierEnterpriseId().getAgencyPhone())) {
			//检验手机验证码是否一致
			if (!vCode.equals(request.getSession().getServletContext().getAttribute(supplier_user.getSupplierEnterpriseId().getAgencyPhone()))) {
				model.addAttribute("message", "手机验证码输入错误！");
				model.addAttribute("supplier_user", supplier_user);
				return "modules/index/supplierSubmitData";
			}
			
			Supplier_enterprise supplier_enterprise = supplier_user.getSupplierEnterpriseId();
			
			try {
				supplier_enterprise.setState("1");
				gysservice.save(supplier_enterprise);
				request.getSession().getServletContext().removeAttribute(supplier_user.getSupplierEnterpriseId().getAgencyPhone());
				
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
				
				model.addAttribute("message", "资料提交成功，等待平台审核！");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("message", e.getMessage());
				model.addAttribute("supplier_user", supplier_user);
				return "modules/index/supplierSubmitData";
			}
		}else {
			model.addAttribute("message", "信息录入不全，无法提交！");
			model.addAttribute("supplier_user", supplier_user);
			return "modules/index/supplierSubmitData";
		}
		model.addAttribute("supplier_user", supplier_user);
		return "modules/index/supplierSubmitData";
	}

}
