package com.barter.hyl.app.event;

public class SearchShopEvent {
    String keyWord;

    public SearchShopEvent(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
