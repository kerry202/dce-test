package cn.dagongniu.oax.assets.presenter;

import android.app.Activity;

import cn.dagongniu.oax.R;
import cn.dagongniu.oax.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.oax.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.oax.assets.module.PropertyWithdrawModule;
import cn.dagongniu.oax.assets.module.SendRedPacketRecordModule;
import cn.dagongniu.oax.assets.view.IPropertyWithdrawView;
import cn.dagongniu.oax.assets.view.ISendRedPacketRecordView;
import cn.dagongniu.oax.base.BasePresenter;
import cn.dagongniu.oax.https.OnBaseDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.https.StateBaseUtils;

/**
 * 我发出的红包记录 Presenter
 */
public class SendRedPacketRecordPresenter extends BasePresenter {

    private SendRedPacketRecordModule sendRedPacketRecordModule;
    private ISendRedPacketRecordView iSendRedPacketRecordView;
    private Activity activity;
    RequestState state;

    public SendRedPacketRecordPresenter(ISendRedPacketRecordView iSendRedPacketRecordView, RequestState state) {
        super(iSendRedPacketRecordView);
        this.state = state;
        activity = (Activity) iSendRedPacketRecordView.getContext();
        this.iSendRedPacketRecordView = iSendRedPacketRecordView;
        sendRedPacketRecordModule = new SendRedPacketRecordModule(activity);
    }

    public void getSendRedPacketRecordModule() {

        sendRedPacketRecordModule.requestServerDataOne(new OnBaseDataListener<SendRedPacketRecordBean>() {

            @Override
            public void onNewData(SendRedPacketRecordBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iSendRedPacketRecordView, state, data);
                    if (data.getData().getPageInfo().getList() == null || data.getData().getPageInfo().getList().size() <= 0) {
                        iSendRedPacketRecordView.setSendRedPacketRecordNull(data);
                    } else {
                        iSendRedPacketRecordView.setSendRedPacketRecordData(data);
                    }

                } else {
                    StateBaseUtils.failure(iSendRedPacketRecordView, state, data.getMsg());
                    iSendRedPacketRecordView.setRefreshErrer();
                }
            }

            @Override
            public void onError(String code) {

                StateBaseUtils.error(iSendRedPacketRecordView, state, code);
                iSendRedPacketRecordView.setRefreshErrer();
            }
        }, state, iSendRedPacketRecordView.getSendRedPacketRecordPageIndex() + "", iSendRedPacketRecordView.getSendRedPacketRecordPageSize() + "");
    }

    /**
     * 加载更多
     */
    public void getSendRedPacketRecordLoadModule() {

        sendRedPacketRecordModule.requestServerDataOne(new OnBaseDataListener<SendRedPacketRecordBean>() {
            @Override
            public void onNewData(SendRedPacketRecordBean data) {
                iSendRedPacketRecordView.setSendRedPacketRecordMoreData(data);
            }

            @Override
            public void onError(String code) {
                iSendRedPacketRecordView.setSendRedPacketRecordMoreErrer(code);
            }
        }, state, iSendRedPacketRecordView.getSendRedPacketRecordPageIndex() + "", iSendRedPacketRecordView.getSendRedPacketRecordPageSize() + "");
    }

}
