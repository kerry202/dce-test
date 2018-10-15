package cn.dagongniu.oax.assets.bean;

import java.math.BigDecimal;
import java.util.List;

public class SendRedPacketRecordBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"pageInfo":{"pageNum":1,"pageSize":20,"size":3,"startRow":1,"endRow":3,"total":3,"pages":1,"list":[{"id":9,"coinId":3,"coinName":"X","type":2,"totalCoinQty":56,"totalCny":26.04,"totalPacketQty":6,"grabPacketQty":0,"createTime":"2018-08-10 15:59"},{"id":8,"coinId":3,"coinName":"X","type":2,"totalCoinQty":50,"totalCny":23.17,"totalPacketQty":2,"grabPacketQty":0,"createTime":"2018-08-10 15:47"},{"id":7,"coinId":3,"coinName":"X","type":2,"totalCoinQty":50,"totalCny":23.22,"totalPacketQty":2,"grabPacketQty":0,"createTime":"2018-08-10 15:37"}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1},"totalCNY":72.43}
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
         * pageInfo : {"pageNum":1,"pageSize":20,"size":3,"startRow":1,"endRow":3,"total":3,"pages":1,"list":[{"id":9,"coinId":3,"coinName":"X","type":2,"totalCoinQty":56,"totalCny":26.04,"totalPacketQty":6,"grabPacketQty":0,"createTime":"2018-08-10 15:59"},{"id":8,"coinId":3,"coinName":"X","type":2,"totalCoinQty":50,"totalCny":23.17,"totalPacketQty":2,"grabPacketQty":0,"createTime":"2018-08-10 15:47"},{"id":7,"coinId":3,"coinName":"X","type":2,"totalCoinQty":50,"totalCny":23.22,"totalPacketQty":2,"grabPacketQty":0,"createTime":"2018-08-10 15:37"}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
         * totalCNY : 72.43
         */

        private PageInfoBean pageInfo;
        private String totalCNY;

        public PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public String getTotalCNY() {
            return totalCNY;
        }

        public void setTotalCNY(String totalCNY) {
            this.totalCNY = totalCNY;
        }

        public static class PageInfoBean {
            /**
             * pageNum : 1
             * pageSize : 20
             * size : 3
             * startRow : 1
             * endRow : 3
             * total : 3
             * pages : 1
             * list : [{"id":9,"coinId":3,"coinName":"X","type":2,"totalCoinQty":56,"totalCny":26.04,"totalPacketQty":6,"grabPacketQty":0,"createTime":"2018-08-10 15:59"},{"id":8,"coinId":3,"coinName":"X","type":2,"totalCoinQty":50,"totalCny":23.17,"totalPacketQty":2,"grabPacketQty":0,"createTime":"2018-08-10 15:47"},{"id":7,"coinId":3,"coinName":"X","type":2,"totalCoinQty":50,"totalCny":23.22,"totalPacketQty":2,"grabPacketQty":0,"createTime":"2018-08-10 15:37"}]
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


            public void setFirstPage(boolean firstPage) {
                isFirstPage = firstPage;
            }

            public void setLastPage(boolean lastPage) {
                isLastPage = lastPage;
            }


            public boolean isFirstPage() {
                return isFirstPage;
            }

            public boolean isLastPage() {
                return isLastPage;
            }


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
                 * id : 9
                 * coinId : 3
                 * coinName : X
                 * type : 2
                 * totalCoinQty : 56
                 * totalCny : 26.04
                 * totalPacketQty : 6
                 * grabPacketQty : 0
                 * createTime : 2018-08-10 15:59
                 */

                private int id;
                private int coinId;
                private String coinName;
                private int type;
                private BigDecimal totalCoinQty;
                private BigDecimal totalCny;
                private int totalPacketQty;
                private int grabPacketQty;
                private String createTime;
                private int expireFlag;//红包是否已过期标识 0表示未过期 1表示已过期
                private int takeFlag;  //是否已领完标识 0未领取玩 1已领取完

                public void setExpireFlag(int expireFlag) {
                    this.expireFlag = expireFlag;
                }

                public void setTakeFlag(int takeFlag) {
                    this.takeFlag = takeFlag;
                }

                public int getExpireFlag() {
                    return expireFlag;
                }

                public int getTakeFlag() {
                    return takeFlag;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
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
            }
        }
    }
}
