package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;
/**
 * 核心企业
 */
public class Core_enterprise extends DataEntity<Core_enterprise> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5665605263031375479L;

	
	/**
     * 企业名称
     */
    private String name;

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 营业期限至
     */
    private String businessPeriodTo;

    /**
     * 注册资本
     */
    private String registeredCapital;

    /**
     * 企业类型（0：股份有限公司；1：有限责任公司；2：合伙企业；3：集体企业；4：国有企业）
     */
    private String type;

    /**
     * 省级区域
     */
    private Area provinceArea;

    /**
     * 市级区域
     */
    private Area cityArea;

    /**
     * 营业地址详址
     */
    private String businessAddress;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 法人身份证正面
     */
    private String legalCardPositive;

    /**
     * 法人身份证背面
     */
    private String legalCardBack;
    
    /**
     * 法人身份证有效期
     */
    private String legalCardValidity;
    
    /**
     * 法人身份证签证机关
     */
    private String legalCardOffice;
    
    /**
     * 法人姓名
     */
    private String legalName;
    
    /**
     * 法人身份证号码
     */
    private String legalIdCard;
    
    /**
     * 法人性别（0：男；1：女）
     */
    private String legalSex;
    
    /**
     * 法人民族
     */
    private String legalNation;
    
    /**
     * 法人地址
     */
    private String legalAddress;
    
    /**
     * 负责人身份证正面
     */
    private String chargeCardPositive;
    
    /**
     * 负责人身份证背面
     */
    private String chargeCardBack;
    
    /**
     * 负责人身份证有效期
     */
    private String chargeCardValidity;
    
    /**
     * 负责人身份证签证机关
     */
    private String chargeCardOffice;

    /**
     * 负责人姓名
     */
    private String chargeName;
    
    /**
     * 负责人身份证号码
     */
    private String chargeIdCard;
    
    /**
     * 负责人性别（0：男；1：女）
     */
    private String chargeSex;
    
    /**
     * 负责人民族
     */
    private String chargeNation;

    /**
     * 负责人手机号
     */
    private String chargePhone;

    /**
     * 负责人邮箱
     */
    private String chargeEmail;
    
    /**
     * 负责人地址
     */
    private String chargeAddress;

    /**
     * 所属机构
     */
    private Office officeId;
    
    /**
     * 配置参数
     */
    private Enterprise_params paramsId;
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getBusinessPeriodTo() {
		return businessPeriodTo;
	}

	public void setBusinessPeriodTo(String businessPeriodTo) {
		this.businessPeriodTo = businessPeriodTo;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Area getProvinceArea() {
		return provinceArea;
	}

	public void setProvinceArea(Area provinceArea) {
		this.provinceArea = provinceArea;
	}

	public Area getCityArea() {
		return cityArea;
	}

	public void setCityArea(Area cityArea) {
		this.cityArea = cityArea;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getLegalCardPositive() {
		return legalCardPositive;
	}

	public void setLegalCardPositive(String legalCardPositive) {
		this.legalCardPositive = legalCardPositive;
	}

	public String getLegalCardBack() {
		return legalCardBack;
	}

	public void setLegalCardBack(String legalCardBack) {
		this.legalCardBack = legalCardBack;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public String getChargePhone() {
		return chargePhone;
	}

	public void setChargePhone(String chargePhone) {
		this.chargePhone = chargePhone;
	}

	public String getChargeEmail() {
		return chargeEmail;
	}

	public void setChargeEmail(String chargeEmail) {
		this.chargeEmail = chargeEmail;
	}

	public Office getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Office officeId) {
		this.officeId = officeId;
	}

	public Enterprise_params getParamsId() {
		return paramsId;
	}

	public void setParamsId(Enterprise_params paramsId) {
		this.paramsId = paramsId;
	}

	public String getLegalCardValidity() {
		return legalCardValidity;
	}

	public void setLegalCardValidity(String legalCardValidity) {
		this.legalCardValidity = legalCardValidity;
	}

	public String getLegalCardOffice() {
		return legalCardOffice;
	}

	public void setLegalCardOffice(String legalCardOffice) {
		this.legalCardOffice = legalCardOffice;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalIdCard() {
		return legalIdCard;
	}

	public void setLegalIdCard(String legalIdCard) {
		this.legalIdCard = legalIdCard;
	}

	public String getLegalSex() {
		return legalSex;
	}

	public void setLegalSex(String legalSex) {
		this.legalSex = legalSex;
	}

	public String getLegalNation() {
		return legalNation;
	}

	public void setLegalNation(String legalNation) {
		this.legalNation = legalNation;
	}

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getChargeCardPositive() {
		return chargeCardPositive;
	}

	public void setChargeCardPositive(String chargeCardPositive) {
		this.chargeCardPositive = chargeCardPositive;
	}

	public String getChargeCardBack() {
		return chargeCardBack;
	}

	public void setChargeCardBack(String chargeCardBack) {
		this.chargeCardBack = chargeCardBack;
	}

	public String getChargeCardValidity() {
		return chargeCardValidity;
	}

	public void setChargeCardValidity(String chargeCardValidity) {
		this.chargeCardValidity = chargeCardValidity;
	}

	public String getChargeCardOffice() {
		return chargeCardOffice;
	}

	public void setChargeCardOffice(String chargeCardOffice) {
		this.chargeCardOffice = chargeCardOffice;
	}

	public String getChargeIdCard() {
		return chargeIdCard;
	}

	public void setChargeIdCard(String chargeIdCard) {
		this.chargeIdCard = chargeIdCard;
	}

	public String getChargeSex() {
		return chargeSex;
	}

	public void setChargeSex(String chargeSex) {
		this.chargeSex = chargeSex;
	}

	public String getChargeNation() {
		return chargeNation;
	}

	public void setChargeNation(String chargeNation) {
		this.chargeNation = chargeNation;
	}

	public String getChargeAddress() {
		return chargeAddress;
	}

	public void setChargeAddress(String chargeAddress) {
		this.chargeAddress = chargeAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}