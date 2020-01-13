package com.jeeplus.modules.cyl.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 融资信息
 */
public class Financing_info extends DataEntity<Financing_info> {

    /**
     *
     */
    private static final long serialVersionUID = -8196199894607467310L;


    /**
     * 融资编号
     */
    @ExcelField(title = "融资编号", align = 2, sort = 1)
    private String num;

    /**
     * 融资金额
     */
    @ExcelField(title = "融资金额", align = 2, sort = 2)
    private String totalAmount;

    /**
     * 放款总额
     */
    @ExcelField(title = "放款总额", align = 2, sort = 3)
    private String loanAmount;

    /**
     * 放款凭证
     */
    @ExcelField(title = "放款凭证", align = 2, sort = 4)
    private String loanUrl;

    /**
     * 生成日期（格式：yyyy-MM-dd）
     */
    @ExcelField(title = "生成日期", align = 2, sort = 5)
    private Date generationDate;

    /**
     * 到期日期（格式：yyyy-MM-dd）
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelField(title = "到期日期", align = 2, sort = 6)
    private Date expiryDate;

    /**
     * 月息费率
     */
    @ExcelField(title = "月息费率", align = 2, sort = 7)
    private String monthRate;

    /**
     * 月融资成本
     */
    @ExcelField(title = "月融资成本", align = 2, sort = 8)
    private String monthFinancing;

    /**
     * 融资状态（<br>
     * 0：待负责人审核；<br>
     * 1：待签约；<br>
     * 2：负责人审核不通过；<br>
     * 3：待银行配置；<br>
     * 4：待银行审核；<br>
     * 5：银行审核不通过；<br>
     * 6：待银行复核；<br>
     * 7：银行复核不通过；<br>
     * 8：待银行放款；<br>
     * 9：已放款；<br>
     * 10：待银行清分；<br>
     * 11：已完成）
     */
    @ExcelField(title = "融资状态", align = 2, sort = 9, dictType = "")
    private String state;

    /**
     * 状态说明
     */
    @ExcelField(title = "状态说明", align = 2, sort = 10)
    private String stateExplain;

    /**
     * 关联单据号
     */
    @ExcelField(title = "关联单据标识", align = 2, sort = 11, value = "billId.num")
    private Bill_info billId;

    /**
     * 利率
     */
    @ExcelField(title = "利率", align = 2, sort = 12)
    private String interestRate;

    /**
     * 融资比例
     */
    @ExcelField(title = "融资比例", align = 2, sort = 13)
    private String financingRatio;

    /**
     * 检索起始日期
     */
    private String searchStartDate;

    /**
     * 检索结束日期
     */
    private String searchEndDate;


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getFinancingRatio() {
        return financingRatio;
    }

    public void setFinancingRatio(String financingRatio) {
        this.financingRatio = financingRatio;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanUrl() {
        return loanUrl;
    }

    public void setLoanUrl(String loanUrl) {
        this.loanUrl = loanUrl;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(Date generationDate) {
        this.generationDate = generationDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(String monthRate) {
        this.monthRate = monthRate;
    }

    public String getMonthFinancing() {
        return monthFinancing;
    }

    public void setMonthFinancing(String monthFinancing) {
        this.monthFinancing = monthFinancing;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateExplain() {
        return stateExplain;
    }

    public void setStateExplain(String stateExplain) {
        this.stateExplain = stateExplain;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}