package com.barter.hyl.app.model;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by ${王涛} on 2020/3/9
 */
public class HylAreaModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"provinceName":"浙江省","provinceCode":"330000","cityNames":null,"cityList":[{"cityName":"杭州市","cityCode":"330100","areaNames":null,"areaList":[{"areaName":"上城区","areaCode":"330102"},{"areaName":"下城区","areaCode":"330103"}]}]}]
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private boolean error;
    private boolean success;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "HylAreaModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", error=" + error +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

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

    public static class DataBean implements IPickerViewData {
        /**
         * provinceName : 浙江省
         * provinceCode : 330000
         * cityNames : null
         * cityList : [{"cityName":"杭州市","cityCode":"330100","areaNames":null,"areaList":[{"areaName":"上城区","areaCode":"330102"},{"areaName":"下城区","areaCode":"330103"}]}]
         */

        private String provinceName;
        private String provinceCode;
        private Object cityNames;
        private List<CityListBean> cityList;

        @Override
        public String toString() {
            return "DataBean{" +
                    "provinceName='" + provinceName + '\'' +
                    ", provinceCode='" + provinceCode + '\'' +
                    ", cityNames=" + cityNames +
                    ", cityList=" + cityList +
                    '}';
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public Object getCityNames() {
            return cityNames;
        }

        public void setCityNames(Object cityNames) {
            this.cityNames = cityNames;
        }

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        @Override
        public String getPickerViewText() {
            return provinceName;
        }

        public static class CityListBean implements IPickerViewData{
            /**
             * cityName : 杭州市
             * cityCode : 330100
             * areaNames : null
             * areaList : [{"areaName":"上城区","areaCode":"330102"},{"areaName":"下城区","areaCode":"330103"}]
             */

            private String cityName;
            private String cityCode;
            private Object areaNames;
            private List<AreaListBean> areaList;

            @Override
            public String toString() {
                return "CityListBean{" +
                        "cityName='" + cityName + '\'' +
                        ", cityCode='" + cityCode + '\'' +
                        ", areaNames=" + areaNames +
                        ", areaList=" + areaList +
                        '}';
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public Object getAreaNames() {
                return areaNames;
            }

            public void setAreaNames(Object areaNames) {
                this.areaNames = areaNames;
            }

            public List<AreaListBean> getAreaList() {
                return areaList;
            }

            public void setAreaList(List<AreaListBean> areaList) {
                this.areaList = areaList;
            }

            @Override
            public String getPickerViewText() {
                return cityName;
            }

            public static class AreaListBean implements IPickerViewData{
                /**
                 * areaName : 上城区
                 * areaCode : 330102
                 */

                private String areaName;
                private String areaCode;

                @Override
                public String toString() {
                    return "AreaListBean{" +
                            "areaName='" + areaName + '\'' +
                            ", areaCode='" + areaCode + '\'' +
                            '}';
                }

                public String getAreaName() {
                    return areaName;
                }

                public void setAreaName(String areaName) {
                    this.areaName = areaName;
                }

                public String getAreaCode() {
                    return areaCode;
                }

                public void setAreaCode(String areaCode) {
                    this.areaCode = areaCode;
                }

                @Override
                public String getPickerViewText() {
                    return areaName;
                }
            }
        }
    }
}
