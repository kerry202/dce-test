package cn.dagongniu.oax.assets.bean;

import java.math.BigDecimal;

public class AwardRedPacketBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"redPacket":{"id":7,"userId":219159,"type":2,"coinId":3,"coinName":"X","totalCoinQty":50,"totalCny":23.22465,"totalPacketQty":2,"wishWords":"","grabCoinQty":0,"grabPacketQty":0,"createTime":"2018-08-10 15:37:56","expireTime":"2018-08-11 15:37:56","updateTime":"2018-08-10 15:37:56","accountNumber":null},"url":"http://test.oax.com/#/git/7"}
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

    public static class DataBean {
        /**
         * redPacket : {"id":7,"userId":219159,"type":2,"coinId":3,"coinName":"X","totalCoinQty":50,"totalCny":23.22465,"totalPacketQty":2,"wishWords":"","grabCoinQty":0,"grabPacketQty":0,"createTime":"2018-08-10 15:37:56","expireTime":"2018-08-11 15:37:56","updateTime":"2018-08-10 15:37:56","accountNumber":null}
         * url : http://test.oax.com/#/git/7
         */

        private RedPacketBean redPacket;
        private String url;
        private String title;
        private String desc;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        public RedPacketBean getRedPacket() {
            return redPacket;
        }

        public void setRedPacket(RedPacketBean redPacket) {
            this.redPacket = redPacket;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class RedPacketBean {
            /**
             * id : 7
             * userId : 219159
             * type : 2
             * coinId : 3
             * coinName : X
             * totalCoinQty : 50
             * totalCny : 23.22465
             * totalPacketQty : 2
             * wishWords :
             * grabCoinQty : 0
             * grabPacketQty : 0
             * createTime : 2018-08-10 15:37:56
             * expireTime : 2018-08-11 15:37:56
             * updateTime : 2018-08-10 15:37:56
             * accountNumber : null
             */

            private int id;
            private int userId;
            private int type;
            private int coinId;
            private String coinName;
            private BigDecimal totalCoinQty;
            private BigDecimal totalCny;
            private int totalPacketQty;
            private String wishWords;
            private BigDecimal grabCoinQty;
            private int grabPacketQty;
            private String createTime;
            private String expireTime;
            private String updateTime;
            private String accountNumber;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public BigDecimal getTotalCoinQty() {
                return totalCoinQty;
            }

            public void setTotalCoinQty(BigDecimal totalCoinQty) {
                this.totalCoinQty = totalCoinQty;
            }

            public BigDecimal getTotalCny() {
                return totalCny;
            }

            public void setTotalCny(BigDecimal totalCny) {
                this.totalCny = totalCny;
            }

            public int getTotalPacketQty() {
                return totalPacketQty;
            }

            public void setTotalPacketQty(int totalPacketQty) {
                this.totalPacketQty = totalPacketQty;
            }

            public String getWishWords() {
                return wishWords;
            }

            public void setWishWords(String wishWords) {
                this.wishWords = wishWords;
            }

            public BigDecimal getGrabCoinQty() {
                return grabCoinQty;
            }

            public void setGrabCoinQty(BigDecimal grabCoinQty) {
                this.grabCoinQty = grabCoinQty;
            }

            public int getGrabPacketQty() {
                return grabPacketQty;
            }

            public void setGrabPacketQty(int grabPacketQty) {
                this.grabPacketQty = grabPacketQty;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getAccountNumber() {
                return accountNumber;
            }

            public void setAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
            }
        }
    }
}
