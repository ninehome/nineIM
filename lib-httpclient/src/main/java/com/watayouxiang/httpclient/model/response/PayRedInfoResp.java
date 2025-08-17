package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/25
 *     desc   :
 * </pre>
 */
public class PayRedInfoResp {

    /**
     * grablist : [{"amount":1,"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","bizcompletetime":"2020-11-25 14:52:38","id":38,"nick":"wata","serialnumber":"1331485708040413184","walletid":"6288883250000000103"}]
     * info : {"amount":2,"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bizcompletetime":"","bizcreattime":"2020-11-25 14:31:38","id":86,"mode":1,"nick":"貌比潘安","packetcount":2,"receivedamount":0,"receivedcount":1,"remark":"恭喜发财，大吉大利","serialnumber":"1331485678990663680","status":"SEND","uid":23440}
     */

    private InfoBean info;
    private List<GrablistBean> grablist;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<GrablistBean> getGrablist() {
        return grablist;
    }

    public void setGrablist(List<GrablistBean> grablist) {
        this.grablist = grablist;
    }

    public static class InfoBean {
        /**
         * amount : 2
         * avatar : /user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg
         * bizcompletetime :
         * bizcreattime : 2020-11-25 14:31:38
         * id : 86
         * mode : 1
         * chatmode : 2
         * nick : 貌比潘安
         * packetcount : 2
         * receivedamount : 0
         * receivedcount : 1
         * remark : 恭喜发财，大吉大利
         * serialnumber : 1331485678990663680
         * status : SEND
         * uid : 23440
         */

        /**
         * 红包总金额（分）
         */
        private int amount;
        private String avatar;
        /**
         * 抢完红包的时间
         */
        private String bizcompletetime;
        /**
         * 创建时间
         */
        private String bizcreattime;
        private int id;
        /**
         * 1 普通，2 手气红包
         */
        private int mode;
        /**
         * 1 私聊，2 群聊
         */
        private int chatmode;
        private String nick;
        /**
         * 红包个数
         */
        private int packetcount;
        /**
         * 已抢金额（分）
         */
        private int receivedamount;
        /**
         * 已抢红包个数
         */
        private int receivedcount;
        /**
         * 祝福语
         */
        private String remark;
        private String serialnumber;
        /**
         * 红包状态：
         * <p>
         * SUCCESS-已抢完;
         * TIMEOUT-24小时超时;
         * SEND-抢红包中
         */
        private String status;
        /**
         * 发红包人uid
         */
        private int uid;

        public int getChatmode() {
            return chatmode;
        }

        public void setChatmode(int chatmode) {
            this.chatmode = chatmode;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBizcompletetime() {
            return bizcompletetime;
        }

        public void setBizcompletetime(String bizcompletetime) {
            this.bizcompletetime = bizcompletetime;
        }

        public String getBizcreattime() {
            return bizcreattime;
        }

        public void setBizcreattime(String bizcreattime) {
            this.bizcreattime = bizcreattime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public int getPacketcount() {
            return packetcount;
        }

        public void setPacketcount(int packetcount) {
            this.packetcount = packetcount;
        }

        public int getReceivedamount() {
            return receivedamount;
        }

        public void setReceivedamount(int receivedamount) {
            this.receivedamount = receivedamount;
        }

        public int getReceivedcount() {
            return receivedcount;
        }

        public void setReceivedcount(int receivedcount) {
            this.receivedcount = receivedcount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }

    public static class GrablistBean {
        /**
         * amount : 1
         * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg
         * bizcompletetime : 2020-11-25 14:52:38
         * id : 38
         * uid : 23436
         * nick : wata
         * serialnumber : 1331485708040413184
         * walletid : 6288883250000000103
         */

        /**
         * 金额（分）
         */
        private int amount;
        private String avatar;
        /**
         * 领取的时间
         */
        private String bizcompletetime;
        private int id;
        private int uid;
        private String nick;
        private String serialnumber;
        private String walletid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBizcompletetime() {
            return bizcompletetime;
        }

        public void setBizcompletetime(String bizcompletetime) {
            this.bizcompletetime = bizcompletetime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getWalletid() {
            return walletid;
        }

        public void setWalletid(String walletid) {
            this.walletid = walletid;
        }
    }
}
