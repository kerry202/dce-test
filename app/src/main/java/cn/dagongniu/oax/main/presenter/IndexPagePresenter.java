package cn.dagongniu.oax.main.presenter;

import android.app.Activity;

import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.module.BannerModule;
import cn.dagongniu.oax.main.module.IndexPageModule;
import cn.dagongniu.oax.main.view.BannerView;
import cn.dagongniu.oax.main.view.IndexPageView;
import cn.dagongniu.oax.utils.Logs;

/**
 * 首页banner加载 Presenter
 */
public class IndexPagePresenter extends BasePresenter {

    private IndexPageModule indexPageModule;
    private IndexPageView indexPageView;
    private Activity activity;
    RequestState state;

    public IndexPagePresenter(IndexPageView indexPageView, RequestState state) {
        super(indexPageView);
        this.state = state;
        activity = (Activity) indexPageView.getContext();
        this.indexPageView = indexPageView;
        indexPageModule = new IndexPageModule(activity);
    }

    public void getIndexPageModule() {

        indexPageModule.requestServerDataOne(new OnBaseDataListener<IndexPageBean>() {

            @Override
            public void onNewData(IndexPageBean data) {

                Logs.s("    市场接口成功   "+data);
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(indexPageView, state, data);
                    indexPageView.setIndexPageData(data);
                } else {
                    StateBaseUtils.failure(indexPageView, state, data.getMsg());
                    indexPageView.refreshErrer();
                }
            }

            @Override
            public void onError(String code) {
                Logs.s("    市场接口error   ");
                StateBaseUtils.error(indexPageView, state, code);
                indexPageView.refreshErrer();
            }
        }, state);
    }


    public void getIndexPageRefreshModule() {

        indexPageModule.requestServerDataOne(new OnBaseDataListener<IndexPageBean>() {

            @Override
            public void onNewData(IndexPageBean data) {
                Logs.s("   首页list数据：  "+data.toString());
                indexPageView.setRefreshIndexPageData(data);
            }

            @Override
            public void onError(String code) {
                indexPageView.setRefreshIndexPageDataErrer(code);
            }
        }, state);
    }

    /**
     * 静默刷新
     */
    public void getIndexPageSilentRefreshModule() {

        indexPageModule.requestServerDataOne(new OnBaseDataListener<IndexPageBean>() {

            @Override
            public void onNewData(IndexPageBean data) {
                indexPageView.setSilentRefreshIndexPageData(data);
            }

            @Override
            public void onError(String code) {
                indexPageView.setRefreshIndexPageDataErrer(code);
            }
        }, state);
    }

}
