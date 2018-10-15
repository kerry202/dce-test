package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.AwardRedPacketBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.base.IView;

public interface IAwardRedPacketView extends IView {

    static int ShareWxType = 1;
    static int SharePyqType = 2;
    static int ShareQqType = 3;
    static int ShareCopyType = 4;

    String getCoinId(); //币种id

    String getType(); // 	红包类型

    String getTotalCoinQty(); //	红包总金额

    String getTotalPacketQty();  //红包数量

    String getWishWords(); //	祝福语

    void setAwardRedPacketData(AwardRedPacketBean awardRedPacketData,int type);//回调数据

    void setsetAwardRedPacketDataErrer(AwardRedPacketBean awardRedPacketBean);//刷新错误


}
