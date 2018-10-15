package cn.dagongniu.oax.account.bean;

import java.math.BigDecimal;

public class EarningsOverviewItemBean {
    private String des;
    private BigDecimal count;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
