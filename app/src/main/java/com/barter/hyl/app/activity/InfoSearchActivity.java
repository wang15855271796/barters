package com.barter.hyl.app.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HotShopAdapter;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.SearchShopEvent;
import com.barter.hyl.app.model.RecommendModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.view.FlowLayout;
import com.barter.hyl.app.view.SearchView;
import com.barter.hyl.app.view.TagAdapter;
import com.barter.hyl.app.view.TagFlowLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoSearchActivity extends BaseActivity implements SearchView.SearchViewListener, View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.fl_search)
    TagFlowLayout fl_search;
    @BindView(R.id.tv_arrow)
    TextView tv_arrow;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    HotShopAdapter hotShopAdapter;
    private TagAdapter mRecordsAdapter;
    private List<String> mListHistory = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_seach_start_hyl);
    }

    @Override
    public void setViewData() {
        getHot();
        String history = SharedPreferencesUtil.getString(mContext,"keyWords");
        if (StringHelper.notEmptyAndNull(history)) {
            for (Object o : history.split(",")) {
                mListHistory.add((String) o);
            }
            iv_clear.setVisibility(View.VISIBLE);
        } else {
            iv_clear.setVisibility(View.GONE);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        hotShopAdapter = new HotShopAdapter(R.layout.item_spec_hyl,list);
        recyclerView.setAdapter(hotShopAdapter);

        hotShopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new SearchShopEvent(list.get(position)));
                SharedPreferencesUtil.saveString(mContext,"keyWords",list.get(position));
                savaHistory(list.get(position));
                finish();
            }
        });

        searchView.setSearchViewListener(this);

        mRecordsAdapter = new TagAdapter<String>(mListHistory) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_spec_hyl,fl_search, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };

        //view加载完成时回调
        fl_search.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isOverFlow = fl_search.isOverFlow();
                boolean isLimit = fl_search.isLimit();
                if (isLimit && isOverFlow) {
                    tv_arrow.setVisibility(View.VISIBLE);
                } else {
                    tv_arrow.setVisibility(View.GONE);
                }
            }
        });

        fl_search.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position, FlowLayout parent) {
                String pos = mListHistory.get(position);
                EventBus.getDefault().post(new SearchShopEvent(mListHistory.get(position)));
                savaHistory(pos);
                finish();
            }
        });

        tv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl_search.setLimit(false);
                mRecordsAdapter.notifyDataChanged();
            }
        });
        fl_search.setAdapter(mRecordsAdapter);


    }

    @Override
    public void setClickListener() {
        iv_clear.setOnClickListener(this);
    }

    List<String> list = new ArrayList<>();
    private void getHot() {
        InfoListAPI.getHotSearch(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecommendModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(RecommendModel recommendModel) {
                        if (recommendModel.getCode()==1) {
                            if(recommendModel.getData()!=null&&recommendModel.getData().size()>0) {
                                list.clear();
                                list.addAll(recommendModel.getData());
                                hotShopAdapter.notifyDataSetChanged();
                            }
                        } else {
                            AppHelper.showMsg(mActivity, recommendModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onRefreshAutoComplete(String text) {

    }

    @Override
    public void onSearch(String text) {
        if (text.isEmpty()) {
            EventBus.getDefault().post(new SearchShopEvent(""));
            finish();

        } else if (!text.isEmpty()) {
            //输入不为空,优先输入
            SharedPreferencesUtil.saveString(mContext,"keyWords",text);
            EventBus.getDefault().post(new SearchShopEvent(text));
            finish();
            savaHistory(text);
        }
    }

    private void savaHistory(String text) {
        //移除相同的元素
        for (int i = 0; i < mListHistory.size(); i++) {
            if (mListHistory.get(i).equals(text)) {
                mListHistory.remove(i);
            }
        }
        for (int i = 0; i < mListHistory.size(); i++) {
            text = text + "," + mListHistory.get(i);
        }
        SharedPreferencesUtil.saveString(mContext,"keyWords",text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                showCleanDialog();
                break;
        }
    }

    /*
     * 显示清除历史记录弹窗
     */
    private AlertDialog mDialog;
    private void showCleanDialog() {
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_clean_history);
        mDialog.getWindow().findViewById(R.id.tv_dialog_message_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().findViewById(R.id.tv_dialog_message_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoHelper.cleanUserSearchHistory(mContext);
                mListHistory.clear();
                iv_clear.setVisibility(View.GONE);
                mRecordsAdapter.notifyDataChanged();
                mDialog.dismiss();
            }
        });
    }

}

