package com.barter.hyl.app.utils;

/**
 * Created by ${王涛} on 2021/5/21
 */
public class Time {
    public static long getTime(long l) {
        if(l>3600) {
            return 3600;
        }else {
            return l;
        }
    }
}
