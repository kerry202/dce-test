package cn.dagongniu.oax.utils;

import java.util.ArrayList;
import java.util.List;

import cn.dagongniu.oax.assets.bean.PropertyShowBean;
import cn.dagongniu.oax.main.bean.AssetsDetesilTradeListBean;
import cn.dagongniu.oax.main.bean.IndexPageBean;
import cn.dagongniu.oax.main.bean.OaxMarketBean;

/**
 * 类转化
 */
public class ClassConversionUtils {

    /**
     * 推荐交易对的实体转化
     *
     * @param recommendMarketListBeans 当前的交易对数据
     * @param listOaxMarketBeanGson    所有交易对的数据
     * @return
     */

    public static List<IndexPageBean.DataBean.RecommendMarketListBean>
    toRecommendMarketListBean(List<IndexPageBean.DataBean.RecommendMarketListBean> recommendMarketListBeans,
                              List<OaxMarketBean> listOaxMarketBeanGson) {


        //websocket 需要传适配器的值
        List<IndexPageBean.DataBean.RecommendMarketListBean> websocketRecommendMarketListBeans = new ArrayList<>();
        if (recommendMarketListBeans == null) {
            return null;
        }
        for (int recommendMarketi = 0; recommendMarketi < recommendMarketListBeans.size(); recommendMarketi++) {
            int marketId = recommendMarketListBeans.get(recommendMarketi).getMarketCoin().getMarketId();
            for (int webRecommendi = 0; webRecommendi < listOaxMarketBeanGson.size(); webRecommendi++) {
                int sumMarId = listOaxMarketBeanGson.get(webRecommendi).getMarketId();
                if (marketId == sumMarId) {
                    IndexPageBean.DataBean.RecommendMarketListBean recommendMarketListItem =
                            new IndexPageBean.DataBean.RecommendMarketListBean();
                    IndexPageBean.DataBean.RecommendMarketListBean.MarketCoinBean marketCoinBean =
                            new IndexPageBean.DataBean.RecommendMarketListBean.MarketCoinBean();

                    marketCoinBean.setCoinId(listOaxMarketBeanGson.get(webRecommendi).getCoinId());
                    marketCoinBean.setCoinName(listOaxMarketBeanGson.get(webRecommendi).getCoinName());
                    marketCoinBean.setMarketCoinId(listOaxMarketBeanGson.get(webRecommendi).getMarketCoinId());
                    marketCoinBean.setMarketCoinName(listOaxMarketBeanGson.get(webRecommendi).getMarketCoinName());
                    marketCoinBean.setTradeQty(listOaxMarketBeanGson.get(webRecommendi).getTradeQty());
                    marketCoinBean.setMaxPrice(listOaxMarketBeanGson.get(webRecommendi).getMaxPrice());
                    marketCoinBean.setMinPrice(listOaxMarketBeanGson.get(webRecommendi).getMinPrice());
                    marketCoinBean.setIncRate(listOaxMarketBeanGson.get(webRecommendi).getIncRate());
                    marketCoinBean.setLastTradePrice(listOaxMarketBeanGson.get(webRecommendi).getLastTradePrice());
                    marketCoinBean.setMarketId(listOaxMarketBeanGson.get(webRecommendi).getMarketId());
                    marketCoinBean.setTotalAmount(listOaxMarketBeanGson.get(webRecommendi).getTotalAmount());
                    marketCoinBean.setPriceDecimals(listOaxMarketBeanGson.get(webRecommendi).getPriceDecimals());
                    marketCoinBean.setQtyDecimals(listOaxMarketBeanGson.get(webRecommendi).getQtyDecimals());
                    marketCoinBean.setCnyPrice(listOaxMarketBeanGson.get(webRecommendi).getCnyPrice());
                    recommendMarketListItem.setMarketCoin(marketCoinBean);

                    websocketRecommendMarketListBeans.add(recommendMarketListItem);
                }
            }
        }

        return websocketRecommendMarketListBeans;
    }


    /**
     * 所有市场交易对的实体转化
     *
     * @param allMarketListBeans    当前所有市场的交易对数据
     * @param listOaxMarketBeanGson 所有交易对的数据
     * @return
     */

    public static List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean>
    toAllMarketListBean(List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> allMarketListBeans,
                        List<OaxMarketBean> listOaxMarketBeanGson) {

        //websocket 需要传适配器的值
        List<IndexPageBean.DataBean.AllMaketListBean.MarketListBean> websocketRecommendMarketListBeans = new ArrayList<>();

        for (int recommendMarketi = 0; recommendMarketi < allMarketListBeans.size(); recommendMarketi++) {
            int marketId = allMarketListBeans.get(recommendMarketi).getMarketId();
            for (int webRecommendi = 0; webRecommendi < listOaxMarketBeanGson.size(); webRecommendi++) {
                int sumMarId = listOaxMarketBeanGson.get(webRecommendi).getMarketId();
                if (marketId == sumMarId) {

                    IndexPageBean.DataBean.AllMaketListBean.MarketListBean allmarketCoinBean =
                            new IndexPageBean.DataBean.AllMaketListBean.MarketListBean();

                    allmarketCoinBean.setCoinId(listOaxMarketBeanGson.get(webRecommendi).getCoinId());
                    allmarketCoinBean.setCoinName(listOaxMarketBeanGson.get(webRecommendi).getCoinName());
                    allmarketCoinBean.setMarketCoinId(listOaxMarketBeanGson.get(webRecommendi).getMarketCoinId());
                    allmarketCoinBean.setMarketCoinName(listOaxMarketBeanGson.get(webRecommendi).getMarketCoinName());
                    allmarketCoinBean.setTradeQty(listOaxMarketBeanGson.get(webRecommendi).getTradeQty());
                    allmarketCoinBean.setMaxPrice(listOaxMarketBeanGson.get(webRecommendi).getMaxPrice());
                    allmarketCoinBean.setMinPrice(listOaxMarketBeanGson.get(webRecommendi).getMinPrice());
                    allmarketCoinBean.setIncRate(listOaxMarketBeanGson.get(webRecommendi).getIncRate());
                    allmarketCoinBean.setLastTradePrice(listOaxMarketBeanGson.get(webRecommendi).getLastTradePrice());
                    allmarketCoinBean.setMarketId(listOaxMarketBeanGson.get(webRecommendi).getMarketId());
                    allmarketCoinBean.setTotalAmount(listOaxMarketBeanGson.get(webRecommendi).getTotalAmount());
                    allmarketCoinBean.setPriceDecimals(listOaxMarketBeanGson.get(webRecommendi).getPriceDecimals());
                    allmarketCoinBean.setQtyDecimals(listOaxMarketBeanGson.get(webRecommendi).getQtyDecimals());
                    allmarketCoinBean.setCnyPrice(listOaxMarketBeanGson.get(webRecommendi).getCnyPrice());

                    websocketRecommendMarketListBeans.add(allmarketCoinBean);
                }
            }
        }
        return websocketRecommendMarketListBeans;
    }

    /**
     * 所有市场交易对的实体转化
     *
     * @param allMarketListBeans    当前所有市场的交易对数据
     * @param listOaxMarketBeanGson 所有交易对的数据
     * @return
     */

    public static List<IndexPageBean.DataBean.UserMaketListBean>
    toUserMarketListBean(List<IndexPageBean.DataBean.UserMaketListBean> allMarketListBeans,
                         List<OaxMarketBean> listOaxMarketBeanGson) {

        //websocket 需要传适配器的值
        List<IndexPageBean.DataBean.UserMaketListBean> websocketRecommendMarketListBeans = new ArrayList<>();

        for (int recommendMarketi = 0; recommendMarketi < allMarketListBeans.size(); recommendMarketi++) {
            int marketId = allMarketListBeans.get(recommendMarketi).getMarketId();

            for (int webRecommendi = 0; webRecommendi < listOaxMarketBeanGson.size(); webRecommendi++) {
                int sumMarId = listOaxMarketBeanGson.get(webRecommendi).getMarketId();
                if (marketId == sumMarId) {

                    IndexPageBean.DataBean.UserMaketListBean allmarketCoinBean =
                            new IndexPageBean.DataBean.UserMaketListBean();

                    allmarketCoinBean.setCoinId(listOaxMarketBeanGson.get(webRecommendi).getCoinId());
                    allmarketCoinBean.setCoinName(listOaxMarketBeanGson.get(webRecommendi).getCoinName());
                    allmarketCoinBean.setMarketCoinId(listOaxMarketBeanGson.get(webRecommendi).getMarketCoinId());
                    allmarketCoinBean.setMarketCoinName(listOaxMarketBeanGson.get(webRecommendi).getMarketCoinName());
                    allmarketCoinBean.setTradeQty(listOaxMarketBeanGson.get(webRecommendi).getTradeQty());
                    allmarketCoinBean.setMaxPrice(listOaxMarketBeanGson.get(webRecommendi).getMaxPrice());
                    allmarketCoinBean.setMinPrice(listOaxMarketBeanGson.get(webRecommendi).getMinPrice());
                    allmarketCoinBean.setIncRate(listOaxMarketBeanGson.get(webRecommendi).getIncRate());
                    allmarketCoinBean.setLastTradePrice(listOaxMarketBeanGson.get(webRecommendi).getLastTradePrice());
                    allmarketCoinBean.setMarketId(listOaxMarketBeanGson.get(webRecommendi).getMarketId());
                    allmarketCoinBean.setTotalAmount(listOaxMarketBeanGson.get(webRecommendi).getTotalAmount());
                    allmarketCoinBean.setPriceDecimals(listOaxMarketBeanGson.get(webRecommendi).getPriceDecimals());
                    allmarketCoinBean.setQtyDecimals(listOaxMarketBeanGson.get(webRecommendi).getQtyDecimals());
                    allmarketCoinBean.setCnyPrice(listOaxMarketBeanGson.get(webRecommendi).getCnyPrice());

                    websocketRecommendMarketListBeans.add(allmarketCoinBean);
                }
            }
        }
        return websocketRecommendMarketListBeans;
    }

    /**
     * @param listBeans
     * @return
     */
    public static List<PropertyShowBean.DataBean.TradeListBean> toTradeListBean(List<AssetsDetesilTradeListBean> listBeans) {
        List<PropertyShowBean.DataBean.TradeListBean> listBeans1 = new ArrayList<>();

        for (int i = 0; i < listBeans.size(); i++) {

            PropertyShowBean.DataBean.TradeListBean tradeListBean = new PropertyShowBean.DataBean.TradeListBean();
            tradeListBean.setId(listBeans.get(i).getId());
            tradeListBean.setCnyPrice(listBeans.get(i).getCnyPrice());
            tradeListBean.setName(listBeans.get(i).getName());
            tradeListBean.setNewPrice(listBeans.get(i).getNewPrice());
            tradeListBean.setRate(listBeans.get(i).getRate());

            listBeans1.add(tradeListBean);
        }
        return listBeans1;
    }


}
