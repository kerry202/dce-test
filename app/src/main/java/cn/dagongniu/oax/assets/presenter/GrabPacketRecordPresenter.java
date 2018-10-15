package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.assets.bean.GrabRedPacketRecordBean;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.assets.module.GrabRedPacketRecordModule;
import cn.dagongniu.oax.assets.module.SendRedPacketRecordModule;
import cn.dagongniu.oax.assets.view.IGrabRedPacketRecordView;
import cn.dagongniu.oax.assets.view.ISendRedPacketRecordView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 我领取的红包记录 Presenter
 */
public class GrabPacketRecordPresenter extends BasePresenter {

    private GrabRedPacketRecordModule grabRedPacketRecordModule;
    private IGrabRedPacketRecordView iGrabRedPacketRecordView;
    private Activity activity;
    RequestState state;

    public GrabPacketRecordPresenter(IGrabRedPacketRecordView iGrabRedPacketRecordView, RequestState state) {
        super(iGrabRedPacketRecordView);
        this.state = state;
        activity = (Activity) iGrabRedPacketRecordView.getContext();
        this.iGrabRedPacketRecordView = iGrabRedPacketRecordView;
        grabRedPacketRecordModule = new GrabRedPacketRecordModule(activity);
    }

    public void getGrabRedPacketRecordModule() {

        grabRedPacketRecordModule.requestServerDataOne(new OnBaseDataListener<GrabRedPacketRecordBean>() {

            @Override
            public void onNewData(GrabRedPacketRecordBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iGrabRedPacketRecordView, state, data);
                    if (data.getData().getGrabRedPacketPageInfo().getList() == null || data.getData().getGrabRedPacketPageInfo().getList().size() <= 0) {
                        iGrabRedPacketRecordView.setGrabRedPacketRecordNull(data);
                    } else {
                        iGrabRedPacketRecordView.setGrabRedPacketRecordData(data);
                    }

                } else {
                    StateBaseUtils.failure(iGrabRedPacketRecordView, state, data.getMsg());
                    iGrabRedPacketRecordView.setRefreshErrer();
                }
            }

            @Override
            public void onError(String code) {

                StateBaseUtils.error(iGrabRedPacketRecordView, state, code);
                iGrabRedPacketRecordView.setRefreshErrer();
            }
        }, state, iGrabRedPacketRecordView.getGrabRedPacketRecordPageIndex() + "", iGrabRedPacketRecordView.getGrabRedPacketRecordPageSize() + "");
    }

    /**
     * 加载更多
     */
    public void getGrabRedPacketRecordLoadModule() {

        grabRedPacketRecordModule.requestServerDataOne(new OnBaseDataListener<GrabRedPacketRecordBean>() {
            @Override
            public void onNewData(GrabRedPacketRecordBean data) {
                iGrabRedPacketRecordView.setGrabRedPacketRecordMoreData(data);
            }

            @Override
            public void onError(String code) {
                iGrabRedPacketRecordView.setGrabRedPacketRecordMoreErrer(code);
            }
        }, state, iGrabRedPacketRecordView.getGrabRedPacketRecordPageIndex() + "", iGrabRedPacketRecordView.getGrabRedPacketRecordPageSize() + "");
    }

}
