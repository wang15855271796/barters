package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/8/19
 */
public class HylMyModel {

    /**
     * code : 1
     * message : 成功
     * data : {"headImg":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//7213dc6f4ad74a7697dce8f53613d2a7.png","phone":"Hello,139****2245","shortName":"老干妈","giftNum":0,"point":0,"waitToPayNum":0,"customerPhone":"057187758340","andAppUpdate":false,"iosAppUpdate":false,"updateMsg":null,"downLoadUrl":null}
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
         * headImg : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//7213dc6f4ad74a7697dce8f53613d2a7.png
         * phone : Hello,139****2245
         * shortName : 老干妈
         * giftNum : 0
         * point : 0
         * waitToPayNum : 0
         * customerPhone : 057187758340
         * andAppUpdate : false
         * iosAppUpdate : false
         * updateMsg : null
         * downLoadUrl : null
         */

        private String headImg;
        private String phone;
        private String shortName;
        private int giftNum;
        private int point;
        private int waitToPayNum;
        private String customerPhone;
        private boolean andAppUpdate;
        private boolean iosAppUpdate;
        private String updateMsg;
        private String downLoadUrl;
        private String andVersion;

        public String getAndVersion() {
            return andVersion;
        }

        public void setAndVersion(String andVersion) {
            this.andVersion = andVersion;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public int getGiftNum() {
            return giftNum;
        }

        public void setGiftNum(int giftNum) {
            this.giftNum = giftNum;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getWaitToPayNum() {
            return waitToPayNum;
        }

        public void setWaitToPayNum(int waitToPayNum) {
            this.waitToPayNum = waitToPayNum;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public boolean isAndAppUpdate() {
            return andAppUpdate;
        }

        public void setAndAppUpdate(boolean andAppUpdate) {
            this.andAppUpdate = andAppUpdate;
        }

        public boolean isIosAppUpdate() {
            return iosAppUpdate;
        }

        public void setIosAppUpdate(boolean iosAppUpdate) {
            this.iosAppUpdate = iosAppUpdate;
        }

        public String getUpdateMsg() {
            return updateMsg;
        }

        public void setUpdateMsg(String updateMsg) {
            this.updateMsg = updateMsg;
        }

        public String getDownLoadUrl() {
            return downLoadUrl;
        }

        public void setDownLoadUrl(String downLoadUrl) {
            this.downLoadUrl = downLoadUrl;
        }
    }
}
