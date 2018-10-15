package cn.dagongniu.oax.assets.bean;

import java.util.List;

public class AssetsPropertyListBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"total":{"usdtPrice":5.694729507918776E11,"cnyPrice":3.746564024868043E12,"btcPrice":49801.9590101994,"ethPrice":1.245048975254985E9},"coinList":[{"id":2,"userId":21,"banlance":49792,"freezingBanlance":8,"totalBanlance":49800,"shortName":"BTC","fullName":"Bitcoin","image":"https://static.feixiaohao.com/coin/7033f2f2c2a16094bbb3bafc47205ba8_small.png","cnyPrice":3746416650000,"usdtPrice":569450550000,"withdrawFee":0},{"id":1,"userId":21,"banlance":48943.239604,"freezingBanlance":32.000396,"totalBanlance":48975.24,"shortName":"ETH","fullName":"Ethereum","image":"blob:http://localhost:3001/62a05d2d-8438-468d-b07e-0b43e7a3b6eb","cnyPrice":1.473748229508E8,"usdtPrice":2.24007850236E7,"withdrawFee":0.001},{"id":4,"userId":21,"banlance":29.97,"freezingBanlance":0,"totalBanlance":29.97,"shortName":"UNC","fullName":"UNC","image":"https://static.feixiaohao.com/coin/c719fa95bfbd9fbe8188e6f5ab3c6dea_small.png","cnyPrice":45.09241245,"usdtPrice":6.85398915,"withdrawFee":123}]}
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
         * total : {"usdtPrice":5.694729507918776E11,"cnyPrice":3.746564024868043E12,"btcPrice":49801.9590101994,"ethPrice":1.245048975254985E9}
         * coinList : [{"id":2,"userId":21,"banlance":49792,"freezingBanlance":8,"totalBanlance":49800,"shortName":"BTC","fullName":"Bitcoin","image":"https://static.feixiaohao.com/coin/7033f2f2c2a16094bbb3bafc47205ba8_small.png","cnyPrice":3746416650000,"usdtPrice":569450550000,"withdrawFee":0},{"id":1,"userId":21,"banlance":48943.239604,"freezingBanlance":32.000396,"totalBanlance":48975.24,"shortName":"ETH","fullName":"Ethereum","image":"blob:http://localhost:3001/62a05d2d-8438-468d-b07e-0b43e7a3b6eb","cnyPrice":1.473748229508E8,"usdtPrice":2.24007850236E7,"withdrawFee":0.001},{"id":4,"userId":21,"banlance":29.97,"freezingBanlance":0,"totalBanlance":29.97,"shortName":"UNC","fullName":"UNC","image":"https://static.feixiaohao.com/coin/c719fa95bfbd9fbe8188e6f5ab3c6dea_small.png","cnyPrice":45.09241245,"usdtPrice":6.85398915,"withdrawFee":123}]
         */

        private TotalBean total;
        private List<CoinListBean> coinList;

        public TotalBean getTotal() {
            return total;
        }

        public void setTotal(TotalBean total) {
            this.total = total;
        }

        public List<CoinListBean> getCoinList() {
            return coinList;
        }

        public void setCoinList(List<CoinListBean> coinList) {
            this.coinList = coinList;
        }

        public static class TotalBean {
            /**
             * usdtPrice : 5.694729507918776E11
             * cnyPrice : 3.746564024868043E12
             * btcPrice : 49801.9590101994
             * ethPrice : 1.245048975254985E9
             */

            private String usdtPrice;
            private String cnyPrice;
            private String btcPrice;
            private String ethPrice;

            public String getUsdtPrice() {
                return usdtPrice;
            }

            public void setUsdtPrice(String usdtPrice) {
                this.usdtPrice = usdtPrice;
            }

            public String getCnyPrice() {
                return cnyPrice;
            }

            public void setCnyPrice(String cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

            public String getBtcPrice() {
                return btcPrice;
            }

            public void setBtcPrice(String btcPrice) {
                this.btcPrice = btcPrice;
            }

            public String getEthPrice() {
                return ethPrice;
            }

            public void setEthPrice(String ethPrice) {
                this.ethPrice = ethPrice;
            }
        }

        public static class CoinListBean {
            /**
             * id : 2
             * userId : 21
             * banlance : 49792
             * freezingBanlance : 8
             * totalBanlance : 49800
             * shortName : BTC
             * fullName : Bitcoin
             * image : https://static.feixiaohao.com/coin/7033f2f2c2a16094bbb3bafc47205ba8_small.png
             * cnyPrice : 3746416650000
             * usdtPrice : 569450550000
             * withdrawFee : 0
             */

            private int id;
            private int userId;
            private String banlance;
            private String freezingBanlance;
            private String totalBanlance;
            private String shortName;
            private String fullName;
            private String image;
            private String cnyPrice;
            private String usdtPrice;
            private String withdrawFee;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getBanlance() {
                return banlance;
            }

            public void setBanlance(String banlance) {
                this.banlance = banlance;
            }

            public String getFreezingBanlance() {
                return freezingBanlance;
            }

            public void setFreezingBanlance(String freezingBanlance) {
                this.freezingBanlance = freezingBanlance;
            }

            public String getTotalBanlance() {
                return totalBanlance;
            }

            public void setTotalBanlance(String totalBanlance) {
                this.totalBanlance = totalBanlance;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getCnyPrice() {
                return cnyPrice;
            }

            public void setCnyPrice(String cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

            public String getUsdtPrice() {
                return usdtPrice;
            }

            public void setUsdtPrice(String usdtPrice) {
                this.usdtPrice = usdtPrice;
            }

            public String getWithdrawFee() {
                return withdrawFee;
            }

            public void setWithdrawFee(String withdrawFee) {
                this.withdrawFee = withdrawFee;
            }
        }
    }
}
