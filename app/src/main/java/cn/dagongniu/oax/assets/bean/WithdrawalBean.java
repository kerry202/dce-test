package cn.dagongniu.oax.assets.bean;

import java.util.List;

public class WithdrawalBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"pageNum":1,"pageSize":100,"size":4,"startRow":1,"endRow":4,"total":4,"pages":1,"list":[{"id":1,"userId":29,"coinId":3,"coinName":"X","address":"2k8sd8e","remark":"asdwegrgvnty","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null},{"id":2,"userId":29,"coinId":3,"coinName":"X","address":"11111111","remark":"yyy","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null},{"id":3,"userId":29,"coinId":3,"coinName":"X","address":"2k8sd8e","remark":"asdwegrgvnty","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null},{"id":4,"userId":29,"coinId":3,"coinName":"X","address":"2k8sd8e","remark":"asdwegrgvnty","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
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
         * pageNum : 1
         * pageSize : 100
         * size : 4
         * startRow : 1
         * endRow : 4
         * total : 4
         * pages : 1
         * list : [{"id":1,"userId":29,"coinId":3,"coinName":"X","address":"2k8sd8e","remark":"asdwegrgvnty","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null},{"id":2,"userId":29,"coinId":3,"coinName":"X","address":"11111111","remark":"yyy","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null},{"id":3,"userId":29,"coinId":3,"coinName":"X","address":"2k8sd8e","remark":"asdwegrgvnty","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null},{"id":4,"userId":29,"coinId":3,"coinName":"X","address":"2k8sd8e","remark":"asdwegrgvnty","createTime":null,"updateTime":null,"smsCode":null,"emailCode":null,"googleCode":null}]
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
             * id : 1
             * userId : 29
             * coinId : 3
             * coinName : X
             * address : 2k8sd8e
             * remark : asdwegrgvnty
             * createTime : null
             * updateTime : null
             * smsCode : null
             * emailCode : null
             * googleCode : null
             */

            private int id;
            private int userId;
            private int coinId;
            private String coinName;
            private String address;
            private String remark;
            private String createTime;
            private String updateTime;
            private String smsCode;
            private String emailCode;
            private String googleCode;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getSmsCode() {
                return smsCode;
            }

            public void setSmsCode(String smsCode) {
                this.smsCode = smsCode;
            }

            public String getEmailCode() {
                return emailCode;
            }

            public void setEmailCode(String emailCode) {
                this.emailCode = emailCode;
            }

            public String getGoogleCode() {
                return googleCode;
            }

            public void setGoogleCode(String googleCode) {
                this.googleCode = googleCode;
            }
        }
    }
}
