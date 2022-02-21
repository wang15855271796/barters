package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;


/**
 * Created by ${王涛} on 2021/9/8
 */
public class Privacy3Dialog extends Dialog {
    Activity mContext;
    TextView tv_content;
    TextView tv_look;
    TextView tv_logout;
    String content = "https://shaokao.qoger.com/apph5/html/hyl_yszc.html";
    String register = "https://shaokao.qoger.com/apph5/html/hyl_enter.html";
    public Privacy3Dialog(@NonNull Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy3_hyl);
        mContext = context;
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_logout = findViewById(R.id.tv_logout);
        tv_look = findViewById(R.id.tv_look);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();
            }
        });

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacysDialog privacysDialog = new PrivacysDialog(mContext);
                privacysDialog.show();
                dismiss();
            }
        });
    }

}
