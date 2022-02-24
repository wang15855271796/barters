package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.CouponAdapter;
import com.barter.hyl.app.adapter.FullGoodsAdapter;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.FullActiveDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FullActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    List<FullActiveDetailModel.DataBean.SendGiftsBean> sendGifts;
    List<FullActiveDetailModel.DataBean.SendGiftsBean> list1 = new ArrayList<>();
    List<FullActiveDetailModel.DataBean.SendGiftsBean> list2 = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full_lists);
    }

    @Override
    public void setViewData() {
        tv_title.setText("赠品列表");
        sendGifts = (List<FullActiveDetailModel.DataBean.SendGiftsBean>) getIntent().getSerializableExtra("sendGifts");

        for (int i = 0; i < sendGifts.size(); i++) {
            if(sendGifts.get(i).getType()==0) {
                //赠品
                list1.add(sendGifts.get(i));
            }else {
                list2.add(sendGifts.get(i));
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        FullGoodsAdapter fullGoodsAdapter = new FullGoodsAdapter(R.layout.item_fulls_cart,list1);
        recyclerView.setAdapter(fullGoodsAdapter);
        fullGoodsAdapter.notifyDataSetChanged();

        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        CouponAdapter coupon4Adapter = new CouponAdapter(R.layout.item_coupons,list2);
        recycler.setAdapter(coupon4Adapter);
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
