package com.barter.hyl.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.barter.hyl.app.adapter.HylReturnGoodsAdapter;
import com.barter.hyl.app.adapter.HylReturnMoneyAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.event.ReturnUnitHylEvent;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.view.FullyGridLayoutManager;
import com.barter.hyl.app.view.GlideEngine;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by ${王涛} on 2021/9/9
 */
public class HylReturnGoodsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;
    @BindView(R.id.rb_check)
    CheckBox rb_check;
    @BindView(R.id.tv_return_money)
    TextView tv_return_money;
    @BindView(R.id.tv_return_way)
    TextView tv_return_way;
    @BindView(R.id.tv_select_reason)
    TextView tv_select_reason;
    @BindView(R.id.tv_post_order)
    TextView tv_post_order;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_post_camera)
    TextView tv_post_camera;
    @BindView(R.id.et_return_content)
    EditText et_return_content;
    String orderId;
    HylReturnGoodsAdapter hylReturnGoodsAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_return_goods_hyl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setViewData() {
        tv_title.setText("售后详情");
        getReturnOrderDetail(orderId);
        initWidget();
        EventBus.getDefault().register(this);
    }

    @Override
    public void setClickListener() {
        rb_check.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_select_reason.setOnClickListener(this);
        tv_post_camera.setOnClickListener(this);
        tv_post_order.setOnClickListener(this);
    }

    /**
     * 获取退货订单信息
     * @param orderId
     */
    List<HylReturnGoodModel.DataBean.ProdsBean> prodsList = new ArrayList<>();
    HylReturnGoodModel hylReturnGoodModels;
    private void getReturnOrderDetail(String orderId) {
        OrderApi.returnGood(mContext, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylReturnGoodModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylReturnGoodModel hylReturnGoodModel) {
                        if (hylReturnGoodModel.getCode()==1) {
                            if (hylReturnGoodModel.getData()!= null) {
                                hylReturnGoodModels = hylReturnGoodModel;
                                prodsList.clear();
                                prodsList.addAll(hylReturnGoodModel.getData().getProds());

                                tv_return_way.setText(hylReturnGoodModel.getData().getChannel());
                                //退货商品adapter
                                rv_goods.setLayoutManager(new LinearLayoutManager(mContext));
                                hylReturnGoodsAdapter =new HylReturnGoodsAdapter(R.layout.item_return_goods,prodsList, hylReturnGoodModels.getData().getOrderId());
                                rv_goods.setAdapter(hylReturnGoodsAdapter);
                                hylReturnGoodsAdapter.notifyDataSetChanged();
                            }

                        } else {
                            AppHelper.showMsg(mContext, hylReturnGoodModel.getMessage());
                        }
                    }
                });
    }


    int AllReturn = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_post_order:
                sendOrder();
                break;

            case R.id.tv_post_camera:

                break;

            case R.id.tv_select_reason:
                showReturnWay(hylReturnGoodModels);
                break;

            case R.id.iv_back:
                finish();
                break;
            case R.id.rb_check:
                if(rb_check.isChecked()) {
                    AllReturn = 1;
                    tv_return_money.setText(hylReturnGoodModels.getData().getTotalAmt());
                }else {
                    AllReturn = 0;
                    tv_return_money.setText("0");
                }
                hylReturnGoodsAdapter.setListener(new HylReturnGoodsAdapter.OnReturnClickListener() {
                    @Override
                    public void onClick() {

                    }
                });
                break;
        }
    }



    /**
     * 提交退货订单
     */
    JSONArray jsonArray;
    private boolean isChecked = true;
    private void sendOrder() {
        jsonArray = new JSONArray();

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < hylReturnGoodModels.getData().getProds().size(); i++) {
            HylReturnGoodModel.DataBean.ProdsBean prodsBean = hylReturnGoodModels.getData().getProds().get(i);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("businessId",prodsBean.getBusinessId());
                jsonObject.put("businessType", prodsBean.getBusinessType());

                for (int j = 0; j < prodsBean.getDetails().size(); j++) {
                    jsonObject.put("detailId", prodsBean.getDetails().get(j).getDetailId());
                    jsonObject.put("num", prodsBean.getDetails().get(j).getNum());
                    for (int k = 0; k < units.size(); k++) {
                        jsonObject.put("unitId", units.get(k));
                    }
                }
                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(isChecked) {
            isChecked = false;
            //生成退货单
            OrderApi.applyReturn(mContext, orderId,tv_select_reason.getText().toString(),
                    et_return_content.getText().toString(),
                    hylReturnGoodModels.getData().getTotalAmt(), returnPic,AllReturn+"",jsonArray)
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
//                                intent.putExtra(AppConstant.RETURNPRODUCTMAINID, String.valueOf(model.data));
//                                intent.putExtra("orderType", orderDeliveryType);
                                startActivity(intent);
                                finish();
                            } else {
                                AppHelper.showMsg(mContext, model.message);
                                isChecked = true;
//                                icFirst = true;
                            }
                        }
                    });
        }
    }

    //退款去向
    private void showReturnWay(final HylReturnGoodModel hylReturnGoodModels) {
        if (hylReturnGoodModels.getData().getReturnType().size() > 1) {
            final Dialog dialog = new Dialog(mActivity, R.style.DialogTheme);
            View view = View.inflate(mActivity, R.layout.deliver_time_popu_hyl, null);
            RecyclerView mRyReturnMoney = view.findViewById(R.id.ry_select);
            TextView tvCancel = view.findViewById(R.id.tv_cancel_reason);
            tvCancel.setVisibility(View.GONE);
            mRyReturnMoney.setLayoutManager(new LinearLayoutManager(mActivity));
            HylReturnMoneyAdapter adapter = new HylReturnMoneyAdapter(R.layout.select_reason, hylReturnGoodModels.getData().getReturnType());
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
                    tv_select_reason.setText(hylReturnGoodModels.getData().getReturnType().get(position));
                    dialog.dismiss();
                }
            });
        }
    }

    //上传图片
    HylGridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> picList = new ArrayList();
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
                            PictureSelector.create(HylReturnGoodsActivity.this).externalPicturePreview(position, selectList,position);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(HylReturnGoodsActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(HylReturnGoodsActivity.this).externalPictureAudio(media.getPath());
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

    String returnPic = "";
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

    private HylGridImageAdapter.onAddPicClickListener onAddPicClickListener = new HylGridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            showPop();

        }
    };

    private PopupWindow pop;
    //照相机
    private int maxSelectNum = 3;
    private void showPop() {
        View bottomView = View.inflate(HylReturnGoodsActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(HylReturnGoodsActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum - selectList.size())
                                .minSelectNum(1)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .imageSpanCount(4)
                                .compress(true)
                                .isCamera(false)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:


                        //拍照
                        PictureSelector.create(HylReturnGoodsActivity.this)
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
    /**
     * 切换单位时
     * @param returnUnitEvent
     */
    List<Integer> units;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUnit(ReturnUnitHylEvent returnUnitHylEvent) {
        units = returnUnitHylEvent.getUnits();
//        Log.d("sdgffdsffefdfvds....",units.get(0)+"s");
    }


}
