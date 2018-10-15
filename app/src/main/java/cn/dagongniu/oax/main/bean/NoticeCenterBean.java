package cn.dagongniu.oax.main.bean;

import java.util.List;

public class NoticeCenterBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"list":[{"id":4,"name":"中文标题","type":5,"releaseTime":"2018-06-11 14:59:43"},{"id":20,"name":"中文标题","type":6,"releaseTime":"2018-06-22 15:15:32"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 4
             * name : 中文标题
             * type : 5
             * releaseTime : 2018-06-11 14:59:43
             */

            private int id;
            private String name;
            private int type;
            private String releaseTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }
        }
    }
}
