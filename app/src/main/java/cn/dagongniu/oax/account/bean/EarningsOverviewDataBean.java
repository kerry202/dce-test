package cn.dagongniu.oax.account.bean;

import java.math.BigDecimal;

public class EarningsOverviewDataBean {

    private BigDecimal total;//总量
    private BigDecimal totalMarketValue; //总市值
    private BigDecimal circulationMarketValue;//流通总量
    private BigDecimal circulationTotal; //流通市值

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(BigDecimal totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public BigDecimal getCirculationMarketValue() {
        return circulationMarketValue;
    }

    public void setCirculationMarketValue(BigDecimal circulationMarketValue) {
        this.circulationMarketValue = circulationMarketValue;
    }

    public BigDecimal getCirculationTotal() {
        return circulationTotal;
    }

    public void setCirculationTotal(BigDecimal circulationTotal) {
        this.circulationTotal = circulationTotal;
    }
}
