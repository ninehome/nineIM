package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/23
 *     desc   :
 * </pre>
 */
public class PhoneRegisterResp {

    /*
    json string:

    {"data":{"loginname":"18768177675","id":34928,"user":{"agreement":"on","avatar":"/avatar/tio/2020/05/09/14.jpg","avatarbig":"/avatar/tio/2020/05/09/14.jpg","code":"505109","createtime":"2020-12-23 17:27:26","id":34928,"invFlag":false,"invitecode":"bfkqz9","ipInfo":{"area":"","city":"杭州市","country":"中国","operator":"电信","province":"浙江省"},"ipid":1286200,"loginname":"18768177675","mg":false,"nick":"wata","phone":"18768177675","phonebindflag":1,"phonepwd":"a18c012c4b5540bfe536db3cde448c37","registertype":2,"status":1,"thirdstatus":1}},"ok":true}
     */

    /**
     * loginname : 18768177675
     * id : 34928
     * user : {"agreement":"on","avatar":"/avatar/tio/2020/05/09/14.jpg","avatarbig":"/avatar/tio/2020/05/09/14.jpg","code":"505109","createtime":"2020-12-23 17:27:26","id":34928,"invFlag":false,"invitecode":"bfkqz9","ipInfo":{"area":"","city":"杭州市","country":"中国","operator":"电信","province":"浙江省"},"ipid":1286200,"loginname":"18768177675","mg":false,"nick":"wata","phone":"18768177675","phonebindflag":1,"phonepwd":"a18c012c4b5540bfe536db3cde448c37","registertype":2,"status":1,"thirdstatus":1}
     */

    private String loginname;
    private int id;
    private UserBean user;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * agreement : on
         * avatar : /avatar/tio/2020/05/09/14.jpg
         * avatarbig : /avatar/tio/2020/05/09/14.jpg
         * code : 505109
         * createtime : 2020-12-23 17:27:26
         * id : 34928
         * invFlag : false
         * invitecode : bfkqz9
         * ipInfo : {"area":"","city":"杭州市","country":"中国","operator":"电信","province":"浙江省"}
         * ipid : 1286200
         * loginname : 18768177675
         * mg : false
         * nick : wata
         * phone : 18768177675
         * phonebindflag : 1
         * phonepwd : a18c012c4b5540bfe536db3cde448c37
         * registertype : 2
         * status : 1
         * thirdstatus : 1
         */

        private String agreement;
        private String avatar;
        private String avatarbig;
        private String code;
        private String createtime;
        private int id;
        private boolean invFlag;
        private String invitecode;
        private IpInfoBean ipInfo;
        private int ipid;
        private String loginname;
        private boolean mg;
        private String nick;
        private String phone;
        private int phonebindflag;
        private String phonepwd;
        private int registertype;
        private int status;
        private int thirdstatus;

        public String getAgreement() {
            return agreement;
        }

        public void setAgreement(String agreement) {
            this.agreement = agreement;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatarbig() {
            return avatarbig;
        }

        public void setAvatarbig(String avatarbig) {
            this.avatarbig = avatarbig;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isInvFlag() {
            return invFlag;
        }

        public void setInvFlag(boolean invFlag) {
            this.invFlag = invFlag;
        }

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public IpInfoBean getIpInfo() {
            return ipInfo;
        }

        public void setIpInfo(IpInfoBean ipInfo) {
            this.ipInfo = ipInfo;
        }

        public int getIpid() {
            return ipid;
        }

        public void setIpid(int ipid) {
            this.ipid = ipid;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public boolean isMg() {
            return mg;
        }

        public void setMg(boolean mg) {
            this.mg = mg;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPhonebindflag() {
            return phonebindflag;
        }

        public void setPhonebindflag(int phonebindflag) {
            this.phonebindflag = phonebindflag;
        }

        public String getPhonepwd() {
            return phonepwd;
        }

        public void setPhonepwd(String phonepwd) {
            this.phonepwd = phonepwd;
        }

        public int getRegistertype() {
            return registertype;
        }

        public void setRegistertype(int registertype) {
            this.registertype = registertype;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getThirdstatus() {
            return thirdstatus;
        }

        public void setThirdstatus(int thirdstatus) {
            this.thirdstatus = thirdstatus;
        }

        public static class IpInfoBean {
            /**
             * area :
             * city : 杭州市
             * country : 中国
             * operator : 电信
             * province : 浙江省
             */

            private String area;
            private String city;
            private String country;
            private String operator;
            private String province;

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }
        }
    }
}
