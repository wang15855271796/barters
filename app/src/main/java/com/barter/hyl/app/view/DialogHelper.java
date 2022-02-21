package com.barter.hyl.app.view;

import android.content.Context;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;

/**
 * Created by Administrator on 2018/4/13.
 */

public class DialogHelper {
    private static BottomSheetDialog bottomSheetDialog;
    /**
     * @param context
     * @param
     */
    public static void showLogoutDialog(Context context, View.OnClickListener onClickListener) {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.dialog_logout_hyl);
        bottomSheetDialog.show();
        TextView tvConfirm = (TextView) bottomSheetDialog.findViewById(R.id.tv_dialog_logout_confirm);
        TextView tvCancel = (TextView) bottomSheetDialog.findViewById(R.id.tv_dialog_logout_cancel);
        tvConfirm.setOnClickListener(onClickListener);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    public static void dismissLogoutDialog() {
        bottomSheetDialog.dismiss();
    }
}
