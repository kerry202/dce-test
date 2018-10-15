package cn.dagongniu.oax.main.presenter;

import android.app.Activity;

import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.base.IView;
import cn.dagongniu.oax.https.StateBaseUtils;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.main.bean.TestBean;
import cn.dagongniu.oax.main.module.HomeModule;
import cn.dagongniu.oax.utils.AppConstants;


/**
 * 首页控制器
 */

public class HomePresenter extends BasePresenter {

    private HomeModule homeModule;
    private IView view;
    private Activity activity;
    RequestState state;

    public HomePresenter(IView iView, RequestState state) {
        super(iView);
        this.state = state;
        activity = (Activity) iView.getContext();
        view = iView;
        homeModule = new HomeModule(activity);
    }

    /***
     * 广场首页
     */
    public void gethomeData() {
        homeModule.requestServerDataOne(new OnBaseDataListener<TestBean>() {
            @Override
            public void onNewData(TestBean data) {
                if (data.getType() == AppConstants.SUCCESS_CODE) {
                    //响应请求数据回去
                    //StateBaseUtils.success(view, state, data);
                    //TODO 此地加一个判断~ 是否有数据
                    if (false) {
                        StateBaseUtils.isNull(view, state, data.getMsg());
                    }
                } else {
                    StateBaseUtils.failure(view, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(view, state, code);
            }
        }, state);
    }

}
