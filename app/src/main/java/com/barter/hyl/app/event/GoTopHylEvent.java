package com.barter.hyl.app.event;

/**
 * Created by ${王涛} on 2021/9/13
 */
public class GoTopHylEvent {
    String id;
    int position;
    public GoTopHylEvent(String id, int position) {
        this.id = id;
        this.position = position;
    }

    public GoTopHylEvent(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
