package cn.dagongniu.oax.main.bean;

import java.util.List;

public class BannerBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : [{"title":"标题","type":2,"cnImage":null,"enImage":null,"url":"www.oax.com","status":null,"createTime":null,"updateTime":null,"adminId":null,"sortNo":null,"image":"www.baidu.com"},{"title":"标题","type":2,"cnImage":null,"enImage":null,"url":"www.oax.com","status":null,"createTime":null,"updateTime":null,"adminId":null,"sortNo":null,"image":"www.baidu.com"},{"title":"标题","type":2,"cnImage":null,"enImage":null,"url":"www.oax.com","status":null,"createTime":null,"updateTime":null,"adminId":null,"sortNo":null,"image":"www.baidu.com"},{"title":"标题","type":2,"cnImage":null,"enImage":null,"url":"www.oax.com","status":null,"createTime":null,"updateTime":null,"adminId":null,"sortNo":null,"image":"www.baidu.com"},{"title":"标题","type":2,"cnImage":null,"enImage":null,"url":"www.oax.com","status":null,"createTime":null,"updateTime":null,"adminId":null,"sortNo":null,"image":"www.baidu.com"}]
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
         * title : 标题
         * type : 2
         * cnImage : null
         * enImage : null
         * url : www.oax.com
         * status : null
         * createTime : null
         * updateTime : null
         * adminId : null
         * sortNo : null
         * image : www.baidu.com
         */

        private String title; //标题
        private int type;
        private String cnImage;
        private String enImage;
        private String url; //链接
        private String status; //1未发布 2已发布
        private String createTime;
        private String updateTime;
        private String adminId;
        private String sortNo;
        private String image; //图片地址

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCnImage() {
            return cnImage;
        }

        public void setCnImage(String cnImage) {
            this.cnImage = cnImage;
        }

        public String getEnImage() {
            return enImage;
        }

        public void setEnImage(String enImage) {
            this.enImage = enImage;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getSortNo() {
            return sortNo;
        }

        public void setSortNo(String sortNo) {
            this.sortNo = sortNo;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
