package cn.dagongniu.oax.account.bean;

import java.io.Serializable;
import java.util.List;

public class UserCenterBean implements Serializable {


    /**
     * code : 1
     * success : true
     * msg : null
     * data : {"userLoginLogList":[{"id":14,"userId":29,"ip":"183.16.194.115","address":"中国 广东 深圳","source":3,"loginTime":1529663691000},{"id":15,"userId":29,"ip":"183.16.194.115","address":"中国 广东 深圳","source":3,"loginTime":1529663716000},{"id":48,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529923731000},{"id":49,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529923774000},{"id":50,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529925860000},{"id":51,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529926739000},{"id":52,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529927278000},{"id":53,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529927317000},{"id":54,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930144000},{"id":55,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930355000},{"id":58,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930958000},{"id":59,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930979000},{"id":60,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529931000000},{"id":62,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529931107000},{"id":63,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529931164000},{"id":66,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529935341000},{"id":67,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529977701000},{"id":72,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529979785000},{"id":79,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529981217000},{"id":82,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983059000},{"id":83,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983178000},{"id":89,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983482000},{"id":91,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983878000},{"id":187,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1530019877000},{"id":188,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1530020249000},{"id":218,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530094131000},{"id":219,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530094491000},{"id":220,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530094596000},{"id":221,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530094698000},{"id":224,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530096519000},{"id":226,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530096660000},{"id":228,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530097141000},{"id":229,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530097695000},{"id":231,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530098372000},{"id":234,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530101351000},{"id":237,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530102326000},{"id":238,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530102531000},{"id":240,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530106768000},{"id":241,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530108226000},{"id":242,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530109117000},{"id":243,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530109610000},{"id":244,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530110085000},{"id":246,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530151030000},{"id":251,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530153856000},{"id":259,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530154312000},{"id":260,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530154344000},{"id":262,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530154544000},{"id":263,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530156067000},{"id":266,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530167176000},{"id":270,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530178256000},{"id":272,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530186612000},{"id":274,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":2,"loginTime":1530188133000},{"id":278,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530194028000}],"usercenter":{"id":29,"phone":null,"email":"674773032@qq.com","needTransactionPassword":null,"checkStatus":0,"idName":null,"idNo":null,"code":"7NuOECnt","level":1,"emailStatus":1,"phoneStatus":0,"googleStatus":0,"registerCounts":0,"vertyCounts":0,"googleKey":null,"transactionPasswordIsNull":1}}
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

    public static class DataBean implements Serializable {
        /**
         * userLoginLogList : [{"id":14,"userId":29,"ip":"183.16.194.115","address":"中国 广东 深圳","source":3,"loginTime":1529663691000},{"id":15,"userId":29,"ip":"183.16.194.115","address":"中国 广东 深圳","source":3,"loginTime":1529663716000},{"id":48,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529923731000},{"id":49,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529923774000},{"id":50,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529925860000},{"id":51,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529926739000},{"id":52,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529927278000},{"id":53,"userId":29,"ip":"183.16.193.111","address":"中国 广东 深圳","source":3,"loginTime":1529927317000},{"id":54,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930144000},{"id":55,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930355000},{"id":58,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930958000},{"id":59,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529930979000},{"id":60,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529931000000},{"id":62,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529931107000},{"id":63,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529931164000},{"id":66,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529935341000},{"id":67,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529977701000},{"id":72,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529979785000},{"id":79,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529981217000},{"id":82,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983059000},{"id":83,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983178000},{"id":89,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983482000},{"id":91,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1529983878000},{"id":187,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1530019877000},{"id":188,"userId":29,"ip":"183.16.188.134","address":"中国 广东 深圳","source":3,"loginTime":1530020249000},{"id":218,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530094131000},{"id":219,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530094491000},{"id":220,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530094596000},{"id":221,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530094698000},{"id":224,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530096519000},{"id":226,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530096660000},{"id":228,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530097141000},{"id":229,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530097695000},{"id":231,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530098372000},{"id":234,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530101351000},{"id":237,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530102326000},{"id":238,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530102531000},{"id":240,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530106768000},{"id":241,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530108226000},{"id":242,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530109117000},{"id":243,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530109610000},{"id":244,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530110085000},{"id":246,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530151030000},{"id":251,"userId":29,"ip":"104.129.181.59","address":"加拿大 不列颠哥伦比亚 温哥华","source":2,"loginTime":1530153856000},{"id":259,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530154312000},{"id":260,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":1,"loginTime":1530154344000},{"id":262,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530154544000},{"id":263,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530156067000},{"id":266,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530167176000},{"id":270,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530178256000},{"id":272,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530186612000},{"id":274,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":2,"loginTime":1530188133000},{"id":278,"userId":29,"ip":"183.16.193.115","address":"中国 广东 深圳","source":3,"loginTime":1530194028000}]
         * usercenter : {"id":29,"phone":null,"email":"674773032@qq.com","needTransactionPassword":null,"checkStatus":0,"idName":null,"idNo":null,"code":"7NuOECnt","level":1,"emailStatus":1,"phoneStatus":0,"googleStatus":0,"registerCounts":0,"vertyCounts":0,"googleKey":null,"transactionPasswordIsNull":1}
         */

        private String LEVEL3_BTC;
        private String LEVEL2_BTC;
        private String LEVEL1_BTC;

        private UsercenterBean usercenter;
        private List<UserLoginLogListBean> userLoginLogList;

        public UsercenterBean getUsercenter() {
            return usercenter;
        }

        public void setUsercenter(UsercenterBean usercenter) {
            this.usercenter = usercenter;
        }

        public List<UserLoginLogListBean> getUserLoginLogList() {
            return userLoginLogList;
        }

        public void setUserLoginLogList(List<UserLoginLogListBean> userLoginLogList) {
            this.userLoginLogList = userLoginLogList;
        }

        public void setLEVEL3_BTC(String LEVEL3_BTC) {
            this.LEVEL3_BTC = LEVEL3_BTC;
        }

        public void setLEVEL2_BTC(String LEVEL2_BTC) {
            this.LEVEL2_BTC = LEVEL2_BTC;
        }

        public void setLEVEL1_BTC(String LEVEL1_BTC) {
            this.LEVEL1_BTC = LEVEL1_BTC;
        }

        public String getLEVEL3_BTC() {
            return LEVEL3_BTC;
        }

        public String getLEVEL2_BTC() {
            return LEVEL2_BTC;
        }

        public String getLEVEL1_BTC() {
            return LEVEL1_BTC;
        }

        public static class UsercenterBean implements Serializable {
            /**
             * id : 29
             * phone : null
             * email : 674773032@qq.com
             * needTransactionPassword : null
             * checkStatus : 0
             * idName : null
             * idNo : null
             * code : 7NuOECnt
             * level : 1
             * emailStatus : 1
             * phoneStatus : 0
             * googleStatus : 0
             * registerCounts : 0
             * vertyCounts : 0
             * googleKey : null
             * transactionPasswordIsNull : 1
             */

            private int id;
            private String phone;
            private String email;
            private String needTransactionPassword;
            private int checkStatus;//checkStatus -1未通过 0未认证 1待审核 2审核通过
            private String idName;
            private String idNo;
            private String code;
            private int level;
            private int emailStatus;
            private int phoneStatus;
            private int googleStatus;
            private int registerCounts;
            private int vertyCounts;
            private String googleKey;
            private int transactionPasswordIsNull;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getNeedTransactionPassword() {
                return needTransactionPassword;
            }

            public void setNeedTransactionPassword(String needTransactionPassword) {
                this.needTransactionPassword = needTransactionPassword;
            }

            public int getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(int checkStatus) {
                this.checkStatus = checkStatus;
            }

            public String getIdName() {
                return idName;
            }

            public void setIdName(String idName) {
                this.idName = idName;
            }

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
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

            public int getGoogleStatus() {
                return googleStatus;
            }

            public void setGoogleStatus(int googleStatus) {
                this.googleStatus = googleStatus;
            }

            public int getRegisterCounts() {
                return registerCounts;
            }

            public void setRegisterCounts(int registerCounts) {
                this.registerCounts = registerCounts;
            }

            public int getVertyCounts() {
                return vertyCounts;
            }

            public void setVertyCounts(int vertyCounts) {
                this.vertyCounts = vertyCounts;
            }

            public String getGoogleKey() {
                return googleKey;
            }

            public void setGoogleKey(String googleKey) {
                this.googleKey = googleKey;
            }

            public int getTransactionPasswordIsNull() {
                return transactionPasswordIsNull;
            }

            public void setTransactionPasswordIsNull(int transactionPasswordIsNull) {
                this.transactionPasswordIsNull = transactionPasswordIsNull;
            }
        }

        public static class UserLoginLogListBean implements Serializable {
            /**
             * id : 14
             * userId : 29
             * ip : 183.16.194.115
             * address : 中国 广东 深圳
             * source : 3
             * loginTime : 1529663691000
             */

            private int id;
            private int userId;
            private String ip;
            private String address;
            private int source;
            private long loginTime;

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

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public long getLoginTime() {
                return loginTime;
            }

            public void setLoginTime(long loginTime) {
                this.loginTime = loginTime;
            }
        }
    }
}
