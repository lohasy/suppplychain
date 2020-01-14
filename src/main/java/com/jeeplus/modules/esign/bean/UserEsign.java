package com.jeeplus.modules.esign.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.cyl.bean.Enterprise_params;
import com.jeeplus.modules.sys.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户e签宝对应表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEsign {
	private static final long serialVersionUID = 4061711054828392716L;
	private Integer id;
	private String userId;
	private Integer esignType;
	private String esignId;
	private String seelId;
	private String realNameStatus;
	private Date createdTime;
	private Date updatedTime;
	private Integer valid;
}
