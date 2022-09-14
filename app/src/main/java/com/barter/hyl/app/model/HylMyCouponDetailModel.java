package com.barter.hyl.app.model;

import java.util.List;

public class HylMyCouponDetailModel {


    private Integer code;
    private String message;
    private DataBean data;
    private Object extData;
    private Boolean error;
    private Boolean success;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class DataBean {
        private Integer pageNum;
        private Integer pageSize;
        private Integer startRow;
        private Integer pages;
        private Integer total;
        private List<ListBean> list;
        private Boolean hasPrePage;
        private Boolean hasNextPage;

        public Integer getPageNum() {
            return pageNum;
        }

        public void setPageNum(Integer pageNum) {
            this.pageNum = pageNum;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getStartRow() {
            return startRow;
        }

        public void setStartRow(Integer startRow) {
            this.startRow = startRow;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public Boolean getHasPrePage() {
            return hasPrePage;
        }

        public void setHasPrePage(Boolean hasPrePage) {
            this.hasPrePage = hasPrePage;
        }

        public Boolean getHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(Boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public static class ListBean {
            private String mainId;
            private String productName;
            private String defaultPic;
            private String spec;
            private String saleNum;
            private String minMaxPrice;
            private Integer inventFlag;
            private List<String> specList;

            public String getMainId() {
                return mainId;
            }

            public void setMainId(String mainId) {
                this.mainId = mainId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(String saleNum) {
                this.saleNum = saleNum;
            }

            public String getMinMaxPrice() {
                return minMaxPrice;
            }

            public void setMinMaxPrice(String minMaxPrice) {
                this.minMaxPrice = minMaxPrice;
            }

            public Integer getInventFlag() {
                return inventFlag;
            }

            public void setInventFlag(Integer inventFlag) {
                this.inventFlag = inventFlag;
            }

            public List<String> getSpecList() {
                return specList;
            }

            public void setSpecList(List<String> specList) {
                this.specList = specList;
            }
        }
    }
}
