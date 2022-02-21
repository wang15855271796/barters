package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylMessageCenterAdapter;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.HylMessageModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * Created by ${王涛} on 2021/7/30
 */
public class HylMessageCenterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    List<HylMessageModel.DataBean.ListBean> list = new ArrayList<>();
    HylMessageCenterAdapter mAdapterMessageCenter;
    View emptyView;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_message_center_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("消息中心");
        emptyView = View.inflate(mActivity, R.layout.no_view, null);
        mAdapterMessageCenter = new HylMessageCenterAdapter(R.layout.item_message_center,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(mAdapterMessageCenter);
        smart.setEnableLoadMore(false);
        mAdapterMessageCenter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,HylMessageDetailActivity.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("readFlag",list.get(position).getReadFlag());
                startActivity(intent);

            }
        });

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getMessageList();
                smart.finishRefresh();
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hylMessageModels.getData()!=null) {
                    if(hylMessageModels.getData().isHasNextPage()) {
                        pageNum++;
                        getMessageList();
                        refreshLayout.finishLoadMore();
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        smart.autoRefresh();
    }




    /**
     * 获取消息列表
     */
    int pageNum = 1;
    int pageSize = 10;
    HylMessageModel hylMessageModels;
    private void getMessageList() {
        HomeApi.getMessageList(mActivity,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMessageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMessageModel hylMessageModel) {
                        if (hylMessageModel.getCode()==1) {
                            smart.setEnableLoadMore(true);
                            if(hylMessageModel.getData()!=null) {
                                hylMessageModels = hylMessageModel;
                                if(hylMessageModel.getData().getList()!=null&& hylMessageModel.getData().getList().size()>0) {
                                    list.addAll(hylMessageModel.getData().getList());
                                    mAdapterMessageCenter.notifyDataSetChanged();
                                }else {
                                    mAdapterMessageCenter.setEmptyView(emptyView);
                                }
                            }

                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylMessageModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
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
