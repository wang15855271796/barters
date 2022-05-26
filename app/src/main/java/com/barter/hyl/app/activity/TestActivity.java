package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.util.Log;
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
        List<String> quarantines = (List<String>) getIntent().getSerializableExtra("quarantines");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        TestAdapter testAdapter = new TestAdapter(R.layout.item_quar,quarantines);
        recyclerView.setAdapter(testAdapter);
    }

    @Override
    public void setClickListener() {

    }
}
