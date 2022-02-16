package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/31
 */
public class HylFullListModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"mainId":8664,"productName":"老干妈大豆酱","spec":"450ml","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","minMaxPrice":"￥1.5-4040","inventFlag":0}]
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
         * spec : 450ml
         * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
         * minMaxPrice : ￥1.5-4040
         * inventFlag : 0
         */

        private int mainId;
        private String productName;
        private String spec;
        private String defaultPic;
        private String minMaxPrice;
        private int inventFlag;

        public int getMainId() {
            return mainId;
        }

        public void setMainId(int mainId) {
            this.mainId = mainId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getDefaultPic() {
            return defaultPic;
        }

        public void setDefaultPic(String defaultPic) {
            this.defaultPic = defaultPic;
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
    }
}
