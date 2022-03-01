package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.FullActiveAdapter;
import com.barter.hyl.app.adapter.FullGivenAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.dialog.CouponFullListDialog;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.CouponListsModel;
import com.barter.hyl.app.model.FullActiveDetailModel;
import com.barter.hyl.app.model.TipsModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.FlowLayout;
import com.barter.hyl.app.view.TagAdapter;
import com.barter.hyl.app.view.TagFlowLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullActiveActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rv_full_given)
    TagFlowLayout rv_full_given;
    @BindView(R.id.tv_roll)
    TextView tv_roll;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_buy)
    TextView tv_buy;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.ll_tips)
    LinearLayout ll_tips;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_more)
    TextView tv_more;
    String fullId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        fullId = getIntent().getStringExtra("fullId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full_active);
    }

    FullActiveAdapter fullActiveAdapter;
    FullGivenAdapter fullGivenAdapter;
    @Override
    public void setViewData() {
        tv_title.setText("参加满赠活动商品");
        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fullActiveAdapter = new FullActiveAdapter(R.layout.item_active_full_list,list,mActivity);
        recyclerView.setAdapter(fullActiveAdapter);

//        fullGivenAdapter = new FullGivenAdapter(R.layout.item_full_desc,sendGifts);
//        rv_full_given.setLayoutManager(new LinearLayoutManager(mContext));
//        rv_full_given.setAdapter(fullGivenAdapter);
        getActiveDetail();

//        fullGivenAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if(sendGifts.get(position).getType()==0) {
//                    Intent intent = new Intent(mContext,HylCommonGoodsActivity.class);
//                    intent.putExtra("mainId",sendGifts.get(position).getProductMainId());
//                    startActivity(intent);
//                }else {
//                    CouponFullListDialog couponFullListDialog = new CouponFullListDialog(mContext,
//                            sendGifts.get(position).getPoolNo(),sendGifts.get(position).getGiftProdUseType(),
//                            sendGifts.get(position).getName());
//                    couponFullListDialog.show();
//                }
//            }
//        });

        fullActiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,HylCommonGoodsActivity.class);
                intent.putExtra("mainId",list.get(position).getProductMainId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setClickListener() {
        tv_buy.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_more.setOnClickListener(this);
    }


    /**
     *  满赠活动详情
     */
    TagAdapter unAbleAdapter;
    TextView tv_given;
    ImageView iv_pic;
    List<FullActiveDetailModel.DataBean.ProdsBean> list = new ArrayList<>();
    List<FullActiveDetailModel.DataBean.SendGiftsBean> sendGifts = new ArrayList<>();
    private void getActiveDetail() {
        MyInfoApi.fullActiveDetail(mActivity,fullId+"")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<FullActiveDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FullActiveDetailModel fullDetailModel) {
                        if(fullDetailModel.getCode()==1) {
                            sendGifts.clear();
                            list.clear();
                            if(fullDetailModel.getData()!=null) {
                                FullActiveDetailModel.DataBean data = fullDetailModel.getData();

                                tv_roll.setText(data.getRoleDesc());
                                if(!data.getTips().getTips().equals("")&&data.getTips().getTips()!=null) {
                                    ll_tips.setVisibility(View.VISIBLE);
                                    tv_tip.setText(data.getTips().getTips());
                                }else {
                                    ll_tips.setVisibility(View.GONE);
                                }
                                if(data.getTips().getRoleType() ==0) {
                                    tv_total.setText(data.getTips().getBuyNum());
                                    tv_total_price.setText(data.getTips().getBuyAmt());
                                    tv_total_price.setVisibility(View.VISIBLE);
                                }else {
                                    tv_total.setText(data.getTips().getBuyAmt());
                                    tv_total_price.setVisibility(View.GONE);
                                }

                                sendGifts.addAll(data.getSendGifts());
                                unAbleAdapter = new TagAdapter<FullActiveDetailModel.DataBean.SendGiftsBean>(sendGifts){

                                    @Override
                                    public View getView(FlowLayout parent, int position, FullActiveDetailModel.DataBean.SendGiftsBean sendGiftsBean) {
                                        FullActiveDetailModel.DataBean.SendGiftsBean sendGiftsBean1 = sendGifts.get(position);
                                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_full_desc,rv_full_given, false);
                                        tv_given = view.findViewById(R.id.tv_given);
                                        iv_pic = view.findViewById(R.id.iv_pic);
                                        tv_given.setText(sendGiftsBean1.getGiftName()+sendGiftsBean1.getSendNum());
                                        tv_given.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(sendGiftsBean1.getType()==0) {
                                                    //赠品
                                                    iv_pic.setImageResource(R.mipmap.icon_zeng);
                                                    Intent intent = new Intent(mContext,HylCommonGoodsActivity.class);
                                                    intent.putExtra("mainId",sendGifts.get(position).getProductMainId());
                                                    startActivity(intent);
                                                }else {
                                                    //优惠券
                                                    iv_pic.setImageResource(R.mipmap.icon_quan);
                                                    CouponFullListDialog couponFullListDialog = new CouponFullListDialog(mContext,
                                                            sendGifts.get(position).getPoolNo(),sendGifts.get(position).getGiftProdUseType(),
                                                            sendGifts.get(position).getName());
                                                    couponFullListDialog.show();
                                                }
                                            }
                                        });

                                        if(sendGiftsBean1.getType()==0) {
                                            //赠品
                                            iv_pic.setImageResource(R.mipmap.icon_zeng);
                                        }else {
                                            //优惠券
                                            iv_pic.setImageResource(R.mipmap.icon_quan);
                                        }
                                        return view;
                                    }
                                };
                                rv_full_given.setAdapter(unAbleAdapter);
                                unAbleAdapter.notifyDataChanged();

                                list.addAll(data.getProds());
                                if(sendGifts.size()>4||sendGifts.size()==4) {
                                    tv_more.setVisibility(View.VISIBLE);
                                }else {
                                    tv_more.setVisibility(View.GONE);
                                }
                                fullGivenAdapter.notifyDataSetChanged();
                                fullActiveAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,fullDetailModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取满赠提示
     * @param fullId
     */
    private void getFullTips(String fullId) {
        MyInfoApi.getTips(mContext,fullId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<TipsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TipsModel tipsModel) {
                        if(tipsModel.getCode()==1) {
                            if(tipsModel.getData()!=null) {
                                TipsModel.DataBean data = tipsModel.getData();
                                if(!data.getTips().getTips().equals("")&&data.getTips().getTips()!=null) {
                                    ll_tips.setVisibility(View.VISIBLE);
                                    tv_tip.setText(data.getTips().getTips());
                                }else {
                                    ll_tips.setVisibility(View.GONE);
                                }

                                if(data.getTips().getRoleType() ==0) {
                                    tv_total.setText(data.getTips().getBuyNum());
                                    tv_total_price.setText(data.getTips().getBuyAmt());
                                    tv_total_price.setVisibility(View.VISIBLE);
                                }else {
                                    tv_total.setText(data.getTips().getBuyAmt());
                                    tv_total_price.setVisibility(View.GONE);
                                }

                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,tipsModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_buy:
                mContext.startActivity(new Intent(mContext, MainActivity.class));
                EventBus.getDefault().post(new JumpCartHylEvent());
                finish();
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_more:
                Intent intent3 = new Intent(mContext,FullActivity.class);
                intent3.putExtra("sendGifts", (Serializable) sendGifts);
                startActivity(intent3);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(CartNumHylEvent cartNumHylEvent) {
        //刷新UI
        getFullTips(fullId);
        getActiveDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
