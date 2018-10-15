package cn.dagongniu.oax.account.bean;



public class GoogleCodeBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"secret":"3BKPUQ3NXOBABCLU","QRBarcodeUrl":"otpauth://totp/674773032@qq.com%3Fsecret%3D3BKPUQ3NXOBABCLU%26issuer%3Doax.com"}
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
         * secret : 3BKPUQ3NXOBABCLU
         * QRBarcodeUrl : otpauth://totp/674773032@qq.com%3Fsecret%3D3BKPUQ3NXOBABCLU%26issuer%3Doax.com
         */

        private String secret;
        private String QRBarcodeUrl;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getQRBarcodeUrl() {
            return QRBarcodeUrl;
        }

        public void setQRBarcodeUrl(String QRBarcodeUrl) {
            this.QRBarcodeUrl = QRBarcodeUrl;
        }
    }
}
