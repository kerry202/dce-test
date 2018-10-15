package cn.dagongniu.oax.account.bean;

public class HelpDetailInfoBean {

    /**
     * id : 1
     * content : <!DOCTYPE html>
     * <html>
     * <head>
     * </head>
     * <body>
     * <p>中文内容1231</p>
     * </body>
     * </html>
     * releaseTime : 2018-06-08 13:18:53
     */

    private int id;
    private String content;
    private String releaseTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }
}
