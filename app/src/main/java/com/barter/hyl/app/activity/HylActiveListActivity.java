package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylSkillListAdapter;
import com.barter.hyl.app.adapter.HylSkillTimeAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylSkillListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

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
 * Created by ${王涛} on 2021/8/31
 */
public class HylActiveListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.rv_time)
    RecyclerView rv_time;
    @BindView(R.id.rv_skill)
    RecyclerView rv_skill;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;
    HylSkillTimeAdapter hylSkillTimeAdapter;
    HylSkillListAdapter hylSkillListAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_active_list_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("秒杀专区");
        iv_image.setVisibility(View.VISIBLE);
        getCartNum();
        getSkillList();
        hylSkillTimeAdapter = new HylSkillTimeAdapter(R.layout.item_skill_time_hyl,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
        rv_time.setLayoutManager(linearLayoutManager);
        rv_time.setAdapter(hylSkillTimeAdapter);

        hylSkillTimeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                skillList.clear();
                skillList.addAll(hylSkillListModel1.getData().get(position).getActives());
                hylSkillListAdapter.notifyDataSetChanged();
            }
        });

        rv_skill.setLayoutManager(new LinearLayoutManager(mActivity));
        hylSkillListAdapter = new HylSkillListAdapter(R.layout.item_skill_hyl, list,skillList, new HylSkillListAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int activeType, int activeId, int num) {
                addCartNum(activeType,-100,activeId,1);
            }
        });

        rv_skill.setAdapter(hylSkillListAdapter);

        hylSkillListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,HylActiveDetailActivity.class);
                intent.putExtra("activeId",skillList.get(position).getActiveId()+"");
                startActivity(intent);
            }
        });

    }

    @Override
    public void setClickListener() {
        ll_back.setOnClickListener(this);
        rl_cart.setOnClickListener(this);
    }

    /**
     * 秒杀数据
     */
    List<HylSkillListModel.DataBean> list = new ArrayList<>();
    List<HylSkillListModel.DataBean.ActivesBean> skillList = new ArrayList<>();
    HylSkillListModel hylSkillListModel1;
    private void getSkillList() {
        HomeApi.getSkillList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSkillListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylSkillListModel hylSkillListModel) {
                        if (hylSkillListModel.getCode()==1) {
                            if(hylSkillListModel.getData()!=null) {
                                hylSkillListModel1 = hylSkillListModel;
                                list.clear();
                                list.addAll(hylSkillListModel.getData());
                                skillList.clear();

                                skillList.addAll(hylSkillListModel.getData().get(0).getActives());
                                hylSkillListAdapter.notifyDataSetChanged();
                                hylSkillTimeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylSkillListModel.getMessage());
                        }
                    }
                });
    }

    //加入购物车
    private void addCartNum(int businessType, int priceId, int businessId, final int num) {
        DetailApi.getAddCarts(mContext,businessType,priceId,businessId,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylLoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylLoginModel hylLoginModel) {
                        if (hylLoginModel.code==1) {
                            EventBus.getDefault().post(new CartListHylEvent());
                            getCartNum();
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_cart:
                //跳转到购物车界面
//                Intent intent = new Intent(mActivity,MainActivity.class);
//                startActivity(intent);
                EventBus.getDefault().post(new JumpCartHylEvent());
                finish();
                break;
        }
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.order_red));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    private void getCartNum() {
        DetailApi.getCartNum(mActivity,0)
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
                                tv_number.setVisibility(View.VISIBLE);
                                tv_number.setText(hylCartNumModel.getData().getProdNum()+"");
                            }else {
                                tv_number.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylCartNumModel.getMessage());
                        }
                    }
                });
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
