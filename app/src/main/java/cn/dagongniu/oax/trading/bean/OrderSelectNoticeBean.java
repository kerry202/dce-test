package cn.dagongniu.oax.trading.bean;

/**
 * 订单筛选模型
 */
public class OrderSelectNoticeBean {

    public String beginDate; //根据时间查询的开始日期 必须是‘yyyy

    public String endDate; //根据时间查询的结束 必须是‘yyyy-MM

    public String marketId; //交易对id

    public String type; //1买入 2 卖出

    public int dateType;// 1天   2一周  3一个月  4三个月  5all

    public String coinName;

    public String marKetName;

    public int buySellType ; // -1 买卖  1=买  2=卖

    public int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public void setMarKetName(String marKetName) {
        this.marKetName = marKetName;
    }

    public void setBuySellType(int buySellType) {
        this.buySellType = buySellType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getType() {
        return type;
    }

    public int getDateType() {
        return dateType;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getMarKetName() {
        return marKetName;
    }

    public int getBuySellType() {
        return buySellType;
    }
}
