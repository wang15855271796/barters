package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/20
 */
public class HylAddCartModel {

    /**
     * code : 1
     * message : 成功
     * data : {"specs":[{"productId":9179,"spec":"450ml"},{"productId":9189,"spec":"600ml"}],"specDetail":{"productId":9179,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","saleNum":"销量：0","minMaxPrice":"￥1.5-4040","invent":"库存：99箱18瓶","inventFlag":0,"prices":[{"priceId":95895,"price":"￥1.5/小瓶","oldPrice":"","deductFlag":0},{"priceId":95866,"price":"￥90/瓶","oldPrice":"","deductFlag":0},{"priceId":95865,"price":"￥194.5/箱","oldPrice":"","deductFlag":0},{"priceId":95905,"price":"￥4040/2箱","oldPrice":"","deductFlag":0}]}}
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
         * specs : [{"productId":9179,"spec":"450ml"},{"productId":9189,"spec":"600ml"}]
         * specDetail : {"productId":9179,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","saleNum":"销量：0","minMaxPrice":"￥1.5-4040","invent":"库存：99箱18瓶","inventFlag":0,"prices":[{"priceId":95895,"price":"￥1.5/小瓶","oldPrice":"","deductFlag":0},{"priceId":95866,"price":"￥90/瓶","oldPrice":"","deductFlag":0},{"priceId":95865,"price":"￥194.5/箱","oldPrice":"","deductFlag":0},{"priceId":95905,"price":"￥4040/2箱","oldPrice":"","deductFlag":0}]}
         */

        private SpecDetailBean specDetail;
        private List<SpecsBean> specs;

        public SpecDetailBean getSpecDetail() {
            return specDetail;
        }

        public void setSpecDetail(SpecDetailBean specDetail) {
            this.specDetail = specDetail;
        }

        public List<SpecsBean> getSpecs() {
            return specs;
        }

        public void setSpecs(List<SpecsBean> specs) {
            this.specs = specs;
        }

        public static class SpecDetailBean {
            /**
             * productId : 9179
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
             * productName : 老干妈大豆酱
             * saleNum : 销量：0
             * minMaxPrice : ￥1.5-4040
             * invent : 库存：99箱18瓶
             * inventFlag : 0
             * prices : [{"priceId":95895,"price":"￥1.5/小瓶","oldPrice":"","deductFlag":0},{"priceId":95866,"price":"￥90/瓶","oldPrice":"","deductFlag":0},{"priceId":95865,"price":"￥194.5/箱","oldPrice":"","deductFlag":0},{"priceId":95905,"price":"￥4040/2箱","oldPrice":"","deductFlag":0}]
             */

            private int productId;
            private String defaultPic;
            private String productName;
            private String saleNum;
            private String minMaxPrice;
            private String invent;
            private int inventFlag;
            private List<PricesBean> prices;

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
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

            public String getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(String saleNum) {
                this.saleNum = saleNum;
            }

            public String getMinMaxPrice() {
                return minMaxPrice;
            }

            public void setMinMaxPrice(String minMaxPrice) {
                this.minMaxPrice = minMaxPrice;
            }

            public String getInvent() {
                return invent;
            }

            public void setInvent(String invent) {
                this.invent = invent;
            }

            public int getInventFlag() {
                return inventFlag;
            }

            public void setInventFlag(int inventFlag) {
                this.inventFlag = inventFlag;
            }

            public List<PricesBean> getPrices() {
                return prices;
            }

            public void setPrices(List<PricesBean> prices) {
                this.prices = prices;
            }

            public static class PricesBean {
                /**
                 * priceId : 95895
                 * price : ￥1.5/小瓶
                 * oldPrice :
                 * deductFlag : 0
                 */

                private int priceId;
                private String price;
                private String oldPrice;
                private int deductFlag;
                private int cartNum;

                public int getCartNum() {
                    return cartNum;
                }

                public void setCartNum(int cartNum) {
                    this.cartNum = cartNum;
                }

                public int getPriceId() {
                    return priceId;
                }

                public void setPriceId(int priceId) {
                    this.priceId = priceId;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getOldPrice() {
                    return oldPrice;
                }

                public void setOldPrice(String oldPrice) {
                    this.oldPrice = oldPrice;
                }

                public int getDeductFlag() {
                    return deductFlag;
                }

                public void setDeductFlag(int deductFlag) {
                    this.deductFlag = deductFlag;
                }
            }
        }

        public static class SpecsBean {
            /**
             * productId : 9179
             * spec : 450ml
             */

            private int productId;
            private String spec;

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }
        }
    }
}
