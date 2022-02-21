package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylReasonAdapter;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王涛} on 2021/9/14
 */
public class HylReasonActivity extends BaseActivity {
    @BindView(R.id.tv_commit)
    TextView tv_commit;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_other)
    RelativeLayout rl_other;
    //    @BindView(R.id.iv_icon)
//    ImageView iv_icon;
    int selectionPosition = 0;
    private HylReasonAdapter hylReasonAdapter;
    List<String> list = new ArrayList<>();
    String phone;
    String [] arrs  = {"门店倒闭不干了","已有其他账号，这是多余账号","短信太多，造成打扰","菜品质量不满意","售后服务不满意","配送不及时","其他"};
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_reason_hyl);
    }


    @Override
    public void setViewData() {
        tv_title.setText("注销");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ButterKnife.bind(this);

        rl_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPreferencesUtil.getString(mContext, "reason")!=null&&
                        !TextUtils.isEmpty(SharedPreferencesUtil.getString(mContext, "reason"))) {
                    String reason = SharedPreferencesUtil.getString(mContext, "reason");
                    Intent intent = new Intent(mActivity,HylCancleResultActivity.class);
                    intent.putExtra("reason",reason);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }else {
                    ToastUtil.showSuccessMsg(mContext,"请选择注销理由");
                }


            }
        });

        final List<String> list = Arrays.asList(arrs);
        hylReasonAdapter = new HylReasonAdapter(R.layout.item_reason_hyl,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(hylReasonAdapter);
        hylReasonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectionPosition = position;
                hylReasonAdapter.selectionPosition(position);
                SharedPreferencesUtil.saveString(mContext,"reason",list.get(position));
                hylReasonAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void setClickListener() {

    }
}
