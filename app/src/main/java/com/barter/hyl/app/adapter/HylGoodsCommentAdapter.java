package com.barter.hyl.app.adapter;

import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.JudgeActivity;
import com.barter.hyl.app.model.HylEvalGoodsModel;
import com.barter.hyl.app.view.FullyGridLayoutManager;
import com.barter.hyl.app.view.StarBarView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/17
 */
public class HylGoodsCommentAdapter extends BaseQuickAdapter<HylEvalGoodsModel.DataBean,BaseViewHolder> {

    Activity context;
    HylShopImageViewsAdapter shopImageViewAdapter;
    OnAddClickListener mOnAddPicClickListener;
    public HylGoodsCommentAdapter(int layoutResId, @Nullable List<HylEvalGoodsModel.DataBean> data, JudgeActivity judgeActivity, OnAddClickListener mOnAddPicClickListener) {
        super(layoutResId, data);
        this.context = judgeActivity;
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylEvalGoodsModel.DataBean item) {
        helper.setIsRecyclable(false);
        final TextView tv_evaluate_status = helper.getView(R.id.tv_evaluate_status);
        final StarBarView star_bar = helper.getView(R.id.star_bar);
        TextView tv_name = helper.getView(R.id.tv_name);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        tv_name.setText(item.getProdName());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        EditText et_content = helper.getView(R.id.et_content);
        //你这边没赋值了大兄弟
        et_content.setText(item.getContent());
        //星星的你自己赋值
        if(item.getLevel()!=null) {
            star_bar.setStarRating(Float.parseFloat(item.getLevel()));
            if(item.getLevel().equals("5")) {
                tv_evaluate_status.setText("很满意");

            }else if(item.getLevel().equals("4")) {
                tv_evaluate_status.setText("满意");

            }else if(item.getLevel().equals("3")) {
                tv_evaluate_status.setText("一般");

            }else if(item.getLevel().equals("2")) {
                tv_evaluate_status.setText("不满意");

            }else if(item.getLevel().equals("1")) {
                tv_evaluate_status.setText("非常差");
            }
        }


        star_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setStarName(item,tv_evaluate_status, star_bar.getStarRating());
            }
        });

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)){
                    item.setContent(s.toString());
                }else {
                    item.setContent("");
                }

            }
        });
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
//        hylShopImageViewAdapter = new HylShopImageViewsAdapter(R.layout.item_filter_image, new HylShopImageViewsAdapter.onAddPicClickListener() {
//            @Override
//            public void onAddPicClick(int pos) {
//                //获取当前点击的是几个recycleview的下标  外加内部点击的是哪一个位置。
//                Log.e("点击的是第几个位置","列表适配器位置"+helper.getLayoutPosition()+"图片适配器位置"+pos);
//                mOnAddPicClickListener.onAddPicClick(hylShopImageViewAdapter,helper.getLayoutPosition(),pos);
//            }
//        });
        final HylShopImageViewAdapter hylShopImageViewAdapter =new HylShopImageViewAdapter(mContext, new HylShopImageViewAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int pos) {

                mOnAddPicClickListener.onAddPicClick(helper.getLayoutPosition(),pos);
//                EventBus.getDefault().post(new CartListHylEvent(hylShopImageViewAdapter));
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(hylShopImageViewAdapter);
        hylShopImageViewAdapter.setList(item.getPics());
        hylShopImageViewAdapter.notifyDataSetChanged();
    }

//    String star;
    private void setStarName(HylEvalGoodsModel.DataBean item, TextView tv_content, float star_num) {
        if (star_num == 5.0f) {
            tv_content.setText("很满意");
            String star = "5";
            item.setLevel(star);
        } else if (star_num == 4.0f) {
            tv_content.setText("满意");
            String star = "4";
            item.setLevel(star);
        } else if (star_num == 3.0f) {
            tv_content.setText("一般");
            String star = "3";
            item.setLevel(star);
        } else if (star_num == 2.0f) {
            tv_content.setText("不满意");
            String star = "2";
            item.setLevel(star);
        } else if (star_num == 1.0f) {
            tv_content.setText("非常差");
            String star = "1";
            item.setLevel(star);
        }
    }

    public interface OnAddClickListener {
        void onAddPicClick(int pos,int SelectPos);
    }
}
