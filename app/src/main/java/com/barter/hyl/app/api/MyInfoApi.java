package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylMyCollectionModel;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.barter.hyl.app.model.MyJifenModel;
import com.barter.hyl.app.model.HylMyModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/8/19
 */
public class MyInfoApi {
    //我的
    public interface MyService {
        @POST(AppInterfaceAddress.My_Info)
        Observable<HylMyModel> setParams();
    }

    public static Observable<HylMyModel> getMyInfo(Context context) {
        Observable<HylMyModel> homeBaseModelObservable = RestHelper.getBaseRetrofit(context).create(MyService.class).setParams();
        return homeBaseModelObservable;
    }

    //我的收藏列表
    public interface MyCollectionService {
        @POST(AppInterfaceAddress.My_Collection)
        Observable<HylMyCollectionModel> setParams();
    }

    public static Observable<HylMyCollectionModel> getCollectionList(Context context) {
        Observable<HylMyCollectionModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(MyCollectionService.class).setParams();
        return myModelObservable;
    }

    //我的积分列表
    public interface MyJifenService {
        @POST(AppInterfaceAddress.My_Jifen)
        Observable<MyJifenModel> setParams();
    }

    public static Observable<MyJifenModel> getJifenList(Context context) {
        Observable<MyJifenModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(MyJifenService.class).setParams();
        return myModelObservable;
    }

    //积分UI换
    public interface ExChangeService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Ex_Change)
        Observable<BaseModel> setParams(@Field("poolNo") String poolNo);
    }

    public static Observable<BaseModel> exChange(Context context,String poolNo) {
        Observable<BaseModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(ExChangeService.class).setParams(poolNo);
        return myModelObservable;
    }

    //积分UI换
    public interface MyCouponService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.My_Coupon_List)
        Observable<HylMyCouponModel> setParams(@Field("pageNum") int pageNum,
                                               @Field("pageSize") int pageSize,
                                               @Field("state") String state);
    }

    public static Observable<HylMyCouponModel> getMyCouponList(Context context, int pageNum, int pageSize, String state) {
        Observable<HylMyCouponModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(MyCouponService.class).setParams(pageNum,pageSize,state);
        return myModelObservable;
    }

}
