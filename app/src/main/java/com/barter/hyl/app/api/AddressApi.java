package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.HylAddressDetailModel;
import com.barter.hyl.app.model.HylAddressListModel;
import com.barter.hyl.app.model.HylAreaModel;
import com.barter.hyl.app.model.BaseModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/8/25
 */
public class AddressApi {
    /**
     * 添加地址
     */
    public interface AddAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Add_Address)
        Observable<BaseModel> setParams(@Field("userName") String userName,
                                        @Field("contactPhone") String contactPhone,
                                        @Field("shopName") String shopName,
                                        @Field("provinceCode") String provinceCode,
                                        @Field("cityCode") String cityCode,
                                        @Field("areaCode") String areaCode,
                                        @Field("detailAddress") String detailAddress,
                                        @Field("isDefault") int isDefault);
    }

    public static Observable<BaseModel> AddAddress(Context context, String userName,
                                                     String contactPhone,String shopName,
                                                     String provinceCode,String cityCode,String areaCode,
                                                     String detailAddress,int isDefault) {
        Observable<BaseModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(AddAddressService.class)
                .setParams(userName,contactPhone,shopName,provinceCode,cityCode,areaCode,detailAddress,isDefault);
        return cartListModelObservable;
    }

    /**
     * 地址区域获取
     */
    public interface AddressAreaService {

        @POST(AppInterfaceAddress.Address_Area)
        Observable<HylAreaModel> setParams();
    }

    public static Observable<HylAreaModel> AddressArea(Context context) {
        Observable<HylAreaModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(AddressAreaService.class)
                .setParams();
        return cartListModelObservable;
    }

    /**
     * 行业资讯-全部地区
     */
    public interface AllAreaService {
        @POST(AppInterfaceAddress.All_Area)
        Observable<HylAreaModel> setParams();
    }

    public static Observable<HylAreaModel> getAllArea(Context context) {
        Observable<HylAreaModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(AllAreaService.class)
                .setParams();
        return cartListModelObservable;
    }

    /**
     * 获取地址列表
     */
    public interface AddressListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Address_List)
        Observable<HylAddressListModel> setParams(@Field("pageNum") int pageNum, @Field("pageSize") int pageSize);
    }

    public static Observable<HylAddressListModel> AddressList(Context context, int pageNum, int pageSize) {
        Observable<HylAddressListModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(AddressListService.class)
                .setParams(pageNum,pageSize);
        return cartListModelObservable;
    }

    /**
     * 设置默认地址
     */
    public interface AddressDefaultService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Address_Default)
        Observable<BaseModel> setParams(@Field("addressId") int addressId);
    }

    public static Observable<BaseModel> AddressDefault(Context context,int addressId) {
        Observable<BaseModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(AddressDefaultService.class).setParams(addressId);
        return cartListModelObservable;
    }

    /**
     * 删除地址
     */

    public interface DeleteAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delete_Address)
        Observable<BaseModel> setParams(@Field("addressId") int addressId);
    }

    public static Observable<BaseModel> AddressDelete(Context context,int addressId) {
        Observable<BaseModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(DeleteAddressService.class).setParams(addressId);
        return cartListModelObservable;
    }

    /**
     * 编辑地址
     */

    public interface EditAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Edit_Address)
        Observable<BaseModel> setParams(@Field("userName") String userName,
                                        @Field("contactPhone") String contactPhone,
                                        @Field("shopName") String shopName,
                                        @Field("provinceCode") String provinceCode,
                                        @Field("cityCode") String cityCode,
                                        @Field("areaCode") String areaCode,
                                        @Field("detailAddress") String detailAddress,
                                        @Field("isDefault") int isDefault,
                                        @Field("addressId") int addressId);
    }

    public static Observable<BaseModel> AddressEdit(Context context, String userName,
                                                    String contactPhone,String shopName,
                                                    String provinceCode,String cityCode,String areaCode,
                                                    String detailAddress,int isDefault,int addressId) {
        Observable<BaseModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(EditAddressService.class)
                .setParams(userName,contactPhone,shopName,provinceCode,cityCode,areaCode,detailAddress,isDefault,addressId);
        return cartListModelObservable;
    }

    /**
     * 地址详情
     */
    public interface DetailAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Address_Detail)
        Observable<HylAddressDetailModel> setParams(@Field("addressId") int addressId);
    }

    public static Observable<HylAddressDetailModel> AddressDetail(Context context, int addressId) {
        Observable<HylAddressDetailModel> cartListModelObservable = RestHelper.getBaseRetrofit(context).create(DetailAddressService.class).setParams(addressId);
        return cartListModelObservable;
    }
}
