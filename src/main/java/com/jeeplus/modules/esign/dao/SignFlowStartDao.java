package com.jeeplus.modules.esign.dao;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface SignFlowStartDao extends CrudDao<JSONObject> {

    void callBackSave(String signTime, String signResult, String resultDescription,String accountId,String flowId);

}
