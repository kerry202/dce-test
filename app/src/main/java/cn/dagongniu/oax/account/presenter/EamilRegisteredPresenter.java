package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.EamilRegisteredModule;
import cn.dagongniu.oax.account.view.IRegisteredView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/***
 * 邮箱注册 Presenter
 */
public class EamilRegisteredPresenter extends BasePresenter {

    private EamilRegisteredModule eamilRegisteredModule;
    private IRegisteredView iRegisteredView;
    private Activity activity;
    RequestState state;

    public EamilRegisteredPresenter(IRegisteredView iRegisteredView, RequestState state) {
        super(iRegisteredView);
        this.state = state;
        activity = (Activity) iRegisteredView.getContext();
        this.iRegisteredView = iRegisteredView;
        eamilRegisteredModule = new EamilRegisteredModule(activity);
    }

    /***
     * 邮箱注册
     */
    public void getEamilRegisteredModule() {
        eamilRegisteredModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iRegisteredView, state, data);
                } else {
                    StateBaseUtils.failure(iRegisteredView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iRegisteredView, state, code);
            }
        }, state, iRegisteredView.getEmailAndPhone(), iRegisteredView.getPassword(), iRegisteredView.getPassword(), iRegisteredView.getInvateCode());
    }

}
