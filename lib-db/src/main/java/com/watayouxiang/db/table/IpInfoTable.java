package com.watayouxiang.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/03
 *     desc   : IP
 * </pre>
 */
@Entity
public class IpInfoTable {
    /**
     * area :
     * city : 杭州市
     * country : 中国
     * operator : 联通
     * province : 浙江省
     */

    @Id
    private Long id;
    private String area;
    private String city;
    private String country;
    private String operator;
    private String province;

    @Generated(hash = 861733648)
    public IpInfoTable(Long id, String area, String city, String country,
            String operator, String province) {
        this.id = id;
        this.area = area;
        this.city = city;
        this.country = country;
        this.operator = operator;
        this.province = province;
    }
    @Generated(hash = 1221112754)
    public IpInfoTable() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getOperator() {
        return this.operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
}
