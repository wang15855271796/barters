package com.barter.hyl.app.api;

import android.content.Context;

import com.barter.app.model.SendImageModel;
import com.barter.hyl.app.constant.AppInterfaceAddress;
import com.barter.hyl.app.constant.RestHelper;
import com.barter.hyl.app.model.BillDetailModel;
import com.barter.hyl.app.model.HylAmountNumModel;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylEvalGoodsModel;
import com.barter.hyl.app.model.HylGetWallertRecordByPageModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylPayInfoModel;
import com.barter.hyl.app.model.HylPayInfoResultModel;
import com.barter.hyl.app.model.HylPayListModel;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.barter.hyl.app.model.HylReturnOrderDetailModel;
import com.barter.hyl.app.model.InfoIsPayModel;
import com.barter.hyl.app.model.JudgeModel;
import com.barter.hyl.app.model.HylMyBillModel;
import com.barter.hyl.app.model.HylMyOrderListModel;
import com.barter.hyl.app.model.HylOrderDetailModel;
import com.barter.hyl.app.model.HylPayResultModel;
import com.barter.hyl.app.model.HylReturnNumModel;
import com.barter.hyl.app.model.HylSearchBillModel;
import com.barter.hyl.app.model.HylSendImageModel;
import com.barter.hyl.app.model.PayInfoListModel;
import com.barter.hyl.app.model.PayInfoModel;
import com.barter.hyl.app.model.UpdateImageModel;

import org.json.JSONArray;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/9/2
 */
public class OrderApi {

    //我的订单列表
    public interface MyOrderListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.My_Order_List)
        Observable<HylMyOrderListModel> setParams(@Field("pageNum") int pageNum,
                                                  @Field("pageSize") int pageSize,
                                                  @Field("orderType") int orderType,
                                                  @Field("orderStatus") String orderStatus);
    }

    public static Observable<HylMyOrderListModel> getMyOrderList(Context context, int pageNum, int pageSize, int orderType, String orderStatus) {
        Observable<HylMyOrderListModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(MyOrderListService.class).setParams(pageNum,pageSize,orderType,orderStatus);
        return loginModelObservable;
    }

    //立即评价
    public interface JudgeService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Judge)
        Observable<HylLoginModel> setParams(@Field("orderId") String orderId,
                                            @Field("comments") JSONArray comments);
    }

    public static Observable<HylLoginModel> getJudge(Context context, String orderId, JSONArray comments) {
        Observable<HylLoginModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(JudgeService.class).setParams(orderId,comments);
        return loginModelObservable;
    }

    //查看评价
    public interface CheckJudgeService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Judge)
        Observable<JudgeModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<JudgeModel> checkJudge(Context context, String orderId) {
        Observable<JudgeModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CheckJudgeService.class).setParams(orderId);
        return loginModelObservable;
    }

    //再次购买
    public interface AgainBuyService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Again_Buy)
        Observable<HylLoginModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<HylLoginModel> getAgainBuy(Context context, String orderId) {
        Observable<HylLoginModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(AgainBuyService.class).setParams(orderId);
        return loginModelObservable;
    }

    //取消订单
    public interface CancelOrderService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cancel_Order)
        Observable<BaseModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<BaseModel> cancleOrder(Context context,String orderId) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CancelOrderService.class).setParams(orderId);
        return loginModelObservable;
    }

    //删除订单
    public interface DeleteOrderService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delete_Order)
        Observable<BaseModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<BaseModel> deleteOrder(Context context,String orderId) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(DeleteOrderService.class).setParams(orderId);
        return loginModelObservable;
    }

    //删除订单2
    public interface Delete1OrderService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delete2_Order)
        Observable<BaseModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<BaseModel> deleteOrder1(Context context,String orderId) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(Delete1OrderService.class).setParams(orderId);
        return loginModelObservable;
    }

    //确定收货
    public interface ConfirmOrderService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Confirm_Order)
        Observable<BaseModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<BaseModel> confirmOrder(Context context,String orderId) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(ConfirmOrderService.class).setParams(orderId);
        return loginModelObservable;
    }

    //订单详情
    public interface OrderDetailService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Order_Detail)
        Observable<HylOrderDetailModel> setParams(@Field("orderId") String orderId,
                                                  @Field("returnMainId") String returnMainId,
                                                  @Field("status") int status);
    }

    public static Observable<HylOrderDetailModel> orderDetail(Context context, String orderId, String returnMainId, int status) {
        Observable<HylOrderDetailModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(OrderDetailService.class).setParams(orderId,returnMainId,status);
        return loginModelObservable;
    }

    //获取退货商品
    public interface ReturnGoodService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Return_Goods_Detail)
        Observable<HylReturnGoodModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<HylReturnGoodModel> returnGood(Context context, String orderId) {
        Observable<HylReturnGoodModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(ReturnGoodService.class).setParams(orderId);
        return loginModelObservable;
    }

    //获取退货商品
    public interface ReturnGoodsService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Return_Goods_Detail)
        Observable<HylReturnOrderDetailModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<HylReturnOrderDetailModel> returnGoods(Context context, String orderId) {
        Observable<HylReturnOrderDetailModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(ReturnGoodsService.class).setParams(orderId);
        return loginModelObservable;
    }

    //判断退货数量
    public interface ReturnNumService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Return_Num)
        Observable<HylReturnNumModel> setParams(@Field("businessType") int businessType,
                                                @Field("detailId") String detailId,
                                                @Field("num") int num,
                                                @Field("unitId") String unitId,
                                                @Field("orderId") String orderId);
    }

    public static Observable<HylReturnNumModel> returnNum(Context context, int businessType, String detailId, int num,
                                                          String unitId, String orderId) {
        Observable<HylReturnNumModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(ReturnNumService.class)
                .setParams(businessType,detailId,num,unitId,orderId);
        return loginModelObservable;
    }

    //判断退货金额
    public interface ReturnPriceService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Return_Price)
        Observable<HylLoginModel> setParams(@Field("businessType") int businessType,
                                            @Field("detailId") String detailId,
                                            @Field("num") int num,
                                            @Field("unitId") String unitId);
    }

    public static Observable<HylLoginModel> returnPrice(Context context, int businessType, String detailId, int num,
                                                        String unitId) {
        Observable<HylLoginModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(ReturnPriceService.class)
                .setParams(businessType,detailId,num,unitId);
        return loginModelObservable;
    }

    //申请退货申请
    public interface ApplyReturnService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Apply_Return)
        Observable<HylLoginModel> setParams(@Field("orderId") String orderId,
                                            @Field("returnType") String returnType,
                                            @Field("returnReason") String returnReason,
                                            @Field("returnAmount") String returnAmount,
                                            @Field("pics") String pics,
                                            @Field("allReturnFlag") String allReturnFlag,
                                            @Field("returnProds") JSONArray returnProds);
    }

    public static Observable<HylLoginModel> applyReturn(Context context, String orderId, String returnType, String returnReason,
                                                        String returnAmount, String pics, String allReturnFlag, JSONArray returnProds) {
        Observable<HylLoginModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(ApplyReturnService.class)
                .setParams(orderId,returnType,returnReason,returnAmount,pics,allReturnFlag,returnProds);
        return loginModelObservable;
    }

    //申请退货申请
    public interface CancelApplyService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cancel_Apply)
        Observable<HylLoginModel> setParams(@Field("returnMainId") String returnMainId);
    }

    public static Observable<HylLoginModel> cancelApply(Context context, String returnMainId) {
        Observable<HylLoginModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CancelApplyService.class)
                .setParams(returnMainId);
        return loginModelObservable;
    }

    //上传图片
    public interface updateImageService {
        @Multipart
        @POST(AppInterfaceAddress.Upload_Img)
        Observable<HylSendImageModel> setParams(@Part List<MultipartBody.Part> parts);
    }

    public static Observable<HylSendImageModel> requestImgDetail(Context context, List<MultipartBody.Part> parts) {
        Observable<HylSendImageModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(updateImageService.class)
                .setParams(parts);
        return loginModelObservable;
    }
    //上传图片2
    public interface updatesImageService {
        @Multipart
        @POST(AppInterfaceAddress.Upload_Img)
        Observable<UpdateImageModel> setParams(@Part List<MultipartBody.Part> parts);
    }

    public static Observable<UpdateImageModel> requestImgsDetail(Context context, List<MultipartBody.Part> parts) {
        Observable<UpdateImageModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(updatesImageService.class)
                .setParams(parts);
        return loginModelObservable;
    }

    public interface updateImagesService {
        @Multipart
        @POST(AppInterfaceAddress.Upload_Imgs)
        Observable<SendImageModel> setParams(@Part List<MultipartBody.Part> parts);
    }

    public static Observable<SendImageModel> updateImage(Context context, List<MultipartBody.Part> parts) {
        Observable<SendImageModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(updateImagesService.class)
                .setParams(parts);
        return loginModelObservable;
    }

    //订单评价商品
    public interface EvalGoodsService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Eval_Goods)
        Observable<HylEvalGoodsModel> setParams(@Field("orderId")String orderId);
    }

    public static Observable<HylEvalGoodsModel> getEvalGoods(Context context, String orderId) {
        Observable<HylEvalGoodsModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(EvalGoodsService.class)
                .setParams(orderId);
        return loginModelObservable;
    }

    //我的账单
    public interface MyAccountService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Return_Goods_Detail)
        Observable<HylReturnGoodModel> setParams(@Field("pageNum") int pageNum,
                                                 @Field("pageSize") int pageSize,
                                                 @Field("payChannel") String payChannel,
                                                 @Field("recordType") String recordType,
                                                 @Field("year") int year,
                                                 @Field("month") int month);
    }

    public static Observable<HylReturnGoodModel> myAccount(Context context, int pageNum, int pageSize, String payChannel, String recordType, int year, int month) {
        Observable<HylReturnGoodModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(MyAccountService.class).setParams(pageNum,pageSize,payChannel,recordType,year,month);
        return loginModelObservable;
    }

    //生成订单
    public interface CartOrderService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cart_Order)
        Observable<HylLoginModel> setParams(@Field("cartIds") String cartIds,
                                            @Field("giftNo") String giftNo,
                                            @Field("memo") String memo);
    }

    public static Observable<HylLoginModel> cartOrder(Context context, String cartIds, String giftNo, String memo) {
        Observable<HylLoginModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CartOrderService.class).setParams(cartIds,giftNo,memo);
        return loginModelObservable;
    }

    //待履约数量金额
    public interface AmountNumService {
        @POST(AppInterfaceAddress.AmountNum)
        Observable<HylAmountNumModel> setParams();
    }

    public static Observable<HylAmountNumModel> getAmountNum(Context context) {
        Observable<HylAmountNumModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(AmountNumService.class).setParams();
        return loginModelObservable;
    }

    //支付方式
    public interface PayWayService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Pay_List)
        Observable<HylPayListModel> setParams(@Field("flag") int flag,@Field("orderId") String orderId);
    }

    public static Observable<HylPayListModel> getPayWay(Context context, int flag,String orderId) {
        Observable<HylPayListModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(PayWayService.class).setParams(flag,orderId);
        return loginModelObservable;
    }

    private interface PayInfoListService {
        @POST(AppInterfaceAddress.Pay_Info_List)
        Observable<PayInfoListModel> setParams();
    }

    public static Observable<PayInfoListModel> getPayList(Context context) {
        PayInfoListService service = RestHelper.getBaseRetrofit(context).create(PayInfoListService.class);
        return service.setParams();
    }

    //判断是否需要支付
    private interface PointIsPayService {
        @POST(AppInterfaceAddress.Info_Is_Pay)
        Observable<InfoIsPayModel> getData();
    }

    public static Observable<InfoIsPayModel> getIsPay(Context context) {
        PointIsPayService service = RestHelper.getBaseRetrofit(context).create(PointIsPayService.class);
        return service.getData();
    }

    //行业资讯 生成支付信息
    private interface InfoPayService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Pay_Info)
        Observable<HylPayInfoModel> getData(@Field("payChannel") int payChannel,
                                         @Field("payAmount") String payAmount,
                                         @Field("msgId") String msgId);
    }

    public static Observable<HylPayInfoModel> getInfoPay(Context context, int payChannel, String payAmount, String msgId) {
        InfoPayService service = RestHelper.getBaseRetrofit(context).create(InfoPayService.class);
        return service.getData( payChannel, payAmount, msgId);
    }

    //一般订单支付信息
    public interface PayInfoService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Pay_Info)
        Observable<HylPayInfoModel> setParams(@Field("orderId") String orderId,
                                              @Field("payChannel") int payChannel,
                                              @Field("errorFlag") int errorFlag);
    }

    public static Observable<HylPayInfoModel> getPayInfo(Context context, String orderId, int payChannel,int errorFlag) {
        Observable<HylPayInfoModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(PayInfoService.class).setParams(orderId,payChannel,errorFlag);
        return loginModelObservable;
    }

    //货到付款订单支付信息
    public interface DelayPayService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delay_Pay)
        Observable<HylPayInfoModel> setParams(@Field("ids") String ids, @Field("payChannel") int payChannel);
    }

    public static Observable<HylPayInfoModel> getDelayPay(Context context, String ids, int payChannel) {
        Observable<HylPayInfoModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(DelayPayService.class).setParams(ids,payChannel);
        return loginModelObservable;
    }

    //支付结果页
    public interface PayResultService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Pay_Result)
        Observable<HylPayResultModel> setParams(@Field("outTradeNo") String outTradeNo);
    }

    public static Observable<HylPayResultModel> getPayResult(Context context, String outTradeNo) {
        Observable<HylPayResultModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(PayResultService.class).setParams(outTradeNo);
        return loginModelObservable;
    }

    //支付结果页2
    public interface PayInfoResultService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Pay_Info_Result)
        Observable<HylPayInfoResultModel> setParams(@Field("outTradeNo") String outTradeNo);
    }

    public static Observable<HylPayInfoResultModel> getPayInfoResult(Context context, String outTradeNo) {
        Observable<HylPayInfoResultModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(PayInfoResultService.class).setParams(outTradeNo);
        return loginModelObservable;
    }

    //我的账单
    public interface MyBillService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.My_Bill)
        Observable<HylMyBillModel> setParams(
                @Field("pageNum") int pageNum,
                @Field("pageSize") int pageSize,
                @Field("payChannel") String payChannel,
                @Field("recordType") String recordType,
                @Field("year") String year,
                @Field("month") String month);
    }

    public static Observable<HylMyBillModel> getMyBill(Context context, int pageNum, int pageSize,
                                                       String payChannel, String recordType, String year, String month) {
        Observable<HylMyBillModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(MyBillService.class).setParams(pageNum,pageSize,payChannel,
                recordType,year,month);
        return loginModelObservable;
    }

    //删除账单条目
    public interface DeleteBillService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delete_Bill)
        Observable<BaseModel> setParams(
                @Field("recordId") String recordId);
    }

    public static Observable<BaseModel> deleteItem(Context context,String recordId) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(DeleteBillService.class).setParams(recordId);
        return loginModelObservable;
    }
    //我的账单
    public interface SearchBillService {
        @POST(AppInterfaceAddress.My_Search_Bill)
        Observable<HylSearchBillModel> setParams();
    }

    public static Observable<HylSearchBillModel> getSearchBill(Context context) {
        Observable<HylSearchBillModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(SearchBillService.class).setParams();
        return loginModelObservable;
    }

    //账单详情
    public interface BillDetailService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Bill_Detail)
        Observable<BillDetailModel> setParams(@Field("id") int id);
    }

    public static Observable<BillDetailModel> getBillDetail(Context context, int id) {
        Observable<BillDetailModel> billDetailModelObservable = RestHelper.getBaseRetrofit(context).create(BillDetailService.class).setParams(id);
        return billDetailModelObservable;
    }


}
