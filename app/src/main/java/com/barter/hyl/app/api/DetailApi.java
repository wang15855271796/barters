package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.HylActiveDetailModel;
import com.barter.hyl.app.model.HylAddCartModel;
import com.barter.hyl.app.model.HylAddToCartModel;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylCartListModel;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylChangeSpecModel;
import com.barter.hyl.app.model.HylCollectionModel;
import com.barter.hyl.app.model.HylCommentModel;
import com.barter.hyl.app.model.HylCommonDetailModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylSearchListModel;
import com.barter.hyl.app.model.HylSearchResultModel;
import com.barter.hyl.app.model.HylSettleModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/8/18
 */
public class DetailApi {
    //普通商品详情
    public interface DetailService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Common_Data)
        Observable<HylCommonDetailModel> setParams(@Field("mainId") String mainId);
    }

    public static Observable<HylCommonDetailModel> getDetail(Context context, String mainId) {
        Observable<HylCommonDetailModel> detailModelObservable = RestHelper.getBaseRetrofit(context).create(DetailService.class).setParams(mainId);
        return detailModelObservable;
    }

    //活动商品详情
    public interface ActiveService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Active_Data)
        Observable<HylActiveDetailModel> setParams(@Field("activeId") int activeId);
    }

    public static Observable<HylActiveDetailModel> getActiveDetail(Context context, int activeId) {
        Observable<HylActiveDetailModel> detailModelObservable = RestHelper.getBaseRetrofit(context).create(ActiveService.class).setParams(activeId);
        return detailModelObservable;
    }

    //收藏
    public interface CollectionService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Collection)
        Observable<HylCollectionModel> setParams(@Field("mainId") String mainId);
    }

    public static Observable<HylCollectionModel> getCollection(Context context, String mainId) {
        Observable<HylCollectionModel> baseModelObservable = RestHelper.getBaseRetrofit(context).create(CollectionService.class).setParams(mainId);
        return baseModelObservable;
    }

    //购物车弹窗
    public interface AddCartService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Dialog_Data)
        Observable<HylAddCartModel> setParams(@Field("mainId") String mainId);
    }

    public static Observable<HylAddCartModel> getCart(Context context, String mainId) {
        Observable<HylAddCartModel> baseModelObservable = RestHelper.getBaseRetrofit(context).create(AddCartService.class).setParams(mainId);
        return baseModelObservable;
    }

    //切换规格
    public interface ChangeSpecService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Change_Spec)
        Observable<HylChangeSpecModel> setParams(@Field("productId") int productId);
    }

    public static Observable<HylChangeSpecModel> changeSpec(Context context, int productId) {
        Observable<HylChangeSpecModel> changeSpecModelObservable = RestHelper.getBaseRetrofit(context).create(ChangeSpecService.class).setParams(productId);
        return changeSpecModelObservable;
    }

    //加入购物车
    public interface AddedCartService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Add_Cart)
        Observable<HylAddToCartModel> setParams(@Field("businessType") int businessType,
                                                @Field("priceId") int priceId,
                                                @Field("businessId") int businessId,
                                                @Field("num") int num
                                           );
    }

    public static Observable<HylAddToCartModel> addCart(Context context, int businessType, int priceId, int businessId, int num) {
        Observable<HylAddToCartModel> changeSpecModelObservable = RestHelper.getBaseRetrofit(context).create(AddedCartService.class)
                .setParams(businessType,priceId,businessId,num);
        return changeSpecModelObservable;
    }

    //搜索推荐
    public interface SearchRecommendService {
        @POST(AppInterfaceAddress.Search_Recommend)
        Observable<HylSearchListModel> setParams();
    }

    public static Observable<HylSearchListModel> getRecommendList(Context context) {
        Observable<HylSearchListModel> changeSpecModelObservable = RestHelper.getBaseRetrofit(context).create(SearchRecommendService.class)
                .setParams();
        return changeSpecModelObservable;
    }

    //搜索结果
    public interface SearchResultService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Search_Result)
        Observable<HylSearchResultModel> setParams(@Field("pageNum") int pageNum,
                                                   @Field("pageSize") int pageSize,
                                                   @Field("searchKey") String searchKey,
                                                   @Field("saleDesc") int saleDesc,
                                                   @Field("priceDesc") int priceDesc);
    }

    public static Observable<HylSearchResultModel> getResultList(Context context, int pageNum, int pageSize, String searchKey,int saleDesc,int priceDesc) {
        Observable<HylSearchResultModel> changeSpecModelObservable = RestHelper.getBaseRetrofit(context).create(SearchResultService.class)
                .setParams(pageNum,pageSize,searchKey,saleDesc,priceDesc);
        return changeSpecModelObservable;
    }

    //购物车角标
    public interface CartNumService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cart_Num)
        Observable<HylCartNumModel> setParams(@Field("flag") int flag);
    }

    public static Observable<HylCartNumModel> getCartNum(Context context, int flag) {
        Observable<HylCartNumModel> changeSpecModelObservable = RestHelper.getBaseRetrofit(context).create(CartNumService.class)
                .setParams(flag);
        return changeSpecModelObservable;
    }

    //购物车列表
    public interface CartListService {
        @POST(AppInterfaceAddress.Cart_List)
        Observable<HylCartListModel> setParams();
    }

    public static Observable<HylCartListModel> getCartList(Context context) {
        Observable<HylCartListModel> changeSpecModelObservable = RestHelper.getBaseRetrofit(context).create(CartListService.class)
                .setParams();
        return changeSpecModelObservable;
    }

    //加入到购物车-累计
    public interface AddCartsService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Add_Carts)
        Observable<HylLoginModel> setParams(@Field("businessType") int businessType,
                                            @Field("priceId") int priceId,
                                            @Field("businessId") int businessId,
                                            @Field("num") int num);
    }

    public static Observable<HylLoginModel> getAddCarts(Context context, int businessType, int priceId, int businessId, int num) {
        Observable<HylLoginModel> changeSpecModelObservable = RestHelper.getBaseRetrofit(context).create(AddCartsService.class).setParams(businessType,priceId,businessId,num);
        return changeSpecModelObservable;
    }

    /**
     * 删除购物车
     */
    public interface DeleteCartListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delete_Cart_List)
        Observable<BaseModel> setParams(@Field("cartIds") String cartIds);
    }

    public static Observable<BaseModel> deleteCartList(Context context,String cartIds) {
        Observable<BaseModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(DeleteCartListService.class)
                .setParams(cartIds);
        return cartListModelObservable;
    }

    /**
     * 结算
     */
    public interface SettleService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Settle)
        Observable<HylSettleModel> setParams(@Field("cartIds") String cartIds, @Field("giftNo") String giftNo);
    }

    public static Observable<HylSettleModel> settle(Context context, String cartIds, String giftNo) {
        Observable<HylSettleModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(SettleService.class)
                .setParams(cartIds,giftNo);
        return cartListModelObservable;
    }

    //商品评论
    public interface CommentService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Comment)
        Observable<HylCommentModel> setParams(@Field("pageNum") int pageNum,
                                              @Field("pageSize") int pageSize,
                                              @Field("mainId") int mainId);
    }

    public static Observable<HylCommentModel> commentList(Context context, int pageNum, int pageSize, int mainId) {
        Observable<HylCommentModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(CommentService.class)
                .setParams(pageNum,pageSize,mainId);
        return cartListModelObservable;
    }

    //删除收藏
    public interface DeleteCollectionService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delete_Collection)
        Observable<BaseModel> setParams(@Field("collectIds") String collectIds);
    }

    public static Observable<BaseModel> deleteCollection(Context context,String collectIds) {
        Observable<BaseModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(DeleteCollectionService.class)
                .setParams(collectIds);
        return cartListModelObservable;
    }
}
