package cn.dagongniu.oax.assets.bean;

import java.math.BigDecimal;
import java.util.List;

public class GrabRedPacketRecordBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"grabRedPacketPageInfo":{"pageNum":1,"pageSize":2,"size":2,"startRow":0,"endRow":1,"total":2,"pages":1,"list":[{"createTime":"2018-08-10 17:24","coinQty":11.40693462,"coinName":"X","accountNumber":"674773032@qq.com","cny":5.29},{"createTime":"2018-08-10 17:22","coinQty":11,"coinName":"X","accountNumber":"674773032@qq.com","cny":5.13}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1},"totalCNY":10.42}
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
         * grabRedPacketPageInfo : {"pageNum":1,"pageSize":2,"size":2,"startRow":0,"endRow":1,"total":2,"pages":1,"list":[{"createTime":"2018-08-10 17:24","coinQty":11.40693462,"coinName":"X","accountNumber":"674773032@qq.com","cny":5.29},{"createTime":"2018-08-10 17:22","coinQty":11,"coinName":"X","accountNumber":"674773032@qq.com","cny":5.13}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
         * totalCNY : 10.42
         */

        private GrabRedPacketPageInfoBean grabRedPacketPageInfo;
        private double totalCNY;

        public GrabRedPacketPageInfoBean getGrabRedPacketPageInfo() {
            return grabRedPacketPageInfo;
        }

        public void setGrabRedPacketPageInfo(GrabRedPacketPageInfoBean grabRedPacketPageInfo) {
            this.grabRedPacketPageInfo = grabRedPacketPageInfo;
        }

        public double getTotalCNY() {
            return totalCNY;
        }

        public void setTotalCNY(double totalCNY) {
            this.totalCNY = totalCNY;
        }

        public static class GrabRedPacketPageInfoBean {
            /**
             * pageNum : 1
             * pageSize : 2
             * size : 2
             * startRow : 0
             * endRow : 1
             * total : 2
             * pages : 1
             * list : [{"createTime":"2018-08-10 17:24","coinQty":11.40693462,"coinName":"X","accountNumber":"674773032@qq.com","cny":5.29},{"createTime":"2018-08-10 17:22","coinQty":11,"coinName":"X","accountNumber":"674773032@qq.com","cny":5.13}]
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
            private int expireFlag;//红包是否已过期标识 0表示未过期 1表示已过期
            private int takeFlag;  //是否已领完标识 0未领取玩 1已领取完
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

            public void setFirstPage(boolean firstPage) {
                isFirstPage = firstPage;
            }

            public void setLastPage(boolean lastPage) {
                isLastPage = lastPage;
            }

            public void setExpireFlag(int expireFlag) {
                this.expireFlag = expireFlag;
            }

            public void setTakeFlag(int takeFlag) {
                this.takeFlag = takeFlag;
            }

            public boolean isFirstPage() {
                return isFirstPage;
            }

            public boolean isLastPage() {
                return isLastPage;
            }

            public int getExpireFlag() {
                return expireFlag;
            }

            public int getTakeFlag() {
                return takeFlag;
            }

            public static class ListBean {
                /**
                 * createTime : 2018-08-10 17:24
                 * coinQty : 11.40693462
                 * coinName : X
                 * accountNumber : 674773032@qq.com
                 * cny : 5.29
                 */

                private String createTime;
                private BigDecimal coinQty;
                private String coinName;
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

                public String getCoinName() {
                    return coinName;
                }

                public void setCoinName(String coinName) {
                    this.coinName = coinName;
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
    }
}
