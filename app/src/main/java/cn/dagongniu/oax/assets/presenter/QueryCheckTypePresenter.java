package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.QueryCheckTypeBean;
import cn.dagongniu.oax.assets.module.CoinAddressAddModule;
import cn.dagongniu.oax.assets.module.QueryCheckTypeModule;
import cn.dagongniu.oax.assets.view.ICoinAddressListView;
import cn.dagongniu.oax.assets.view.IQueryCheckTypeView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 查询需要的验证类型 Presenter
 */
public class QueryCheckTypePresenter extends BasePresenter {

    private QueryCheckTypeModule queryCheckTypeModule;
    private IQueryCheckTypeView iQueryCheckTypeView;
    private Activity activity;
    RequestState state;

    public QueryCheckTypePresenter(IQueryCheckTypeView iQueryCheckTypeView, RequestState state) {
        super(iQueryCheckTypeView);
        this.state = state;
        activity = (Activity) iQueryCheckTypeView.getContext();
        this.iQueryCheckTypeView = iQueryCheckTypeView;
        queryCheckTypeModule = new QueryCheckTypeModule(activity);
    }

    public void getQueryCheckTypeModule() {
        queryCheckTypeModule.requestServerDataOne(new OnBaseDataListener<QueryCheckTypeBean>() {
            @Override
            public void onNewData(QueryCheckTypeBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iQueryCheckTypeView, state, data);
                    iQueryCheckTypeView.setQueryCheckTypeData(data);
                } else {
                    StateBaseUtils.failure(iQueryCheckTypeView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iQueryCheckTypeView, state, code);
            }
        }, state, iQueryCheckTypeView.getUsername());
    }


}
