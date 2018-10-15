package cn.dagongniu.oax.assets.bean;

import java.math.BigDecimal;
import java.util.List;

public class RedPacketIndexBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"coinList":[{"id":1,"type":1,"coinId":3,"limitCoinQty":200,"limitPacketQty":10,"createTime":null,"updateTime":null,"coinName":"X"}],"userCoinList":[{"coinId":3,"cnyPrice":0.6431260032,"banlance":999674}]}
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
        private List<CoinListBean> coinList;
        private List<UserCoinListBean> userCoinList;

        public List<CoinListBean> getCoinList() {
            return coinList;
        }

        public void setCoinList(List<CoinListBean> coinList) {
            this.coinList = coinList;
        }

        public List<UserCoinListBean> getUserCoinList() {
            return userCoinList;
        }

        public void setUserCoinList(List<UserCoinListBean> userCoinList) {
            this.userCoinList = userCoinList;
        }

        public static class CoinListBean {
            /**
             * id : 1
             * type : 1
             * coinId : 3
             * limitCoinQty : 200
             * limitPacketQty : 10
             * createTime : null
             * updateTime : null
             * coinName : X
             */

            private int id;
            private int type;
            private int coinId;
            private BigDecimal limitCoinQty;
            private BigDecimal limitPacketQty;
            private String createTime;
            private String updateTime;
            private String coinName;
            private BigDecimal cnyPrice;

            public void setCnyPrice(BigDecimal cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

            public BigDecimal getCnyPrice() {
                return cnyPrice;
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

            public int getCoinId() {
                return coinId;
            }

            public void setCoinId(int coinId) {
                this.coinId = coinId;
            }

            public BigDecimal getLimitCoinQty() {
                return limitCoinQty;
            }

            public void setLimitCoinQty(BigDecimal limitCoinQty) {
                this.limitCoinQty = limitCoinQty;
            }

            public BigDecimal getLimitPacketQty() {
                return limitPacketQty;
            }

            public void setLimitPacketQty(BigDecimal limitPacketQty) {
                this.limitPacketQty = limitPacketQty;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getCoinName() {
                return coinName;
            }

            public void setCoinName(String coinName) {
                this.coinName = coinName;
            }
        }

        public static class UserCoinListBean {
            /**
             * coinId : 3
             * cnyPrice : 0.6431260032
             * banlance : 999674
             */

            private int coinId;
            private BigDecimal cnyPrice;
            private BigDecimal banlance;

            public int getCoinId() {
                return coinId;
            }

            public void setCoinId(int coinId) {
                this.coinId = coinId;
            }

            public BigDecimal getCnyPrice() {
                return cnyPrice;
            }

            public void setCnyPrice(BigDecimal cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

            public BigDecimal getBanlance() {
                return banlance;
            }

            public void setBanlance(BigDecimal banlance) {
                this.banlance = banlance;
            }
        }
    }
}
