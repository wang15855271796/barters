package com.barter.hyl.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.ImageViewAdapter;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.InfoDetailModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_call)
    TextView tv_call;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String msgId;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_cate)
    TextView tv_cate;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    InfoDetailModel.DataBean lists;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        msgId = getIntent().getStringExtra("msgId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shop_detail);
    }

    @Override
    public void setViewData() {

        iv_back.setOnClickListener(this);
        tv_call.setOnClickListener(this);
        getCityList();
    }

    @Override
    public void setClickListener() {

    }


    private void getCityList() {
        InfoListAPI.getDetail(mActivity, msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoDetailModel infoListModel) {
                        if (infoListModel.isSuccess()) {
                            if (infoListModel.getData() != null) {
                                lists = infoListModel.getData();
                                tv_phone.setText(lists.getUserPhone());
                                tv_content.setText(lists.getContent());
                                tv_cate.setText(lists.getMsgTypeName());
                                tv_contact.setText(lists.getContactPhone());
                                tv_address.setText(lists.getDetailAddress());

                                ImageViewAdapter imageViewAdapter = new ImageViewAdapter(R.layout.item_image, lists.getPictureList());
                                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                                recyclerView.setAdapter(imageViewAdapter);
                            }
                        } else {
                            AppHelper.showMsg(mActivity, infoListModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_call:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lists.getContactPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

}

