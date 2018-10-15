package cn.dagongniu.oax.kline.bean;

public class TransactionBean {

    /**
     * 返回数据中还有其他参数，有需要可以抓包
     * <p>
     * price : 200
     * qty : 2.34E-5
     * createTime : 20:48:39
     * buyOrSell : 1
     * dealType : 0
     */

    private double price;
    private double qty;
    private String createTime;
    private int buyOrSell;
    private int dealType;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(int buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }
}
