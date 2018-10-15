package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.oax.assets.bean.TakeRedPacketDetailsBean;
import cn.dagongniu.oax.base.IView;

public interface ITakeRedPacketDetailsView extends IView {


    int getTakeRedPacketDetailsPageIndex();//页码

    int getTakeRedPacketDetailsPageSize();//	每页显示条数

    int getTakeRedPacketDetailsId();

    void setTakeRedPacketDetailsData(TakeRedPacketDetailsBean takeRedPacketDetailsData);//回调数据

    void setTakeRedPacketDetailsNull(TakeRedPacketDetailsBean takeRedPacketDetailsNull);//回调数据

    void setTakeRedPacketDetailsMoreData(TakeRedPacketDetailsBean takeRedPacketDetailsMoreData);//加载更多回调

    void setTakeRedPacketDetailsMoreErrer(String noticeCenterMoreData);//加载更多错误回调

    void setRefreshErrer();//刷新错误


}
