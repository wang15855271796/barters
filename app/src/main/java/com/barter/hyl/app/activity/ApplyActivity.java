package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.barter.app.model.SendImageModel;
import com.barter.hyl.app.R;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.GlideEngine;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    @BindView(R.id.et_business_num)
    EditText et_business_num;
    String phone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
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

    String companyName;
    String address;
    String contactPhone;
    String contactUser;
    String shortName;
    String userCard;
    String businessNum;
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
                if(TextUtils.isEmpty(et_company_name.getText().toString()) ||
                   TextUtils.isEmpty(et_address.getText().toString()) ||
                   TextUtils.isEmpty(et_contact_phone.getText().toString()) ||
                   TextUtils.isEmpty(et_contact_user.getText().toString()) ||
                   TextUtils.isEmpty(et_short_name.getText().toString())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写对应信息");
                    return;
                }

                companyName = et_company_name.getText().toString();
                address = et_address.getText().toString();
                contactPhone = et_contact_phone.getText().toString();
                contactUser = et_contact_user.getText().toString();
                shortName = et_short_name.getText().toString();
                userCard = et_user_card.getText().toString();
                businessNum = et_business_num.getText().toString();
                submitApply();
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
                                .loadImageEngine(GlideEngine.createGlideEngine())
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

    List<String> picList = new ArrayList<>();
    List<LocalMedia> images = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picList.clear();
        images.clear();
        switch (requestCode) {
            case 0:
                images = PictureSelector.obtainMultipleResult(data);
                String compressPath = images.get(0).getCompressPath();
                picList.add(compressPath);
                Glide.with(mActivity).load(compressPath).into(iv_business);
                List<MultipartBody.Part> parts = filesToMultipartBodyParts(picList);
                upImage(parts,requestCode);
                break;

            case 1:
                images = PictureSelector.obtainMultipleResult(data);
                String compressPath1 = images.get(0).getCompressPath();
                picList.add(compressPath1);
                Glide.with(mActivity).load(compressPath1).into(iv_allow);
                List<MultipartBody.Part> parts1 = filesToMultipartBodyParts(picList);
                upImage(parts1,requestCode);
                break;


            case 2:
                images = PictureSelector.obtainMultipleResult(data);
                String compressPath2 = images.get(0).getCompressPath();
                picList.add(compressPath2);
                Glide.with(mActivity).load(compressPath2).into(iv_card1);
                List<MultipartBody.Part> parts2 = filesToMultipartBodyParts(picList);
                upImage(parts2,requestCode);
                break;

            case 3:
                images = PictureSelector.obtainMultipleResult(data);
                String compressPath3 = images.get(0).getCompressPath();
                picList.add(compressPath3);
                Glide.with(mActivity).load(compressPath3).into(iv_card2);
                List<MultipartBody.Part> parts3 = filesToMultipartBodyParts(picList);
                upImage(parts3,requestCode);
                break;

            case 4:
                images = PictureSelector.obtainMultipleResult(data);
                String compressPath4 = images.get(0).getCompressPath();
                picList.add(compressPath4);
                Glide.with(mActivity).load(compressPath4).into(iv_card3);
                List<MultipartBody.Part> parts4 = filesToMultipartBodyParts(picList);
                upImage(parts4,requestCode);
                break;


        }
    }

    public List<MultipartBody.Part> filesToMultipartBodyParts(List<String> localUrls) {
        List<MultipartBody.Part> parts = new ArrayList<>(localUrls.size());
        for (String url : localUrls) {
            File file = new File(url);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    String businessPath;
    String allowPath;
    String card1Path;
    String card2Path;
    String card3Path;
    public void upImage(List<MultipartBody.Part> parts,int requestCode) {
        OrderApi.updateImage(mContext, parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SendImageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SendImageModel sendImageModel) {
                        if (sendImageModel.code==1) {
                            if(sendImageModel.data!=null) {
                                switch (requestCode) {
                                    case 0:
                                        businessPath = sendImageModel.data;
                                        break;

                                    case 1:
                                        allowPath = sendImageModel.data;
                                        break;

                                    case 2:
                                        card1Path = sendImageModel.data;
                                        break;

                                    case 3:
                                        card2Path = sendImageModel.data;
                                        break;

                                    case 4:
                                        card3Path = sendImageModel.data;
                                        break;
                                }
                            }

                        } else {
                            AppHelper.showMsg(mContext, sendImageModel.message);
                        }
                    }
                });
    }


    private void submitApply() {
        MyInfoApi.submitApply(mActivity,companyName,shortName,address,contactUser,contactPhone,businessPath,allowPath,
                userCard,card1Path,card2Path,card3Path,businessNum,phone)
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
                        if(baseModel.code==1) {
                            Intent intent = new Intent(mActivity,ApplyResultActivity.class);
                            startActivity(intent);
                        }else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }


}
