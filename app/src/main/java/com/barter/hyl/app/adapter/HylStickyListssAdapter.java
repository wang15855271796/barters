package com.barter.hyl.app.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylMyBillModel;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

import java.util.List;


public class HylStickyListssAdapter extends BaseAdapter implements StickyListHeadersAdapter {

	Activity mActivity;
	List<HylMyBillModel.DataBean.ListBean> list;
	OnclickListener onclickListener;
	TextView tv_month_select;
	public HylStickyListssAdapter(Activity mActivity, List<HylMyBillModel.DataBean.ListBean> list,OnclickListener onclickListener) {
		this.mActivity = mActivity;
		this.list = list;
		this.onclickListener = onclickListener;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		View headView = LayoutInflater.from(mActivity).inflate(R.layout.item_bill_header, null);
		HylMyBillModel.DataBean.ListBean listBean = list.get(position);
		tv_month_select = headView.findViewById(R.id.tv_month_select);
		TextView tv_expenditure = headView.findViewById(R.id.tv_expenditure);
		TextView tv_income = headView.findViewById(R.id.tv_income);
		tv_income.setText(list.get(position).getInAmt());
		tv_expenditure.setText(list.get(position).getOutAmt());
		tv_month_select.setText(listBean.getNowYear()+"-"+listBean.getNowMonth()+"");
		tv_month_select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onclickListener.headClick();
			}
		});
		return headView;
	}

	@Override
	public long getHeaderId(int position) {
		long time;
		String nowYear = String.valueOf(list.get(position).getNowYear());
		String nowMonth = String.valueOf(list.get(position).getNowMonth());
		time = Long.parseLong(nowYear + nowMonth);
		return time;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	StickyListAdapter stickyListAdapter;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.item_bill_content_hyl, null);
			holder = createHolder(view);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		holder.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

		stickyListAdapter = new StickyListAdapter(R.layout.item_sticky, list.get(position).getRecords());
		holder.recyclerView.setAdapter(stickyListAdapter);

		holder.recyclerView.setHasFixedSize(true);
		holder.recyclerView.setNestedScrollingEnabled(false);
		return view;
	}

	private Holder createHolder(View view) {
		return new Holder(view);
	}

	class Holder {
		RecyclerView recyclerView;
		public Holder(View view) {
			recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		}
	}

	public interface OnclickListener {
		void headClick();
	}
}



