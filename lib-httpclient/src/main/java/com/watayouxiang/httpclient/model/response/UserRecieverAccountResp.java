package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-01-06
 * desc :
 */
public class UserRecieverAccountResp {

    private List<UserWithdrawAccount> data;

    public List<UserWithdrawAccount> getData() {
        return data;
    }

    public void setData(List<UserWithdrawAccount> data) {
        this.data = data;
    }

    public static class UserWithdrawAccount{
        private Integer id;
        private Integer uid;
        private String createtime;
        private String updatetime;
        private Integer accounttype;
        private String accountname;
        private String accountno;
        private String bankname;
        private String brandname;
        private String remark;
        private Integer delflag;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public Integer getAccounttype() {
            return accounttype;
        }

        public void setAccounttype(Integer accounttype) {
            this.accounttype = accounttype;
        }

        public String getAccountname() {
            return accountname;
        }

        public void setAccountname(String accountname) {
            this.accountname = accountname;
        }

        public String getAccountno() {
            return accountno;
        }

        public void setAccountno(String accountno) {
            this.accountno = accountno;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Integer getDelflag() {
            return delflag;
        }

        public void setDelflag(Integer delflag) {
            this.delflag = delflag;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }
    }
}
