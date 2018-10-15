package cn.dagongniu.oax.assets.bean;

import java.math.BigDecimal;
import java.util.List;

public class PropertyShowBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"userCoin":{"id":3,"userId":77,"banlance":49984.311264,"freezingBanlance":18.2145,"totalBanlance":50002.525764,"shortName":"X","fullName":" XCoin","image":"https://static.feixiaohao.com/coin/20180615/d6eb596d23b542acaa7537e6409a1428_16_16.png","cnyPrice":1.1042077765474427E9,"usdtPrice":1.6783697790366948E8,"withdrawFee":0},"tradeList":[{"id":5,"name":"X/ETH","newPrice":7,"cnyPrice":22083.04,"rate":0}]}
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
         * userCoin : {"id":3,"userId":77,"banlance":49984.311264,"freezingBanlance":18.2145,"totalBanlance":50002.525764,"shortName":"X","fullName":" XCoin","image":"https://static.feixiaohao.com/coin/20180615/d6eb596d23b542acaa7537e6409a1428_16_16.png","cnyPrice":1.1042077765474427E9,"usdtPrice":1.6783697790366948E8,"withdrawFee":0}
         * tradeList : [{"id":5,"name":"X/ETH","newPrice":7,"cnyPrice":22083.04,"rate":0}]
         */

        private UserCoinBean userCoin;
        private List<TradeListBean> tradeList;

        public UserCoinBean getUserCoin() {
            return userCoin;
        }

        public void setUserCoin(UserCoinBean userCoin) {
            this.userCoin = userCoin;
        }

        public List<TradeListBean> getTradeList() {
            return tradeList;
        }

        public void setTradeList(List<TradeListBean> tradeList) {
            this.tradeList = tradeList;
        }

        public static class UserCoinBean {
            /**
             * id : 3
             * userId : 77
             * banlance : 49984.311264
             * freezingBanlance : 18.2145
             * totalBanlance : 50002.525764
             * shortName : X
             * fullName :  XCoin
             * image : https://static.feixiaohao.com/coin/20180615/d6eb596d23b542acaa7537e6409a1428_16_16.png
             * cnyPrice : 1.1042077765474427E9
             * usdtPrice : 1.6783697790366948E8
             * withdrawFee : 0
             */

            private int id;
            private int userId;
            private BigDecimal banlance;
            private BigDecimal freezingBanlance;
            private BigDecimal totalBanlance;
            private BigDecimal btcPrice;
            private String shortName;
            private String fullName;
            private String image;
            private BigDecimal cnyPrice;
            private BigDecimal usdtPrice;
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

            public BigDecimal getBanlance() {
                return banlance;
            }

            public void setBtcPrice(BigDecimal btcPrice) {
                this.btcPrice = btcPrice;
            }

            public void setBanlance(BigDecimal banlance) {
                this.banlance = banlance;
            }

            public BigDecimal getFreezingBanlance() {
                return freezingBanlance;
            }

            public void setFreezingBanlance(BigDecimal freezingBanlance) {
                this.freezingBanlance = freezingBanlance;
            }

            public BigDecimal getTotalBanlance() {
                return totalBanlance;
            }

            public void setTotalBanlance(BigDecimal totalBanlance) {
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


            public BigDecimal getBtcPrice() {
                return btcPrice;
            }

            public BigDecimal getCnyPrice() {
                return cnyPrice;
            }

            public void setCnyPrice(BigDecimal cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

            public BigDecimal getUsdtPrice() {
                return usdtPrice;
            }

            public void setUsdtPrice(BigDecimal usdtPrice) {
                this.usdtPrice = usdtPrice;
            }

            public String getWithdrawFee() {
                return withdrawFee;
            }

            public void setWithdrawFee(String withdrawFee) {
                this.withdrawFee = withdrawFee;
            }
        }

        public static class TradeListBean {
            /**
             * id : 5
             * name : X/ETH
             * newPrice : 7
             * cnyPrice : 22083.04
             * rate : 0
             */

            private int id;
            private String name;
            private BigDecimal newPrice;
            private BigDecimal cnyPrice;
            private String rate;

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

            public BigDecimal getNewPrice() {
                return newPrice;
            }

            public void setNewPrice(BigDecimal newPrice) {
                this.newPrice = newPrice;
            }

            public BigDecimal getCnyPrice() {
                return cnyPrice;
            }

            public void setCnyPrice(BigDecimal cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }
        }
    }
}
