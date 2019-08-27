package com.example.applibrary.entity;

import java.io.Serializable;

public class ApprovalList implements Serializable {


    /**
     * approvalId : 1
     * reason:
     * userName : 张三
     * approvalAmount : 100
     * approvalType : 1
     * approvalDate : 2019-08-12
     * approvalStatus : 1
     */

    private int approvalId;
    private String userName;
    private String approvalAmount;
    private String approvalType;
    private String approvalDate;
    private String approvalStatus;

    public ApprovalList() {
    }

    public ApprovalList(int approvalId, String userName, String approvalAmount, String approvalType, String approvalDate, String approvalStatus) {
        this.approvalId = approvalId;
        this.userName = userName;
        this.approvalAmount = approvalAmount;
        this.approvalType = approvalType;
        this.approvalDate = approvalDate;
        this.approvalStatus = approvalStatus;
    }

    public int getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(int approvalId) {
        this.approvalId = approvalId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(String approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
