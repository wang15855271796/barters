package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/23
 */
public class JudgeModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"businessId":null,"businessType":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","prodName":"商品9","level":"5","content":"很很好","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//42dcb8464818444fa530e206b2a59c1d.jpg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//d62b1d1c9460404693f50be1030a33e6.jpg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//6ec9dbb1d53e4d24bda849af998592e3.jpg"]}]
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
         * businessId : null
         * businessType : null
         * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
         * prodName : 商品9
         * level : 5
         * content : 很很好
         * pics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//42dcb8464818444fa530e206b2a59c1d.jpg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//d62b1d1c9460404693f50be1030a33e6.jpg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//6ec9dbb1d53e4d24bda849af998592e3.jpg"]
         */

        private Object businessId;
        private Object businessType;
        private String defaultPic;
        private String prodName;
        private String level;
        private String content;
        private List<String> pics;

        public Object getBusinessId() {
            return businessId;
        }

        public void setBusinessId(Object businessId) {
            this.businessId = businessId;
        }

        public Object getBusinessType() {
            return businessType;
        }

        public void setBusinessType(Object businessType) {
            this.businessType = businessType;
        }

        public String getDefaultPic() {
            return defaultPic;
        }

        public void setDefaultPic(String defaultPic) {
            this.defaultPic = defaultPic;
        }

        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
