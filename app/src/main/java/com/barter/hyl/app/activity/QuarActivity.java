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
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    List<String> quarantines = new ArrayList<>();
    QuarAdapter quarAdapter;
    String mainId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        mainId = getIntent().getStringExtra("mainId");
//        quarantines = (List<String>) getIntent().getSerializableExtra("quarantines");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_quar);
    }

    @Override
    public void setViewData() {
        tv_title.setText("检疫证明");
        getDetail(mainId);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        quarAdapter = new QuarAdapter(R.layout.item_quar,quarantines);
        recyclerView.setAdapter(quarAdapter);
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

    private void getDetail(String mainId) {
        DetailApi.getDetail(mActivity,mainId)
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
                            Log.d("fdsgfsf......","sdsd");
                            if(hylCommonDetailModel.getData()!=null) {
                                HylCommonDetailModel.DataBean data = hylCommonDetailModel.getData();
                                if(data.getQuarantines()!=null&&data.getQuarantines().size()>0) {

                                    quarantines.addAll(data.getQuarantines());
                                    quarAdapter.notifyDataSetChanged();

                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCommonDetailModel.getMessage());
                        }
                    }
                });
    }

}
