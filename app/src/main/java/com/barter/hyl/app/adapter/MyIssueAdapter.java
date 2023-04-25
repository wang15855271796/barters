package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.IssueEditInfoActivity;
import com.barter.hyl.app.activity.ShopDetailActivity;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.event.DeletedShopEvent;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.InfoListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.RoundImageView;
import com.barter.hyl.app.view.StringSpecialHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdk.mobile.manager.login.NoDoubleClickListener;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/4
 */
public class MyIssueAdapter extends BaseQuickAdapter<InfoListModel.DataBean.ListBean, BaseViewHolder> {

    public MyIssueAdapter(int layoutResId, @Nullable List<InfoListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfoListModel.DataBean.ListBean item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_state =  helper.getView(R.id.iv_state);
        TextView tv_look = helper.getView(R.id.tv_look);
        TextView tv_reason = helper.getView(R.id.tv_reason);
        ImageView iv_player = helper.getView(R.id.iv_player);
        TextView tv_status2 = helper.getView(R.id.tv_status2);
        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopDetailActivity.class);
                intent.putExtra("msgId",item.getMsgId());
                mContext.startActivity(intent);
            }
        });

        if(item.getVideoCoverUrl()!=null) {
            iv_player.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getVideoCoverUrl()).into(iv_pic);
        }else {
            iv_player.setVisibility(View.GONE);
            if(item.getPictureList().size()>0) {
                for (int i = 0; i < item.getPictureList().size(); i++) {
                    Glide.with(mContext).load(item.getPictureList().get(i)).into(iv_pic);
                }
            }else {
                iv_pic.setImageResource(R.mipmap.icon_empty);
            }
        }


        TextView tv_deleted = helper.getView(R.id.tv_deleted);
        if(item.getMsgType().equals("1")) {
            helper.setText(R.id.tv_title,"店铺转让");
        }else if(item.getMsgType().equals("2")) {
            helper.setText(R.id.tv_title,"器具转让");
        }else if(item.getMsgType().equals("3")) {
            helper.setText(R.id.tv_title,"厨师招聘");
        }else if(item.getMsgType().equals("4")) {
            helper.setText(R.id.tv_title,"其他信息");
        }

        helper.setText(R.id.tv_desc,item.getContent());
        helper.setText(R.id.tv_time,item.getCreateTime());
        helper.setText(R.id.tv_num,item.getBrowseNum()+"人看过");
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_edit = helper.getView(R.id.tv_edit);
        if(item.getCheckStatus().equals("1")) {
            //已审核
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("审核已通过");
            iv_state.setBackgroundResource(R.mipmap.ic_pass);
            tv_edit.setVisibility(View.GONE);
            tv_look.setVisibility(View.VISIBLE);
            tv_reason.setVisibility(View.GONE);
            if(item.isDeleteFlag()) {
                tv_status2.setVisibility(View.VISIBLE);
                tv_deleted.setVisibility(View.GONE);
            }else {
                tv_deleted.setVisibility(View.VISIBLE);
                tv_status2.setVisibility(View.GONE);
            }
        }else if(item.getCheckStatus().equals("0")) {
            //待审核
            tv_status.setVisibility(View.VISIBLE);
            iv_state.setBackgroundResource(R.mipmap.ic_verify);
            tv_status.setText("信息正在审核中");
            tv_edit.setVisibility(View.VISIBLE);
            tv_look.setVisibility(View.GONE);
            tv_reason.setVisibility(View.GONE);
            tv_status2.setVisibility(View.GONE);
        }else if(item.getCheckStatus().equals("2")){
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("审核未通过");
            iv_state.setBackgroundResource(R.mipmap.ic_unpass);
            tv_edit.setVisibility(View.GONE);
            tv_look.setVisibility(View.VISIBLE);
            tv_reason.setVisibility(View.VISIBLE);
            tv_status2.setVisibility(View.GONE);
            tv_reason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showReason(item);
                }
            });
        }
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, IssueEditInfoActivity.class);
                intent.putExtra("msgId",item.getMsgId());
                intent.putExtra("msgType",item.getMsgType());
                mContext.startActivity(intent);
            }
        });
        tv_deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteCollection(item);
            }
        });
    }

    private void showReason(InfoListModel.DataBean.ListBean item) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.delete_reason);
        TextView tv_reason = (TextView) window.findViewById(R.id.tv_reason);
        TextView tv_sure = (TextView) window.findViewById(R.id.tv_sure);
        tv_reason.setText(item.getRefuseReason());

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }

    private void showDeleteCollection(InfoListModel.DataBean.ListBean item) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_shop);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_deleted);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCityList(item);
                alertDialog.dismiss();
            }
        });
    }

    private void getCityList(InfoListModel.DataBean.ListBean item) {
        InfoListAPI.InfoDeleted(mContext,item.getMsgId())
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
                    public void onNext(HylLoginModel infoListModel) {
                        if (infoListModel.success) {
                            ToastUtil.showSuccessMsg(mContext,infoListModel.message);
                            EventBus.getDefault().post(new DeletedShopEvent());
                        } else {
                            AppHelper.showMsg(mContext, infoListModel.message);
                        }
                    }
                });
    }

}
