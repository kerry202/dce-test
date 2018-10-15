package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.base.IView;

public interface ICoinAddressListView extends IView {

    String getCoinId();//币种id

    int getCoinAddressListIndex();//页码

    int getCoinAddressListPageSize();//	每页显示条数

    void setCoinAddressListData(CoinAddressListBean coinAddressListData);//回调数据

    void goLogin(String msg);



}
