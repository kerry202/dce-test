package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.AwardRedPacketBean;
import cn.dagongniu.oax.assets.bean.RedPacketIndexBean;
import cn.dagongniu.oax.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.oax.base.IView;

public interface IRedPacketIndexView extends IView {

    static String ordinaryTYPE = "1"; //普通
    static String randomTYPE = "2";  //随机

    void setOrdinaryData(RedPacketIndexBean redPacketIndexBean);//回调数据

    void setRandomData(RedPacketIndexBean redPacketIndexBean);//回调数据

    void setOrdinaryRefreshData(RedPacketIndexBean redPacketIndexBean);//回调数据

    void setRandomRefreshData(RedPacketIndexBean redPacketIndexBean);//回调数据

    void setOrdinaryDataErrer(String msg);//回调数据

    void setRandomDataErrer(String msg);//回调数据
}
