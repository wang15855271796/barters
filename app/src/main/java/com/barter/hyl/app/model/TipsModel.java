package com.barter.hyl.app.model;

import java.io.Serializable;
import java.util.List;

public class TipsModel {
    /**
     * code : 1
     * message : 成功
     * data : {"roleDesc":"购买以下商品满1箱,赠（不限次数）：","sendGifts":[{"giftName":"优惠券测试-2规格-1","sendNum":"1箱","type":0,"poolNo":null},{"giftName":"不予秒杀同享优惠券","sendNum":"1张","type":1,"poolNo":"1002021070300000213"}],"prods":[{"type":"批发","activeId":null,"productMainId":8617,"buyFlag":"","productId":9137,"productName":"优惠券测试-2","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","flag":1,"businessType":1,"typeUrl":null,"spec":"规格-1","salesVolume":null,"minMaxPrice":null,"specialOffer":"","prodSpecs":null,"inventory":"","prodPrices":null,"deductAmount":"","selfProd":"","sendTimeTpl":"","companyId":null,"notSend":0,"unitName":"","amount":null,"saleNum":null}],"tips":{"roleType":0,"tips":"提示:再买1箱可享受满赠","buyNum":"0","buyAmt":"￥0"}}
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
         * roleDesc : 购买以下商品满1箱,赠（不限次数）：
         * sendGifts : [{"giftName":"优惠券测试-2规格-1","sendNum":"1箱","type":0,"poolNo":null},{"giftName":"不予秒杀同享优惠券","sendNum":"1张","type":1,"poolNo":"1002021070300000213"}]
         * prods : [{"type":"批发","activeId":null,"productMainId":8617,"buyFlag":"","productId":9137,"productName":"优惠券测试-2","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","flag":1,"businessType":1,"typeUrl":null,"spec":"规格-1","salesVolume":null,"minMaxPrice":null,"specialOffer":"","prodSpecs":null,"inventory":"","prodPrices":null,"deductAmount":"","selfProd":"","sendTimeTpl":"","companyId":null,"notSend":0,"unitName":"","amount":null,"saleNum":null}]
         * tips : {"roleType":0,"tips":"提示:再买1箱可享受满赠","buyNum":"0","buyAmt":"￥0"}
         */

        private String roleDesc;
        private TipsBean tips;

        public String getRoleDesc() {
            return roleDesc;
        }

        public void setRoleDesc(String roleDesc) {
            this.roleDesc = roleDesc;
        }

        public TipsBean getTips() {
            return tips;
        }

        public void setTips(TipsBean tips) {
            this.tips = tips;
        }

        public static class TipsBean {
            /**
             * roleType : 0
             * tips : 提示:再买1箱可享受满赠
             * buyNum : 0
             * buyAmt : ￥0
             */

            private int roleType;
            private String tips;
            private String buyNum;
            private String buyAmt;


            public int getRoleType() {
                return roleType;
            }

            public void setRoleType(int roleType) {
                this.roleType = roleType;
            }

            public String getTips() {
                return tips;
            }

            public void setTips(String tips) {
                this.tips = tips;
            }

            public String getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(String buyNum) {
                this.buyNum = buyNum;
            }

            public String getBuyAmt() {
                return buyAmt;
            }

            public void setBuyAmt(String buyAmt) {
                this.buyAmt = buyAmt;
            }
        }
    }

}
