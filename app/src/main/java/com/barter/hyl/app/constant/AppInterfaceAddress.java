package com.barter.hyl.app.constant;

/**
 * Created by GuoGai on 2016/10/31.
 */
public class AppInterfaceAddress {
//    https://apis.huoyl.cn/qiaoge/正式
    // http://192.168.2.188:8082/qiaoge/
    //http://116.62.67.230:8082/qiaoge/   测试http://120.55.55.99:8082/qiaoge/
    public static final String BASE_URL = "https://apis.huoyl.cn/qiaoge/";
    // 正式https://shaokao.qoger.com/qiaoge/
    //http://192.168.1.45/   本地192.168.101.69:8088
    //http://qg.zhiyun88u.com/shen
    //http://192.168.101.17/

    //http://192.168.101.41:8088/
//    queryReturnProdIsContainFullGift
    /**
     * 首页-获取优惠券数量
     */
    public static final String Get_Coupon_Num = "hyl/home/getHylUserEnableGiftNum";
    /**
     *切换首页tab
     */
    public static final String Get_Style_Tab = "common/initAppBottomModule";
    /**
     * 企业信息-new
     */
    public static final String Company_Info = "hyl/getHylCompanyShowInfo";
    /**
     *搜索店铺
     */
    public static final String Info_Search = "message/getList";
    /**
     *店铺热门搜索
     */
    public static final String Info_Hot_Search = "hyl/message/getMessageHotSearch";

    /**
     *资讯编辑
     */
    public static final String Info_Classify = "hyl/message/updateMessage";
    /**
     *获取资讯详情
     */
    public static final String Info_Issue_detail = "message/detail";
    /**
     * 发布资讯
     */
    public static final String Info_Issue = "hyl/message/addMessage";
    /**
     *删除资讯
     */
    public static final String Info_Deleted = "hyl/message/deleteMessage";
    /**
     * 我的资讯列表
     */
    public static final String Info_My_List = "hyl/message/myList";
    /**
     * 资讯详情
     */
    public static final String Info_Detail = "hyl/message/detail";
    /**
     *  资讯列表
     */
    public static final String Info_List = "hyl/message/getList";

    /**
     * 更改登录企业
     */
    public static final String Change_Company = "hyl/home/updateHylUserCompanyId";
    /**
     * 获取满赠提示
     */
    public static final String Get_Tips = "hyl/home/getHylAppFullActiveTipsV3";

    /**
     *优惠券查看可用商品列表
     */
    public static final String Coupon_GoodsList = "hyl/home/getFullGiftUseProdPage";
    /**
     *满赠活动查看优惠券
     */
    public static final String Full_Active_CouponList = "hyl/home/getHylAppFullActiveGiftDetailV3";
    /**
     *满赠活动详情
     */
    public static final String Full_Active_Detail = "hyl/home/getHylAppFullActiveDetailV3";
    /**
     * 提交申请
     */
    public static final String Submit_Apply = "common/applyHylAccount";
    /**
     * 企业选择-新
     */
    public static final String Company_Choose = "hyl/home/getUserAttachFranchise";
    /**
     * 获取公司简称
     */
    public static final String Company_Name = "auth/hyl/getHylShortName";
    /**
     * 添加企业-新接口
     */
    public static final String Add_Company = "hyl/home/bindUserToFranchise";
    /**
     * 申请试用-验证码校验
     */
    public static final String Check_Message = "common/hylGwCheckSmsCode";

    /**
     * 申请试用-发送短信
     */
    public static final String Send_Message = "common/hylGwSendMsg";
    /**
     * 注销
     */
    public static final String Cancel = "hyl/cancelUser";
    /**
     *忘记密码
     */
    public static final String Forget_Pwd = "auth/hyl/forgetPwd";
    /**
     *查询支付结果页
     */
    public static final String Pay_Result = "hyl/order/getOrderPayBack";
    /**
     *查询支付结果页2
     */
    public static final String Pay_Info_Result = "common/getMsgPayResult";
    /**
     *生成支付信息
     */
    public static final String Pay_Info = "hyl/order/orderPay";
    /**
     *转让招租生成支付信息
     */
    public static final String Get_Pay_Info = "hyl/message/getPayInfo";
    /**
     * 获取支付方式
     */
    public static final String Pay_List = "hyl/order/getPayChannels";
    /**
     *
     * 获取支付方式2
     */
    public static final String Pay_Info_List = "common/getMsgPayChannel";

    /**
     * 待履约订单数量金额
     */
    public static final String AmountNum = "hyl/order/getWaitPayDelayOrderNum";
    /**
     *货到付款支付信息生成
     */
    public static final String Delay_Pay = "hyl/order/delayOrderPay";
    /**
     *生成订单
     */
    public static final String Cart_Order = "hyl/order/orderGenerate";
    /**
     *我的账单
     */
    public static final String My_Bill = "hyl/myBillInfo";

    /**
     *账单详情
     */
    public static final String Bill_Detail = "hyl/myBillDetail";
    /**
     *账单查询条件获取
     */
    public static final String My_Search_Bill = "hyl/myBillSearchInfo";
    /**
     *删除账单条目
     */
    public static final String Delete_Bill = "hyl/removeMyBill";
    /**
     *获取订单评价商品
     */
    public static final String Eval_Goods = "hyl/order/getProdToComment";
    /**
     * 上传图片凭证
     */
    public static final String Upload_Img = "common/uploadMessageImg";

    /**
     * 上传图片凭证
     */
    public static final String Upload_Imgs = "common/imgUpload";
    /**
     * 撤销申请
     */
    public static final String Cancel_Apply = "hyl/order/cancelReturnOrder";
    /**
     *提交退货申请
     */
    public static final String Apply_Return = "hyl/order/returnHylOrder";
    /**
     *判断退货金额
     */
    public static final String Return_Price = "hyl/order/planReturnProdAmt";
    /**
     *判断退货的数量
     */
    public static final String Return_Num = "hyl/order/planReturnProdNum";
    /**
     *获取订单退货商品
     */
    public static final String Return_Goods_Detail = "hyl/order/getOrderReturnProds";
    /**
     * 获取订单详情
     */
    public static final String Order_Detail = "hyl/order/getMyOrderDetail";
    /**
     *确认收货
     */
    public static final String Confirm_Order = "hyl/order/receiveOrder";
    /**
     *删除订单
     */
    public static final String Delete_Order = "hyl/order/removeOrder";

    /**
     * 删除订单2
     */
    public static final String Delete2_Order = "hyl/order/removeHylUserFinishOrder";

    /**
     *取消订单
     */
    public static final String Cancel_Order = "hyl/order/cancelMyOrder";

    /**
     *再次购买
     */
    public static final String Again_Buy = "hyl/order/copyOrderProd";
    /**
     *查看评价
     */
    public static final String Check_Judge = "hyl/order/getOrderCommentInfo";

    /**
     *立即评价
     */
    public static final String Judge = "hyl/order/commentHylOrder";
    /**
     * 我的订单列表
     */
    public static final String My_Order_List = "hyl/order/getMyOrderPage";
    /**
     *设置
     */
    public static final String Setting = "hyl/hylMySetInfo";
    /**
     *  未读消息数量
     */
    public static final String Message_UnRead = "hyl/home/getUnReadNoticeNum";
    /**
     * 优惠券选择列表
     */
    public static final String Choose_Coupon = "hyl/order/getOrderGiftList";
    /**
     * 获取消息详情
     */
    public static final String Message_Detail = "hyl/home/getHomeNoticeDetail";
    /**
     * 获取消息列表
     */
    public static final String Message_List = "hyl/home/getHomeNotice";
    /**
     *获取品牌数据
     */
    public static final String Goods_Name = "hyl/prod/getHylProdBrand";

    /**
     * 分类商品列表
     */
    public static final String Goods_List = "hyl/prod/getProdByClassify";
    /**
     * 分类
     */
    public static final String Goods_Categori = "hyl/prod/getHylAllClassify";

    /**
     *地址详情
     */
    public static final String Address_Detail = "hyl/address/getHylAddressDetail";
    /**
     * 编辑地址
     */
    public static final String Edit_Address = "hyl/address/hylUserUpdateAddress";
    /**
     * 删除地址
     */
    public static final String Delete_Address = "hyl/address/delHylUserAddress";
    /**
     * 设置默认地址
     */
    public static final String Address_Default = "hyl/address/hylUserSetDefaultAddress";
    /**
     * 获取地址列表
     */
    public static final String Address_List = "hyl/address/getHylUserAddress";
    /**
     * 地址区域获取
     */
    public static final String Address_Area = "hyl/address/getOpenArea";
    /**
     * 行业资讯-全部地区
     */
    public static final String All_Area = "hyl/address/getAllArea";
    /**
     *新增地址
     */
    public static final String Add_Address = "hyl/address/hylUserAddAddress";
    /**
     * 结算
     */
    public static final String Settle = "hyl/order/orderSettle";
    /**
     *  删除购物车
     */
    public static final String Delete_Cart_List = "hyl/cart/removeCart";
    /**
     * 购物车列表
     */
    public static final String Cart_List = "hyl/cart/getHylAppUserCartListV3";
    /**
     *添加到购物车-累计
     */
    public static final String Add_Carts = "hyl/cart/cartAccumulate";
    /**
     * 购物车角标
     */
    public static final String Cart_Num = "hyl/cart/getMyCartInfo";

    /**
     * 搜索结果页面
     */
    public static final String Search_Result = "hyl/prod/getSearchProd";
    /**
     * 搜索界面推荐列表
     */
    public static final String Search_Recommend = "hyl/home/getRecommendKey";
    /**
     *添加商品到购物车
     */
    public static final String Add_Cart = "hyl/cart/addToCart";
    /**
     *商品切换规格
     */
    public static final String Change_Spec = "hyl/prod/changeProdSpec";
    /**
     * 购物车弹窗列表
     */
    public static final String Dialog_Data = "hyl/prod/getProdSpecInfo";

    /**
     *移除我的收藏
     */
    public static final String Delete_Collection = "hyl/prod/removeFromCollect";

    /**
     *我的收藏
     */
    public static final String My_Collection = "hyl/prod/getMyCollectProd";
    /**
     *我的优惠券
     */
    public static final String My_Coupon_List = "hyl/getMyGiftPage";

    /**
     *我的优惠券
     */
    public static final String My_Coupon_Detail = "hyl/getGiftUseProdList";

    /**
     *我的积分
     */
    public static final String My_Jifen = "hyl/pointMall";
    /**
     * 积分兑换
     */
    public static final String Ex_Change = "hyl/pointExchange";
    /**
     *我的
     */
    public static final String My_Info = "hyl/getHylMyInfo";
    /**
     *收藏
     */
    public static final String Collection = "hyl/prod/collectCancelProd";
    /**
     * 商品评论
     */
    public static final String Comment = "hyl/prod/getProdComments";
    /**
     * 普通商品详情页
     */
    public static final String Common_Data = "hyl/prod/getProdDetail";

    /**
     *活动详情
     */
    public static final String Active_Data = "hyl/home/getActiveDetail";

    /**
     *常购清单
     */
    public static final String Common_List = "hyl/home/getUserBuyProdPage";

    /**
     *获取热销
     */
    public static final String Hot_Info = "hyl/home/getHotProdPage";
    /**
     *秒杀活动列表
     */
    public static final String Skill_List = "hyl/home/getHomeSpikeActive";
    /**
     *满赠活动列表
     */
    public static final String Full_List = "hyl/home/getHylAppFullActivesV3";
    /**
     *组合列表数据
     */
    public static final String Team_List = "hyl/home/getHomeTeamActive";
    /**
     * 首页数据
     */
    public static final String Base_Info = "hyl/home/getHomeInfo";

    /**
     * 弹窗点击设为已读
     */
    public static final String Privacy_Dialog = "hyl/home/viewPopups";

    /**
     * 创蓝一键注册
     */
    public static final String CHAUNGLAN = "auth/hyl/hylClFlashCheck";

    /**
     * 登录
     */
    public static final String LOGIN = "auth/hyl/login";

    /**
     * 登录-添加企业
     */
    public static final String LOGIN_Address = "auth/hyl/loginAddFranchise";

    /**
     * 退出登录
     */
    public static final String LOGOUT = "hyl/loginOut";

    /**
     *发送验证码
     */
    public static final String Send_YZM = "auth/hyl/sendRegisterSms";

    /**
     * 注册协议，隐私政策获取
     */
    public static final String Privacy_Policy = "auth/hyl/getHylProtocol";

    /**
     *  短信注册前置校验
     */
    public static final String Yzm_Check = "auth/hyl/registerCheck";

    /**
     *获取授权公司
     */
    public static final String Get_Company = "auth/hyl/getHylShortName";


    /**
     * 注册
     */
    public static final String REGISTER = "auth/hyl/register";


    /**
     * 判断是否需要支付
     */
    public static final String Info_Is_Pay = "common/getMsgShouldPay";
}
