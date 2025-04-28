package com.watayouxiang.httpclient.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/26
 *     desc   :
 * </pre>
 */
public class PayGrabRedPacketListResp {
    /**
     * firstPage : true
     * lastPage : true
     * list : [{"walletid":"6288883250000000103","chatmode":1,"remark":"","senduid":23436,"localerrormsg":"更改消息会话错误-tosend-抢红包","reqid":"13311465518877982721874603691758","sendredstatus":"SUCCESS","mode":2,"nick":"wata","uid":23436,"id":23,"bizcompletetime":"2020-11-24 17:41:57","chatbizid":"22627","amount":1,"createtime":"2020-11-24 17:41:57","sendid":53,"serialnumber":"1331146573522026496","ip":"60.177.221.26","sendserialnumber":"1331146551887798272","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-24 17:41:57","device":2,"queuetime":"2020-11-24 17:41:57","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"remark":"","senduid":23436,"localerrormsg":"更改消息会话错误-tosend-抢红包","reqid":"13311704194195210241155331656076","sendredstatus":"SUCCESS","mode":2,"nick":"wata","uid":23436,"id":24,"bizcompletetime":"2020-11-24 17:44:29","chatbizid":"22627","amount":1,"createtime":"2020-11-24 17:44:29","sendid":63,"serialnumber":"1331170452441280512","ip":"60.177.221.26","sendserialnumber":"1331170419419521024","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-24 17:44:29","device":2,"queuetime":"2020-11-24 17:44:29","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"remark":"","senduid":23436,"localerrormsg":"更改消息会话错误-tosend-抢红包","reqid":"13311728989242736641469695041724","sendredstatus":"SUCCESS","mode":2,"nick":"wata","uid":23436,"id":25,"bizcompletetime":"2020-11-24 17:48:56","chatbizid":"22627","amount":1,"createtime":"2020-11-24 17:48:56","sendid":73,"serialnumber":"1331172929320402944","ip":"60.177.221.26","sendserialnumber":"1331172898924273664","appversion":"2.1.10","avatar":"/user/avatar/22/90 10/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-24 17:48:56","device":2,"queuetime":"2020-11-24 17:48:56","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"remark":"","senduid":23436,"localerrormsg":"更改消息会话错误-tosend-抢红包","reqid":"13311739279422341121218520449190","sendredstatus":"SUCCESS","mode":2,"nick":"wata","uid":23436,"id":26,"bizcompletetime":"2020-11-24 17:54:05","chatbizid":"22627","amount":1,"createtime":"2020-11-24 17:54:05","sendid":74,"serialnumber":"1331174002718281728","ip":"60.177.221.26","sendserialnumber":"1331173927942234112","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-24 17:54:06","device":2,"queuetime":"2020-11-24 17:54:06","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"remark":"","senduid":23440,"localerrormsg":"","reqid":"13311832479709347841809787128330","sendredstatus":"SUCCESS","mode":2,"nick":"貌比潘安","uid":23436,"id":31,"bizcompletetime":"2020-11-24 18:33:40","chatbizid":"362","amount":1,"createtime":"2020-11-24 18:33:40","sendid":81,"serialnumber":"1331183357136089090","ip":"60.177.221.26","sendserialnumber":"1331183247970934784","appversion":"2.1.6","avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288884780000001168","updatetime":"2020-11-24 18:33:40","device":3,"queuetime":"2020-11-24 18:33:40","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"remark":"","senduid":23436,"localerrormsg":"","reqid":"13311881102262353921736909604171","sendredstatus":"SUCCESS","mode":2,"nick":"wata","uid":23436,"id":33,"bizcompletetime":"2020-11-25 10:46:41","chatbizid":"362","amount":1,"createtime":"2020-11-25 10:46:41","sendid":83,"serialnumber":"1331188156057391104","ip":"60.177.221.26","sendserialnumber":"1331188110226235392","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-25 10:46:41","device":2,"queuetime":"2020-11-25 10:46:41","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"remark":"","senduid":23436,"localerrormsg":"","reqid":"13311850844553297921967799172029","sendredstatus":"SUCCESS","mode":2,"nick":"wata","uid":23436,"id":34,"bizcompletetime":"2020-11-25 11:11:16","chatbizid":"362","amount":1,"createtime":"2020-11-25 11:11:16","sendid":82,"serialnumber":"1331185108237029377","ip":"60.177.221.26","sendserialnumber":"1331185084455329792","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-25 11:11:16","device":2,"queuetime":"2020-11-25 11:11:16","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"remark":"","senduid":23440,"localerrormsg":"","reqid":"13314384656189440001700710120037","sendredstatus":"SEND","mode":1,"nick":"貌比潘安","uid":23436,"id":37,"bizcompletetime":"2020-11-25 14:52:34","chatbizid":"362","amount":1,"createtime":"2020-11-25 14:52:34","sendid":85,"serialnumber":"1331438496077979649","ip":"183.156.73.201","sendserialnumber":"1331438465618944000","appversion":"2.1.10","avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288884780000001168","updatetime":"2020-11-25 14:52:34","device":2,"queuetime":"2020-11-25 14:52:34","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"remark":"","senduid":23440,"localerrormsg":"","reqid":"13314856789906636801149527568716","sendredstatus":"SEND","mode":1,"nic k":"貌比潘安","uid":23436,"id":38,"bizcompletetime":"2020-11-25 14:52:38","chatbizid":"362","amount":1,"createtime":"2020-11-25 14:52:38","sendid":86,"serialnumber":"1331485708040413184","ip":"183.156.73.201","sendserialnumber":"1331485678990663680","appversion":"2.1.10","avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288884780000001168","updatetime":"2020-11-25 14:52:38","device":2,"queuetime":"2020-11-25 14:52:38","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"remark":"","senduid":23436,"localerrormsg":"","reqid":"13315405358396907521947099792205","sendredstatus":"SEND","mode":1,"nick":"wata","uid":23436,"id":39,"bizcompletetime":"2020-11-25 18:10:05","chatbizid":"362","amount":1,"createtime":"2020-11-25 18:10:05","sendid":88,"serialnumber":"1331540573353549824","ip":"183.156.73.201","sendserialnumber":"1331540535839690752","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-25 18:10:05","device":2,"queuetime":"2020-11-25 18:10:05","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"remark":"","senduid":23436,"localerrormsg":"","reqid":"13315403355826503681624972070575","sendredstatus":"SEND","mode":2,"nick":"wata","uid":23436,"id":40,"bizcompletetime":"2020-11-25 18:10:15","chatbizid":"362","amount":1,"createtime":"2020-11-25 18:10:15","sendid":87,"serialnumber":"1331540409268191232","ip":"183.156.73.201","sendserialnumber":"1331540335582650368","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-25 18:10:15","device":2,"queuetime":"2020-11-25 18:10:15","status":"SUCCESS"},{"walletid":"6288883250000000103","chat mode":2,"remark":"","senduid":23436,"localerrormsg":"","reqid":"13317906409457049601419574032497","sendredstatus":"SUCCESS","mode":2,"nick":"wata","uid":23436,"id":41,"bizcompletetime":"2020-11-26 10:43:39","chatbizid":"362","amount":1,"createtime":"2020-11-26 10:43:39","sendid":95,"serialnumber":"1331790681735303168","ip":"183.156.73.201","sendserialnumber":"1331790640945704960","appversion":"2.1.10","avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","ordererrormsg":"","bizid":"890000595","coinsyn":2,"sendwalletid":"6288883250000000103","updatetime":"2020-11-26 10:43:39","device":2,"queuetime":"2020-11-26 10:43:39","status":"SUCCESS"}]
     * pageNumber : 1
     * pageSize : 16
     * totalPage : 1
     * totalRow : 12
     */

    private boolean firstPage;
    private boolean lastPage;
    private int pageNumber;
    private int pageSize;
    private int totalPage;
    private int totalRow;
    private List<ListBean> list;

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String createtime;
        private String remark;
        private String avatar;
        private Integer type;
        /**
         * 类型 1：单人红包 21：群普通红包 22：群拼手气红包
         */
        private Integer mode;
        private String nick;
        private Integer uid;
        private Integer money;
        private Integer redid;
        private Integer id;
        private String updatetime;
        /**
         * 红包状态 2-抢红包中 3-已抢完 4-过期红包
         */
        private Integer status;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getMode() {
            return mode;
        }

        public void setMode(Integer mode) {
            this.mode = mode;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }

        public Integer getRedid() {
            return redid;
        }

        public void setRedid(Integer redid) {
            this.redid = redid;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        //        private String walletid;
//        private int chatmode;
//        private String remark;
//        private int senduid;
//        private String localerrormsg;
//        private String reqid;
//        private String sendredstatus;
//        private int mode;
//        private String nick;
//        private int uid;
//        private int id;
//        private String bizcompletetime;
//        private String chatbizid;
//        private int amount;
//        private String createtime;
//        private int sendid;
//        private String serialnumber;
//        private String ip;
//        private String sendserialnumber;
//        private String appversion;
//        private String avatar;
//        private String ordererrormsg;
//        private String bizid;
//        private int coinsyn;
//        private String sendwalletid;
//        private String updatetime;
//        private int device;
//        private String queuetime;
//        private String status;
//        @SerializedName("nic k")
//        private String _$NicK246;// FIXME check this code
//        @SerializedName("chat mode")
//        private int _$ChatMode182;// FIXME check this code
//
//        public String getWalletid() {
//            return walletid;
//        }
//
//        public void setWalletid(String walletid) {
//            this.walletid = walletid;
//        }
//
//        public int getChatmode() {
//            return chatmode;
//        }
//
//        public void setChatmode(int chatmode) {
//            this.chatmode = chatmode;
//        }
//
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//
//        public int getSenduid() {
//            return senduid;
//        }
//
//        public void setSenduid(int senduid) {
//            this.senduid = senduid;
//        }
//
//        public String getLocalerrormsg() {
//            return localerrormsg;
//        }
//
//        public void setLocalerrormsg(String localerrormsg) {
//            this.localerrormsg = localerrormsg;
//        }
//
//        public String getReqid() {
//            return reqid;
//        }
//
//        public void setReqid(String reqid) {
//            this.reqid = reqid;
//        }
//
//        public String getSendredstatus() {
//            return sendredstatus;
//        }
//
//        public void setSendredstatus(String sendredstatus) {
//            this.sendredstatus = sendredstatus;
//        }
//
//        public int getMode() {
//            return mode;
//        }
//
//        public void setMode(int mode) {
//            this.mode = mode;
//        }
//
//        public String getNick() {
//            return nick;
//        }
//
//        public void setNick(String nick) {
//            this.nick = nick;
//        }
//
//        public int getUid() {
//            return uid;
//        }
//
//        public void setUid(int uid) {
//            this.uid = uid;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getBizcompletetime() {
//            return bizcompletetime;
//        }
//
//        public void setBizcompletetime(String bizcompletetime) {
//            this.bizcompletetime = bizcompletetime;
//        }
//
//        public String getChatbizid() {
//            return chatbizid;
//        }
//
//        public void setChatbizid(String chatbizid) {
//            this.chatbizid = chatbizid;
//        }
//
//        public int getAmount() {
//            return amount;
//        }
//
//        public void setAmount(int amount) {
//            this.amount = amount;
//        }
//
//        public String getCreatetime() {
//            return createtime;
//        }
//
//        public void setCreatetime(String createtime) {
//            this.createtime = createtime;
//        }
//
//        public int getSendid() {
//            return sendid;
//        }
//
//        public void setSendid(int sendid) {
//            this.sendid = sendid;
//        }
//
//        public String getSerialnumber() {
//            return serialnumber;
//        }
//
//        public void setSerialnumber(String serialnumber) {
//            this.serialnumber = serialnumber;
//        }
//
//        public String getIp() {
//            return ip;
//        }
//
//        public void setIp(String ip) {
//            this.ip = ip;
//        }
//
//        public String getSendserialnumber() {
//            return sendserialnumber;
//        }
//
//        public void setSendserialnumber(String sendserialnumber) {
//            this.sendserialnumber = sendserialnumber;
//        }
//
//        public String getAppversion() {
//            return appversion;
//        }
//
//        public void setAppversion(String appversion) {
//            this.appversion = appversion;
//        }
//
//        public String getAvatar() {
//            return avatar;
//        }
//
//        public void setAvatar(String avatar) {
//            this.avatar = avatar;
//        }
//
//        public String getOrdererrormsg() {
//            return ordererrormsg;
//        }
//
//        public void setOrdererrormsg(String ordererrormsg) {
//            this.ordererrormsg = ordererrormsg;
//        }
//
//        public String getBizid() {
//            return bizid;
//        }
//
//        public void setBizid(String bizid) {
//            this.bizid = bizid;
//        }
//
//        public int getCoinsyn() {
//            return coinsyn;
//        }
//
//        public void setCoinsyn(int coinsyn) {
//            this.coinsyn = coinsyn;
//        }
//
//        public String getSendwalletid() {
//            return sendwalletid;
//        }
//
//        public void setSendwalletid(String sendwalletid) {
//            this.sendwalletid = sendwalletid;
//        }
//
//        public String getUpdatetime() {
//            return updatetime;
//        }
//
//        public void setUpdatetime(String updatetime) {
//            this.updatetime = updatetime;
//        }
//
//        public int getDevice() {
//            return device;
//        }
//
//        public void setDevice(int device) {
//            this.device = device;
//        }
//
//        public String getQueuetime() {
//            return queuetime;
//        }
//
//        public void setQueuetime(String queuetime) {
//            this.queuetime = queuetime;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public String get_$NicK246() {
//            return _$NicK246;
//        }
//
//        public void set_$NicK246(String _$NicK246) {
//            this._$NicK246 = _$NicK246;
//        }
//
//        public int get_$ChatMode182() {
//            return _$ChatMode182;
//        }
//
//        public void set_$ChatMode182(int _$ChatMode182) {
//            this._$ChatMode182 = _$ChatMode182;
//        }
    }
}
