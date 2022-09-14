package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2021/8/2
 */
public class HylLoginModel extends BaseModel{
    public String data;
    public String companyName;
    public String connectPhone;
    public ExtDataBean extData;

    public ExtDataBean getExtData() {
        return extData;
    }

    public void setExtData(ExtDataBean extData) {
        this.extData = extData;
    }

    public static class ExtDataBean {
        public String companyName;
        public String contactPhone;
        public String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }
    }
}
