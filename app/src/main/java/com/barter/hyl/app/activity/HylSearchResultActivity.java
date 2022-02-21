package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylSearchResultAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.dialog.SearchDialog;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylSearchResultModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/11
 */
public class HylSearchResultActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.ptr_layout)
    PtrClassicFrameLayout ptr_layout;
    @BindView(R.id.tv_activity_result)
    TextView tv_activity_result;
    @BindView(R.id.rl_num)
    RelativeLayout rl_num;
    String searchWord;
    HylSearchResultAdapter hylSearchResultAdapter;
    int pageNum = 1;
    int pageSize = 10;
    View emptyView;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        searchWord = getIntent().getStringExtra("searchWord");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.search_result_activity);
    }

    @Override
    public void setViewData() {
        getCartNum();
        EventBus.getDefault().register(this);
        emptyView = View.inflate(mActivity, R.layout.no_view, null);
        hylSearchResultAdapter = new HylSearchResultAdapter(R.layout.item_search_hyl, list, new HylSearchResultAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int mainId,int position) {
                SearchDialog searchDialog = new SearchDialog(mActivity,list.get(position));
                searchDialog.show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(hylSearchResultAdapter);

        hylSearchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,HylCommonGoodsActivity.class);
                intent.putExtra("mainId",list.get(position).getMainId());
                startActivity(intent);
            }
        });

        getRecommend(searchWord);
        ptr_layout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                getRecommend(searchWord);
                ptr_layout.autoRefresh();
                ptr_layout.refreshComplete();
            }
        });

        hylSearchResultAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                getRecommend(searchWord);
            }
        });

        ptr_layout.autoRefresh();
    }

    @Override
    public void setClickListener() {
        ll_back.setOnClickListener(this);
        tv_activity_result.setOnClickListener(this);
        rl_num.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.tv_activity_result:
                Intent intent = new Intent(mActivity,HylSearchStartActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.rl_num:
                EventBus.getDefault().post(new JumpCartHylEvent());
                finish();
                break;
        }
    }

    /**
     * 获取搜索列表产品
     */
    List<HylSearchResultModel.DataBean.ListBean> list = new ArrayList<>();
    private void getRecommend(String searchWord) {
        DetailApi.getResultList(mContext,pageNum,pageSize,searchWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSearchResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylSearchResultModel hylSearchResultModel) {
                        if (hylSearchResultModel.getCode()==1) {
                            if(hylSearchResultModel.getData()!=null&& hylSearchResultModel.getData().getList().size()>0) {
                                ptr_layout.refreshComplete();
                                setState(hylSearchResultModel.getData());
                            }else {
                                hylSearchResultAdapter.setEmptyView(emptyView);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylSearchResultModel.getMessage());
                        }
                    }
                });
    }

    private void setState(HylSearchResultModel.DataBean data) {
        if(pageNum==1) {
            list.clear();
            list.addAll(data.getList());
            hylSearchResultAdapter.notifyDataSetChanged();
        }else {
            list.addAll(data.getList());
            hylSearchResultAdapter.notifyDataSetChanged();
        }

        if(data.isHasNextPage()) {
            hylSearchResultAdapter.loadMoreComplete();
        }else {
            hylSearchResultAdapter.loadMoreEnd();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(CartNumHylEvent cartNumHylEvent) {
        getCartNum();
    }

    /**
     * 获取角标数据
     * @param
     */
    private void getCartNum() {
        DetailApi.getCartNum(mContext,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCartNumModel hylCartNumModel) {
                        if (hylCartNumModel.getCode()==1) {
                            if(hylCartNumModel.getData().getProdNum()>0) {
                                tv_num.setVisibility(View.VISIBLE);
                                tv_num.setText(hylCartNumModel.getData().getProdNum()+"");
                            }else {
                                tv_num.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCartNumModel.getMessage());
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
