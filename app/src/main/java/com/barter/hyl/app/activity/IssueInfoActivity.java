package com.barter.hyl.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.barter.app.model.SendImageModel;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.ShopImageViewAdapter;
import com.barter.hyl.app.api.AddressApi;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.dialog.InfoPayDialog;
import com.barter.hyl.app.dialog.ShopStyleDialog;
import com.barter.hyl.app.event.DeletePicEvent;
import com.barter.hyl.app.event.InfoPayEvent;
import com.barter.hyl.app.event.MyShopEvent;
import com.barter.hyl.app.event.ShopStyleEvent;
import com.barter.hyl.app.event.WeChatPayEvent;
import com.barter.hyl.app.event.WeChatUnPayEvent;
import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.CityChangeModel;
import com.barter.hyl.app.model.HylAreaModel;
import com.barter.hyl.app.model.HylPayInfoModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.model.InfoIsPayModel;
import com.barter.hyl.app.model.InfoPubModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.CascadingMenuPopWindow;
import com.barter.hyl.app.view.FullyGridLayoutManager;
import com.barter.hyl.app.view.GlideEngine;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

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
    @BindView(R.id.tv_money)
    TextView tv_money;
    PopupWindow pop;
    private int maxSelectNum = 6;
    private List<String> picList = new ArrayList();
    private List<LocalMedia> selectList = new ArrayList<>();
    private ShopImageViewAdapter shopImageViewAdapter;
    String returnPic = "";
    String provinceCode;
    String cityCode;
    String videoUrl = "";
    String videoCoverUrl = "";
    List<String> test = new ArrayList<>();
    List<String> test1 = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
//                if (selectList.size() > 0) {
//                    LocalMedia media = selectList.get(position);
//                    String pictureType = media.getMimeType();
//                    int mediaType = PictureMimeType.getMimeType(pictureType);
//
//                    switch (mediaType) {
//                        case 1:
//                            // 预览图片 可自定长按保存路径
//                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
////                            PictureSelector.create(mActivity).externalPicturePreview(position, selectList,position);
//                            AppHelper.showPhotoDetailsDialog(mContext,picList,position,selectList,shopImageViewAdapter);
//                            break;
//                    }
//                }
                if(picsList.size()>0) {
                    AppHelper.showIssueDetailDialog(mActivity, picsList, position);
                }
            }

            @Override
            public void deletPic(int position) {
                String url = picsList.get(position);
                Gson gson1 = new Gson();
                if(url.contains(".mp4")) {
                    //删除的是视频
                    picsList.remove(position);
                    videoCoverUrl = "";
                    videoUrl = "";
                    returnPic = gson1.toJson(picsList);
                }else {
                    picsList.remove(position);
                    for (int i = 0; i < picsList.size(); i++) {
                        if(!picsList.get(i).contains(".mp4")) {
                            test.add(picsList.get(i));
                        }
                    }

                    returnPic = gson1.toJson(test);
                }

//                picList.remove(position);
//                upImage(filesToMultipartBodyParts(picList));
            }
        });

        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKbTwo();
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
                    getIsPay();
//                    IssueInfo(et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                }

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("上传中......");

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStyleDialog shopStyleDialog = new ShopStyleDialog(mContext);
                shopStyleDialog.show();
            }
        });
        getCityList();
    }

    /**
     * 判断是否需要支付
     */
    String amount;
    InfoPayDialog infoPayDialog;
    private void getIsPay() {
        OrderApi.getIsPay(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoIsPayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoIsPayModel infoIsPayModel) {
                        if(infoIsPayModel.getCode()==1) {
                            if(infoIsPayModel.getData().getPayFlag()==1) {
                                //是
                                tv_money.setVisibility(View.VISIBLE);
                                amount = infoIsPayModel.getData().getShouldPayAmt();
                                infoPayDialog = new InfoPayDialog(mContext,amount);
                                infoPayDialog.show();
                            }else {
                                //否
                                IssueInfo(et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                            }
                        }
                    }
                });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
    String areaCode;
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                provinceCode = options1Items.get(options1).getProvinceCode();
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



    private ShopImageViewAdapter.onAddPicClickListener onAddPicClickListener = new ShopImageViewAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            hintKbTwo();
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
//                        PictureSelector.create(IssueInfoActivity.this)
//                                .openGallery(PictureMimeType.ofAll())
//                                .maxSelectNum(maxSelectNum - selectList.size())
//                                .minSelectNum(1)
//                                .imageSpanCount(4)
//                                .queryMaxFileSize(55)
//                                .loadImageEngine(GlideEngine.createGlideEngine())
//                                .compress(true)
//                                .isCamera(false)
//                                .selectionMode(PictureConfig.MULTIPLE)
//                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        PictureSelector.create(IssueInfoActivity.this)
                                .openGallery(PictureMimeType.ofAll())
                                .maxSelectNum(maxSelectNum - selectList.size())
                                .minSelectNum(1)
                                .maxVideoSelectNum(1)
                                .imageSpanCount(4)
                                .queryMaxFileSize(55)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .compress(true)
                                .isCamera(false)
                                .recordVideoSecond(30)
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

    List<String> picsList = new ArrayList<>();
    //记录所有的选中视频和图片
    List<String> picsAllList = new ArrayList<>();
    //图片集合
    List<String> listPic = new ArrayList<>();
    //视频集合
    List<String> listVideo = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        listVideo.clear();
        listPic.clear();
        progressDialog.show();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    images = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < images.size(); i++) {
                        if(images.get(i).getRealPath().contains(".mp4")) {
                            if(videoUrl!=null&& !videoUrl.equals("")) {
                                progressDialog.dismiss();
                                ToastUtil.showSuccessMsg(mContext,"只能上传一个视频");
                                return;
                            }else {
                                picsAllList.add(images.get(i).getRealPath());
                            }
                        }else {
                            picsAllList.add(images.get(i).getRealPath());
                        }
                    }

                    for (LocalMedia media : images) {
                        if(media.getRealPath().contains(".mp4")) {
                            listVideo.add(media.getRealPath());
                        }else {
                            listPic.add(media.getRealPath());
                        }
                    }

                    selectList.addAll(images);
                    shopImageViewAdapter.setList(selectList);


                    if(listPic.size()>0) {
                        upImage(filesToMultipartBodyParts(listPic));
                        shopImageViewAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    if(listVideo.size()>0) {
                        upCover(filesToMultipartBodyParts(listVideo));
                    }

                    break;
            }
        }
    }

    private void upCover(List<MultipartBody.Part> filesToMultipartBodyParts) {
        OrderApi.requestImgDetail(mContext, filesToMultipartBodyParts)
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
                            videoCoverUrl = "";
                            if (baseModel.data != null) {
                                for(String url: baseModel.data) {
                                    videoCoverUrl = url;
                                    videoUrl = url;
                                    picsList.add(url);
                                }
                            }
                            shopImageViewAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();

                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
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
                            test1.clear();
                            if (baseModel.data != null) {
                                Gson gson = new Gson();
                                for(String datas : baseModel.data) {
                                    picsList.add(datas);
                                }

                                for (int i = 0; i < picsList.size(); i++) {
                                    if(picsList.get(i).contains(".mp4")) {

                                    }else {
                                        test1.add(picsList.get(i));
                                    }
                                }
                                returnPic = gson.toJson(test1);
                                Log.d("wdasdwdsd.......",returnPic+"b");
                            }

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

    int flag;
    LoadingDailog dialog;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(InfoPayEvent infoPayEvent) {
        flag = infoPayEvent.getS();
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("加载中......")
                .setCancelable(false)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        IssueInfo1(et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(DeletePicEvent deletePicEvent) {
        picList.remove(deletePicEvent.getPos());
        for (int i = 0; i < picList.size(); i++) {
        }

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
        InfoListAPI.InfoIssue(mContext,position,content,returnPic,provinceCode,cityCode,address,phone,videoUrl,videoCoverUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoPubModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoPubModel infoPubModel) {
                        if (infoPubModel.getCode()==1) {
                            ToastUtil.showSuccessMsg(mContext,infoPubModel.getMessage());
                            finish();
                            EventBus.getDefault().post(new MyShopEvent());
                        } else {
                            AppHelper.showMsg(mContext, infoPubModel.getMessage());
                        }
                    }
                });
    }

    String msgId;
    private void IssueInfo1(String content,String address,String phone) {
        InfoListAPI.InfoIssue(mContext,position,content,returnPic,provinceCode,cityCode,address,phone,videoUrl,videoCoverUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoPubModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoPubModel infoPubModel) {
                        if (infoPubModel.getCode()==1) {
                            ToastUtil.showSuccessMsg(mContext,infoPubModel.getMessage());
                            finish();
                            EventBus.getDefault().post(new MyShopEvent());

                            msgId = infoPubModel.getData();
                            EventBus.getDefault().post(new MyShopEvent());
                            getPayInfo(flag,amount,msgId);

                        } else {
                            AppHelper.showMsg(mContext, infoPubModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 生成订单信息
     * @param flag
     * @param amount
     * @param msgId
     */
    String payToken;
    String outTradeNo;
    private void getPayInfo(int flag, String amount, String msgId) {
        OrderApi.getInfoPay(mContext,flag,amount,msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylPayInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(HylPayInfoModel payInfoModel) {
                        if (payInfoModel.getCode()==1) {
                            dialog.dismiss();
                            payToken = payInfoModel.getData().getPayToken();
                            outTradeNo = payInfoModel.getData().getOutTradeNo();
                            if(flag==14) {
                                payAliPay(payInfoModel.getData().getPayToken());
                            }else {
                                //微信 官方
                                weChatPay2(payInfoModel.getData().getPayToken());
                            }
                        } else {
                            dialog.dismiss();
                            AppHelper.showMsg(mContext, payInfoModel.getMessage());
                        }
                    }
                });
    }


    private void weChatPay2(String json) {
        try {
            IWXAPI api = WXAPIFactory.createWXAPI(mContext, "wxbc18d7b8fee86977");
            JSONObject obj = new JSONObject(json);
            PayReq request = new PayReq();
            request.appId = obj.optString("appId");
            request.partnerId = obj.optString("mchID");
            request.prepayId = obj.optString("prepayId");
            request.packageValue = obj.optString("pkg");
            request.nonceStr = obj.optString("nonceStr");
            request.timeStamp = obj.optString("timeStamp");
            request.sign = obj.optString("paySign");
            api.sendReq(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void payAliPay(String parms) {
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        msg.payData = parms;
        UnifyPayPlugin.getInstance(mContext).sendPayRequest(msg);
    }

    /**
     * 微信支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatPayEvent event) {
        Intent intent = new Intent(mContext, InfoPayResultActivity.class);
        intent.putExtra("payChannel", flag);
        intent.putExtra("outTradeNo", outTradeNo);
        startActivity(intent);
        if(event.getCode().equals("1")) {
            //支付成功
            finish();
        }
        infoPayDialog.dismiss();
        tv_address.clearFocus();
    }

    /**
     * 微信支付的回调,使用的eventBus.......取消支付
     **/
    @Subscribe
    public void onEventMainThread(WeChatUnPayEvent event) {
        Intent intent = new Intent(mContext, InfoPayResultActivity.class);
        intent.putExtra("payChannel", flag);
        intent.putExtra("outTradeNo", outTradeNo);
        startActivity(intent);
//        finish();
        infoPayDialog.dismiss();
        tv_address.clearFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
