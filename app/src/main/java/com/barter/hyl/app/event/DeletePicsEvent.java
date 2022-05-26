package com.barter.hyl.app.event;

import java.util.List;

public class DeletePicsEvent {
    List<String> selectList;
    public DeletePicsEvent(List<String> selectList) {
        this.selectList = selectList;
    }

    public List<String> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }
}
