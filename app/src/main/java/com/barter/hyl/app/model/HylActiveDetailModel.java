package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/27
 */
public class HylActiveDetailModel {

    /**
     * code : 1
     * message : 成功
     * data : {"activeId":5050,"name":"老干妈大豆酱","spec":"规格：600ml","price":"20","oldPrice":"21","limitNum":"限购3袋","invent":"余量：10袋","inventFlag":0,"totalNum":10,"remainNum":10,"intrduction":"老干妈大豆酱","place":"","flag":1,"nowTime":1629277324639,"startTime":1629216000000,"endTime":1629475200000,"topPics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//4da9686ecb6c49e0aff0eeb6e7e7a9e1.jpg?x-oss-process=style/add_press"],"detailPics":[]}
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
         * activeId : 5050
         * name : 老干妈大豆酱
         * spec : 规格：600ml
         * price : 20
         * oldPrice : 21
         * limitNum : 限购3袋
         * invent : 余量：10袋
         * inventFlag : 0
         * totalNum : 10
         * remainNum : 10
         * intrduction : 老干妈大豆酱
         * place :
         * flag : 1
         * nowTime : 1629277324639
         * startTime : 1629216000000
         * endTime : 1629475200000
         * topPics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//4da9686ecb6c49e0aff0eeb6e7e7a9e1.jpg?x-oss-process=style/add_press"]
         * detailPics : []
         */
        private int cartNum;
        private int activeId;
        private String name;
        private String spec;
        private String price;
        private String oldPrice;
        private String limitNum;
        private String invent;
        private int inventFlag;
        private int totalNum;
        private int remainNum;
        private String intrduction;
        private String place;
        private int flag;
        private long nowTime;
        private long startTime;
        private long endTime;
        private List<String> topPics;
        private List<String> detailPics;
        private String activeStartTime;
        private int activeType;

        public int getActiveType() {
            return activeType;
        }

        public void setActiveType(int activeType) {
            this.activeType = activeType;
        }

        public String getActiveStartTime() {
            return activeStartTime;
        }

        public void setActiveStartTime(String activeStartTime) {
            this.activeStartTime = activeStartTime;
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

        public String getLimitNum() {
            return limitNum;
        }

        public void setLimitNum(String limitNum) {
            this.limitNum = limitNum;
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

        public String getIntrduction() {
            return intrduction;
        }

        public void setIntrduction(String intrduction) {
            this.intrduction = intrduction;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
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

        public List<String> getTopPics() {
            return topPics;
        }

        public void setTopPics(List<String> topPics) {
            this.topPics = topPics;
        }

        public List<String> getDetailPics() {
            return detailPics;
        }

        public void setDetailPics(List<String> detailPics) {
            this.detailPics = detailPics;
        }
    }
}
