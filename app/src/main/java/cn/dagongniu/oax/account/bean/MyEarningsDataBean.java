package cn.dagongniu.oax.account.bean;

import java.math.BigDecimal;

public class MyEarningsDataBean {

    /**
     * yesterdayFeedBack :昨日累计收益折合
     */

    private BigDecimal yesterdayFeedBack;

    public BigDecimal getYesterdayFeedBack() {
        return yesterdayFeedBack;
    }

    public void setYesterdayFeedBack(BigDecimal yesterdayFeedBack) {
        this.yesterdayFeedBack = yesterdayFeedBack;
    }
}
