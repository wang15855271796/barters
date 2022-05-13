package com.barter.hyl.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.Menu2ItemAdapter;
import com.barter.hyl.app.adapter.Menu3ItemAdapter;
import com.barter.hyl.app.adapter.MenuItemAdapter;
import com.barter.hyl.app.model.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 三级级联动ListView
 * 
 */
public class CascadingMenuView extends LinearLayout {
	private static final String TAG = CascadingMenuView.class.getSimpleName();
	// 三级菜单选择后触发的接口，即最终选择的内容
	private CascadingMenuViewOnSelectListener mOnSelectListener;
	private ListView firstMenuListView;
	private ListView secondMenuListView;
	private ListView thirdMenuListView;

	// 每次选择的子菜单内容
	private List<AddressBean.DataBean.ListBeanX.ListBean> thirdItem = new ArrayList<>();
	private List<AddressBean.DataBean.ListBeanX> secondItem = new ArrayList<>();
	private List<AddressBean.DataBean> menuItem;

	private MenuItemAdapter firstMenuListViewAdapter;

	private Menu2ItemAdapter secondMenuListViewAdapter;

	private Menu3ItemAdapter thirdMenuListViewAdapter;

	private int firstPosition = 0;
	private int secondPosition = 0;
	private int thirdPosition = 0;

	private Context context;
    private AddressBean.DataBean.ListBeanX menuItem1;

    /**
	 * @param context
	 *            上下文
	 */
	public CascadingMenuView(Context context, List<AddressBean.DataBean> menuList) {
		super(context);
		this.menuItem = menuList;
		this.context = context;
		init(context);
	}

	public CascadingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		firstMenuListView = (ListView) findViewById(R.id.listView);
		secondMenuListView = (ListView) findViewById(R.id.listView2);
		thirdMenuListView = (ListView) findViewById(R.id.listView3);

		// 初始化一级主菜单
		firstMenuListViewAdapter = new MenuItemAdapter(context, menuItem, R.color.white, R.color.white);
		firstMenuListView.setAdapter(firstMenuListViewAdapter);
		firstMenuListViewAdapter.setTextSize(17);
		firstMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// 选择主菜单，清空原本子菜单内容，增加新内容
				if (mOnSelectListener != null) {
					mOnSelectListener.getValuess(menuItem.get(position));
				}

				if(menuItem.get(position).getList()!=null&&menuItem.get(position).getList().size()!=0) {
					secondItem = menuItem.get(position).getList();
					secondMenuListViewAdapter = new Menu2ItemAdapter(context, secondItem, R.color.white, R.color.white);
					secondMenuListView.setAdapter(secondMenuListViewAdapter);
					// 通知适配器刷新
					secondMenuListView.setVisibility(View.VISIBLE);
					secondMenuListViewAdapter.setTextSize(15);
					secondMenuListViewAdapter.setSelectedPositionNoNotifys(0, secondItem);
					secondMenuListViewAdapter.notifyDataSetChanged();
					secondMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {

						@Override
						public void onItemClick(View view, final int position) {
							// 选择主菜单，清空原本子菜单内容，增加新内容
							if (mOnSelectListener != null) {
								mOnSelectListener.getValues(secondItem.get(position));
							}

							if(secondItem.get(position).getList()!=null&&secondItem.get(position).getList().size()!=0) {
								thirdItem = secondItem.get(position).getList();
								menuItem1 = secondItem.get(position);
								thirdMenuListViewAdapter = new Menu3ItemAdapter(context, thirdItem, R.color.white,R.color.white);
								// 通知适配器刷新
								thirdMenuListViewAdapter.setTextSize(15);
								thirdMenuListViewAdapter.notifyDataSetChanged();
								thirdMenuListViewAdapter.setSelectedPositionNoNotifys(0,thirdItem);
								thirdMenuListView.setVisibility(View.VISIBLE);
								thirdMenuListView.setAdapter(thirdMenuListViewAdapter);
								thirdMenuListViewAdapter.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {
									@Override
									public void onItemClick(View view, final int position) {
										AddressBean.DataBean.ListBeanX.ListBean menuItem = thirdItem.get(position);
										if (mOnSelectListener != null) {
											mOnSelectListener.getValue(menuItem);
										}
									}
								});

							}else {
								thirdMenuListView.setVisibility(View.GONE);
							}
						}
					});

				}else {

					secondMenuListView.setVisibility(View.GONE);
					thirdMenuListView.setVisibility(View.GONE);
				}
			}
		});

		// 设置默认选择
		setDefaultSelect();
	}

	public void setDefaultSelect() {
		firstMenuListView.setSelection(firstPosition);
		secondMenuListView.setSelection(secondPosition);
		thirdMenuListView.setSelection(thirdPosition);
	}

	public void setCascadingMenuViewOnSelectListener(CascadingMenuViewOnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}
}
