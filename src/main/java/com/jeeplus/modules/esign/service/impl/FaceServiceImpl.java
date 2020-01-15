package com.jeeplus.modules.esign.service.impl;

import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.bean.Supplier_user;
import com.jeeplus.modules.cyl.dao.Supplier_enterpriseDao;
import com.jeeplus.modules.cyl.dao.Supplier_userDao;
import com.jeeplus.modules.esign.bean.*;
import com.jeeplus.modules.esign.dao.UserEsignDao;
import com.jeeplus.modules.esign.service.FaceService;
import com.jeeplus.modules.esign.util.EsignUtil;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaceServiceImpl implements FaceService {

    @Autowired
    private Supplier_enterpriseDao supplier_enterprisedao;

    @Autowired
    private Supplier_userDao supplier_userDao ;

    @Autowired
    private UserEsignDao userEsignDao ;

    @Override
    public  Object getFaceUrl() {
        String userId = UserUtils.getUser().getId();
        ContextInfo contextInfo=new ContextInfo();
        contextInfo.setNotifyUrl("haha");
        contextInfo.setRedirectUrl("");
        OrgEntity orgEntity = new OrgEntity();
        Supplier_user supplier_user = new Supplier_user();
        supplier_user.setUserId(UserUtils.getUser());
        List<Supplier_user> list = supplier_userDao.findList(supplier_user);
        Supplier_enterprise supplierEnterprise = list.get(0).getSupplierEnterpriseId();
        orgEntity.setCertNo(supplierEnterprise.getOrgCode());
        orgEntity.setName(supplierEnterprise.getName());
        orgEntity.setCertType("ORGANIZATION_USC_CODE");
        orgEntity.setLegalRepCertNo(supplierEnterprise.getLegalIdCard());
        orgEntity.setCertType("INDIVIDUAL_CH_IDCARD");
        orgEntity.setLegalRepName(supplierEnterprise.getLegalName());
        orgEntity.setOperatorType(supplierEnterprise.getAgencyIdCard().equals(supplierEnterprise.getLegalIdCard())?"1":"2");

        UserEsignFaceDto userEsignFaceDto = new UserEsignFaceDto();
        UserEsign userEsignRe = getUserEsignByUserId(userId);
        userEsignFaceDto.setAgentAccountId(userEsignRe.getEsignId());
        contextInfo.setContextId(userEsignRe.getEsignId());

        UserEsign userEnter = getUserEsignByUserId(supplierEnterprise.getId());
        userEsignFaceDto.setAccountId(userEnter.getEsignId());

        userEsignFaceDto.setContextInfo(contextInfo);
        userEsignFaceDto.setOrgEntity(orgEntity);
        return EsignUtil.getFaceUrl(userEsignFaceDto);
    }

    @Override
    public Object faceResult(FaceResultDto faceResultDto) {
        UserEsign userEsign=new UserEsign();
        UserEsign userEsignRe = userEsignDao.getUserEsignByUserId(faceResultDto.getAccountId());
        userEsignRe.setRealNameStatus("4");
        if(faceResultDto.getSuccess()){
            userEsignRe.setRealNameStatus("3");
        }
        userEsignDao.upfateUserEsignByUserId(userEsignRe);
        //企业更新审核状态为-1
        UserEsign userEnter = getUserEsignByEsignId(faceResultDto.getAccountId());
        Supplier_enterprise supplierEnterprise = supplier_enterprisedao.getById(userEnter.getUserId());
        supplierEnterprise.setState("-1");
        supplier_enterprisedao.update(supplierEnterprise);
        return "false";
    }

    public UserEsign getUserEsignByUserId(String userId){
        UserEsign userEsignRe = userEsignDao.getUserEsignByUserId(userId);
        return userEsignRe;
    }

    public UserEsign getUserEsignByEsignId(String esignId){
        UserEsign userEsignRe = userEsignDao.getUserEsignByEsignId(esignId);
        return userEsignRe;
    }
    @Test
    public  void test() {
        getFaceUrl();
    }
}
