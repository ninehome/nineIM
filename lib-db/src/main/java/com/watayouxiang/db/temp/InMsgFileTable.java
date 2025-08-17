package com.watayouxiang.db.temp;

import com.watayouxiang.imclient.model.body.wx.msg.FileIconType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFile;

import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 *     {@link InnerMsgFile}
 * </pre>
 */
public class InMsgFileTable {
    @Id
    private long id;
    /**
     * 文件
     * ext : xlsx
     * filename : APP1.0需求功能列表(1).xlsx
     * id : 1987
     * fileicontype : 4
     * session : 12134427822957880038000117760
     * size : 10751
     * uid : 23436
     * url : /wx/upload/file/22/9010/1119563/88097616/74541310984/97/180455/1235506568913625088.xlsx.zip
     */
    private String ext;
    /**
     * 文件icon类型
     * <p>
     * {@link FileIconType}
     */
    private int fileicontype;
    private String filename;
    private String session;
    private long size;
    private int uid;
    private String url;

}
