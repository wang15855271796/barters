package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.HylActiviteModel;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylFullListModel;
import com.barter.hyl.app.model.HomeBaseModel;
import com.barter.hyl.app.model.HylMessageDetailModel;
import com.barter.hyl.app.model.HylMessageModel;
import com.barter.hyl.app.model.HylMessageNumModel;
import com.barter.hyl.app.model.HylTeamListModel;
import com.barter.hyl.app.model.HylSkillListModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/8/13
 */
public class HomeApi {
    //登录
    public interface HomeBaseService {
        @POST(AppInterfaceAddress.Base_Info)
        Observable<HomeBaseModel> setParams();
    }

    public static Observable<HomeBaseModel> getBaseData(Context context) {
        Observable<HomeBaseModel> homeBaseModelObservable = RestHelper.getBaseRetrofit(context).create(HomeBaseService.class).setParams();
        return homeBaseModelObservable;
    }

    //热销
    public interface HotService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Hot_Info)
        Observable<HylActiviteModel> setParams(@Field("pageNum") int pageNum,
                                               @Field("pageSize") int pageSize);
    }

    public static Observable<HylActiviteModel> getHot(Context context, int pageNum, int pageSize) {
        Observable<HylActiviteModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(HotService.class).setParams(pageNum, pageSize);
        return loginModelObservable;
    }

    //常购清单
    public interface CommonService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Common_List)
        Observable<HylActiviteModel> setParams(@Field("pageNum") int pageNum,
                                               @Field("pageSize") int pageSize);
    }

    public static Observable<HylActiviteModel> getCommon(Context context, int pageNum, int pageSize) {
        Observable<HylActiviteModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CommonService.class).setParams(pageNum, pageSize);
        return loginModelObservable;
    }

    //消息列表
    public interface MessageService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Message_List)
        Observable<HylMessageModel> setParams(@Field("pageNum") int pageNum,
                                              @Field("pageSize") int pageSize);
    }

    public static Observable<HylMessageModel> getMessageList(Context context, int pageNum, int pageSize) {
        Observable<HylMessageModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(MessageService.class).setParams(pageNum, pageSize);
        return loginModelObservable;
    }

    //消息详情
    public interface MessageDetailService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Message_Detail)
        Observable<HylMessageDetailModel> setParams(@Field("noticeId") int noticeId,
                                                    @Field("readFlag") int readFlag);
    }

    public static Observable<HylMessageDetailModel> getMessageDetail(Context context, int noticeId, int readFlag) {
        Observable<HylMessageDetailModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(MessageDetailService.class).setParams(noticeId, readFlag);
        return loginModelObservable;
    }

    //未读消息数量
    public interface MessageUnReadService {
        @POST(AppInterfaceAddress.Message_UnRead)
        Observable<HylMessageNumModel> setParams();
    }

    public static Observable<HylMessageNumModel> getMessageNum(Context context) {
        Observable<HylMessageNumModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(MessageUnReadService.class).setParams();
        return loginModelObservable;
    }

    //组合数据
    public interface TeamListService {
        @POST(AppInterfaceAddress.Team_List)
        Observable<HylTeamListModel> setParams();
    }

    public static Observable<HylTeamListModel> getTeamList(Context context) {
        Observable<HylTeamListModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(TeamListService.class).setParams();
        return loginModelObservable;
    }

    //满赠数据
    public interface FullListService {
        @POST(AppInterfaceAddress.Full_List)
        Observable<HylFullListModel> setParams();
    }

    public static Observable<HylFullListModel> getFullList(Context context) {
        Observable<HylFullListModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(FullListService.class).setParams();
        return loginModelObservable;
    }

    //秒杀列表数据
    public interface SkillListService {
        @POST(AppInterfaceAddress.Skill_List)
        Observable<HylSkillListModel> setParams();
    }

    public static Observable<HylSkillListModel> getSkillList(Context context) {
        Observable<HylSkillListModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(SkillListService.class).setParams();
        return loginModelObservable;
    }

    //弹窗是否被点击
    public interface PrivacyDialogService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Privacy_Dialog)
        Observable<BaseModel> setParams(@Field("id") int id,
                                        @Field("type") int type);
    }

    public static Observable<BaseModel> getRead(Context context,int id,int type) {
        Observable<BaseModel> homeBaseModelObservable = RestHelper.getBaseRetrofit(context).create(PrivacyDialogService.class).setParams(id,type);
        return homeBaseModelObservable;
    }
}
