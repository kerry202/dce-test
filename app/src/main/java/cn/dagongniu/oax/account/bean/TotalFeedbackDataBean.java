package cn.dagongniu.oax.account.bean;

import java.math.BigDecimal;

public class TotalFeedbackDataBean {


    /**
     * todayCompensationFeedBack : 今日补偿待分配收入折合
     * todayFeedBackSum : 今日待分配收入折合
     * todayTradeFeedBack : 今日交易待分配收入折合
     */

    private TodayCompensationFeedBackBean todayCompensationFeedBack;
    private TodayFeedBackSumBean todayFeedBackSum;
    private TodayTradeFeedBackBean todayTradeFeedBack;

    public TodayCompensationFeedBackBean getTodayCompensationFeedBack() {
        return todayCompensationFeedBack;
    }

    public void setTodayCompensationFeedBack(TodayCompensationFeedBackBean todayCompensationFeedBack) {
        this.todayCompensationFeedBack = todayCompensationFeedBack;
    }

    public TodayFeedBackSumBean getTodayFeedBackSum() {
        return todayFeedBackSum;
    }

    public void setTodayFeedBackSum(TodayFeedBackSumBean todayFeedBackSum) {
        this.todayFeedBackSum = todayFeedBackSum;
    }

    public TodayTradeFeedBackBean getTodayTradeFeedBack() {
        return todayTradeFeedBack;
    }

    public void setTodayTradeFeedBack(TodayTradeFeedBackBean todayTradeFeedBack) {
        this.todayTradeFeedBack = todayTradeFeedBack;
    }

    public static class TodayCompensationFeedBackBean {
        /**
         * feedBackETH : 0
         * feedBackX : 0
         */

        private BigDecimal feedBackETH;
        private BigDecimal feedBackX;

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

    public static class TodayFeedBackSumBean {
        /**
         * feedBackETH : 0
         * feedBackX : 0
         */

        private BigDecimal feedBackETH;
        private BigDecimal feedBackX;

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

    public static class TodayTradeFeedBackBean {
        /**
         * feedBackETH : 0
         * feedBackX : 0
         */

        private BigDecimal feedBackETH;
        private BigDecimal feedBackX;

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
}
