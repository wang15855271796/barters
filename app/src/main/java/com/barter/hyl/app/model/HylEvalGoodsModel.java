package com.barter.hyl.app.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ${王涛} on 2021/9/17
 */
public class HylEvalGoodsModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"businessId":9179,"businessType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","prodName":"老干妈大豆酱","level":null,"content":null,"pics":null}]
     * success : true
     * error : false
     */

    private int code;
    private String message;
    private boolean success;
    private boolean error;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * businessId : 9179
         * businessType : 1
         * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
         * prodName : 老干妈大豆酱
         * level : null
         * content : null
         * pics : null
         */

        private int businessId;
        private int businessType;
        private String defaultPic;
        private String prodName;
        private String level;
        private String content;
        private List<String> pics;

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public int getBusinessType() {
            return businessType;
        }

        public void setBusinessType(int businessType) {
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
            if (pics==null){
                return  new ArrayList<>();
            }else {
                return pics;
            }

        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
