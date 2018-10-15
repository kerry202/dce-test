package cn.dagongniu.oax.utils.events;

public class KLineBuyOrSellEvent {
    public int marketId;
    public int minType;
    public boolean isBuy;

    public KLineBuyOrSellEvent(int marketId, int minType, boolean isBuy) {
        this.marketId = marketId;
        this.minType = minType;
        this.isBuy = isBuy;
    }
}
