package com.barter.hyl.app.event;

import com.barter.hyl.app.fragment.HylHome1Fragment;

/**
 * Created by ${王涛} on 2021/10/27
 */
public class ChangeIvEvent {
    HylHome1Fragment.AppBarLayoutState state;
    public ChangeIvEvent(HylHome1Fragment.AppBarLayoutState state) {
        this.state = state;
    }

    public HylHome1Fragment.AppBarLayoutState getState() {
        return state;
    }

    public void setState(HylHome1Fragment.AppBarLayoutState state) {
        this.state = state;
    }
}
