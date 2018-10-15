package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.BindEamilModule;
import cn.dagongniu.oax.account.module.BindPhoneModule;
import cn.dagongniu.oax.account.module.SwitchCheckModule;
import cn.dagongniu.oax.account.view.IBindPhoneEmailView;
import cn.dagongniu.oax.account.view.ISwitchCheckView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 切换安全验证 Presenter
 */
public class SwitchCheckPresenter extends BasePresenter {

    private SwitchCheckModule switchCheckModule;
    private ISwitchCheckView iSwitchCheckView;
    private Activity activity;
    RequestState state;

    public SwitchCheckPresenter(ISwitchCheckView iSwitchCheckView, RequestState state) {
        super(iSwitchCheckView);
        this.state = state;
        activity = (Activity) iSwitchCheckView.getContext();
        this.iSwitchCheckView = iSwitchCheckView;
        switchCheckModule = new SwitchCheckModule(activity);
    }

    public void getSwitchCheckModule() {
        switchCheckModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iSwitchCheckView, state, data);
                    iSwitchCheckView.successful();
                } else {
                    StateBaseUtils.failure(iSwitchCheckView, state, data.getMsg());
                    iSwitchCheckView.failure(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iSwitchCheckView, state, code);
                iSwitchCheckView.failure(code);
            }
        }, state, iSwitchCheckView.getEmailCode(), iSwitchCheckView.getSmsCode(), iSwitchCheckView.getGoogleCode(), iSwitchCheckView.getCheckType() + "", iSwitchCheckView.getStatus() + "");
    }


}
