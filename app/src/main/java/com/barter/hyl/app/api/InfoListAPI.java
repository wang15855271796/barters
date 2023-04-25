package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HomeStyleTabModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.InfoDetailIssueModel;
import com.barter.hyl.app.model.InfoDetailModel;
import com.barter.hyl.app.model.InfoListModel;
import com.barter.hyl.app.model.InfoPubModel;
import com.barter.hyl.app.model.RecommendModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/1/5
 */
public class InfoListAPI {

    private interface InfoList {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_List)
        Observable<InfoListModel> getData(@Field("keyword") String keyword, @Field("type") String type, @Field("pageNum") int pageNum, @Field("pageSize") int pageSize
                , @Field("provinceCode") String provinceCode, @Field("cityCode") String cityCode);
    }

    public static Observable<InfoListModel> requestData(Context context,String keyword,String type, int pageNum, int pageSize,String provinceCode,String cityCode) {
        InfoList service = RestHelper.getBaseRetrofit(context).create(InfoList.class);
        return service.getData(keyword,type,pageNum, pageSize,provinceCode,cityCode);
    }

    private interface InfoDetail {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Detail)
        Observable<InfoDetailModel> getData(@Field("msgId") String msgId);
    }

    public static Observable<InfoDetailModel> getDetail(Context context,String msgId) {
        InfoDetail service = RestHelper.getBaseRetrofit(context).create(InfoDetail.class);
        return service.getData(msgId);
    }

    private interface MyInfoList {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_My_List)
        Observable<InfoListModel> getData(@Field("pageNum") int pageNum, @Field("pageSize") int pageSize);
    }

    public static Observable<InfoListModel> getMyList(Context context, int pageNum,int pageSize) {
        MyInfoList service = RestHelper.getBaseRetrofit(context).create(MyInfoList.class);
        return service.getData(pageNum,pageSize);
    }

    private interface InfoDeleted {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Deleted)
        Observable<HylLoginModel> getData(@Field("msgId") String msgId);
    }

    public static Observable<HylLoginModel> InfoDeleted(Context context,String msgId) {
        InfoDeleted service = RestHelper.getBaseRetrofit(context).create(InfoDeleted.class);
        return service.getData(msgId);
    }

    /**
     * 发布资讯
     */
    private interface InfoIssue {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Issue)
        Observable<InfoPubModel> getData(@Field("msgType") int msgType, @Field("content") String content,
                                         @Field("pictureJson") String pictureJson, @Field("provinceCode") String provinceCode,
                                         @Field("cityCode") String cityCode,@Field("areaCode") String areaCode, @Field("detailAddress") String detailAddress,
                                         @Field("phone") String phone,@Field("videoUrl") String videoUrl,@Field("videoCoverUrl") String videoCoverUrl);
    }

    public static Observable<InfoPubModel> InfoIssue(Context context,int msgType,String content,String pictureJson,String provinceCode,
                                                     String cityCode,String areaCode,String detailAddress,String phone,String videoUrl,String videoCoverUrl) {
        InfoIssue service = RestHelper.getBaseRetrofit(context).create(InfoIssue.class);
        return service.getData(msgType,content,pictureJson,provinceCode,cityCode,areaCode,detailAddress,phone,videoUrl,videoCoverUrl);
    }

    /**
     * 资讯详情
     */
    private interface InfoDetailIssue {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Issue_detail)
        Observable<InfoDetailIssueModel> getData(@Field("msgId") String msgId);
    }

    public static Observable<InfoDetailIssueModel> InfoDetailIssue(Context context,String msgId) {
        InfoDetailIssue service = RestHelper.getBaseRetrofit(context).create(InfoDetailIssue.class);
        return service.getData(msgId);
    }

    /**
     * 资讯编辑
     */

    private interface InfoClassifyIssue {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Classify)
        Observable<BaseModel> getData(@Field("msgId") String msgId, @Field("msgType") int msgType,
                                      @Field("content") String content, @Field("pictureJson") String pictureJson,
                                      @Field("provinceCode") String provinceCode, @Field("cityCode") String cityCode,
                                      @Field("areaCode") String areaCode,
                                      @Field("detailAddress") String detailAddress, @Field("phone") String phone,
                                      @Field("videoUrl") String videoUrl,@Field("videoCoverUrl") String videoCoverUrl);
    }

    public static Observable<BaseModel> EditInfo(Context context,String msgId,int msgType,String content,String pictureJson,
                                                 String provinceCode,String cityCode,String areaCode,String detailAddress,String phone,
                                                 String videoUrl,String videoCoverUrl) {
        InfoClassifyIssue service = RestHelper.getBaseRetrofit(context).create(InfoClassifyIssue.class);
        return service.getData(msgId,msgType,content,pictureJson,provinceCode,cityCode,areaCode,detailAddress,phone,videoUrl,videoCoverUrl);
    }

    /**
     * 店铺热门搜索
     */
    public interface SearchHotService{
        @POST(AppInterfaceAddress.Info_Hot_Search)
        Observable<RecommendModel> setParam();
    }

    public static Observable<RecommendModel>getHotSearch(Context context){
        Observable<RecommendModel> cityChangeModelObservable = RestHelper.getBaseRetrofit(context).create(SearchHotService.class).setParam();
        return cityChangeModelObservable;
    }

    /**
     * 店铺搜索
     */
    public interface InfoSearchService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Search)
        Observable<HylLoginModel> getData(@Field("keyword") String keyword);
    }

    public static Observable<HylLoginModel> getShopList(Context context, String keyword) {
        InfoSearchService service = RestHelper.getBaseRetrofit(context).create(InfoSearchService.class);
        return service.getData(keyword);
    }


    /**
     * 编辑咨询名称
     */
    public interface HomeTabStyleService{
        @POST(AppInterfaceAddress.Get_Style_Tab)
        Observable<HomeStyleTabModel> getData();
    }

    public static Observable<HomeStyleTabModel> getTabStyle(Context context) {
        HomeTabStyleService service = RestHelper.getBaseRetrofit(context).create(HomeTabStyleService.class);
        return service.getData();
    }
}
