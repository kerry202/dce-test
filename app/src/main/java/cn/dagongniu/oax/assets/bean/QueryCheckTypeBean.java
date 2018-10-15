package cn.dagongniu.oax.assets.bean;

public class QueryCheckTypeBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"googleStatus":0,"emailStatus":1,"phoneStatus":0}
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
         * googleStatus : 0
         * emailStatus : 1
         * phoneStatus : 0
         */

        private int googleStatus;
        private int emailStatus;
        private int phoneStatus;

        public int getGoogleStatus() {
            return googleStatus;
        }

        public void setGoogleStatus(int googleStatus) {
            this.googleStatus = googleStatus;
        }

        public int getEmailStatus() {
            return emailStatus;
        }

        public void setEmailStatus(int emailStatus) {
            this.emailStatus = emailStatus;
        }

        public int getPhoneStatus() {
            return phoneStatus;
        }

        public void setPhoneStatus(int phoneStatus) {
            this.phoneStatus = phoneStatus;
        }
    }
}
