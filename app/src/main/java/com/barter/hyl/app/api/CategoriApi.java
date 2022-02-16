package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.HylChooseCouponModel;
import com.barter.hyl.app.model.HylGoodCateModel;
import com.barter.hyl.app.model.HylGoodListModel;
import com.barter.hyl.app.model.GoodNameModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/8/25
 */
public class CategoriApi {
    /**
     * 商品分类顶部
     */
    public interface GoodCateService {
        @POST(AppInterfaceAddress.Goods_Categori)
        Observable<HylGoodCateModel> setParams();
    }

    public static Observable<HylGoodCateModel> goodsCate(Context context) {
        Observable<HylGoodCateModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(GoodCateService.class).setParams();
        return cartListModelObservable;
    }

    /**
     * 商品分类列表
     */
    public interface GoodListService {
         @FormUrlEncoded
        @POST(AppInterfaceAddress.Goods_List)
        Observable<HylGoodListModel> setParams(@Field("pageNum") int pageNum,
                                               @Field("pageSize") int pageSize,
                                               @Field("brandName") String brandName,
                                               @Field("minPrice") String minPrice,
                                               @Field("maxPrice") String maxPrice,
                                               @Field("saleFlag") int saleFlag,
                                               @Field("priceFlag") int priceFlag,
                                               @Field("firstId") int firstId,
                                               @Field("secondId") int secondId);
    }

    public static Observable<HylGoodListModel> goodsList(Context context, int pageNum, int pageSize, String brandName
            , String minPrice, String maxPrice, int saleFlag, int priceFlag, int firstId, int secondId) {
        Observable<HylGoodListModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(GoodListService.class)
                .setParams(pageNum,pageSize,brandName,minPrice,maxPrice,saleFlag,priceFlag,firstId,secondId);
        return cartListModelObservable;
    }

    /**
     * 商品品牌
     */
    public interface GoodNameService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Goods_Name)
        Observable<GoodNameModel> setParams(@Field("name") String name,
                                            @Field("firstId") int firstId,
                                            @Field("secondId") int secondId);
    }

    public static Observable<GoodNameModel> goodsName(Context context,String name,int firstId,int secondId) {
        Observable<GoodNameModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(GoodNameService.class)
                .setParams(name,firstId,secondId);
        return cartListModelObservable;
    }

    /**
     * 优惠券选择列表
     */
    public interface ChooseCouponService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Choose_Coupon)
        Observable<HylChooseCouponModel> setParams(
                                            @Field("enabled") int enabled,
                                            @Field("cartIds") String cartIds);
    }

    public static Observable<HylChooseCouponModel> couponList(Context context, int enabled, String cartIds) {
        Observable<HylChooseCouponModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(ChooseCouponService.class)
                .setParams(enabled,cartIds);
        return cartListModelObservable;
    }


}
