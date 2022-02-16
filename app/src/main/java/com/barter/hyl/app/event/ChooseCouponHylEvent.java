package com.barter.hyl.app.event;

/**
 * Created by ${王涛} on 2021/8/26
 */
public class ChooseCouponHylEvent {
    String GiftDetailNo;

    public ChooseCouponHylEvent(String giftDetailNo) {
        GiftDetailNo = giftDetailNo;
    }

    public String getGiftDetailNo() {
        return GiftDetailNo;
    }

    public void setGiftDetailNo(String giftDetailNo) {
        GiftDetailNo = giftDetailNo;
    }
}
