package cn.dagongniu.oax.trading.fragment;

import java.util.List;

import cn.dagongniu.oax.base.OAXIView;
import cn.dagongniu.oax.https.CommonJsonToBean;
import cn.dagongniu.oax.https.CommonJsonToList;
import cn.dagongniu.oax.kline.bean.TradingInfoBean;

public interface OaxTradingIView extends OAXIView {

    /**
     * 市场交易对，用户搜藏交易对，k线图，实时委托，实时交易
     *
     * @param data
     */
    void setTradingInfoData(CommonJsonToBean<TradingInfoBean> data);

    /**
     * 买卖
     *
     * @param data
     */
    void setBuyOrSellState(CommonJsonToBean<String> data);

    /**
     * 收藏
     */
    void collectMarketState(CommonJsonToBean<String> data);

    /**
     * 取消收藏
     */
    void cancelCollectMarket(CommonJsonToBean<String> data);

    /**
     * 订阅k线数据
     */
    void setTopicKlineData(List<TradingInfoBean.KlineListBean> data);
}
