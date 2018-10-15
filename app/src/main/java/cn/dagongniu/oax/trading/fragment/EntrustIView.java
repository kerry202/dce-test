package cn.dagongniu.oax.trading.fragment;

import cn.dagongniu.oax.base.OAXIView;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.trading.bean.EntrustInfoBean;
import cn.dagongniu.oax.trading.bean.CurrentEntrustBean;

public interface EntrustIView extends OAXIView {
    void onTopicTradeListData(EntrustInfoBean bean);

    void onCancellationsState(CommonJsonToBean<String> state);

    void onCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean);

    void onRefreshCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean);

    void onLoadMoreCurrentEnstrust(CommonJsonToBean<CurrentEntrustBean> bean);
}
