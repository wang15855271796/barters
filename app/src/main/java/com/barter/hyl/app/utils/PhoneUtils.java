package com.barter.hyl.app.utils;

import android.text.TextUtils;

/**
 * Created by ${王涛} on 2021/8/2
 */
public class PhoneUtils {
    /**
     * 验证手机号码
     * @param phone
     * @return
     */
    public static int checkPhoneNum(String phone){
        if (TextUtils.isEmpty(phone)){
            return 2;
        }else if (!phone.matches("^[1][0-9]{10}$")){
            return 1;
        }else {
            return 0;
        }
    }
}
