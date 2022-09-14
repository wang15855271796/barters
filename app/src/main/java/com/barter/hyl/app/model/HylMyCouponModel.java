package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class HylMyCouponModel {

    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":10,"startRow":0,"pages":1,"total":3,"list":[{"amount":"1.00","giftName":"货易链优惠券","useDesc":"满10.00元减1.00元","role":"全场通用;;与活动同享","dateTime":"2021.09.01 0:00-2021.10.01 0:00","state":"ENABLED"},{"amount":"1.00","giftName":"货易链优惠券","useDesc":"满10.00元减1.00元","role":"全场通用;;与活动同享","dateTime":"2021.08.29 0:00-2021.09.28 0:00","state":"ENABLED"},{"amount":"3.00","giftName":"全场通用券-测试1033","useDesc":"满100.00元减3.00元","role":"全场通用;;与活动同享","dateTime":"2021.08.24 0:00-2021.09.23 0:00","state":"ENABLED"}],"hasPrePage":false,"hasNextPage":false}
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
         * total : 3
         * list : [{"amount":"1.00","giftName":"货易链优惠券","useDesc":"满10.00元减1.00元","role":"全场通用;;与活动同享","dateTime":"2021.09.01 0:00-2021.10.01 0:00","state":"ENABLED"},{"amount":"1.00","giftName":"货易链优惠券","useDesc":"满10.00元减1.00元","role":"全场通用;;与活动同享","dateTime":"2021.08.29 0:00-2021.09.28 0:00","state":"ENABLED"},{"amount":"3.00","giftName":"全场通用券-测试1033","useDesc":"满100.00元减3.00元","role":"全场通用;;与活动同享","dateTime":"2021.08.24 0:00-2021.09.23 0:00","state":"ENABLED"}]
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
             * amount : 1.00
             * giftName : 货易链优惠券
             * useDesc : 满10.00元减1.00元
             * role : 全场通用;;与活动同享
             * dateTime : 2021.09.01 0:00-2021.10.01 0:00
             * state : ENABLED
             */

            private String amount;
            private String giftName;
            private String useDesc;
            private List<String> role;
            private String dateTime;
            private String state;
            int useProd;
            String poolNo;

            public int getUseProd() {
                return useProd;
            }

            public void setUseProd(int useProd) {
                this.useProd = useProd;
            }

            public String getPoolNo() {
                return poolNo;
            }

            public void setPoolNo(String poolNo) {
                this.poolNo = poolNo;
            }

            public List<String> getRole() {
                return role;
            }

            public void setRole(List<String> role) {
                this.role = role;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getGiftName() {
                return giftName;
            }

            public void setGiftName(String giftName) {
                this.giftName = giftName;
            }

            public String getUseDesc() {
                return useDesc;
            }

            public void setUseDesc(String useDesc) {
                this.useDesc = useDesc;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
