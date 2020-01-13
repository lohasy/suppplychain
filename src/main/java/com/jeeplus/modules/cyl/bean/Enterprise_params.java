package com.jeeplus.modules.cyl.bean;

import com.jeeplus.common.persistence.DataEntity;
/**
 * 企业参数配置
 */
public class Enterprise_params extends DataEntity<Enterprise_params>{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4298740442651933615L;
	

	/**
	 * 总额度
	 */
    private String allQuota;

    /**
     * 可用额度
     */
    private String availableQuota;

    /**
     * 利率
     */
    private String interestRate;

    /**
     * 融资比例
     */
    private String financingRatio;

    /**
     * 放款账户姓名
     */
    private String loanName;

    /**
     * 放款银行卡号
     */
    private String loanAccount;

    /**
     * 放款开户行
     */
    private String loanOpenBank;
    
    /**
     * 放款账户余额
     */
    private String loanBalance;

    /**
     * 回款账户姓名
     */
    private String returnName;

    /**
     * 回款银行卡号
     */
    private String returnAccount;

    /**
     * 回款开户行
     */
    private String returnOpenBank;
    
    /**
     * 回款账户余额
     */
    private String returnBalance;

    /**
     * 提醒核心企业还款参数（4个整数，用英文逗号分隔）
     */
    private String remindRepayment;
    

	public String getAllQuota() {
		return allQuota;
	}

	public void setAllQuota(String allQuota) {
		this.allQuota = allQuota;
	}

	public String getAvailableQuota() {
		return availableQuota;
	}

	public void setAvailableQuota(String availableQuota) {
		this.availableQuota = availableQuota;
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

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}

	public String getLoanOpenBank() {
		return loanOpenBank;
	}

	public void setLoanOpenBank(String loanOpenBank) {
		this.loanOpenBank = loanOpenBank;
	}

	public String getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(String loanBalance) {
		this.loanBalance = loanBalance;
	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	public String getReturnAccount() {
		return returnAccount;
	}

	public void setReturnAccount(String returnAccount) {
		this.returnAccount = returnAccount;
	}

	public String getReturnOpenBank() {
		return returnOpenBank;
	}

	public void setReturnOpenBank(String returnOpenBank) {
		this.returnOpenBank = returnOpenBank;
	}

	public String getReturnBalance() {
		return returnBalance;
	}

	public void setReturnBalance(String returnBalance) {
		this.returnBalance = returnBalance;
	}

	public String getRemindRepayment() {
		return remindRepayment;
	}

	public void setRemindRepayment(String remindRepayment) {
		this.remindRepayment = remindRepayment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}