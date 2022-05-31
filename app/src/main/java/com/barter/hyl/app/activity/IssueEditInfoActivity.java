package com.barter.hyl.app.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.ShopImageViewssAdapter;
import com.barter.hyl.app.api.AddressApi;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.dialog.ShopStyleDialog;
import com.barter.hyl.app.event.DeletePicEvent;
import com.barter.hyl.app.event.DeletePicsEvent;
import com.barter.hyl.app.event.MyShopEvent;
import com.barter.hyl.app.event.ShopStyleEvent;
import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.CityChangeModel;
import com.barter.hyl.app.model.HylAreaModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.model.InfoDetailIssueModel;
import com.barter.hyl.app.model.UpdateImageModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.CascadingMenuPopWindow;
import com.barter.hyl.app.view.GlideEngine;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/11
 */
public class IssueEditInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_message_style)
    TextView tv_message_style;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    String msgId;
    ShopImageViewssAdapter shopImageViewAdapter;
    private List<String> picList = new ArrayList();
    String returnPic;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        msgId = getIntent().getStringExtra("msgId");
        position = Integer.parseInt(getIntent().getStringExtra("msgType"));
        return false;
    }

    @Override
    public void setContentView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_issue_edit);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        getCityList(msgId);
        getCityList();

    }

    @Override
    public void setClickListener() {
        tv_area.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        rl.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    private int checkPhoneNum(String username){
        if (TextUtils.isEmpty(username)){
            return 2;
        }else if (!username.matches("^[1][0-9]{10}$")){
            return 1;
        }else {
            return 0;
        }
    }

    private void IssueInfo(String msgIds,String returnPic,String content,String address,String phone) {
        InfoListAPI.EditInfo(mContext,msgIds,position,content,returnPic,provinceCode,cityCode,address,phone)
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
                    public void onNext(BaseModel infoListModel) {
                        if (infoListModel.code==1) {

                            ToastUtil.showSuccessMsg(mContext,infoListModel.message);
                            finish();
                            EventBus.getDefault().post(new MyShopEvent());
                        } else {
                            AppHelper.showMsg(mContext, infoListModel.message);
                        }
                    }
                });
    }

    private void upImage(List<MultipartBody.Part> parts) {
        OrderApi.requestImgsDetail(mContext, parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpdateImageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(UpdateImageModel baseModel) {

                        if (baseModel.code==1) {
                            returnPic = "";
                            if (baseModel.data != null) {
                                Gson gson = new Gson();
                                pictureLists.addAll(baseModel.data);
                                returnPic = gson.toJson(pictureLists);
                                shopImageViewAdapter.notifyDataSetChanged();
                            }
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    public List<MultipartBody.Part> filesToMultipartBodyParts(List<String> localUrls) {
        List<MultipartBody.Part> parts = new ArrayList<>(localUrls.size());
        for (String url : localUrls) {
            File file = new File(url);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("detailFiles", file.getName(), requestBody);
            parts.add(part);

        }
        return parts;
    }

    String provinceCode;
    String cityCode;

    private void getCityList() {
        AddressApi.AddressArea(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAreaModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylAreaModel cityChangeModel) {
                        if (cityChangeModel.getCode()==1) {
                            parseData(cityChangeModel.getData());
                        } else {
                            AppHelper.showMsg(mContext, cityChangeModel.getMessage());
                        }
                    }
                });
    }


    List<String> pictureLists = new ArrayList<>();
    InfoDetailIssueModel.DataBean data;
    private void getCityList(String msgId) {
        InfoListAPI.InfoDetailIssue(mContext,msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoDetailIssueModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoDetailIssueModel infoListModel) {
                        if (infoListModel.getCode()==1) {
                            data = infoListModel.getData();
                            et.setText(data.getContent());

                            cityCode = data.getCityCode();
                            provinceCode = data.getProvinceCode();
                            tv_area.setText(data.getAreaName()+data.getCityName());
                            tv_message_style.setText(data.getMsgTypeName()+"");
                            et_phone.setText(data.getContactPhone());
                            tv_address.setText(data.getDetailAddress());
                            Gson gson = new Gson();
                            pictureLists.addAll(data.getPictureList());
                            returnPic = gson.toJson(pictureLists);
                            GridLayoutManager manager = new GridLayoutManager(mContext,3);

                            shopImageViewAdapter = new ShopImageViewssAdapter(mActivity,pictureLists, new ShopImageViewssAdapter.Onclick() {
                                @Override
                                public void addDialog() {
                                    showPop();
                                    hintKbTwo();
                                }

                                @Override
                                public void deletPic(int pos) {

                                }
                            });

                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(shopImageViewAdapter);

                            shopImageViewAdapter.setOnItemClickListener(new ShopImageViewssAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    if (pictureLists.size() > 0) {
                                        AppHelper.showPhotoDetailssDialog(mContext,position,pictureLists,shopImageViewAdapter);
                                    }
                                }

                                @Override
                                public void deletPic(int position) {
                                }
                            });

                        } else {
                            AppHelper.showMsg(mContext, infoListModel.getMessage());
                        }
                    }
                });
    }

    PopupWindow pop;
    private void showPop() {
        View bottomView = View.inflate(IssueEditInfoActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(IssueEditInfoActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(6-pictureLists.size())
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .compress(true)
                                .isCamera(false)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                }

                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    Gson gson1 = new Gson();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotals(DeletePicEvent deletePicEvent) {
        Log.d("efsdfsdfdf.....",deletePicEvent.getPos()+"aaaa");
        pictureLists.remove(deletePicEvent.getPos());
        returnPic = gson1.toJson(pictureLists);
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode== PictureConfig.CHOOSE_REQUEST&&resultCode==Activity.RESULT_OK){
            handleImgeOnKitKat(data);
        }
    }

    List<LocalMedia> images;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        images = PictureSelector.obtainMultipleResult(data);
        picList.clear();
        for (int i = 0; i < images.size(); i++) {
            picList.add(images.get(i).getCompressPath());
        }


        List<MultipartBody.Part> parts = filesToMultipartBodyParts(picList);
        upImage(parts);
    }


    private void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    String datum;
    int position = -1;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(ShopStyleEvent shopStyleEvent) {
        datum = shopStyleEvent.getDatum();
        position = shopStyleEvent.getPosition();
        tv_message_style.setText(shopStyleEvent.getDatum());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_area:
                hintKbTwo();
                if(isLoaded) {
                    showPickerView();
                }

                break;
            case R.id.rl:
                ShopStyleDialog shopStyleDialog = new ShopStyleDialog(mContext);
                shopStyleDialog.show();
                break;

            case R.id.tv_sure:
                String phone = et_phone.getText().toString();
                int result = checkPhoneNum(phone);
                if (result == 2) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (result == 1) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(pictureLists.size()>0) {
                        IssueInfo(msgId,returnPic,et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                    }else {
                        returnPic = "";
                        IssueInfo(msgId,returnPic,et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                    }
                }
                break;
        }
    }

    String proviceCode;
    String areaCode;
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                proviceCode = options1Items.get(options1).getProvinceCode();
                cityCode = options2Items.get(options1).get(options2).getCityCode();
                areaCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();
                String tx = options1Items.get(options1).getProvinceName() +
                        options2Items.get(options1).get(options2).getCityName() +
                        options3Items.get(options1).get(options2).get(options3).getAreaName();
                tv_area.setText(tx);
                tv_area.setTextColor(Color.parseColor("#333333"));
            }
        })

                .setTitleText("选择地区")
                .setCancelColor(Color.parseColor("#333333"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setFlag(false)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    /**
     * 处理地址数据结构
     * @param data
     */
    boolean isLoaded = false;
    //  省
    private List<HylAreaModel.DataBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<HylAreaModel.DataBean.CityListBean>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<HylAreaModel.DataBean.CityListBean.AreaListBean>>> options3Items = new ArrayList<>();
    private void parseData(List<HylAreaModel.DataBean> data) {
        options1Items = data;
        //     遍历省
        for(int i = 0; i <data.size() ; i++) {

//         存放城市
            ArrayList<HylAreaModel.DataBean.CityListBean> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<HylAreaModel.DataBean.CityListBean.AreaListBean>> province_AreaList = new ArrayList<>();
            List<HylAreaModel.DataBean.CityListBean> children1 = data.get(i).getCityList();
            cityList.addAll(children1);
//         遍历市
            for(int c = 0; c <data.get(i).getCityList().size() ; c++) {
                //该城市的所有地区列表
                ArrayList<HylAreaModel.DataBean.CityListBean.AreaListBean> city_AreaList = new ArrayList<>();

                if (data.get(i).getCityList().get(c).getAreaList() == null || data.get(i).getCityList().get(c).getAreaList().size() == 0) {
                    HylAreaModel.DataBean.CityListBean.AreaListBean childrenBean = new HylAreaModel.DataBean.CityListBean.AreaListBean();
                    childrenBean.setAreaName("");
                    city_AreaList.add(childrenBean);
                } else {

                    List<HylAreaModel.DataBean.CityListBean.AreaListBean> children = data.get(i).getCityList().get(c).getAreaList();
                    city_AreaList.addAll(children);
                    province_AreaList.add(city_AreaList);

                }
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
        isLoaded = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
