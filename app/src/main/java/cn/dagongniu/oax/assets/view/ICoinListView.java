package cn.dagongniu.oax.assets.view;

import cn.dagongniu.oax.assets.bean.CoinListBean;
import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.base.IView;

public interface ICoinListView extends IView {


    void setCoinListBeanData(CoinListBean coinListBean);

    void refreshErrer();

}
