package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/26
 */
public class HylChooseCouponModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"giftDetailNo":"112021082400117651","giftName":"全场通用券-测试1033","applyFrom":"业务员赠送","amount":"3","limitAmtStr":"满100元可用","state":"ENABLED","dateTime":"2021-08-24-2021-09-22","role":["全场通用;();与活动同享"],"giftProdUseType":0,"jumpFlag":1,"reason":"","giftFlag":0}]
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private boolean error;
    private boolean success;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * giftDetailNo : 112021082400117651
         * giftName : 全场通用券-测试1033
         * applyFrom : 业务员赠送
         * amount : 3
         * limitAmtStr : 满100元可用
         * state : ENABLED
         * dateTime : 2021-08-24-2021-09-22
         * role : ["全场通用;();与活动同享"]
         * giftProdUseType : 0
         * jumpFlag : 1
         * reason :
         * giftFlag : 0
         */
        private boolean flags;
        private String giftDetailNo;
        private String giftName;
        private String applyFrom;
        private String amount;
        private String limitAmtStr;
        private String state;
        private String dateTime;
        private int giftProdUseType;
        private int jumpFlag;
        private String reason;
        private int giftFlag;
        private List<String> role;

        public boolean isFlags() {
            return flags;
        }

        public void setFlags(boolean flags) {
            this.flags = flags;
        }

        public String getGiftDetailNo() {
            return giftDetailNo;
        }

        public void setGiftDetailNo(String giftDetailNo) {
            this.giftDetailNo = giftDetailNo;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getApplyFrom() {
            return applyFrom;
        }

        public void setApplyFrom(String applyFrom) {
            this.applyFrom = applyFrom;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getLimitAmtStr() {
            return limitAmtStr;
        }

        public void setLimitAmtStr(String limitAmtStr) {
            this.limitAmtStr = limitAmtStr;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public int getGiftProdUseType() {
            return giftProdUseType;
        }

        public void setGiftProdUseType(int giftProdUseType) {
            this.giftProdUseType = giftProdUseType;
        }

        public int getJumpFlag() {
            return jumpFlag;
        }

        public void setJumpFlag(int jumpFlag) {
            this.jumpFlag = jumpFlag;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getGiftFlag() {
            return giftFlag;
        }

        public void setGiftFlag(int giftFlag) {
            this.giftFlag = giftFlag;
        }

        public List<String> getRole() {
            return role;
        }

        public void setRole(List<String> role) {
            this.role = role;
        }
    }
}
