package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.barter.hyl.app.R;

public abstract class OrderErrorDialog extends Dialog {
    Activity mContext;
    TextView tv_ok;
    TextView tv_message;
    String message;
    public OrderErrorDialog(@NonNull Activity context, String message) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_order_delete);
        mContext = context;
        this.message = message;
        initView();
    }


    private void initView() {
        tv_ok = findViewById(R.id.tv_ok);
        tv_message = findViewById(R.id.tv_message);
        tv_message.setText(message);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
            }
        });

    }

    public abstract void Confirm();
}
