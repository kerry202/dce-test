package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.CoinAddressListBean;
import cn.dagongniu.oax.assets.bean.WithdrawalBean;
import cn.dagongniu.oax.assets.module.CoinAddressAddModule;
import cn.dagongniu.oax.assets.module.WithdrawalModule;
import cn.dagongniu.oax.assets.view.ICoinAddressView;
import cn.dagongniu.oax.assets.view.IWithdrawalView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 提现 Presenter
 */
public class WithdrawalPresenter extends BasePresenter {

    private WithdrawalModule withdrawalModule;
    private IWithdrawalView iWithdrawalView;
    private Activity activity;
    RequestState state;

    public WithdrawalPresenter(IWithdrawalView iWithdrawalView, RequestState state) {
        super(iWithdrawalView);
        this.state = state;
        activity = (Activity) iWithdrawalView.getContext();
        this.iWithdrawalView = iWithdrawalView;
        withdrawalModule = new WithdrawalModule(activity);
    }

    public void getWithdrawalModule() {
        withdrawalModule.requestServerDataOne(new OnBaseDataListener<WithdrawalBean>() {
            @Override
            public void onNewData(WithdrawalBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iWithdrawalView, state, data);
                    iWithdrawalView.isSuccess();
                } else {
                    StateBaseUtils.failure(iWithdrawalView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iWithdrawalView.goLogin(code);
                } else {
                    StateBaseUtils.error(iWithdrawalView, state, code);
                }
            }
        }, state, iWithdrawalView.getAddress(), iWithdrawalView.getRemark(), iWithdrawalView.getqty(), iWithdrawalView.getCoinId());
    }


}
