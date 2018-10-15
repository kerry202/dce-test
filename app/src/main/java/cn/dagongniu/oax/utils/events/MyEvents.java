package cn.dagongniu.oax.utils.events;


import java.util.List;

import cn.dagongniu.oax.main.bean.OaxMarketBean;

/**
 * 通知工具
 */
public class MyEvents<T> {


    //信息状态
    public static final int status_empty = 0;
    public static final int status_ok = 1; // 成功
    public static final int status_error = 2; // 网络错误
    public static final int status_msg = 3;//后台信息
    public static final int status_failure = 4;//登录失效
    public static final int status_NoInternet = 10;// 没有网络
    public static final int status_others = 5;//其他

    public static final int status_pass = 6;//传递数据
    //信息类型
    public static final String Language = "Language";//切换语言
    public static final String LoginSuccess = "LoginSuccess";//登录成功
    public static final String RedPacketSuccess = "RedPacketSuccess";//红包发送成功
    public static final String ShareSuccess = "ShareSuccess";//分享成功
    public static final String Withdrawal_Success = "Withdrawal_Success";//提现成功
    public static final String LoginEsc = "LoginEsc";//退出登录
    public static final String Add_Withdrawal_Adderss_Success = "Add_Withdrawal_Adderss_Success";//添加提现地址成功
    public static final String Home_refresh = "Home_refresh";//首页刷新
    public static final String Home_WebSocket_Market_List = "Home_WebSocket_Market_List";//首页交易对的websocket推送数据数据
    public static final String Bind_PhoneOrEmail_Success = "Bind_PhoneOrEmail_Success";//绑定邮箱手机号码成功
    public static final String Account_Check_Refresh = "Account_Check_Refresh";//开启验证通知我的刷新
    public static final String Bind_Google_Success = "Bind_Google_Success";//绑定google成功
    public static final String User_Identity_Authen = "User_Identity_Authen";//上传身份证资料认证成功
    public static final String Order_Select_Notice = "Order_Select_Notice";//订单筛选通知
    public static final String Token_failure = "Token_failure";//token失效通知
    public static final String User_Update_Passwrod_Success = "User_Update_Passwrod_Success";//修改密码重新登录

    public static final String COLLECTION_SILENT = "COLLECTION_SILENT";//收藏静默刷新

    //模型
    public int status; //状态
    public String status_type; //类型
    public String errmsg; //说明
    public T something; //数据

    public List<OaxMarketBean> listOaxMarketBeanGson;

}


