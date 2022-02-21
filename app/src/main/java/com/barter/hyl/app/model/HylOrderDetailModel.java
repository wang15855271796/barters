package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/2
 */
public class HylOrderDetailModel {

    /**
     * code : 1
     * message : 成功
     * data : {"orderTime":"2021-08-27 15:24:50","checkTime":"2021-08-27 15:25:37","expectPayTime":"2021-08-30 15:25:37","payTime":"","cancelTime":"","sendTime":"","receiveTime":"","applier":null,"applyTime":null,"returnType":null,"orderId":"20210827152450-a736","title":"我们将尽快发货，感谢您的信任","orderStatus":2,"orderStatusStr":"待发货-待接收","addressVO":{"id":384907,"orderId":405406,"userId":0,"userName":"未激活","contactPhone":"15462354544","provinceCode":"330000","provinceName":"浙江省","cityCode":"330100","cityName":"杭州市","areaCode":"330102","areaName":"上城区","detailAddress":"浙江省杭州市上城区的稀缺地址111111","isDefault":null,"shopName":"店铺名称","sendType":null},"prods":[{"businessId":8664,"businessType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","spec":"450ml","prodFlag":"","returnNum":null,"prices":["￥194.500000/箱 * 3"],"totalPrice":"583.50","additionNum":0,"additions":null}],"prodNum":1,"prodAmt":"583.50","deliveryFee":"0.00","deductAmt":"0.00","giftAmt":"0","payChannel":"货到付款","totalAmt":"583.50","payAmount":null,"shouldPayAmt":"583.50","memo":"我是备注","payFlag":false,"returnOrders":null,"checkStatus":null}
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
         * orderTime : 2021-08-27 15:24:50
         * checkTime : 2021-08-27 15:25:37
         * expectPayTime : 2021-08-30 15:25:37
         * payTime :
         * cancelTime :
         * sendTime :
         * receiveTime :
         * applier : null
         * applyTime : null
         * returnType : null
         * orderId : 20210827152450-a736
         * title : 我们将尽快发货，感谢您的信任
         * orderStatus : 2
         * orderStatusStr : 待发货-待接收
         * addressVO : {"id":384907,"orderId":405406,"userId":0,"userName":"未激活","contactPhone":"15462354544","provinceCode":"330000","provinceName":"浙江省","cityCode":"330100","cityName":"杭州市","areaCode":"330102","areaName":"上城区","detailAddress":"浙江省杭州市上城区的稀缺地址111111","isDefault":null,"shopName":"店铺名称","sendType":null}
         * prods : [{"businessId":8664,"businessType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","spec":"450ml","prodFlag":"","returnNum":null,"prices":["￥194.500000/箱 * 3"],"totalPrice":"583.50","additionNum":0,"additions":null}]
         * prodNum : 1
         * prodAmt : 583.50
         * deliveryFee : 0.00
         * deductAmt : 0.00
         * giftAmt : 0
         * payChannel : 货到付款
         * totalAmt : 583.50
         * payAmount : null
         * shouldPayAmt : 583.50
         * memo : 我是备注
         * payFlag : false
         * returnOrders : null
         * checkStatus : null
         */
        private String contactPhone;
        private String orderTime;
        private String checkTime;
        private String expectPayTime;
        private String payTime;
        private String cancelTime;
        private String sendTime;
        private String receiveTime;
        private String applier;
        private String applyTime;
        private String returnType;
        private String orderId;
        private String title;
        private int orderStatus;
        private String orderStatusStr;
        private AddressVOBean addressVO;
        private int prodNum;
        private String prodAmt;
        private String deliveryFee;
        private String deductAmt;
        private String giftAmt;
        private String payChannel;
        private String totalAmt;
        private Object payAmount;
        private String shouldPayAmt;
        private String memo;
        private boolean payFlag;
        private List<ReturnOrdersBean> returnOrders;
        private int checkStatus;
        private List<ProdsBean> prods;
        private String giftName;
        private long endTime;
        private long startTime;
        private long nowTime;
        private int orderType;
        private int id;
        private boolean offlinePay;
        private String offlinePayAmt;
        private String offlineRemainAmt;

        @Override
        public String toString() {
            return "DataBean{" +
                    "contactPhone='" + contactPhone + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    ", checkTime='" + checkTime + '\'' +
                    ", expectPayTime='" + expectPayTime + '\'' +
                    ", payTime='" + payTime + '\'' +
                    ", cancelTime='" + cancelTime + '\'' +
                    ", sendTime='" + sendTime + '\'' +
                    ", receiveTime='" + receiveTime + '\'' +
                    ", applier='" + applier + '\'' +
                    ", applyTime='" + applyTime + '\'' +
                    ", returnType='" + returnType + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", title='" + title + '\'' +
                    ", orderStatus=" + orderStatus +
                    ", orderStatusStr='" + orderStatusStr + '\'' +
                    ", addressVO=" + addressVO +
                    ", prodNum=" + prodNum +
                    ", prodAmt='" + prodAmt + '\'' +
                    ", deliveryFee='" + deliveryFee + '\'' +
                    ", deductAmt='" + deductAmt + '\'' +
                    ", giftAmt='" + giftAmt + '\'' +
                    ", payChannel='" + payChannel + '\'' +
                    ", totalAmt='" + totalAmt + '\'' +
                    ", payAmount=" + payAmount +
                    ", shouldPayAmt='" + shouldPayAmt + '\'' +
                    ", memo='" + memo + '\'' +
                    ", payFlag=" + payFlag +
                    ", returnOrders=" + returnOrders +
                    ", checkStatus=" + checkStatus +
                    ", prods=" + prods +
                    ", giftName='" + giftName + '\'' +
                    ", endTime=" + endTime +
                    ", startTime=" + startTime +
                    ", nowTime=" + nowTime +
                    '}';
        }

        public boolean getOfflinePay() {
            return offlinePay;
        }

        public void setOfflinePay(boolean offlinePay) {
            this.offlinePay = offlinePay;
        }

        public String getOfflinePayAmt() {
            return offlinePayAmt;
        }

        public void setOfflinePayAmt(String offlinePayAmt) {
            this.offlinePayAmt = offlinePayAmt;
        }

        public String getOfflineRemainAmt() {
            return offlineRemainAmt;
        }

        public void setOfflineRemainAmt(String offlineRemainAmt) {
            this.offlineRemainAmt = offlineRemainAmt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public long getNowTime() {
            return nowTime;
        }

        public void setNowTime(long nowTime) {
            this.nowTime = nowTime;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public String getExpectPayTime() {
            return expectPayTime;
        }

        public void setExpectPayTime(String expectPayTime) {
            this.expectPayTime = expectPayTime;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getApplier() {
            return applier;
        }

        public void setApplier(String applier) {
            this.applier = applier;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getReturnType() {
            return returnType;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusStr() {
            return orderStatusStr;
        }

        public void setOrderStatusStr(String orderStatusStr) {
            this.orderStatusStr = orderStatusStr;
        }

        public AddressVOBean getAddressVO() {
            return addressVO;
        }

        public void setAddressVO(AddressVOBean addressVO) {
            this.addressVO = addressVO;
        }

        public int getProdNum() {
            return prodNum;
        }

        public void setProdNum(int prodNum) {
            this.prodNum = prodNum;
        }

        public String getProdAmt() {
            return prodAmt;
        }

        public void setProdAmt(String prodAmt) {
            this.prodAmt = prodAmt;
        }

        public String getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(String deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public String getDeductAmt() {
            return deductAmt;
        }

        public void setDeductAmt(String deductAmt) {
            this.deductAmt = deductAmt;
        }

        public String getGiftAmt() {
            return giftAmt;
        }

        public void setGiftAmt(String giftAmt) {
            this.giftAmt = giftAmt;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getTotalAmt() {
            return totalAmt;
        }

        public void setTotalAmt(String totalAmt) {
            this.totalAmt = totalAmt;
        }

        public Object getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(Object payAmount) {
            this.payAmount = payAmount;
        }

        public String getShouldPayAmt() {
            return shouldPayAmt;
        }

        public void setShouldPayAmt(String shouldPayAmt) {
            this.shouldPayAmt = shouldPayAmt;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public boolean isPayFlag() {
            return payFlag;
        }

        public void setPayFlag(boolean payFlag) {
            this.payFlag = payFlag;
        }

        public List<ReturnOrdersBean> getReturnOrders() {
            return returnOrders;
        }

        public void setReturnOrders(List<ReturnOrdersBean> returnOrders) {
            this.returnOrders = returnOrders;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public List<ProdsBean> getProds() {
            return prods;
        }

        public void setProds(List<ProdsBean> prods) {
            this.prods = prods;
        }

        public static class AddressVOBean {
            /**
             * id : 384907
             * orderId : 405406
             * userId : 0
             * userName : 未激活
             * contactPhone : 15462354544
             * provinceCode : 330000
             * provinceName : 浙江省
             * cityCode : 330100
             * cityName : 杭州市
             * areaCode : 330102
             * areaName : 上城区
             * detailAddress : 浙江省杭州市上城区的稀缺地址111111
             * isDefault : null
             * shopName : 店铺名称
             * sendType : null
             */

            private int id;
            private int orderId;
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
            private Object isDefault;
            private String shopName;

            @Override
            public String toString() {
                return "AddressVOBean{" +
                        "id=" + id +
                        ", orderId=" + orderId +
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
                        ", sendType=" + sendType +
                        '}';
            }

            private Object sendType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
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

            public Object getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(Object isDefault) {
                this.isDefault = isDefault;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public Object getSendType() {
                return sendType;
            }

            public void setSendType(Object sendType) {
                this.sendType = sendType;
            }
        }

        public static class ProdsBean {
            /**
             * businessId : 8664
             * businessType : 1
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
             * productName : 老干妈大豆酱
             * spec : 450ml
             * prodFlag :
             * returnNum : null
             * prices : ["￥194.500000/箱 * 3"]
             * totalPrice : 583.50
             * additionNum : 0
             * additions : null
             */

            private int businessId;
            private int businessType;
            private String defaultPic;
            private String productName;
            private String spec;
            private String prodFlag;
            private String returnNum;
            private String totalPrice;
            private int additionNum;
            private Object additions;
            private List<String> prices;

            @Override
            public String toString() {
                return "ProdsBean{" +
                        "businessId=" + businessId +
                        ", businessType=" + businessType +
                        ", defaultPic='" + defaultPic + '\'' +
                        ", productName='" + productName + '\'' +
                        ", spec='" + spec + '\'' +
                        ", prodFlag='" + prodFlag + '\'' +
                        ", returnNum='" + returnNum + '\'' +
                        ", totalPrice='" + totalPrice + '\'' +
                        ", additionNum=" + additionNum +
                        ", additions=" + additions +
                        ", prices=" + prices +
                        '}';
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

            public String getReturnNum() {
                return returnNum;
            }

            public void setReturnNum(String returnNum) {
                this.returnNum = returnNum;
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

            public Object getAdditions() {
                return additions;
            }

            public void setAdditions(Object additions) {
                this.additions = additions;
            }

            public List<String> getPrices() {
                return prices;
            }

            public void setPrices(List<String> prices) {
                this.prices = prices;
            }
        }

        public static class ReturnOrdersBean {
            String amount;
            String returnDesc;
            String returnMainId;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getReturnDesc() {
                return returnDesc;
            }

            public void setReturnDesc(String returnDesc) {
                this.returnDesc = returnDesc;
            }

            public String getReturnMainId() {
                return returnMainId;
            }

            public void setReturnMainId(String returnMainId) {
                this.returnMainId = returnMainId;
            }

            @Override
            public String toString() {
                return "ReturnProdsBean{" +
                        "amount='" + amount + '\'' +
                        ", returnDesc='" + returnDesc + '\'' +
                        ", returnMainId='" + returnMainId + '\'' +
                        '}';
            }
        }
    }
}
