package com.jeeplus.modules.esign.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.esign.bean.UserEsign;
import com.jeeplus.modules.esign.bean.UserEsignContact;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface ThyDao extends CrudDao<UserEsignContact> {

    //更新流程id
    public void updateProcessId(@Param("contract_id") String contract_id, @Param("process_id")String process_id);

}
