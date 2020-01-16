package com.jeeplus.modules.esign.bean;

public class FaceResultDto {
    private String flowId;
    private String accountId;
    private String agentAccountId;
    private Boolean success;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAgentAccountId() {
        return agentAccountId;
    }

    public void setAgentAccountId(String agentAccountId) {
        this.agentAccountId = agentAccountId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "FaceResultDto{" +
                "flowId='" + flowId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", agentAccountId='" + agentAccountId + '\'' +
                ", success=" + success +
                '}';
    }
}
