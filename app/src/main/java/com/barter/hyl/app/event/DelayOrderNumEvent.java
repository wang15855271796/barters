package com.barter.hyl.app.event;

import com.barter.hyl.app.model.HylMyOrderListModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/17
 */
public class DelayOrderNumEvent {
    List<HylMyOrderListModel.DataBean.ListBean> data;
    public DelayOrderNumEvent(List<HylMyOrderListModel.DataBean.ListBean> data) {
        this.data = data;
    }

    public List<HylMyOrderListModel.DataBean.ListBean> getData() {
        return data;
    }

    public void setData(List<HylMyOrderListModel.DataBean.ListBean> data) {
        this.data = data;
    }
}
