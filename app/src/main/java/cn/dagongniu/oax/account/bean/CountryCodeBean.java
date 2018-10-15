package cn.dagongniu.oax.account.bean;

import java.util.List;

public class CountryCodeBean {

    /**
     * code : 1
     * success : true
     * msg : null
     * data : [{"id":1,"cnName":"中国","enName":"China","shortName":"CN","code":"86"},{"id":2,"cnName":"中国香港","enName":"ChinaHongKong","shortName":"HK","code":"852"},{"id":3,"cnName":"泰国","enName":"Thailand","shortName":"TL","code":"66"},{"id":4,"cnName":"韩国","enName":"Korea","shortName":"ROK","code":"82"},{"id":5,"cnName":"中国澳门","enName":"ChinaMacau","shortName":"OMA","code":"853"},{"id":6,"cnName":"中国台湾","enName":"ChinaTaiwan","shortName":"TWN","code":"886"},{"id":7,"cnName":"美国","enName":"The United States","shortName":"US","code":"1"},{"id":8,"cnName":"日本","enName":"Japan","shortName":"JP","code":"81"},{"id":9,"cnName":"新加坡","enName":"Singapore","shortName":"SG","code":"65"},{"id":10,"cnName":"马来西亚","enName":"Malaysia","shortName":"MYS","code":"60"}]
     */

    private String code;
    private boolean success;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * cnName : 中国
         * enName : China
         * shortName : CN
         * code : 86
         */

        private int id;
        private String cnName;
        private String enName;
        private String shortName;
        private String code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
