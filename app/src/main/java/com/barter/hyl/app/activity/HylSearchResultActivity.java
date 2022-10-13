package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.iv_all)
    ImageView iv_all;
    @BindView(R.id.ll_sale)
    LinearLayout ll_sale;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.iv_sale)
    ImageView iv_sale;
    @BindView(R.id.ll_price)
    LinearLayout ll_price;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.iv_price)
    ImageView iv_price;
    @BindView(R.id.iv_direction)
    ImageView iv_direction;
    String searchWord;
    HylSearchResultAdapter hylSearchResultAdapter;
    int pageNum = 1;
    int pageSize = 10;
    int saleDesc = 0;
    int priceDesc = 0;
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
            public void onAddClick(String mainId,int position) {
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
            }
        });

        hylSearchResultAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                getRecommend(searchWord);
            }
        });
        tv_activity_result.setText(searchWord);
        ptr_layout.autoRefresh();
    }

    @Override
    public void setClickListener() {
        ll_back.setOnClickListener(this);
        tv_activity_result.setOnClickListener(this);
        rl_num.setOnClickListener(this);
        ll_all.setOnClickListener(this);
        ll_sale.setOnClickListener(this);
        ll_price.setOnClickListener(this);
    }

    boolean isAll;
    boolean isSale;
    int isPrice = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all:
                isAll = !isAll;
                saleDesc = 0;
                priceDesc = 0;

                isSale = false;
                tv_sale.setTextColor(Color.parseColor("#333333"));
                iv_sale.setImageResource(R.mipmap.icon_sale);

                tv_price.setTextColor(Color.parseColor("#333333"));
                iv_price.setImageResource(R.mipmap.icon_price);
                iv_direction.setImageResource(R.mipmap.icon_default);

                if(!isAll) {
                    tv_all.setTextColor(Color.parseColor("#333333"));
                    iv_all.setImageResource(R.mipmap.icon_all_un);
                }else {
                    iv_all.setImageResource(R.mipmap.icon_all_en);
                    tv_all.setTextColor(Color.parseColor("#FF2925"));
                }
                pageNum = 1;
                ptr_layout.autoRefresh();
                break;

            case R.id.ll_sale:
                isSale = !isSale;
                priceDesc = 0;
                tv_price.setTextColor(Color.parseColor("#333333"));
                iv_price.setImageResource(R.mipmap.icon_price);
                iv_direction.setImageResource(R.mipmap.icon_default);

                tv_all.setTextColor(Color.parseColor("#333333"));
                iv_all.setImageResource(R.mipmap.icon_all_un);

                if(!isSale) {
                    tv_sale.setTextColor(Color.parseColor("#333333"));
                    iv_sale.setImageResource(R.mipmap.icon_sale);
                    saleDesc = 0;
                }else {
                    saleDesc = 2;
                    iv_sale.setImageResource(R.mipmap.icon_sale_en);
                    tv_sale.setTextColor(Color.parseColor("#FF2925"));
                }
                pageNum = 1;
                ptr_layout.autoRefresh();
                break;

            case R.id.ll_price:
                isPrice++;

                tv_sale.setTextColor(Color.parseColor("#333333"));
                iv_sale.setImageResource(R.mipmap.icon_sale);
                saleDesc = 0;

                tv_all.setTextColor(Color.parseColor("#333333"));
                iv_all.setImageResource(R.mipmap.icon_all_un);

                if(isPrice%3==0) {
                    priceDesc = 0;
                    tv_price.setTextColor(Color.parseColor("#333333"));
                    iv_price.setImageResource(R.mipmap.icon_price);
                    iv_direction.setImageResource(R.mipmap.icon_default);
                }else if(isPrice%3==1){
                    priceDesc = 1;
                    iv_price.setImageResource(R.mipmap.icon_price_en);
                    tv_price.setTextColor(Color.parseColor("#FF2925"));
                    iv_direction.setImageResource(R.mipmap.icon_up);
                }else {
                    priceDesc = 2;
                    iv_price.setImageResource(R.mipmap.icon_price_en);
                    tv_price.setTextColor(Color.parseColor("#FF2925"));
                    iv_direction.setImageResource(R.mipmap.icon_down);
                }
                pageNum = 1;
                ptr_layout.autoRefresh();
                break;
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
        DetailApi.getResultList(mContext,pageNum,pageSize,searchWord,saleDesc,priceDesc)
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

        ptr_layout.refreshComplete();
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
