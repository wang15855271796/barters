package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylCommentModel;
import com.barter.hyl.app.view.StarBarView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class HylCommentAdapter extends BaseQuickAdapter<HylCommentModel.DataBean.ListBean,BaseViewHolder> {

    public HylCommentAdapter(int layoutResId, @Nullable List<HylCommentModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HylCommentModel.DataBean.ListBean item) {
        TextView tv_phone = helper.getView(R.id.tv_phone);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_content = helper.getView(R.id.tv_content);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        RecyclerView recyclerView = helper.getView(R.id.rv_image);
        TextView tv_reply = helper.getView(R.id.tv_reply);
        TextView tv_replay_name = helper.getView(R.id.tv_replay_name);
        final StarBarView starBar = helper.getView(R.id.starBar);
        TextView tv_evaluate_status = helper.getView(R.id.tv_evaluate_status);

        tv_content.setText(item.getContent());
        tv_phone.setText(item.getPhone());
        tv_time.setText(item.getCommentTime());
        tv_reply.setText(item.getReplay());
        tv_replay_name.setText(item.getReplayName());
        Glide.with(mContext).load(item.getHeadPic()).into(iv_pic);


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

//        starBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                starBar.setStarSolid(Float.parseFloat(item.getLevel()));
//            }
//        });


        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        HylCommentImageViewAdapter hylCommentImageViewAdapter = new HylCommentImageViewAdapter(R.layout.item_evalute_image,item.getPics());
        recyclerView.setAdapter(hylCommentImageViewAdapter);
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
