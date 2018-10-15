package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.bean.IdentityResultBean;
import cn.dagongniu.oax.account.module.IdentityResultModule;
import cn.dagongniu.oax.account.module.SwitchCheckModule;
import cn.dagongniu.oax.account.view.ISwitchCheckView;
import cn.dagongniu.oax.account.view.IidentityResultView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 身份认证审核结果 Presenter
 */
public class IdentityResultPresenter extends BasePresenter {

    private IdentityResultModule identityResultModule;
    private IidentityResultView iidentityResultView;
    private Activity activity;
    RequestState state;

    public IdentityResultPresenter(IidentityResultView iidentityResultView, RequestState state) {
        super(iidentityResultView);
        this.state = state;
        activity = (Activity) iidentityResultView.getContext();
        this.iidentityResultView = iidentityResultView;
        identityResultModule = new IdentityResultModule(activity);
    }

    public void getIdentityResultModule() {
        identityResultModule.requestServerDataOne(new OnBaseDataListener<IdentityResultBean>() {

            @Override
            public void onNewData(IdentityResultBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    iidentityResultView.IidentityResultsuccessful(data);
                } else {
                    iidentityResultView.IidentityResultfailure(data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iidentityResultView, state, code);
            }
        }, state);
    }


}
