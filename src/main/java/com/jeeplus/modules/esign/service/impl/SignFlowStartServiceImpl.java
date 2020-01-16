package com.jeeplus.modules.esign.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.cyl.bean.Supplier_enterprise;
import com.jeeplus.modules.cyl.dao.Supplier_enterpriseDao;
import com.jeeplus.modules.esign.bean.UserEsign;
import com.jeeplus.modules.esign.bean.signflow.ServerResponseResult;
import com.jeeplus.modules.esign.comm.HttpHelper;
import com.jeeplus.modules.esign.comm.JSONHelper;
import com.jeeplus.modules.esign.constant.ConfigConstant;
import com.jeeplus.modules.esign.dao.SignFlowStartDao;
import com.jeeplus.modules.esign.dao.UserEsignDao;
import com.jeeplus.modules.esign.enums.RequestType;
import com.jeeplus.modules.esign.exception.DefineException;
import com.jeeplus.modules.esign.service.SignFlowStartService;
import com.jeeplus.modules.esign.util.ESignFlowUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author 李坏
 */
@Service
public class SignFlowStartServiceImpl implements SignFlowStartService {

    private static int CALLBACK_SUCCESS = 2;
    private static String SIGN_SUCCESS = "4";
    private static String SIGN_TYPE = "0";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SignFlowStartDao signFlowStartDao;

    @Autowired
    private UserEsignDao userEsignDao;

    @Autowired
    private Supplier_enterpriseDao supplier_enterprisedao;

    @Override
    public ServerResponseResult signFlowStart(String flowId) throws DefineException {
        JSONObject json = ESignFlowUtils.startSignFlow(flowId);
        Object obj = json.get("data");
        String message = json.getString("message");
        int code = json.getIntValue("code");
        if (code != 0 && obj == null) {
            return ServerResponseResult.createByErrorMessage(message);
        }
        return ServerResponseResult.createBySuccess(message);
    }

    @Override
    public int callBackSave(JSONObject json) {
        logger.info("=======================回调开始=======================");
        Map<String, Object> resultMap = JSONObject.toJavaObject(json, Map.class);

        String signTime = (String) resultMap.get("signTime");
        Integer signResult = (Integer) resultMap.get("signResult");

        String authorizedAccountId = (String) resultMap.get("authorizedAccountId");

        if (Objects.equals(signResult, CALLBACK_SUCCESS)) {
            UserEsign userEnter = getUserEsignByEsignId(authorizedAccountId);
            Supplier_enterprise supplierEnterprise = supplier_enterprisedao.getById(userEnter.getUserId());
            supplierEnterprise.setState(SIGN_SUCCESS);
            supplier_enterprisedao.update(supplierEnterprise);
        }
        String resultDescription = (String) resultMap.get("resultDescription");
        String accountId = (String) resultMap.get("accountId");
        String flowId = (String) resultMap.get("flowId");
        logger.info("流程id：" + flowId);
        logger.info("账户id：" + accountId);
        logger.info("组织id：" + authorizedAccountId);
        return signFlowStartDao.callBackSave(signTime, signResult.toString(), resultDescription, accountId, flowId);
    }

    @Override
    public String saveFlowDoc(String flowId) throws DefineException {
        return ESignFlowUtils.downloadFlowDoc(flowId);
    }

    public UserEsign getUserEsignByEsignId(String esignId) {
        UserEsign userEsignRe = userEsignDao.getUserEsignByEsignId(esignId);
        return userEsignRe;
    }

    /**
     * @description 获取签署地址
     *
     *              说明：
     *              <p>
     *              流程开启后，获取指定签署人的签署链接地址，如仅传入签署人账号id，则获取的签署任务链接仅包含本人的签署任务；如同时签署人账号id+机构id，则获取的签署任务链接包含企业与个人的签署任务
     *
     * @param flowId     创建签署流程时返回的签署流程ID
     * @param accountId  签署人账号id, 可指定, 默认用当前登录人账号id
     * @param organizeId 机构Id，传入本参数后，获取当前操作人代签企业的签署任务,默认空
     * @return
     * @throws DefineException
     * @author 宫清
     * @date 2019年7月21日 下午9:33:12
     */
    @Override
    public ServerResponseResult getSignUrl(String flowId, String accountId, String organizeId){
        try {
            logger.info("=================开始获取url===================");
            JSONObject result = qrySignUrl(flowId, accountId, organizeId, SIGN_TYPE);
            return ServerResponseResult.createBySuccess(result);
        } catch (DefineException e){
            return ServerResponseResult.createByErrorMessage("调用失败");
        }
    }

    public static JSONObject qrySignUrl(String flowId, String accountId, String organizeId, String urlType)
            throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET,
                ConfigConstant.Sign_URL(flowId, accountId, organizeId, urlType), null);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

}
