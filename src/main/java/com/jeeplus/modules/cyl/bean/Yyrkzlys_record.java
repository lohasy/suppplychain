package com.jeeplus.modules.cyl.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
/**
 * 医院入库质量验收记录
 */
public class Yyrkzlys_record extends DataEntity<Yyrkzlys_record> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8032081536511016539L;
	
	/**
	 * 品名
	 */
	@ExcelField(title="品名", align=2, sort=1)
	private String projectName;
	
	/**
	 * 规格
	 */
	@ExcelField(title="规格", align=2, sort=2)
	private String specifications;
	
	/**
	 * 单位
	 */
	@ExcelField(title="单位", align=2, sort=3)
	private String unit;
	
	/**
	 * 数量
	 */
	@ExcelField(title="数量", align=2, sort=4)
	private String count;
	
	/**
	 * 生产企业
	 */
	@ExcelField(title="生产企业", align=2, sort=5)
	private String produceCompany;
	
	/**
	 * 生产批号
	 */
	@ExcelField(title="生产批号", align=2, sort=6)
	private String produceNum;
	
	/**
	 * 灭菌批号 / 设备编号
	 */
	@ExcelField(title="灭菌批号/设备编号", align=2, sort=7)
	private String deviceNum;
	
	/**
	 * 有效期
	 */
	@ExcelField(title="有效期", align=2, sort=8)
	private String validityTime;
	
	/**
	 * 质量状况
	 */
	@ExcelField(title="质量状况", align=2, sort=9)
	private String qualityCondition;
	
	/**
	 * 验收结论
	 */
	@ExcelField(title="验收结论", align=2, sort=10)
	private String acceptConclusion;
	
	/**
	 * 记录时间
	 */
	@ExcelField(title="记录时间", align=2, sort=11)
	private Date time;
	
	/**
	 * 验收员
	 */
	@ExcelField(title="验收员", align=2, sort=12)
	private String inspector;
	
	/**
	 * 单价
	 */
	@ExcelField(title="单价", align=2, sort=13)
	private String unitPrice;
	
	/**
	 * 总价
	 */
	@ExcelField(title="总价", align=2, sort=14)
	private String allPrice;
	
	/**
	 * 核心企业
	 */
	private Core_enterprise coreEnterpriseId;
	
	/**
	 * 供应商
	 */
	private Supplier_enterprise supplierEnterpriseId;
	
	/**
	 * 上级供应商（相当于核心企业）
	 */
	private Supplier_enterprise supplierParentId;
	
	/**
	 * 所属单据
	 */
	private Bill_info billId;
	
	/**
	 * 供应商名称
	 */
	@ExcelField(title="供应商名称", align=2, sort=15)
	private String supplierName;
	
	/**
	 * 单据合同号
	 */
	@ExcelField(title="合同号", align=2, sort=16)
	private String billContractNum;
	
	/**
	 * 检索起始日期
	 */
	private String searchStartDate;
	
	/**
	 * 检索结束日期
	 */
	private String searchEndDate;
	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getProduceCompany() {
		return produceCompany;
	}

	public void setProduceCompany(String produceCompany) {
		this.produceCompany = produceCompany;
	}

	public String getProduceNum() {
		return produceNum;
	}

	public void setProduceNum(String produceNum) {
		this.produceNum = produceNum;
	}

	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(String validityTime) {
		this.validityTime = validityTime;
	}

	public String getQualityCondition() {
		return qualityCondition;
	}

	public void setQualityCondition(String qualityCondition) {
		this.qualityCondition = qualityCondition;
	}

	public String getAcceptConclusion() {
		return acceptConclusion;
	}

	public void setAcceptConclusion(String acceptConclusion) {
		this.acceptConclusion = acceptConclusion;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public Supplier_enterprise getSupplierEnterpriseId() {
		return supplierEnterpriseId;
	}

	public void setSupplierEnterpriseId(Supplier_enterprise supplierEnterpriseId) {
		this.supplierEnterpriseId = supplierEnterpriseId;
	}

	public Core_enterprise getCoreEnterpriseId() {
		return coreEnterpriseId;
	}

	public void setCoreEnterpriseId(Core_enterprise coreEnterpriseId) {
		this.coreEnterpriseId = coreEnterpriseId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Bill_info getBillId() {
		return billId;
	}

	public void setBillId(Bill_info billId) {
		this.billId = billId;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	
	public String getBillContractNum() {
		if(billId != null) {
			billContractNum = billId.getContractNum();
		}
		return billContractNum;
	}

	public void setBillContractNum(String billContractNum) {
		this.billContractNum = billContractNum;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(String allPrice) {
		this.allPrice = allPrice;
	}

	public Supplier_enterprise getSupplierParentId() {
		return supplierParentId;
	}

	public void setSupplierParentId(Supplier_enterprise supplierParentId) {
		this.supplierParentId = supplierParentId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
