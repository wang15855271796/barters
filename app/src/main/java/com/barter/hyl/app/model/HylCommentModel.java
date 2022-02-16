package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class HylCommentModel {

    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":10,"startRow":0,"pages":1,"total":1,"list":[{"id":470312,"headPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//7213dc6f4ad74a7697dce8f53613d2a7.png","phone":"120****0001","commentTime":"2021-08-16","level":"5","content":"代收仅覅大很舒服发举案说法爱发科朗聚脲是否能","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//fe96c4e4b8df4d4da46c6c80765d428d.jpg"],"replayFlag":1,"replay":"活法黄沙古渡是国行","replayName":"商家"}],"hasPrePage":false,"hasNextPage":false}
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
         * pageNum : 1
         * pageSize : 10
         * startRow : 0
         * pages : 1
         * total : 1
         * list : [{"id":470312,"headPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//7213dc6f4ad74a7697dce8f53613d2a7.png","phone":"120****0001","commentTime":"2021-08-16","level":"5","content":"代收仅覅大很舒服发举案说法爱发科朗聚脲是否能","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//fe96c4e4b8df4d4da46c6c80765d428d.jpg"],"replayFlag":1,"replay":"活法黄沙古渡是国行","replayName":"商家"}]
         * hasPrePage : false
         * hasNextPage : false
         */

        private int pageNum;
        private int pageSize;
        private int startRow;
        private int pages;
        private int total;
        private boolean hasPrePage;
        private boolean hasNextPage;
        private List<ListBean> list;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isHasPrePage() {
            return hasPrePage;
        }

        public void setHasPrePage(boolean hasPrePage) {
            this.hasPrePage = hasPrePage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
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
    }
}
