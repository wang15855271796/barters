package com.barter.hyl.app.event;

public class DeletePicEvent {
    int pos;
    public DeletePicEvent(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
