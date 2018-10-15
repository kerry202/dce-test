package cn.dagongniu.oax.account.bean;

public class IdentityResultBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"reason":"审核未通过:上传错误正面照不清晰","checkStatus":-1}
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
         * reason : 审核未通过:上传错误正面照不清晰
         * checkStatus : -1
         */

        private String reason;
        private int checkStatus;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }
    }
}
