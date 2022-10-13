package com.barter.hyl.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.TestAdapter;
import com.barter.hyl.app.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    List<String> quarantines = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test);
    }


    @Override
    public void setViewData() {
        tv_title.setText("检疫证明");
        for (int i = 0; i < 50; i++) {
            quarantines.add(i+"s");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        TestAdapter testAdapter = new TestAdapter(R.layout.item_test1,quarantines);
        recyclerView.setAdapter(testAdapter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                    Log.d("efesfewsf.....",i1+"-----");
//                }
//            });
//        }
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.scrollTo(0,500);
            }
        });

    }

    @Override
    public void setClickListener() {

    }
}
