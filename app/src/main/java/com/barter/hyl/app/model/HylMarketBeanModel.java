package com.barter.hyl.app.model;

/**
 * Created by ${王涛} on 2020/8/2
 */
public class HylMarketBeanModel {
    private String s;
    private boolean state;

    public HylMarketBeanModel(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
