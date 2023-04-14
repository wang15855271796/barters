package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/2
 */
public class HylMyOrderListModel {

    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":10,"startRow":0,"pages":1,"total":1,"list":[{"name":"老干妈大豆酱1件商品","orderId":"20210827152450-a736","orderStatus":2,"orderStatusStr":"待发货-待接收","orderStateStr":"待履约","returnMainId":null,"pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png"],"totalAmount":"￥583.50","dateTime":"2021-08-27 15:24:50"}],"hasPrePage":false,"hasNextPage":false}
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
         * list : [{"name":"老干妈大豆酱1件商品","orderId":"20210827152450-a736","orderStatus":2,"orderStatusStr":"待发货-待接收","orderStateStr":"待履约","returnMainId":null,"pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png"],"totalAmount":"￥583.50","dateTime":"2021-08-27 15:24:50"}]
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
             * name : 老干妈大豆酱1件商品
             * orderId : 20210827152450-a736
             * orderStatus : 2
             * orderStatusStr : 待发货-待接收
             * orderStateStr : 待履约
             * returnMainId : null
             * pics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png"]
             * totalAmount : ￥583.50
             * dateTime : 2021-08-27 15:24:50
             */
            private int orderType;
            private String name;
            private String orderId;
            private int orderStatus;
            private String orderStatusStr;
            private String orderStateStr;
            private String returnMainId;
            private String totalAmount;
            private String dateTime;
            private List<String> pics;
            boolean selected;
            private String id;
            private boolean payFlag;
            private boolean offlinePay;
            boolean bankReturnFlag;
            public boolean isBankReturnFlag() {
                return bankReturnFlag;
            }
            public boolean canDelete;

            public boolean isCanDelete() {
                return canDelete;
            }

            public void setCanDelete(boolean canDelete) {
                this.canDelete = canDelete;
            }

            public void setBankReturnFlag(boolean bankReturnFlag) {
                this.bankReturnFlag = bankReturnFlag;
            }

            public boolean isOfflinePay() {
                return offlinePay;
            }

            public void setOfflinePay(boolean offlinePay) {
                this.offlinePay = offlinePay;
            }

            public boolean isPayFlag() {
                return payFlag;
            }

            public void setPayFlag(boolean payFlag) {
                this.payFlag = payFlag;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderStatusStr() {
                return orderStatusStr;
            }

            public void setOrderStatusStr(String orderStatusStr) {
                this.orderStatusStr = orderStatusStr;
            }

            public String getOrderStateStr() {
                return orderStateStr;
            }

            public void setOrderStateStr(String orderStateStr) {
                this.orderStateStr = orderStateStr;
            }

            public String getReturnMainId() {
                return returnMainId;
            }

            public void setReturnMainId(String returnMainId) {
                this.returnMainId = returnMainId;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
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
