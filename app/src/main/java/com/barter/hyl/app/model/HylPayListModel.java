package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/7
 */
public class HylPayListModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"payChannelName":"微信","payChannel":3,"desc":null},{"payChannelName":"支付宝","payChannel":14,"desc":null},{"payChannelName":"云闪付","payChannel":16,"desc":null},{"payChannelName":"货到付款","payChannel":17,"desc":"特别说明：\n货到付款需审核通过后，才会发货。\n审核通过后，您需要在3天内完成支付"}]
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
         * payChannelName : 微信
         * payChannel : 3
         * desc : null
         */

        private String payChannelName;
        private int payChannel;
        private String desc;

        public String getPayChannelName() {
            return payChannelName;
        }

        public void setPayChannelName(String payChannelName) {
            this.payChannelName = payChannelName;
        }

        public int getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(int payChannel) {
            this.payChannel = payChannel;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
