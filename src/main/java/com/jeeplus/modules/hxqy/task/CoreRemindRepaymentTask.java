package com.jeeplus.modules.hxqy.task;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.cyl.bean.*;
import com.jeeplus.modules.cyl.dao.Core_enterpriseDao;
import com.jeeplus.modules.cyl.dao.Core_userDao;
import com.jeeplus.modules.cyl.dao.Financing_infoDao;
import com.jeeplus.modules.iim.dao.MailDao;
import com.jeeplus.modules.iim.entity.Mail;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.service.MailComposeService;
import com.jeeplus.modules.sys.entity.User;
import fangfangrj.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 核心企业提醒还款任务类
 * @author LGT
 */
@Service 
@Lazy(false)
public class CoreRemindRepaymentTask {

	@Autowired
	private Core_enterpriseDao core_enterpriseDao;
	
	@Autowired
	private Financing_infoDao financing_infoDao;
	
	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private MailDao mailDao;
	
	@Autowired
	private Core_userDao core_userDao;
	
	
	/**
	 * 每隔一小时自动扫描还款提醒
	 */
	@Scheduled(cron = "0 0 */1 * * ?") 
    public void task() { 
        //查询所有核心企业还款配置参数
		if(core_enterpriseDao != null) {
			List<Core_enterprise> coreList = core_enterpriseDao.findList(new Core_enterprise());
			if(coreList != null && coreList.size() > 0) {
				for(Core_enterprise core : coreList) {
					Enterprise_params coreParams = core.getParamsId();
					if(coreParams != null && !Utils.isEmpty(coreParams.getRemindRepayment())) {
						String[] remindRepayments = coreParams.getRemindRepayment().split(",");
						if(remindRepayments != null && remindRepayments.length > 0) {
							String remindRepayment1 = remindRepayments.length >= 1? remindRepayments[0] : null;
							String remindRepayment2 = remindRepayments.length >= 2? remindRepayments[1] : null;
							String remindRepayment3 = remindRepayments.length >= 3? remindRepayments[2] : null;
							String remindRepayment4 = remindRepayments.length >= 4? remindRepayments[3] : null;
							
							//查询核心企业下的待还款融资
							Financing_info searchFinancing = new Financing_info();
							Bill_info bill = new Bill_info();
							bill.setCoreEnterpriseId(core);
							searchFinancing.setBillId(bill);
							searchFinancing.setState("9");
							List<Financing_info> financingList = financing_infoDao.findList(searchFinancing);
							if(financingList != null && financingList.size() > 0) {
								for(Financing_info financing : financingList) {
									if(financing.getExpiryDate() != null) {
										String expiryDateStr = DateUtils.formatDate(financing.getExpiryDate()) + " 00:00:00";
										Date expiryDate = DateUtils.parseDate(expiryDateStr);
										Date nowDate = DateUtils.parseDate(DateUtils.getDate() + " 00:00:00");
										if(expiryDate.getTime() >= nowDate.getTime()) {
											long times = expiryDate.getTime() - nowDate.getTime();
											if(!Utils.isEmpty(remindRepayment1)) {
												long remindTime1 = Long.parseLong(remindRepayment1) * 86400000;
												if(times == remindTime1) {
													if(Utils.isEmpty(remindRepayment4)) {
														remindRepayment4 = "10";
													}
													if(remindRepayment4.equals(DateUtils.formatDate(new Date(), "HH"))) {
														//推送还款提醒邮件消息
														MailCompose mailCompose = new MailCompose();
														Mail mail = new Mail();
														mailCompose.setMail(mail);
														mail.setId(IdGen.uuid());
														mail.setTitle("融资编号"+ financing.getNum() +"还有"+ remindRepayment1 +"天需要还款！");
														mail.setOverview("融资编号"+ financing.getNum() +"还有"+ remindRepayment1 +"天需要还款！");
														mail.setContent("融资编号"+ financing.getNum() +"还有"+ remindRepayment1 +"天需要还款！");
														mailDao.insert(mail);
														mailCompose.setStatus("1");
														mailCompose.setReadstatus("1");
														List<User> receiverList = new LinkedList<User>();
														Core_user su = new Core_user();
														su.setCoreEnterpriseId(core);
														List<Core_user> suList = core_userDao.findList(su);
														if(suList != null && suList.size() > 0) {
															for(Core_user entity : suList) {
																if(entity != null && entity.getUserId() != null && !Utils.isEmpty(entity.getUserId().getId())) {
																	receiverList.add(entity.getUserId());
																}
															}
														}
														mailCompose.setReceiverList(receiverList);
														mailComposeService.sendMail(mailCompose);
													}
												}
											}
											if(!Utils.isEmpty(remindRepayment2)) {
												long remindTime2 = Long.parseLong(remindRepayment2) * 86400000;
												if(times == remindTime2) {
													if(Utils.isEmpty(remindRepayment4)) {
														remindRepayment4 = "10";
													}
													if(remindRepayment4.equals(DateUtils.formatDate(new Date(), "HH"))) {
														//推送还款提醒邮件消息
														MailCompose mailCompose = new MailCompose();
														Mail mail = new Mail();
														mailCompose.setMail(mail);
														mail.setId(IdGen.uuid());
														mail.setTitle("融资编号"+ financing.getNum() +"还有"+ remindRepayment2 +"天需要还款！");
														mail.setOverview("融资编号"+ financing.getNum() +"还有"+ remindRepayment2 +"天需要还款！");
														mail.setContent("融资编号"+ financing.getNum() +"还有"+ remindRepayment2 +"天需要还款！");
														mailDao.insert(mail);
														mailCompose.setStatus("1");
														mailCompose.setReadstatus("1");
														List<User> receiverList = new LinkedList<User>();
														Core_user su = new Core_user();
														su.setCoreEnterpriseId(core);
														List<Core_user> suList = core_userDao.findList(su);
														if(suList != null && suList.size() > 0) {
															for(Core_user entity : suList) {
																if(entity != null && entity.getUserId() != null && !Utils.isEmpty(entity.getUserId().getId())) {
																	receiverList.add(entity.getUserId());
																}
															}
														}
														mailCompose.setReceiverList(receiverList);
														mailComposeService.sendMail(mailCompose);
													}
												}
											}
											if(!Utils.isEmpty(remindRepayment3)) {
												long remindTime3 = Long.parseLong(remindRepayment3) * 86400000;
												if(times == remindTime3) {
													if(Utils.isEmpty(remindRepayment4)) {
														remindRepayment4 = "10";
													}
													if(remindRepayment4.equals(DateUtils.formatDate(new Date(), "HH"))) {
														//推送还款提醒邮件消息
														MailCompose mailCompose = new MailCompose();
														Mail mail = new Mail();
														mailCompose.setMail(mail);
														mail.setId(IdGen.uuid());
														mail.setTitle("融资编号"+ financing.getNum() +"还有"+ remindRepayment3 +"天需要还款！");
														mail.setOverview("融资编号"+ financing.getNum() +"还有"+ remindRepayment3 +"天需要还款！");
														mail.setContent("融资编号"+ financing.getNum() +"还有"+ remindRepayment3 +"天需要还款！");
														mailDao.insert(mail);
														mailCompose.setStatus("1");
														mailCompose.setReadstatus("1");
														List<User> receiverList = new LinkedList<User>();
														Core_user su = new Core_user();
														su.setCoreEnterpriseId(core);
														List<Core_user> suList = core_userDao.findList(su);
														if(suList != null && suList.size() > 0) {
															for(Core_user entity : suList) {
																if(entity != null && entity.getUserId() != null && !Utils.isEmpty(entity.getUserId().getId())) {
																	receiverList.add(entity.getUserId());
																}
															}
														}
														mailCompose.setReceiverList(receiverList);
														mailComposeService.sendMail(mailCompose);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
    }
	
}
