package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.MyIssueAdapter;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.InfoListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IssueActivity extends BaseActivity {
    @BindView(R.id.tv_issue)
    TextView tv_issue;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    List<InfoListModel.DataBean.ListBean> list = new ArrayList<>();
    MyIssueAdapter myIssueAdapter;
    int pageNum = 1;
    int pageSize = 10;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_issue);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        myIssueAdapter = new MyIssueAdapter(R.layout.item_issue,list);
        recyclerView.setAdapter(myIssueAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                pageNum = 1;
                getCityList(1,10);
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(infoListModels.getData() != null) {
                    if(infoListModels.getData().isHasNextPage()) {
                        pageNum++;
                        getCityList(pageNum, 10);
                        refreshLayout.finishLoadMore();      //加载完成
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,IssueInfoActivity.class);
                startActivity(intent);
            }
        });
        myIssueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        getCityList(1,10);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deletedShop(DeletedShopEvent event) {
        refreshLayout.autoRefresh();

    }

    @Override
    public void setClickEvent() {

    }

    InfoListModel infoListModels;
    private void getCityList(int pageNum,int pageSize) {
        InfoListAPI.getMyList(mActivity,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoListModel infoListModel) {
                        if (infoListModel.isSuccess()) {
                            if(infoListModel.getData()!=null) {
                                infoListModels = infoListModel;
                                List<InfoListModel.DataBean.ListBean> lists = infoListModel.getData().getList();
                                list.addAll(lists);
                                myIssueAdapter.notifyDataSetChanged();

                                if(lists.size()>0) {
                                    ll_empty.setVisibility(View.GONE);
                                }else {
                                    ll_empty.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            AppHelper.showMsg(mActivity, infoListModel.getMessage());
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myShop(MyShopEvent event) {
        refreshLayout.autoRefresh();
    }
}
