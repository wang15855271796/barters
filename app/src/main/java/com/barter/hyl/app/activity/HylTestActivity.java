package com.barter.hyl.app.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.barter.hyl.app.R;

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
