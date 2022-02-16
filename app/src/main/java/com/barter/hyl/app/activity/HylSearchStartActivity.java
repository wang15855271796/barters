package com.barter.hyl.app.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylSearchRecommendAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.model.HylSearchListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.FlowLayout;
import com.barter.hyl.app.view.SearchView;
import com.barter.hyl.app.view.TagAdapter;
import com.barter.hyl.app.view.TagFlowLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/13.
 */

public class HylSearchStartActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnClickListener, SearchView.SearchViewListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fl_search)
    TagFlowLayout fl_search;
    @BindView(R.id.tv_arrow)
    TextView tv_arrow;
    @BindView(R.id.et_goods)
    EditText et_goods;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.searchView)
    SearchView searchView;
    HylSearchRecommendAdapter hylSearchRecommendAdapter;
    private List<String> mListHistory = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_seach_start_hyl);
    }

    TagAdapter mRecordsAdapter;
    @Override
    public void setViewData() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        hylSearchRecommendAdapter = new HylSearchRecommendAdapter(R.layout.item_spec_hyl,list);
        recyclerView.setAdapter(hylSearchRecommendAdapter);

        String history = UserInfoHelper.getUserSearchHistory(mContext);
        if (StringHelper.notEmptyAndNull(history)) {
            for (Object o : history.split(",")) {
                mListHistory.add((String) o);
            }
            iv_clear.setVisibility(View.VISIBLE);
        } else {
            iv_clear.setVisibility(View.GONE);
        }

        hylSearchRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,HylSearchResultActivity.class);
                intent.putExtra("searchWord",list.get(position));
                startActivity(intent);
                savaHistory(list.get(position));
                finish();
            }
        });

        getRecommend();

        searchView.setSearchViewListener(this);
        mRecordsAdapter = new TagAdapter<String>(mListHistory) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_search_history,fl_search, false);
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
                UserInfoHelper.saveUserContent(mContext, pos);
                Intent intent = new Intent(mContext, HylSearchResultActivity.class);
                intent.putExtra("searchWord",pos);

                savaHistory(pos);
                startActivity(intent);
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
        editKeyBoard();
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
        UserInfoHelper.saveUserHistory(mContext, text);

    }

    // 调用键盘弹出搜索按钮键盘
    protected void editKeyBoard() {
        et_goods.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (et_goods.getText().toString().isEmpty()) {
                        //传参和输入都是空
                        UserInfoHelper.saveUserHistory(mContext, et_goods.getText().toString());
                        AppHelper.showMsg(mContext, "请输入商品名称");
                    } else if (!et_goods.getText().toString().isEmpty()) {
                        UserInfoHelper.saveUserContent(mContext, et_goods.getText().toString());
                        //输入不为空,优先输入
                        Intent intent = new Intent(mContext, HylSearchResultActivity.class);
                        intent.putExtra("searchWord",et_goods.getText().toString());
                        startActivity(intent);
                        finish();
                        savaHistory(et_goods.getText().toString());

                    } else {
                        //默认关键字
                        Intent intent = new Intent(mContext, HylSearchResultActivity.class);
                        intent.putExtra("searchWord",et_goods.getText().toString());
                        startActivity(intent);
                        finish();
                        savaHistory(et_goods.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    //    判断EditText是否市区焦点
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            et_goods.setHint("请输入商品名称");
        }else {
            et_goods.setHint("");
        }
    }

    @Override
    public void setClickListener() {
        iv_clear.setOnClickListener(this);
    }

    /**
     * 获取推荐产品
     */
    List<String> list = new ArrayList<>();
    private void getRecommend() {
        DetailApi.getRecommendList(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSearchListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylSearchListModel hylSearchListModel) {
                        if (hylSearchListModel.getCode()==1) {
                            list.clear();
                            list.addAll(hylSearchListModel.getData());
                            hylSearchRecommendAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylSearchListModel.getMessage());
                        }
                    }
                });
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
        mDialog.getWindow().setContentView(R.layout.dialog_clean_history_hyl);
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

    @Override
    public void onRefreshAutoComplete(String text) {

    }

    @Override
    public void onSearch(String text) {

        if (text.isEmpty()) {
            //传参和输入都是空
            AppHelper.showMsg(mContext, "请输入商品名称");

        } else if (!text.isEmpty()) {
            UserInfoHelper.saveUserContent(mContext, text);
            //输入不为空,优先输入
            UserInfoHelper.saveUserHistory(mContext,text);
            Intent intent = new Intent(mContext, HylSearchResultActivity.class);
            intent.putExtra("searchWord", text);
            startActivity(intent);
            finish();
            savaHistory(text);
        }

    }


}
