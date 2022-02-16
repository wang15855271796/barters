package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/9/9
 */
public class HylAmountNumModel extends BaseModel{

    /**
     * data : {"orderNum":11,"orderAmt":"6146.00"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderNum : 11
         * orderAmt : 6146.00
         */

        private int orderNum;
        private String orderAmt;

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getOrderAmt() {
            return orderAmt;
        }

        public void setOrderAmt(String orderAmt) {
            this.orderAmt = orderAmt;
        }
    }
}
