package com.barter.hyl.app.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylCommentAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.HylCommentModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class HylCommentActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    int mainId;
    HylCommentAdapter hylCommentAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        mainId = getIntent().getIntExtra("mainId",0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_comment_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("用户评价");
        smart.autoRefresh();
        hylCommentAdapter = new HylCommentAdapter(R.layout.item_evaluation_hyl,list);
        recyclerView.setAdapter(hylCommentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getComment();
                smart.finishRefresh();
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hylCommentModels.getData()!=null) {
                    if(hylCommentModels.getData().isHasNextPage()) {
                        pageNum++;
                        getComment();
                        refreshLayout.finishLoadMore();
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
    }

    int pageNum = 1;
    int pageSize = 10;
    HylCommentModel hylCommentModels;
    List<HylCommentModel.DataBean.ListBean> list = new ArrayList<>();
    private void getComment() {
        DetailApi.commentList(mActivity,pageNum,pageSize,mainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCommentModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCommentModel hylCommentModel) {
                        if (hylCommentModel.getCode()==1) {
                            if(hylCommentModel.getData()!=null) {
                                hylCommentModels = hylCommentModel;
                                list.clear();
                                list.addAll(hylCommentModel.getData().getList());
                                hylCommentAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylCommentModel.getMessage());
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
