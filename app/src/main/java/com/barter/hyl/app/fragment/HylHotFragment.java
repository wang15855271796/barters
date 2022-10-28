package com.barter.hyl.app.fragment;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonGoodsActivity;
import com.barter.hyl.app.adapter.HylHotAdapter;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.dialog.CommonDialog;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.event.GoTopEvent;
import com.barter.hyl.app.event.HotHylEvent;
import com.barter.hyl.app.model.HylActiviteModel;
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
 * Created by ${王涛} on 2021/8/4
 */
public class HylHotFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    int pageNum = 1;
    int pageSize = 10;
    View emptyView;

    public static HylHotFragment getInstance() {
        HylHotFragment fragment = new HylHotFragment();

        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.common_fragment_hyl;
    }

    HylHotAdapter hylHotAdapter;
    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        smart.autoRefresh();
        smart.setEnableLoadMore(false);
        emptyView = View.inflate(mActivity, R.layout.no_view, null);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));
        hylHotAdapter = new HylHotAdapter(R.layout.item_common_hyl, list, new HylHotAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int position) {
                CommonDialog commonDialog = new CommonDialog(mActivity,list.get(position));
                commonDialog.show();
            }
        });
        recyclerView.setAdapter(hylHotAdapter);

        hylHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,HylCommonGoodsActivity.class);
                intent.putExtra("mainId",list.get(position).getMaidId());
                startActivity(intent);
            }
        });




        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                hylHotAdapter.notifyDataSetChanged();
                getHot();
                smart.finishRefresh();
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hylActiviteModels.getData()!=null) {
                    if(hylActiviteModels.getData().isHasNextPage()) {
                        pageNum++;
                        getHot();
                        refreshLayout.finishLoadMore();
                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }else {
                    refreshLayout.finishLoadMore();
                }
            }
        });

    }

    HylActiviteModel hylActiviteModels;
    List<HylActiviteModel.DataBean.ListBean> list = new ArrayList<>();
    private void getHot() {
        HomeApi.getHot(mActivity,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylActiviteModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylActiviteModel hylActiviteModel) {
                        if (hylActiviteModel.getCode()==1) {
                            if(hylActiviteModel.getData()!=null&& hylActiviteModel.getData().getList().size()>0) {
                                hylActiviteModels = hylActiviteModel;
                                list.addAll(hylActiviteModel.getData().getList());
                                smart.setEnableLoadMore(true);
                            }else {
                                smart.setEnableLoadMore(false);
                                hylHotAdapter.setEmptyView(emptyView);
                            }
                            hylHotAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylActiviteModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 账号切换时信息更新
     * @param changeAccountHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHot(ChangeAccountHylEvent changeAccountHylEvent) {
        smart.autoRefresh();
    }

    /**
     * 处理热销列表闪退问题
     * @param hotHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHots(HotHylEvent hotHylEvent) {
        list.clear();
        hylHotAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getScrolls(GoTopEvent event) {
        recyclerView.smoothScrollToPosition(0);
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
