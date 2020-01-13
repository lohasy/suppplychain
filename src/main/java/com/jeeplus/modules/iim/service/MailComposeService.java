/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.iim.dao.MailComposeDao;
import com.jeeplus.modules.iim.entity.MailBox;
import com.jeeplus.modules.iim.entity.MailCompose;
import com.jeeplus.modules.iim.entity.MailPage;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 发件箱Service
 * @author jeeplus
 * @version 2015-11-13
 */
@Service
@Transactional(readOnly = true)
public class MailComposeService extends CrudService<MailComposeDao, MailCompose> {
	@Autowired
	private MailComposeDao mailComposeDao;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MailBoxService mailBoxService;
	
	
	public MailCompose get(String id) {
		return super.get(id);
	}
	
	public List<MailCompose> findList(MailCompose mailCompose) {
		return super.findList(mailCompose);
	}
	
	public Page<MailCompose> findPage(MailPage<MailCompose> page, MailCompose mailCompose) {
		return super.findPage(page, mailCompose);
	}
	
	@Transactional(readOnly = false)
	public void save(MailCompose mailCompose) {
		super.save(mailCompose);
	}
	
	@Transactional(readOnly = false)
	public void delete(MailCompose mailCompose) {
		super.delete(mailCompose);
	}

	public int getCount(MailCompose mailCompose) {
		return mailComposeDao.getCount(mailCompose);
	}
	
	
	/**
	 * 发送邮件
	 */
	@Transactional(readOnly = false)
	public void sendMail(MailCompose mailCompose) {
		mailService.saveOnlyMain(mailCompose.getMail());
		Date date = new Date(System.currentTimeMillis());
		mailCompose.setSender(UserUtils.getUser());
		mailCompose.setSendtime(date);
		for(User receiver : mailCompose.getReceiverList()){
			mailCompose.setReceiver(receiver);
			mailCompose.setId(null);//标记为新纪录，每次往发件箱插入一条记录
			save(mailCompose);//0 显示在草稿箱，1 显示在已发送需同时保存到收信人的收件箱。
		
		
			if(mailCompose.getStatus().equals("1"))//已发送，同时保存到收信人的收件箱
			{
				MailBox mailBox = new MailBox();
				mailBox.setReadstatus("0");
				mailBox.setReceiver(receiver);
				mailBox.setSender(UserUtils.getUser());
				mailBox.setMail(mailCompose.getMail());
				mailBox.setSendtime(date);
				mailBoxService.save(mailBox);
			}
		}
	}
	
}