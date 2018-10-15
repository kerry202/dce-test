package cn.dagongniu.oax.main.presenter;

import android.app.Activity;

import cn.dagongniu.oax.account.module.SendEamilCodeModule;
import cn.dagongniu.oax.account.view.SendSmsView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.module.BannerModule;
import cn.dagongniu.oax.main.view.BannerView;

/**
 * 首页banner加载 Presenter
 */
public class BannerPresenter extends BasePresenter {

    private BannerModule bannerModule;
    private BannerView bannerView;
    private Activity activity;
    RequestState state;

    public BannerPresenter(BannerView bannerView, RequestState state) {
        super(bannerView);
        this.state = state;
        activity = (Activity) bannerView.getContext();
        this.bannerView = bannerView;
        bannerModule = new BannerModule(activity);
    }

    public void getBannerModule() {

        bannerModule.requestServerDataOne(new OnBaseDataListener<BannerBean>() {

            @Override
            public void onNewData(BannerBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(bannerView, state, data);
                    bannerView.setBannerData(data);
                } else {
                    StateBaseUtils.failure(bannerView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(bannerView, state, code);
            }
        }, state);
    }

}
