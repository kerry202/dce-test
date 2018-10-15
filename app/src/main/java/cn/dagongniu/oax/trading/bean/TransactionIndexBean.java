package cn.dagongniu.oax.trading.bean;

import java.math.BigDecimal;
import java.util.List;

public class TransactionIndexBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"marketOrdersMap":{"buyList":[{"amount":0.8,"price":0.004,"qty":200}],"sellList":[{"amount":6,"price":0.02,"qty":300}]},"articleList":[{"id":3,"name":"cn标题","type":2,"releaseTime":"2018-06-08 09:18:53"},{"id":8,"name":"中文标题","type":2,"releaseTime":"2018-06-08 09:18:53"},{"id":9,"name":"cn标题","type":2,"releaseTime":"2018-06-08 09:18:53"},{"id":19,"name":"xxx1","type":2,"releaseTime":"2018-06-10 19:59:48"}],"userMaketList":null,"marketTradeList":[],"allMaketList":[{"categoryName":"ETH","marketList":null},{"categoryName":"BTC","marketList":[{"coinId":1,"coinName":"ETH","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":4,"totalAmount":0,"priceDecimals":8,"qtyDecimals":4,"cnyPrice":40000},{"coinId":3,"coinName":"X","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":5,"totalAmount":0,"priceDecimals":8,"qtyDecimals":5,"cnyPrice":40000},{"coinId":4,"coinName":"UNC","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":6,"totalAmount":0,"priceDecimals":8,"qtyDecimals":5,"cnyPrice":40000},{"coinId":5,"coinName":"CEC","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":7,"totalAmount":0,"priceDecimals":8,"qtyDecimals":5,"cnyPrice":40000}]}],"klineList":[],"feeRate":"0.1"}
     */

    private String code;
    private boolean success;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * marketOrdersMap : {"buyList":[{"amount":0.8,"price":0.004,"qty":200}],"sellList":[{"amount":6,"price":0.02,"qty":300}]}
         * articleList : [{"id":3,"name":"cn标题","type":2,"releaseTime":"2018-06-08 09:18:53"},{"id":8,"name":"中文标题","type":2,"releaseTime":"2018-06-08 09:18:53"},{"id":9,"name":"cn标题","type":2,"releaseTime":"2018-06-08 09:18:53"},{"id":19,"name":"xxx1","type":2,"releaseTime":"2018-06-10 19:59:48"}]
         * userMaketList : null
         * marketTradeList : []
         * allMaketList : [{"categoryName":"ETH","marketList":null},{"categoryName":"BTC","marketList":[{"coinId":1,"coinName":"ETH","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":4,"totalAmount":0,"priceDecimals":8,"qtyDecimals":4,"cnyPrice":40000},{"coinId":3,"coinName":"X","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":5,"totalAmount":0,"priceDecimals":8,"qtyDecimals":5,"cnyPrice":40000},{"coinId":4,"coinName":"UNC","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":6,"totalAmount":0,"priceDecimals":8,"qtyDecimals":5,"cnyPrice":40000},{"coinId":5,"coinName":"CEC","marketCoinId":2,"marketCoinName":"BTC","tradeQty":0,"maxPrice":0,"minPrice":0,"incRate":0,"lastTradePrice":0,"marketId":7,"totalAmount":0,"priceDecimals":8,"qtyDecimals":5,"cnyPrice":40000}]}]
         * klineList : []
         * feeRate : 0.1
         */

        private MarketOrdersMapBean marketOrdersMap;
        private Object userMaketList;
        private String feeRate;
        private List<ArticleListBean> articleList;
        private List<?> marketTradeList;
        private List<AllMaketListBean> allMaketList;
        private List<?> klineList;

        public MarketOrdersMapBean getMarketOrdersMap() {
            return marketOrdersMap;
        }

        public void setMarketOrdersMap(MarketOrdersMapBean marketOrdersMap) {
            this.marketOrdersMap = marketOrdersMap;
        }

        public Object getUserMaketList() {
            return userMaketList;
        }

        public void setUserMaketList(Object userMaketList) {
            this.userMaketList = userMaketList;
        }

        public String getFeeRate() {
            return feeRate;
        }

        public void setFeeRate(String feeRate) {
            this.feeRate = feeRate;
        }

        public List<ArticleListBean> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<ArticleListBean> articleList) {
            this.articleList = articleList;
        }

        public List<?> getMarketTradeList() {
            return marketTradeList;
        }

        public void setMarketTradeList(List<?> marketTradeList) {
            this.marketTradeList = marketTradeList;
        }

        public List<AllMaketListBean> getAllMaketList() {
            return allMaketList;
        }

        public void setAllMaketList(List<AllMaketListBean> allMaketList) {
            this.allMaketList = allMaketList;
        }

        public List<?> getKlineList() {
            return klineList;
        }

        public void setKlineList(List<?> klineList) {
            this.klineList = klineList;
        }

        public static class MarketOrdersMapBean {
            private List<BuyListBean> buyList;
            private List<SellListBean> sellList;

            public List<BuyListBean> getBuyList() {
                return buyList;
            }

            public void setBuyList(List<BuyListBean> buyList) {
                this.buyList = buyList;
            }

            public List<SellListBean> getSellList() {
                return sellList;
            }

            public void setSellList(List<SellListBean> sellList) {
                this.sellList = sellList;
            }

            public static class BuyListBean {
                /**
                 * amount : 0.8
                 * price : 0.004
                 * qty : 200
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

            public static class SellListBean {
                /**
                 * amount : 6
                 * price : 0.02
                 * qty : 300
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
        }

        public static class ArticleListBean {
            /**
             * id : 3
             * name : cn标题
             * type : 2
             * releaseTime : 2018-06-08 09:18:53
             */

            private int id;
            private String name;
            private int type;
            private String releaseTime;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }
        }

        public static class AllMaketListBean {
            /**
             * categoryName : ETH
             * marketList : null
             */

            private String categoryName;
            private Object marketList;

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public Object getMarketList() {
                return marketList;
            }

            public void setMarketList(Object marketList) {
                this.marketList = marketList;
            }
        }
    }
}
