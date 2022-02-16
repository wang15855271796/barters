package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class MyJifenModel {

    /**
     * code : 1
     * message : 成功
     * data : {"point":0,"gifts":[{"amount":"10","giftName":"转盘优惠","useDesc":"满100元减10元","effectTime":"领券后：长期有效","point":212,"poolNo":"1002021072900000220","exchangeFlag":0}]}
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
         * point : 0
         * gifts : [{"amount":"10","giftName":"转盘优惠","useDesc":"满100元减10元","effectTime":"领券后：长期有效","point":212,"poolNo":"1002021072900000220","exchangeFlag":0}]
         */

        private int point;
        private List<GiftsBean> gifts;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public List<GiftsBean> getGifts() {
            return gifts;
        }

        public void setGifts(List<GiftsBean> gifts) {
            this.gifts = gifts;
        }

        public static class GiftsBean {
            /**
             * amount : 10
             * giftName : 转盘优惠
             * useDesc : 满100元减10元
             * effectTime : 领券后：长期有效
             * point : 212
             * poolNo : 1002021072900000220
             * exchangeFlag : 0
             */

            private String amount;
            private String giftName;
            private String useDesc;
            private String effectTime;
            private int point;
            private String poolNo;
            private int exchangeFlag;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getGiftName() {
                return giftName;
            }

            public void setGiftName(String giftName) {
                this.giftName = giftName;
            }

            public String getUseDesc() {
                return useDesc;
            }

            public void setUseDesc(String useDesc) {
                this.useDesc = useDesc;
            }

            public String getEffectTime() {
                return effectTime;
            }

            public void setEffectTime(String effectTime) {
                this.effectTime = effectTime;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public String getPoolNo() {
                return poolNo;
            }

            public void setPoolNo(String poolNo) {
                this.poolNo = poolNo;
            }

            public int getExchangeFlag() {
                return exchangeFlag;
            }

            public void setExchangeFlag(int exchangeFlag) {
                this.exchangeFlag = exchangeFlag;
            }
        }
    }
}
