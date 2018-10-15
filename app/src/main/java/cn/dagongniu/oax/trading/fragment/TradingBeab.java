package cn.dagongniu.oax.trading.fragment;

public class TradingBeab {

    /**
     * cnyPrice : 500.0
     * coinId : 4
     * coinName : VITE
     * incRate : 0.0
     * lastTradePrice : 0.0
     * marketCoinId : 9
     * marketCoinName : ETH
     * marketId : 4
     * maxPrice : 0.0
     * minPrice : 0.0
     * priceDecimals : 8
     * qtyDecimals : 3
     * totalAmount : 0.0
     * tradeQty : 0.0
     */

    private double cnyPrice;
    private int coinId;
    private String coinName;
    private double incRate;
    private double lastTradePrice;
    private int marketCoinId;
    private String marketCoinName;
    private int marketId;
    private double maxPrice;
    private double minPrice;
    private int priceDecimals;
    private int qtyDecimals;
    private double totalAmount;
    private double tradeQty;

    public double getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(double cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

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

    public double getIncRate() {
        return incRate;
    }

    public void setIncRate(double incRate) {
        this.incRate = incRate;
    }

    public double getLastTradePrice() {
        return lastTradePrice;
    }

    public void setLastTradePrice(double lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    public int getMarketCoinId() {
        return marketCoinId;
    }

    public void setMarketCoinId(int marketCoinId) {
        this.marketCoinId = marketCoinId;
    }

    public String getMarketCoinName() {
        return marketCoinName;
    }

    public void setMarketCoinName(String marketCoinName) {
        this.marketCoinName = marketCoinName;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getPriceDecimals() {
        return priceDecimals;
    }

    public void setPriceDecimals(int priceDecimals) {
        this.priceDecimals = priceDecimals;
    }

    public int getQtyDecimals() {
        return qtyDecimals;
    }

    public void setQtyDecimals(int qtyDecimals) {
        this.qtyDecimals = qtyDecimals;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTradeQty() {
        return tradeQty;
    }

    public void setTradeQty(double tradeQty) {
        this.tradeQty = tradeQty;
    }
}
