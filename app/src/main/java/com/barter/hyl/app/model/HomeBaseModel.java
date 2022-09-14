package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/13
 */
public class HomeBaseModel {


    /**
     * code : 1
     * message : 成功
     * data : {"companyName":"老干妈","noticeNum":"2","banners":[{"banner":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//863f8db1bda745c8ab6d9a57f22daf42.jpg","type":0,"detailPic":null,"businessId":null,"businessType":null}],"noticeList":[{"id":141,"title":"的顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶","dateTime":"2021-08-19","readFlag":0},{"id":140,"title":"的顶顶顶顶顶的","dateTime":"2021-08-19","readFlag":0}],"classifies":[{"id":1242,"name":"老干妈一级分类","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/7c8df83756424e9c8f02e51737b141c3.jpg"}],"spike":{"title":"秒杀标语","smallTitle":null,"pic":null,"businessType":2,"flag":1,"nowTime":1629363205020,"startTime":1629216000000,"endTime":1629475200000,"activeList":[{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"20.00","activeId":5050,"inventFlag":0}]},"team":{"title":"组合标语","smallTitle":"组合小标语","pic":"组合小图标","businessType":3,"flag":0,"nowTime":0,"startTime":0,"endTime":0,"activeList":[{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","price":"20.00","activeId":5047,"inventFlag":0}]},"fullActive":{"title":"满赠标语","smallTitle":"满赠小标语","pic":"满赠小图标","businessType":12,"flag":0,"nowTime":0,"startTime":0,"endTime":0,"activeList":[{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","price":"￥1.5-4040","activeId":8664,"inventFlag":0}]},"popUpsFlag":1,"popUps":{"id":0,"type":1,"url":"https://shaokao.qoger.com/apph5/html/yszc.html","jumpType":"disable","detailUrl":null,"gifts":null,"title":""}}
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
         * companyName : 老干妈
         * noticeNum : 2
         * banners : [{"banner":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//863f8db1bda745c8ab6d9a57f22daf42.jpg","type":0,"detailPic":null,"businessId":null,"businessType":null}]
         * noticeList : [{"id":141,"title":"的顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶","dateTime":"2021-08-19","readFlag":0},{"id":140,"title":"的顶顶顶顶顶的","dateTime":"2021-08-19","readFlag":0}]
         * classifies : [{"id":1242,"name":"老干妈一级分类","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/7c8df83756424e9c8f02e51737b141c3.jpg"}]
         * spike : {"title":"秒杀标语","smallTitle":null,"pic":null,"businessType":2,"flag":1,"nowTime":1629363205020,"startTime":1629216000000,"endTime":1629475200000,"activeList":[{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"20.00","activeId":5050,"inventFlag":0}]}
         * team : {"title":"组合标语","smallTitle":"组合小标语","pic":"组合小图标","businessType":3,"flag":0,"nowTime":0,"startTime":0,"endTime":0,"activeList":[{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","price":"20.00","activeId":5047,"inventFlag":0}]}
         * fullActive : {"title":"满赠标语","smallTitle":"满赠小标语","pic":"满赠小图标","businessType":12,"flag":0,"nowTime":0,"startTime":0,"endTime":0,"activeList":[{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","price":"￥1.5-4040","activeId":8664,"inventFlag":0}]}
         * popUpsFlag : 1
         * popUps : {"id":0,"type":1,"url":"https://shaokao.qoger.com/apph5/html/yszc.html","jumpType":"disable","detailUrl":null,"gifts":null,"title":""}
         */

        private String companyName;
        private String noticeNum;
        private SpikeBean spike;
        private TeamBean team;
        private FullActiveBean fullActive;
        private int popUpsFlag;
        private PopUpsBean popUps;
        private List<BannersBean> banners;
        private List<NoticeListBean> noticeList;
        private List<ClassifiesBean> classifies;
        private int showCompanyFLag;
        private String companyPhone;
        private String companyAddress;
        private String companyDesc;
        private int companyEnabled;

        public int getCompanyEnabled() {
            return companyEnabled;
        }

        public void setCompanyEnabled(int companyEnabled) {
            this.companyEnabled = companyEnabled;
        }

        public String getCompanyDesc() {
            return companyDesc;
        }

        public void setCompanyDesc(String companyDesc) {
            this.companyDesc = companyDesc;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public String getCompanyPhone() {
            return companyPhone;
        }

        public void setCompanyPhone(String companyPhone) {
            this.companyPhone = companyPhone;
        }

        public int getShowCompanyFLag() {
            return showCompanyFLag;
        }

        public void setShowCompanyFLag(int showCompanyFLag) {
            this.showCompanyFLag = showCompanyFLag;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getNoticeNum() {
            return noticeNum;
        }

        public void setNoticeNum(String noticeNum) {
            this.noticeNum = noticeNum;
        }

        public SpikeBean getSpike() {
            return spike;
        }

        public void setSpike(SpikeBean spike) {
            this.spike = spike;
        }

        public TeamBean getTeam() {
            return team;
        }

        public void setTeam(TeamBean team) {
            this.team = team;
        }

        public FullActiveBean getFullActive() {
            return fullActive;
        }

        public void setFullActive(FullActiveBean fullActive) {
            this.fullActive = fullActive;
        }

        public int getPopUpsFlag() {
            return popUpsFlag;
        }

        public void setPopUpsFlag(int popUpsFlag) {
            this.popUpsFlag = popUpsFlag;
        }

        public PopUpsBean getPopUps() {
            return popUps;
        }

        public void setPopUps(PopUpsBean popUps) {
            this.popUps = popUps;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public List<NoticeListBean> getNoticeList() {
            return noticeList;
        }

        public void setNoticeList(List<NoticeListBean> noticeList) {
            this.noticeList = noticeList;
        }

        public List<ClassifiesBean> getClassifies() {
            return classifies;
        }

        public void setClassifies(List<ClassifiesBean> classifies) {
            this.classifies = classifies;
        }

        public static class SpikeBean {
            /**
             * title : 秒杀标语
             * smallTitle : null
             * pic : null
             * businessType : 2
             * flag : 1
             * nowTime : 1629363205020
             * startTime : 1629216000000
             * endTime : 1629475200000
             * activeList : [{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"20.00","activeId":5050,"inventFlag":0}]
             */

            private String title;
            private String smallTitle;
            private String pic;
            private int businessType;
            private int flag;
            private long nowTime;
            private long startTime;
            private long endTime;
            private List<ActiveListBean> activeList;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSmallTitle() {
                return smallTitle;
            }

            public void setSmallTitle(String smallTitle) {
                this.smallTitle = smallTitle;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
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

            public List<ActiveListBean> getActiveList() {
                return activeList;
            }

            public void setActiveList(List<ActiveListBean> activeList) {
                this.activeList = activeList;
            }

            public static class ActiveListBean {
                /**
                 * pic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
                 * price : 20.00
                 * activeId : 5050
                 * inventFlag : 0
                 */

                private String pic;
                private String price;
                private int activeId;
                private int inventFlag;

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

                public int getActiveId() {
                    return activeId;
                }

                public void setActiveId(int activeId) {
                    this.activeId = activeId;
                }

                public int getInventFlag() {
                    return inventFlag;
                }

                public void setInventFlag(int inventFlag) {
                    this.inventFlag = inventFlag;
                }
            }
        }

        public static class TeamBean {
            /**
             * title : 组合标语
             * smallTitle : 组合小标语
             * pic : 组合小图标
             * businessType : 3
             * flag : 0
             * nowTime : 0
             * startTime : 0
             * endTime : 0
             * activeList : [{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","price":"20.00","activeId":5047,"inventFlag":0}]
             */

            private String title;
            private String smallTitle;
            private String pic;
            private int businessType;
            private int flag;
            private int nowTime;
            private int startTime;
            private int endTime;
            private List<ActiveListBeanX> activeList;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSmallTitle() {
                return smallTitle;
            }

            public void setSmallTitle(String smallTitle) {
                this.smallTitle = smallTitle;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getNowTime() {
                return nowTime;
            }

            public void setNowTime(int nowTime) {
                this.nowTime = nowTime;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public List<ActiveListBeanX> getActiveList() {
                return activeList;
            }

            public void setActiveList(List<ActiveListBeanX> activeList) {
                this.activeList = activeList;
            }

            public static class ActiveListBeanX {
                /**
                 * pic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
                 * price : 20.00
                 * activeId : 5047
                 * inventFlag : 0
                 */

                private String pic;
                private String price;
                private int activeId;
                private int inventFlag;

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

                public int getActiveId() {
                    return activeId;
                }

                public void setActiveId(int activeId) {
                    this.activeId = activeId;
                }

                public int getInventFlag() {
                    return inventFlag;
                }

                public void setInventFlag(int inventFlag) {
                    this.inventFlag = inventFlag;
                }
            }
        }

        public static class FullActiveBean {
            /**
             * title : 满赠标语
             * smallTitle : 满赠小标语
             * pic : 满赠小图标
             * businessType : 12
             * flag : 0
             * nowTime : 0
             * startTime : 0
             * endTime : 0
             * activeList : [{"pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","price":"￥1.5-4040","activeId":8664,"inventFlag":0}]
             */

            private String title;
            private String smallTitle;
            private String pic;
            private int businessType;
            private int flag;
            private int nowTime;
            private int startTime;
            private int endTime;
            private List<ActiveListBeanXX> activeList;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSmallTitle() {
                return smallTitle;
            }

            public void setSmallTitle(String smallTitle) {
                this.smallTitle = smallTitle;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getNowTime() {
                return nowTime;
            }

            public void setNowTime(int nowTime) {
                this.nowTime = nowTime;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public List<ActiveListBeanXX> getActiveList() {
                return activeList;
            }

            public void setActiveList(List<ActiveListBeanXX> activeList) {
                this.activeList = activeList;
            }

            public static class ActiveListBeanXX {
                /**
                 * pic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
                 * price : ￥1.5-4040
                 * activeId : 8664
                 * inventFlag : 0
                 */

                private String pic;
                private String price;
                private int activeId;
                private int inventFlag;

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

                public int getActiveId() {
                    return activeId;
                }

                public void setActiveId(int activeId) {
                    this.activeId = activeId;
                }

                public int getInventFlag() {
                    return inventFlag;
                }

                public void setInventFlag(int inventFlag) {
                    this.inventFlag = inventFlag;
                }
            }
        }

        public static class PopUpsBean {
            /**
             * id : 0
             * type : 1
             * url : https://shaokao.qoger.com/apph5/html/yszc.html
             * jumpType : disable
             * detailUrl : null
             * gifts : null
             * title :
             */

            private int id;
            private int type;
            private String url;
            private String jumpType;
            private String detailUrl;
            private List<GiftsBean> gifts;
            private String title;

            public List<GiftsBean> getGifts() {
                return gifts;
            }

            public void setGifts(List<GiftsBean> gifts) {
                this.gifts = gifts;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getJumpType() {
                return jumpType;
            }

            public void setJumpType(String jumpType) {
                this.jumpType = jumpType;
            }

            public String getDetailUrl() {
                return detailUrl;
            }

            public void setDetailUrl(String detailUrl) {
                this.detailUrl = detailUrl;
            }


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public static class GiftsBean {
                /**
                  * giftName : 6元无门槛消费券
                  * giftType : null
                  * amount : null
                  * amountStr : 6
                  * limitAmtStr : 满0元可用
                  * limitAmt : null
                  * applyFrom : null
                  * dateTime : 有效期至2020-10-15
                  * role : ["全场通用"]
                  * overTimePic : null
                  * usedPic : null
                  * unAblePic : null
                  * state : null
                  * giftDetailNo : null
                  * orderId : null
              */
                private String giftName;
                private Object giftType;
                private Object amount;
                private String amountStr;
                private String limitAmtStr;
                private Object limitAmt;
                private Object applyFrom;
                private String dateTime;
                private Object overTimePic;
                private Object usedPic;
                private Object unAblePic;
                private Object state;
                private Object giftDetailNo;
                private String orderId;
                private List<String> role;

                public String getGiftName() {
                    return giftName;
                }

                public void setGiftName(String giftName) {
                    this.giftName = giftName;
                }

                public Object getGiftType() {
                    return giftType;
                }

                public void setGiftType(Object giftType) {
                    this.giftType = giftType;
                }

                public Object getAmount() {
                    return amount;
                }

                public void setAmount(Object amount) {
                    this.amount = amount;
                }

                public String getAmountStr() {
                    return amountStr;
                }

                public void setAmountStr(String amountStr) {
                    this.amountStr = amountStr;
                }

                public String getLimitAmtStr() {
                    return limitAmtStr;
                }

                public void setLimitAmtStr(String limitAmtStr) {
                    this.limitAmtStr = limitAmtStr;
                }

                public Object getLimitAmt() {
                    return limitAmt;
                }

                public void setLimitAmt(Object limitAmt) {
                    this.limitAmt = limitAmt;
                }

                public Object getApplyFrom() {
                    return applyFrom;
                }

                public void setApplyFrom(Object applyFrom) {
                    this.applyFrom = applyFrom;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public Object getOverTimePic() {
                    return overTimePic;
                }

                public void setOverTimePic(Object overTimePic) {
                    this.overTimePic = overTimePic;
                }

                public Object getUsedPic() {
                    return usedPic;
                }

                public void setUsedPic(Object usedPic) {
                    this.usedPic = usedPic;
                }

                public Object getUnAblePic() {
                    return unAblePic;
                }

                public void setUnAblePic(Object unAblePic) {
                    this.unAblePic = unAblePic;
                }

                public Object getState() {
                    return state;
                }

                public void setState(Object state) {
                    this.state = state;
                }

                public Object getGiftDetailNo() {
                    return giftDetailNo;
                }

                public void setGiftDetailNo(Object giftDetailNo) {
                    this.giftDetailNo = giftDetailNo;
                }

                public String getOrderId() {
                    return orderId;
                }

                public void setOrderId(String orderId) {
                    this.orderId = orderId;
                }

                public List<String> getRole() {
                    return role;
                }

                public void setRole(List<String> role) {
                    this.role = role;
                }
            }
        }

        public static class BannersBean {
            /**
             * banner : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//863f8db1bda745c8ab6d9a57f22daf42.jpg
             * type : 0
             * detailPic : null
             * businessId : null
             * businessType : null
             */

            private String banner;
            private int type;
            private String detailPic;
            private String businessId;
            private int businessType;

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDetailPic() {
                return detailPic;
            }

            public void setDetailPic(String detailPic) {
                this.detailPic = detailPic;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }
        }

        public static class NoticeListBean {
            /**
             * id : 141
             * title : 的顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶
             * dateTime : 2021-08-19
             * readFlag : 0
             */

            private int id;
            private String title;
            private String dateTime;
            private int readFlag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public int getReadFlag() {
                return readFlag;
            }

            public void setReadFlag(int readFlag) {
                this.readFlag = readFlag;
            }
        }

        public static class ClassifiesBean {
            /**
             * id : 1242
             * name : 老干妈一级分类
             * pic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/7c8df83756424e9c8f02e51737b141c3.jpg
             */

            private int id;
            private String name;
            private String pic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }

    }
}
