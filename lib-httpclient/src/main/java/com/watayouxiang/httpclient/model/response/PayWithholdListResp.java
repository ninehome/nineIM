package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class PayWithholdListResp {

    /**
     * firstPage : true
     * lastPage : false
     * list : [{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201127151703240192675924651052","uid":23436,"notifyurl":"","currency":"CNY","id":49,"bizcreattime":"2020-11-27 15:17:03","bizcompletetime":"2020-11-27 15:17:14","bankcode":"POST","arrivalamount":2,"amount":2,"createtime":"2020-11-27 15:17:03","serialnumber":"1332221884191879168","ip":"183.156.73.201","appversion":"2.1.11","token":"20201127299138516995881032339456","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 15:21:42","device":2,"queuetime":"2020-11-27 15:17:15","status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201127151635097168880480290093","uid":23436,"notifyurl":"","currency":"CNY","id":46,"bizcreattime":"2020-11-27 15:16:35","bizcompletetime":"2020-11-27 15:16:45","bankcode":"POST","arrivalamount":3,"amount":3,"createtime":"2020-11-27 15:16:35","serialnumber":"1332221766264827904","ip":"183.156.73.201","appversion":"2.1.11","token":"20201127999922516995763101093888","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 15:21:41","device":2,"queuetime":"2020-11-27 15:16:44","status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201127151355763129172143690840","uid":23436,"notifyurl":"","currency":"CNY","id":44,"bizcreattime":"2020-11-27 15:13:56","bizcompletetime":"2020-11-27 15:14:05","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-27 15:13:55","serialnumber":"1332221097936031744","ip":"183.156.73.201","appversion":"2.1.11","token":"20201127292777516995094654865408","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 15:16:41","device":2,"queuetime":"2020-11-27 15:14:05","sta tus":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201127151231783131080615425922","uid":23436,"notifyurl":"","currency":"CNY","id":43,"bizcreattime":"2020-11-27 15:12:32","bizcompletetime":"2020-11-27 15:12:40","bankcode":"POST","arrivalamount":2,"amount":2,"createtime":"2020-11-27 15:12:31","serialnumber":"1332220745580953600","ip":"183.156.73.201","appversion":"2.1.11","token":"20201127891777516994742333329408","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 15:16:41","device":2,"queuetime":"2020-11-27 15:12:40","status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201127150412847104520424644392","uid":23436,"notifyurl":"","currency":"CNY","id":39,"bizcreattime":"2020-11-27 15:04:13","bizcompletetime":"2020-11-27 15:04:24","bankcode":"POST","arrivalamount":2,"amount":2,"createtime":"2020-11-27 15:04:12","serialnumber":"1332218652992344064","ip":"183.156.73.201","appversion":"2.1.11","token":"20201127793488516992649727954944","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 15:06:41","device":2,"queuetime":"2020-11-27 15:04:24","status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201127143442727192269017740303","uid":23436,"notifyurl":"","currency":"CNY","id":38,"bizcreattime":"2020-11-27 14:34:43","bizcompletetime":"2020-11-27 14:34:51","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-27 14:34:42","serialnumber":"1332211228596113408","ip":"183.156.73.201","appversion":"2.1.11","token":"20201127790705516985225298169856","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:36:41","device":2,"queuetime":"2020-11-27 14:34:51","status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201127142637980165097585295588","uid":23436,"notifyurl":"","currency":"CNY","id":37,"bizcreattime":"2020-11-27 14:26:38","bizcompletetime":"2020-11-27 14:26:46","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-27 14:26:38","serialnumber":"1332209195696660480","ip":"183.156.73.201","appversion":"2.1.11","token":"20201127890188516983192495181824","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:31:41","device":2,"queuetime":"2020-11-27 14:26:46","status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124111300608165220728622526","uid":23436,"notifyurl":"","currency":"CNY","id":20,"bizcreattime":"2020-11-24 11:13:01","bizcompletetime":"2020-11-24 11:13:09","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 11:13:00","serialnumber":"1331073304844447744","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124743824515847301626187776","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124111103714115993683423642","uid":23436,"notifyurl":"","currency":"CNY","id":19,"bizcreattime":"2020-11-24 11:11:04","bizcompletetime":"2020-11-24 11:11:13","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 11:11:03","serialnumber":"1331072814656143360","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124012610515846811450462208","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124110818275149778108103822","uid":23436,"notifyurl":"","currency":"CNY","id":18,"bizcreattime":"2020-11-24 11:08:18","bizcompletetime":"2020-11-24 11:08:27","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 11:08:18","serialnumber":"1331072120784044032","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124011822515846117536419840","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124110622212107861406323557","uid":23436,"notifyurl":"","currency":"CNY","id":17,"bizcreattime":"2020-11-24 11:06:22","bizcompletetime":"2020-11-24 11:06:32","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 11:06:22","serialnumber":"1331071634253164544","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124742366515845631102013440","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124110547011165961990444873","uid":23436,"notifyurl":"","currency":"CNY","id":16,"bizcreattime":"2020-11-24 11:05:47","bizcompletetime":"2020-11-24 11:05:57","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 11:05:47","serialnumber":"1331071486391361536","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124460249515845483143741440","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124110426640127877858164906","uid":23436,"notifyurl":"","currency":"CNY","id":15,"bizcreattime":"2020-11-24 11:04:27","bizcompletetime":"2020-11-24 11:04:36","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 11:04:26","serialnumber":"1331071149257408512","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124349248515845146030755840","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124110353900137867042079356","uid":23436,"notifyurl":"","currency":"CNY","id":14,"bizcreattime":"2020-11-24 11:03:54","bizcompletetime":"2020-11-24 11:04:03","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 11:03:54","serialnumber":"1331071011893952512","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124196480515845008663101440","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201124105656747143337919558616","uid":23436,"notifyurl":"","currency":"CNY","id":13,"bizcreattime":"2020-11-24 10:56:57","bizcompletetime":"2020-11-24 10:57:06","bankcode":"POST","arrivalamount":1,"amount":1,"createtime":"2020-11-24 10:56:56","serialnumber":"1331069262281977856","ip":"60.177.221.26","appversion":"2.1.10","token":"20201124110021515843259038552064","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:42","device":2,"status":"SUCCESS"},{"walletid":"6288883250000000103","bankicon":"https://tx.t-io.org/bank/default.png","timeout":5,"bankcardnumber":"621***5341","reqid":"20201119143654141133257467517202709183169666148892","uid":23436,"notifyurl":"","currency":"CNY","id":10,"bizcreattime":"2020-11-19 14:36:54","bizcompletetime":"2020-11-19 14:37:05","bankcode":"POST","arrivalamount":13,"amount":13,"createtime":"2020-11-19 14:36:54","serialnumber":"1329312676798664704","ip":"","appversion":"","token":"20201119724616514086673551048704","querysyn":3,"bizid":"890000595","coinsyn":3,"bankname":"中国邮政储蓄","updatetime":"2020-11-27 14:16:41","device":1,"status":"SUCCESS"}]
     * pageNumber : 1
     * pageSize : 16
     * totalPage : 2
     * totalRow : 18
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
        /**
         * walletid : 6288883250000000103
         * bankicon : https://tx.t-io.org/bank/default.png
         * timeout : 5
         * bankcardnumber : 621***5341
         * reqid : 20201127151703240192675924651052
         * uid : 23436
         * notifyurl :
         * currency : CNY
         * id : 49
         * bizcreattime : 2020-11-27 15:17:03
         * bizcompletetime : 2020-11-27 15:17:14
         * bankcode : POST
         * arrivalamount : 2
         * amount : 2
         * createtime : 2020-11-27 15:17:03
         * serialnumber : 1332221884191879168
         * ip : 183.156.73.201
         * appversion : 2.1.11
         * token : 20201127299138516995881032339456
         * querysyn : 3
         * bizid : 890000595
         * coinsyn : 3
         * bankname : 中国邮政储蓄
         * updatetime : 2020-11-27 15:21:42
         * device : 2
         * queuetime : 2020-11-27 15:17:15
         * status : SUCCESS
         */

        private String walletid;
        private String bankicon;
        private int timeout;
        private String bankcardnumber;
        private String reqid;
        private int uid;
        private String notifyurl;
        private String currency;
        private int id;
        private String bizcreattime;
        private String bizcompletetime;
        private String bankcode;
        private int arrivalamount;
        private int amount;
        private String createtime;
        private String serialnumber;
        private String ip;
        private String appversion;
        private String token;
        private int querysyn;
        private String bizid;
        private int coinsyn;
        private String bankname;
        private String updatetime;
        private int device;
        private String queuetime;
        private String status;

        public String getWalletid() {
            return walletid;
        }

        public void setWalletid(String walletid) {
            this.walletid = walletid;
        }

        public String getBankicon() {
            return bankicon;
        }

        public void setBankicon(String bankicon) {
            this.bankicon = bankicon;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public String getBankcardnumber() {
            return bankcardnumber;
        }

        public void setBankcardnumber(String bankcardnumber) {
            this.bankcardnumber = bankcardnumber;
        }

        public String getReqid() {
            return reqid;
        }

        public void setReqid(String reqid) {
            this.reqid = reqid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNotifyurl() {
            return notifyurl;
        }

        public void setNotifyurl(String notifyurl) {
            this.notifyurl = notifyurl;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBizcreattime() {
            return bizcreattime;
        }

        public void setBizcreattime(String bizcreattime) {
            this.bizcreattime = bizcreattime;
        }

        public String getBizcompletetime() {
            return bizcompletetime;
        }

        public void setBizcompletetime(String bizcompletetime) {
            this.bizcompletetime = bizcompletetime;
        }

        public String getBankcode() {
            return bankcode;
        }

        public void setBankcode(String bankcode) {
            this.bankcode = bankcode;
        }

        public int getArrivalamount() {
            return arrivalamount;
        }

        public void setArrivalamount(int arrivalamount) {
            this.arrivalamount = arrivalamount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getAppversion() {
            return appversion;
        }

        public void setAppversion(String appversion) {
            this.appversion = appversion;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getQuerysyn() {
            return querysyn;
        }

        public void setQuerysyn(int querysyn) {
            this.querysyn = querysyn;
        }

        public String getBizid() {
            return bizid;
        }

        public void setBizid(String bizid) {
            this.bizid = bizid;
        }

        public int getCoinsyn() {
            return coinsyn;
        }

        public void setCoinsyn(int coinsyn) {
            this.coinsyn = coinsyn;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getDevice() {
            return device;
        }

        public void setDevice(int device) {
            this.device = device;
        }

        public String getQueuetime() {
            return queuetime;
        }

        public void setQueuetime(String queuetime) {
            this.queuetime = queuetime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
