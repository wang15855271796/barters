package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class HylSettingModel {


    /**
     * code : 1
     * message : 成功
     * data : {"phone":"13911112245","encryptPhone":"139****2245","companyName":"杭州老干妈","authCode":"1","licensePic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//3c99412bdafc44c681368b9051d15b25.png","businessPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//cc479b5f2aa544aba4823b04c16f173b.png","register":"http://staff.qoger.com/h5/enter.html","privacy":"https://shaokao.qoger.com/apph5/html/yszc.html"}
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
         * phone : 13911112245
         * encryptPhone : 139****2245
         * companyName : 杭州老干妈
         * authCode : 1
         * licensePic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//3c99412bdafc44c681368b9051d15b25.png
         * businessPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//cc479b5f2aa544aba4823b04c16f173b.png
         * register : http://staff.qoger.com/h5/enter.html
         * privacy : https://shaokao.qoger.com/apph5/html/yszc.html
         */

        private String phone;
        private String encryptPhone;
        private String companyName;
        private String authCode;
        private String licensePic;
        private String businessPic;
        private String register;
        private String privacy;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEncryptPhone() {
            return encryptPhone;
        }

        public void setEncryptPhone(String encryptPhone) {
            this.encryptPhone = encryptPhone;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public String getLicensePic() {
            return licensePic;
        }

        public void setLicensePic(String licensePic) {
            this.licensePic = licensePic;
        }

        public String getBusinessPic() {
            return businessPic;
        }

        public void setBusinessPic(String businessPic) {
            this.businessPic = businessPic;
        }

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }
    }
}
