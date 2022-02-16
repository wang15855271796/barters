package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24
 */
public class HylCartListModel {


    /**
     * code : 1
     * message : 成功
     * data : {"validList":[{"productName":"老干妈大豆酱","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","businessType":1,"priceStr":"","productMainId":8664,"businessId":null,"sendTimeStr":"","sendTimeTpl":"","selfOrNot":0,"specProductList":[{"businessId":9179,"inventory":"","cartId":1660866,"picUrl":null,"spec":"450ml","buyNumLimit":"单日限购4箱","notSend":1,"additionFlag":1,"productDescVOList":[{"price":"194.50","priceStr":"￥194.5/箱","unit":null,"highNum":1,"productNum":1,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null},{"price":"1.50","priceStr":"￥1.5/小瓶","unit":null,"highNum":1,"productNum":4,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null}],"additionProductVOList":null,"additionVOList":[{"type":1,"productId":null,"productMainId":null,"giftPoolNo":"1002021081600000222","warehouseId":null,"productUnit":null,"name":"1元优惠券","spec":null,"picUrl":null,"sendNum":1,"sendNumDesc":"1张","amount":"1","additionFlag":1,"triggerNum":1,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}],"buySendAdditionInfo":""}],"additionProductVOList":null,"valid":true},{"productName":"老干妈大豆酱","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylms%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","businessType":2,"priceStr":"","productMainId":5050,"businessId":null,"sendTimeStr":"","sendTimeTpl":"","selfOrNot":0,"specProductList":[{"businessId":5050,"inventory":"","cartId":1660867,"picUrl":null,"spec":null,"buyNumLimit":"单日限购3袋","notSend":null,"additionFlag":0,"productDescVOList":[{"price":"20.00","priceStr":"￥20","unit":null,"highNum":1,"productNum":3,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null}],"additionProductVOList":null,"additionVOList":null,"buySendAdditionInfo":""}],"additionProductVOList":null,"valid":true}],"inValidList":[],"sendAmount":"200.00","selfSendTime":"","unSelfSendTime":""}
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
         * validList : [{"productName":"老干妈大豆酱","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","businessType":1,"priceStr":"","productMainId":8664,"businessId":null,"sendTimeStr":"","sendTimeTpl":"","selfOrNot":0,"specProductList":[{"businessId":9179,"inventory":"","cartId":1660866,"picUrl":null,"spec":"450ml","buyNumLimit":"单日限购4箱","notSend":1,"additionFlag":1,"productDescVOList":[{"price":"194.50","priceStr":"￥194.5/箱","unit":null,"highNum":1,"productNum":1,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null},{"price":"1.50","priceStr":"￥1.5/小瓶","unit":null,"highNum":1,"productNum":4,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null}],"additionProductVOList":null,"additionVOList":[{"type":1,"productId":null,"productMainId":null,"giftPoolNo":"1002021081600000222","warehouseId":null,"productUnit":null,"name":"1元优惠券","spec":null,"picUrl":null,"sendNum":1,"sendNumDesc":"1张","amount":"1","additionFlag":1,"triggerNum":1,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}],"buySendAdditionInfo":""}],"additionProductVOList":null,"valid":true},{"productName":"老干妈大豆酱","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylms%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","businessType":2,"priceStr":"","productMainId":5050,"businessId":null,"sendTimeStr":"","sendTimeTpl":"","selfOrNot":0,"specProductList":[{"businessId":5050,"inventory":"","cartId":1660867,"picUrl":null,"spec":null,"buyNumLimit":"单日限购3袋","notSend":null,"additionFlag":0,"productDescVOList":[{"price":"20.00","priceStr":"￥20","unit":null,"highNum":1,"productNum":3,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null}],"additionProductVOList":null,"additionVOList":null,"buySendAdditionInfo":""}],"additionProductVOList":null,"valid":true}]
         * inValidList : []
         * sendAmount : 200.00
         * selfSendTime :
         * unSelfSendTime :
         */

        private String sendAmount;
        private String selfSendTime;
        private String unSelfSendTime;
        private List<ValidListBean> validList;
        private List<InValidListBean> inValidList;

        public String getSendAmount() {
            return sendAmount;
        }

        public void setSendAmount(String sendAmount) {
            this.sendAmount = sendAmount;
        }

        public String getSelfSendTime() {
            return selfSendTime;
        }

        public void setSelfSendTime(String selfSendTime) {
            this.selfSendTime = selfSendTime;
        }

        public String getUnSelfSendTime() {
            return unSelfSendTime;
        }

        public void setUnSelfSendTime(String unSelfSendTime) {
            this.unSelfSendTime = unSelfSendTime;
        }

        public List<ValidListBean> getValidList() {
            return validList;
        }

        public void setValidList(List<ValidListBean> validList) {
            this.validList = validList;
        }

        public List<InValidListBean> getInValidList() {
            return inValidList;
        }

        public void setInValidList(List<InValidListBean> inValidList) {
            this.inValidList = inValidList;
        }

        public static class ValidListBean {
            /**
             * productName : 老干妈大豆酱
             * flagUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
             * businessType : 1
             * priceStr :
             * productMainId : 8664
             * businessId : null
             * sendTimeStr :
             * sendTimeTpl :
             * selfOrNot : 0
             * specProductList : [{"businessId":9179,"inventory":"","cartId":1660866,"picUrl":null,"spec":"450ml","buyNumLimit":"单日限购4箱","notSend":1,"additionFlag":1,"productDescVOList":[{"price":"194.50","priceStr":"￥194.5/箱","unit":null,"highNum":1,"productNum":1,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null},{"price":"1.50","priceStr":"￥1.5/小瓶","unit":null,"highNum":1,"productNum":4,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null}],"additionProductVOList":null,"additionVOList":[{"type":1,"productId":null,"productMainId":null,"giftPoolNo":"1002021081600000222","warehouseId":null,"productUnit":null,"name":"1元优惠券","spec":null,"picUrl":null,"sendNum":1,"sendNumDesc":"1张","amount":"1","additionFlag":1,"triggerNum":1,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}],"buySendAdditionInfo":""}]
             * additionProductVOList : null
             * valid : true
             */
            //判断是否选中
            private boolean isSelected = true;
            private String productName;
            private String flagUrl;
            private String defaultPic;
            private int businessType;
            private String priceStr;
            private int productMainId;
            private String businessId;
            private String sendTimeStr;
            private String sendTimeTpl;
            private int selfOrNot;
            private Object additionProductVOList;
            private boolean valid;
            private List<SpecProductListBean> specProductList;

            @Override
            public String toString() {
                return "ValidListBean{" +
                        "isSelected=" + isSelected +
                        ", productName='" + productName + '\'' +
                        ", flagUrl='" + flagUrl + '\'' +
                        ", defaultPic='" + defaultPic + '\'' +
                        ", businessType=" + businessType +
                        ", priceStr='" + priceStr + '\'' +
                        ", productMainId=" + productMainId +
                        ", businessId=" + businessId +
                        ", sendTimeStr='" + sendTimeStr + '\'' +
                        ", sendTimeTpl='" + sendTimeTpl + '\'' +
                        ", selfOrNot=" + selfOrNot +
                        ", additionProductVOList=" + additionProductVOList +
                        ", valid=" + valid +
                        ", specProductList=" + specProductList +
                        '}';
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getFlagUrl() {
                return flagUrl;
            }

            public void setFlagUrl(String flagUrl) {
                this.flagUrl = flagUrl;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }

            public String getPriceStr() {
                return priceStr;
            }

            public void setPriceStr(String priceStr) {
                this.priceStr = priceStr;
            }

            public int getProductMainId() {
                return productMainId;
            }

            public void setProductMainId(int productMainId) {
                this.productMainId = productMainId;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getSendTimeStr() {
                return sendTimeStr;
            }

            public void setSendTimeStr(String sendTimeStr) {
                this.sendTimeStr = sendTimeStr;
            }

            public String getSendTimeTpl() {
                return sendTimeTpl;
            }

            public void setSendTimeTpl(String sendTimeTpl) {
                this.sendTimeTpl = sendTimeTpl;
            }

            public int getSelfOrNot() {
                return selfOrNot;
            }

            public void setSelfOrNot(int selfOrNot) {
                this.selfOrNot = selfOrNot;
            }

            public Object getAdditionProductVOList() {
                return additionProductVOList;
            }

            public void setAdditionProductVOList(Object additionProductVOList) {
                this.additionProductVOList = additionProductVOList;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }

            public List<SpecProductListBean> getSpecProductList() {
                return specProductList;
            }

            public void setSpecProductList(List<SpecProductListBean> specProductList) {
                this.specProductList = specProductList;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public static class SpecProductListBean {
                /**
                 * businessId : 9179
                 * inventory :
                 * cartId : 1660866
                 * picUrl : null
                 * spec : 450ml
                 * buyNumLimit : 单日限购4箱
                 * notSend : 1
                 * additionFlag : 1
                 * productDescVOList : [{"price":"194.50","priceStr":"￥194.5/箱","unit":null,"highNum":1,"productNum":1,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null},{"price":"1.50","priceStr":"￥1.5/小瓶","unit":null,"highNum":1,"productNum":4,"unitDesc":"","productCombinationPriceId":null,"oldPrice":null}]
                 * additionProductVOList : null
                 * additionVOList : [{"type":1,"productId":null,"productMainId":null,"giftPoolNo":"1002021081600000222","warehouseId":null,"productUnit":null,"name":"1元优惠券","spec":null,"picUrl":null,"sendNum":1,"sendNumDesc":"1张","amount":"1","additionFlag":1,"triggerNum":1,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}]
                 * buySendAdditionInfo :
                 */
                boolean isSelected = true;
                private int businessId;
                private String inventory;
                private int cartId;
                private Object picUrl;
                private String spec;
                private String buyNumLimit;
                private int notSend;
                private int additionFlag;
                private Object additionProductVOList;
                private String buySendAdditionInfo;
                private List<ProductDescVOListBean> productDescVOList;
                private List<AdditionVOListBean> additionVOList;

                public int getBusinessId() {
                    return businessId;
                }

                public void setBusinessId(int businessId) {
                    this.businessId = businessId;
                }

                public String getInventory() {
                    return inventory;
                }

                public void setInventory(String inventory) {
                    this.inventory = inventory;
                }

                public int getCartId() {
                    return cartId;
                }

                public void setCartId(int cartId) {
                    this.cartId = cartId;
                }

                public Object getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(Object picUrl) {
                    this.picUrl = picUrl;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }

                public String getBuyNumLimit() {
                    return buyNumLimit;
                }

                public void setBuyNumLimit(String buyNumLimit) {
                    this.buyNumLimit = buyNumLimit;
                }

                public int getNotSend() {
                    return notSend;
                }

                public void setNotSend(int notSend) {
                    this.notSend = notSend;
                }

                public int getAdditionFlag() {
                    return additionFlag;
                }

                public void setAdditionFlag(int additionFlag) {
                    this.additionFlag = additionFlag;
                }

                public Object getAdditionProductVOList() {
                    return additionProductVOList;
                }

                public void setAdditionProductVOList(Object additionProductVOList) {
                    this.additionProductVOList = additionProductVOList;
                }

                public String getBuySendAdditionInfo() {
                    return buySendAdditionInfo;
                }

                public void setBuySendAdditionInfo(String buySendAdditionInfo) {
                    this.buySendAdditionInfo = buySendAdditionInfo;
                }

                public List<ProductDescVOListBean> getProductDescVOList() {
                    return productDescVOList;
                }

                public void setProductDescVOList(List<ProductDescVOListBean> productDescVOList) {
                    this.productDescVOList = productDescVOList;
                }

                public List<AdditionVOListBean> getAdditionVOList() {
                    return additionVOList;
                }

                public void setAdditionVOList(List<AdditionVOListBean> additionVOList) {
                    this.additionVOList = additionVOList;
                }

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

                public static class ProductDescVOListBean {
                    /**
                     * price : 194.50
                     * priceStr : ￥194.5/箱
                     * unit : null
                     * highNum : 1
                     * productNum : 1
                     * unitDesc :
                     * productCombinationPriceId : null
                     * oldPrice : null
                     */

                    private String price;
                    private String priceStr;
                    private Object unit;
                    private int highNum;
                    private int productNum;
                    private String unitDesc;
                    private int productCombinationPriceId;
                    private String oldPrice;

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getPriceStr() {
                        return priceStr;
                    }

                    public void setPriceStr(String priceStr) {
                        this.priceStr = priceStr;
                    }

                    public Object getUnit() {
                        return unit;
                    }

                    public void setUnit(Object unit) {
                        this.unit = unit;
                    }

                    public int getHighNum() {
                        return highNum;
                    }

                    public void setHighNum(int highNum) {
                        this.highNum = highNum;
                    }

                    public int getProductNum() {
                        return productNum;
                    }

                    public void setProductNum(int productNum) {
                        this.productNum = productNum;
                    }

                    public String getUnitDesc() {
                        return unitDesc;
                    }

                    public void setUnitDesc(String unitDesc) {
                        this.unitDesc = unitDesc;
                    }

                    public int getProductCombinationPriceId() {
                        return productCombinationPriceId;
                    }

                    public void setProductCombinationPriceId(int productCombinationPriceId) {
                        this.productCombinationPriceId = productCombinationPriceId;
                    }

                    public String getOldPrice() {
                        return oldPrice;
                    }

                    public void setOldPrice(String oldPrice) {
                        this.oldPrice = oldPrice;
                    }
                }

                public static class AdditionVOListBean {
                    @Override
                    public String toString() {
                        return "AdditionVOListBean{" +
                                "type=" + type +
                                ", productId=" + productId +
                                ", productMainId=" + productMainId +
                                ", giftPoolNo='" + giftPoolNo + '\'' +
                                ", warehouseId=" + warehouseId +
                                ", productUnit=" + productUnit +
                                ", name='" + name + '\'' +
                                ", spec=" + spec +
                                ", picUrl=" + picUrl +
                                ", sendNum=" + sendNum +
                                ", sendNumDesc='" + sendNumDesc + '\'' +
                                ", amount='" + amount + '\'' +
                                ", additionFlag=" + additionFlag +
                                ", triggerNum=" + triggerNum +
                                ", flagUrl='" + flagUrl + '\'' +
                                ", finishUrl='" + finishUrl + '\'' +
                                ", supplierId=" + supplierId +
                                '}';
                    }

                    /**
                     * type : 1
                     * productId : null
                     * productMainId : null
                     * giftPoolNo : 1002021081600000222
                     * warehouseId : null
                     * productUnit : null
                     * name : 1元优惠券
                     * spec : null
                     * picUrl : null
                     * sendNum : 1
                     * sendNumDesc : 1张
                     * amount : 1
                     * additionFlag : 1
                     * triggerNum : 1
                     * flagUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png
                     * finishUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png
                     * supplierId : 0
                     */

                    private int type;
                    private Object productId;
                    private Object productMainId;
                    private String giftPoolNo;
                    private Object warehouseId;
                    private Object productUnit;
                    private String name;
                    private Object spec;
                    private Object picUrl;
                    private int sendNum;
                    private String sendNumDesc;
                    private String amount;
                    private int additionFlag;
                    private int triggerNum;
                    private String flagUrl;
                    private String finishUrl;
                    private int supplierId;

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public Object getProductId() {
                        return productId;
                    }

                    public void setProductId(Object productId) {
                        this.productId = productId;
                    }

                    public Object getProductMainId() {
                        return productMainId;
                    }

                    public void setProductMainId(Object productMainId) {
                        this.productMainId = productMainId;
                    }

                    public String getGiftPoolNo() {
                        return giftPoolNo;
                    }

                    public void setGiftPoolNo(String giftPoolNo) {
                        this.giftPoolNo = giftPoolNo;
                    }

                    public Object getWarehouseId() {
                        return warehouseId;
                    }

                    public void setWarehouseId(Object warehouseId) {
                        this.warehouseId = warehouseId;
                    }

                    public Object getProductUnit() {
                        return productUnit;
                    }

                    public void setProductUnit(Object productUnit) {
                        this.productUnit = productUnit;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public Object getSpec() {
                        return spec;
                    }

                    public void setSpec(Object spec) {
                        this.spec = spec;
                    }

                    public Object getPicUrl() {
                        return picUrl;
                    }

                    public void setPicUrl(Object picUrl) {
                        this.picUrl = picUrl;
                    }

                    public int getSendNum() {
                        return sendNum;
                    }

                    public void setSendNum(int sendNum) {
                        this.sendNum = sendNum;
                    }

                    public String getSendNumDesc() {
                        return sendNumDesc;
                    }

                    public void setSendNumDesc(String sendNumDesc) {
                        this.sendNumDesc = sendNumDesc;
                    }

                    public String getAmount() {
                        return amount;
                    }

                    public void setAmount(String amount) {
                        this.amount = amount;
                    }

                    public int getAdditionFlag() {
                        return additionFlag;
                    }

                    public void setAdditionFlag(int additionFlag) {
                        this.additionFlag = additionFlag;
                    }

                    public int getTriggerNum() {
                        return triggerNum;
                    }

                    public void setTriggerNum(int triggerNum) {
                        this.triggerNum = triggerNum;
                    }

                    public String getFlagUrl() {
                        return flagUrl;
                    }

                    public void setFlagUrl(String flagUrl) {
                        this.flagUrl = flagUrl;
                    }

                    public String getFinishUrl() {
                        return finishUrl;
                    }

                    public void setFinishUrl(String finishUrl) {
                        this.finishUrl = finishUrl;
                    }

                    public int getSupplierId() {
                        return supplierId;
                    }

                    public void setSupplierId(int supplierId) {
                        this.supplierId = supplierId;
                    }
                }

            }
        }

        public static class InValidListBean {
            /**
             * productName : 旺达-15
             * flagUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/afa2f351e7d74757bd59b740901440d1.png
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
             * businessType : 11
             * specProductList : [{"businessId":398,"inventory":"10箱","cartId":12941,"picUrl":null,"spec":"150g*4个*18包/箱","productDescVOList":[{"price":"220.00","priceStr":"￥220.00/箱","unit":15,"highNum":1,"productNum":5,"unitDesc":"10盒/箱","productCombinationPriceId":null,"oldPrice":"280.00/箱"}]}]
             * additionProductVOList : null
             * valid : false
             */
            private String priceStr;
            private String productName;
            private String flagUrl;
            private String defaultPic;
            private int businessType;
            private Object additionProductVOList;
            private boolean valid;
            private List<SpecProductListBeanX> specProductList;

            public String getPriceStr() {
                return priceStr;
            }

            public void setPriceStr(String priceStr) {
                this.priceStr = priceStr;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getFlagUrl() {
                return flagUrl;
            }

            public void setFlagUrl(String flagUrl) {
                this.flagUrl = flagUrl;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }

            public Object getAdditionProductVOList() {
                return additionProductVOList;
            }

            public void setAdditionProductVOList(Object additionProductVOList) {
                this.additionProductVOList = additionProductVOList;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }

            public List<SpecProductListBeanX> getSpecProductList() {
                return specProductList;
            }

            public void setSpecProductList(List<SpecProductListBeanX> specProductList) {
                this.specProductList = specProductList;
            }

            public static class SpecProductListBeanX {
                /**
                 * businessId : 398
                 * inventory : 10箱
                 * cartId : 12941
                 * picUrl : null
                 * spec : 150g*4个*18包/箱
                 * productDescVOList : [{"price":"220.00","priceStr":"￥220.00/箱","unit":15,"highNum":1,"productNum":5,"unitDesc":"10盒/箱","productCombinationPriceId":null,"oldPrice":"280.00/箱"}]
                 */

                private int businessId;
                private String inventory;
                private int cartId;
                private Object picUrl;
                private String spec;
                private List<ProductDescVOListBeanX> productDescVOList;

                public int getBusinessId() {
                    return businessId;
                }

                public void setBusinessId(int businessId) {
                    this.businessId = businessId;
                }

                public String getInventory() {
                    return inventory;
                }

                public void setInventory(String inventory) {
                    this.inventory = inventory;
                }

                public int getCartId() {
                    return cartId;
                }

                public void setCartId(int cartId) {
                    this.cartId = cartId;
                }

                public Object getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(Object picUrl) {
                    this.picUrl = picUrl;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }

                public List<ProductDescVOListBeanX> getProductDescVOList() {
                    return productDescVOList;
                }

                public void setProductDescVOList(List<ProductDescVOListBeanX> productDescVOList) {
                    this.productDescVOList = productDescVOList;
                }

                public static class ProductDescVOListBeanX {
                    /**
                     * price : 220.00
                     * priceStr : ￥220.00/箱
                     * unit : 15
                     * highNum : 1
                     * productNum : 5
                     * unitDesc : 10盒/箱
                     * productCombinationPriceId : null
                     * oldPrice : 280.00/箱
                     */

                    private String price;
                    private String priceStr;
                    private int unit;
                    private int highNum;
                    private int productNum;
                    private String unitDesc;
                    private Object productCombinationPriceId;
                    private String oldPrice;

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getPriceStr() {
                        return priceStr;
                    }

                    public void setPriceStr(String priceStr) {
                        this.priceStr = priceStr;
                    }

                    public int getUnit() {
                        return unit;
                    }

                    public void setUnit(int unit) {
                        this.unit = unit;
                    }

                    public int getHighNum() {
                        return highNum;
                    }

                    public void setHighNum(int highNum) {
                        this.highNum = highNum;
                    }

                    public int getProductNum() {
                        return productNum;
                    }

                    public void setProductNum(int productNum) {
                        this.productNum = productNum;
                    }

                    public String getUnitDesc() {
                        return unitDesc;
                    }

                    public void setUnitDesc(String unitDesc) {
                        this.unitDesc = unitDesc;
                    }

                    public Object getProductCombinationPriceId() {
                        return productCombinationPriceId;
                    }

                    public void setProductCombinationPriceId(Object productCombinationPriceId) {
                        this.productCombinationPriceId = productCombinationPriceId;
                    }

                    public String getOldPrice() {
                        return oldPrice;
                    }

                    public void setOldPrice(String oldPrice) {
                        this.oldPrice = oldPrice;
                    }
                }
            }
        }

    }
}
