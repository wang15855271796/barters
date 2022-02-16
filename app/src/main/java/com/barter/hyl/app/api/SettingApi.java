package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylMyCollectionModel;
import com.barter.hyl.app.model.HylSettingModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class SettingApi {

    //我的
    public interface SettingService {
        @POST(AppInterfaceAddress.Setting)
        Observable<HylSettingModel> setParams();
    }

    public static Observable<HylSettingModel> setting(Context context) {
        Observable<HylSettingModel> homeBaseModelObservable = RestHelper.getBaseRetrofit(context).create(SettingService.class).setParams();
        return homeBaseModelObservable;
    }

    //我的收藏列表
    public interface MyCollectionService {
        @POST(AppInterfaceAddress.My_Collection)
        Observable<HylMyCollectionModel> setParams();
    }

    public static Observable<HylMyCollectionModel> getCollectionList(Context context) {
        Observable<HylMyCollectionModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(MyInfoApi.MyCollectionService.class).setParams();
        return myModelObservable;
    }


    //忘记登录密码
    public interface SettingPwdService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Forget_Pwd)
        Observable<BaseModel> setParams(@Field("phone") String phone,@Field("smsCode") String smsCode,@Field("newPwd") String newPwd);
    }

    public static Observable<BaseModel> forgetSecret(Context context,String phone,String smsCode,String newPwd) {
        Observable<BaseModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(SettingPwdService.class).setParams(phone,smsCode,newPwd);
        return myModelObservable;
    }

    //注销
    public interface CancelService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cancel)
        Observable<BaseModel> setParams(@Field("phone") String phone,@Field("verifyCode") String verifyCode,
                                        @Field("pwd") String pwd,@Field("cancelReason") String cancelReason);
    }

    public static Observable<BaseModel> cancel(Context context,String phone,String verifyCode,String pwd,String cancelReason) {
        Observable<BaseModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(CancelService.class).setParams(phone,verifyCode,pwd,cancelReason);
        return myModelObservable;
    }
}
