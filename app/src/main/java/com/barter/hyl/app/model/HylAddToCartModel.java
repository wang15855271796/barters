package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/8/20
 */
public class HylAddToCartModel {

    /**
     * code : 1
     * message : 成功
     * data : {"addFlag":0,"num":1,"message":null}
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
         * addFlag : 0
         * num : 1
         * message : null
         */

        private int addFlag;
        private int num;
        private String message;

        public int getAddFlag() {
            return addFlag;
        }

        public void setAddFlag(int addFlag) {
            this.addFlag = addFlag;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
