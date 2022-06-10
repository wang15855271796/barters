package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylAddressListsActivity;
import com.barter.hyl.app.activity.EditAddressActivity;
import com.barter.hyl.app.adapter.HylChooseAddresssAdapter;
import com.barter.hyl.app.api.AddressApi;
import com.barter.hyl.app.event.SetDefaultHylEvent;
import com.barter.hyl.app.model.HylAddressListModel;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/4/23
 */
public class ChooseAddressDialog extends Dialog {

    Context mContext;
    View view;
    private RecyclerView recyclerView;
    RelativeLayout rl_add_address;
    HylChooseAddresssAdapter addressAdapter;
    public ChooseAddressDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        mContext = context;
        initView();

    }

    private int defaultId = -1;
    private void initView() {
        view = View.inflate(mContext, R.layout.dialog_choose_addresss_hyl, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(view);
        recyclerView = view.findViewById(R.id.recyclerView);
        rl_add_address = view.findViewById(R.id.rl_add_address);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(mContext);
        getWindow().setAttributes(attributes);
        getAddressList();
        addressAdapter = new HylChooseAddresssAdapter(R.layout.item_dialog_address_hyl, list, new HylChooseAddresssAdapter.Onclick() {
            @Override
            public void jump(int position) {
                Intent intent = new Intent(mContext,EditAddressActivity.class);
                intent.putExtra("addressId",list.get(position).getAddressId());
                mContext.startActivity(intent);
                dismiss();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(addressAdapter);

        rl_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = HylAddressListsActivity.getIntent(mContext, HylAddressListsActivity.class);
                mContext.startActivity(intent1);
                dismiss();
            }
        });


        addressAdapter.setOnItemChangeClickListener(new HylChooseAddresssAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(View view, int position, String flag) {

                if (flag.equals("default")) {
                    //这里只是表面上地显示成这个地址为默认地址,要退出这个界面的时候调接口告知后台哪个变成默认地址了
                    for (int i = 0; i < list.size(); i++) {
                        if (i == position) {
                            if (list.get(i).isDefault == 1) {
                                //原来就是默认地址,这里点击没有效果,原来也设置了原来是默认地址这里就没有效果
                                //点击原来的默认地址,不会有操作,跳出界面的时候也不会有调接口操作
                            } else if (list.get(i).isDefault == 0) {
                                //原来不是默认地址,可以点击
                                //一旦点击,这个即变成默认地址了
                                //关键是,不能让用户点击一次就调一次接口重新刷新列表,需要在用户准备跳出这个界面的时候调接口
                                list.get(i).isDefault = 1;
                                //这里代表着切换了默认地址
                                defaultId = list.get(i).getAddressId();

                                getDefaultAddress(defaultId);

                                dismiss();
                            }
                        } else {
                            list.get(i).isDefault = 0;
                        }
                    }
                    addressAdapter.notifyDataSetChanged();
                }
            }
        });

        addressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                getDefaultAddress(list.get(position).getAddressId());
            }
        });


    }
    List<HylAddressListModel.DataBean.ListBean> list = new ArrayList<>();
    HylAddressListModel hylAddressListModels;
    private void getAddressList() {
        AddressApi.AddressList(mContext,1,100)
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
                                list.clear();
                                list.addAll(hylAddressListModel.getData().getList());
                                addressAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAddressListModel.getMessage());
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
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

}
