package com.barter.hyl.app.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/17
 */
public class HylActiviteModel {

    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":1,"startRow":0,"pages":1,"total":1,"list":[{"maidId":8664,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","minMaxPrice":"￥1.5-4040","inventFlag":0}],"hasPrePage":false,"hasNextPage":false}
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

    @Override
    public String toString() {
        return "HylActiviteModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error=" + error +
                ", success=" + success +
                '}';
    }

    public static class DataBean {
        /**
         * pageNum : 1
         * pageSize : 1
         * startRow : 0
         * pages : 1
         * total : 1
         * list : [{"maidId":8664,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png","productName":"老干妈大豆酱","minMaxPrice":"￥1.5-4040","inventFlag":0}]
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
             * maidId : 8664
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//18c341dc77e34d9e80e9985d94c9e1fa.png
             * productName : 老干妈大豆酱
             * minMaxPrice : ￥1.5-4040
             * inventFlag : 0
             */

            private int maidId;
            private String defaultPic;
            private String productName;
            private String minMaxPrice;
            private int inventFlag;
            private int cartNum;

            public int getCartNum() {
                return cartNum;
            }

            public void setCartNum(int cartNum) {
                this.cartNum = cartNum;
            }

            public int getMaidId() {
                return maidId;
            }

            public void setMaidId(int maidId) {
                this.maidId = maidId;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getMinMaxPrice() {
                return minMaxPrice;
            }

            public void setMinMaxPrice(String minMaxPrice) {
                this.minMaxPrice = minMaxPrice;
            }

            public int getInventFlag() {
                return inventFlag;
            }

            public void setInventFlag(int inventFlag) {
                this.inventFlag = inventFlag;
            }
        }
    }
}
