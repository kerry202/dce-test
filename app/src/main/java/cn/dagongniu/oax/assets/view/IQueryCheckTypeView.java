package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.QueryCheckTypeBean;
import cn.dagongniu.oax.base.IView;

public interface IQueryCheckTypeView extends IView {

    String getUsername();//账号


    void setQueryCheckTypeData(QueryCheckTypeBean queryCheckTypeData);//回调数据


}
