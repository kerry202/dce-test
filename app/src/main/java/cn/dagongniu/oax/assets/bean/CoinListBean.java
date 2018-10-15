package cn.dagongniu.oax.assets.bean;

import java.util.List;

public class CoinListBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : [{"coinId":1,"coinName":"ETH"},{"coinId":2,"coinName":"BTC"},{"coinId":3,"coinName":"X"},{"coinId":4,"coinName":"UNC"},{"coinId":5,"coinName":"CEC"}]
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
         * coinId : 1
         * coinName : ETH
         */

        private int coinId;
        private String coinName;

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
    }
}
