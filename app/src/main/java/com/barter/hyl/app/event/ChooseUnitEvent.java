package com.barter.hyl.app.event;

/**
 * Created by ${王涛} on 2021/9/20(退货选择单位时触发)
 */
public class ChooseUnitEvent {
    String unitName;
    public ChooseUnitEvent(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
