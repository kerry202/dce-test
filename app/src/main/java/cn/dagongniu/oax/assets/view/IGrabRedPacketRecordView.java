package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.GrabRedPacketRecordBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.base.IView;

public interface IGrabRedPacketRecordView extends IView {


    int getGrabRedPacketRecordPageIndex();//页码

    int getGrabRedPacketRecordPageSize();//	每页显示条数

    void setGrabRedPacketRecordData(GrabRedPacketRecordBean grabRedPacketRecordData);//回调数据

    void setGrabRedPacketRecordNull(GrabRedPacketRecordBean grabRedPacketRecordData);//回调数据

    void setGrabRedPacketRecordMoreData(GrabRedPacketRecordBean grabRedPacketRecordData);//加载更多回调

    void setGrabRedPacketRecordMoreErrer(String noticeCenterMoreData);//加载更多错误回调

    void setRefreshErrer();//刷新错误


}
