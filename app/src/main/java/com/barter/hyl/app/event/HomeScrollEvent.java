package com.barter.hyl.app.event;

public class HomeScrollEvent {
    int length;
    public HomeScrollEvent(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
