package cn.dagongniu.oax.trading.bean;

import java.util.List;

public class MarketCategoryBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : [{"categoryName":"ETH","marketCategoryList":[{"marketId":2,"marketMame":"UNC/ETH","categoryName":"ETH"},{"marketId":3,"marketMame":"CEC/ETH","categoryName":"ETH"},{"marketId":17,"marketMame":"CER/ETH","categoryName":"ETH"},{"marketId":18,"marketMame":"FGD/ETH","categoryName":"ETH"},{"marketId":19,"marketMame":"GHFH/ETH","categoryName":"ETH"},{"marketId":20,"marketMame":"FGHE/ETH","categoryName":"ETH"},{"marketId":21,"marketMame":"TUI/ETH","categoryName":"ETH"},{"marketId":22,"marketMame":"LKY/ETH","categoryName":"ETH"},{"marketId":23,"marketMame":"WSERT/ETH","categoryName":"ETH"},{"marketId":5,"marketMame":"X/ETH","categoryName":"ETH"},{"marketId":12,"marketMame":"CEC/ETH","categoryName":"ETH"},{"marketId":13,"marketMame":"WERR/ETH","categoryName":"ETH"},{"marketId":14,"marketMame":"ERE/ETH","categoryName":"ETH"},{"marketId":15,"marketMame":"ADS/ETH","categoryName":"ETH"},{"marketId":16,"marketMame":"BFG/ETH","categoryName":"ETH"}]},{"categoryName":"BTC","marketCategoryList":[{"marketId":1,"marketMame":"ETH/BTC","categoryName":"BTC"}]}]
     */

    private String code;
    private boolean success;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * categoryName : ETH
         * marketCategoryList : [{"marketId":2,"marketMame":"UNC/ETH","categoryName":"ETH"},{"marketId":3,"marketMame":"CEC/ETH","categoryName":"ETH"},{"marketId":17,"marketMame":"CER/ETH","categoryName":"ETH"},{"marketId":18,"marketMame":"FGD/ETH","categoryName":"ETH"},{"marketId":19,"marketMame":"GHFH/ETH","categoryName":"ETH"},{"marketId":20,"marketMame":"FGHE/ETH","categoryName":"ETH"},{"marketId":21,"marketMame":"TUI/ETH","categoryName":"ETH"},{"marketId":22,"marketMame":"LKY/ETH","categoryName":"ETH"},{"marketId":23,"marketMame":"WSERT/ETH","categoryName":"ETH"},{"marketId":5,"marketMame":"X/ETH","categoryName":"ETH"},{"marketId":12,"marketMame":"CEC/ETH","categoryName":"ETH"},{"marketId":13,"marketMame":"WERR/ETH","categoryName":"ETH"},{"marketId":14,"marketMame":"ERE/ETH","categoryName":"ETH"},{"marketId":15,"marketMame":"ADS/ETH","categoryName":"ETH"},{"marketId":16,"marketMame":"BFG/ETH","categoryName":"ETH"}]
         */

        private String categoryName;
        private List<MarketCategoryListBean> marketCategoryList;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<MarketCategoryListBean> getMarketCategoryList() {
            return marketCategoryList;
        }

        public void setMarketCategoryList(List<MarketCategoryListBean> marketCategoryList) {
            this.marketCategoryList = marketCategoryList;
        }

        public static class MarketCategoryListBean {
            /**
             * marketId : 2
             * marketMame : UNC/ETH
             * categoryName : ETH
             */

            private int marketId;
            private String marketMame;
            private String categoryName;

            public int getMarketId() {
                return marketId;
            }

            public void setMarketId(int marketId) {
                this.marketId = marketId;
            }

            public String getMarketMame() {
                return marketMame;
            }

            public void setMarketMame(String marketMame) {
                this.marketMame = marketMame;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }
        }
    }
}
