package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylImageDetailAdapter;
import com.barter.hyl.app.adapter.PicVideoAdapter;
import com.barter.hyl.app.adapter.QuarAdapter;
import com.barter.hyl.app.adapter.TestAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.HylCommonDetailModel;
import com.barter.hyl.app.model.PicVideoModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.NumIndicator;
import com.bumptech.glide.Glide;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuarActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    List<String> quarantines;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        quarantines = (List<String>) getIntent().getSerializableExtra("quarantines");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test);
    }


    @Override
    public void setViewData() {
        tv_title.setText("检疫证明");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        QuarAdapter testAdapter = new QuarAdapter(R.layout.item_quar,quarantines);
        recyclerView.setAdapter(testAdapter);
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
