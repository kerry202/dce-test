package cn.dagongniu.oax.trading.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

public class EntrustInfoBean {
    /**
     * coinBalance : {"rightCoinBalance":1000.999,"leftCoinBalance":0}
     * orderList : [{"marketName":"X/ETH","createTime":"2018/06/22","price":0.005,"qty":100,"tradeQty":0,"id":9,"type":2},{"marketName":"X/ETH","createTime":"2018/06/22","price":0.005,"qty":100,"tradeQty":0,"id":10,"type":2},{"marketName":"X/ETH","createTime":"2018/06/22","price":0.005,"qty":100,"tradeQty":0,"id":8,"type":2},{"marketName":"X/ETH","createTime":"2018/06/22","price":0.005,"qty":100,"tradeQty":0,"id":6,"type":2},{"marketName":"X/ETH","createTime":"2018/06/22","price":0.005,"qty":100,"tradeQty":0,"id":7,"type":2},{"marketName":"X/ETH","createTime":"2018/06/22","price":0.005,"qty":100,"tradeQty":0,"id":5,"type":2}]
     * marketTradeList : [{"id":3,"qty":100,"price":0.005,"type":2},{"id":1,"qty":100,"price":0.006,"type":2}]
     */

    private CoinBalanceBean coinBalance;//用户市场资产信息
    private List<OrderListBean> orderList;//用户市场委托单
    private List<MarketTradeListBean> marketTradeList;//用户市场成交单

    public CoinBalanceBean getCoinBalance() {
        return coinBalance;
    }

    public void setCoinBalance(CoinBalanceBean coinBalance) {
        this.coinBalance = coinBalance;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public List<MarketTradeListBean> getMarketTradeList() {
        return marketTradeList;
    }

    public void setMarketTradeList(List<MarketTradeListBean> marketTradeList) {
        this.marketTradeList = marketTradeList;
    }

    @Override
    public String toString() {
        return "EntrustInfoBean{" +
                "coinBalance=" + coinBalance +
                ", orderList=" + orderList +
                ", marketTradeList=" + marketTradeList +
                '}';
    }

    public static class CoinBalanceBean {
        /**
         * rightCoinBalance : 1000.999
         * leftCoinBalance : 0
         */

        private BigDecimal rightCoinBalance;//右币余额
        private BigDecimal leftCoinBalance;//左币余额

        public BigDecimal getRightCoinBalance() {
            return rightCoinBalance;
        }

        public void setRightCoinBalance(BigDecimal rightCoinBalance) {
            this.rightCoinBalance = rightCoinBalance;
        }

        public BigDecimal getLeftCoinBalance() {
            return leftCoinBalance;
        }

        public void setLeftCoinBalance(BigDecimal leftCoinBalance) {
            this.leftCoinBalance = leftCoinBalance;
        }

        @Override
        public String toString() {
            return "CoinBalanceBean{" +
                    "rightCoinBalance=" + rightCoinBalance +
                    ", leftCoinBalance=" + leftCoinBalance +
                    '}';
        }
    }

    public static class OrderListBean {
        /**
         * marketName : X/ETH
         * createTime : 2018/06/22
         * price : 0.005
         * qty : 100
         * tradeQty : 0
         * id : 9
         * type : 2
         */

        private String marketName;
        private String createTime;
        private BigDecimal price;
        private BigDecimal qty;
        private BigDecimal tradeQty;
        private int id;//订单id
        private int type;//类型 1买入 2卖出

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public BigDecimal getTradeQty() {
            return tradeQty;
        }

        public void setTradeQty(BigDecimal tradeQty) {
            this.tradeQty = tradeQty;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "OrderListBean{" +
                    "marketName='" + marketName + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", price=" + price +
                    ", qty=" + qty +
                    ", tradeQty=" + tradeQty +
                    ", id=" + id +
                    ", type=" + type +
                    '}';
        }
    }

    public static class MarketTradeListBean {
        /**
         * id : 3
         * qty : 100
         * price : 0.005
         * type : 2
         */

        private int id;//	成交单id
        private BigDecimal qty;
        private BigDecimal price;
        private int type;//	类型 1买入2卖出

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public BigDecimal getQty() {
            return qty;
        }

        public void setQty(BigDecimal qty) {
            this.qty = qty;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "MarketTradeListBean{" +
                    "id=" + id +
                    ", qty=" + qty +
                    ", price=" + price +
                    ", type=" + type +
                    '}';
        }
    }
}
