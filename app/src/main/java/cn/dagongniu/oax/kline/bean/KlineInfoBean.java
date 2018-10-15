package cn.dagongniu.oax.kline.bean;

public class KlineInfoBean {

    /**
     * id : 3
     * marketId : 1
     * open : 0.058
     * high : 0.086
     * low : 0.066
     * close : 0.07388
     * qty : 500
     * lasteTradeId : 625
     * minType : 60
     * createTime : 11
     */

    private int id;
    private int marketId;
    private float open;
    private float high;
    private float low;
    private float close;
    private float qty;
    private int lasteTradeId;
    private int minType;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public int getLasteTradeId() {
        return lasteTradeId;
    }

    public void setLasteTradeId(int lasteTradeId) {
        this.lasteTradeId = lasteTradeId;
    }

    public int getMinType() {
        return minType;
    }

    public void setMinType(int minType) {
        this.minType = minType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
