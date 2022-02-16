package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/24
 */
public class HylGetWallertRecordByPageModel {
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
        private int pageNum;
        private int pageSize;
        private int startRow;
        private int pages;
        private int total;
        private boolean hasPrePage;
        private boolean hasNextPage;
        private List<DataBean.ListBean> list;

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

        public List<DataBean.ListBean> getList() {
            return list;
        }

        public void setList(List<DataBean.ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * nextMonth : null
             * nextYear : null
             * nowMonth : 8
             * nowYear : 2021
             * lastMonth : null
             * lastYear : null
             * inAmt : 收入 ￥1585.46
             * outAmt : 支出 ￥4456.26
             * records : [{"dateTime":"2021-08-26 11:05:15","flowRecordTypeName":"购物消费","createDate":null,"amount":"-299.00","walletRecordChannelType":null,"recordType":1,"userId":null,"subUserId":null,"type":null,"id":390631,"iconUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/6115d2affece4b23bd1b311c3e8e2436.png"},{"dateTime":"2021-08-26 10:25:36","flowRecordTypeName":"购物消费","createDate":null,"amount":"-168.00","walletRecordChannelType":null,"recordType":1,"userId":null,
             */

            private Object nextMonth;
            private Object nextYear;
            private int nowMonth;
            private int nowYear;
            private Object lastMonth;
            private Object lastYear;
            private String inAmt;
            private String outAmt;
            private List<DataBean.ListBean.RecordsBean> records;

            public Object getNextMonth() {
                return nextMonth;
            }

            public void setNextMonth(Object nextMonth) {
                this.nextMonth = nextMonth;
            }

            public Object getNextYear() {
                return nextYear;
            }

            public void setNextYear(Object nextYear) {
                this.nextYear = nextYear;
            }

            public int getNowMonth() {
                return nowMonth;
            }

            public void setNowMonth(int nowMonth) {
                this.nowMonth = nowMonth;
            }

            public int getNowYear() {
                return nowYear;
            }

            public void setNowYear(int nowYear) {
                this.nowYear = nowYear;
            }

            public Object getLastMonth() {
                return lastMonth;
            }

            public void setLastMonth(Object lastMonth) {
                this.lastMonth = lastMonth;
            }

            public Object getLastYear() {
                return lastYear;
            }

            public void setLastYear(Object lastYear) {
                this.lastYear = lastYear;
            }

            public String getInAmt() {
                return inAmt;
            }

            public void setInAmt(String inAmt) {
                this.inAmt = inAmt;
            }

            public String getOutAmt() {
                return outAmt;
            }

            public void setOutAmt(String outAmt) {
                this.outAmt = outAmt;
            }

            public List<DataBean.ListBean.RecordsBean> getRecords() {
                return records;
            }

            public void setRecords(List<DataBean.ListBean.RecordsBean> records) {
                this.records = records;
            }

            public static class RecordsBean {
                /**
                 * dateTime : 2021-08-26 11:05:15
                 * flowRecordTypeName : 购物消费
                 * createDate : null
                 * amount : -299.00
                 * walletRecordChannelType : null
                 * recordType : 1
                 * userId : null
                 * subUserId : null
                 * type : null
                 * id : 390631
                 * iconUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/6115d2affece4b23bd1b311c3e8e2436.png
                 */

                private String dateTime;
                private String flowRecordTypeName;
                private String createDate;
                private String amount;
                private Object walletRecordChannelType;
                private int recordType;
                private Object userId;
                private Object subUserId;
                private Object type;
                private int id;
                private String iconUrl;

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public String getFlowRecordTypeName() {
                    return flowRecordTypeName;
                }

                public void setFlowRecordTypeName(String flowRecordTypeName) {
                    this.flowRecordTypeName = flowRecordTypeName;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public Object getWalletRecordChannelType() {
                    return walletRecordChannelType;
                }

                public void setWalletRecordChannelType(Object walletRecordChannelType) {
                    this.walletRecordChannelType = walletRecordChannelType;
                }

                public int getRecordType() {
                    return recordType;
                }

                public void setRecordType(int recordType) {
                    this.recordType = recordType;
                }

                public Object getUserId() {
                    return userId;
                }

                public void setUserId(Object userId) {
                    this.userId = userId;
                }

                public Object getSubUserId() {
                    return subUserId;
                }

                public void setSubUserId(Object subUserId) {
                    this.subUserId = subUserId;
                }

                public Object getType() {
                    return type;
                }

                public void setType(Object type) {
                    this.type = type;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }
            }
        }
    }
}
