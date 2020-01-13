package com.jeeplus.modules.bank.service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.dao.Bill_infoDao;
import com.jeeplus.modules.cyl.dao.Credit_detailedDao;
import com.jeeplus.modules.cyl.dao.Enterprise_paramsDao;
import com.jeeplus.modules.cyl.dao.Payment_listDao;
import com.jeeplus.modules.cyl.dao.Supplier_enterpriseDao;
import com.jeeplus.modules.cyl.dao.Transaction_contractDao;
import com.jeeplus.modules.cyl.dao.Yyrkzlys_recordDao;

import fangfangrj.Utils;

import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Credit_detailed;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.cyl.bean.Payment_list;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Transaction_contract;
import com.jeeplus.modules.cyl.bean.Yyrkzlys_record;

@Service
@Transactional           //readonly=true 是否需要
public class BankCreditAuditService extends CrudService<Supplier_enterpriseDao, Supplier_enterprise> {

	@Autowired
	private Enterprise_paramsDao enterprise_paramsDao;
	
	@Autowired
	private Transaction_contractDao transaction_contractDao;
	
	@Autowired
	private Payment_listDao payment_listDao;
	
	@Autowired
	private Yyrkzlys_recordDao yyrkzlys_recordDao;
	
	@Autowired
	private Bill_infoDao bill_infoDao;
	
	@Autowired
	private Supplier_enterpriseDao supplier_enterpriseDao;
	
	@Autowired
	private Credit_detailedDao credit_detailedDao;


	public Enterprise_params saveEnterprise_params(Enterprise_params enterprise_params) {

		if (enterprise_params.getIsNewRecord()) {
			enterprise_params.preInsert();
			enterprise_paramsDao.insert(enterprise_params);
		} else {
			enterprise_params.preUpdate();
			enterprise_paramsDao.update(enterprise_params);
		}
		
		return  enterprise_params;
	}
	
	
	/**
	 * 获取供应商一年之内交易合同数据
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Transaction_contract> queryTransactionContractByOneYear(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			Transaction_contract entity = new Transaction_contract();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			if(Utils.isEmpty(supplier_enterprise.getBeginDate())) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Calendar c = Calendar.getInstance();
		        c.setTime(new Date());
		        c.add(Calendar.YEAR, -1);
		        Date y = c.getTime();
		        String year = format.format(y);
				entity.setSearchStartDate(year + "-01 00:00:00");
			}else {
				entity.setSearchStartDate(supplier_enterprise.getBeginDate());
			}
			if(!Utils.isEmpty(supplier_enterprise.getEndDate())) {
				entity.setSearchEndDate(supplier_enterprise.getEndDate());
			}
			return transaction_contractDao.findList(entity);
		}
	}
	
	
	/**
	 * 获取供应商一年之内付款清单数据
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Payment_list> queryPaymentListByOneYear(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			Payment_list entity = new Payment_list();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			if(Utils.isEmpty(supplier_enterprise.getBeginDate())) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Calendar c = Calendar.getInstance();
		        c.setTime(new Date());
		        c.add(Calendar.YEAR, -1);
		        Date y = c.getTime();
		        String year = format.format(y);
				entity.setSearchStartDate(year + "-01 00:00:00");
			}else {
				entity.setSearchStartDate(supplier_enterprise.getBeginDate());
			}
			if(!Utils.isEmpty(supplier_enterprise.getEndDate())) {
				entity.setSearchEndDate(supplier_enterprise.getEndDate());
			}
			return payment_listDao.findList(entity);
		}
	}
	
	
	/**
	 * 获取供应商一年之内入库记录数据
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Yyrkzlys_record> queryYyrkzlysRecordByOneYear(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			Yyrkzlys_record entity = new Yyrkzlys_record();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			if(Utils.isEmpty(supplier_enterprise.getBeginDate())) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Calendar c = Calendar.getInstance();
		        c.setTime(new Date());
		        c.add(Calendar.YEAR, -1);
		        Date y = c.getTime();
		        String year = format.format(y);
				entity.setSearchStartDate(year + "-01 00:00:00");
			}else {
				entity.setSearchStartDate(supplier_enterprise.getBeginDate());
			}
			if(!Utils.isEmpty(supplier_enterprise.getEndDate())) {
				entity.setSearchEndDate(supplier_enterprise.getEndDate());
			}
			return yyrkzlys_recordDao.findList(entity);
		}
	}
	
	
	/**
	 * 获取供应商一年之内应收账款数据
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Bill_info> queryBillInfoByOneYear(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			Bill_info entity = new Bill_info();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			if(Utils.isEmpty(supplier_enterprise.getBeginDate())) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Calendar c = Calendar.getInstance();
		        c.setTime(new Date());
		        c.add(Calendar.YEAR, -1);
		        Date y = c.getTime();
		        String year = format.format(y);
				entity.setSearchStartDate(year + "-01 00:00:00");
			}else {
				entity.setSearchStartDate(supplier_enterprise.getBeginDate());
			}
			if(!Utils.isEmpty(supplier_enterprise.getEndDate())) {
				entity.setSearchEndDate(supplier_enterprise.getEndDate());
			}
			return bill_infoDao.findList(entity);
		}
	}
	
	
	/**
	 * 获取供应商一年之内授信历史记录数据
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Credit_detailed> queryCreditDetailedByOneYear(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			String beginDate = supplier_enterprise.getBeginDate();
			String endDate = supplier_enterprise.getEndDate();
			supplier_enterprise = supplier_enterpriseDao.get(supplier_enterprise.getId());
			supplier_enterprise.setBeginDate(beginDate);
			supplier_enterprise.setEndDate(endDate);
			if(supplier_enterprise == null || supplier_enterprise.getParamsId() == null || Utils.isEmpty(supplier_enterprise.getParamsId().getId())) {
				return null;
			}else {
				Credit_detailed entity = new Credit_detailed();
				entity.setParamsId(supplier_enterprise.getParamsId());
				if(Utils.isEmpty(supplier_enterprise.getBeginDate())) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			        Calendar c = Calendar.getInstance();
			        c.setTime(new Date());
			        c.add(Calendar.YEAR, -1);
			        Date y = c.getTime();
			        String year = format.format(y);
					entity.setBeginDate(year + "-01 00:00:00");
				}else {
					entity.setBeginDate(supplier_enterprise.getBeginDate());
				}
				if(!Utils.isEmpty(supplier_enterprise.getEndDate())) {
					entity.setEndDate(supplier_enterprise.getEndDate());
				}
				return credit_detailedDao.findList(entity);
			}
		}
	}
	
	
	/**
	 * 获取近三年订单数据统计
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Map<String, Object>> queryNearlyThreeBillInfos(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
			Bill_info entity = new Bill_info();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			Long allThreeCount = 0L;
			Long allThreeValue = 0L;
			
			//最近一年
			Map<String, Object> map1 = new HashMap<String, Object>();
			datas.add(map1);
			String year1 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 1) + "";
			entity.setSearchStartDate(year1 + "-01-01 00:00:00");
			entity.setSearchEndDate(year1 + "-12-31 00:00:00");
			List<Bill_info> list1 = bill_infoDao.findList(entity);
			Long count1 = 0L;
			Long allValue1 = 0L;
			Long averageValue1 = 0L;
			Long minValue1 = 0L;
			Long maxValue1 = 0L;
			if(list1 != null && list1.size() > 0) {
				for(int i = 0; i < list1.size(); i++) {
					if(!Utils.isEmpty(list1.get(i).getAmount())) {
						count1++;
						Long amount = Long.valueOf(list1.get(i).getAmount());
						allValue1 += amount;
						if(minValue1 == 0 && count1 == 1) {
							minValue1 = amount;
						}
						if(minValue1 > amount) {
							minValue1 = amount;
						}
						if(maxValue1 == 0 && count1 == 1) {
							maxValue1 = amount;
						}
						if(maxValue1 < amount) {
							maxValue1 = amount;
						}
					}
				}
				if(count1 > 0) {
					averageValue1 = allValue1 / count1;
				}
			}
			allThreeCount += count1;
			allThreeValue += allValue1;
			map1.put("year", year1);
			map1.put("allVal", allValue1);
			map1.put("averageVal", averageValue1);
			map1.put("minVal", minValue1);
			map1.put("maxVal", maxValue1);
			
			//最近两年
			Map<String, Object> map2 = new HashMap<String, Object>();
			datas.add(map2);
			String year2 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 2) + "";
			entity.setSearchStartDate(year2 + "-01-01 00:00:00");
			entity.setSearchEndDate(year2 + "-12-31 00:00:00");
			List<Bill_info> list2 = bill_infoDao.findList(entity);
			Long count2 = 0L;
			Long allValue2 = 0L;
			Long averageValue2 = 0L;
			Long minValue2 = 0L;
			Long maxValue2 = 0L;
			if(list2 != null && list2.size() > 0) {
				for(int j = 0; j < list2.size(); j++) {
					if(!Utils.isEmpty(list2.get(j).getAmount())) {
						count2++;
						Long amount = Long.valueOf(list2.get(j).getAmount());
						allValue2 += amount;
						if(minValue2 == 0 && count2 == 1) {
							minValue2 = amount;
						}
						if(minValue2 > amount) {
							minValue2 = amount;
						}
						if(maxValue2 == 0 && count2 == 1) {
							maxValue2 = amount;
						}
						if(maxValue2 < amount) {
							maxValue2 = amount;
						}
					}
				}
				if(count2 > 0) {
					averageValue2 = allValue2 / count2;
				}
			}
			allThreeCount += count2;
			allThreeValue += allValue2;
			map2.put("year", year2);
			map2.put("allVal", allValue2);
			map2.put("averageVal", averageValue2);
			map2.put("minVal", minValue2);
			map2.put("maxVal", maxValue2);
			
			//最近三年
			Map<String, Object> map3 = new HashMap<String, Object>();
			datas.add(map3);
			String year3 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 3) + "";
			entity.setSearchStartDate(year3 + "-01-01 00:00:00");
			entity.setSearchEndDate(year3 + "-12-31 00:00:00");
			List<Bill_info> list3 = bill_infoDao.findList(entity);
			Long count3 = 0L;
			Long allValue3 = 0L;
			Long averageValue3 = 0L;
			Long minValue3 = 0L;
			Long maxValue3 = 0L;
			if(list3 != null && list3.size() > 0) {
				for(int k = 0; k < list3.size(); k++) {
					if(!Utils.isEmpty(list3.get(k).getAmount())) {
						count3++;
						Long amount = Long.valueOf(list3.get(k).getAmount());
						allValue3 += amount;
						if(minValue3 == 0 && count3 == 1) {
							minValue3 = amount;
						}
						if(minValue3 > amount) {
							minValue3 = amount;
						}
						if(maxValue3 == 0 && count3 == 1) {
							maxValue3 = amount;
						}
						if(maxValue3 < amount) {
							maxValue3 = amount;
						}
					}
				}
				if(count3 > 0) {
					averageValue3 = allValue3 / count3;
				}
			}
			allThreeCount += count3;
			allThreeValue += allValue3;
			map3.put("year", year3);
			map3.put("allVal", allValue3);
			map3.put("averageVal", averageValue3);
			map3.put("minVal", minValue3);
			map3.put("maxVal", maxValue3);
			
			//近半年
			Map<String, Object> map = new HashMap<String, Object>();
			datas.add(map);
			Calendar c = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String year = Utils.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
	        Date m = c.getTime();
			entity.setSearchStartDate(format.format(m));
			entity.setSearchEndDate(year);
			List<Bill_info> list = bill_infoDao.findList(entity);
			Long count = 0L;
			Long allValue = 0L;
			Long averageValue = 0L;
			Long minValue = 0L;
			Long maxValue = 0L;
			if(list != null && list.size() > 0) {
				for(int x = 0; x < list.size(); x++) {
					if(!Utils.isEmpty(list.get(x).getAmount())) {
						count++;
						Long amount = Long.valueOf(list.get(x).getAmount());
						allValue += amount;
						if(minValue == 0 && count == 1) {
							minValue = amount;
						}
						if(minValue > amount) {
							minValue = amount;
						}
						if(maxValue == 0 && count == 1) {
							maxValue = amount;
						}
						if(maxValue < amount) {
							maxValue = amount;
						}
					}
				}
				if(count > 0) {
					averageValue = allValue / count;
				}
			}
			map.put("year", year);
			map.put("allVal", allValue);
			map.put("averageVal", averageValue);
			map.put("minVal", minValue);
			map.put("maxVal", maxValue);
			map.put("threeAverageVal", allThreeCount == 0 ? 0 : allThreeValue / allThreeCount);
			
			return datas;
		}
	}
	
	
	/**
	 * 获取近三年付款数据统计
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Map<String, Object>> queryNearlyThreePayments(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
			Payment_list entity = new Payment_list();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			Long allThreeCount = 0L;
			Long allThreeValue = 0L;
			
			//最近一年
			Map<String, Object> map1 = new HashMap<String, Object>();
			datas.add(map1);
			String year1 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 1) + "";
			entity.setSearchStartDate(year1 + "-01-01 00:00:00");
			entity.setSearchEndDate(year1 + "-12-31 00:00:00");
			List<Payment_list> list1 = payment_listDao.findList(entity);
			Long count1 = 0L;
			Long allValue1 = 0L;
			Long averageValue1 = 0L;
			Long minValue1 = 0L;
			Long maxValue1 = 0L;
			if(list1 != null && list1.size() > 0) {
				for(int i = 0; i < list1.size(); i++) {
					if(!Utils.isEmpty(list1.get(i).getAmount())) {
						count1++;
						Long amount = Long.valueOf(list1.get(i).getAmount());
						allValue1 += amount;
						if(minValue1 == 0 && count1 == 1) {
							minValue1 = amount;
						}
						if(minValue1 > amount) {
							minValue1 = amount;
						}
						if(maxValue1 == 0 && count1 == 1) {
							maxValue1 = amount;
						}
						if(maxValue1 < amount) {
							maxValue1 = amount;
						}
					}
				}
				if(count1 > 0) {
					averageValue1 = allValue1 / count1;
				}
			}
			allThreeCount += count1;
			allThreeValue += allValue1;
			map1.put("year", year1);
			map1.put("allVal", allValue1);
			map1.put("averageVal", averageValue1);
			map1.put("minVal", minValue1);
			map1.put("maxVal", maxValue1);
			
			//最近两年
			Map<String, Object> map2 = new HashMap<String, Object>();
			datas.add(map2);
			String year2 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 2) + "";
			entity.setSearchStartDate(year2 + "-01-01 00:00:00");
			entity.setSearchEndDate(year2 + "-12-31 00:00:00");
			List<Payment_list> list2 = payment_listDao.findList(entity);
			Long count2 = 0L;
			Long allValue2 = 0L;
			Long averageValue2 = 0L;
			Long minValue2 = 0L;
			Long maxValue2 = 0L;
			if(list2 != null && list2.size() > 0) {
				for(int j = 0; j < list2.size(); j++) {
					if(!Utils.isEmpty(list2.get(j).getAmount())) {
						count2++;
						Long amount = Long.valueOf(list2.get(j).getAmount());
						allValue2 += amount;
						if(minValue2 == 0 && count2 == 1) {
							minValue2 = amount;
						}
						if(minValue2 > amount) {
							minValue2 = amount;
						}
						if(maxValue2 == 0 && count2 == 1) {
							maxValue2 = amount;
						}
						if(maxValue2 < amount) {
							maxValue2 = amount;
						}
					}
				}
				if(count2 > 0) {
					averageValue2 = allValue2 / count2;
				}
			}
			allThreeCount += count2;
			allThreeValue += allValue2;
			map2.put("year", year2);
			map2.put("allVal", allValue2);
			map2.put("averageVal", averageValue2);
			map2.put("minVal", minValue2);
			map2.put("maxVal", maxValue2);
			
			//最近三年
			Map<String, Object> map3 = new HashMap<String, Object>();
			datas.add(map3);
			String year3 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 3) + "";
			entity.setSearchStartDate(year3 + "-01-01 00:00:00");
			entity.setSearchEndDate(year3 + "-12-31 00:00:00");
			List<Payment_list> list3 = payment_listDao.findList(entity);
			Long count3 = 0L;
			Long allValue3 = 0L;
			Long averageValue3 = 0L;
			Long minValue3 = 0L;
			Long maxValue3 = 0L;
			if(list3 != null && list3.size() > 0) {
				for(int k = 0; k < list3.size(); k++) {
					if(!Utils.isEmpty(list3.get(k).getAmount())) {
						count3++;
						Long amount = Long.valueOf(list3.get(k).getAmount());
						allValue3 += amount;
						if(minValue3 == 0 && count3 == 1) {
							minValue3 = amount;
						}
						if(minValue3 > amount) {
							minValue3 = amount;
						}
						if(maxValue3 == 0 && count3 == 1) {
							maxValue3 = amount;
						}
						if(maxValue3 < amount) {
							maxValue3 = amount;
						}
					}
				}
				if(count3 > 0) {
					averageValue3 = allValue3 / count3;
				}
			}
			allThreeCount += count3;
			allThreeValue += allValue3;
			map3.put("year", year3);
			map3.put("allVal", allValue3);
			map3.put("averageVal", averageValue3);
			map3.put("minVal", minValue3);
			map3.put("maxVal", maxValue3);
			
			//近半年
			Map<String, Object> map = new HashMap<String, Object>();
			datas.add(map);
			Calendar c = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String year = Utils.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
	        Date m = c.getTime();
			entity.setSearchStartDate(format.format(m));
			entity.setSearchEndDate(year);
			List<Payment_list> list = payment_listDao.findList(entity);
			Long count = 0L;
			Long allValue = 0L;
			Long averageValue = 0L;
			Long minValue = 0L;
			Long maxValue = 0L;
			if(list != null && list.size() > 0) {
				for(int x = 0; x < list.size(); x++) {
					if(!Utils.isEmpty(list.get(x).getAmount())) {
						count++;
						Long amount = Long.valueOf(list.get(x).getAmount());
						allValue += amount;
						if(minValue == 0 && count == 1) {
							minValue = amount;
						}
						if(minValue > amount) {
							minValue = amount;
						}
						if(maxValue == 0 && count == 1) {
							maxValue = amount;
						}
						if(maxValue < amount) {
							maxValue = amount;
						}
					}
				}
				if(count > 0) {
					averageValue = allValue / count;
				}
			}
			map.put("year", year);
			map.put("allVal", allValue);
			map.put("averageVal", averageValue);
			map.put("minVal", minValue);
			map.put("maxVal", maxValue);
			map.put("threeAverageVal", allThreeCount == 0 ? 0 : allThreeValue / allThreeCount);
			
			return datas;
		}
	}
	
	
	/**
	 * 获取近三年账期数据统计
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Map<String, Object>> queryNearlyThreeAccountDays(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
			Payment_list entity = new Payment_list();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			Long allThreeCount = 0L;
			Long allThreeValue = 0L;
			
			//最近一年
			Map<String, Object> map1 = new HashMap<String, Object>();
			datas.add(map1);
			String year1 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 1) + "";
			entity.setSearchStartDate(year1 + "-01-01 00:00:00");
			entity.setSearchEndDate(year1 + "-12-31 00:00:00");
			List<Payment_list> list1 = payment_listDao.findList(entity);
			Long count1 = 0L;
			Long allValue1 = 0L;
			Long averageValue1 = 0L;
			Long minValue1 = 0L;
			Long maxValue1 = 0L;
			if(list1 != null && list1.size() > 0) {
				for(int i = 0; i < list1.size(); i++) {
					if(list1.get(i).getPaymentTime() != null && list1.get(i).getInvoiceDate() != null) {
						count1++;
						Long sdl = Utils.getDateLongByDateStr(list1.get(i).getInvoiceDate());
						Long edl = Utils.getDateLongByDateStr(list1.get(i).getPaymentTime());
						Long dayDiffValue = Long.valueOf(((edl - sdl) / (1000*3600*24)));
						dayDiffValue = dayDiffValue < 0 ? 0 : dayDiffValue;
						allValue1 += dayDiffValue;
						if(minValue1 == 0 && count1 == 1) {
							minValue1 = dayDiffValue;
						}
						if(minValue1 > dayDiffValue) {
							minValue1 = dayDiffValue;
						}
						if(maxValue1 == 0 && count1 == 1) {
							maxValue1 = dayDiffValue;
						}
						if(maxValue1 < dayDiffValue) {
							maxValue1 = dayDiffValue;
						}
					}
				}
				if(count1 > 0) {
					averageValue1 = allValue1 / count1;
				}
			}
			allThreeCount += count1;
			allThreeValue += allValue1;
			map1.put("year", year1);
			map1.put("allVal", allValue1);
			map1.put("averageVal", averageValue1);
			map1.put("minVal", minValue1);
			map1.put("maxVal", maxValue1);
			
			//最近两年
			Map<String, Object> map2 = new HashMap<String, Object>();
			datas.add(map2);
			String year2 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 2) + "";
			entity.setSearchStartDate(year2 + "-01-01 00:00:00");
			entity.setSearchEndDate(year2 + "-12-31 00:00:00");
			List<Payment_list> list2 = payment_listDao.findList(entity);
			Long count2 = 0L;
			Long allValue2 = 0L;
			Long averageValue2 = 0L;
			Long minValue2 = 0L;
			Long maxValue2 = 0L;
			if(list2 != null && list2.size() > 0) {
				for(int j = 0; j < list2.size(); j++) {
					if(list2.get(j).getPaymentTime() != null && list2.get(j).getInvoiceDate() != null) {
						count2++;
						Long sdl = Utils.getDateLongByDateStr(list2.get(j).getInvoiceDate());
						Long edl = Utils.getDateLongByDateStr(list2.get(j).getPaymentTime());
						Long dayDiffValue = Long.valueOf(((edl - sdl) / (1000*3600*24)));
						dayDiffValue = dayDiffValue < 0 ? 0 : dayDiffValue;
						allValue2 += dayDiffValue;
						if(minValue2 == 0 && count2 == 1) {
							minValue2 = dayDiffValue;
						}
						if(minValue2 > dayDiffValue) {
							minValue2 = dayDiffValue;
						}
						if(maxValue2 == 0 && count2 == 1) {
							maxValue2 = dayDiffValue;
						}
						if(maxValue2 < dayDiffValue) {
							maxValue2 = dayDiffValue;
						}
					}
				}
				if(count2 > 0) {
					averageValue2 = allValue2 / count2;
				}
			}
			allThreeCount += count2;
			allThreeValue += allValue2;
			map2.put("year", year2);
			map2.put("allVal", allValue2);
			map2.put("averageVal", averageValue2);
			map2.put("minVal", minValue2);
			map2.put("maxVal", maxValue2);
			
			//最近三年
			Map<String, Object> map3 = new HashMap<String, Object>();
			datas.add(map3);
			String year3 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 3) + "";
			entity.setSearchStartDate(year3 + "-01-01 00:00:00");
			entity.setSearchEndDate(year3 + "-12-31 00:00:00");
			List<Payment_list> list3 = payment_listDao.findList(entity);
			Long count3 = 0L;
			Long allValue3 = 0L;
			Long averageValue3 = 0L;
			Long minValue3 = 0L;
			Long maxValue3 = 0L;
			if(list3 != null && list3.size() > 0) {
				for(int k = 0; k < list3.size(); k++) {
					if(list3.get(k).getPaymentTime() != null && list3.get(k).getInvoiceDate() != null) {
						count3++;
						Long sdl = Utils.getDateLongByDateStr(list3.get(k).getInvoiceDate());
						Long edl = Utils.getDateLongByDateStr(list3.get(k).getPaymentTime());
						Long dayDiffValue = Long.valueOf(((edl - sdl) / (1000*3600*24)));
						dayDiffValue = dayDiffValue < 0 ? 0 : dayDiffValue;
						allValue3 += dayDiffValue;
						if(minValue3 == 0 && count3 == 1) {
							minValue3 = dayDiffValue;
						}
						if(minValue3 > dayDiffValue) {
							minValue3 = dayDiffValue;
						}
						if(maxValue3 == 0 && count3 == 1) {
							maxValue3 = dayDiffValue;
						}
						if(maxValue3 < dayDiffValue) {
							maxValue3 = dayDiffValue;
						}
					}
				}
				if(count3 > 0) {
					averageValue3 = allValue3 / count3;
				}
			}
			allThreeCount += count3;
			allThreeValue += allValue3;
			map3.put("year", year3);
			map3.put("allVal", allValue3);
			map3.put("averageVal", averageValue3);
			map3.put("minVal", minValue3);
			map3.put("maxVal", maxValue3);
			
			//近半年
			Map<String, Object> map = new HashMap<String, Object>();
			datas.add(map);
			Calendar c = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String year = Utils.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
	        Date m = c.getTime();
			entity.setSearchStartDate(format.format(m));
			entity.setSearchEndDate(year);
			List<Payment_list> list = payment_listDao.findList(entity);
			Long count = 0L;
			Long allValue = 0L;
			Long averageValue = 0L;
			Long minValue = 0L;
			Long maxValue = 0L;
			if(list != null && list.size() > 0) {
				for(int x = 0; x < list.size(); x++) {
					if(list.get(x).getPaymentTime() != null && list.get(x).getInvoiceDate() != null) {
						count++;
						Long sdl = Utils.getDateLongByDateStr(list.get(x).getInvoiceDate());
						Long edl = Utils.getDateLongByDateStr(list.get(x).getPaymentTime());
						Long dayDiffValue = Long.valueOf(((edl - sdl) / (1000*3600*24)));
						dayDiffValue = dayDiffValue < 0 ? 0 : dayDiffValue;
						allValue += dayDiffValue;
						if(minValue == 0 && count == 1) {
							minValue = dayDiffValue;
						}
						if(minValue > dayDiffValue) {
							minValue = dayDiffValue;
						}
						if(maxValue == 0 && count == 1) {
							maxValue = dayDiffValue;
						}
						if(maxValue < dayDiffValue) {
							maxValue = dayDiffValue;
						}
					}
				}
				if(count > 0) {
					averageValue = allValue / count;
				}
			}
			map.put("year", year);
			map.put("allVal", allValue);
			map.put("averageVal", averageValue);
			map.put("minVal", minValue);
			map.put("maxVal", maxValue);
			map.put("threeAverageVal", allThreeCount == 0 ? 0 : allThreeValue / allThreeCount);
			
			return datas;
		}
	}
	
	
	/**
	 * 获取近三年入库数据统计
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Map<String, Object>> queryNearlyThreeYyrkzlysInfos(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
			Yyrkzlys_record entity = new Yyrkzlys_record();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			Long allThreeCount = 0L;
			Long allThreeValue = 0L;
			
			//最近一年
			Map<String, Object> map1 = new HashMap<String, Object>();
			datas.add(map1);
			String year1 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 1) + "";
			entity.setSearchStartDate(year1 + "-01-01 00:00:00");
			entity.setSearchEndDate(year1 + "-12-31 00:00:00");
			List<Yyrkzlys_record> list1 = yyrkzlys_recordDao.findList(entity);
			Long count1 = 0L;
			Long allValue1 = 0L;
			Long averageValue1 = 0L;
			Long minValue1 = 0L;
			Long maxValue1 = 0L;
			if(list1 != null && list1.size() > 0) {
				for(int i = 0; i < list1.size(); i++) {
					if(!Utils.isEmpty(list1.get(i).getAllPrice())) {
						count1++;
						Long amount = Long.valueOf(list1.get(i).getAllPrice());
						allValue1 += amount;
						if(minValue1 == 0 && count1 == 1) {
							minValue1 = amount;
						}
						if(minValue1 > amount) {
							minValue1 = amount;
						}
						if(maxValue1 == 0 && count1 == 1) {
							maxValue1 = amount;
						}
						if(maxValue1 < amount) {
							maxValue1 = amount;
						}
					}
				}
				if(count1 > 0) {
					averageValue1 = allValue1 / count1;
				}
			}
			allThreeCount += count1;
			allThreeValue += allValue1;
			map1.put("year", year1);
			map1.put("allVal", allValue1);
			map1.put("averageVal", averageValue1);
			map1.put("minVal", minValue1);
			map1.put("maxVal", maxValue1);
			
			//最近两年
			Map<String, Object> map2 = new HashMap<String, Object>();
			datas.add(map2);
			String year2 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 2) + "";
			entity.setSearchStartDate(year2 + "-01-01 00:00:00");
			entity.setSearchEndDate(year2 + "-12-31 00:00:00");
			List<Yyrkzlys_record> list2 = yyrkzlys_recordDao.findList(entity);
			Long count2 = 0L;
			Long allValue2 = 0L;
			Long averageValue2 = 0L;
			Long minValue2 = 0L;
			Long maxValue2 = 0L;
			if(list2 != null && list2.size() > 0) {
				for(int j = 0; j < list2.size(); j++) {
					if(!Utils.isEmpty(list2.get(j).getAllPrice())) {
						count2++;
						Long amount = Long.valueOf(list2.get(j).getAllPrice());
						allValue2 += amount;
						if(minValue2 == 0 && count2 == 1) {
							minValue2 = amount;
						}
						if(minValue2 > amount) {
							minValue2 = amount;
						}
						if(maxValue2 == 0 && count2 == 1) {
							maxValue2 = amount;
						}
						if(maxValue2 < amount) {
							maxValue2 = amount;
						}
					}
				}
				if(count2 > 0) {
					averageValue2 = allValue2 / count2;
				}
			}
			allThreeCount += count2;
			allThreeValue += allValue2;
			map2.put("year", year2);
			map2.put("allVal", allValue2);
			map2.put("averageVal", averageValue2);
			map2.put("minVal", minValue2);
			map2.put("maxVal", maxValue2);
			
			//最近三年
			Map<String, Object> map3 = new HashMap<String, Object>();
			datas.add(map3);
			String year3 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 3) + "";
			entity.setSearchStartDate(year3 + "-01-01 00:00:00");
			entity.setSearchEndDate(year3 + "-12-31 00:00:00");
			List<Yyrkzlys_record> list3 = yyrkzlys_recordDao.findList(entity);
			Long count3 = 0L;
			Long allValue3 = 0L;
			Long averageValue3 = 0L;
			Long minValue3 = 0L;
			Long maxValue3 = 0L;
			if(list3 != null && list3.size() > 0) {
				for(int k = 0; k < list3.size(); k++) {
					if(!Utils.isEmpty(list3.get(k).getAllPrice())) {
						count3++;
						Long amount = Long.valueOf(list3.get(k).getAllPrice());
						allValue3 += amount;
						if(minValue3 == 0 && count3 == 1) {
							minValue3 = amount;
						}
						if(minValue3 > amount) {
							minValue3 = amount;
						}
						if(maxValue3 == 0 && count3 == 1) {
							maxValue3 = amount;
						}
						if(maxValue3 < amount) {
							maxValue3 = amount;
						}
					}
				}
				if(count3 > 0) {
					averageValue3 = allValue3 / count3;
				}
			}
			allThreeCount += count3;
			allThreeValue += allValue3;
			map3.put("year", year3);
			map3.put("allVal", allValue3);
			map3.put("averageVal", averageValue3);
			map3.put("minVal", minValue3);
			map3.put("maxVal", maxValue3);
			
			//近半年
			Map<String, Object> map = new HashMap<String, Object>();
			datas.add(map);
			Calendar c = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String year = Utils.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
	        Date m = c.getTime();
			entity.setSearchStartDate(format.format(m));
			entity.setSearchEndDate(year);
			List<Yyrkzlys_record> list = yyrkzlys_recordDao.findList(entity);
			Long count = 0L;
			Long allValue = 0L;
			Long averageValue = 0L;
			Long minValue = 0L;
			Long maxValue = 0L;
			if(list != null && list.size() > 0) {
				for(int x = 0; x < list.size(); x++) {
					if(!Utils.isEmpty(list.get(x).getAllPrice())) {
						count++;
						Long amount = Long.valueOf(list.get(x).getAllPrice());
						allValue += amount;
						if(minValue == 0 && count == 1) {
							minValue = amount;
						}
						if(minValue > amount) {
							minValue = amount;
						}
						if(maxValue == 0 && count == 1) {
							maxValue = amount;
						}
						if(maxValue < amount) {
							maxValue = amount;
						}
					}
				}
				if(count > 0) {
					averageValue = allValue / count;
				}
			}
			map.put("year", year);
			map.put("allVal", allValue);
			map.put("averageVal", averageValue);
			map.put("minVal", minValue);
			map.put("maxVal", maxValue);
			map.put("threeAverageVal", allThreeCount == 0 ? 0 : allThreeValue / allThreeCount);
			
			return datas;
		}
	}
	
	
	/**
	 * 获取近三年合同数据统计
	 * @param supplier_enterprise
	 * @return
	 */
	public List<Map<String, Object>> queryNearlyThreeContractInfos(Supplier_enterprise supplier_enterprise){
		if(supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
			return null;
		}else {
			List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
			Transaction_contract entity = new Transaction_contract();
			entity.setSupplierEnterpriseId(supplier_enterprise);
			Long allThreeCount = 0L;
			Long allThreeValue = 0L;
			
			//最近一年
			Map<String, Object> map1 = new HashMap<String, Object>();
			datas.add(map1);
			String year1 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 1) + "";
			entity.setSearchStartDate(year1 + "-01-01 00:00:00");
			entity.setSearchEndDate(year1 + "-12-31 00:00:00");
			List<Transaction_contract> list1 = transaction_contractDao.findList(entity);
			Long count1 = 0L;
			Long allValue1 = 0L;
			Long averageValue1 = 0L;
			Long minValue1 = 0L;
			Long maxValue1 = 0L;
			if(list1 != null && list1.size() > 0) {
				for(int i = 0; i < list1.size(); i++) {
					if(!Utils.isEmpty(list1.get(i).getAmount())) {
						count1++;
						Long amount = Long.valueOf(list1.get(i).getAmount());
						allValue1 += amount;
						if(minValue1 == 0 && count1 == 1) {
							minValue1 = amount;
						}
						if(minValue1 > amount) {
							minValue1 = amount;
						}
						if(maxValue1 == 0 && count1 == 1) {
							maxValue1 = amount;
						}
						if(maxValue1 < amount) {
							maxValue1 = amount;
						}
					}
				}
				if(count1 > 0) {
					averageValue1 = allValue1 / count1;
				}
			}
			allThreeCount += count1;
			allThreeValue += allValue1;
			map1.put("year", year1);
			map1.put("allVal", allValue1);
			map1.put("averageVal", averageValue1);
			map1.put("minVal", minValue1);
			map1.put("maxVal", maxValue1);
			
			//最近两年
			Map<String, Object> map2 = new HashMap<String, Object>();
			datas.add(map2);
			String year2 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 2) + "";
			entity.setSearchStartDate(year2 + "-01-01 00:00:00");
			entity.setSearchEndDate(year2 + "-12-31 00:00:00");
			List<Transaction_contract> list2 = transaction_contractDao.findList(entity);
			Long count2 = 0L;
			Long allValue2 = 0L;
			Long averageValue2 = 0L;
			Long minValue2 = 0L;
			Long maxValue2 = 0L;
			if(list2 != null && list2.size() > 0) {
				for(int j = 0; j < list2.size(); j++) {
					if(!Utils.isEmpty(list2.get(j).getAmount())) {
						count2++;
						Long amount = Long.valueOf(list2.get(j).getAmount());
						allValue2 += amount;
						if(minValue2 == 0 && count2 == 1) {
							minValue2 = amount;
						}
						if(minValue2 > amount) {
							minValue2 = amount;
						}
						if(maxValue2 == 0 && count2 == 1) {
							maxValue2 = amount;
						}
						if(maxValue2 < amount) {
							maxValue2 = amount;
						}
					}
				}
				if(count2 > 0) {
					averageValue2 = allValue2 / count2;
				}
			}
			allThreeCount += count2;
			allThreeValue += allValue2;
			map2.put("year", year2);
			map2.put("allVal", allValue2);
			map2.put("averageVal", averageValue2);
			map2.put("minVal", minValue2);
			map2.put("maxVal", maxValue2);
			
			//最近三年
			Map<String, Object> map3 = new HashMap<String, Object>();
			datas.add(map3);
			String year3 = (Integer.valueOf(Utils.getDateStrByFormat("yyyy")) - 3) + "";
			entity.setSearchStartDate(year3 + "-01-01 00:00:00");
			entity.setSearchEndDate(year3 + "-12-31 00:00:00");
			List<Transaction_contract> list3 = transaction_contractDao.findList(entity);
			Long count3 = 0L;
			Long allValue3 = 0L;
			Long averageValue3 = 0L;
			Long minValue3 = 0L;
			Long maxValue3 = 0L;
			if(list3 != null && list3.size() > 0) {
				for(int k = 0; k < list3.size(); k++) {
					if(!Utils.isEmpty(list3.get(k).getAmount())) {
						count3++;
						Long amount = Long.valueOf(list3.get(k).getAmount());
						allValue3 += amount;
						if(minValue3 == 0 && count3 == 1) {
							minValue3 = amount;
						}
						if(minValue3 > amount) {
							minValue3 = amount;
						}
						if(maxValue3 == 0 && count3 == 1) {
							maxValue3 = amount;
						}
						if(maxValue3 < amount) {
							maxValue3 = amount;
						}
					}
				}
				if(count3 > 0) {
					averageValue3 = allValue3 / count3;
				}
			}
			allThreeCount += count3;
			allThreeValue += allValue3;
			map3.put("year", year3);
			map3.put("allVal", allValue3);
			map3.put("averageVal", averageValue3);
			map3.put("minVal", minValue3);
			map3.put("maxVal", maxValue3);
			
			//近半年
			Map<String, Object> map = new HashMap<String, Object>();
			datas.add(map);
			Calendar c = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String year = Utils.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
	        Date m = c.getTime();
			entity.setSearchStartDate(format.format(m));
			entity.setSearchEndDate(year);
			List<Transaction_contract> list = transaction_contractDao.findList(entity);
			Long count = 0L;
			Long allValue = 0L;
			Long averageValue = 0L;
			Long minValue = 0L;
			Long maxValue = 0L;
			if(list != null && list.size() > 0) {
				for(int x = 0; x < list.size(); x++) {
					if(!Utils.isEmpty(list.get(x).getAmount())) {
						count++;
						Long amount = Long.valueOf(list.get(x).getAmount());
						allValue += amount;
						if(minValue == 0 && count == 1) {
							minValue = amount;
						}
						if(minValue > amount) {
							minValue = amount;
						}
						if(maxValue == 0 && count == 1) {
							maxValue = amount;
						}
						if(maxValue < amount) {
							maxValue = amount;
						}
					}
				}
				if(count > 0) {
					averageValue = allValue / count;
				}
			}
			map.put("year", year);
			map.put("allVal", allValue);
			map.put("averageVal", averageValue);
			map.put("minVal", minValue);
			map.put("maxVal", maxValue);
			map.put("threeAverageVal", allThreeCount == 0 ? 0 : allThreeValue / allThreeCount);
			
			return datas;
		}
	}

}
