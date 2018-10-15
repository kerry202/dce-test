package cn.dagongniu.oax.kline.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

    public class TradingInfoBean implements Serializable {

    /**
     * allMaketList : [{"categoryName":"ETH"},{"categoryName":"BTC","marketList":[{"cnyPrice":40000,"coinId":1,"coinName":"ETH","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":4,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":4,"totalAmount":0,"tradeQty":0},{"cnyPrice":40000,"coinId":3,"coinName":"X","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":5,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":5,"totalAmount":0,"tradeQty":0},{"cnyPrice":40000,"coinId":4,"coinName":"UNC","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":6,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":5,"totalAmount":0,"tradeQty":0},{"cnyPrice":40000,"coinId":5,"coinName":"CEC","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":7,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":5,"totalAmount":0,"tradeQty":0}]}]
     * articleList : [{"id":3,"name":"cn标题","releaseTime":"2018-06-08 09:18:53","type":2},{"id":8,"name":"中文标题","releaseTime":"2018-06-08 09:18:53","type":2},{"id":9,"name":"cn标题","releaseTime":"2018-06-08 09:18:53","type":2},{"id":19,"name":"xxx1","releaseTime":"2018-06-10 19:59:48","type":2}]
     * feeRate : 0.1
     * klineList : [{"close":0.07388,"createTime":"2018-06-23 05:04:00","high":0.086,"id":3,"lasteTradeId":625,"low":0.066,"marketId":1,"minType":60,"open":0.058,"qty":500},{"close":0.07,"createTime":"2018-06-23 06:16:00","high":0.2,"id":75,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:17:00","high":0.2,"id":76,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:18:00","high":0.2,"id":77,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:19:00","high":0.2,"id":78,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:20:00","high":0.2,"id":79,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:21:00","high":0.2,"id":80,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:22:00","high":0.2,"id":81,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:23:00","high":0.2,"id":82,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:24:00","high":0.2,"id":83,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:25:00","high":0.2,"id":84,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:26:00","high":0.2,"id":85,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:27:00","high":0.2,"id":86,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:28:00","high":0.2,"id":87,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:29:00","high":0.2,"id":88,"lasteTradeId":465,"lo     w":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:30:00","high":0.2,"id":89,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:31:00","high":0.2,"id":90,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:32:00","high":0.2,"id":91,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:33:00","high":0.2,"id":92,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:34:00","high":0.2,"id":93,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:35:00","high":0.2,"id":94,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:36:00","high":0.2,"id":95,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:37:00","high":0.2,"id":96,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:38:00","high":0.2,"id":97,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:39:00","high":0.2,"id":98,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:40:00","high":0.2,"id":99,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:41:00","high":0.2,"id":100,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:42:00","high":0.2,"id":101,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:43:00","high":0.2,"id":102,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:44:00","high":0.2,"id":103,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:45:00","high":0.2,"id":104,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:46:00","high":0.2,"id":105,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:47:00","high":0.2,"id":106,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:48:00","high":0.2,"id":107,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:49:00","high":0.2,"id":108,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:50:00","high":0.2,"id":109,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:51:00","high":0.2,"id":110,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:52:00","high":0.2,"id":111,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:53:00","high":0.2,"id":112,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:54:00","high":0.2,"id":113,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:55:00","high":0.2,"id":114,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:56:00","high":0.2,"id":115,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:57:00","high":0.2,"id":116,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:58:00","high":0.2,"id":117,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 06:59:00","high":0.2,"id":118,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:00:00","high":0.2,"id":119,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:01:00","high":0.2,"id":120,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:02:00","high":0.2,"id":121,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:03:00","high":0.2,"id":122,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:04:00","high":0.2,"id":123,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:05:00","high":0.2,"id":124,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:06:00","high":0.2,"id":125,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:07:00","high":0.2,"id":126,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:08:00","high":0.2,"id":127,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:09:00","high":0.2,"id":128,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:10:00","high":0.2,"id":129,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:11:00","high":0.2,"id":130,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:12:00","high":0.2,"id":131,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:13:00","high":0.2,"id":132,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:14:00","high":0.2,"id":133,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:15:00","high":0.2,"id":134,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300},{"close":0.07,"createTime":"2018-06-23 07:16:00","high":0.2,"id":135,"lasteTradeId":465,"low":0.11,"marketId":1,"minType":60,"open":0.13,"qty":300}]
     * marketOrdersMap : {"buyList":[{"amount":1,"price":0.005,"qty":200}],"sellList":[]}
     * marketTradeList : [{"id":2,"price":0.005,"qty":100,"type":1},{"id":1,"price":0.005,"qty":100,"type":2}]
     */

    private String feeRate;
    private MarketOrdersMapBean marketOrdersMap; //实时委托
    private List<AllMaketListBean> allMaketList;
    private List<ArticleListBean> articleList;//公告数据体
    private List<KlineListBean> klineList;
    private List<MarketTradeListBean> marketTradeList;

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public MarketOrdersMapBean getMarketOrdersMap() {
        return marketOrdersMap;
    }

    public void setMarketOrdersMap(MarketOrdersMapBean marketOrdersMap) {
        this.marketOrdersMap = marketOrdersMap;
    }

    public List<AllMaketListBean> getAllMaketList() {
        return allMaketList;
    }

    public void setAllMaketList(List<AllMaketListBean> allMaketList) {
        this.allMaketList = allMaketList;
    }

    public List<ArticleListBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleListBean> articleList) {
        this.articleList = articleList;
    }

    public List<KlineListBean> getKlineList() {
        return klineList;
    }

    public void setKlineList(List<KlineListBean> klineList) {
        this.klineList = klineList;
    }

    public List<MarketTradeListBean> getMarketTradeList() {
        return marketTradeList;
    }

    public void setMarketTradeList(List<MarketTradeListBean> marketTradeList) {
        this.marketTradeList = marketTradeList;
    }

        @Override
        public String toString() {
            return "TradingInfoBean{" +
                    "feeRate='" + feeRate + '\'' +
                    ", marketOrdersMap=" + marketOrdersMap +
                    ", allMaketList=" + allMaketList +
                    ", articleList=" + articleList +
                    ", klineList=" + klineList +
                    ", marketTradeList=" + marketTradeList +
                    '}';
        }

        public static class MarketOrdersMapBean {
        private List<BuyOrSellListBean> buyList;
        private List<BuyOrSellListBean> sellList;

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

        public static class BuyOrSellListBean {
            /**
             * amount : 1.0
             * price : 0.005
             * qty : 200.0
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

            @Override
            public String toString() {
                return "BuyOrSellListBean{" +
                        "amount=" + amount +
                        ", price=" + price +
                        ", qty=" + qty +
                        '}';
            }
        }
    }

    public static class AllMaketListBean {
        /**
         * categoryName : ETH
         * marketList : [{"cnyPrice":40000,"coinId":1,"coinName":"ETH","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":4,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":4,"totalAmount":0,"tradeQty":0},{"cnyPrice":40000,"coinId":3,"coinName":"X","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":5,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":5,"totalAmount":0,"tradeQty":0},{"cnyPrice":40000,"coinId":4,"coinName":"UNC","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":6,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":5,"totalAmount":0,"tradeQty":0},{"cnyPrice":40000,"coinId":5,"coinName":"CEC","incRate":0,"lastTradePrice":0,"marketCoinId":2,"marketCoinName":"BTC","marketId":7,"maxPrice":0,"minPrice":0,"priceDecimals":8,"qtyDecimals":5,"totalAmount":0,"tradeQty":0}]
         */

        private String categoryName;
        private List<MarketListBean> marketList;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<MarketListBean> getMarketList() {
            return marketList;
        }

        public void setMarketList(List<MarketListBean> marketList) {
            this.marketList = marketList;
        }

        @Override
        public String toString() {
            return "AllMaketListBean{" +
                    "categoryName='" + categoryName + '\'' +
                    ", marketList=" + marketList +
                    '}';
        }

        public static class MarketListBean {
            /**
             * cnyPrice : 40000.0
             * coinId : 1
             * coinName : ETH
             * incRate : 0.0
             * lastTradePrice : 0.0
             * marketCoinId : 2
             * marketCoinName : BTC
             * marketId : 4
             * maxPrice : 0.0
             * minPrice : 0.0
             * priceDecimals : 8
             * qtyDecimals : 4
             * totalAmount : 0.0
             * tradeQty : 0.0
             */

            private BigDecimal cnyPrice;
            private int coinId;
            private String coinName;
            private String incRate;
            private String lastTradePrice;
            private int marketCoinId;
            private String marketCoinName;
            private int marketId;
            private BigDecimal maxPrice;
            private BigDecimal minPrice;
            private int priceDecimals;
            private int qtyDecimals;
            private BigDecimal totalAmount;
            private BigDecimal tradeQty;

            public BigDecimal getCnyPrice() {
                return cnyPrice;
            }

            public void setCnyPrice(BigDecimal cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

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

            public int getMarketId() {
                return marketId;
            }

            public void setMarketId(int marketId) {
                this.marketId = marketId;
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

            public BigDecimal getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(BigDecimal totalAmount) {
                this.totalAmount = totalAmount;
            }

            public BigDecimal getTradeQty() {
                return tradeQty;
            }

            public void setTradeQty(BigDecimal tradeQty) {
                this.tradeQty = tradeQty;
            }

            @Override
            public String toString() {
                return "MarketListBean{" +
                        "cnyPrice=" + cnyPrice +
                        ", coinId=" + coinId +
                        ", coinName='" + coinName + '\'' +
                        ", incRate='" + incRate + '\'' +
                        ", lastTradePrice='" + lastTradePrice + '\'' +
                        ", marketCoinId=" + marketCoinId +
                        ", marketCoinName='" + marketCoinName + '\'' +
                        ", marketId=" + marketId +
                        ", maxPrice=" + maxPrice +
                        ", minPrice=" + minPrice +
                        ", priceDecimals=" + priceDecimals +
                        ", qtyDecimals=" + qtyDecimals +
                        ", totalAmount=" + totalAmount +
                        ", tradeQty=" + tradeQty +
                        '}';
            }
        }
    }

    public static class ArticleListBean {
        /**
         * id : 3
         * name : cn标题
         * releaseTime : 2018-06-08 09:18:53
         * type : 2
         */

        private int id;
        private String name;
        private String releaseTime;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "ArticleListBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", releaseTime='" + releaseTime + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    public static class KlineListBean {
        /**
         * close : 0.07388
         * createTime : 2018-06-23 05:04:00
         * high : 0.086
         * id : 3
         * lasteTradeId : 625
         * low : 0.066
         * marketId : 1
         * minType : 60
         * open : 0.058
         * qty : 500.0
         * lo     w : 0.11
         */

        private BigDecimal close;
        private String createTime;
        private BigDecimal high;
        private int id;
        private int lasteTradeId;
        private BigDecimal low;
        private int marketId;
        private int minType;
        private BigDecimal open;
        private BigDecimal qty;

        public BigDecimal getClose() {
            return close;
        }

        public void setClose(BigDecimal close) {
            this.close = close;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public BigDecimal getHigh() {
            return high;
        }

        public void setHigh(BigDecimal high) {
            this.high = high;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLasteTradeId() {
            return lasteTradeId;
        }

        public void setLasteTradeId(int lasteTradeId) {
            this.lasteTradeId = lasteTradeId;
        }

        public BigDecimal getLow() {
            return low;
        }

        public void setLow(BigDecimal low) {
            this.low = low;
        }

        public int getMarketId() {
            return marketId;
        }

        public void setMarketId(int marketId) {
            this.marketId = marketId;
        }

        public int getMinType() {
            return minType;
        }

        public void setMinType(int minType) {
            this.minType = minType;
        }

        public BigDecimal getOpen() {
            return open;
        }

        public void setOpen(BigDecimal open) {
            this.open = open;
        }

        public BigDecimal getQty() {
            return qty;
        }

        public void setQty(BigDecimal qty) {
            this.qty = qty;
        }

        @Override
        public String toString() {
            return "KlineListBean{" +
                    "close=" + close +
                    ", createTime='" + createTime + '\'' +
                    ", high=" + high +
                    ", id=" + id +
                    ", lasteTradeId=" + lasteTradeId +
                    ", low=" + low +
                    ", marketId=" + marketId +
                    ", minType=" + minType +
                    ", open=" + open +
                    ", qty=" + qty +
                    '}';
        }

    }

    public static class MarketTradeListBean {
        /**
         * id : 2
         * price : 0.005
         * qty : 100.0
         * type : 1
         */

        private int id;
        private double price;
        private double qty;
        private int type;
        private String createTime;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getQty() {
            return qty;
        }

        public void setQty(double qty) {
            this.qty = qty;
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
                    ", price=" + price +
                    ", qty=" + qty +
                    ", type=" + type +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }
    }
}
