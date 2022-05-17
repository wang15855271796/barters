package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.app.model.CompanyListModel;
import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.CompanyInfoModel;
import com.barter.hyl.app.model.CouponListsModel;
import com.barter.hyl.app.model.FullActiveCouponListModel;
import com.barter.hyl.app.model.FullActiveDetailModel;
import com.barter.hyl.app.model.HylMyCollectionModel;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.barter.hyl.app.model.HylOneRegisterModel;
import com.barter.hyl.app.model.MyJifenModel;
import com.barter.hyl.app.model.HylMyModel;
import com.barter.hyl.app.model.TipsModel;

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
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Submit_Apply)
        Observable<BaseModel> setParams(@Field("companyName") String companyName,
                                        @Field("shortName") String shortName,
                                        @Field("companyAddress") String companyAddress,
                                        @Field("contactName") String contactName,
                                        @Field("contactPhone") String contactPhone,
                                        @Field("licenseNoPic") String licenseNoPic,
                                        @Field("qualification") String qualification,
                                        @Field("legalIdCard") String legalIdCard,
                                        @Field("idCardFrontPic") String idCardFrontPic,
                                        @Field("idCardBackPic") String idCardBackPic,
                                        @Field("idCardPic") String idCardPic,
                                        @Field("businessLicense") String businessLicense,
                                        @Field("phone") String phone
                                        );
    }
    public static Observable<BaseModel>submitApply(Context context,String companyName,String shortName,String companyAddress,String contactName,String contactPhone,String licenseNoPic,
                                                   String qualification,String legalIdCard,String idCardFrontPic,String idCardBackPic,String idCardPic,String businessLicense,String phone) {
        Observable<BaseModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(SubmitApplyService.class).setParams(companyName,shortName,companyAddress,contactName,contactPhone,
                licenseNoPic,qualification,legalIdCard,idCardFrontPic,idCardBackPic,idCardPic,businessLicense,phone);
        return myModelObservable;
    }

    //满赠活动详情
    public interface FullActiveDetailService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Full_Active_Detail)
        Observable<FullActiveDetailModel> setParams(@Field("fullId") String fullId);
    }

    public static Observable<FullActiveDetailModel>fullActiveDetail(Context context, String fullId) {
        Observable<FullActiveDetailModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(FullActiveDetailService.class).setParams(fullId);
        return myModelObservable;
    }

    //满赠活动查看优惠券
    public interface FullActiveCouponListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Full_Active_CouponList)
        Observable<FullActiveCouponListModel> setParams(@Field("poolNo") String poolNo);
    }

    public static Observable<FullActiveCouponListModel>fullActiveCouponList(Context context, String poolNo) {
        Observable<FullActiveCouponListModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(FullActiveCouponListService.class).setParams(poolNo);
        return myModelObservable;
    }

    public interface CouponGoodsListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Coupon_GoodsList)
        Observable<CouponListsModel> setParams(@Field("poolNo") String poolNo,
                                                        @Field("pageNum") int pageNum,
                                                        @Field("pageSize") int pageSize);
    }

    public static Observable<CouponListsModel>couponGoodList(Context context, String poolNo, int pageNum, int pageSize) {
        Observable<CouponListsModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(CouponGoodsListService.class).setParams(poolNo,pageNum,pageSize);
        return myModelObservable;
    }

    //获取满赠提示
    public interface GetTipService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Tips)
        Observable<TipsModel> setParams(@Field("fullId") String fullId);
    }

    public static Observable<TipsModel>getTips(Context context, String fullId) {
        Observable<TipsModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(GetTipService.class).setParams(fullId);
        return myModelObservable;
    }
    //更改登录企业
    public interface ChangeCompanyService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Change_Company)
        Observable<BaseModel> setParams(@Field("companyId") String companyId);
    }

    public static Observable<BaseModel>changeCompany(Context context, String companyId) {
        Observable<BaseModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(ChangeCompanyService.class).setParams(companyId);
        return myModelObservable;
    }

    //企业信息-new
    public interface CompanyInfoService {
        @POST(AppInterfaceAddress.Company_Info)
        Observable<CompanyInfoModel> setParams();
    }

    public static Observable<CompanyInfoModel>getCompanyInfo(Context context) {
        Observable<CompanyInfoModel> myModelObservable = RestHelper.getBaseRetrofit(context).create(CompanyInfoService.class).setParams();
        return myModelObservable;
    }
}
