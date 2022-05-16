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
        private List<CityNamesBean> cityNames;
        private List<CityListBean> cityList;

        public List<CityNamesBean> getCityNames() {
            return cityNames;
        }

        public void setCityNames(List<CityNamesBean> cityNames) {
            this.cityNames = cityNames;
        }

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

        public static class CityNamesBean {
            /**
             * cityName : 北京市
             * cityCode : 110110
             * areaNames : [{"areaName":"东城区","areaCode":"110101"},{"areaName":"西城区","areaCode":"110102"},{"areaName":"朝阳区","areaCode":"110105"},{"areaName":"丰台区","areaCode":"110106"},{"areaName":"石景山区","areaCode":"110107"},{"areaName":"海淀区","areaCode":"110108"},{"areaName":"门头沟区","areaCode":"110109"},{"areaName":"房山区","areaCode":"110111"},{"areaName":"通州区","areaCode":"110112"},{"areaName":"顺义区","areaCode":"110113"},{"areaName":"昌平区","areaCode":"110114"},{"areaName":"大兴区","areaCode":"110115"},{"areaName":"怀柔区","areaCode":"110116"},{"areaName":"平谷区","areaCode":"110117"},{"areaName":"密云县","areaCode":"110228"},{"areaName":"延庆县","areaCode":"110229"}]
             */

            private String cityName;
            private String cityCode;
            private List<AreaNamesBean> areaNames;

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

            public List<AreaNamesBean> getAreaNames() {
                return areaNames;
            }

            public void setAreaNames(List<AreaNamesBean> areaNames) {
                this.areaNames = areaNames;
            }

            @Override
            public String toString() {
                return "CityNamesBean{" +
                        "cityName='" + cityName + '\'' +
                        ", cityCode='" + cityCode + '\'' +
                        ", areaNames=" + areaNames +
                        '}';
            }

            public static class AreaNamesBean {
                /**
                 * areaName : 东城区
                 * areaCode : 110101
                 */

                private String areaName;
                private String areaCode;

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
            }

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
            private List<AreaNamesBean> areaNames;
            private List<AreaListBean> areaList;

            public List<AreaNamesBean> getAreaNames() {
                return areaNames;
            }

            public void setAreaNames(List<AreaNamesBean> areaNames) {
                this.areaNames = areaNames;
            }

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

            public static class AreaNamesBean {
                /**
                 * areaName : 东城区
                 * areaCode : 110101
                 */

                private String areaName;
                private String areaCode;

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
