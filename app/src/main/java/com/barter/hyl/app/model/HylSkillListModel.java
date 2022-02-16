package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/31
 */
public class HylSkillListModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"title":"距开始","nowTime":1629097815574,"startTime":1629216000000,"endTime":1629475200000,"flag":0,"actives":[{"activeId":5050,"name":"老干妈大豆酱","spec":"600ml","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"￥20.00","oldPrice":"21.00","inventFlag":0,"totalNum":10,"remainNum":10,"unitName":"袋"}]}]
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

    public static class DataBean {
        /**
         * title : 距开始
         * nowTime : 1629097815574
         * startTime : 1629216000000
         * endTime : 1629475200000
         * flag : 0
         * actives : [{"activeId":5050,"name":"老干妈大豆酱","spec":"600ml","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"￥20.00","oldPrice":"21.00","inventFlag":0,"totalNum":10,"remainNum":10,"unitName":"袋"}]
         */

        private String title;
        private long nowTime;
        private long startTime;
        private long endTime;
        private int flag;
        private int activeId;
        private List<ActivesBean> actives;

        public int getActiveId() {
            return activeId;
        }

        public void setActiveId(int activeId) {
            this.activeId = activeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getNowTime() {
            return nowTime;
        }

        public void setNowTime(long nowTime) {
            this.nowTime = nowTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public List<ActivesBean> getActives() {
            return actives;
        }

        public void setActives(List<ActivesBean> actives) {
            this.actives = actives;
        }

        public static class ActivesBean {
            /**
             * activeId : 5050
             * name : 老干妈大豆酱
             * spec : 600ml
             * pic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
             * price : ￥20.00
             * oldPrice : 21.00
             * inventFlag : 0
             * totalNum : 10
             * remainNum : 10
             * unitName : 袋
             */

            private int activeId;
            private String name;
            private String spec;
            private String pic;
            private String price;
            private String oldPrice;
            private int inventFlag;
            private int totalNum;
            private int remainNum;
            private String unitName;
            private int cartNum;
            private int flag;

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getCartNum() {
                return cartNum;
            }

            public void setCartNum(int cartNum) {
                this.cartNum = cartNum;
            }

            public int getActiveId() {
                return activeId;
            }

            public void setActiveId(int activeId) {
                this.activeId = activeId;
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

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
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

            public int getInventFlag() {
                return inventFlag;
            }

            public void setInventFlag(int inventFlag) {
                this.inventFlag = inventFlag;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public int getRemainNum() {
                return remainNum;
            }

            public void setRemainNum(int remainNum) {
                this.remainNum = remainNum;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }
        }
    }
}
