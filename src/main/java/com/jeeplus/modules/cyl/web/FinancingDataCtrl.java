package com.jeeplus.modules.cyl.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.gyscontroller.GysService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Core_enterprise;
import com.jeeplus.modules.cyl.bean.Payment_list;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Transaction_contract;
import com.jeeplus.modules.cyl.bean.Yyrkzlys_record;
import com.jeeplus.modules.cyl.dao.Bill_infoDao;
import com.jeeplus.modules.cyl.dao.Payment_listDao;
import com.jeeplus.modules.cyl.dao.Transaction_contractDao;
import com.jeeplus.modules.cyl.dao.Yyrkzlys_recordDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

import fangfangrj.Utils;
/**
 * 融资资料相关控制层
 * @author LGT
 */
@Controller
@RequestMapping(value="${adminPath}/fdCtrl")
public class FinancingDataCtrl extends BaseController {

	@Autowired
	private Payment_listDao paymentListDao;
	
	@Autowired
	private Bill_infoDao bill_infoDao;
	
	@Autowired
	private Transaction_contractDao transactionContractDao;
	
	@Autowired
	private Yyrkzlys_recordDao yyrkzlysRecordDao;
	
	@Autowired
	private GysService gysservice;
	
	
	/**
	 * 分页查询付款清单
	 * @param payment_list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("findPaymentListByPage")
	public String findPaymentListByPage(Payment_list payment_list, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			if(payment_list.getCoreEnterpriseId() == null) {
				payment_list.setCoreEnterpriseId(new Core_enterprise());
			}
			payment_list.getCoreEnterpriseId().setId(currentUser.getCore().getId());
		}
		Page<Payment_list> page = new Page<Payment_list>(request, response);
		payment_list.setPage(page);
		page.setList(paymentListDao.findList(payment_list));
		model.addAttribute("page", page);
		if(payment_list != null) {
			model.addAttribute("bill_info", payment_list.getBillId());
		}
		return "platform/paymentList";
	}
	
	
	/**
	 * 批量删除付款清单
	 * @param ids
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("deletePaymentLists")
	public String deletePaymentLists(@RequestParam(value="ids", required=false) String ids, @RequestParam(value="bill_id", required=false) String billId, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(ids)) {
			addMessage(redirectAttributes, "删除标识集合为空，无法删除！");
		}else {
			String idArray[] = ids.split(",");
			Payment_list payment = new Payment_list();
			for(String id : idArray){
				payment.setId(id);
				paymentListDao.delete(payment);
			}
			addMessage(redirectAttributes, "删除成功！");
		}
		return "redirect:" + adminPath + "/fdCtrl/findPaymentListByPage?billId.id="+ billId +"&repage";
	}
	
	
	/**
	 * 分页查询交易合同
	 * @param Transaction_contract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("findTransactionListByPage")
	public String findTransactionListByPage(Transaction_contract transaction_contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			if(transaction_contract.getCoreEnterpriseId() == null) {
				transaction_contract.setCoreEnterpriseId(new Core_enterprise());
			}
			transaction_contract.getCoreEnterpriseId().setId(currentUser.getCore().getId());
		}
		Page<Transaction_contract> page = new Page<Transaction_contract>(request, response);
		transaction_contract.setPage(page);
		page.setList(transactionContractDao.findList(transaction_contract));
		model.addAttribute("page", page);
		if(transaction_contract != null) {
			model.addAttribute("bill_info", transaction_contract.getBillId());
		}
		return "platform/transactionList";
	}
	
	
	/**
	 * 批量删除交易合同
	 * @param ids
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("deleteTransactionLists")
	public String deleteTransactionLists(@RequestParam(value="ids", required=false) String ids, @RequestParam(value="bill_id", required=false) String billId, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(ids)) {
			addMessage(redirectAttributes, "删除标识集合为空，无法删除！");
		}else {
			String idArray[] = ids.split(",");
			Transaction_contract transaction_contract = new Transaction_contract();
			for(String id : idArray){
				transaction_contract.setId(id);
				transactionContractDao.delete(transaction_contract);
			}
			addMessage(redirectAttributes, "删除成功！");
		}
		return "redirect:" + adminPath + "/fdCtrl/findTransactionListByPage?billId.id="+ billId +"&repage";
	}
	
	
	/**
	 * 分页查询医院入库质量验收记录
	 * @param Yyrkzlys_record
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("findYyrkzlysListByPage")
	public String findYyrkzlysListByPage(Yyrkzlys_record yyrkzlys_record, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(currentUser != null && !Utils.isEmpty(currentUser.getId()) && currentUser.getCore() != null && !Utils.isEmpty(currentUser.getCore().getId())) {
			if(yyrkzlys_record.getCoreEnterpriseId() == null) {
				yyrkzlys_record.setCoreEnterpriseId(new Core_enterprise());
			}
			yyrkzlys_record.getCoreEnterpriseId().setId(currentUser.getCore().getId());
		}
		Page<Yyrkzlys_record> page = new Page<Yyrkzlys_record>(request, response);
		yyrkzlys_record.setPage(page);
		page.setList(yyrkzlysRecordDao.findList(yyrkzlys_record));
		model.addAttribute("page", page);
		if(yyrkzlys_record != null) {
			model.addAttribute("bill_info", yyrkzlys_record.getBillId());
		}
		return "platform/YyrkzlysList";
	}
	
	
	/**
	 * 批量删除医院入库质量验收记录
	 * @param ids
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("deleteYyrkzlysLists")
	public String deleteYyrkzlysLists(@RequestParam(value="ids", required=false) String ids, @RequestParam(value="bill_id", required=false) String billId, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(Utils.isEmpty(ids)) {
			addMessage(redirectAttributes, "删除标识集合为空，无法删除！");
		}else {
			String idArray[] = ids.split(",");
			Yyrkzlys_record yyrkzlys_record = new Yyrkzlys_record();
			for(String id : idArray){
				yyrkzlys_record.setId(id);
				yyrkzlysRecordDao.delete(yyrkzlys_record);
			}
			addMessage(redirectAttributes, "删除成功！");
		}
		return "redirect:" + adminPath + "/fdCtrl/findYyrkzlysListByPage?billId.id="+ billId +"&repage";
	}
	
	
	/**
	 * 下载对应模板
	 */
    @RequestMapping(value = "download-template")
    public void downloadTemplete(@RequestParam(value="type", required=false) String type, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			if("1".equals(type)) {
				String fileName = "付款清单模板.xlsx";
	    		List<Payment_list> list = Lists.newArrayList(); 
	    		new ExportExcel("付款清单", Payment_list.class, 1).setDataList(list).write(response, fileName).dispose();
			}else if("2".equals(type)) {
				String fileName = "交易合同模板.xlsx";
	    		List<Transaction_contract> list = Lists.newArrayList(); 
	    		new ExportExcel("交易合同", Transaction_contract.class, 1).setDataList(list).write(response, fileName).dispose();
			}else {
				String fileName = "医院入库质量验收记录模板.xlsx";
	    		List<Yyrkzlys_record> list = Lists.newArrayList(); 
	    		new ExportExcel("医院入库质量验收记录", Yyrkzlys_record.class, 1).setDataList(list).write(response, fileName).dispose();
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载Excel模板失败！失败信息："+e.getMessage());
		}
    }
	
    
    /**
     * 批量导入付款清单Excel数据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "importPaymentExcelData", method=RequestMethod.POST)
    public String importPaymentExcelData(@RequestParam(value="file", required=false) MultipartFile file, @RequestParam(value="bill_id", required=false) String billId, Payment_list payment_list, RedirectAttributes redirectAttributes) {
		try {
			if(file == null || payment_list == null) {
				addMessage(redirectAttributes, "缺少Excel文件或付款清单信息不全，无法导入！");
				return "redirect:" + adminPath + "/fdCtrl/findPaymentListByPage?billId.id="+ billId +"&repage";
			}
			int successNum = 0;
			int failureNum = 0;
			String num = "";
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if(ei != null) {
				List<Payment_list> list = ei.getDataList(Payment_list.class);
				if(list != null) {
					for (Payment_list entity : list){
						try{
							Payment_list pl = new Payment_list();
							pl.setNum(entity.getNum());
							List<Payment_list> lt = paymentListDao.findList(pl);
							if(lt.size() > 0) {
								num += "付款编号："+ entity.getNum() +"已存在！";
							}else {
								Supplier_enterprise name = gysservice.getName(entity.getSupplierName());
								if(name == null) {
									num += "供应商名称："+ entity.getSupplierName() + "不存在！";
								}else {
									//关联单据
									Bill_info bill = new Bill_info();
									bill.setContractNum(entity.getBillContractNum());
									List<Bill_info> lst = bill_infoDao.findList(bill);
									if(lst == null || lst.size() == 0) {
										num += "单据合同号：" + entity.getBillContractNum() + "不存在！";
									}else {
										entity.setSupplierEnterpriseId(name);
										entity.setBillId(lst.get(0));
										entity.setCoreEnterpriseId(payment_list.getCoreEnterpriseId());
										entity.preInsert();
										paymentListDao.insert(entity);
										successNum++;
									}
								}
							}
						}catch (Exception ex) {
							ex.printStackTrace();
							failureNum++;
							continue;
						}
					}
				}
			}
			if(failureNum > 0){
				failureMsg.append("，" + failureNum + "条付款清单导入异常！");
			}
			addMessage(redirectAttributes, "已成功导入" + successNum + "条付款清单记录" + failureMsg + num);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入付款清单失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/fdCtrl/findPaymentListByPage?billId.id="+ billId +"&repage";
    }
    
    
    /**
     * 批量导入交易合同Excel数据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "importTransactionExcelData", method=RequestMethod.POST)
    public String importTransactionExcelData(@RequestParam(value="file", required=false) MultipartFile file, @RequestParam(value="bill_id", required=false) String billId, Transaction_contract transaction_contract, RedirectAttributes redirectAttributes) {
		try {
			if(file == null || transaction_contract == null) {
				addMessage(redirectAttributes, "缺少Excel文件或交易合同信息不全，无法导入！");
				return "redirect:" + adminPath + "/fdCtrl/findTransactionListByPage?billId.id="+ billId +"&repage";
			}
			int successNum = 0;
			int failureNum = 0;
			String num = "";
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if(ei != null) {
				List<Transaction_contract> list = ei.getDataList(Transaction_contract.class);
				if(list != null) {
					for (Transaction_contract entity : list){
						try{
							Transaction_contract tc= new Transaction_contract();
							tc.setNum(entity.getNum());
							List<Transaction_contract> lt = transactionContractDao.findList(tc);
							if(lt.size() > 0) {
								num += "交易合同编号："+ entity.getNum() +"已存在！";
							}else {
								Supplier_enterprise name = gysservice.getName(entity.getSupplierName());
								if(name == null) {
									num += "供应商名称：" + entity.getSupplierName() + "不存在！";
								}else {
									//关联单据
									Bill_info bill = new Bill_info();
									bill.setContractNum(entity.getBillContractNum());
									List<Bill_info> lst = bill_infoDao.findList(bill);
									if(lst == null || lst.size() == 0) {
										num += "单据合同号：" + entity.getBillContractNum() + "不存在！";
									}else {
										entity.setSupplierEnterpriseId(name);
										entity.setBillId(lst.get(0));
										entity.setCoreEnterpriseId(transaction_contract.getCoreEnterpriseId());
										entity.preInsert();
										transactionContractDao.insert(entity);
										successNum++;
									}
								}
							}
						}catch (Exception ex) {
							ex.printStackTrace();
							failureNum++;
							continue;
						}
					}
				}
			}
			if(failureNum > 0){
				failureMsg.append("，" + failureNum + "条交易合同导入异常！");
			}
			addMessage(redirectAttributes, "已成功导入" + successNum + "条交易合同记录" + failureMsg + num);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入交易合同失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/fdCtrl/findTransactionListByPage?billId.id="+ billId +"&repage";
    }
    
    
    /**
     * 批量导入医院入库质量验收记录Excel数据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "importYyrkzlysExcelData", method=RequestMethod.POST)
    public String importYyrkzlysExcelData(@RequestParam(value="file", required=false) MultipartFile file, @RequestParam(value="bill_id", required=false) String billId, Yyrkzlys_record yyrkzlys_record, RedirectAttributes redirectAttributes) {
		try {
			if(file == null || yyrkzlys_record == null) {
				addMessage(redirectAttributes, "缺少Excel文件或医院入库质量验收记录信息不全，无法导入！");
				return "redirect:" + adminPath + "/fdCtrl/findYyrkzlysListByPage?billId.id="+ billId +"&repage";
			}
			int successNum = 0;
			int failureNum = 0;
			String num = "";
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if(ei != null) {
				List<Yyrkzlys_record> list = ei.getDataList(Yyrkzlys_record.class);
				if(list != null) {
					for (Yyrkzlys_record entity : list){
						try{
							Supplier_enterprise name = gysservice.getName(entity.getSupplierName());
							if(name == null) {
								num += "供应商名称：" + entity.getSupplierName() + "不存在！";
							}else {
								//关联单据
								Bill_info bill = new Bill_info();
								bill.setContractNum(entity.getBillContractNum());
								List<Bill_info> lst = bill_infoDao.findList(bill);
								if(lst == null || lst.size() == 0) {
									num += "单据合同号：" + entity.getBillContractNum() + "不存在！";
								}else {
									entity.setSupplierEnterpriseId(name);
									entity.setBillId(lst.get(0));
									entity.setCoreEnterpriseId(yyrkzlys_record.getCoreEnterpriseId());
									//判断数据是否重复
									Long count = yyrkzlysRecordDao.findCount(entity);
									if(count > 0) {
										num += "品名为：" + entity.getProjectName() + "的整条数据完全重复！";
									}else {
										entity.preInsert();
										yyrkzlysRecordDao.insert(entity);
										successNum++;
									}
								}
							}
						}catch (Exception ex) {
							ex.printStackTrace();
							failureNum++;
							continue;
						}
					}
				}
			}
			if(failureNum > 0){
				failureMsg.append("，" + failureNum + "条医院入库清单导入异常！");
			}
			addMessage(redirectAttributes, "已成功导入" + successNum + "条医院入库质量验收记录" + failureMsg + num);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入医院入库质量验收记录失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/fdCtrl/findYyrkzlysListByPage?billId.id="+ billId +"&repage";
    }
    
}
