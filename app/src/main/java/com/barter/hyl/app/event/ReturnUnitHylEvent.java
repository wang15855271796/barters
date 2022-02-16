package com.barter.hyl.app.event;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/24
 */
public class ReturnUnitHylEvent {
    List<Integer> units;
    public ReturnUnitHylEvent(List<Integer> units) {
        this.units = units;
    }

    public List<Integer> getUnits() {
        return units;
    }

    public void setUnits(List<Integer> units) {
        this.units = units;
    }
}
