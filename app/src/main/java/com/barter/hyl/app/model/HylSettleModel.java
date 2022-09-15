package com.barter.hyl.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24
 */
public class HylSettleModel {

    /**
     * code : 1
     * message : 成功
     * data : {"hasDefaultAddressFlag":false,"addressVO":null,"prods":[{"businessId":8664,"businessType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","spec":"450ml","prodFlag":"","prices":["￥90.00/瓶 * 1","￥194.50/箱 * 1","￥4040.00/2箱 * 1"],"totalPrice":"4324.50","additionNum":1,"additions":[{"type":1,"defaultPic":null,"prodName":"1元优惠券","spec":null,"additionFlag":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","sendNum":"1张"}]}],"prodNum":1,"prodAmount":"4324.50","deliverFee":"0","freeDeliverFee":"满200元免配送费","giftInfo":"暂无可用优惠券","giftNo":null,"totalAmount":"4324.5"}
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

    public static class DataBean implements Serializable {
        /**
         * hasDefaultAddressFlag : false
         * addressVO : null
         * prods : [{"businessId":8664,"businessType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","spec":"450ml","prodFlag":"","prices":["￥90.00/瓶 * 1","￥194.50/箱 * 1","￥4040.00/2箱 * 1"],"totalPrice":"4324.50","additionNum":1,"additions":[{"type":1,"defaultPic":null,"prodName":"1元优惠券","spec":null,"additionFlag":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","sendNum":"1张"}]}]
         * prodNum : 1
         * prodAmount : 4324.50
         * deliverFee : 0
         * freeDeliverFee : 满200元免配送费
         * giftInfo : 暂无可用优惠券
         * giftNo : null
         * totalAmount : 4324.5
         */

        private boolean hasDefaultAddressFlag;
        private AddressVOBean addressVO;
        private int prodNum;
        private String prodAmount;
        private String deliverFee;
        private String freeDeliverFee;
        private String giftInfo;
        private String giftNo;
        private String totalAmount;
        private List<ProdsBean> prods;
        private List<AdditionsBean> additions;
        @Override
        public String toString() {
            return "DataBean{" +
                    "hasDefaultAddressFlag=" + hasDefaultAddressFlag +
                    ", addressVO=" + addressVO +
                    ", prodNum=" + prodNum +
                    ", prodAmount='" + prodAmount + '\'' +
                    ", deliverFee='" + deliverFee + '\'' +
                    ", freeDeliverFee='" + freeDeliverFee + '\'' +
                    ", giftInfo='" + giftInfo + '\'' +
                    ", giftNo=" + giftNo +
                    ", totalAmount='" + totalAmount + '\'' +
                    ", prods=" + prods +
                    '}';
        }

        public AddressVOBean getAddressVO() {
            return addressVO;
        }

        public void setAddressVO(AddressVOBean addressVO) {
            this.addressVO = addressVO;
        }

        public boolean isHasDefaultAddressFlag() {
            return hasDefaultAddressFlag;
        }

        public void setHasDefaultAddressFlag(boolean hasDefaultAddressFlag) {
            this.hasDefaultAddressFlag = hasDefaultAddressFlag;
        }

        public int getProdNum() {
            return prodNum;
        }

        public void setProdNum(int prodNum) {
            this.prodNum = prodNum;
        }

        public String getProdAmount() {
            return prodAmount;
        }

        public void setProdAmount(String prodAmount) {
            this.prodAmount = prodAmount;
        }

        public String getDeliverFee() {
            return deliverFee;
        }

        public void setDeliverFee(String deliverFee) {
            this.deliverFee = deliverFee;
        }

        public String getFreeDeliverFee() {
            return freeDeliverFee;
        }

        public void setFreeDeliverFee(String freeDeliverFee) {
            this.freeDeliverFee = freeDeliverFee;
        }

        public String getGiftInfo() {
            return giftInfo;
        }

        public void setGiftInfo(String giftInfo) {
            this.giftInfo = giftInfo;
        }

        public String getGiftNo() {
            return giftNo;
        }

        public void setGiftNo(String giftNo) {
            this.giftNo = giftNo;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<ProdsBean> getProds() {
            return prods;
        }

        public void setProds(List<ProdsBean> prods) {
            this.prods = prods;
        }

        public List<AdditionsBean> getAdditions() {
            return additions;
        }

        public void setAdditions(List<AdditionsBean> additions) {
            this.additions = additions;
        }

        public static class AddressVOBean implements Serializable{
            @Override
            public String toString() {
                return "AddressVOBean{" +
                        "id=" + id +
                        ", userId=" + userId +
                        ", userName='" + userName + '\'' +
                        ", contactPhone='" + contactPhone + '\'' +
                        ", provinceCode='" + provinceCode + '\'' +
                        ", provinceName='" + provinceName + '\'' +
                        ", cityCode='" + cityCode + '\'' +
                        ", cityName='" + cityName + '\'' +
                        ", areaCode='" + areaCode + '\'' +
                        ", areaName='" + areaName + '\'' +
                        ", detailAddress='" + detailAddress + '\'' +
                        ", isDefault=" + isDefault +
                        ", shopName='" + shopName + '\'' +
                        '}';
            }

            /**
             * id : 29
             * userId : 302
             * userName : 韩铝熙
             * contactPhone : 17369639769
             * provinceCode : 330000
             * provinceName : 浙江省
             * cityCode : 330100
             * cityName : 杭州市
             * areaCode : 330108
             * areaName : 滨江区
             * detailAddress : 地址详细。
             * isDefault : 1
             * shopName : 京东
             */

            private int id;
            private int userId;
            private String userName;
            private String contactPhone;
            private String provinceCode;
            private String provinceName;
            private String cityCode;
            private String cityName;
            private String areaCode;
            private String areaName;
            private String detailAddress;
            private int isDefault;
            private String shopName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
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
        }

        public static class AdditionsBean implements Serializable{
            /**
             * type : 1
             * defaultPic : null
             * prodName : 1元优惠券
             * spec : null
             * additionFlag : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png
             * sendNum : 1张
             */

            private int type;
            private String defaultPic;
            private String prodName;
            private Object spec;
            private String additionFlag;
            private String sendNum;
            int sendFlag;

            public int getSendFlag() {
                return sendFlag;
            }

            public void setSendFlag(int sendFlag) {
                this.sendFlag = sendFlag;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public Object getSpec() {
                return spec;
            }

            public void setSpec(Object spec) {
                this.spec = spec;
            }

            public String getAdditionFlag() {
                return additionFlag;
            }

            public void setAdditionFlag(String additionFlag) {
                this.additionFlag = additionFlag;
            }

            public String getSendNum() {
                return sendNum;
            }

            public void setSendNum(String sendNum) {
                this.sendNum = sendNum;
            }
        }

        public static class ProdsBean implements Serializable{
            /**
             * businessId : 8664
             * businessType : 1
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
             * productName : 老干妈大豆酱
             * spec : 450ml
             * prodFlag :
             * prices : ["￥90.00/瓶 * 1","￥194.50/箱 * 1","￥4040.00/2箱 * 1"]
             * totalPrice : 4324.50
             * additionNum : 1
             * additions : [{"type":1,"defaultPic":null,"prodName":"1元优惠券","spec":null,"additionFlag":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","sendNum":"1张"}]
             */

            private int businessId;
            private int businessType;
            private String defaultPic;
            private String productName;
            private String spec;
            private String prodFlag;
            private String totalPrice;
            private int additionNum;
            private List<String> prices;
            int sendFlag;
//            private List<AdditionsBean> additions;

            @Override
            public String toString() {
                return "ProdsBean{" +
                        "businessId=" + businessId +
                        ", businessType=" + businessType +
                        ", defaultPic='" + defaultPic + '\'' +
                        ", productName='" + productName + '\'' +
                        ", spec='" + spec + '\'' +
                        ", prodFlag='" + prodFlag + '\'' +
                        ", totalPrice='" + totalPrice + '\'' +
                        ", additionNum=" + additionNum +
                        ", prices=" + prices +
                        ", additions=" +  +
                        '}';
            }

            public int getSendFlag() {
                return sendFlag;
            }

            public void setSendFlag(int sendFlag) {
                this.sendFlag = sendFlag;
            }

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

            public String getProdFlag() {
                return prodFlag;
            }

            public void setProdFlag(String prodFlag) {
                this.prodFlag = prodFlag;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getAdditionNum() {
                return additionNum;
            }

            public void setAdditionNum(int additionNum) {
                this.additionNum = additionNum;
            }

            public List<String> getPrices() {
                return prices;
            }

            public void setPrices(List<String> prices) {
                this.prices = prices;
            }

//            public List<AdditionsBean> getAdditions() {
//                return additions;
//            }
//
//            public void setAdditions(List<AdditionsBean> additions) {
//                this.additions = additions;
//            }
        }


    }
}
