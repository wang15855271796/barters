package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.CouponUseAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.CouponListsModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class CouponUseActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_title)
    TextView tv_title;
    int pageNum = 1;
    int pageSize = 10;
    String type;
    String poolNo;
    String name;
    CouponUseAdapter couponUseAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        poolNo = getIntent().getStringExtra("poolNo");
        name = getIntent().getStringExtra("name");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coupon_use);
    }


    @Override
    public void setViewData() {
        tv_title.setText("优惠券详情");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        couponUseAdapter = new CouponUseAdapter(R.layout.item_coupon_use,list);
        recyclerView.setAdapter(couponUseAdapter);

        if(type.equals("1")) {
            tv_desc.setText("以下商品可使用"+name);
        }else if(type.equals("2")) {
            tv_desc.setText("以下商品不可使用"+name);
        }

        getFullList(poolNo);

        couponUseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, HylCommonGoodsActivity.class);
                intent.putExtra("activeId",list.get(position).getProductMainId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);

    }

    /**
     * 获取列表数据
     */
    List<CouponListsModel.DataBean.ListBean> list = new ArrayList<>();
    private void getFullList(String poolNo) {
        MyInfoApi.couponGoodList(mContext,poolNo,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CouponListsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CouponListsModel fullCouponListModel) {
                        if(fullCouponListModel.getCode()==1) {
                            if(fullCouponListModel.getData()!=null) {
                                list.addAll(fullCouponListModel.getData().getList());
                                couponUseAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,fullCouponListModel.getMessage());
                        }
                    }
                });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
