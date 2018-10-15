package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.AssetsPropertyListBean;
import cn.dagongniu.oax.assets.module.AssetsPropertyListModule;
import cn.dagongniu.oax.assets.view.IAssetsPropertyListView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.main.bean.BannerBean;
import cn.dagongniu.oax.main.bean.NoticeCenterMoreBean;
import cn.dagongniu.oax.main.bean.NoticeCenterReadDetailBean;
import cn.dagongniu.oax.main.module.BannerModule;
import cn.dagongniu.oax.main.view.BannerView;

/**
 * 公告查看详情 Presenter
 */
public class AssetsPropertyListPresenter extends BasePresenter {

    private AssetsPropertyListModule assetsPropertyListModule;
    private IAssetsPropertyListView iAssetsPropertyListView;
    private Activity activity;
    RequestState state;

    public AssetsPropertyListPresenter(IAssetsPropertyListView iAssetsPropertyListView, RequestState state) {
        super(iAssetsPropertyListView);
        this.state = state;
        activity = (Activity) iAssetsPropertyListView.getContext();
        this.iAssetsPropertyListView = iAssetsPropertyListView;
        assetsPropertyListModule = new AssetsPropertyListModule(activity);
    }

    public void getAssetsPropertyListModule() {

        assetsPropertyListModule.requestServerDataOne(new OnBaseDataListener<AssetsPropertyListBean>() {

            @Override
            public void onNewData(AssetsPropertyListBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iAssetsPropertyListView, state, data);
                    if (data.getData().getCoinList() != null && data.getData().getCoinList().size() > 0) {
                        iAssetsPropertyListView.setIAssetsPropertyData(data);
                    } else {
                        iAssetsPropertyListView.setIAssetsPropertyDataNull(data);
                    }

                } else {
                    iAssetsPropertyListView.refreshErrer();
                    StateBaseUtils.failure(iAssetsPropertyListView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iAssetsPropertyListView.goLogin(code);
                } else {
                    StateBaseUtils.error(iAssetsPropertyListView, state, code);
                }
                iAssetsPropertyListView.refreshErrer();
            }
        }, state, iAssetsPropertyListView.getCoinName(), iAssetsPropertyListView.getType() + "");
    }


}
