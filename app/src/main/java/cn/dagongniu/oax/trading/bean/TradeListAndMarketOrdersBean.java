package cn.dagongniu.oax.trading.bean;

import java.math.BigDecimal;
import java.util.List;

public class TradeListAndMarketOrdersBean {

    private List<BuyOrSellListBean> buyList;
    private List<BuyOrSellListBean> sellList;
    private List<TradeListBean> tradeList;

    public List<BuyOrSellListBean> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<BuyOrSellListBean> buyList) {
        this.buyList = buyList;
    }

    public List<BuyOrSellListBean> getSellList() {
        return sellList;
    }

    public void setSellList(List<BuyOrSellListBean> sellList) {
        this.sellList = sellList;
    }

    public List<TradeListBean> getTradeList() {
        return tradeList;
    }

    public void setTradeList(List<TradeListBean> tradeList) {
        this.tradeList = tradeList;
    }

    public static class BuyOrSellListBean {
        /**
         * amount : 70
         * price : 0.1
         * qty : 700
         */

        private BigDecimal amount;
        private BigDecimal price;
        private BigDecimal qty;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getQty() {
            return qty;
        }

        public void setQty(BigDecimal qty) {
            this.qty = qty;
        }
    }

    public static class TradeListBean {
        /**
         * createTime : 20:41:56
         * id : 12
         * price : 0.1
         * qty : 100
         * type : 1  类型 1买入 2 卖出
         */

        private String createTime;
        private int id;
        private BigDecimal price;
        private BigDecimal qty;
        private int type;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getQty() {
            return qty;
        }

        public void setQty(BigDecimal qty) {
            this.qty = qty;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
