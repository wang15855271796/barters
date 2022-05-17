package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.QuarAdapter;
import com.barter.hyl.app.base.BaseActivity;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;

public class QuarActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    List<String> quarantines;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        quarantines = (List<String>) getIntent().getSerializableExtra("quarantines");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_quar);
    }

    @Override
    public void setViewData() {
        tv_title.setText("检疫证明");
        QuarAdapter quarAdapter = new QuarAdapter(R.layout.item_quar,quarantines);
        recyclerView.setAdapter(quarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
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
