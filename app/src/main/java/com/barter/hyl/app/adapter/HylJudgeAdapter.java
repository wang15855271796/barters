package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.JudgeModel;
import com.barter.hyl.app.view.StarBarView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/23
 */
public class HylJudgeAdapter extends BaseQuickAdapter<JudgeModel.DataBean,BaseViewHolder> {

    public HylJudgeAdapter(int layoutResId, @Nullable List<JudgeModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JudgeModel.DataBean item) {

        ImageView iv_pic = helper.getView(R.id.iv_pic);
        StarBarView starBar = helper.getView(R.id.star_bar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_evaluate_status = helper.getView(R.id.tv_evaluate_status);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        starBar.setStar(Integer.parseInt(item.getLevel()));
        tv_name.setText(item.getProdName());
        tv_content.setText(item.getContent());

        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        CheckJudgeIvAdpater checkJudgeIvAdpater = new CheckJudgeIvAdpater(R.layout.item_img_hyl,item.getPics());
        recyclerView.setAdapter(checkJudgeIvAdpater);

        if (item.getLevel() != null) {
            starBar.setVisibility(View.VISIBLE);
            tv_evaluate_status.setVisibility(View.VISIBLE);

            if (item.getLevel().equals("5")) {
                starBar.setStarRating(5.0f);
                setStarName(tv_evaluate_status, 5.0f);


            } else if (item.getLevel().equals("4")) {
                starBar.setStarRating(4.0f);
                setStarName(tv_evaluate_status, 4.0f);


            } else if (item.getLevel().equals("3")) {
                starBar.setStarRating(3.0f);
                setStarName(tv_evaluate_status, 3.0f);


            } else if (item.getLevel().equals("2")) {
                starBar.setStarRating(2.0f);
                setStarName(tv_evaluate_status, 2.0f);

            } else if (item.getLevel().equals("1")) {
                starBar.setStarRating(1.0f);
                setStarName(tv_evaluate_status, 1.0f);
            }
        } else {
            starBar.setVisibility(View.GONE);
            tv_evaluate_status.setVisibility(View.GONE);
        }

    }

    /**
     * 设置星星文字
     */
    private void setStarName(TextView tv_content, float star_num) {
        if (star_num == 5.0f) {
            tv_content.setText("很满意");

        } else if (star_num == 4.0f) {
            tv_content.setText("满意");

        } else if (star_num == 3.0f) {
            tv_content.setText("一般");

        } else if (star_num == 2.0f) {
            tv_content.setText("不满意");

        } else if (star_num == 1.0f) {
            tv_content.setText("非常差");

        }

    }
}
