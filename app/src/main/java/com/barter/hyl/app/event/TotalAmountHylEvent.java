package com.barter.hyl.app.event;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24
 */
public class TotalAmountHylEvent {
    String allPrice;
    List<String>ids;
    public TotalAmountHylEvent(String allPrice) {
        this.allPrice = allPrice;
    }

    public TotalAmountHylEvent(String allPrice, List<String> ids) {
        this.allPrice = allPrice;
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }
}
