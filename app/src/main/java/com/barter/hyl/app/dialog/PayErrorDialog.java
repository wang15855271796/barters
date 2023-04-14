package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.barter.hyl.app.R;

public abstract class PayErrorDialog extends Dialog {
    Context mContext;
    TextView tv_continue;
    TextView tv_message;
    TextView tv_cancel;
    String message;
    public PayErrorDialog(@NonNull Context context, String message) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_pay_error);
        mContext = context;
        this.message = message;
        initView();
    }


    private void initView() {
        tv_continue = findViewById(R.id.tv_continue);
        tv_message = findViewById(R.id.tv_message);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_message.setText(message);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancel();
            }
        });
    }

    public abstract void Confirm();
    public abstract void Cancel();
}
