package com.watayouxiang.db.table;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 存红包状态
 */
@Entity
public class CacheTable {
    @Id
    private Long id;

    private Long uid;

    private String cacheKey;

    private String cacheValue;

    @Generated(hash = 470157035)
    public CacheTable(Long id, Long uid, String cacheKey, String cacheValue) {
        this.id = id;
        this.uid = uid;
        this.cacheKey = cacheKey;
        this.cacheValue = cacheValue;
    }

    @Generated(hash = 19984151)
    public CacheTable() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheValue() {
        return this.cacheValue;
    }

    public void setCacheValue(String cacheValue) {
        this.cacheValue = cacheValue;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
