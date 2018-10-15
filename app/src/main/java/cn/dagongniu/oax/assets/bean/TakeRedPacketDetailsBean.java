package cn.dagongniu.oax.assets.bean;

import java.math.BigDecimal;
import java.util.List;

public class TakeRedPacketDetailsBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"redPacketLogDetailsPageInfo":{"pageNum":1,"pageSize":3,"size":3,"startRow":0,"endRow":2,"total":3,"pages":1,"list":[{"createTime":"2018-08-10 17:22","coinQty":11,"accountNumber":"674773032@qq.com","cny":5.13},{"createTime":"2018-08-10 17:20","coinQty":11,"accountNumber":"18692223662","cny":5.13},{"createTime":"2018-08-10 17:19","coinQty":11,"cny":5.13}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1},"redPacketInfo":{"totalPacketQty":10,"totalCoinQty":110,"grabPacketQty":3,"expireFlag":1,"grabCoinQty":33,"surplusCoinQty":77,"coinName":"X","id":10,"title":"大公牛货币红包","url":"http://test.oax.com/redpacket.html#/git/10","takeFlag":0,"desc":"福利来啦，发红包啦"}}
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
         * redPacketLogDetailsPageInfo : {"pageNum":1,"pageSize":3,"size":3,"startRow":0,"endRow":2,"total":3,"pages":1,"list":[{"createTime":"2018-08-10 17:22","coinQty":11,"accountNumber":"674773032@qq.com","cny":5.13},{"createTime":"2018-08-10 17:20","coinQty":11,"accountNumber":"18692223662","cny":5.13},{"createTime":"2018-08-10 17:19","coinQty":11,"cny":5.13}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
         * redPacketInfo : {"totalPacketQty":10,"totalCoinQty":110,"grabPacketQty":3,"expireFlag":1,"grabCoinQty":33,"surplusCoinQty":77,"coinName":"X","id":10,"title":"大公牛货币红包","url":"http://test.oax.com/redpacket.html#/git/10","takeFlag":0,"desc":"福利来啦，发红包啦"}
         */

        private RedPacketLogDetailsPageInfoBean redPacketLogDetailsPageInfo;
        private RedPacketInfoBean redPacketInfo;

        public RedPacketLogDetailsPageInfoBean getRedPacketLogDetailsPageInfo() {
            return redPacketLogDetailsPageInfo;
        }

        public void setRedPacketLogDetailsPageInfo(RedPacketLogDetailsPageInfoBean redPacketLogDetailsPageInfo) {
            this.redPacketLogDetailsPageInfo = redPacketLogDetailsPageInfo;
        }

        public RedPacketInfoBean getRedPacketInfo() {
            return redPacketInfo;
        }

        public void setRedPacketInfo(RedPacketInfoBean redPacketInfo) {
            this.redPacketInfo = redPacketInfo;
        }

        public static class RedPacketLogDetailsPageInfoBean {
            /**
             * pageNum : 1
             * pageSize : 3
             * size : 3
             * startRow : 0
             * endRow : 2
             * total : 3
             * pages : 1
             * list : [{"createTime":"2018-08-10 17:22","coinQty":11,"accountNumber":"674773032@qq.com","cny":5.13},{"createTime":"2018-08-10 17:20","coinQty":11,"accountNumber":"18692223662","cny":5.13},{"createTime":"2018-08-10 17:19","coinQty":11,"cny":5.13}]
             * prePage : 0
             * nextPage : 0
             * isFirstPage : true
             * isLastPage : true
             * hasPreviousPage : false
             * hasNextPage : false
             * navigatePages : 8
             * navigatepageNums : [1]
             * navigateFirstPage : 1
             * navigateLastPage : 1
             * firstPage : 1
             * lastPage : 1
             */

            private int pageNum;
            private int pageSize;
            private int size;
            private int startRow;
            private int endRow;
            private int total;
            private int pages;
            private int prePage;
            private int nextPage;
            private boolean isFirstPage;
            private boolean isLastPage;
            private boolean hasPreviousPage;
            private boolean hasNextPage;
            private int navigatePages;
            private int navigateFirstPage;
            private int navigateLastPage;
            private int firstPage;
            private int lastPage;
            private List<ListBean> list;
            private List<Integer> navigatepageNums;

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public int getPrePage() {
                return prePage;
            }

            public void setPrePage(int prePage) {
                this.prePage = prePage;
            }

            public int getNextPage() {
                return nextPage;
            }

            public void setNextPage(int nextPage) {
                this.nextPage = nextPage;
            }

            public boolean isIsFirstPage() {
                return isFirstPage;
            }

            public void setIsFirstPage(boolean isFirstPage) {
                this.isFirstPage = isFirstPage;
            }

            public boolean isIsLastPage() {
                return isLastPage;
            }

            public void setIsLastPage(boolean isLastPage) {
                this.isLastPage = isLastPage;
            }

            public boolean isHasPreviousPage() {
                return hasPreviousPage;
            }

            public void setHasPreviousPage(boolean hasPreviousPage) {
                this.hasPreviousPage = hasPreviousPage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public int getNavigatePages() {
                return navigatePages;
            }

            public void setNavigatePages(int navigatePages) {
                this.navigatePages = navigatePages;
            }

            public int getNavigateFirstPage() {
                return navigateFirstPage;
            }

            public void setNavigateFirstPage(int navigateFirstPage) {
                this.navigateFirstPage = navigateFirstPage;
            }

            public int getNavigateLastPage() {
                return navigateLastPage;
            }

            public void setNavigateLastPage(int navigateLastPage) {
                this.navigateLastPage = navigateLastPage;
            }

            public int getFirstPage() {
                return firstPage;
            }

            public void setFirstPage(int firstPage) {
                this.firstPage = firstPage;
            }

            public int getLastPage() {
                return lastPage;
            }

            public void setLastPage(int lastPage) {
                this.lastPage = lastPage;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public List<Integer> getNavigatepageNums() {
                return navigatepageNums;
            }

            public void setNavigatepageNums(List<Integer> navigatepageNums) {
                this.navigatepageNums = navigatepageNums;
            }

            public static class ListBean {
                /**
                 * createTime : 2018-08-10 17:22
                 * coinQty : 11
                 * accountNumber : 674773032@qq.com
                 * cny : 5.13
                 */

                private String createTime;
                private BigDecimal coinQty;
                private String accountNumber;
                private BigDecimal cny;

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public BigDecimal getCoinQty() {
                    return coinQty;
                }

                public void setCoinQty(BigDecimal coinQty) {
                    this.coinQty = coinQty;
                }

                public String getAccountNumber() {
                    return accountNumber;
                }

                public void setAccountNumber(String accountNumber) {
                    this.accountNumber = accountNumber;
                }

                public BigDecimal getCny() {
                    return cny;
                }

                public void setCny(BigDecimal cny) {
                    this.cny = cny;
                }
            }
        }

        public static class RedPacketInfoBean {
            /**
             * totalPacketQty : 10
             * totalCoinQty : 110
             * grabPacketQty : 3
             * expireFlag : 1
             * grabCoinQty : 33
             * surplusCoinQty : 77
             * coinName : X
             * id : 10
             * title : 大公牛货币红包
             * url : http://test.oax.com/redpacket.html#/git/10
             * takeFlag : 0
             * desc : 福利来啦，发红包啦
             */

            private int totalPacketQty;
            private BigDecimal totalCoinQty;
            private int grabPacketQty;
            private int expireFlag;
            private BigDecimal grabCoinQty;
            private BigDecimal surplusCoinQty;
            private String coinName;
            private int id;
            private String title;
            private String url;
            private int takeFlag;
            private String desc;

            public int getTotalPacketQty() {
                return totalPacketQty;
            }

            public void setTotalPacketQty(int totalPacketQty) {
                this.totalPacketQty = totalPacketQty;
            }

            public BigDecimal getTotalCoinQty() {
                return totalCoinQty;
            }

            public void setTotalCoinQty(BigDecimal totalCoinQty) {
                this.totalCoinQty = totalCoinQty;
            }

            public int getGrabPacketQty() {
                return grabPacketQty;
            }

            public void setGrabPacketQty(int grabPacketQty) {
                this.grabPacketQty = grabPacketQty;
            }

            public int getExpireFlag() {
                return expireFlag;
            }

            public void setExpireFlag(int expireFlag) {
                this.expireFlag = expireFlag;
            }

            public BigDecimal getGrabCoinQty() {
                return grabCoinQty;
            }

            public void setGrabCoinQty(BigDecimal grabCoinQty) {
                this.grabCoinQty = grabCoinQty;
            }

            public BigDecimal getSurplusCoinQty() {
                return surplusCoinQty;
            }

            public void setSurplusCoinQty(BigDecimal surplusCoinQty) {
                this.surplusCoinQty = surplusCoinQty;
            }

            public String getCoinName() {
                return coinName;
            }

            public void setCoinName(String coinName) {
                this.coinName = coinName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getTakeFlag() {
                return takeFlag;
            }

            public void setTakeFlag(int takeFlag) {
                this.takeFlag = takeFlag;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
