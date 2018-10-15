package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.assets.bean.AwardRedPacketBean;
import cn.dagongniu.oax.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.oax.assets.bean.TakeRedPacketDetailsBean;
import cn.dagongniu.oax.assets.module.SendRedPacketRecordModule;
import cn.dagongniu.oax.assets.module.TakeRedPacketDetailsModule;
import cn.dagongniu.oax.assets.view.ISendRedPacketRecordView;
import cn.dagongniu.oax.assets.view.ITakeRedPacketDetailsView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 发送红包详情 Presenter
 */
public class TakeRedPacketDetailsPresenter extends BasePresenter {

    private TakeRedPacketDetailsModule takeRedPacketDetailsModule;
    private ITakeRedPacketDetailsView iTakeRedPacketDetailsView;
    private Activity activity;
    RequestState state;

    public TakeRedPacketDetailsPresenter(ITakeRedPacketDetailsView iTakeRedPacketDetailsView, RequestState state) {
        super(iTakeRedPacketDetailsView);
        this.state = state;
        activity = (Activity) iTakeRedPacketDetailsView.getContext();
        this.iTakeRedPacketDetailsView = iTakeRedPacketDetailsView;
        takeRedPacketDetailsModule = new TakeRedPacketDetailsModule(activity);
    }

    public void getTakeRedPacketDetailsModule() {

        takeRedPacketDetailsModule.requestServerDataOne(new OnBaseDataListener<TakeRedPacketDetailsBean>() {

            @Override
            public void onNewData(TakeRedPacketDetailsBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iTakeRedPacketDetailsView, state, data);
                    if (data.getData().getRedPacketLogDetailsPageInfo().getList() == null || data.getData().getRedPacketLogDetailsPageInfo().getList().size() <= 0) {
                        iTakeRedPacketDetailsView.setTakeRedPacketDetailsNull(data);
                    } else {
                        iTakeRedPacketDetailsView.setTakeRedPacketDetailsData(data);
                    }

                } else {
                    StateBaseUtils.failure(iTakeRedPacketDetailsView, state, data.getMsg());
                    iTakeRedPacketDetailsView.setRefreshErrer();
                }
            }

            @Override
            public void onError(String code) {

                StateBaseUtils.error(iTakeRedPacketDetailsView, state, code);
                iTakeRedPacketDetailsView.setRefreshErrer();
            }
        }, state, iTakeRedPacketDetailsView.getTakeRedPacketDetailsPageIndex() + "", iTakeRedPacketDetailsView.getTakeRedPacketDetailsPageSize() + "", iTakeRedPacketDetailsView.getTakeRedPacketDetailsId() + "");
    }

    /**
     * 加载更多
     */
    public void getTakeRedPacketDetailsLoadModule() {

        takeRedPacketDetailsModule.requestServerDataOne(new OnBaseDataListener<TakeRedPacketDetailsBean>() {
            @Override
            public void onNewData(TakeRedPacketDetailsBean data) {
                iTakeRedPacketDetailsView.setTakeRedPacketDetailsMoreData(data);
            }

            @Override
            public void onError(String code) {
                iTakeRedPacketDetailsView.setTakeRedPacketDetailsMoreErrer(code);
            }
        }, state, iTakeRedPacketDetailsView.getTakeRedPacketDetailsPageIndex() + "", iTakeRedPacketDetailsView.getTakeRedPacketDetailsPageSize() + "", iTakeRedPacketDetailsView.getTakeRedPacketDetailsId() + "");
    }

}
