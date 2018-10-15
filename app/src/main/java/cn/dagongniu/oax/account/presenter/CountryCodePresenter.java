package cn.dagongniu.oax.account.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.bean.CountryCodeBean;
import cn.dagongniu.oax.account.module.CountryCodeModule;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.utils.Logger;

/**
 * 国家区号 Presenter
 */
public class CountryCodePresenter extends BasePresenter {

    private CountryCodeModule countryCodeModule;
    private Activity activity;
    IView view;
    RequestState state;
    StateBaseUtils stateBaseUtils = new StateBaseUtils();

    public CountryCodePresenter(IView view, RequestState state) {
        super(view);
        this.state = state;
        activity = (Activity) view.getContext();
        this.view = view;
        countryCodeModule = new CountryCodeModule(activity);
    }

    /***
     * 国家区号
     */
    public void getCountryCodeModule() {

        countryCodeModule.requestServerDataOne(new OnBaseDataListener<CountryCodeBean>() {

            @Override
            public void onNewData(CountryCodeBean data) {
                if (data.isSuccess()) {
                    Logger.e("***************", data.toString());
                    data.getData();
                    stateBaseUtils.success(view, state, data);
                } else {
                    stateBaseUtils.failure(view, state, data.getMsg());
                }
            }

            //TODO sendSmsView.getChooseCountries() +  目前不加
            @Override
            public void onError(String code) {
                stateBaseUtils.error(view, state, code);
            }
        }, state);
    }

}
