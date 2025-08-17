package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc :
 */
public class InnerMsgFile {
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
    public String ext;
    /**
     * 文件icon类型
     * <p>
     * {@link FileIconType}
     */
    public int fileicontype;
    public String filename;
    public int id;
    public String session;
    public long size;
    public int uid;
    public String url;
}
