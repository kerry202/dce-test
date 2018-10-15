package cn.dagongniu.oax.main.presenter;

import android.app.Activity;

import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.HttpBaseBean;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;
import cn.dagongniu.oax.main.module.BannerModule;
import cn.dagongniu.oax.main.module.NoticeCenterReadDetailModule;
import cn.dagongniu.oax.main.view.BannerView;
import cn.dagongniu.oax.main.view.INoticeCenterReadDetailView;

/**
 * 公告查看详情 Presenter
 */
public class NoticeCenterReadDetailPresenter extends BasePresenter {

    private NoticeCenterReadDetailModule noticeCenterReadDetailModule;
    private INoticeCenterReadDetailView iNoticeCenterReadDetailView;
    private Activity activity;
    RequestState state;

    public NoticeCenterReadDetailPresenter(INoticeCenterReadDetailView iNoticeCenterReadDetailView, RequestState state) {
        super(iNoticeCenterReadDetailView);
        this.state = state;
        activity = (Activity) iNoticeCenterReadDetailView.getContext();
        this.iNoticeCenterReadDetailView = iNoticeCenterReadDetailView;
        noticeCenterReadDetailModule = new NoticeCenterReadDetailModule(activity);
    }

    public void getNoticeCenterReadDetailModule() {

        noticeCenterReadDetailModule.requestServerDataOne(new OnBaseDataListener<NoticeCenterReadDetailBean>() {

            @Override
            public void onNewData(NoticeCenterReadDetailBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iNoticeCenterReadDetailView, state, data);
                    iNoticeCenterReadDetailView.setNoticeCenterReadDetailData(data);
                } else {
                    StateBaseUtils.failure(iNoticeCenterReadDetailView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iNoticeCenterReadDetailView, state, code);
            }
        }, state,iNoticeCenterReadDetailView.getReadDetail());
    }

}
