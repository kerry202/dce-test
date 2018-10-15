package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.PropertyRechargeBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.assets.module.PropertyRechargeModule;
import cn.dagongniu.oax.assets.module.PropertyWithdrawModule;
import cn.dagongniu.oax.assets.view.IPropertyRechargeView;
import cn.dagongniu.oax.assets.view.IPropertyWithdrawView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 提现记录 Presenter
 */
public class PropertyWithdrawPresenter extends BasePresenter {

    private PropertyWithdrawModule propertyWithdrawModule;
    private IPropertyWithdrawView iPropertyWithdrawView;
    private Activity activity;
    RequestState state;

    public PropertyWithdrawPresenter(IPropertyWithdrawView iPropertyWithdrawView, RequestState state) {
        super(iPropertyWithdrawView);
        this.state = state;
        activity = (Activity) iPropertyWithdrawView.getContext();
        this.iPropertyWithdrawView = iPropertyWithdrawView;
        propertyWithdrawModule = new PropertyWithdrawModule(activity);
    }

    public void getPropertyWithdrawModule() {

        propertyWithdrawModule.requestServerDataOne(new OnBaseDataListener<PropertyWithdrawBean>() {

            @Override
            public void onNewData(PropertyWithdrawBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iPropertyWithdrawView, state, data);
                    if (data.getData().getList() != null && data.getData().getList().size() > 0) {
                        iPropertyWithdrawView.setPropertyWithdrawData(data);
                    } else {
                        iPropertyWithdrawView.setPropertyWithdrawDataNull(data);
                    }

                } else {
                    StateBaseUtils.failure(iPropertyWithdrawView, state, data.getMsg());
                    iPropertyWithdrawView.refreshErrer();
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iPropertyWithdrawView.goLogin(code);
                } else {
                    StateBaseUtils.error(iPropertyWithdrawView, state, code);
                }
                iPropertyWithdrawView.refreshErrer();
            }
        }, state, iPropertyWithdrawView.getStatus() + "", iPropertyWithdrawView.getCoinName(), iPropertyWithdrawView.getPropertyWithdrawPageIndex() + "", iPropertyWithdrawView.getPropertyWithdrawPageSize() + "");
    }

    public void getPropertyWithdrawLoadModule() {

        propertyWithdrawModule.requestServerDataOne(new OnBaseDataListener<PropertyWithdrawBean>() {
            @Override
            public void onNewData(PropertyWithdrawBean data) {
                iPropertyWithdrawView.setRefreshPropertyWithdrawMoreData(data);
            }

            @Override
            public void onError(String code) {
                iPropertyWithdrawView.setRefreshPropertyWithdrawLoadMoreErrer(code);
            }
        }, state, iPropertyWithdrawView.getStatus() + "", iPropertyWithdrawView.getCoinName(), iPropertyWithdrawView.getPropertyWithdrawPageIndex() + "", iPropertyWithdrawView.getPropertyWithdrawPageSize() + "");
    }

}
