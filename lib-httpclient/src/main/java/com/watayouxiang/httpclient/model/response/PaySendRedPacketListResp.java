package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/26
 *     desc   :
 * </pre>
 */
public class PaySendRedPacketListResp {

    /**
     * firstPage : true
     * lastPage : true
     * list : [{"walletid":"6288883250000000103","chatmode":1,"remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201124154303038169451261334955","mode":2,"uid":23436,"receivedcount":0,"currency":"CNY","refundamount":1,"id":48,"bizcreattime":"2020-11-24 15:43:03","bizcompletetime":"","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-24 15:43:03","refundtype":"BALANCE","serialnumber":"1331141263105331200","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124053361515915259857715200","refundcount":1,"receivedamount":0,"bizid":"890000595","coinsyn":2,"debitdatetime":"","updatetime":"2020-11-25 15:43:10","device":2,"paymenttype":"","queuetime":"2020-11-25 15:43:10","status":"TIMEOUT"},{"walletid":"6288883250000000103","chatmode":1,"remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201124155441486159570425919363","mode":2,"uid":23436,"receivedcount":0,"currency":"CNY","refundamount":1,"id":51,"bizcreattime":"2020-11-24 15:54:41","bizcompletetime":"","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-24 15:54:41","refundtype":"BALANCE","serialnumber":"1331144192356913152","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124461494515918189042188288","refundcount":1,"receivedamount":0,"bizid":"890000595","coinsyn":2,"debitdatetime":"","updatetime":"2020-11-25 15:54:48","device":2,"paymenttype":"","queuetime":"2020-11-25 15:54:48","status":"TIMEOUT"},{"walletid":"6288883250000000103","chatmode":1,"mgsid":"390756","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201124160403992101315518417935","mode":2,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":53,"bizcreattime":"2020-11-24 16:04:04","bizcompletetime":"2020-11-24 17:41:58","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-24 16:04:04","refundtype":"","serialnumber":"1331146551887798272","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124691040515920548665348096","refundcount":0,"receivedamount":1,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-24 16:04:09","updatetime":"2020-11-24 17:41:58","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-24 17:41:58","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"mgsid":"390780","remark":"666","localerrormsg":"","timeout":1440,"reqid":"20201124173854168106617908521727","mode":2,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":63,"bizcreattime":"2020-11-24 17:38:54","bizcompletetime":"2020-11-24 17:44:29","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-24 17:38:54","refundtype":"","serialnumber":"1331170419419521024","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124518319515944416167710720","refundcount":0,"receivedamount":1,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-24 17:39:02","updatetime":"2020-11-24 17:44:29","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-24 17:44:29","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"mgsid":"390785","remark":"$$$","localerrormsg":"","timeout":1440,"reqid":"20201124174845583161876425869641","mode":2,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":73,"bizcreattime":"2020-11-24 17:48:45","bizcompletetime":"2020-11-24 17:48:57","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-24 17:48:45","refundtype":"","serialnumber":"1331172898924273664","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124054965515946895659876352","refundcount":0,"receivedamount":1,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-24 17:48:53","updatetime":"2020-11-24 17:48:56","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-24 17:48:56","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"mgsid":"390788","remark":"x","localerrormsg":"","timeout":1440,"reqid":"20201124175250982113104154795268","mode":2,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":74,"bizcreattime":"2020-11-24 17:52:51","bizcompletetime":"2020-11-24 17:54:06","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-24 17:52:51","refundtype":"","serialnumber":"1331173927942234112","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124446479515947924686229504","refundcount":0,"receivedamount":1,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-24 17:53:09","updatetime":"2020-11-24 17:54:06","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-24 17:54:06","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"mgsid":"390792","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201124175619773107476114707851","mode":2,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":75,"bizcreattime":"2020-11-24 17:56:19","bizcompletetime":"","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-24 17:56:20","refundtype":"","serialnumber":"1331174803842932736","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124292145515948800695975936","refundcount":0,"receivedamount":0,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-24 17:56:24","updatetime":"2020-11-24 17:56:27","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-24 17:56:27","status":"SEND"},{"walletid":"6288883250000000103","chatmode":2,"mgsid":"352033","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201124183710877190740699530319","mode":2,"uid":23436,"receivedcount":2,"currency":"CNY","refundamount":0,"id":82,"bizcreattime":"2020-11-24 18:37:11","bizcompletetime":"2020-11-25 11:11:16","chatbizid":"362","amount":2,"packetcount":1,"createtime":"2020-11-24 18:37:11","refundtype":"","serialnumber":"1331185084455329792","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124294064515959081241264128","refundcount":0,"receivedamount":2,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-24 18:37:17","updatetime":"2020-11-25 11:11:17","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-25 11:11:17","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":2,"mgsid":"352034","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201124184912260101647863933483","mode":2,"uid":23436,"receivedcount":2,"currency":"CNY","refundamount":0,"id":83,"bizcreattime":"2020-11-24 18:49:12","bizcompletetime":"2020-11-25 11:15:13","chatbizid":"362","amount":2,"packetcount":1,"createtime":"2020-11-24 18:49:12","refundtype":"","serialnumber":"1331188110226235392","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124512016515962106898927616","refundcount":0,"receivedamount":2,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-24 18:49:23","updatetime":"2020-11-25 11:15:12","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-25 11:15:12","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"mgsid":"390794","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201125104600293143166124329948","mode":1,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":84,"bizcreattime":"2020-11-25 10:46:00","bizcompletetime":"","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-25 10:46:01","refundtype":"","serialnumber":"1331428899149717504","ip":"60.177.221.26","appversion":"2.1.10","token":"20201125690082516202895948238848","refundcount":0,"receivedamount":0,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-25 10:46:08","updatetime":"2020-11-25 10:46:11","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-25 10:46:11","status":"SEND"},{"walletid":"6288883250000000103","chatmode":2,"mgsid":"352050","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201125180849324158594446838990","mode":2,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":87,"bizcreattime":"2020-11-25 18:08:49","bizcompletetime":"","chatbizid":"362","amount":3,"packetcount":3,"createtime":"2020-11-25 18:08:49","refundtype":"","serialnumber":"1331540335582650368","ip":"183.156.73.201","appversion":"2.1.10","token":"20201125440804516314332322451456","refundcount":0,"receivedamount":0,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-2 5 18:09:07","updatetime":"2020-11-25 18:09:07","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-25 18:09:07","status":"SEND"},{"walletid":"6288883250000000103","chatmode":2,"mgsid":"352051","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201125180937084164064847221929","mode":1,"uid":23436,"receivedcount":2,"currency":"CNY","refundamount":0,"id":88,"bizcreattime":"2020-11-25 18:09:37","bizcompletetime":"2020-11-26 11:42:16","chatbizid":"362","amount":2,"packetcount":2,"createtime":"2020-11-25 18:09:37","refundtype":"","serialnumber":"1331540535839690752","ip":"183.156.73.201","appversion":"2.1.10","token":"20201125564975516314532436885504","refundcount":0,"receivedamount":2,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-25 18:09:46","updatetime":"2020-11-26 11:42:16","device":2,"paymenttype":"BANK_CARD","queuetime":"2020-11-26 11:42:16","status":"SUCCESS"},{"walletid":"6288883250000000103","chatmode":1,"mgsid":"390795","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201125181030124195311076716097","mode":1,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":89,"bizcreattime":"2020-11-25 18:10:30","bizcompletetime":"","chatbizid":"22627","amount":1,"packetcount":1,"createtime":"2020-11-25 18:10:30","refundtype":"","serialnumber":"1331540758238470144","ip":"183.156.73.201","appversion":"2.1.10","token":"20201125190721516314754982461440","refundcount":0,"receivedamount":0,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-25 18:10:42","updatetime":"2020-11-25 18:10:42","device":2,"paymenttype":"BANK_CARD","queuetime":"2020-11-25 18:10:42","status":"SEND"},{"walletid":"6288883250000000103","chatmode":2,"mgsid":"352054","remark":"恭喜发财，大吉大利","localerrormsg":"","timeout":1440,"reqid":"20201126104326814171929047642828","mode":2,"uid":23436,"receivedcount":1,"currency":"CNY","refundamount":0,"id":95,"bizcreattime":"2020-11-26 10:43:26","bizcompletetime":"2020-11-26 10:43:39","chatbizid":"362","amount":1,"packetcount":1,"createtime":"2020-11-26 10:43:26","refundtype":"","serialnumber":"1331790640945704960","ip":"183.156.73.201","appversion":"2.1.10","token":"20201126446033516564637790359552","refundcount":0,"receivedamount":1,"bizid":"890000595","coinsyn":2,"debitdatetime":"2020-11-26 10:43:37","updatetime":"2020-11-26 10:43:39","device":2,"paymenttype":"BALANCE","queuetime":"2020-11-26 10:43:39","status":"SUCCESS"}]
     * pageNumber : 1
     * pageSize : 16
     * totalPage : 1
     * totalRow : 14
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
        private Integer id;
        private Integer senduid;
        private String createtime;
        private String updatetime;
        private Integer money;
        private Integer mode;
        private Integer num;
        private Integer remainnum;
        private Integer remainmoney;
        private Integer toid;
        private Integer chatlinkid;
        private Integer status;
        private String remark;
        private String sendip;
        private Integer devicetype;
        private String appver;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSenduid() {
            return senduid;
        }

        public void setSenduid(Integer senduid) {
            this.senduid = senduid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }

        public Integer getMode() {
            return mode;
        }

        public void setMode(Integer mode) {
            this.mode = mode;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getRemainnum() {
            return remainnum;
        }

        public void setRemainnum(Integer remainnum) {
            this.remainnum = remainnum;
        }

        public Integer getRemainmoney() {
            return remainmoney;
        }

        public void setRemainmoney(Integer remainmoney) {
            this.remainmoney = remainmoney;
        }

        public Integer getToid() {
            return toid;
        }

        public void setToid(Integer toid) {
            this.toid = toid;
        }

        public Integer getChatlinkid() {
            return chatlinkid;
        }

        public void setChatlinkid(Integer chatlinkid) {
            this.chatlinkid = chatlinkid;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSendip() {
            return sendip;
        }

        public void setSendip(String sendip) {
            this.sendip = sendip;
        }

        public Integer getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(Integer devicetype) {
            this.devicetype = devicetype;
        }

        public String getAppver() {
            return appver;
        }

        public void setAppver(String appver) {
            this.appver = appver;
        }
    }
}
