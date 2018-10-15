package cn.dagongniu.oax.main.bean;

import java.math.BigDecimal;
import java.util.List;

public class OaxMarketBean {



        /**
         * coinId : 4
         * coinName : VITE
         * marketCoinId : 9
         * marketCoinName : ETH
         * tradeQty : 0.0
         * maxPrice : 0.0
         * minPrice : 0.0
         * incRate : 0.0
         * lastTradePrice : 0.0
         * marketId : 4
         * totalAmount : 0.0
         * priceDecimals : 8
         * qtyDecimals : 3
         * cnyPrice : 500.0
         */

        private int coinId;
        private String coinName;
        private int marketCoinId;
        private String marketCoinName;
        private BigDecimal tradeQty;
        private BigDecimal maxPrice;
        private BigDecimal minPrice;
        private String incRate;
        private String lastTradePrice;
        private int marketId;
        private BigDecimal totalAmount;
        private int priceDecimals;
        private int qtyDecimals;
        private BigDecimal cnyPrice;

        public int getCoinId() {
            return coinId;
        }

        public void setCoinId(int coinId) {
            this.coinId = coinId;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public int getMarketCoinId() {
            return marketCoinId;
        }

        public void setMarketCoinId(int marketCoinId) {
            this.marketCoinId = marketCoinId;
        }

        public String getMarketCoinName() {
            return marketCoinName;
        }

        public void setMarketCoinName(String marketCoinName) {
            this.marketCoinName = marketCoinName;
        }

        public BigDecimal getTradeQty() {
            return tradeQty;
        }

        public void setTradeQty(BigDecimal tradeQty) {
            this.tradeQty = tradeQty;
        }

        public BigDecimal getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
        }

        public BigDecimal getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
        }

        public String getIncRate() {
            return incRate;
        }

        public void setIncRate(String incRate) {
            this.incRate = incRate;
        }

        public String getLastTradePrice() {
            return lastTradePrice;
        }

        public void setLastTradePrice(String lastTradePrice) {
            this.lastTradePrice = lastTradePrice;
        }

        public int getMarketId() {
            return marketId;
        }

        public void setMarketId(int marketId) {
            this.marketId = marketId;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getPriceDecimals() {
            return priceDecimals;
        }

        public void setPriceDecimals(int priceDecimals) {
            this.priceDecimals = priceDecimals;
        }

        public int getQtyDecimals() {
            return qtyDecimals;
        }

        public void setQtyDecimals(int qtyDecimals) {
            this.qtyDecimals = qtyDecimals;
        }

        public BigDecimal getCnyPrice() {
            return cnyPrice;
        }

        public void setCnyPrice(BigDecimal cnyPrice) {
            this.cnyPrice = cnyPrice;
        }




}
