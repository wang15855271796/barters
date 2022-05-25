package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylFullListAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylFullListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.MyScrollView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * Created by ${王涛} on 2021/8/6
 */
public class HylFullListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_back1)
    ImageView iv_back1;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    HylFullListAdapter fullAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full_list_hyl);
    }

    private int fadingHeight = 600;
    @Override
    public void setViewData() {
        setTranslucentStatus();
        getFullList();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        fullAdapter = new HylFullListAdapter(R.layout.item_full_hyl,list);
        recyclerView.setAdapter(fullAdapter);

        fullAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, FullActiveActivity.class);
                intent.putExtra("fullId",list.get(position).getFullId());
                mContext.startActivity(intent);
            }
        });

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                getFullList();
                smart.finishRefresh();
            }
        });
    }



    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);

        scrollView.setScrollChangeListener(new MyScrollView.ScrollChangedListener() {
            @Override
            public void onScrollChangedListener(int x, int y, int oldX, int oldY) {
                Log.d("sfdsfsfs...",y+"aa");
                if (y > fadingHeight) {
                    y = fadingHeight; // 当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可

                } else if (y < 0) {
                    y = 0;
                }else {

                }

                if(y>0) {
                    iv_back1.setVisibility(View.GONE);
                }else if(y==0) {
                    iv_back1.setVisibility(View.VISIBLE);
                }
                float scale = (float) y / 255;
                ll_title.setAlpha(scale);
            }
        });
    }

    protected void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 组合数据
     */
    List<HylFullListModel.DataBean> list = new ArrayList<>();
    private void getFullList() {
        HomeApi.getFullList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylFullListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylFullListModel hylFullListModel) {
                        if (hylFullListModel.getCode()==1) {
                            if(hylFullListModel.getData()!=null) {
                                list.clear();
                                list.addAll(hylFullListModel.getData());
                                fullAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylFullListModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_back1:
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
