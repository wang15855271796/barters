package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/24
 */
public class HylSearchBillModel {

    /**
     * code : 1
     * message : 成功
     * data : {"list1":[{"key":"","value":"全部"},{"key":"10","value":"一般订单"},{"key":"11","value":"信用订单"},{"key":"12","value":"订单售后"}],"list2":[{"key":"","value":"全部"},{"key":"14","value":"支付宝交易明细"},{"key":"3","value":"微信交易明细"}],"list3":[{"yearStr":"2019年","year":2019,"months":[{"monthStr":"8月","month":8}]},{"yearStr":"2020年","year":2020,"months":[{"monthStr":"6月","month":6},{"monthStr":"8月","month":8}]},{"yearStr":"2021年","year":2021,"months":[{"monthStr":"6月","month":6},{"monthStr":"7月","month":7},{"monthStr":"8月","month":8}]}]}
     * success : true
     * error : false
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean success;
    private boolean error;

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

    public static class DataBean {
        private List<List1Bean> list1;
        private List<List2Bean> list2;
        private List<List3Bean> list3;

        public List<List1Bean> getList1() {
            return list1;
        }

        public void setList1(List<List1Bean> list1) {
            this.list1 = list1;
        }

        public List<List2Bean> getList2() {
            return list2;
        }

        public void setList2(List<List2Bean> list2) {
            this.list2 = list2;
        }

        public List<List3Bean> getList3() {
            return list3;
        }

        public void setList3(List<List3Bean> list3) {
            this.list3 = list3;
        }

        public static class List1Bean {
            /**
             * key :
             * value : 全部
             */

            private String key;
            private String value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class List2Bean {
            /**
             * key :
             * value : 全部
             */

            private String key;
            private String value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class List3Bean {
            /**
             * yearStr : 2019年
             * year : 2019
             * months : [{"monthStr":"8月","month":8}]
             */

            private String yearStr;
            private int year;
            private List<MonthsBean> months;

            public String getYearStr() {
                return yearStr;
            }

            public void setYearStr(String yearStr) {
                this.yearStr = yearStr;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public List<MonthsBean> getMonths() {
                return months;
            }

            public void setMonths(List<MonthsBean> months) {
                this.months = months;
            }

            public static class MonthsBean {
                /**
                 * monthStr : 8月
                 * month : 8
                 */

                private String monthStr;
                private int month;

                public String getMonthStr() {
                    return monthStr;
                }

                public void setMonthStr(String monthStr) {
                    this.monthStr = monthStr;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }
            }
        }
    }
}
