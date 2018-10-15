package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.UserCoinTopBean;
import cn.dagongniu.oax.assets.bean.UserCoinWithdrawalBean;
import cn.dagongniu.oax.assets.module.UserCoinTopModule;
import cn.dagongniu.oax.assets.module.UserCoinWithdrawalModule;
import cn.dagongniu.oax.assets.view.IUserCoinTopView;
import cn.dagongniu.oax.assets.view.IUserCoinWithdrawalView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 提现 Presenter
 */
public class UserCoinWithdrawalPresenter extends BasePresenter {

    private UserCoinWithdrawalModule userCoinWithdrawalModule;
    private IUserCoinWithdrawalView iUserCoinWithdrawalView;
    private Activity activity;
    RequestState state;

    public UserCoinWithdrawalPresenter(IUserCoinWithdrawalView iUserCoinWithdrawalView, RequestState state) {
        super(iUserCoinWithdrawalView);
        this.state = state;
        activity = (Activity) iUserCoinWithdrawalView.getContext();
        this.iUserCoinWithdrawalView = iUserCoinWithdrawalView;
        userCoinWithdrawalModule = new UserCoinWithdrawalModule(activity);
    }

    public void getUserCoinWithdrawalModule() {

        userCoinWithdrawalModule.requestServerDataOne(new OnBaseDataListener<UserCoinWithdrawalBean>() {

            @Override
            public void onNewData(UserCoinWithdrawalBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iUserCoinWithdrawalView, state, data);
                    iUserCoinWithdrawalView.setUserCoinWithdrawalData(data);
                } else {
                    StateBaseUtils.failure(iUserCoinWithdrawalView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iUserCoinWithdrawalView.goLogin(code);
                } else {
                    StateBaseUtils.error(iUserCoinWithdrawalView, state, code);
                }
            }
        }, state, iUserCoinWithdrawalView.getCoinId());
    }


}
