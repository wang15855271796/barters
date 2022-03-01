package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/19
 */
public class HylMyCollectionModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"mainId":8664,"productName":"老干妈大豆酱","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","spec":"450ml","saleNum":"0","minMaxPrice":"￥1.5-4040","inventFlag":0,"collectId":24549}]
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
         * mainId : 8664
         * productName : 老干妈大豆酱
         * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
         * spec : 450ml
         * saleNum : 0
         * minMaxPrice : ￥1.5-4040
         * inventFlag : 0
         * collectId : 24549
         */

        private String mainId;
        private String productName;
        private String defaultPic;
        private String spec;
        private String saleNum;
        private String minMaxPrice;
        private int inventFlag;
        private int collectId;

        public String getMainId() {
            return mainId;
        }

        public void setMainId(String mainId) {
            this.mainId = mainId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getDefaultPic() {
            return defaultPic;
        }

        public void setDefaultPic(String defaultPic) {
            this.defaultPic = defaultPic;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(String saleNum) {
            this.saleNum = saleNum;
        }

        public String getMinMaxPrice() {
            return minMaxPrice;
        }

        public void setMinMaxPrice(String minMaxPrice) {
            this.minMaxPrice = minMaxPrice;
        }

        public int getInventFlag() {
            return inventFlag;
        }

        public void setInventFlag(int inventFlag) {
            this.inventFlag = inventFlag;
        }

        public int getCollectId() {
            return collectId;
        }

        public void setCollectId(int collectId) {
            this.collectId = collectId;
        }
    }
}
