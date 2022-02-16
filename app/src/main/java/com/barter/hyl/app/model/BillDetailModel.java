package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/25
 */
public class BillDetailModel {

    /**
     * code : 1
     * message : 成功
     * data : {"iconUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/6115d2affece4b23bd1b311c3e8e2436.png","recordTypeName":"购物消费","recordType":1,"amount":"-22.00","payChannel":"余额","point":22,"dateTime":"2020-07-14 14:47:39","returnMainId":null,"orders":[{"orderId":"20200714144725-ef14","orderTime":"2020-07-14 14:47:26","orderAmt":"22.00","orderStatus":6}]}
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean error;
    private boolean success;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * iconUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/6115d2affece4b23bd1b311c3e8e2436.png
         * recordTypeName : 购物消费
         * recordType : 1
         * amount : -22.00
         * payChannel : 余额
         * point : 22
         * dateTime : 2020-07-14 14:47:39
         * returnMainId : null
         * orders : [{"orderId":"20200714144725-ef14","orderTime":"2020-07-14 14:47:26","orderAmt":"22.00","orderStatus":6}]
         */

        private String iconUrl;
        private String recordTypeName;
        private int recordType;
        private String amount;
        private String payChannel;
        private int point;
        private String dateTime;
        private String returnMainId;
        private List<OrdersBean> orders;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getRecordTypeName() {
            return recordTypeName;
        }

        public void setRecordTypeName(String recordTypeName) {
            this.recordTypeName = recordTypeName;
        }

        public int getRecordType() {
            return recordType;
        }

        public void setRecordType(int recordType) {
            this.recordType = recordType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getReturnMainId() {
            return returnMainId;
        }

        public void setReturnMainId(String returnMainId) {
            this.returnMainId = returnMainId;
        }

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public static class OrdersBean {
            /**
             * orderId : 20200714144725-ef14
             * orderTime : 2020-07-14 14:47:26
             * orderAmt : 22.00
             * orderStatus : 6
             */

            private String orderId;
            private String orderTime;
            private String orderAmt;
            private int orderStatus;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getOrderAmt() {
                return orderAmt;
            }

            public void setOrderAmt(String orderAmt) {
                this.orderAmt = orderAmt;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }
        }
    }
}
