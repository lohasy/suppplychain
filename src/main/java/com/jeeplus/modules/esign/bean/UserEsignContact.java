package com.jeeplus.modules.esign.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEsignContact {
    private Integer id;
    private String userId;
    private Integer signType;
    private String processId;
    private String originalFileUrl;
    private String seelFileUrl;
    private Date createdTime;
    private Date updatedTime;
    private Integer valid;
}
