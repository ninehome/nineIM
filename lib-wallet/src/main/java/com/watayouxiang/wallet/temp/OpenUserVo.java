/*
 * 使用本软件请从杭州钛特云有限公司获取授权，其它途径获取本软件的行为皆为侵权行为
 * grantinfo
 */
package com.watayouxiang.wallet.temp;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 开户请求Vo
 *
 * @author xufei
 * 2020年11月2日 下午7:07:44
 */
public class OpenUserVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4990253834557702763L;

    /**
     * uid-Y
     */
    private Integer uid;

    /**
     * 姓名-Y
     */
    private String name;


    /**
     * 证件号码-Y
     */
    private String cardno;

    /**
     * 手机号-Y
     */
    private String mobile;

    /**
     * mac地址-S
     */
    private String mac;

    /**
     * nickName-S
     */
    private String nickName;

    /**
     * 职业-N
     */
    private String profession = "A";

    /**
     * 证件类型:默认-IDCARD-N
     */
    private String cardtype = "IDCARD";

    /**
     * ip-N
     */
    private String ip;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return TextUtils.isEmpty(mac) ? "0.0.0.0" : mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
