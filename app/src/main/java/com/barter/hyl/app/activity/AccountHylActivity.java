package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class AccountHylActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_select)
    TextView tv_select;
//    @BindView(R.id.ll_account)
//    LinearLayout ll_account;
//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
    int pageNum = 1;
    int pageSize = 6;
    int isrefreshormore = 1;//1刷新  2加载
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_account_hyl);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickListener() {
        tv_title.setText("我的账单");
        iv_back.setOnClickListener(this);
        tv_select.setOnClickListener(this);
//        getMyAccount();
    }

    /**
     * 获取我的账单
     */
    String payChannel;
//    private void getMyAccount() {
//        OrderApi.myAccount(mContext,pageNum,pageSize,payChannel)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseModel baseModel) {
//                        if (baseModel.success) {
//                            tvOrderTitle.setVisibility(View.GONE);
//                            tvOrderContent.setVisibility(View.GONE);
//                            iv_cancel_return.setVisibility(View.GONE);
//                            tv_return_success.setVisibility(View.VISIBLE);
//                            tv_return_success.setText("申请已撤销");
//                            AppHelper.showMsg(mContext, "撤销成功");
//
//                            getReturnDetail();
//                        } else {
//                            AppHelper.showMsg(mContext, baseModel.message);
//                        }
//                    }
//                });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_select:
                //筛选弹窗
//                showSelectPup();
                break;
        }
    }

    //筛选弹窗
    PopupWindow popupWindow;
    private boolean isSelected;
    private String recordType;
//    private void showSelectPup() {
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View vPopupWindow = inflater.inflate(R.layout.popup_select, null, false);//引入弹窗布局
//        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
//        //设置背景透明
//        //  addBackground();
//
//        //设置进出动画
//        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
//
//        //引入依附的布局
//        //  View parentView = LayoutInflater.from(mContext).inflate(R.layout.activity_my_wallet_detail_hyl, null);
//        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
//
//
//        RecyclerView rl_select = vPopupWindow.findViewById(R.id.rl_select);
//        TextView tv_cancel = vPopupWindow.findViewById(R.id.tv_cancel);
//        TextView tv_ok = vPopupWindow.findViewById(R.id.tv_ok);
//        HylSearchAccountAdapter madater;
//        tv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//
//        rl_select.setLayoutManager(new GridLayoutManager(mContext, 3));
//        madater = new HylSearchAccountAdapter(R.layout.search_list, mList1);
//
//        rl_select.setAdapter(madater);
//
//        popupWindow.showAsDropDown(ll_account, 0, 0);
//
//
//        madater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                madater.selectPosition(position);
//                isSelected = true;
//                recordType = mList1.get(position).getKey();
//            }
//        });
//
//        tv_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isSelected) {
//                    isSelected = false;
//                    popupWindow.dismiss();
//                    pageNum = 1;
//                    mListData.clear();
//                    isrefreshormore = 1;
//                    refreshLayout.autoRefresh();
//                    getWallertRecord(recordType,year,month,"",showType,null);
//                    requsetPrice(recordType, year, month, phone, walletRecordChannelType,1);
//
//                } else {
//                    AppHelper.showMsg(mContext, "请选择筛选类型");
//                }
//            }
//        });
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                bgAlpha(1f);
//            }
//        });
//    }

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
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
