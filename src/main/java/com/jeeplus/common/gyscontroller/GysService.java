package com.jeeplus.common.gyscontroller;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.cyl.bean.Financing_info;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Supplier_user;
import com.jeeplus.modules.cyl.dao.*;
import com.jeeplus.modules.sys.service.SystemService;
import fangfangrj.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * 2018年8月22日22:32:49
 * 供应商servce
 */
@Service
public class GysService extends CrudService<Supplier_enterpriseDao, Supplier_enterprise> {

    @Autowired
    private Supplier_enterpriseDao supplier_enterprisedao;

    @Autowired
    private Financing_infoDao financing_infoDao;

    @Autowired
    private Bill_infoDao bill_infoDao;

    @Autowired
    private Enterprise_paramsDao enterprise_paramsdao;

    @Autowired
    private Core_supplierDao core_supplierDao;

    @Autowired
    private Supplier_userDao supplier_userDao;

    @Autowired
    private Supplier_shareholderDao supplier_shareholderDao;

    @Autowired
    private SystemService systemService;

    public List<Map<String, Object>> selectState() {
        return supplier_enterprisedao.selectState();
    }

    public Supplier_enterprise getName(String name) {
        return supplier_enterprisedao.getName(name);
    }


    /**
     * 更新并获取可用额度
     *
     * @param supplier_enterprise 供应商
     * @return
     */
    public String getAvailableQuota(Supplier_enterprise supplier_enterprise) {
        if (supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
            return "0";
        } else {
            supplier_enterprise = dao.get(supplier_enterprise.getId());
            if (supplier_enterprise.getParamsId() == null || Utils.isEmpty(supplier_enterprise.getParamsId().getAllQuota())
                    || "0".equals(supplier_enterprise.getParamsId().getAllQuota())) {
                return "0";
            }
            if (!Utils.isEmpty(supplier_enterprise.getParamsId().getAvailableQuota())) {
                return supplier_enterprise.getParamsId().getAvailableQuota();
            }
            String allQuota = supplier_enterprise.getParamsId().getAllQuota();
            String availableQuota = null;
            Financing_info f = new Financing_info();
            Bill_info b = new Bill_info();
            b.setSupplierEnterpriseId(supplier_enterprise);
            f.setBillId(b);
            List<Financing_info> list = financing_infoDao.findList(f);
            if (list == null || list.size() == 0) {
                availableQuota = supplier_enterprise.getParamsId().getAllQuota();
            } else {
                //可用额度 = 总额度 - 融资额度 + 还款额度
                BigDecimal all = new BigDecimal(supplier_enterprise.getParamsId().getAllQuota());
                all = all.setScale(2, BigDecimal.ROUND_HALF_UP);
                for (Financing_info i : list) {
                    BigDecimal m = new BigDecimal(0);
                    if (Utils.isEmpty(i.getLoanAmount()) && Utils.isEmpty(i.getTotalAmount())) {
                        continue;
                    }
                    if (!Utils.isEmpty(i.getLoanAmount())) {
                        m = new BigDecimal(i.getLoanAmount());
                    } else {
                        m = new BigDecimal(i.getTotalAmount());
                    }
                    m = m.setScale(2, BigDecimal.ROUND_HALF_UP);
                    //待银行清分/已完成
                    if (("10".equals(i.getState()) || "11".equals(i.getState())) && (!"2".equals(i.getState()) && !"5".equals(i.getState()) && !"7".equals(i.getState()))) {
                        //当前融资已还款
                        all = all.add(m);
                    }
                    if (!"2".equals(i.getState()) && !"5".equals(i.getState()) && !"7".equals(i.getState())) {
                        if (all.compareTo(BigDecimal.ZERO) > 0) {
                            all = all.subtract(m);
                        }
                    }
                }
                availableQuota = String.valueOf((all.compareTo(BigDecimal.ZERO) < 0 ? 0.00 : all.doubleValue()));
                if (!Utils.isEmpty(allQuota) && !Utils.isEmpty(availableQuota) && new BigDecimal(availableQuota).compareTo(new BigDecimal(allQuota)) > 0) {
                    availableQuota = allQuota;
                }
            }
            return availableQuota;
        }
    }


    /**
     * 获取融资相关统计金额
     *
     * @param supplier_enterprise 供应商
     * @return
     */
    public Map<String, Object> getAllWaitFinancingAmount(Supplier_enterprise supplier_enterprise) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
            map.put("waitFinancingCount", 0);
            map.put("waitFinancingAmount", 0);
            map.put("havingFinancingAmount", 0);
        } else {
            Bill_info b = new Bill_info();
            b.setSupplierEnterpriseId(supplier_enterprise);
            List<Bill_info> list = bill_infoDao.findList(b);
            BigDecimal waitAllAmount = new BigDecimal(0);
            waitAllAmount= waitAllAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal allAmount = new BigDecimal(0);
            allAmount= allAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
            long count = 0;
            if (list != null && list.size() > 0) {
                for (Bill_info bill_info : list) {
//                    if ("1".equals(bill_info.getState()) && !Utils.isEmpty(bill_info.getAmount())) {
////						waitAllAmount += Long.valueOf(bill_info.getAmount());
//                        waitAllAmount = waitAllAmount.add(new BigDecimal(bill_info.getAmount()));
//                        count++;
//                    } else if (("11".equals(bill_info.getState()) || "12".equals(bill_info.getState()) || "13".equals(bill_info.getState())) && !Utils.isEmpty(bill_info.getAmount())) {
////						allAmount += Long.valueOf(bill_info.getAmount());
//                        allAmount = allAmount.add(new BigDecimal(bill_info.getAmount()));
//                    }

                    //1：待供应商融资  单据金额
                    if ("1".equals(bill_info.getState()) && !Utils.isEmpty(bill_info.getAmount())) {
                        waitAllAmount = waitAllAmount.add(new BigDecimal(bill_info.getAmount()));
                        count++;
                        //11：已放款
                    }
//                    else if (("11".equals(bill_info.getState()) && !Utils.isEmpty(bill_info.getAmount()))) {
//                        allAmount = allAmount.add(new BigDecimal(bill_info.getAmount()));
//                    }
                }
            }
            Financing_info f = new Financing_info();
            f.setBillId(b);
            List<Financing_info> financingInfoList = financing_infoDao.findList(f);
            if (financingInfoList != null && financingInfoList.size() > 0) {
                for (Financing_info financing_info : financingInfoList) {
                    //已放款
                    if("9".equals(financing_info.getState())&&!Utils.isEmpty(financing_info.getTotalAmount())){
                        allAmount = allAmount.add(new BigDecimal(financing_info.getTotalAmount()));
                    }
                }
            }
            //应收账款笔数:count(Bill_info[id])
            map.put("waitFinancingCount", count);
            //可立即申请融资:sum(Bill_info[amount])
            map.put("waitFinancingAmount", waitAllAmount);
            //融资余额:sum(Financing_info[totalAmount])
            map.put("havingFinancingAmount", allAmount);
        }
        return map;
    }


    /**
     * 获取供应商相关融资集合
     *
     * @param supplier_enterprise
     * @return
     */
    public List<Financing_info> getAllFinancingListBySupplier(Supplier_enterprise supplier_enterprise) {
        List<Financing_info> list = null;
        if (supplier_enterprise != null && !Utils.isEmpty(supplier_enterprise.getId())) {
            Financing_info f = new Financing_info();
            Bill_info b = new Bill_info();
            f.setBillId(b);
            b.setSupplierEnterpriseId(supplier_enterprise);
            list = financing_infoDao.findList(f);
        }
        return list;
    }


    /**
     * 级联删除供应商
     *
     * @param supplier_enterprise
     */
    @Transactional(readOnly = false)
    public void deleteSupplierByJoin(Supplier_enterprise supplier_enterprise) {
        if (supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
            return;
        }
        supplier_enterprise = supplier_enterprisedao.get(supplier_enterprise.getId());
        if (supplier_enterprise == null || Utils.isEmpty(supplier_enterprise.getId())) {
            return;
        }
        //删除供应商企业参数
        if (supplier_enterprise.getParamsId() != null && !Utils.isEmpty(supplier_enterprise.getParamsId().getId())) {
            enterprise_paramsdao.delete(supplier_enterprise.getParamsId());
        }
        //删除供应商相关用户
        Supplier_user su = new Supplier_user();
        su.setSupplierEnterpriseId(supplier_enterprise);
        List<Supplier_user> suList = supplier_userDao.findList(su);
        if (suList != null && suList.size() > 0) {
            for (Supplier_user supplier_user : suList) {
                if (supplier_user.getUserId() != null && !Utils.isEmpty(supplier_user.getUserId().getId())) {
                    systemService.deleteUserJoin(supplier_user.getUserId());
                }
            }
        }
        //删除供应商相关股东
        supplier_shareholderDao.deleteBySupplier(supplier_enterprise.getId());
        //删除核心企业与供应商关系记录
        core_supplierDao.deleteBySupplier(supplier_enterprise.getId());
        //删除供应商
        supplier_enterprisedao.delete(supplier_enterprise);
    }

}
