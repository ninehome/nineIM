package com.watayouxiang.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 存转账状态
 */
@Entity
public class TransAmountStatusTable {
    @Id
    private Long id;

    private Long transId;

    private Long uid;

    private Integer transStatus;

    private String ext1;

    private String ext2;

    private String ext3;

    @Generated(hash = 93893249)
    public TransAmountStatusTable(Long id, Long transId, Long uid,
            Integer transStatus, String ext1, String ext2, String ext3) {
        this.id = id;
        this.transId = transId;
        this.uid = uid;
        this.transStatus = transStatus;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.ext3 = ext3;
    }

    @Generated(hash = 2091624247)
    public TransAmountStatusTable() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransId() {
        return this.transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getTransStatus() {
        return this.transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }

    public String getExt1() {
        return this.ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return this.ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return this.ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
}
