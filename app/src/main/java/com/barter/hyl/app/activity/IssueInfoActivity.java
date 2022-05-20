package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.app.model.SendImageModel;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.ShopImageViewAdapter;
import com.barter.hyl.app.api.AddressApi;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.dialog.ShopStyleDialog;
import com.barter.hyl.app.event.DeletePicEvent;
import com.barter.hyl.app.event.MyShopEvent;
import com.barter.hyl.app.event.ShopStyleEvent;
import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.CityChangeModel;
import com.barter.hyl.app.model.HylAreaModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.CascadingMenuPopWindow;
import com.barter.hyl.app.view.FullyGridLayoutManager;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IssueInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_message_style)
    TextView tv_message_style;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    PopupWindow pop;
    private int maxSelectNum = 6;
    private List<String> picList = new ArrayList();
    private List<LocalMedia> selectList = new ArrayList<>();
    private ShopImageViewAdapter shopImageViewAdapter;
    String returnPic = "";
    CascadingMenuPopWindow cascadingMenuPopWindow;
    String provinceCode;
    String provinceName;
    String cityName;
    String cityCode;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_issue_info);
    }

    @Override
    public void setViewData() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        shopImageViewAdapter = new ShopImageViewAdapter(mContext,onAddPicClickListener);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(shopImageViewAdapter);
        shopImageViewAdapter.setOnItemClickListener(new ShopImageViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(pictureType);

                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
//                            PictureSelector.create(mActivity).externalPicturePreview(position, selectList,position);
                            AppHelper.showPhotoDetailsDialog(mContext,picList,position,selectList,shopImageViewAdapter);
                            break;
                    }
                }
            }

            @Override
            public void deletPic(int position) {
                picList.remove(position);
                upImage(filesToMultipartBodyParts(picList));
            }
        });
        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cascadingMenuPopWindow = new CascadingMenuPopWindow(mActivity, listCity);
//                cascadingMenuPopWindow.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
//                cascadingMenuPopWindow.showAsDropDown(et, 5, 5);
//                cascadingMenuPopWindow.setOutsideTouchable(true);
//                cascadingMenuPopWindow.setBackgroundDrawable(new BitmapDrawable());
//                cascadingMenuPopWindow.setTouchable(true);
//                cascadingMenuPopWindow.setOnDismissListener(new popupDismissListener());
//                backgroundAlpha(0.3f);
                if(isLoaded) {
                    showPickerView();
                }
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_phone.getText().toString();
                int result = checkPhoneNum(phone);
                if (result == 2) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (result == 1) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    IssueInfo(et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                }

            }
        });


        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStyleDialog shopStyleDialog = new ShopStyleDialog(mContext);
                shopStyleDialog.show();
            }
        });
        getCityList();
    }

    @Override
    public void setClickListener() {
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
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
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


    // 级联菜单选择回调接口
    class NMCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener {
        @Override
        public void getValue(HylAreaModel.DataBean area) {
            provinceName = area.getProvinceName();
            provinceCode = area.getProvinceCode();
        }

        @Override
        public void getValues(HylAreaModel.DataBean.CityListBean area) {
            backgroundAlpha(1);
            cityName = area.getCityName();
            tv_area.setText(provinceName+cityName);
            cityCode = area.getCityCode();
        }

        @Override
        public void cloese() {
            backgroundAlpha(1);
        }

    }


    private ShopImageViewAdapter.onAddPicClickListener onAddPicClickListener = new ShopImageViewAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            showPop();

        }
    };

    private void showPop() {
        View bottomView = View.inflate(IssueInfoActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(IssueInfoActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum - selectList.size())
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .compress(true)
                                .isCamera(false)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(IssueInfoActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .compress(true)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .setOutputCameraPath("/CustomPath")
                                .forResult(PictureConfig.CHOOSE_REQUEST);
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
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);
                    for (int i = 0; i < images.size(); i++) {
                        picList.add(images.get(i).getCompressPath());
                    }
                    shopImageViewAdapter.setList(selectList);
                    shopImageViewAdapter.notifyDataSetChanged();
                    upImage(filesToMultipartBodyParts(picList));

                    break;
            }
        }
    }

    private void upImage(List<MultipartBody.Part> parts) {
        OrderApi.requestImgDetail(mContext, parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSendImageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylSendImageModel baseModel) {
                        if (baseModel.success) {
                            returnPic = "";
                            if (baseModel.data != null) {
                                String[] data = baseModel.data;
                                Gson gson = new Gson();
                                returnPic = gson.toJson(data);
                            }
                            Log.i("wwwwbbb", "onNext: " + returnPic);
                            //  sendMgs();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 售卖类型
     * @param shopStyleEvent
     */
    String datum;

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
    int position = -1;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(ShopStyleEvent shopStyleEvent) {
        datum = shopStyleEvent.getDatum();
        position = shopStyleEvent.getPosition();
        tv_message_style.setText(shopStyleEvent.getDatum());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(DeletePicEvent deletePicEvent) {
        Log.d("sdfqewadfs....",picList.size()+"---------"+position);
        picList.remove(deletePicEvent.getPos());
        upImage(filesToMultipartBodyParts(picList));
    }


    ArrayList<HylAreaModel.DataBean> listCity = new ArrayList<>();
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
                    public void onNext(HylAreaModel hylAreaModel) {
                        if (hylAreaModel.getCode()==1) {
                            parseData(hylAreaModel.getData());

                        } else {
                            AppHelper.showMsg(mContext, hylAreaModel.getMessage());
                        }
                    }
                });
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

    private void IssueInfo(String content,String address,String phone) {
        InfoListAPI.InfoIssue(mContext,position,content,returnPic,provinceCode,cityCode,address,phone)
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
}
