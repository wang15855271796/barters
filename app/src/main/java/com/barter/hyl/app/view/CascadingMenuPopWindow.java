package com.barter.hyl.app.view;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.model.CityChangeModel;
import com.barter.hyl.app.model.HylAreaModel;

import java.util.ArrayList;


/**
 * @author LILIN
 * 下午1:48:27
 * 提供PopWindow调用方法
 */
public class CascadingMenuPopWindow extends PopupWindow {


	private Activity context;
	private CascadingMenuViews cascadingMenuView;
	private ArrayList<HylAreaModel.DataBean> areas;
    //提供给外的接口
	private com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener menuViewOnSelectListener;

	public CascadingMenuPopWindow(Activity mActivity, ArrayList<HylAreaModel.DataBean> listCity) {
		super(mActivity);
		this.context=mActivity;
		this.areas=listCity;
		init();
	}

	public void setMenuViewOnSelectListener(CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
		this.menuViewOnSelectListener = menuViewOnSelectListener;
	}

	public void init(){
		//实例化级联菜单

		cascadingMenuView=new CascadingMenuViews(context,areas);
		setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		setContentView(cascadingMenuView);
		//设置回调接口
		cascadingMenuView.setCascadingMenuViewOnSelectListener(new MCascadingMenuViewOnSelectListener());
	}
	//级联菜单选择回调接口
	class MCascadingMenuViewOnSelectListener implements com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener {

		@Override
		public void getValue(HylAreaModel.DataBean menuItem) {
			if(menuViewOnSelectListener!=null){
				menuViewOnSelectListener.getValue(menuItem);
			}
		}

		@Override
		public void getValues(HylAreaModel.DataBean.CityListBean area,int pos) {
			if(menuViewOnSelectListener!=null){
				menuViewOnSelectListener.getValues(area,pos);
				dismiss();
			}
		}

		@Override
		public void cloese() {
			if(menuViewOnSelectListener!=null){
				dismiss();
			}
		}

	}
}
