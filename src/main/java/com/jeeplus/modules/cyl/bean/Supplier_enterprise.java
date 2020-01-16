package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;
/**
 * 供应商
 */
public class Supplier_enterprise extends DataEntity<Supplier_enterprise>{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4673405491187015529L;

	
	/**
	 * 企业名称
	 */
	@ExcelField(title="企业名称", align=2, sort=1)
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
    private String agencyCardPositive;
    
    /**
     * 负责人身份证背面
     */
    private String agencyCardBack;
    
    /**
     * 负责人身份证有效期
     */
    private String agencyCardValidity;
    
    /**
     * 负责人身份证签证机关
     */
    private String agencyCardOffice;
    
    /**
     * 负责人姓名
     */
    @ExcelField(title="负责人姓名", align=2, sort=2)
    private String agencyName;
    
    /**
     * 负责人身份证号码
     */
    private String agencyIdCard;
    
    /**
     * 负责人性别（0：男；1：女）
     */
    private String agencySex;
    
    /**
     * 负责人民族
     */
    private String agencyNation;
    
    /**
     * 负责人手机号
     */
    @ExcelField(title="负责人手机号", align=2, sort=3)
    private String agencyPhone;

    /**
     * 负责人邮箱
     */
    @ExcelField(title="负责人邮箱", align=2, sort=4)
    private String agencyEmail;
    
    /**
     * 负责人地址
     */
    private String agencyAddress;
    
    /**
     * 操作员身份证正面
     */
    private String operatorCardPositive;

    /**
     * 操作员身份证背面
     */
    private String operatorCardBack;
    
    /**
     * 操作员身份证有效期
     */
    private String operatorCardValidity;
    
    /**
     * 操作员身份证签证机关
     */
    private String operatorCardOffice;
    
    /**
     * 操作员姓名
     */
    private String operatorName;
    
    /**
     * 操作员身份证号码
     */
    private String operatorIdCard;
    
    /**
     * 操作员性别（0：男；1：女）
     */
    private String operatorSex;
    
    /**
     * 操作员民族
     */
    private String operatorNation;
    
    /**
     * 操作员地址
     */
    private String operatorAddress;
    
    /**
     * 手持身份证照片
     */
    private String holdCardUrl;
    
    /**
     * 平台操作授权书
     */
    private String platformOperateAuthor;
    
    /**
     * 开户许可证
     */
    private String openingPermitLetter;
    
    /**
     * 机构信用证
     */
    private String officeCreditLetter;
    
    /**
     * 公司章程
     */
    private String companyConstitution;

    /**
	 * // TODO: 2020/1/15 -1
     * 状态（-1:实名认证 0：待提交资料；1：待平台审核；2：平台审核不通过；3：待签约；4：已签约待银行授信；5：授信通过；6：银行授信不通过）
     */
    private String state;
    
    /**
     * 所属机构
     */
    private Office officeId;
    
    /**
     * 配置参数
     */
    private Enterprise_params paramsId;
    
	/**
     * 起始日期
     */
    private String beginDate;
    
    /**
     * 结束日期
     */
    private String endDate;
    
    /**
     * 核心企业与供应商关系（检索用）
     */
    private Core_supplier coresupplier;
    
    /**
     * 是否允许邀请下级供应商（0：否；1：是）
     */
    private String isYqgys;
    

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

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyPhone() {
		return agencyPhone;
	}

	public void setAgencyPhone(String agencyPhone) {
		this.agencyPhone = agencyPhone;
	}

	public String getAgencyEmail() {
		return agencyEmail;
	}

	public void setAgencyEmail(String agencyEmail) {
		this.agencyEmail = agencyEmail;
	}

	public String getHoldCardUrl() {
		return holdCardUrl;
	}

	public void setHoldCardUrl(String holdCardUrl) {
		this.holdCardUrl = holdCardUrl;
	}

	public String getPlatformOperateAuthor() {
		return platformOperateAuthor;
	}

	public void setPlatformOperateAuthor(String platformOperateAuthor) {
		this.platformOperateAuthor = platformOperateAuthor;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Core_supplier getCoresupplier() {
		return coresupplier;
	}

	public void setCoresupplier(Core_supplier coresupplier) {
		this.coresupplier = coresupplier;
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

	public String getAgencyCardPositive() {
		return agencyCardPositive;
	}

	public void setAgencyCardPositive(String agencyCardPositive) {
		this.agencyCardPositive = agencyCardPositive;
	}

	public String getAgencyCardBack() {
		return agencyCardBack;
	}

	public void setAgencyCardBack(String agencyCardBack) {
		this.agencyCardBack = agencyCardBack;
	}

	public String getAgencyCardValidity() {
		return agencyCardValidity;
	}

	public void setAgencyCardValidity(String agencyCardValidity) {
		this.agencyCardValidity = agencyCardValidity;
	}

	public String getAgencyCardOffice() {
		return agencyCardOffice;
	}

	public void setAgencyCardOffice(String agencyCardOffice) {
		this.agencyCardOffice = agencyCardOffice;
	}

	public String getAgencyIdCard() {
		return agencyIdCard;
	}

	public void setAgencyIdCard(String agencyIdCard) {
		this.agencyIdCard = agencyIdCard;
	}

	public String getAgencySex() {
		return agencySex;
	}

	public void setAgencySex(String agencySex) {
		this.agencySex = agencySex;
	}

	public String getAgencyNation() {
		return agencyNation;
	}

	public void setAgencyNation(String agencyNation) {
		this.agencyNation = agencyNation;
	}

	public String getAgencyAddress() {
		return agencyAddress;
	}

	public void setAgencyAddress(String agencyAddress) {
		this.agencyAddress = agencyAddress;
	}

	public String getOpeningPermitLetter() {
		return openingPermitLetter;
	}

	public void setOpeningPermitLetter(String openingPermitLetter) {
		this.openingPermitLetter = openingPermitLetter;
	}

	public String getOfficeCreditLetter() {
		return officeCreditLetter;
	}

	public void setOfficeCreditLetter(String officeCreditLetter) {
		this.officeCreditLetter = officeCreditLetter;
	}

	public String getCompanyConstitution() {
		return companyConstitution;
	}

	public void setCompanyConstitution(String companyConstitution) {
		this.companyConstitution = companyConstitution;
	}

	public String getOperatorCardPositive() {
		return operatorCardPositive;
	}

	public void setOperatorCardPositive(String operatorCardPositive) {
		this.operatorCardPositive = operatorCardPositive;
	}

	public String getOperatorCardBack() {
		return operatorCardBack;
	}

	public void setOperatorCardBack(String operatorCardBack) {
		this.operatorCardBack = operatorCardBack;
	}

	public String getOperatorCardValidity() {
		return operatorCardValidity;
	}

	public void setOperatorCardValidity(String operatorCardValidity) {
		this.operatorCardValidity = operatorCardValidity;
	}

	public String getOperatorCardOffice() {
		return operatorCardOffice;
	}

	public void setOperatorCardOffice(String operatorCardOffice) {
		this.operatorCardOffice = operatorCardOffice;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorIdCard() {
		return operatorIdCard;
	}

	public void setOperatorIdCard(String operatorIdCard) {
		this.operatorIdCard = operatorIdCard;
	}

	public String getOperatorSex() {
		return operatorSex;
	}

	public void setOperatorSex(String operatorSex) {
		this.operatorSex = operatorSex;
	}

	public String getOperatorNation() {
		return operatorNation;
	}

	public void setOperatorNation(String operatorNation) {
		this.operatorNation = operatorNation;
	}

	public String getOperatorAddress() {
		return operatorAddress;
	}

	public void setOperatorAddress(String operatorAddress) {
		this.operatorAddress = operatorAddress;
	}

	public String getIsYqgys() {
		return isYqgys;
	}

	public void setIsYqgys(String isYqgys) {
		this.isYqgys = isYqgys;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}