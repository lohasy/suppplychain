package com.jeeplus.modules.esign.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.cyl.bean.Bill_info;
import com.jeeplus.modules.esign.bean.UserEsign;

import java.util.List;

@MyBatisDao
public interface UserEsignDao extends CrudDao<UserEsign> {
    UserEsign getUserEsignByUserId(String userId);


    void upfateUserEsignByUserId(UserEsign userEsignRe);
}