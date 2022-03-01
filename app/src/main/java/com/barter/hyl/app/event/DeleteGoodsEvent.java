package com.barter.hyl.app.event;

import com.barter.hyl.app.model.HylCartListModel;

public class DeleteGoodsEvent {
    int adapterPosition;
    HylCartListModel.DataBean.ProdsBeanX.ProdsBean item;
    public DeleteGoodsEvent(int adapterPosition, HylCartListModel.DataBean.ProdsBeanX.ProdsBean item) {
        this.adapterPosition = adapterPosition;
        this.item = item;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public HylCartListModel.DataBean.ProdsBeanX.ProdsBean getItem() {
        return item;
    }

    public void setItem(HylCartListModel.DataBean.ProdsBeanX.ProdsBean item) {
        this.item = item;
    }
}
