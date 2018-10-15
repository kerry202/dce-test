package cn.dagongniu.oax.main.presenter;

import android.app.Activity;

import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.bean.NoticeCenterBean;
import cn.dagongniu.oax.main.module.BannerModule;
import cn.dagongniu.oax.main.module.NoticeCenterModule;
import cn.dagongniu.oax.main.view.BannerView;
import cn.dagongniu.oax.main.view.NoticeCenterView;

/**
 * 首页公告 Presenter
 */
public class NoticeCenterPresenter extends BasePresenter {

    private NoticeCenterModule noticeCenterModule;
    private NoticeCenterView noticeCenterView;
    private Activity activity;
    RequestState state;

    public NoticeCenterPresenter(NoticeCenterView noticeCenterView, RequestState state) {
        super(noticeCenterView);
        this.state = state;
        activity = (Activity) noticeCenterView.getContext();
        this.noticeCenterView = noticeCenterView;
        noticeCenterModule = new NoticeCenterModule(activity);
    }

    public void getNoticeCenterModule() {

        noticeCenterModule.requestServerDataOne(new OnBaseDataListener<NoticeCenterBean>() {

            @Override
            public void onNewData(NoticeCenterBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(noticeCenterView, state, data);
                    noticeCenterView.setNoticeCenterData(data);
                } else {
                    StateBaseUtils.failure(noticeCenterView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(noticeCenterView, state, code);
            }
        }, state);
    }

}
