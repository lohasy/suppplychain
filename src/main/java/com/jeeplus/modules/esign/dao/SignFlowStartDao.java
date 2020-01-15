package com.jeeplus.modules.esign.dao;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface SignFlowStartDao extends CrudDao<JSONObject> {

    void callBackSave(@Param("signTime") String signTime,@Param("signResult") String signResult,@Param("resultDescription") String resultDescription,@Param("accountId") String accountId,@Param("flowId") String flowId);

}
