package com.barter.hyl.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylChooseAddress1Adapter;
import com.barter.hyl.app.adapter.HylChooseAddresssAdapter;
import com.barter.hyl.app.api.AddressApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.AddAddressHylEvent;
import com.barter.hyl.app.event.SetDefaultHylEvent;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylAddressListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseConfirmActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_add)
    TextView tv_add;

    String searchKey;
    int pageNum = 1;
    int pageSize = 10;

    List<HylAddressListModel.DataBean.ListBean> data0 = new ArrayList<>();
    List<HylAddressListModel.DataBean.ListBean> data1 = new ArrayList<>();
    List<HylAddressListModel.DataBean.ListBean> mListData = new ArrayList<>();
    HylChooseAddresssAdapter addressAdapter;
    HylChooseAddress1Adapter address1Adapter;
    private int defaultId = -1;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_confirm_address);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setClickListener() {
        iv_clear.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        getAddress();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchKey = editable.toString();
                getAddress();
            }
        });
    }

    /**
     * 获取所有地址
     */
    private void getAddress() {
        AddressApi.AddressList(mContext,pageNum,pageSize,1,searchKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAddressListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylAddressListModel hylAddressListModel) {
                        if(hylAddressListModel.getCode()==1) {
                            mListData.clear();
                            data0.clear();
                            data1.clear();
                            if(hylAddressListModel.getData()!=null) {
                                if(hylAddressListModel.getData().getList()!=null && hylAddressListModel.getData().getList().size()>0) {
                                    mListData.addAll(hylAddressListModel.getData().getList());

                                    for (int i = 0; i <mListData.size() ; i++) {
                                        if(mListData.get(i).getSendType()==1) {
                                            //没超出范围
                                            data0.add(mListData.get(i));
                                        }else {
                                            data1.add(mListData.get(i));
                                        }
                                    }

                                    addressAdapter = new HylChooseAddresssAdapter(R.layout.item_dialog_address_hyl, data0, new HylChooseAddresssAdapter.Onclick() {
                                        @Override
                                        public void jump(int position) {
                                            Intent intent = new Intent(mContext,EditAddressActivity.class);
                                            intent.putExtra("addressId",data0.get(position).getAddressId());
                                            mContext.startActivity(intent);
                                        }
                                    });

                                    addressAdapter.setOnItemChangeClickListener(new HylChooseAddresssAdapter.OnEventClickListener() {
                                        @Override
                                        public void onEventClick(View view, int position, String flag) {
                                            if (flag.equals("default")) {
                                                defaultId = data0.get(position).getAddressId();
                                                getDefaultAddress(defaultId);
                                                //这里只是表面上地显示成这个地址为默认地址,要退出这个界面的时候调接口告知后台哪个变成默认地址了
//                                                for (int i = 0; i < mListData.size(); i++) {
//                                                    HylAddressListModel.DataBean.ListBean listBean = mListData.get(i);
//                                                    if (i == position) {
//                                                        if (listBean.isDefault == 1) {
//                                                            //原来就是默认地址,这里点击没有效果,原来也设置了原来是默认地址这里就没有效果
//                                                            //点击原来的默认地址,不会有操作,跳出界面的时候也不会有调接口操作
//                                                            Log.d("awsdasdwdss....","123");
//                                                        } else if (listBean.isDefault == 0) {
//                                                            Log.d("awsdasdwdss....","456");
//                                                            //原来不是默认地址,可以点击
//                                                            //一旦点击,这个即变成默认地址了
//                                                            //关键是,不能让用户点击一次就调一次接口重新刷新列表,需要在用户准备跳出这个界面的时候调接口
//                                                            listBean.isDefault = 1;
//                                                            //这里代表着切换了默认地址
//                                                            defaultId = mListData.get(i).getAddressId();
//                                                            getDefaultAddress(defaultId);
//                                                        }
//                                                    } else {
//                                                        mListData.get(i).isDefault = 0;
//                                                    }
//                                                }
                                                addressAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                                    recyclerView.setAdapter(addressAdapter);
                                    addressAdapter.notifyDataSetChanged();

                                    address1Adapter = new HylChooseAddress1Adapter(R.layout.item_dialog_address_hyl,data1);
                                    if(data1.size()==0) {
                                        tv1.setVisibility(View.GONE);
                                    }else {
                                        tv1.setVisibility(View.VISIBLE);
                                    }

                                    recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
                                    recyclerView1.setAdapter(address1Adapter);
                                    address1Adapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                });
    }

    /**
     * 切换默认地址
     */
    private void getDefaultAddress(int addressId) {
        AddressApi.AddressDefault(mContext,addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                            EventBus.getDefault().post(new SetDefaultHylEvent());
                            addressAdapter.notifyDataSetChanged();
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
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

            case R.id.iv_clear:
                et_search.getText().clear();
                break;

            case R.id.tv_add:
//                Intent intent1 = HylAddressListsActivity.getIntent(mContext, HylAddressListsActivity.class);
//                mContext.startActivity(intent1);
                Intent intent = new Intent(mContext,HylAddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 新增默认地址的回调
     * @param addAddressHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addAddress(AddAddressHylEvent addAddressHylEvent) {
        getAddress();
    }

    /**
     * 编辑默认地址的回调
     * @param setDefaultHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addAddress(SetDefaultHylEvent setDefaultHylEvent) {
        getAddress();
    }
}
