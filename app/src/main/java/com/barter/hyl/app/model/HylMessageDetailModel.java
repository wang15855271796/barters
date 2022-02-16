package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/8/27
 */
public class HylMessageDetailModel {


    /**
     * code : 1
     * message : 成功
     * data : {"title":"的顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶","dateTime":"2021-08-19 15:02:25","content":"顶顶顶顶顶顶顶顶顶顶顶顶顶顶"}
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
         * title : 的顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶
         * dateTime : 2021-08-19 15:02:25
         * content : 顶顶顶顶顶顶顶顶顶顶顶顶顶顶
         */

        private String title;
        private String dateTime;
        private String content;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
