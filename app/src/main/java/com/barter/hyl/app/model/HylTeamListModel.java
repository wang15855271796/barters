package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class HylTeamListModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"title":"进行中","flag":0,"actives":[{"dateTime":null,"actives":[{"activeId":5047,"activeType":3,"name":"超值组合","spec":"老干妈","price":"￥20.00","oldPrice":"39.90","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","inventFlag":0,"cartNum":0}]}]}]
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

    @Override
    public String toString() {
        return "HylTeamListModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", error=" + error +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", flag=" + flag +
                    ", actives=" + actives +
                    '}';
        }

        /**
         * title : 进行中
         * flag : 0
         * actives : [{"dateTime":null,"actives":[{"activeId":5047,"activeType":3,"name":"超值组合","spec":"老干妈","price":"￥20.00","oldPrice":"39.90","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","inventFlag":0,"cartNum":0}]}]
         */

        private String title;
        private int flag;
        private List<ActivesBeanX> actives;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public List<ActivesBeanX> getActives() {
            return actives;
        }

        public void setActives(List<ActivesBeanX> actives) {
            this.actives = actives;
        }

        public static class ActivesBeanX {
            @Override
            public String toString() {
                return "ActivesBeanX{" +
                        "dateTime='" + dateTime + '\'' +
                        ", actives=" + actives +
                        '}';
            }

            /**
             * dateTime : null
             * actives : [{"activeId":5047,"activeType":3,"name":"超值组合","spec":"老干妈","price":"￥20.00","oldPrice":"39.90","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","inventFlag":0,"cartNum":0}]
             */

            private String dateTime;
            private List<ActivesBean> actives;

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public List<ActivesBean> getActives() {
                return actives;
            }

            public void setActives(List<ActivesBean> actives) {
                this.actives = actives;
            }

            public static class ActivesBean {
                @Override
                public String toString() {
                    return "ActivesBean{" +
                            "activeId=" + activeId +
                            ", activeType=" + activeType +
                            ", name='" + name + '\'' +
                            ", spec='" + spec + '\'' +
                            ", price='" + price + '\'' +
                            ", oldPrice='" + oldPrice + '\'' +
                            ", pic='" + pic + '\'' +
                            ", inventFlag=" + inventFlag +
                            ", cartNum=" + cartNum +
                            '}';
                }

                /**
                 * activeId : 5047
                 * activeType : 3
                 * name : 超值组合
                 * spec : 老干妈
                 * price : ￥20.00
                 * oldPrice : 39.90
                 * pic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
                 * inventFlag : 0
                 * cartNum : 0
                 */

                private int activeId;
                private int activeType;
                private String name;
                private String spec;
                private String price;
                private String oldPrice;
                private String pic;
                private int inventFlag;
                private int cartNum;
                int flag;

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                public int getActiveId() {
                    return activeId;
                }

                public void setActiveId(int activeId) {
                    this.activeId = activeId;
                }

                public int getActiveType() {
                    return activeType;
                }

                public void setActiveType(int activeType) {
                    this.activeType = activeType;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
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

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public int getInventFlag() {
                    return inventFlag;
                }

                public void setInventFlag(int inventFlag) {
                    this.inventFlag = inventFlag;
                }

                public int getCartNum() {
                    return cartNum;
                }

                public void setCartNum(int cartNum) {
                    this.cartNum = cartNum;
                }
            }
        }
    }
}
