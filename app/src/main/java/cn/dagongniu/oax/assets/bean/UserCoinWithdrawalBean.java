package cn.dagongniu.oax.assets.bean;

import java.math.BigDecimal;

public class UserCoinWithdrawalBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"id":3,"userId":77,"banlance":49984.311264,"freezingBanlance":18.2145,"totalBanlance":50002.525764,"shortName":"X","fullName":" XCoin","image":"https://static.feixiaohao.com/coin/20180615/d6eb596d23b542acaa7537e6409a1428_16_16.png","cnyPrice":1.1088105090440187E9,"usdtPrice":1.685370132643655E8,"withdrawFee":0}
     */

    private String code;
    private boolean success;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserCoinWithdrawalBean{" +
                "code='" + code + '\'' +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {

        /**
         * id : 3
         * userId : 77
         * banlance : 49984.311264
         * freezingBanlance : 18.2145
         * totalBanlance : 50002.525764
         * shortName : X
         * fullName :  XCoin
         * image : https://static.feixiaohao.com/coin/20180615/d6eb596d23b542acaa7537e6409a1428_16_16.png
         * cnyPrice : 1.1088105090440187E9
         * usdtPrice : 1.685370132643655E8
         * withdrawFee : 0
         */

        private int id;
        private int userId;
        private BigDecimal banlance;
        private BigDecimal freezingBanlance;
        private BigDecimal totalBanlance;
        private String shortName;
        private String fullName;
        private String image;
        private BigDecimal cnyPrice;
        private BigDecimal usdtPrice;
        private BigDecimal withdrawFee;
        private int type;
        private BigDecimal minOutQty;
        private BigDecimal maxOutQty;
        private BigDecimal useredWithdrawal;//已用

        private BigDecimal withdrawalAmount;//总额可用

        public void setUseredWithdrawal(BigDecimal useredWithdrawal) {
            useredWithdrawal = useredWithdrawal;
        }

        public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
            withdrawalAmount = withdrawalAmount;
        }

        public BigDecimal getUseredWithdrawal() {
            return useredWithdrawal;
        }

        public BigDecimal getWithdrawalAmount() {
            return withdrawalAmount;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setMinOutQty(BigDecimal minOutQty) {
            this.minOutQty = minOutQty;
        }

        public void setMaxOutQty(BigDecimal maxOutQty) {
            this.maxOutQty = maxOutQty;
        }

        public int getType() {
            return type;
        }

        public BigDecimal getMinOutQty() {
            return minOutQty;
        }

        public BigDecimal getMaxOutQty() {
            return maxOutQty;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public BigDecimal getBanlance() {
            return banlance;
        }

        public void setBanlance(BigDecimal banlance) {
            this.banlance = banlance;
        }

        public BigDecimal getFreezingBanlance() {
            return freezingBanlance;
        }

        public void setFreezingBanlance(BigDecimal freezingBanlance) {
            this.freezingBanlance = freezingBanlance;
        }

        public BigDecimal getTotalBanlance() {
            return totalBanlance;
        }

        public void setTotalBanlance(BigDecimal totalBanlance) {
            this.totalBanlance = totalBanlance;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public BigDecimal getCnyPrice() {
            return cnyPrice;
        }

        public void setCnyPrice(BigDecimal cnyPrice) {
            this.cnyPrice = cnyPrice;
        }

        public BigDecimal getUsdtPrice() {
            return usdtPrice;
        }

        public void setUsdtPrice(BigDecimal usdtPrice) {
            this.usdtPrice = usdtPrice;
        }

        public BigDecimal getWithdrawFee() {
            return withdrawFee;
        }

        public void setWithdrawFee(BigDecimal withdrawFee) {
            this.withdrawFee = withdrawFee;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", banlance=" + banlance +
                    ", freezingBanlance=" + freezingBanlance +
                    ", totalBanlance=" + totalBanlance +
                    ", shortName='" + shortName + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", image='" + image + '\'' +
                    ", cnyPrice=" + cnyPrice +
                    ", usdtPrice=" + usdtPrice +
                    ", withdrawFee=" + withdrawFee +
                    ", type=" + type +
                    ", minOutQty=" + minOutQty +
                    ", maxOutQty=" + maxOutQty +
                    ", useredWithdrawal=" + useredWithdrawal +
                    ", withdrawalAmount=" + withdrawalAmount +
                    '}';
        }
    }
}
