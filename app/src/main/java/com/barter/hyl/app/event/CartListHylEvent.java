package com.barter.hyl.app.event;

import com.barter.hyl.app.model.HylCartNumModel;

/**
 * Created by ${王涛} on 2021/8/24 刷新购物车列表
 */
public class CartListHylEvent {
    HylCartNumModel hylCartNumModel;
    public CartListHylEvent() {
    }

    public CartListHylEvent(HylCartNumModel hylCartNumModel) {
        this.hylCartNumModel = hylCartNumModel;
    }

    public HylCartNumModel getCartNumModel() {
        return hylCartNumModel;
    }

    public void setCartNumModel(HylCartNumModel hylCartNumModel) {
        this.hylCartNumModel = hylCartNumModel;
    }
}
