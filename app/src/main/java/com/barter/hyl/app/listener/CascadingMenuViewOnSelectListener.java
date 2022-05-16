package com.barter.hyl.app.listener;

import com.barter.hyl.app.model.HylAreaModel;

/**
 * 通用级联菜单接口
 * @author LILIN
 * 下午3:21:35
 */
public interface CascadingMenuViewOnSelectListener {
	void getValue(HylAreaModel.DataBean area);
	void getValues(HylAreaModel.DataBean.CityListBean area);
	void cloese();
}
