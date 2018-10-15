package cn.dagongniu.oax.main.view;

import cn.dagongniu.oax.base.OAXIView;
import cn.dagongniu.oax.https.CommonJsonToBean;

public interface MarketIView  extends OAXIView{
    /**
     * 收藏
     */
    void collectMarketState(CommonJsonToBean<String> data);

    /**
     * 取消收藏
     */
    void cancelCollectMarket(CommonJsonToBean<String> data);


}
