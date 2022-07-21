package com.barter.hyl.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylGridImageAdapter;
import com.barter.hyl.app.adapter.HylReturnGoodDetailChangeAdapter;
import com.barter.hyl.app.adapter.HylReturnGoodDetailTwoAdapter;
import com.barter.hyl.app.adapter.HylReturnMoneyAdapter;
import com.barter.hyl.app.adapter.HylReturnOrderAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.model.HylReturnOrderDetailModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.view.FullyGridLayoutManager;
import com.barter.hyl.app.view.GlideEngine;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdk.mobile.manager.login.NoDoubleClickListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/2
 */
public class HylReturnGoodActivity extends Base1Activity implements View.OnClickListener {

    private String orderId;
    private String orderStatus;
    private TextView mTvSelectReason;
    private List<String> mReturnReason = new ArrayList<>();
    private RecyclerView mRyOrderDetail;
    private List<HylReturnOrderDetailModel.DataBean.ProdsBean> mProductList = new ArrayList<>();
    private HylReturnGoodDetailTwoAdapter mRvDetailAdapter;
    private HylReturnGoodDetailChangeAdapter mRvDetailChangeAdapter;
    //退货理由
    private RecyclerView mRySeclectionReason;
    private HylReturnOrderAdapter mReturnAdapter;
    private TextView tvCancel;
    private TextView tv_return_money;
    //照相机
    private int maxSelectNum = 3;
    private List<LocalMedia> selectList = new ArrayList<>();
    private HylGridImageAdapter adapter;
    private RecyclerView mRecyclerView;
    private PopupWindow pop;
    private TextView tv_post_order;
    TextView tv_desc;
    public HylReturnOrderDetailModel mDetailModel;
    private HylReturnOrderDetailModel mDetailModels;
    private List<String> picList = new ArrayList();
    private EditText et_return_content;
    String returnPic = "";
    double totalPrice = 0.00;
    private boolean isChecked = true;
    private boolean icFirst = true;
    private int orderDeliveryType;
    private CheckBox rd_check;
    private TextView tv_return_way;
    RecyclerView recycler;
    ImageView iv_back;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_return_order_hyl);

    }

    @Override
    public void findViewById() {
        iv_back = findViewById(R.id.iv_back);
        tv_desc = findViewById(R.id.tv_desc);
        recycler = findViewById(R.id.recycler);
        mTvSelectReason = findViewById(R.id.tv_select_reason);
        mRyOrderDetail = findViewById(R.id.recyclerview_return_good);
        mRecyclerView = findViewById(R.id.recycler);
        tv_post_order = findViewById(R.id.tv_post_order);
        tv_return_money = findViewById(R.id.tv_return_money);
        et_return_content = findViewById(R.id.et_return_content);
        rd_check = findViewById(R.id.rd_check);
        tv_return_way = findViewById(R.id.tv_return_way);

    }

    @Override
    public void setViewData() {
        orderId = getIntent().getStringExtra("orderId");
        UserInfoHelper.saveOrderId(mContext, orderId);
        orderStatus = getIntent().getStringExtra("orderStatus");
        orderDeliveryType = getIntent().getIntExtra("orderDeliveryType", 0);
        mRyOrderDetail.setHasFixedSize(true);
        mRyOrderDetail.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        getReturnOrderDetail(orderId);
        initWidget();
        //防止复用刷新加载太频繁
        et_return_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                icFirst = true;
            }
        });
    }

    @Override
    public void setClickEvent() {
        mTvSelectReason.setOnClickListener(this);
        tv_post_order.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    double f1;
    //获取退货订单信息
    private void getReturnOrderDetail(String orderId) {
        OrderApi.returnGoods(mContext, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylReturnOrderDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylReturnOrderDetailModel hylReturnOrderDetailModel) {

                        if (hylReturnOrderDetailModel.getCode()==1) {
                            if (hylReturnOrderDetailModel.getData() != null) {
                                mDetailModel = hylReturnOrderDetailModel;
                                mProductList.clear();
                                mProductList.addAll(hylReturnOrderDetailModel.getData().getProds());
                                tv_return_money.setText(0 + "");
                                tv_return_way.setText(hylReturnOrderDetailModel.getData().getChannel());
                                getOrder(mProductList);

                                rd_check.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (rd_check.isChecked()) {
                                            mDetailModel.getData().setAllReturn(1);
                                            mRvDetailAdapter = new HylReturnGoodDetailTwoAdapter(mProductList, mContext, mDetailModel.getData().getAllReturn() + "");
                                            mRyOrderDetail.setAdapter(mRvDetailAdapter);
                                            tv_return_money.setText("￥"+mDetailModel.getData().getTotalAmt());
                                        } else {
                                            mDetailModel.getData().setAllReturn(0);
                                            mRvDetailAdapter = new HylReturnGoodDetailTwoAdapter(mProductList, mContext, mDetailModel.getData().getAllReturn() + "");
                                            mRyOrderDetail.setAdapter(mRvDetailAdapter);
                                            tv_return_money.setText("0.0");
                                            mRvDetailAdapter.setListener(new HylReturnGoodDetailTwoAdapter.OnReturnClickListener() {
                                                @Override
                                                public void onClick() {
                                                    //回调计算总金额
                                                    double toPrice = 0.00;
                                                    for (int i = 0; i < mDetailModel.getData().getProds().size(); i++) {
                                                        for (int j = 0; j < mDetailModel.getData().getProds().get(i).getDetails().size(); j++) {
                                                            toPrice += mDetailModel.getData().getProds().get(i).getDetails().get(j).getItemPrice();
                                                        }
                                                    }
                                                    BigDecimal bg = new BigDecimal(toPrice);
                                                    f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                                    tv_return_money.setText("￥" + f1);
                                                    totalPrice = toPrice;
                                                }
                                            });

                                        }
                                    }
                                });
                            }
                        } else {
                            AppHelper.showMsg(mContext, hylReturnOrderDetailModel.getMessage());
                        }
                    }
                });
    }


    private void getOrder(List<HylReturnOrderDetailModel.DataBean.ProdsBean> list) {
        mRvDetailAdapter = new HylReturnGoodDetailTwoAdapter(list, mContext,mDetailModel.getData().getAllReturn()+"");
        mRvDetailAdapter.setListener(new HylReturnGoodDetailTwoAdapter.OnReturnClickListener() {
            @Override
            public void onClick() {
                //回调计算总金额
                mDetailModels = mDetailModel;
                double toPrice = 0.00;
                for (int i = 0; i < mDetailModel.getData().getProds().size(); i++) {
                    for (int j = 0; j < mDetailModel.getData().getProds().get(i).getDetails().size(); j++) {
                        toPrice += mDetailModel.getData().getProds().get(i).getDetails().get(j).getItemPrice();
                    }
                }
                BigDecimal bg = new BigDecimal(toPrice);
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                tv_return_money.setText("￥" + f1);
                totalPrice = toPrice;
            }
        });
        mRyOrderDetail.setAdapter(mRvDetailAdapter);
        mRyOrderDetail.setItemViewCacheSize(500);

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





    private void sendMgs() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mProductList.size(); i++) {
            HylReturnOrderDetailModel.DataBean.ProdsBean prodsBean = mProductList.get(i);

            for (int j = 0; j < prodsBean.getDetails().size(); j++) {
                JSONObject jsonObject = new JSONObject();
                List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean> details = prodsBean.getDetails();
                try {
                    jsonObject.put("detailId", details.get(j).getItemDetailIds());
                    jsonObject.put("businessId", prodsBean.getBusinessId());
                    jsonObject.put("businessType",prodsBean.getBusinessType());
                    jsonObject.put("unitId",details.get(j).getItemUnitId());
                    jsonObject.put("num",details.get(j).getItemNum());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
//        for (int i = 0; i < mDetailModels.getData().getProds().size(); i++) {
//            HylReturnOrderDetailModel.DataBean.ProdsBean prodsBean = mDetailModels.getData().getProds().get(i);
//            try {
//                for (int j = 0; j < prodsBean.getDetails().size(); j++) {
//                    JSONObject jsonObject = new JSONObject();
//                    List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean> details = prodsBean.getDetails();
//                    jsonObject.put("detailId", details.get(j).getItemDetailIds());
//                    jsonObject.put("businessId", prodsBean.getBusinessId());
//                    jsonObject.put("businessType",prodsBean.getBusinessType());
//                    jsonObject.put("unitId",details.get(j).getItemUnitId());
//                    jsonObject.put("num",details.get(j).getItemNum());
//                    jsonArray.put(jsonObject);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        if (!rd_check.isChecked()) {

//            isChecked = false;
            OrderApi.applyReturn(mContext, orderId,"选错", et_return_content.getText().toString(),
                    f1+"", returnPic,mDetailModel.getData().getAllReturn()+"",jsonArray)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HylLoginModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            isChecked = true;
                            tv_post_order.setClickable(true);
                            AppHelper.showMsg(mContext, e.getMessage());
                        }

                        @Override
                        public void onNext(HylLoginModel model) {
                            if (model.success) {
                                isChecked = true;
                                AppHelper.showMsg(mContext,"申请退货成功,请等待审核");
                                Intent intent = new Intent(mContext, HylReturnGoodDetailActivity.class);
                                intent.putExtra("returnMainId",model.data);
                                intent.putExtra("orderId",orderId);
                                startActivity(intent);
                                finish();
                            } else {
                                AppHelper.showMsg(mContext, model.message);
                                isChecked = true;
                            }
                        }
                    });
        }else {

            OrderApi.applyReturn(mContext, orderId,"选错", et_return_content.getText().toString(),
                    f1+"", returnPic,mDetailModel.getData().getAllReturn()+"",null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HylLoginModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            isChecked = true;
                            tv_post_order.setClickable(true);
                            AppHelper.showMsg(mContext, e.getMessage());
                        }

                        @Override
                        public void onNext(HylLoginModel model) {
                            if (model.success) {
                                isChecked = true;
                                AppHelper.showMsg(mContext,"申请退货成功,请等待审核");
                                Intent intent = new Intent(mContext, HylReturnGoodDetailActivity.class);
                                intent.putExtra("returnMainId",model.data);
                                intent.putExtra("orderId",orderId);
                                startActivity(intent);
                                finish();
                            } else {
                                AppHelper.showMsg(mContext, model.message);
                                isChecked = true;
                            }
                        }
                    });
        }
    }

    //退款去向
    private void showReturnWay(final HylReturnOrderDetailModel returnGoodModels) {
        if (returnGoodModels.getData().getReturnType().size() > 1) {
            final Dialog dialog = new Dialog(mActivity, R.style.DialogTheme);
            View view = View.inflate(mActivity, R.layout.deliver_time_popu_hyl, null);
            RecyclerView mRyReturnMoney = view.findViewById(R.id.ry_select);
            TextView tvCancel = view.findViewById(R.id.tv_cancel_reason);
            tvCancel.setVisibility(View.GONE);
            mRyReturnMoney.setLayoutManager(new LinearLayoutManager(mActivity));
            HylReturnMoneyAdapter adapter = new HylReturnMoneyAdapter(R.layout.select_reason, returnGoodModels.getData().getReturnType());
            mRyReturnMoney.setAdapter(adapter);

            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(true);
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.BOTTOM;
            dialogWindow.setAttributes(lp);

            dialog.show();
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mTvSelectReason.setText(returnGoodModels.getData().getReturnType().get(position));
                    dialog.dismiss();
                }
            });
        }
    }

    //上传图片
    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        adapter = new HylGridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new HylGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getMimeType();
                    int mimeType = PictureMimeType.getMimeType(pictureType);
                    switch (mimeType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(HylReturnGoodActivity.this).externalPicturePreview(position, selectList,position);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(HylReturnGoodActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(HylReturnGoodActivity.this).externalPictureAudio(media.getPath());
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
    }

    private HylGridImageAdapter.onAddPicClickListener onAddPicClickListener = new HylGridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            showPop();

        }
    };

    public void upImage(List<MultipartBody.Part> parts) {
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
                                for (int i = 0; i < baseModel.data.length; i++) {
                                    returnPic += baseModel.data[i] + ",";
                                }
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

    private void showPop() {
        View bottomView = View.inflate(HylReturnGoodActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(HylReturnGoodActivity.this)
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
                        PictureSelector.create(HylReturnGoodActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .compress(true)
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

                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    upImage(filesToMultipartBodyParts(picList));
                    break;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_reason:
                showReturnWay(mDetailModel);
                break;

            case R.id.tv_post_order:
                sendMgs();
                if (icFirst) {
                    if (mTvSelectReason.getText().toString() != null) {

                    }
                }
                break;

            case R.id.iv_back:
                finish();
        }
    }
}
