package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/25
 */
public class HylGoodCateModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"firstId":1242,"name":"老干妈一级分类","pic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/7c8df83756424e9c8f02e51737b141c3.jpg","seconds":[{"secondId":0,"name":"全部商品"},{"secondId":-1,"name":"热卖"},{"secondId":1243,"name":"老干妈二级分类"}]}]
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
         * firstId : 1242
         * name : 老干妈一级分类
         * pic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/7c8df83756424e9c8f02e51737b141c3.jpg
         * seconds : [{"secondId":0,"name":"全部商品"},{"secondId":-1,"name":"热卖"},{"secondId":1243,"name":"老干妈二级分类"}]
         */

        private int firstId;
        private String name;
        private String pic;
        private List<SecondsBean> seconds;

        public int getFirstId() {
            return firstId;
        }

        public void setFirstId(int firstId) {
            this.firstId = firstId;
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

        public List<SecondsBean> getSeconds() {
            return seconds;
        }

        public void setSeconds(List<SecondsBean> seconds) {
            this.seconds = seconds;
        }

        public static class SecondsBean {
            /**
             * secondId : 0
             * name : 全部商品
             */

            private int secondId;
            private String name;

            public int getSecondId() {
                return secondId;
            }

            public void setSecondId(int secondId) {
                this.secondId = secondId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
