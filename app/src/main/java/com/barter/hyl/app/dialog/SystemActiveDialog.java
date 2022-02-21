package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylActiveListActivity;
import com.barter.hyl.app.activity.HylCommonH5Activity;
import com.barter.hyl.app.activity.HylMyCouponActivity;
import com.barter.hyl.app.activity.HylTeamActivity;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HomeBaseModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.bumptech.glide.Glide;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/14
 */
public class SystemActiveDialog extends Dialog {

    Context context;
    ImageView popImageView;
    ImageView buttonDelete;
    HomeBaseModel.DataBean.PopUpsBean popUpsBean;

    public SystemActiveDialog(@NonNull Context mContext, HomeBaseModel.DataBean.PopUpsBean popUpsBean) {
        super(mContext, R.style.promptDialog);
        setContentView(R.layout.system_active_dialog);
        this.context = mContext;
        this.popUpsBean = popUpsBean;
        initView();
    }

    private void initView() {
        popImageView = findViewById(R.id.popImageView);
        buttonDelete = findViewById(R.id.buttonDelete);
        Glide.with(context).load(popUpsBean.getUrl()).into(popImageView);

        popImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrivacyRead(popUpsBean.getId(),popUpsBean.getType());
                showSystemDialog(popUpsBean.getJumpType());
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrivacyRead(popUpsBean.getId(),popUpsBean.getType());
            }
        });
    }


    private void showSystemDialog(String jumpType) {
        switch (jumpType) {
            case "fullGiftSend"://满赠界面
                Intent intent1 = new Intent(context,HylMyCouponActivity.class);
                context.startActivity(intent1);
                dismiss();
                break;
            case "deduct": // 优惠券页面
                Intent intent2 = new Intent(context,HylMyCouponActivity.class);
                context.startActivity(intent2);
                dismiss();
                break;

            case "seckill": //秒杀页面
                Intent intent3 = new Intent(context,HylActiveListActivity.class);
                context.startActivity(intent3);
                dismiss();
                break;
            case "group": //团购页面
                Intent intent4 = new Intent(context, HylTeamActivity.class);
                context.startActivity(intent4);
                dismiss();
                break;
            case "disable":
                popImageView.setEnabled(false);
                break;

            default:
                context.startActivity(HylCommonH5Activity.getIntent(context, HylCommonH5Activity.class,popUpsBean.getDetailUrl()));
                break;


        }
        dismiss();
    }

    /**
     * 是否已读
     * @param id
     * @param type
     */
    private void getPrivacyRead(int id,int type) {
        HomeApi.getRead(getContext(),id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            dismiss();
                        } else {
                            ToastUtil.showSuccessMsg(getContext(), baseModel.message);
                        }
                    }
                });
    }

}
