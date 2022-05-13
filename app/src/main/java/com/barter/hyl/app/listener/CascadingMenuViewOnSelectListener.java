package com.barter.hyl.app.listener;


import com.barter.hyl.app.model.CityChangeModel;

/**
 * 通用级联菜单接口
 * @author LILIN
 * 下午3:21:35
 */
public interface CascadingMenuViewOnSelectListener {
	void getValue(CityChangeModel.DataBean area);
	void getValues(CityChangeModel.DataBean.CityNamesBean area);
	void cloese();
}
