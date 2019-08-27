package com.example.applibrary.entity;

import java.util.List;

public class ApprovalDetail {


    /**
     * id : 1
     * reason : 因公出差
     * type : 1
     * happenDate : 2018-08-01
     * amount : 100
     * remark : 备注
     * userName : 张三
     * approvalDate : 2018-08-9
     * approvalStream : [{"processUser":"经理","processDate":"2018-08-11","suggest":"","processResult":"1"},{"processUser":"主管","processDate":"2018-08-10","suggest":"","processResult":"1"}]
     */

    private int id;
    private String reason;
    private String type;
    private String happenDate;
    private String amount;
    private String remark;
    private String userName;
    private String approvalDate;
    private List<ApprovalStreamBean> approvalStream;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(String happenDate) {
        this.happenDate = happenDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public List<ApprovalStreamBean> getApprovalStream() {
        return approvalStream;
    }

    public void setApprovalStream(List<ApprovalStreamBean> approvalStream) {
        this.approvalStream = approvalStream;
    }

    public static class ApprovalStreamBean {
        /**
         * processUser : 经理
         * processDate : 2018-08-11
         * suggest :
         * processResult : 1
         */

        private String processUser;
        private String processDate;
        private String suggest;
        private String processResult;

        public String getProcessUser() {
            return processUser;
        }

        public void setProcessUser(String processUser) {
            this.processUser = processUser;
        }

        public String getProcessDate() {
            return processDate;
        }

        public void setProcessDate(String processDate) {
            this.processDate = processDate;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public String getProcessResult() {
            return processResult;
        }

        public void setProcessResult(String processResult) {
            this.processResult = processResult;
        }
    }
}
