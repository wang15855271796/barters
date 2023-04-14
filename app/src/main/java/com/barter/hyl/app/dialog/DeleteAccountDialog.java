package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.view.StringSpecialHelper;


public abstract class DeleteAccountDialog extends Dialog {
    Context mContext;
    TextView tv_continue;
    TextView tv_cancel;
    TextView tv_message;
    public DeleteAccountDialog(Context mContext) {
        super(mContext, R.style.promptDialog);
        setContentView(R.layout.dialog_delete_account);
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        tv_continue = findViewById(R.id.tv_continue);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_message = findViewById(R.id.tv_message);
        String message = tv_message.getText().toString();
        SpannableStringBuilder account = StringSpecialHelper.buildSpanColorStyle(message, 2,
                4, Color.parseColor("#FF0000"));
        tv_message.setText(account);
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
