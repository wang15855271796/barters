package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/26
 */
public class HylReturnOrderDetailModel {

    /**
     * code : 1
     * message : 成功
     * data : {"prods":[{"businessId":9179,"businessType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","prodName":"老干妈大豆酱","spec":"450ml","details":[{"detailId":1453932,"showPrice":"194.500000/箱*3","price":"583.500000","units":[{"unitId":18457,"unitName":"箱"},{"unitId":18458,"unitName":"瓶"},{"unitId":18472,"unitName":"小瓶"}]}]}],"payFlag":false,"channel":null,"returnType":["错买","其他"],"orderId":"20210827152450-a736"}
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
         * prods : [{"businessId":9179,"businessType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","prodName":"老干妈大豆酱","spec":"450ml","details":[{"detailId":1453932,"showPrice":"194.500000/箱*3","price":"583.500000","units":[{"unitId":18457,"unitName":"箱"},{"unitId":18458,"unitName":"瓶"},{"unitId":18472,"unitName":"小瓶"}]}]}]
         * payFlag : false
         * channel : null
         * returnType : ["错买","其他"]
         * orderId : 20210827152450-a736
         */

        private boolean payFlag;
        private String channel;
        private String orderId;
        private List<ProdsBean> prods;
        private List<String> returnType;
        int allReturn;
        private String totalAmt;
        @Override
        public String toString() {
            return "DataBean{" +
                    "payFlag=" + payFlag +
                    ", channel='" + channel + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", prods=" + prods +
                    ", returnType=" + returnType +
                    ", allReturn=" + allReturn +
                    '}';
        }


        public String getTotalAmt() {
            return totalAmt;
        }

        public void setTotalAmt(String totalAmt) {
            this.totalAmt = totalAmt;
        }

        public int getAllReturn() {
            return allReturn;
        }

        public void setAllReturn(int allReturn) {
            this.allReturn = allReturn;
        }

        public boolean isPayFlag() {
            return payFlag;
        }

        public void setPayFlag(boolean payFlag) {
            this.payFlag = payFlag;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public List<ProdsBean> getProds() {
            return prods;
        }

        public void setProds(List<ProdsBean> prods) {
            this.prods = prods;
        }

        public List<String> getReturnType() {
            return returnType;
        }

        public void setReturnType(List<String> returnType) {
            this.returnType = returnType;
        }

        public static class ProdsBean {
            /**
             * businessId : 9179
             * businessType : 1
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
             * prodName : 老干妈大豆酱
             * spec : 450ml
             * details : [{"detailId":1453932,"showPrice":"194.500000/箱*3","price":"583.500000","units":[{"unitId":18457,"unitName":"箱"},{"unitId":18458,"unitName":"瓶"},{"unitId":18472,"unitName":"小瓶"}]}]
             */
            private int additionFlag;
            private int businessId;
            private int businessType;
            private String defaultPic;
            private String prodName;
            private String spec;
            private List<DetailsBean> details;

            @Override
            public String toString() {
                return "ProdsBean{" +
                        "businessId=" + businessId +
                        ", businessType=" + businessType +
                        ", defaultPic='" + defaultPic + '\'' +
                        ", prodName='" + prodName + '\'' +
                        ", spec='" + spec + '\'' +
                        ", details=" + details +
                        '}';
            }

            public int getAdditionFlag() {
                return additionFlag;
            }

            public void setAdditionFlag(int additionFlag) {
                this.additionFlag = additionFlag;
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

            public String getProdName() {
                return prodName;
            }

            public void setProdName(String prodName) {
                this.prodName = prodName;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public List<DetailsBean> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBean> details) {
                this.details = details;
            }

            public static class DetailsBean {
                /**
                 * detailId : 1453932
                 * showPrice : 194.500000/箱*3
                 * price : 583.500000
                 * units : [{"unitId":18457,"unitName":"箱"},{"unitId":18458,"unitName":"瓶"},{"unitId":18472,"unitName":"小瓶"}]
                 */

                private int detailId;
                private String showPrice;
                private Double price;
                private List<UnitsBean> units;
                private List<UnitssBean> unitss;
                private int num;
                private int itemNum;
                private int itemUnitId;
                private double itemPrice;
                private List<ItemDetailIdBean> itemDetailId;
                private int itemDetailIds;
                @Override
                public String toString() {
                    return "DetailsBean{" +
                            "detailId=" + detailId +
                            ", showPrice='" + showPrice + '\'' +
                            ", price='" + price + '\'' +
                            ", units=" + units +
                            '}';
                }

                public int getItemDetailIds() {
                    return itemDetailIds;
                }

                public void setItemDetailIds(int itemDetailIds) {
                    this.itemDetailIds = itemDetailIds;
                }

                public List<ItemDetailIdBean> getItemDetailId() {
                    return itemDetailId;
                }

                public void setItemDetailId(List<ItemDetailIdBean> itemDetailId) {
                    this.itemDetailId = itemDetailId;
                }

                public double getItemPrice() {
                    return itemPrice;
                }

                public void setItemPrice(double itemPrice) {
                    this.itemPrice = itemPrice;
                }

                public int getItemNum() {
                    return itemNum;
                }

                public void setItemNum(int itemNum) {
                    this.itemNum = itemNum;
                }

                public int getItemUnitId() {
                    return itemUnitId;
                }

                public void setItemUnitId(int itemUnitId) {
                    this.itemUnitId = itemUnitId;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public int getDetailId() {
                    return detailId;
                }

                public void setDetailId(int detailId) {
                    this.detailId = detailId;
                }

                public String getShowPrice() {
                    return showPrice;
                }

                public void setShowPrice(String showPrice) {
                    this.showPrice = showPrice;
                }

                public Double getPrice() {
                    return price;
                }

                public void setPrice(Double price) {
                    this.price = price;
                }

                public List<UnitsBean> getUnits() {
                    return units;
                }

                public void setUnits(List<UnitsBean> units) {
                    this.units = units;
                }

                public List<UnitssBean> getUnitss() {
                    return unitss;
                }

                public void setUnitss(List<UnitssBean> unitss) {
                    this.unitss = unitss;
                }

                public static class UnitsBean {
                    /**
                     * unitId : 18457
                     * unitName : 箱
                     */

                    private int unitId;
                    private String unitName;

                    @Override
                    public String toString() {
                        return "UnitsBean{" +
                                "unitId=" + unitId +
                                ", unitName='" + unitName + '\'' +
                                '}';
                    }

                    public int getUnitId() {
                        return unitId;
                    }

                    public void setUnitId(int unitId) {
                        this.unitId = unitId;
                    }

                    public String getUnitName() {
                        return unitName;
                    }

                    public void setUnitName(String unitName) {
                        this.unitName = unitName;
                    }
                }

                public static class ItemDetailIdBean {
                    /**
                     * unitId : 18457
                     * unitName : 箱
                     */

                    private int detailId;

                    @Override
                    public String toString() {
                        return "UnitsBean{" +
                                "detailId=" + detailId;
                    }

                    public int getDetailId() {
                        return detailId;
                    }

                    public void setDetailId(int detailId) {
                        this.detailId = detailId;
                    }
                }

                public static class UnitssBean {
                    /**
                     * unitId : 18457
                     * unitName : 箱
                     */

                    private int unitId;

                    public int getUnitId() {
                        return unitId;
                    }

                    public void setUnitId(int unitId) {
                        this.unitId = unitId;
                    }
                }
            }
        }
    }

}
