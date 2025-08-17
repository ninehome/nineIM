package com.watayouxiang.db.table;

import com.watayouxiang.db.converter.RolesConverter;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/03
 *     desc   : 当前用户
 * </pre>
 */
@Entity
public class CurrUserTable {
    /**
     * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg
     * avatarbig : /user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624.jpg
     * createtime : 2018-11-08 09:08:29
     * domain :
     * fdvalidtype : 1
     * id : 23436
     * invFlag : false
     * invitecode : 854135
     * ipInfo : {"area":"","city":"杭州市","country":"中国","operator":"联通","province":"浙江省"}
     * ipid : 164340
     * level : 1
     * loginname : watayouxiang@qq.com
     * mg : false
     * msgremindflag : 2
     * nick : wata
     * phone :
     * reghref : https://www.t-io.org
     * registertype : 1
     * remark : 王涛
     * roles : [2,6,7]
     * searchflag : 1
     * sex : 1
     * sign : wata主账号_(:з」∠)_
     * status : 1
     * thirdstatus : 1
     * updatetime : 2020-08-27 10:00:22
     * xx : 2
     */

    /**
     * 头像
     */
    private String avatar;
    /**
     * 大图
     */
    private String avatarbig;
    /**
     * 创建时间
     */
    private String createtime;
    private String domain;
    private int fdvalidtype;
    /**
     * 用户id
     */
    @Id
    private Long id;
    private boolean invFlag;
    /**
     * 邀请码
     */
    private String invitecode;
    /**
     * IP 地址
     * CurrUserTable # ipid 作为外键与 IpInfoTable 的主键相连
     */
    @ToOne(joinProperty = "ipid")
    private IpInfoTable ipInfo;
    /**
     * IP 的id
     */
    private long ipid;
    /**
     * 等级
     */
    private int level;
    /**
     * 登录名
     */
    private String loginname;
    private boolean mg;
    /**
     * 消息提醒开关：1：开启；2：不开启
     */
    private int msgremindflag;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 手机号
     */
    private String phone;
    private String email;
    private String reghref;
    private int registertype;
    /**
     * 备注
     */
    private String remark;
    private int searchflag;
    /**
     * 性别
     */
    private int sex;
    /**
     * 签名
     */
    private String sign;
    private int status;
    private int thirdstatus;
    /**
     * 更新时间
     */
    private String updatetime;
    private int xx;
    /**
     * 角色
     */
    @Convert(converter = RolesConverter.class, columnType = String.class)
    private List<String> roles;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1227603140)
    private transient CurrUserTableDao myDao;
    @Generated(hash = 490147112)
    public CurrUserTable(String avatar, String avatarbig, String createtime, String domain,
            int fdvalidtype, Long id, boolean invFlag, String invitecode, long ipid, int level,
            String loginname, boolean mg, int msgremindflag, String nick, String phone, String email,
            String reghref, int registertype, String remark, int searchflag, int sex, String sign,
            int status, int thirdstatus, String updatetime, int xx, List<String> roles) {
        this.avatar = avatar;
        this.avatarbig = avatarbig;
        this.createtime = createtime;
        this.domain = domain;
        this.fdvalidtype = fdvalidtype;
        this.id = id;
        this.invFlag = invFlag;
        this.invitecode = invitecode;
        this.ipid = ipid;
        this.level = level;
        this.loginname = loginname;
        this.mg = mg;
        this.msgremindflag = msgremindflag;
        this.nick = nick;
        this.phone = phone;
        this.email = email;
        this.reghref = reghref;
        this.registertype = registertype;
        this.remark = remark;
        this.searchflag = searchflag;
        this.sex = sex;
        this.sign = sign;
        this.status = status;
        this.thirdstatus = thirdstatus;
        this.updatetime = updatetime;
        this.xx = xx;
        this.roles = roles;
    }
    @Generated(hash = 292716561)
    public CurrUserTable() {
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatarbig() {
        return this.avatarbig;
    }
    public void setAvatarbig(String avatarbig) {
        this.avatarbig = avatarbig;
    }
    public String getCreatetime() {
        return this.createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getDomain() {
        return this.domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public int getFdvalidtype() {
        return this.fdvalidtype;
    }
    public void setFdvalidtype(int fdvalidtype) {
        this.fdvalidtype = fdvalidtype;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getInvFlag() {
        return this.invFlag;
    }
    public void setInvFlag(boolean invFlag) {
        this.invFlag = invFlag;
    }
    public String getInvitecode() {
        return this.invitecode;
    }
    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }
    public long getIpid() {
        return this.ipid;
    }
    public void setIpid(long ipid) {
        this.ipid = ipid;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getLoginname() {
        return this.loginname;
    }
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    public boolean getMg() {
        return this.mg;
    }
    public void setMg(boolean mg) {
        this.mg = mg;
    }
    public int getMsgremindflag() {
        return this.msgremindflag;
    }
    public void setMsgremindflag(int msgremindflag) {
        this.msgremindflag = msgremindflag;
    }
    public String getNick() {
        return this.nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getReghref() {
        return this.reghref;
    }
    public void setReghref(String reghref) {
        this.reghref = reghref;
    }
    public int getRegistertype() {
        return this.registertype;
    }
    public void setRegistertype(int registertype) {
        this.registertype = registertype;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public int getSearchflag() {
        return this.searchflag;
    }
    public void setSearchflag(int searchflag) {
        this.searchflag = searchflag;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getThirdstatus() {
        return this.thirdstatus;
    }
    public void setThirdstatus(int thirdstatus) {
        this.thirdstatus = thirdstatus;
    }
    public String getUpdatetime() {
        return this.updatetime;
    }
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public int getXx() {
        return this.xx;
    }
    public void setXx(int xx) {
        this.xx = xx;
    }
    public List<String> getRoles() {
        return this.roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    @Generated(hash = 17139818)
    private transient Long ipInfo__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 702126961)
    public IpInfoTable getIpInfo() {
        long __key = this.ipid;
        if (ipInfo__resolvedKey == null || !ipInfo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IpInfoTableDao targetDao = daoSession.getIpInfoTableDao();
            IpInfoTable ipInfoNew = targetDao.load(__key);
            synchronized (this) {
                ipInfo = ipInfoNew;
                ipInfo__resolvedKey = __key;
            }
        }
        return ipInfo;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 602292939)
    public void setIpInfo(@NotNull IpInfoTable ipInfo) {
        if (ipInfo == null) {
            throw new DaoException(
                    "To-one property 'ipid' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ipInfo = ipInfo;
            ipid = ipInfo.getId();
            ipInfo__resolvedKey = ipid;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1641498775)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCurrUserTableDao() : null;
    }
}
