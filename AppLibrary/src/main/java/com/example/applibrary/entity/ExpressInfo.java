package com.example.applibrary.entity;

import java.util.List;

public class ExpressInfo {

    /**
     * LogisticCode : 73117078376144
     * ShipperCode : 中通快递
     * Traces : [{"AcceptStation":"【嘉兴市】  快件离开 【杭州中转部】 已发往 【成都中转】","AcceptTime":"2019-07-28 04:31:51"},{"AcceptStation":"【嘉兴市】  快件已经到达 【杭州中转部】","AcceptTime":"2019-07-28 04:30:37"},{"AcceptStation":"【上海市】  快件离开 【上海】 已发往 【杭州中转部】","AcceptTime":"2019-07-27 23:28:22"},{"AcceptStation":"【上海市】  快件已经到达 【上海】","AcceptTime":"2019-07-27 23:22:28"},{"AcceptStation":"【上海市】  快件离开 【闸北】 已发往 【成都中转】","AcceptTime":"2019-07-27 21:04:40"},{"AcceptStation":"【上海市】  【闸北】（021-31080041） 的 王俊巍（18917958566） 已揽收","AcceptTime":"2019-07-27 19:42:56"}]
     * State : 2
     * OrderCode : wx2019072820122710013
     * EBusinessID : 1527169
     * Success : true
     */

    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String OrderCode;
    private String EBusinessID;
    private boolean Success;
    private List<Express> Traces;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<Express> getTraces() {
        return Traces;
    }

    public void setTraces(List<Express> Traces) {
        this.Traces = Traces;
    }

    public static class Express {
        /**
         * AcceptStation : 【嘉兴市】  快件离开 【杭州中转部】 已发往 【成都中转】
         * AcceptTime : 2019-07-28 04:31:51
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}
