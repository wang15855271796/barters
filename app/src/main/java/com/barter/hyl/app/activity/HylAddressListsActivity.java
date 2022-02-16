package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylAddressAdapter;
import com.barter.hyl.app.api.AddressApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.AddAddressHylEvent;
import com.barter.hyl.app.model.HylAddressListModel;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${王涛} on 2020/3/11
 */
public class HylAddressListsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_add_address)
    RelativeLayout rl_add_address;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    HylAddressAdapter hylAddressAdapter;

    View emptyView;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_address_list_hyl);
    }

    int position;
    @Override
    public void setViewData() {
        tv_title.setText("收货地址管理");
        EventBus.getDefault().register(this);
        emptyView = View.inflate(mActivity, R.layout.no_view, null);
        hylAddressAdapter = new HylAddressAdapter(R.layout.item_address_hyl,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(hylAddressAdapter);
        smart.autoRefresh();

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getAddressList();
                smart.finishRefresh();
            }
        });

        smart.setEnableAutoLoadMore(true);
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hylAddressListModels.getData()!=null) {
                    if(hylAddressListModels.getData().isHasNextPage()) {
                        pageNum++;
                        getAddressList();
                        refreshLayout.finishLoadMore();
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });


        hylAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                finish();
            }
        });

        hylAddressAdapter.setAddressClickListener(new HylAddressAdapter.OnAddressClickListener() {
            @Override
            public void onAddressClick(int addressId,int pos) {
                position = pos;
                for (int i = 0; i < list.size(); i++) {

                    //isDefault 1默认地址
//                    if(list.get(i).isDefault==0) {
//                        list.get(i).isDefault = 1;
//                        getDefaultAddress(addressId);
//                    }else {
//                        list.get(i).isDefault = 0;
//                        getDefaultAddress(addressId);
//                    }
                    if(i == position) {
                        if(list.get(i).isDefault == 0) {
                            list.get(i).isDefault = 1;
                            getDefaultAddress(addressId);
                        }
                    } else {
                        list.get(i).isDefault = 0;
                        getDefaultAddress(addressId);
                    }
                }

            }

            //删除地址
            @Override
            public void onAddressDeleteClick(int addressId) {
                deleteAddress(addressId);
            }

            //编辑地址
            @Override
            public void onAddressEditClick(int addressId,int pos) {
                Intent intent = new Intent(mContext,EditAddressActivity.class);
                intent.putExtra("addressId",addressId);
                startActivityForResult(intent,1);
            }
        });
    }


    /**
     * 从编辑出来
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getAddressList();
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
                            hylAddressAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 删除地址
     */
    private void deleteAddress(int addressId) {
        AddressApi.AddressDelete(mContext,addressId)
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
                            getAddressList();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        rl_add_address.setOnClickListener(this);
    }


    /**
     * 获取地址列表
     * @param
     */
    int pageNum = 1;
    int pageSize = 10;
    List<HylAddressListModel.DataBean.ListBean> list = new ArrayList<>();
    HylAddressListModel hylAddressListModels;
    private void getAddressList() {
        AddressApi.AddressList(mContext,pageNum,pageSize)
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
                        if (hylAddressListModel.getCode()==1) {
                            if(hylAddressListModel.getData()!=null) {
                                hylAddressListModels = hylAddressListModel;
                                if(hylAddressListModel.getData().getList()!=null&& hylAddressListModel.getData().getList().size()>0) {
                                    list.clear();
                                    list.addAll(hylAddressListModel.getData().getList());
                                    hylAddressAdapter.notifyDataSetChanged();
                                }else {
                                    hylAddressAdapter.setEmptyView(emptyView);
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAddressListModel.getMessage());
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

            case R.id.rl_add_address:
                Intent intent = new Intent(mContext,HylAddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 编辑默认地址的回调
     * @param addAddressHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addAddress(AddAddressHylEvent addAddressHylEvent) {
        getAddressList();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    public static Date getNowDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStrings = formatters.format(currentTimes);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatters.parse(dateStrings, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }

}
