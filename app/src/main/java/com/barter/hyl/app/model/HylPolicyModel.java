package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/8/2
 */
public class HylPolicyModel {


    /**
     * code : 1
     * message : 成功
     * data : {"register":"http://staff.qoger.com/h5/enter.html","privacy":"https://shaokao.qoger.com/apph5/html/yszc.html"}
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
         * register : http://staff.qoger.com/h5/enter.html
         * privacy : https://shaokao.qoger.com/apph5/html/yszc.html
         */

        private String register;
        private String privacy;

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }
    }
}
