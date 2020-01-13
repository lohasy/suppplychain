package com.jeeplus.modules.hxqy.service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.cyl.bean.*;
import com.jeeplus.modules.cyl.dao.*;
import com.jeeplus.modules.sys.service.SystemService;
import fangfangrj.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 核心企业业务类
 *
 * @author LGT
 */
@Service
@Transactional(readOnly = true)
public class CoreEnterpriseService extends CrudService<Core_enterpriseDao, Core_enterprise> {

    @Autowired
    private Financing_infoDao financing_infoDao;

    @Autowired
    private Clear_branch_infoDao clear_branch_infoDao;

    @Autowired
    private Account_detailedDao account_detailedDao;

    @Autowired
    private Core_supplierDao core_supplierDao;

    @Autowired
    private Enterprise_paramsDao enterprise_paramsdao;

    @Autowired
    private SystemService systemService;

    @Autowired
    private Core_userDao core_userDao;


    @Override
    public Core_enterprise get(String id) {
        return dao.get(id);
    }

    public Page<Core_enterprise> find(Page<Core_enterprise> page, Core_enterprise core_enterprise) {
        core_enterprise.setPage(page);
        page.setList(dao.findList(core_enterprise));
        return page;
    }

    public Long findCount(Core_enterprise core_enterprise) {
        return dao.findCount(core_enterprise);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Core_enterprise core_enterprise) {
        super.save(core_enterprise);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Core_enterprise core_enterprise) {
        super.delete(core_enterprise);
    }


    /**
     * 更新并获取可用额度
     *
     * @param core_enterprise
     * @return
     */
    public String getAvailableQuota(Core_enterprise core_enterprise) {
        if (core_enterprise == null || Utils.isEmpty(core_enterprise.getId())) {
            return "0";
        } else {
            core_enterprise = dao.get(core_enterprise.getId());
            if (core_enterprise.getParamsId() == null || Utils.isEmpty(core_enterprise.getParamsId().getAllQuota())
                    || "0".equals(core_enterprise.getParamsId().getAllQuota())) {
                return "0";
            }
            if (!Utils.isEmpty(core_enterprise.getParamsId().getAvailableQuota())) {
                return core_enterprise.getParamsId().getAvailableQuota();
            }
            String allQuota = core_enterprise.getParamsId().getAllQuota();
            String availableQuota = null;
            Financing_info f = new Financing_info();
            Bill_info b = new Bill_info();
            b.setCoreEnterpriseId(core_enterprise);
            f.setBillId(b);
            List<Financing_info> list = financing_infoDao.findList(f);
            if (list == null || list.size() == 0) {
                availableQuota = core_enterprise.getParamsId().getAllQuota();
            } else {
                //可用额度 = 总额度 -   + 还款额度
                BigDecimal all = new BigDecimal(core_enterprise.getParamsId().getAllQuota());
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
//                    if ("10".equals(i.getState()) || "11".equals(i.getState())) {
//                        //当前融资已还款
//                        all = all.add(m);
//                    } else if (!"2".equals(i.getState()) && !"5".equals(i.getState()) && !"7".equals(i.getState())) {
//
//                        if (all.compareTo(BigDecimal.ZERO) > 0) {
//                            all = all.subtract(m);
//                        }
//                    }
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
     * 获取收益
     *
     * @param core_enterprise
     * @return
     */
    public String getProfit(Core_enterprise core_enterprise) {
        double profit = 0;
        if (core_enterprise != null && !Utils.isEmpty(core_enterprise.getId())) {
            //查询清分表获取融资收益
            Clear_branch_info c = new Clear_branch_info();
            Financing_info f = new Financing_info();
            Bill_info b = new Bill_info();
            b.setCoreEnterpriseId(core_enterprise);
            f.setBillId(b);
            c.setFinancingId(f);
            List<Clear_branch_info> cList = clear_branch_infoDao.findList(c);
            List<String> cArr = new LinkedList<String>();
            if (cList != null && cList.size() > 0) {
                for (Clear_branch_info i : cList) {
                    if (i.getFinancingId() != null && !Utils.isEmpty(i.getFinancingId().getId()) && !Utils.isEmpty(i.getCoreServiceCharge())) {
                        cArr.add(i.getFinancingId().getId());
                        double m = Double.valueOf(i.getCoreServiceCharge());
                        profit += m;
                    }
                }
            }

            //查询交易明细表获取收益
            Account_detailed a = new Account_detailed();
            a.setCoreEnterpriseId(core_enterprise);
            a.setTransactionType("0");
            List<Account_detailed> aList = account_detailedDao.findList(a);
            if (aList != null && aList.size() > 0) {
                for (Account_detailed j : aList) {
                    if (j.getFinancingId() == null || !cArr.contains(j.getFinancingId().getId()) || !Utils.isEmpty(j.getAmount())) {
                        double m = Double.valueOf(j.getAmount());
                        profit += m;
                    }
                }
            }
        }
        return String.valueOf(profit);
    }


    /**
     * 获取核心企业相关的供应商集合
     *
     * @param core_enterprise
     * @return
     */
    public List<Supplier_enterprise> getAllSupplierListByCore(Core_enterprise core_enterprise) {
        if (core_enterprise == null || Utils.isEmpty(core_enterprise.getId())) {
            return null;
        } else {
            Core_supplier cs = new Core_supplier();
            cs.setCoreEnterpriseId(core_enterprise);
            List<Core_supplier> list = core_supplierDao.findList(cs);
            if (list == null || list.size() == 0) {
                return null;
            } else {
                List<Supplier_enterprise> data = new LinkedList<Supplier_enterprise>();
                for (Core_supplier core_supplier : list) {
                    data.add(core_supplier.getSupplierEnterpriseId());
                }
                return data;
            }
        }
    }


    /**
     * 获取核心企业相关供应商融资集合
     *
     * @param core_enterprise
     * @return
     */
    public List<Financing_info> getAllFinancingListByCore(Core_enterprise core_enterprise) {
        if (core_enterprise == null || Utils.isEmpty(core_enterprise.getId())) {
            return null;
        } else {
            Financing_info f = new Financing_info();
            Bill_info b = new Bill_info();
            b.setCoreEnterpriseId(core_enterprise);
            f.setBillId(b);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 10);
            Date today = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String result = format.format(today);
            f.setSearchStartDate(result);
            List<Financing_info> list = financing_infoDao.findList(f);
            return list;
        }
    }


    /**
     * 级联删除核心企业
     *
     * @param core_enterprise
     */
    @Transactional(readOnly = false)
    public void deleteCoreByJoin(Core_enterprise core_enterprise) {
        if (core_enterprise == null || Utils.isEmpty(core_enterprise.getId())) return;
        core_enterprise = dao.get(core_enterprise.getId());
        if (core_enterprise == null || Utils.isEmpty(core_enterprise.getId())) return;
        //删除核心企业参数
        if (core_enterprise.getParamsId() != null && !Utils.isEmpty(core_enterprise.getParamsId().getId())) {
            enterprise_paramsdao.delete(core_enterprise.getParamsId());
        }
        //删除核心企业相关用户
        Core_user cu = new Core_user();
        cu.setCoreEnterpriseId(core_enterprise);
        List<Core_user> cuList = core_userDao.findList(cu);
        if (cuList != null && cuList.size() > 0) {
            for (Core_user core_user : cuList) {
                if (core_user.getUserId() != null && !Utils.isEmpty(core_user.getUserId().getId())) {
                    systemService.deleteUserJoin(core_user.getUserId());
                }
            }
        }
        //删除核心企业与供应商关系记录
        core_supplierDao.deleteByCore(core_enterprise.getId());
        //删除核心企业
        dao.delete(core_enterprise);
    }

}
