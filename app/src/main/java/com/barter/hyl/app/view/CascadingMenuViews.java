package com.barter.hyl.app.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.MenuItemAdapter;
import com.barter.hyl.app.adapter.MenuItemsAdapter;
import com.barter.hyl.app.adapter.MenuSecondItemAdapter;
import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.model.CityChangeModel;
import com.barter.hyl.app.model.HylAreaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/1/5
 */
public class CascadingMenuViews extends LinearLayout {

    private static final String TAG = CascadingMenuView.class.getSimpleName();
    // 三级菜单选择后触发的接口，即最终选择的内容
    private com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener mOnSelectListener;
    private ListView firstMenuListView;
    private ListView secondMenuListView;
    private ListView thirdMenuListView;

    // 每次选择的子菜单内容
    private List<HylAreaModel.DataBean.CityListBean> secondItem = new ArrayList<>();
    private ArrayList<HylAreaModel.DataBean> menuItem;

    private MenuItemsAdapter firstMenuListViewAdapter;

    private MenuSecondItemAdapter secondMenuListViewAdapter;

    ImageView iv_close;
    private int firstPosition = 0;
    private int secondPosition = 0;
    private Activity context;

    /**
     * @param context
     *            上下文
     */

    public CascadingMenuViews(Activity context, ArrayList<HylAreaModel.DataBean> menuList) {
        super(context);
        this.menuItem = menuList;
        this.context = context;
        init(context);
    }

    private void init(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_regions, this, true);
        firstMenuListView = (ListView) findViewById(R.id.listView);
        secondMenuListView = (ListView) findViewById(R.id.listView2);
        iv_close = findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSelectListener.cloese();
            }
        });
        // 初始化一级主菜单
        firstMenuListViewAdapter = new MenuItemsAdapter(context, menuItem,
                R.drawable.choose_eara_item_selector,
                R.drawable.choose_eara_item_selector);
        firstMenuListViewAdapter.setTextSize(17);
        firstMenuListViewAdapter.setSelectedPositionNoNotify(firstPosition, menuItem);
        firstMenuListView.setAdapter(firstMenuListViewAdapter);
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

                        secondMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, final int position) {
                                if (mOnSelectListener != null) {
                                    mOnSelectListener.getValues(secondItem.get(position));
                                }
                            }
                        });
                    }
                });


        // 初始化二级主菜单
        secondItem = menuItem.get(firstPosition).getCityList();
        secondMenuListViewAdapter = new MenuSecondItemAdapter(context, secondItem, R.drawable.choose_eara_item_selector, R.drawable.choose_eara_item_selector);
        secondMenuListViewAdapter.setTextSize(15);
        secondMenuListViewAdapter.setSelectedPositionNoNotify(secondPosition, secondItem);
        secondMenuListView.setAdapter(secondMenuListViewAdapter);

    }

    public void setCascadingMenuViewOnSelectListener(CascadingMenuViewOnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

}
