package com.barter.hyl.app.event;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class FromIndexHylEvent {
    String id;
    public FromIndexHylEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
