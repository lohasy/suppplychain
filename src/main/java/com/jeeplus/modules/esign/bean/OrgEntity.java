/**
 * 
 */
package com.jeeplus.modules.esign.bean;

/**企业基本信息模型
 * @author chenxi
 *
 */
public class OrgEntity {
	  //组织机构名称
      private String name;
      //组织机构证件号
      private String certNo;
      //组织结构证件类型
      private String certType;
      //法定代表人证件号
      private String legalRepCertNo;
      //法定代表人证件类型
      private String legalRepCertType;
      //法定代表人姓名
      private String legalRepName;
      //法定代表人地区/国籍
      private String legalRepNationality;
      //办理人身份
      private String operatorType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getLegalRepCertNo() {
		return legalRepCertNo;
	}
	public void setLegalRepCertNo(String legalRepCertNo) {
		this.legalRepCertNo = legalRepCertNo;
	}
	public String getLegalRepCertType() {
		return legalRepCertType;
	}
	public void setLegalRepCertType(String legalRepCertType) {
		this.legalRepCertType = legalRepCertType;
	}
	public String getLegalRepName() {
		return legalRepName;
	}
	public void setLegalRepName(String legalRepName) {
		this.legalRepName = legalRepName;
	}
	public String getLegalRepNationality() {
		return legalRepNationality;
	}
	public void setLegalRepNationality(String legalRepNationality) {
		this.legalRepNationality = legalRepNationality;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	@Override
	public String toString() {
		return "OrgEntity [name=" + name + ", certNo=" + certNo + ", certType=" + certType + ", legalRepCertNo="
				+ legalRepCertNo + ", legalRepCertType=" + legalRepCertType + ", legalRepName=" + legalRepName
				+ ", legalRepNationality=" + legalRepNationality + ", operatorType=" + operatorType + "]";
	}
      
      
}
