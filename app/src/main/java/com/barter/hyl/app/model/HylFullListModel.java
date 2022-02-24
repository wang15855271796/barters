package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/31
 */
public class HylFullListModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"fullId":441,"name":"多满赠","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"],"currentTime":0,"startTime":null,"endTime":null},{"fullId":442,"name":"多满赠","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"],"currentTime":0,"startTime":null,"endTime":null},{"fullId":460,"name":"b","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//e5c5132c6ab2409490e3c669258a2d64.png"],"currentTime":0,"startTime":null,"endTime":null},{"fullId":485,"name":"a","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"],"currentTime":0,"startTime":null,"endTime":null}]
     * success : true
     * error : false
     */

    private int code;
    private String message;
    private boolean success;
    private boolean error;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fullId : 441
         * name : 多满赠
         * pics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"]
         * currentTime : 0
         * startTime : null
         * endTime : null
         */

        private String fullId;
        private String name;
        private int currentTime;
        private Object startTime;
        private Object endTime;
        private List<String> pics;

        public String getFullId() {
            return fullId;
        }

        public void setFullId(String fullId) {
            this.fullId = fullId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(int currentTime) {
            this.currentTime = currentTime;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
