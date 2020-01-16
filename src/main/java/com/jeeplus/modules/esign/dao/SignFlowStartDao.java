package com.jeeplus.modules.esign.dao;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface SignFlowStartDao extends CrudDao<JSONObject> {

    /**
     * @author Jax
     * @param signTime 注册时间
     * @param signResult 注册结果
     * @param resultDescription 结果描述
     * @param accountId 账户id
     * @param flowId 流程id
     * @return
     */
    int callBackSave(@Param("signTime") String signTime, @Param("signResult") String signResult, @Param("resultDescription") String resultDescription, @Param("accountId") String accountId, @Param("flowId") String flowId);

}
