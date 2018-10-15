package cn.dagongniu.oax.trading.presenter;

import android.app.Activity;


import java.util.HashMap;

import cn.dagongniu.oax.base.OAXBasePresenter;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.OAXStateBaseUtils;
import cn.dagongniu.oax.https.OnDataListener;
import cn.dagongniu.oax.https.RequestState;
import cn.dagongniu.oax.trading.bean.EntrustInfoBean;
import cn.dagongniu.oax.trading.bean.CurrentEntrustBean;
import cn.dagongniu.oax.trading.fragment.EntrustIView;
import cn.dagongniu.oax.trading.module.EntrustModule;

public class EntrustPresenter extends OAXBasePresenter {

    private EntrustModule mModule;
    private Activity activity;
    EntrustIView view;
    RequestState mState;

    public EntrustPresenter(EntrustIView view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mModule = new EntrustModule(activity);
    }

    /**
     * 获取 用户余额 托管订单信息
     *
     * @param marketId
     * @param state
     */
    public void getEntrustInfo(int marketId, RequestState state) {
        mModule.getCommitteeList(marketId + "", state, new EntrustModule.OnEntrustDataListener() {
            @Override
            public void newData(CommonJsonToBean<EntrustInfoBean> bean) {
                if (bean.getSuccess()) {
                    view.onTopicTradeListData(bean.getData());
                } else {
                    view.showToast(bean.getMsg());
                }

            }

            @Override
            public void onError(String msg) {
                view.showToast(msg);
            }
        });
    }

    /**
     * 余额 订单信息
     */
    public void topicTradeList(int marketId, String userId) {
        mModule.topicTradeList(marketId, userId, new EntrustModule.OnUserEntrustInfoListener() {
            @Override
            public void onTopicTradeListData(EntrustInfoBean bean) {
                view.onTopicTradeListData(bean);
            }
        });
    }

    /**
     * 取消订阅 余额 订单信息
     */
    public void unTopicTradeList() {
        if (mModule != null) {
            mModule.unTopicTradeList();
        }
    }

    /**
     * 点对点获取 余额 订单信息
     */
    public void sendTradeList(int marketId, int userId) {
        mModule.sendTradeList(marketId, userId);
    }

    public void cancellations(int id, RequestState state) {
        mModule.cancellations(id, null, state, new EntrustModule.OnCancellationsListener() {
            @Override
            public void newCancellationsInfo(CommonJsonToBean<String> data) {
                view.onCancellationsState(data);
            }

            @Override
            public void onError(String msg) {
                view.showToast(msg);
            }
        });
    }


    /**
     * 当前委托
     *
     * @param map
     * @param state
     */
    public void getCurrentEntrust(HashMap<String, Object> map, RequestState state, boolean isRefresh, boolean isLoadMore) {
        mModule.getCurrentEntrust(map, state, new OnDataListener<CurrentEntrustBean>() {
            @Override
            public void onNewData(CommonJsonToBean<CurrentEntrustBean> data) {

                if (data == null || data.getData() == null || data.getData().getList() == null || data.getData().getList().size() == 0) {
                    OAXStateBaseUtils.isNull(view, state, data.getMsg());
                } else {
                    if (isRefresh) {
                        view.onRefreshCurrentEnstrust(data);
                    } else if (isLoadMore) {
                        view.onLoadMoreCurrentEnstrust(data);
                    } else {
                        view.onCurrentEnstrust(data);
                    }
                }

            }

            @Override
            public void onError(String msg) {
                OAXStateBaseUtils.error(view, state, msg);
            }
        });
    }
}
