package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/18
 */
public class HylCommonDetailModel {


    /**
     * code : 1
     * message : 成功
     * data : {"mainId":8664,"collectFlag":0,"topPics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png?x-oss-process=style/add_press","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//4da9686ecb6c49e0aff0eeb6e7e7a9e1.jpg?x-oss-process=style/add_press"],"minMaxPrice":"￥1.5-4040","productName":"老干妈大豆酱","saleNum":"销量 0","intrduction":"老干妈大豆酱","specs":[{"productId":9179,"spec":"450ml"},{"productId":9189,"spec":"600ml"}],"commentNum":1,"comment":{"id":470312,"headPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//7213dc6f4ad74a7697dce8f53613d2a7.png","phone":"120****0001","commentTime":"2021-08-16","level":"5","content":"代收仅覅大很舒服发举案说法爱发科朗聚脲是否能","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//fe96c4e4b8df4d4da46c6c80765d428d.jpg"],"replayFlag":1,"replay":"活法黄沙古渡是国行","replayName":"商家"},"detailPics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//bdb02df8a0a842628e37e716ddb29a69.jpg?x-oss-process=style/add_press"],"fullActiveFlag":1,"fullRoles":[{"buyLogo":"","buySpec":"买 450ml 2瓶","sendLogo":"","sendProd":"赠 优惠券：货易链优惠券 1张","role":"仅可参与一次"}]}
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
         * mainId : 8664
         * collectFlag : 0
         * topPics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png?x-oss-process=style/add_press","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//4da9686ecb6c49e0aff0eeb6e7e7a9e1.jpg?x-oss-process=style/add_press"]
         * minMaxPrice : ￥1.5-4040
         * productName : 老干妈大豆酱
         * saleNum : 销量 0
         * intrduction : 老干妈大豆酱
         * specs : [{"productId":9179,"spec":"450ml"},{"productId":9189,"spec":"600ml"}]
         * commentNum : 1
         * comment : {"id":470312,"headPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//7213dc6f4ad74a7697dce8f53613d2a7.png","phone":"120****0001","commentTime":"2021-08-16","level":"5","content":"代收仅覅大很舒服发举案说法爱发科朗聚脲是否能","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//fe96c4e4b8df4d4da46c6c80765d428d.jpg"],"replayFlag":1,"replay":"活法黄沙古渡是国行","replayName":"商家"}
         * detailPics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//bdb02df8a0a842628e37e716ddb29a69.jpg?x-oss-process=style/add_press"]
         * fullActiveFlag : 1
         * fullRoles : [{"buyLogo":"","buySpec":"买 450ml 2瓶","sendLogo":"","sendProd":"赠 优惠券：货易链优惠券 1张","role":"仅可参与一次"}]
         */

        private String mainId;
        private int collectFlag;
        private String minMaxPrice;
        private String productName;
        private String saleNum;
        private String intrduction;
        private int commentNum;
        private CommentBean comment;
        private int fullActiveFlag;
        private List<String> topPics;
        private List<SpecsBean> specs;
        private List<String> detailPics;
        private List<FullRolesBean> fullRoles;
        private String fullId;
        private String fullName;
        private List<String> quarantines;
        private String videoUrl;
        List<ActivesBean> actives;

        public List<ActivesBean> getActives() {
            return actives;
        }

        public void setActives(List<ActivesBean> actives) {
            this.actives = actives;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public List<String> getQuarantines() {
            return quarantines;
        }

        public void setQuarantines(List<String> quarantines) {
            this.quarantines = quarantines;
        }

        public String getFullId() {
            return fullId;
        }

        public void setFullId(String fullId) {
            this.fullId = fullId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMainId() {
            return mainId;
        }

        public void setMainId(String mainId) {
            this.mainId = mainId;
        }

        public int getCollectFlag() {
            return collectFlag;
        }

        public void setCollectFlag(int collectFlag) {
            this.collectFlag = collectFlag;
        }

        public String getMinMaxPrice() {
            return minMaxPrice;
        }

        public void setMinMaxPrice(String minMaxPrice) {
            this.minMaxPrice = minMaxPrice;
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

        public String getIntrduction() {
            return intrduction;
        }

        public void setIntrduction(String intrduction) {
            this.intrduction = intrduction;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public int getFullActiveFlag() {
            return fullActiveFlag;
        }

        public void setFullActiveFlag(int fullActiveFlag) {
            this.fullActiveFlag = fullActiveFlag;
        }

        public List<String> getTopPics() {
            return topPics;
        }

        public void setTopPics(List<String> topPics) {
            this.topPics = topPics;
        }

        public List<SpecsBean> getSpecs() {
            return specs;
        }

        public void setSpecs(List<SpecsBean> specs) {
            this.specs = specs;
        }

        public List<String> getDetailPics() {
            return detailPics;
        }

        public void setDetailPics(List<String> detailPics) {
            this.detailPics = detailPics;
        }

        public List<FullRolesBean> getFullRoles() {
            return fullRoles;
        }

        public void setFullRoles(List<FullRolesBean> fullRoles) {
            this.fullRoles = fullRoles;
        }

        public static class ActivesBean {
            /**
             * productId : 9179
             * spec : 450ml
             */
            private int activeType;
            private String activeId;
            private String activeName;

            public int getActiveType() {
                return activeType;
            }

            public void setActiveType(int activeType) {
                this.activeType = activeType;
            }

            public String getActiveId() {
                return activeId;
            }

            public void setActiveId(String activeId) {
                this.activeId = activeId;
            }

            public String getActiveName() {
                return activeName;
            }

            public void setActiveName(String activeName) {
                this.activeName = activeName;
            }
        }

        public static class CommentBean {
            /**
             * id : 470312
             * headPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//7213dc6f4ad74a7697dce8f53613d2a7.png
             * phone : 120****0001
             * commentTime : 2021-08-16
             * level : 5
             * content : 代收仅覅大很舒服发举案说法爱发科朗聚脲是否能
             * pics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//fe96c4e4b8df4d4da46c6c80765d428d.jpg"]
             * replayFlag : 1
             * replay : 活法黄沙古渡是国行
             * replayName : 商家
             */

            private int id;
            private String headPic;
            private String phone;
            private String commentTime;
            private String level;
            private String content;
            private int replayFlag;
            private String replay;
            private String replayName;
            private List<String> pics;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(String commentTime) {
                this.commentTime = commentTime;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getReplayFlag() {
                return replayFlag;
            }

            public void setReplayFlag(int replayFlag) {
                this.replayFlag = replayFlag;
            }

            public String getReplay() {
                return replay;
            }

            public void setReplay(String replay) {
                this.replay = replay;
            }

            public String getReplayName() {
                return replayName;
            }

            public void setReplayName(String replayName) {
                this.replayName = replayName;
            }

            public List<String> getPics() {
                return pics;
            }

            public void setPics(List<String> pics) {
                this.pics = pics;
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

        public static class FullRolesBean {
            /**
             * buyLogo :
             * buySpec : 买 450ml 2瓶
             * sendLogo :
             * sendProd : 赠 优惠券：货易链优惠券 1张
             * role : 仅可参与一次
             */

            private String buyLogo;
            private String buySpec;
            private String sendLogo;
            private String sendProd;
            private String role;

            public String getBuyLogo() {
                return buyLogo;
            }

            public void setBuyLogo(String buyLogo) {
                this.buyLogo = buyLogo;
            }

            public String getBuySpec() {
                return buySpec;
            }

            public void setBuySpec(String buySpec) {
                this.buySpec = buySpec;
            }

            public String getSendLogo() {
                return sendLogo;
            }

            public void setSendLogo(String sendLogo) {
                this.sendLogo = sendLogo;
            }

            public String getSendProd() {
                return sendProd;
            }

            public void setSendProd(String sendProd) {
                this.sendProd = sendProd;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }
        }
    }
}
