package com.barter.hyl.app.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.MenuItemAdapter;
import com.barter.hyl.app.adapter.MenuItemsAdapter;
import com.barter.hyl.app.adapter.MenuSecondItemAdapter;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.model.CityChangeModel;
import com.barter.hyl.app.model.HylAreaModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class ChooseViews extends LinearLayout {
    // 三级菜单选择后触发的接口，即最终选择的内容
    private CascadingMenuViewOnSelectListener mOnSelectListener;

    // 每次选择的子菜单内容
    private List<HylAreaModel.DataBean.CityListBean> secondItem = new ArrayList<>();
    private ArrayList<HylAreaModel.DataBean> menuItem;
    private ListView firstMenuListView;
    private ListView secondMenuListView;
    private MenuItemsAdapter firstMenuListViewAdapter;
    AVLoadingIndicatorView lav_activity_loading;
    private MenuSecondItemAdapter secondMenuListViewAdapter;
    private int firstPosition = 0;
    private int secondPosition = 0;
    /**
     * @param context
     *            上下文
     */

    public ChooseViews(Activity context, ArrayList<HylAreaModel.DataBean> menuList) {
        super(context);
        this.menuItem = menuList;
        init(context);
    }

    private void init(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_regionss, this, true);
        lav_activity_loading = findViewById(R.id.lav_activity_loading);
        TextView tv_area = findViewById(R.id.tv_area);
        LinearLayout ll_all = findViewById(R.id.ll_all);
        String city = UserInfoHelper.getCity(context);
        tv_area.setText(city);

        ll_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSelectListener != null) {
                    mOnSelectListener.cloese();
                    firstMenuListViewAdapter.setCustText("");
                    firstMenuListViewAdapter.notifyDataSetChanged();
                    secondMenuListViewAdapter.setCustText("");
                    secondMenuListViewAdapter.notifyDataSetChanged();
                }
            }
        });
        firstMenuListView = (ListView) findViewById(R.id.listView);
        secondMenuListView = (ListView) findViewById(R.id.listView2);
        // 初始化一级主菜单
        firstMenuListViewAdapter = new MenuItemsAdapter(context, menuItem,
                R.drawable.choose_eara_selector,
                R.drawable.choose_eara_selector);
        firstMenuListViewAdapter.setTextSize(17);
        firstMenuListViewAdapter.setSelectedPositionNoNotify(firstPosition, menuItem);
        firstMenuListView.setAdapter(firstMenuListViewAdapter);
        View headView = inflater.inflate(R.layout.head_view,null);
//        firstMenuListView.addHeaderView(headView);
//        secondMenuListView.addHeaderView(headView);

        firstMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                secondItem = menuItem.get(position).getCityList();

                // 通知适配器刷新
                secondMenuListViewAdapter.notifyDataSetChanged();
                secondMenuListViewAdapter.setSelectedPositionNoNotify(0, secondItem);

                if (mOnSelectListener != null) {
                    mOnSelectListener.getValue(menuItem.get(position));
                }
            }
        });

        // 初始化二级主菜单
        secondItem = menuItem.get(firstPosition).getCityList();
        secondMenuListViewAdapter = new MenuSecondItemAdapter(context, secondItem, R.drawable.choose_eara_item_selector, R.drawable.choose_eara_item_selector);
        secondMenuListViewAdapter.setTextSize(15);
//        secondMenuListViewAdapter
        secondMenuListViewAdapter.setSelectedPositionNoNotify(secondPosition, secondItem);
        secondMenuListView.setAdapter(secondMenuListViewAdapter);

        secondMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                if (mOnSelectListener != null) {
                    mOnSelectListener.getValues(secondItem.get(position),position);
                }
            }
        });
    }

    public void setCascadingMenuViewOnSelectListener(CascadingMenuViewOnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
}
