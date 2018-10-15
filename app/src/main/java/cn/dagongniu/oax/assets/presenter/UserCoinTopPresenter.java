package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.assets.bean.UserCoinTopBean;
import cn.dagongniu.oax.assets.module.PropertyShowModule;
import cn.dagongniu.oax.assets.module.UserCoinTopModule;
import cn.dagongniu.oax.assets.view.IPropertyShowView;
import cn.dagongniu.oax.assets.view.IUserCoinTopView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 充值 Presenter
 */
public class UserCoinTopPresenter extends BasePresenter {

    private UserCoinTopModule userCoinTopModule;
    private IUserCoinTopView iUserCoinTopView;
    private Activity activity;
    RequestState state;

    public UserCoinTopPresenter(IUserCoinTopView iUserCoinTopView, RequestState state) {
        super(iUserCoinTopView);
        this.state = state;
        activity = (Activity) iUserCoinTopView.getContext();
        this.iUserCoinTopView = iUserCoinTopView;
        userCoinTopModule = new UserCoinTopModule(activity);
    }

    public void getUserCoinTopModule() {

        userCoinTopModule.requestServerDataOne(new OnBaseDataListener<UserCoinTopBean>() {

            @Override
            public void onNewData(UserCoinTopBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iUserCoinTopView, state, data);
                    iUserCoinTopView.setUserCoinTopData(data);
                } else {
                    StateBaseUtils.failure(iUserCoinTopView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iUserCoinTopView.goLogin(code);
                } else {
                    StateBaseUtils.error(iUserCoinTopView, state, code);
                }
            }
        }, state, iUserCoinTopView.getCoinId());
    }


}
