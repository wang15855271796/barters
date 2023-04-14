package com.barter.hyl.app.event;

public class ChangeInfoNameEvent {
    String rentName;
    public ChangeInfoNameEvent(String rentName) {
        this.rentName = rentName;
    }

    public String getRentName() {
        return rentName;
    }

    public void setRentName(String rentName) {
        this.rentName = rentName;
    }
}
