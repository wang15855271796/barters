package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class HylChangeSecretActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_new_secret)
    EditText et_new_secret;
    @BindView(R.id.et_old_secret)
    EditText et_old_secret;
    @BindView(R.id.et_secret_again)
    EditText et_secret_again;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_secret_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("账户与安全");
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_save:
                String oldPwd = et_old_secret.getText().toString();
                String newPwd = et_new_secret.getText().toString();
                String pwdAgain = et_secret_again.getText().toString();
                if(oldPwd!=null&&newPwd!=null&&pwdAgain!=null) {
                    if(newPwd.equals(pwdAgain)) {
//                        checkLoginSecret(oldPwd,newPwd);
                    }
                }
                break;
        }
    }





    public static Date getNowDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStrings = formatters.format(currentTimes);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatters.parse(dateStrings, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }


}
