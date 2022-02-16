package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/9/10
 */
public class HylTestActivity extends Base2Activity {
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.test1);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickListener() {

    }

    //    @Override
//    public boolean handleExtra(Bundle savedInstanceState) {
//        return false;
//    }
//
//    @Override
//    public void setContentView() {
//        setContentView(R.layout.test1);
//    }
//
//    @Override
//    public void setViewData() {
//
//    }
//
//    @Override
//    public void setClickListener() {
//
//    }


    //    List<String> list = new ArrayList<>();
//    @Override
//    public boolean handleExtra(Bundle savedInstanceState) {
//        return false;
//    }
//
//    @Override
//    public void setContentView() {
//        setContentView(R.layout.test1);
//    }
//
//    @Override
//    public void setViewData() {
//
//    }
//
//    @Override
//    public void setClickListener() {

//    }
}
