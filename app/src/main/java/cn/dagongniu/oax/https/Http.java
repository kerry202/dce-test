package cn.dagongniu.oax.https;

/**
 * 接口信息
 */

public class Http {

    public static final String WEBSOCKET_ROOT = "ws://api.dce.cash:81/endpointWisely/websocket";//生产服务器 长链接
        public static final String TEST_ROOT = "http://api.dce.cash:81";//生产服务器
//    public static final String TEST_ROOT = "http://188.131.139.105:8080";//测试服务器


    /**
     * 邮箱注册
     */
    public static final String user_emailRegister = TEST_ROOT + "/user/emailRegister";

    /**
     * 手机号码注册
     */
    public static final String user_phoneRegister = TEST_ROOT + "/user/phoneRegister";

    /**
     * 短信验证码
     */
    public static final String user_sendSms = TEST_ROOT + "/sms/sendSms";

    /**
     * 邮箱验证码
     */
    public static final String user_sendEmailCode = TEST_ROOT + "/email/sendEmailCode";

    /**
     * 国家区号
     */
    public static final String user_countryCode = TEST_ROOT + "/countryCode/list";

    /**
     * 校验短信验证码
     */
    public static final String user_checkSms = TEST_ROOT + "/sms/checkSms";

    /**
     * 注册（邮箱手机）
     */
    public static final String user_registerApp = TEST_ROOT + "/user/registerApp";

    /**
     * 效验邮箱验证码
     */
    public static final String user_checkEmailCode = TEST_ROOT + "/email/checkEmailCode";

    /**
     * 登录
     */
    public static final String user_login = TEST_ROOT + "/user/login";

    /**
     * 登录效验是否短信或者google验证
     */
    public static final String user_queryCheckType = TEST_ROOT + "/user/queryCheckType";

    /**
     * 忘记密码
     */
    public static final String user_forgetPasswordApp = TEST_ROOT + "/user/forgetPasswordApp";

    /**
     * 个人中心
     */
    public static final String USER_USERCENTER = TEST_ROOT + "/user/userCenter";


    /**
     * 首页banner加载
     */
    public static final String main_banner = TEST_ROOT + "/banner/list";

    /**
     * 首页公告
     */
    public static final String main_noticeCenter = TEST_ROOT + "/noticeCenter/listApp";

    /**
     * 首页
     */
    public static final String main_indexPage = TEST_ROOT + "/indexPage";

    /**
     * 公告查看详情
     */
    public static final String noticeCenter_readDetail = TEST_ROOT + "/noticeCenter/readDetail";
    /**
     * 公告查看更多
     */
    public static final String noticeCenter_readMore = TEST_ROOT + "/noticeCenter/readMore";

    /**
     * 帮助中心
     */
    public static final String HELP_CENTER = TEST_ROOT + "/helpCenter/readMore";

    /**
     * 帮助中心详情
     */
    public static final String HELP_CENTER_READDETAIL = TEST_ROOT + "/helpCenter/readDetail";


    /**
     * 实时委托
     */
    public static final String ORDERS_MARKETORDERS = TEST_ROOT + "/Entrust_orders/marketOrders";

    /**
     * 实时成交
     */
    public static final String TRADE_TRADELISTBYMARKETID = TEST_ROOT + "/trade/tradeListByMarketId";

    /**
     * K线列表数据
     */
    public static final String KLINE_FINDLISTBYMARKETID = TEST_ROOT + "/kline/findListByMarketId";


    /**
     * App更新
     */
    public static final String APP_CHECKVERSION = TEST_ROOT + "/app/checkVersion";


    /**
     * 某个交易对的信息（公告，市场交易对，用户搜藏交易对，k线图，实时委托，实时交易）
     */
    public static final String TRANSACTIONPAGE_INDEX = TEST_ROOT + "/transactionPage/index";

    /**
     * webSocket 实时委托
     */
    public static final String TOPIC_MARKETORDERS = "/topic/marketOrders/";

    /**
     * webSocket 实时成交
     */
    public static final String TOPIC_TRADELIST = "/topic/tradeList/";

    /**
     * websocket 首页查询所有市场
     */
    public static final String marketCategory_all = "/topic/marketCategory/all";

    /**
     * websocket推送用户资产页面的市场对
     */
    public static final String TOPIC_MARKETLIST = "/topic/marketList/";

    /**
     * 用户资产列表
     */
    public static final String ASSETS_PROPERTYLIST = TEST_ROOT + "/userCoin/propertyList";
    /**
     * 充值记录
     */
    public static final String PROPERTY_RECHARGE = TEST_ROOT + "/property/recharge";
    /**
     * 提现记录
     */
    public static final String PROPERTY_WITHDRAW = TEST_ROOT + "/property/withdraw";
    /**
     * 充值显示 详情
     */
    public static final String USERCOIN_RECHARGESHOW = TEST_ROOT + "/userCoin/rechargeShow";
    /**
     * 币种列表
     */
    public static final String USERCOIN_LIST = TEST_ROOT + "/userCoin/list";
    /**
     * 充值
     */
    public static final String USERCOIN_RECHARGE = TEST_ROOT + "/userCoin/recharge";
    /**
     * 提现查询
     */
    public static final String USERCOIN_QUERYCOININFO = TEST_ROOT + "/userCoin/queryCoinInfo";
    /**
     * 提币地址列表
     */
    public static final String COINADDRESS_LIST = TEST_ROOT + "/coinAddress/list";
    /**
     * 添加提币地址
     */
    public static final String COINADDRESS_ADD = TEST_ROOT + "/coinAddress/add";
    /**
     * 查询需要的验证类型
     */
    public static final String USER_QUERYCHECKTYPE = TEST_ROOT + "/user/queryCheckType";

    /**
     * 提现
     */
    public static final String USERCOIN_WITHDRAWAL = TEST_ROOT + "/userCoin/withdrawal";

    /**
     * 买入卖出订单 余额
     */
    public static final String TRANSACTION_PAGE = TEST_ROOT + "/transactionPage/";

    /**
     * 添加买入/卖出
     */
    public static final String ORDERS_ADD = TEST_ROOT + "/orders/add";

    /**
     * 用户托管订单 点对点刷新
     */
    public static final String CHECK_TRADE = "/checkTrade/";

    /**
     * 撤销订单
     */
    public static final String ORDERS_CANCEL = TEST_ROOT + "/orders/cancel/";

    /**
     * 收藏
     */
    public static final String USERMAKET_SAVE = TEST_ROOT + "/userMaket/save/";

    /**
     * 取消收藏
     */
    public static final String USERMAKET_CANCEL = TEST_ROOT + "/userMaket/cancel/";

    /**
     * 取消收藏
     */
    public static final String TOPIC_KLINELIST = "/topic/klineList/";
    /**
     * 我的邀请
     */
    public static final String USER_MUINVATE = TEST_ROOT + "/user/myInvate";

    /**
     * 绑定手机
     */
    public static final String USER_BINDPHONE = TEST_ROOT + "/user/bindPhone";

    /**
     * 绑定邮箱
     */
    public static final String USER_BINDEMAIL = TEST_ROOT + "/user/bindEmail";

    /**
     * 切换安全验证   (手机-邮箱-google)
     */
    public static final String USER_SWITCHCHECK = TEST_ROOT + "/user/switchCheck";

    /**
     * google验证查询
     */
    public static final String USER_GETGOOGLEQRBARCODEURL = TEST_ROOT + "/user/getGoogleQRBarcodeUrl";

    /**
     * 绑定或修改谷歌验证
     */
    public static final String USER_BINDGOOPGLECODE = TEST_ROOT + "/user/bindGoogleCode";
    /**
     * 修改登录密码
     */
    public static final String USER_UPDATELOGINPASSWORD = TEST_ROOT + "/user/updateLoginPassword";

    /**
     * 图片上传
     */
    public static final String FILEUPDATE_UPDATEPIC = TEST_ROOT + "/fileUpload/uploadPic";

    /**
     * 身份认证
     */
    public static final String USER_IDENTITYAUTHEN = TEST_ROOT + "/user/identityAuthen";

    /**
     * 网易云滑块验证码二次校验
     */
    public static final String THIRDAPI_WYY_SECONDVERIFY = TEST_ROOT + "/thirdApi/wyy/secondVerify";

    /**
     * 登陆校验密码
     */
    public static final String USER_CHECKLOGINPASSWORD = TEST_ROOT + "/user/checkLoginPassword";

    /**
     * 成交订单
     */
    public static final String ORDERSRECORD_FINDTRADEORDERLISTBYPAGE = TEST_ROOT + "/ordersRecord/findTradeOrderListByPage";

    /**
     * 委托
     */
    public static final String ORDERSRECORD_FINDLISTBYPAGE = TEST_ROOT + "/ordersRecord/findListByPage";

    /**
     * 联动交易对信息查询
     */
    public static final String MARKET_MARKETCATEGORYLIST = TEST_ROOT + "/market/marketCategoryList";

    /**
     * 判断邮箱是否已被注册
     */
    public static final String USER_CHECKEMAIL = TEST_ROOT + "/user/checkEmail";

    /**
     * 判断手机是否已被注册
     */
    public static final String USER_CHECKPHONE = TEST_ROOT + "/user/checkPhone";


    /**
     * 实时成交和委托深度
     */
    public static final String TOPIC_TRADELISTANDMARKETORDERS = "/topic/tradeListAndMarketOrders/";

    /**
     * 身份认证审核结果
     */
    public static final String IDENTITYRESULT = TEST_ROOT + "/user/identityResult";

    /**
     * 我发出的红包记录
     */
    public static final String AWARDREDPACKETRECORD = TEST_ROOT + "/redPacket/awardRedPacketRecord";

    /**
     * 我领取的红包记录
     */
    public static final String GRABREDPACKETRECORD = TEST_ROOT + "/redPacket/grabRedPacketRecord";

    /**
     * 发送红包
     */
    public static final String AWARD = TEST_ROOT + "/redPacket/award";

    /**
     * 发红包加载页
     */
    public static final String RED_INDEX = TEST_ROOT + "/redPacket/index/";

    /**
     * 红包被领取详情
     */
    public static final String RED_takeRedPacketDetails = TEST_ROOT + "/redPacket/takeRedPacketDetails";

    /**
     * 我的收益
     */
    public static final String MyEarnings = TEST_ROOT + "/feedBack/user/myIncome";

    /**
     * 回馈累计
     */
    public static final String Total_Feedback = TEST_ROOT + "/feedBack/app/feedBackSum";

    /**
     * 总览
     */
    public static final String overview = TEST_ROOT + "/feedBack/app/overview";

}
