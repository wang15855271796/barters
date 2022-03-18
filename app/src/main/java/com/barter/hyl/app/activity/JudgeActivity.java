package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylGoodsCommentAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.HylEvalGoodsModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

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
 * Created by ${王涛} on 2021/8/16
 */
public class JudgeActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_issue)
    TextView tv_issue;
//    @BindView(R.id.star_bar)
//    StarBarView star_bar;
//    @BindView(R.id.tv_eval_status)
//    TextView tv_eval_status;
//    @BindView(R.id.et_content)
//    EditText et_content;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    String orderId;
    HylGoodsCommentAdapter hylGoodsCommentAdapter;
//    HylEvalGoodsModel.DataBean dataBean;
    //这个是当前的图片集合
List<String> pics= new ArrayList<>() ;
//String [] lists =
//当前图片集合的下标
private int selectPosition;
    HylEvalGoodsModel.DataBean dataBean;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_judge);
    }

//    HylShopImageViewsAdapter shopImageViewAdapter;
    @Override
    public void setViewData() {
//        sendMsg();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        hylGoodsCommentAdapter = new HylGoodsCommentAdapter(R.layout.item_goods_eval_hyl,list,JudgeActivity.this, new HylGoodsCommentAdapter.OnAddClickListener() {
            @Override
            public void onAddPicClick( final int Position,int SelectPos) {
                //这边先取到你点的对象的那个集合
                 pics= hylGoodsCommentAdapter.getData().get(Position).getPics();
                dataBean = hylGoodsCommentAdapter.getData().get(Position);
                selectPosition = SelectPos;

//                shopImageViewAdapter = shopImageViewAdapters;
                showPop();


//                shopImageViewAdapter.setOnItemClickListener(new HylShopImageViewsAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position, View v) {
//                        if (selectList.size() > 0) {
//                            LocalMedia media = selectList.get(position);
//                            String pictureType = media.getPictureType();
//                            int mediaType = PictureMimeType.pictureToVideo(pictureType);
//                            switch (mediaType) {
//                                case 1:
//                                    // 预览图片 可自定长按保存路径
//                                    PictureSelector.create(mActivity).externalPicturePreview(position, selectList);
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void deletPic(int position) {
//                        picList.remove(position);
//                        upImage(filesToMultipartBodyParts(picList));
//                    }
//                });


            }
        });
        recyclerView.setAdapter(hylGoodsCommentAdapter);
        getEvalGoods();

    }
//
    JSONArray jsonArray;
//    private void sendMsg() {
//        List<HylEvalGoodsModel.DataBean> data = hylGoodsCommentAdapter.getData();
//        for (int i=0;i<data.size();i++){
//            data.get(i).setPics(listToString(data.get(i).getPics()));
//        }
//
//    }


    PopupWindow pop;
    int maxSelectNum = 3;
    private void showPop() {
        View bottomView = View.inflate(mContext, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(JudgeActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .compress(true)
                                .isCamera(false)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(JudgeActivity.this)
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
     * 获取评价商品
     */
    List<HylEvalGoodsModel.DataBean> list = new ArrayList<>();
    private void getEvalGoods() {
        OrderApi.getEvalGoods(mContext, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylEvalGoodsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylEvalGoodsModel hylEvalGoodsModel) {
                        if (hylEvalGoodsModel.getCode()==1) {
                           if(hylEvalGoodsModel.getData()!=null) {
//                               list.clear();
//                               list.addAll(hylEvalGoodsModel.getData());
                               hylGoodsCommentAdapter.setNewData(hylEvalGoodsModel.getData());
//                               hylGoodsCommentAdapter.notifyDataSetChanged();
                           }
                        } else {
                            AppHelper.showMsg(mContext, hylEvalGoodsModel.getMessage());
                        }
                    }
                });
    }

    private List<String> picList = new ArrayList();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    images = PictureSelector.obtainMultipleResult(data);
//                    selectList.addAll(images);
                    picList.clear();
                    for (int i = 0; i < images.size(); i++) {

                        picList.add(images.get(i).getCompressPath());
//                        pics.add(images.get(i).getCompressPath());
                    }
                    upImage(filesToMultipartBodyParts(picList));
                    //这个是存放的本地图片的集合 需要上传到服务器  暂时不刷新适配器了等上传成功以后

                   //这里是刷新的
//                   pics.addAll(picList);
//                    dataBean.setPics(pics);
//                    hylGoodsCommentAdapter.notifyDataSetChanged();

//                    shopImageViewAdapter.setList(pics);
//                    shopImageViewAdapter.notifyDataSetChanged();

//你看看直接上传图片能行不能不能上传成功


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
                                    pics.add(baseModel.data[i]);

//                                    returnPic += baseModel.data[i] + ",";
                                }
                                dataBean.setPics(pics);

                                hylGoodsCommentAdapter.notifyDataSetChanged();
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

    /**
     * 立即评论
     */
    private void getComment() {
        List<HylEvalGoodsModel.DataBean> data = hylGoodsCommentAdapter.getData();


        jsonArray = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            HylEvalGoodsModel.DataBean dataBean = data.get(i);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("businessId",dataBean.getBusinessId());
                jsonObject.put("businessType", dataBean.getBusinessType());
                jsonObject.put("level",dataBean.getLevel());
                jsonObject.put("pics",listToString(dataBean.getPics()));
                jsonObject.put("content",dataBean.getContent());
                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        OrderApi.getJudge(mActivity,orderId,jsonArray)
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
                    public void onNext(HylLoginModel hylLoginModel) {
                        if (hylLoginModel.code==1) {
                            finish();
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylLoginModel.message);
                        }
                    }
                });
    }

    @Override
    public void setClickListener() {
        tv_issue.setOnClickListener(this);
        iv_back.setOnClickListener(this);

//        star_bar.setOnClickListener(this);

    }
    //图片拼接处理
    public  String listToString (List<String> list){
//        if (list.size()==0||list==null){
//            return "";
//        }
        StringBuffer sb=new StringBuffer();
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                if (i<list.size()-1){
                    sb.append(list.get(i)+",");
                }else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_issue:
                getComment();
                break;

            case R.id.iv_back:
                finish();
                break;

//            case R.id.star_bar:
//                setStarName(tv_eval_status,star_bar.getStarRating());
//                break;
        }
    }

    //设置星星
    String star;
    private void setStarName(TextView tv_content, float star_num) {
        if (star_num == 5.0f) {
            tv_content.setText("很满意");
            star = "5";
        } else if (star_num == 4.0f) {
            tv_content.setText("满意");
            star = "4";
        } else if (star_num == 3.0f) {
            tv_content.setText("一般");
            star = "3";
        } else if (star_num == 2.0f) {
            tv_content.setText("不满意");
            star = "2";
        } else if (star_num == 1.0f) {
            tv_content.setText("非常差");
            star = "1";
        }
    }

    private List<LocalMedia> selectList = new ArrayList<>();

}
