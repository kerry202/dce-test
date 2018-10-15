package cn.dagongniu.oax.main.presenter;

import android.app.Activity;

import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.NoticeCenterMoreBean;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;
import cn.dagongniu.oax.main.module.NoticeCenterReadDetailModule;
import cn.dagongniu.oax.main.module.NoticeCenterReadMoreModule;
import cn.dagongniu.oax.main.view.INoticeCenterMorelView;
import cn.dagongniu.oax.main.view.INoticeCenterReadDetailView;

/**
 * 公告查看更多 Presenter
 */
public class NoticeCenterReadMorePresenter extends BasePresenter {

    private NoticeCenterReadMoreModule noticeCenterReadDetailModule;
    private INoticeCenterMorelView iNoticeCenterMorelView;
    private Activity activity;
    RequestState state;

    public NoticeCenterReadMorePresenter(INoticeCenterMorelView iNoticeCenterMorelView, RequestState state) {
        super(iNoticeCenterMorelView);
        this.state = state;
        activity = (Activity) iNoticeCenterMorelView.getContext();
        this.iNoticeCenterMorelView = iNoticeCenterMorelView;
        noticeCenterReadDetailModule = new NoticeCenterReadMoreModule(activity);
    }

    public void getNoticeCenterReadMoreModule() {

        noticeCenterReadDetailModule.requestServerDataOne(new OnBaseDataListener<NoticeCenterMoreBean>() {

            @Override
            public void onNewData(NoticeCenterMoreBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iNoticeCenterMorelView, state, data);
                    iNoticeCenterMorelView.setNoticeCenterMoreData(data);
                } else {
                    StateBaseUtils.failure(iNoticeCenterMorelView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iNoticeCenterMorelView, state, code);
            }
        }, state, iNoticeCenterMorelView.getNoticeCenterMoreType(), iNoticeCenterMorelView.getNoticeCenterPageIndex() + "", iNoticeCenterMorelView.getNoticeCenterPageSize() + "");
    }


    public void getNoticeCenterReadMoreRefreshModule() {

        noticeCenterReadDetailModule.requestServerDataOne(new OnBaseDataListener<NoticeCenterMoreBean>() {

            @Override
            public void onNewData(NoticeCenterMoreBean data) {
                iNoticeCenterMorelView.setRefreshNoticeCenterMoreData(data);
            }

            @Override
            public void onError(String code) {
                iNoticeCenterMorelView.setRefreshNoticeCenterMoreErrer(code);
            }
        }, state, iNoticeCenterMorelView.getNoticeCenterMoreType(), iNoticeCenterMorelView.getNoticeCenterPageIndex() + "", iNoticeCenterMorelView.getNoticeCenterPageSize() + "");
    }

    public void getNoticeCenterReadMoreLoadModule() {

        noticeCenterReadDetailModule.requestServerDataOne(new OnBaseDataListener<NoticeCenterMoreBean>() {
            @Override
            public void onNewData(NoticeCenterMoreBean data) {
                iNoticeCenterMorelView.setRefreshNoticeCenterLoadMoreData(data);
            }

            @Override
            public void onError(String code) {
                iNoticeCenterMorelView.setRefreshNoticeCenterLoadMoreErrer(code);
            }
        }, state, iNoticeCenterMorelView.getNoticeCenterMoreType(), iNoticeCenterMorelView.getNoticeCenterPageIndex() + "", iNoticeCenterMorelView.getNoticeCenterPageSize() + "");
    }


}
