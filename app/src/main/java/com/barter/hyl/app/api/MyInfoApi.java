package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.app.model.CompanyListModel;
import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylMyCollectionModel;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.barter.hyl.app.model.HylOneRegisterModel;
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

    //添加企业-新接口
    public interface AddCompanyService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Add_Company)
        Observable<BaseModel> setParams(@Field("authCode") String authCode);
    }

    public static Observable<BaseModel>addCompany(Context context,String authCode) {
        Observable<BaseModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(AddCompanyService.class).setParams(authCode);
        return myModelObservable;
    }

    //获取公司简称
    public interface CompanyNameService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Company_Name)
        Observable<HylOneRegisterModel> setParams(@Field("authCode") String authCode);
    }

    public static Observable<HylOneRegisterModel>getCompanyName(Context context, String authCode) {
        Observable<HylOneRegisterModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(CompanyNameService.class).setParams(authCode);
        return myModelObservable;
    }

    //企业选择-新
    public interface CompanyChooseService {
        @POST(AppInterfaceAddress.Company_Choose)
        Observable<CompanyListModel> setParams();
    }

    public static Observable<CompanyListModel>companyChoose(Context context) {
        Observable<CompanyListModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(CompanyChooseService.class).setParams();
        return myModelObservable;
    }

    //提交申请
    public interface SubmitApplyService {
        @POST(AppInterfaceAddress.Submit_Apply)
        Observable<BaseModel> setParams(@Field("companyName") String companyName,
                                        @Field("shortName") String shortName,
                                        @Field("contactName") String contactName,
                                        @Field("contactPhone") String contactPhone,
                                        @Field("licenseNoPic") String licenseNoPic,
                                        @Field("qualification") String qualification,
                                        @Field("legalIdCard") String legalIdCard,
                                        @Field("idCardFrontPic") String idCardFrontPic,
                                        @Field("idCardBackPic") String idCardBackPic,
                                        @Field("idCardPic") String idCardPic,
                                        @Field("businessLicense") String businessLicense
                                        );
    }
    public static Observable<BaseModel>submitApply(Context context,String companyName,String shortName,String contactName,String contactPhone,String licenseNoPic,
                                                   String qualification,String legalIdCard,String idCardFrontPic,String idCardBackPic,String idCardPic,String businessLicense) {
        Observable<BaseModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(SubmitApplyService.class).setParams(companyName,shortName,contactName,contactPhone,
                licenseNoPic,qualification,legalIdCard,idCardFrontPic,idCardBackPic,idCardPic,businessLicense);
        return myModelObservable;
    }

}
