package cn.dagongniu.oax.main.bean;

import java.math.BigDecimal;

public class AssetsDetesilTradeListBean {


    /**
     * cnyPrice : 400
     * id : 1
     * name : X/ETH
     * newPrice : 0.1
     * rate : 0
     */

    private BigDecimal cnyPrice;
    private int id;
    private String name;
    private BigDecimal newPrice;
    private String rate;

    public BigDecimal getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(BigDecimal cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
