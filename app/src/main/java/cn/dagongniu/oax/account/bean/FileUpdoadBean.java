package cn.dagongniu.oax.account.bean;

import java.util.List;

public class FileUpdoadBean {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : ["7f6b49e0ba9545c7b4a48b4352485595.png"]
     */

    private String code;
    private boolean success;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
