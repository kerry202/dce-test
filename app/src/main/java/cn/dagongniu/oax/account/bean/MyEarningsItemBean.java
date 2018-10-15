package cn.dagongniu.oax.account.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyEarningsItemBean {
    private String title;
    private List<CoinsBean> coins = new ArrayList<>();

    public List<CoinsBean> getCoins() {
        return coins;
    }

    public void setCoins(List<CoinsBean> coins) {
        this.coins = coins;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class CoinsBean {
        private BigDecimal count;
        private String coinsName;

        public BigDecimal getCount() {
            return count;
        }

        public void setCount(BigDecimal count) {
            this.count = count;
        }

        public String getCoinsName() {
            return coinsName;
        }

        public void setCoinsName(String coinsName) {
            this.coinsName = coinsName;
        }
    }
}
