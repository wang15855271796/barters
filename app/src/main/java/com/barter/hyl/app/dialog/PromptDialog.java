package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;

/**
 * Created by ${王涛} on 2021/9/14
 */
public abstract class PromptDialog extends Dialog {
    Context mContext;

    public TextView tv_sure,hint;
    public TextView title;

    public PromptDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_prompt_hyl);

        mContext = context;

        initView();
        initAction();
    }

    private void initView() {
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        hint = (TextView) findViewById(R.id.hint);
        title = findViewById(R.id.title);
    }


    private void initAction() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
    }

    public abstract void Confirm();

}
