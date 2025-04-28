package com.watayouxiang.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/01
 *     desc   : 焦点（表）
 * </pre>
 */
@Entity
public class FocusTable {
    /**
     * 会话id
     */
    @Id
    private String chatLinkId;
    /**
     * 操作码
     * <p>
     * 1 需要更新数据
     * 2 不需要更新数据
     */
    private String code;
    @Generated(hash = 661056753)
    public FocusTable(String chatLinkId, String code) {
        this.chatLinkId = chatLinkId;
        this.code = code;
    }
    @Generated(hash = 1124014863)
    public FocusTable() {
    }
    public String getChatLinkId() {
        return this.chatLinkId;
    }
    public void setChatLinkId(String chatLinkId) {
        this.chatLinkId = chatLinkId;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
