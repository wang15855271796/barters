package com.barter.hyl.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylChooseSpecAdapter;
import com.barter.hyl.app.adapter.HylImageDetailAdapter;
import com.barter.hyl.app.adapter.PicVideoAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.banner.BannerConfig;
import com.barter.hyl.app.banner.GlideImageLoader;
import com.barter.hyl.app.banner.Transformer;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.dialog.CommonDetailDialog;
import com.barter.hyl.app.dialog.FullDetailDialog;
import com.barter.hyl.app.dialog.FullDetailsDialog;
import com.barter.hyl.app.dialog.ProductDescDialog;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylCollectionModel;
import com.barter.hyl.app.model.HylCommonDetailModel;
import com.barter.hyl.app.model.PicVideoModel;
import com.barter.hyl.app.model.TipsModel;
import com.barter.hyl.app.model.VideoHolder;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.DetailFlowLayout;
import com.barter.hyl.app.view.FingerFrameLayout;
import com.barter.hyl.app.view.MyScrollView;
import com.barter.hyl.app.view.NumIndicator;
import com.barter.hyl.app.view.PhotoViewAdapter;
import com.barter.hyl.app.view.PhotoViewPager;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${王涛} on 2021/8/10
 */
public class HylCommonGoodsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.fl_container)
    DetailFlowLayout fl_container;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.rv_image)
    RecyclerView rv_image;
    @BindView(R.id.iv_collection)
    ImageView iv_collection;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_desc)
    RelativeLayout rl_desc;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.tv_add_car)
    TextView tv_add_car;
    @BindView(R.id.tv_fee)
    TextView tv_fee;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.ll_car)
    LinearLayout ll_car;
    @BindView(R.id.tv_full_desc)
    TextView tv_full_desc;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.ll_no_eval)
    LinearLayout ll_no_eval;
    @BindView(R.id.ll_eval)
    LinearLayout ll_eval;
    @BindView(R.id.ll_full)
    LinearLayout ll_full;
    @BindView(R.id.tv_full)
    TextView tv_full;
    @BindView(R.id.rl_check)
    RelativeLayout rl_check;
    @BindView(R.id.iv_sound)
    ImageView iv_sound;
    @BindView(R.id.ll_full_active)
    LinearLayout ll_full_active;
    @BindView(R.id.ll_skill_active)
    LinearLayout ll_skill_active;
    @BindView(R.id.ll_team_active)
    LinearLayout ll_team_active;
    @BindView(R.id.tv_skill)
    TextView tv_skill;
    @BindView(R.id.tv_team)
    TextView tv_team;
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;
    @BindView(R.id.scroll)
    MyScrollView scroll;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.iv_back1)
    ImageView iv_back1;
    String mainId;
    private List<PicVideoModel.DatasBean> picVideo = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        mainId = getIntent().getStringExtra("mainId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.common_detail_activity_hyl);
    }

    int fadingHeight = 375;
    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        getDetail(mainId,this);
        getCartNum();

        scroll.setScrollChangeListener(new MyScrollView.ScrollChangedListener() {
            @Override
            public void onScrollChangedListener(int x, int y, int oldX, int oldY) {

                if (y > fadingHeight) {
                    y = fadingHeight; // 当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可
                } else if (y < 0) {
                    y = 0;
                } else {
                }

                float scale = (float) y / 255;
                rl_title.setAlpha(scale);
            }
        });
        rv_image.setNestedScrollingEnabled(false);

    }



    @Override
    public void setClickListener() {
        iv_collection.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_back1.setOnClickListener(this);
        rl_desc.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        tv_add_car.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        ll_full.setOnClickListener(this);
        rl_check.setOnClickListener(this);
        iv_sound.setOnClickListener(this);
        ll_skill_active.setOnClickListener(this);
        ll_team_active.setOnClickListener(this);
        ll_full_active.setOnClickListener(this);
    }


    /**
     * 收藏
     */
    private void getCollectionStatus(String mainId) {
        DetailApi.getCollection(mContext,mainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCollectionModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCollectionModel hylCollectionModel) {
                        if (hylCollectionModel.code==1) {
                            if(hylCollectionModel.getData().equals("1")) {
                                iv_collection.setImageResource(R.mipmap.ic_unlove);
                            }else {
                                iv_collection.setImageResource(R.mipmap.ic_love);
                            }

                            ToastUtil.showSuccessMsg(mActivity, hylCollectionModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCollectionModel.message);
                        }
                    }
                });
    }
    /**
     * 获取详情
     */
    HylCommonDetailModel.DataBean data;
    List<HylCommonDetailModel.DataBean.SpecsBean> specs = new ArrayList<>();
    List<String> detailPics = new ArrayList<>();
    String skillId;
    String teamId;
    String fullId;
    private void getDetail(String mainId,HylCommonGoodsActivity hylCommonGoodsActivity) {
        DetailApi.getDetail(mContext,mainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCommonDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCommonDetailModel hylCommonDetailModel) {
                        if (hylCommonDetailModel.getCode()==1) {
                            data = hylCommonDetailModel.getData();
                            tv_title.setText(data.getProductName());
                            tv_price.setText(data.getMinMaxPrice());
                            tv_sale.setText(data.getSaleNum());
                            tv_desc.setText(data.getIntrduction());

                            tv_num.setText("商品评价"+data.getCommentNum());
                            if(data.getIntrduction()==null||data.getIntrduction().equals("")) {
                                rl_desc.setVisibility(View.GONE);
                            }else {
                                tv_desc.setText(data.getIntrduction());
                                rl_desc.setVisibility(View.VISIBLE);
                            }

                            if(data.getActives()!=null && data.getActives().size()>0) {
                                ll_coupon.setVisibility(View.VISIBLE);
                            }else {
                                ll_coupon.setVisibility(View.GONE);
                            }

                            if(data.getFullRoles()!=null&&data.getFullRoles().size()>0) {
                                String buySpec = data.getFullRoles().get(0).getBuySpec();
                                String sendProd = data.getFullRoles().get(0).getSendProd();
                                tv_full_desc.setText(buySpec+sendProd);
                            }

                            if(data.getCollectFlag()==0) {
                                iv_collection.setImageResource(R.mipmap.ic_unlove);
                            }else {
                                iv_collection.setImageResource(R.mipmap.ic_love);
                            }

                            if(data.getQuarantines()!=null&&data.getQuarantines().size()>0) {
                                rl_check.setVisibility(View.VISIBLE);
                            }else {
                                rl_check.setVisibility(View.GONE);
                            }
                            if(data.getComment()!=null) {
                                HylCommonDetailModel.DataBean.CommentBean comment = data.getComment();
                                Glide.with(mActivity).load(comment.getHeadPic()).into(iv_head);
                                tv_username.setText(comment.getReplayName());
                                tv_date.setText(comment.getCommentTime());
                                tv_content.setText(comment.getContent());
                                ll_no_eval.setVisibility(View.GONE);
                                ll_eval.setVisibility(View.VISIBLE);
                            }else {
                                ll_eval.setVisibility(View.GONE);
                                ll_no_eval.setVisibility(View.VISIBLE);
                            }

                            if(data.getActives()!=null&&data.getActives().size()>0) {
                                List<HylCommonDetailModel.DataBean.ActivesBean> actives = data.getActives();
                                for (int i = 0; i < actives.size(); i++) {
                                    if(actives.get(i).getActiveType()==2) {
                                        ll_skill_active.setVisibility(View.VISIBLE);
                                        tv_skill.setText(actives.get(i).getActiveName());
                                        skillId = actives.get(i).getActiveId();
                                    }else if(actives.get(i).getActiveType()==3) {
                                        ll_team_active.setVisibility(View.VISIBLE);
                                        tv_team.setText(actives.get(i).getActiveName());
                                        teamId = actives.get(i).getActiveId();
                                    }else {
                                        ll_full_active.setVisibility(View.VISIBLE);
                                        tv_full.setText(actives.get(i).getActiveName());
                                        fullId = actives.get(i).getActiveId();
                                    }
                                }
                            }


                            List<String> topPics = data.getTopPics();
                            //banner设置点击监听
                            banner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(Object data, int position) {
                                    showPhotoDetailDialog(mContext, topPics, position);
                                }
                            });
                            //规格
                            specs.clear();
                            specs.addAll(data.getSpecs());
                            getSpec(specs);

                            if(data.getVideoUrl()!=null&&!data.getVideoUrl().equals("")) {
                                topPics.add(0,data.getVideoUrl());
                                iv_sound.setVisibility(View.VISIBLE);
                            }else {
                                iv_sound.setVisibility(View.GONE);
                            }


                            if(topPics.size()>0) {
                                for (int i = 0; i < topPics.size(); i++) {
                                    if(data.getVideoUrl()!=null&&!data.getVideoUrl().equals("")) {
                                        if(i==0) {
                                            picVideo.add(new PicVideoModel.DatasBean(topPics.get(0),2));
                                        }else {
                                            picVideo.add(new PicVideoModel.DatasBean(topPics.get(i),1));
                                        }
                                    } else {
                                        picVideo.add(new PicVideoModel.DatasBean(topPics.get(i),1));
                                    }
                                }
                            }

                            banner.addBannerLifecycleObserver(hylCommonGoodsActivity)
                                    .setAdapter(new PicVideoAdapter(mContext, picVideo))
                                    .setIndicator(new NumIndicator(mContext))
                                    .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                                    .addOnPageChangeListener(new OnPageChangeListener() {
                                        @Override
                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                            stopVideo(position);
                                        }

                                        @Override
                                        public void onPageSelected(int position) {
                                            stopVideo(position);
                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int state) {

                                        }
                                    });

                            //底部详情图片
                            detailPics.clear();
                            detailPics.addAll(data.getDetailPics());
                            HylImageDetailAdapter hylImageDetailAdapter = new HylImageDetailAdapter(R.layout.item_imageview,detailPics);
                            rv_image.setLayoutManager(new LinearLayoutManager(mActivity));

                            rv_image.setAdapter(hylImageDetailAdapter);

                            //banner
//                            getBanner(topPics);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCommonDetailModel.getMessage());
                        }
                    }
                });
    }

    public static Dialog dialog;
    public static View dialogView;
    public static boolean isShow = false;
    public static void showPhotoDetailDialog(Context mContext, final List<String> mListUrl, int position) {
        dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_photo, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        // window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        final TextView mTv = dialog.findViewById(R.id.tv_dialog_photo);
        PhotoViewPager mVp = dialog.findViewById(R.id.vp_dialog_photo);
        FingerFrameLayout mFl = dialog.findViewById(R.id.ffl_dialog_photo);
        mFl.setOnAlphaChangeListener(new FingerFrameLayout.onAlphaChangedListener() {
            @Override
            public void onAlphaChanged(float alpha) {
                Log.e("fengan", "[onAlphaChanged]:alpha=" + alpha);
            }

            @Override
            public void onTranslationYChanged(float translationY) {
                Log.e("fengan", "[onTranslationYChanged]:translationY=" + translationY);
            }

            @Override
            public void onFinishAction() {
                AppHelper.hidePhotoDetailDialog();
            }
        });
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(mListUrl, mContext);
        mVp.setAdapter(photoViewAdapter);
        mVp.setCurrentItem(position);
        mTv.setText(position  + 1+"/" + mListUrl.size());
        photoViewAdapter.setPhotoListener(new PhotoViewAdapter.OnPhotoListener() {
            @Override
            public void onPhotoListenter() {
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTv.setText(position + 1 + "/" + mListUrl.size());
                mTv.getBackground().setAlpha(100);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        dialog.show();
        isShow = true;
    }

    StandardGSYVideoPlayer player;
    private void stopVideo(int position) {
        if (player == null) {
            RecyclerView.ViewHolder viewHolder = banner.getAdapter().getViewHolder();
            if (viewHolder instanceof VideoHolder) {
                VideoHolder holder = (VideoHolder) viewHolder;
                player = holder.player;
                if (position != 0) {
                    player.onVideoPause();
                }
            }
        }else {
            if (position != 0) {
                player.onVideoPause();
            }
        }
    }

    /**
     * 购物车数量
     */
    private void getCartNum() {
        DetailApi.getCartNum(mActivity,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCartNumModel hylCartNumModel) {
                        if (hylCartNumModel.getCode()==1) {
                            if(hylCartNumModel.getData().getProdNum()>0) {
                                tv_number.setVisibility(View.VISIBLE);
                                tv_fee.setVisibility(View.VISIBLE);
                                tv_number.setText(hylCartNumModel.getData().getProdNum()+"");
                                tv_total_amount.setText("￥"+ hylCartNumModel.getData().getAmount());
                                tv_fee.setText("满"+ hylCartNumModel.getData().getSendAmount()+"元免配送费");
                            }else {
                                tv_number.setVisibility(View.GONE);
                                tv_total_amount.setText("未选购商品");
                                tv_fee.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylCartNumModel.getMessage());
                        }
                    }
                });
    }

    HylChooseSpecAdapter hylChooseSpecAdapter;
    private void getSpec(List<HylCommonDetailModel.DataBean.SpecsBean> specs) {


        hylChooseSpecAdapter = new HylChooseSpecAdapter(mContext,specs, new HylChooseSpecAdapter.Onclick() {
            @Override
            public void addDialog(int position) {
                CommonDetailDialog commonDialog = new CommonDetailDialog(mActivity,data,position);
                commonDialog.show();
            }
        });
        fl_container.setAdapter(hylChooseSpecAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        banner.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        banner.stopAutoPlay();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_full:
                Intent intentss = new Intent(mActivity,FullActiveActivity.class);
                intentss.putExtra("fullId",data.getFullId());
                startActivity(intentss);
                break;
            case R.id.tv_detail:
                FullDetailsDialog fullDetailDialog = new FullDetailsDialog(mActivity, data) {
                    @Override
                    public void Confirm() {
                        dismiss();
                    }
                };
                fullDetailDialog.show();
                break;

            case R.id.ll_car:
//                finish();
                Intent intents = new Intent(mContext,MainActivity.class);
                startActivity(intents);
                EventBus.getDefault().post(new JumpCartHylEvent());
                break;

            case R.id.tv_add_car:
                CommonDetailDialog commonDetailDialog = new CommonDetailDialog(mActivity,data,0);
                commonDetailDialog.show();
                break;

            case R.id.iv_collection:
                getCollectionStatus(mainId);
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_back1:
                finish();
                break;

            case R.id.rl_desc:
                ProductDescDialog productDescDialog = new ProductDescDialog(mActivity,data.getIntrduction());
                productDescDialog.show();
                break;

            case R.id.ll_comment:
                Intent intent = new Intent(mActivity,HylCommentActivity.class);
                intent.putExtra("mainId",mainId);
                startActivity(intent);
                break;

            case R.id.rl_check:
                Intent intent1 = new Intent(mContext,QuarActivity.class);
                List<String> quarantines = data.getQuarantines();
                intent1.putExtra("quarantines", (Serializable) quarantines);
                startActivity(intent1);


//                Intent intent1 = new Intent(mContext,TestActivity.class);
//                List<String> quarantines = data.getQuarantines();
//                intent1.putExtra("quarantines", (Serializable) quarantines);
//                startActivity(intent1);
                break;

            case R.id.iv_sound:
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                boolean muteFlag = false;//获取当前音乐多媒体是否静音
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    muteFlag = audioManager.isStreamMute(AudioManager.STREAM_MUSIC);
                }
                if(muteFlag){
                    iv_sound.setImageResource(R.mipmap.icon_opens);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_UNMUTE, 0);//取消静音
                }else{
                    iv_sound.setImageResource(R.mipmap.icon_close);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE , 0);//设为静音
                }
                break;

            case R.id.ll_skill_active:
                Intent intent2 = new Intent(mActivity,HylActiveDetailActivity.class);
                intent2.putExtra("activeId",skillId);
                startActivity(intent2);
                break;

            case R.id.ll_team_active:
                Intent intent3 = new Intent(mActivity,HylActiveDetailActivity.class);
                intent3.putExtra("activeId",teamId);
                startActivity(intent3);
                break;

            case R.id.ll_full_active:
                Intent intent4 = new Intent(mActivity,FullActiveActivity.class);
                intent4.putExtra("fullId", fullId);
                startActivity(intent4);
                break;
        }
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    /**
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum(CartNumHylEvent cartNumHylEvent) {
        getCartNum();
    }





    public static Date getNowDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStrings = formatters.format(currentTimes);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatters.parse(dateStrings, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }

}
