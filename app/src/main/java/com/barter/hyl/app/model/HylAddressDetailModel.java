package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/8/25
 */
public class HylAddressDetailModel {


    /**
     * code : 1
     * message : 成功
     * data : {"addressId":4,"userName":"未激活","contactPhone":"15462354544","detailAddress":"的稀缺地址111111","isDefault":1,"shopName":"店铺名称","provinceCode":"330000","provinceName":"浙江省","cityCode":"330100","cityName":"杭州市","areaCode":"330102","areaName":"上城区","setDefault":1}
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
         * addressId : 4
         * userName : 未激活
         * contactPhone : 15462354544
         * detailAddress : 的稀缺地址111111
         * isDefault : 1
         * shopName : 店铺名称
         * provinceCode : 330000
         * provinceName : 浙江省
         * cityCode : 330100
         * cityName : 杭州市
         * areaCode : 330102
         * areaName : 上城区
         * setDefault : 1
         */

        private int addressId;
        private String userName;
        private String contactPhone;
        private String detailAddress;
        private int isDefault;
        private String shopName;
        private String provinceCode;
        private String provinceName;
        private String cityCode;
        private String cityName;
        private String areaCode;
        private String areaName;
        private int setDefault;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getSetDefault() {
            return setDefault;
        }

        public void setSetDefault(int setDefault) {
            this.setDefault = setDefault;
        }
    }
}
