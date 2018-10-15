package cn.dagongniu.oax.account.bean;


public class MyInvateBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"vertycounts":0,"invateAddress":"7NuOECnt","registercounts":0}
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
         * vertycounts : 0
         * invateAddress : 7NuOECnt
         * registercounts : 0
         */

        private int vertycounts;
        private String invateAddress;
        private int registercounts;

        public int getVertycounts() {
            return vertycounts;
        }

        public void setVertycounts(int vertycounts) {
            this.vertycounts = vertycounts;
        }

        public String getInvateAddress() {
            return invateAddress;
        }

        public void setInvateAddress(String invateAddress) {
            this.invateAddress = invateAddress;
        }

        public int getRegistercounts() {
            return registercounts;
        }

        public void setRegistercounts(int registercounts) {
            this.registercounts = registercounts;
        }
    }
}
