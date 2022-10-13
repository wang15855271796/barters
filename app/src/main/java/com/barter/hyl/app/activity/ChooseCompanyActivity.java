package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.app.event.CompanyEvent;
import com.barter.app.model.CompanyListModel;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.CompanyListAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.TipsModel;
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

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class ChooseCompanyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_long)
    TextView tv_long;
    @BindView(R.id.tv_short)
    TextView tv_short;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    CompanyListAdapter companyListAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.choose_company_activity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        companyListAdapter = new CompanyListAdapter(R.layout.item_company,list);
        recyclerView.setAdapter(companyListAdapter);
        companyListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                companyListAdapter.setSelectionPos(position);
                companyId = list.get(position).getCompanyId();
                companyListAdapter.notifyDataSetChanged();
            }
        });
        getCompanyList();
    }

    @Override
    public void setClickListener() {
        ll_add.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_add:
                Intent intent = new Intent(mActivity,AddCompanyActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_sure:
                getFullTips(companyId);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCompanyList(CompanyEvent companyEvent) {
        getCompanyList();
    }

    /**
     * 更改企业
     * @param companyId
     */
    private void getFullTips(String companyId) {
        MyInfoApi.changeCompany(mContext,companyId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            EventBus.getDefault().post(new ChangeAccountHylEvent());
                            finish();
                        }else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    /**
     * 获取公司列表
     */
    String companyId;
    List<CompanyListModel.DataBean> list = new ArrayList<>();
    private void getCompanyList() {
        MyInfoApi.companyChoose(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CompanyListModel companyListModel) {
                        if(companyListModel.getCode()==1) {
                            list.clear();
                            if(companyListModel.getData()!=null&&companyListModel.getData().size()>0) {
                                List<CompanyListModel.DataBean> data = companyListModel.getData();
                                for (int i = 0; i < data.size(); i++) {
                                    if(data.get(i).getFlag()==1) {
                                        tv_short.setText(data.get(i).getShortName());
                                        tv_long.setText(data.get(i).getCompanyName());
                                        companyId = data.get(i).getCompanyId();
                                        if(data.get(i).getEnabled()==0) {
                                            if(data.get(i).getCustomerPhone()!=null && !data.get(i).getCustomerPhone().equals("")) {
                                                tv_phone.setText("客服电话："+data.get(i).getCustomerPhone());
                                                tv_phone.setVisibility(View.VISIBLE);
                                            }else {
                                                tv_phone.setVisibility(View.GONE);
                                            }
                                        }else {
                                            tv_phone.setVisibility(View.GONE);
                                        }

                                        if(data.get(i).getCompanyEnabled()==0) {
                                            tv_desc.setText("当前企业(企业已停用)");
                                        }else {
                                            tv_desc.setText("当前企业");
                                        }
                                    }else {
                                        list.add(data.get(i));
                                    }
                                }
                                companyListAdapter.notifyDataSetChanged();
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,companyListModel.getMessage());
                        }
                    }
                });
    }

}
