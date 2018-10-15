package cn.dagongniu.oax.account.bean;

import java.math.BigDecimal;

public class TotalFeedbackItemBean {
    private String des;
    private BigDecimal feedBackETH;
    private BigDecimal feedBackX;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public BigDecimal getFeedBackETH() {
        return feedBackETH;
    }

    public void setFeedBackETH(BigDecimal feedBackETH) {
        this.feedBackETH = feedBackETH;
    }

    public BigDecimal getFeedBackX() {
        return feedBackX;
    }

    public void setFeedBackX(BigDecimal feedBackX) {
        this.feedBackX = feedBackX;
    }

}
