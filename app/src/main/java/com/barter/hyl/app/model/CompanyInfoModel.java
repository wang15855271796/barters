package com.barter.hyl.app.model;

import java.io.Serializable;
import java.util.List;

public class CompanyInfoModel extends BaseModel{


    /**
     * data : [{"companyPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message/f11bd8f83fbb43e8aa9a38b41f874303.jpeg","companyDesc":null},{"companyPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message/72ceca596d464c59847a0b2c0f8efd56.jpeg","companyDesc":null},{"companyPic":null,"companyDesc":"秒杀度是多么撒大声地"}]
     * extData : null
     */

    private Object extData;
    private List<DataBean> data;

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * companyPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message/f11bd8f83fbb43e8aa9a38b41f874303.jpeg
         * companyDesc : null
         */

        private String companyPic;
        private String companyDesc;

        public String getCompanyPic() {
            return companyPic;
        }

        public void setCompanyPic(String companyPic) {
            this.companyPic = companyPic;
        }

        public String getCompanyDesc() {
            return companyDesc;
        }

        public void setCompanyDesc(String companyDesc) {
            this.companyDesc = companyDesc;
        }
    }
}
