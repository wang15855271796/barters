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
 * Created by ${王涛} on 2021/1/8
 */
public class ChooseCityPopWindow extends PopupWindow {
    private Activity context;
    private ChooseViews cascadingMenuView;
    private ArrayList<HylAreaModel.DataBean> areas;
    //提供给外的接口
    private CascadingMenuViewOnSelectListener menuViewOnSelectListener;

    public ChooseCityPopWindow(Activity mActivity, ArrayList<HylAreaModel.DataBean> listCity) {
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
        cascadingMenuView=new ChooseViews(context,areas);
        setContentView(cascadingMenuView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置回调接口
        cascadingMenuView.setCascadingMenuViewOnSelectListener(new MCascadingMenuViewOnSelectListener());

    }
    //级联菜单选择回调接口
    class MCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener{

        @Override
        public void getValue(HylAreaModel.DataBean menuItem) {
            if(menuViewOnSelectListener!=null){
                menuViewOnSelectListener.getValue(menuItem);
            }
        }

        @Override
        public void getValues(HylAreaModel.DataBean.CityListBean area) {
            if(menuViewOnSelectListener!=null){
                menuViewOnSelectListener.getValues(area);
                dismiss();
            }
        }

        @Override
        public void cloese() {
            if(menuViewOnSelectListener!=null){
                dismiss();
                menuViewOnSelectListener.cloese();
            }
        }

    }
}
