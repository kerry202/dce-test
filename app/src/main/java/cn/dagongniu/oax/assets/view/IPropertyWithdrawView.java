package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.base.IView;

public interface IPropertyWithdrawView extends IView {

    String getCoinName();//币种名称

    int getStatus();//状态

    int getPropertyWithdrawPageIndex();//页码

    int getPropertyWithdrawPageSize();//	每页显示条数

    void setPropertyWithdrawData(PropertyWithdrawBean PropertyWithdrawBean);//回调数据

    void setPropertyWithdrawDataNull(PropertyWithdrawBean PropertyWithdrawBean);//回调数据

    void goLogin(String msg);

    void setRefreshPropertyWithdrawMoreData(PropertyWithdrawBean PropertyWithdrawBean);

    void setRefreshPropertyWithdrawLoadMoreErrer(String noticeCenterMoreData);

    void refreshErrer();



}
