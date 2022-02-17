package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;

public class ApplyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_business)
    ImageView iv_business;
    @BindView(R.id.iv_allow)
    ImageView iv_allow;
    @BindView(R.id.iv_card1)
    ImageView iv_card1;
    @BindView(R.id.iv_card2)
    ImageView iv_card2;
    @BindView(R.id.iv_card3)
    ImageView iv_card3;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_short_name)
    EditText et_short_name;
    @BindView(R.id.et_contact_phone)
    EditText et_contact_phone;
    @BindView(R.id.et_contact_user)
    EditText et_contact_user;
    @BindView(R.id.et_user_card)
    EditText et_user_card;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_apply);
    }

    @Override
    public void setViewData() {
        tv_title.setText("企业入驻申请表");
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        iv_business.setOnClickListener(this);
        iv_allow.setOnClickListener(this);
        iv_card1.setOnClickListener(this);
        iv_card2.setOnClickListener(this);
        iv_card3.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_business:
                requestCode = 0;
                showPop(requestCode);
                break;

            case R.id.iv_allow:
                requestCode = 1;
                showPop(requestCode);
                break;

            case R.id.iv_card1:
                requestCode = 2;
                showPop(requestCode);
                break;

            case R.id.iv_card2:
                requestCode = 3;
                showPop(requestCode);
                break;

            case R.id.iv_card3:
                requestCode = 4;
                showPop(requestCode);
                break;

            case R.id.tv_submit:
                String companyName = et_company_name.getText().toString();
                String address = et_address.getText().toString();
                String contactPhone = et_contact_phone.getText().toString();
                String contactUser = et_contact_user.getText().toString();
                String shortName = et_short_name.getText().toString();
                String userCard = et_user_card.getText().toString();
//                submitApply(companyName,address,contactPhone,contactUser,shortName,userCard,);
                break;
        }
    }

    PopupWindow pop;
    int requestCode = 0;
    private void showPop(final int requestCode) {
        View bottomView = View.inflate(mActivity, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(mActivity)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .compress(true)
                                .isCamera(false)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(requestCode);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(mActivity)
                                .openCamera(PictureMimeType.ofImage())
                                .compress(true)
                                .setOutputCameraPath("/CustomPath")
                                .forResult(requestCode);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);

    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("sfwef.......",requestCode+"a");
        List<LocalMedia> images;
        if(requestCode==0) {
            images = PictureSelector.obtainMultipleResult(data);
            String compressPath = images.get(0).getCompressPath();
            Log.d("sfwef.......",compressPath+"aaa");
        }
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//
//                    break;
//            }
//        }
    }

//    private void submitApply() {
//        MyInfoApi.submitApply(mActivity)
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
//                        if(baseModel.code==1) {
//
//
//                        }else {
//                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
//                        }
//                    }
//                });
//    }


}
