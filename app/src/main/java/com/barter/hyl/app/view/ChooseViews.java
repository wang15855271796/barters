package com.barter.hyl.app.view;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.model.CityChangeModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class ChooseViews extends LinearLayout {
    private static final String TAG = CascadingMenuView.class.getSimpleName();
    // 三级菜单选择后触发的接口，即最终选择的内容
    private CascadingMenuViewOnSelectListener mOnSelectListener;

    // 每次选择的子菜单内容
    private List<CityChangeModel.DataBean.CityNamesBean> secondItem = new ArrayList<>();
    private ArrayList<CityChangeModel.DataBean> menuItem;
    private Activity context;
    /**
     * @param context
     *            上下文
     */

    public ChooseViews(Activity context, ArrayList<CityChangeModel.DataBean> menuList) {
        super(context);
        this.menuItem = menuList;
        this.context = context;
    }


    public void setCascadingMenuViewOnSelectListener(CascadingMenuViewOnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
}
