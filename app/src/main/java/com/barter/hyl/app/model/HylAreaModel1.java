package com.barter.hyl.app.model;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class HylAreaModel1 {
    /**
     * code : 1
     * message : 成功
     * data : [{"provinceName":"浙江省","provinceCode":"330000","cityNames":null,"cityList":[{"cityName":"杭州市","cityCode":"330100","areaNames":null,"areaList":[{"areaName":"上城区","areaCode":"330102"},{"areaName":"下城区","areaCode":"330103"}]}]}]
     * error : false
     * success : true
     */

    private List<HylAreaModel1.DataBean> data;

    public List<HylAreaModel1.DataBean> getData() {
        return data;
    }

    public void setData(List<HylAreaModel1.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String provinceName;
        private String provinceCode;
        private List<HylAreaModel1.DataBean.CityListBean> cityList;


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


        public List<HylAreaModel1.DataBean.CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<HylAreaModel1.DataBean.CityListBean> cityList) {
            this.cityList = cityList;
        }


        public static class CityListBean {

            private String cityName;
            private String cityCode;

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
        }
    }
}
