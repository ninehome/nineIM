package com.watayouxiang.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 存红包状态
 */
@Entity
public class RedPacketStatusTable {
    @Id
    private Long id;

    private Long redId;

    private Long uid;

    private String redStatus;

    private String ext1;

    private String ext2;

    private String ext3;

    @Generated(hash = 553240085)
    public RedPacketStatusTable(Long id, Long redId, Long uid, String redStatus, String ext1, String ext2, String ext3) {
        this.id = id;
        this.redId = redId;
        this.uid = uid;
        this.redStatus = redStatus;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.ext3 = ext3;
    }

    @Generated(hash = 1138567604)
    public RedPacketStatusTable() {
    }

    public Long getRedId() {
        return redId;
    }

    public void setRedId(Long redId) {
        this.redId = redId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getRedStatus() {
        return redStatus;
    }

    public void setRedStatus(String redStatus) {
        this.redStatus = redStatus;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
