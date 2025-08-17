package com.watayouxiang.httpclient.model.response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/26
 *     desc   :
 * </pre>
 */
public class PayGetWalletItemsResp {

    /**
     * firstPage : true
     * lastPage : false
     * list : [{"amount":9,"createtime":"2020-11-27 15:17:38","orderstatus":"SUCCESS","serialnumber":"1332222005457592320","bizstr":"充值","remark":"","othercny":0,"coinflag":1,"mode":1,"uid":23436,"bizid":114,"id":70,"bizcompletetime":"2020-11-27 15:17:38","bizcreattime":"2020-11-27 15:17:32","updatetime":"2020-11-27 15:17:38","status":1},{"amount":2,"createtime":"2020-11-27 15:17:15","orderstatus":"SUCCESS","serialnumber":"1332221884191879168","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":49,"id":69,"bizcompletetime":"2020-11-27 15:17:14","bizcreattime":"2020-11-27 15:17:03","updatetime":"2020-11-27 15:17:15","status":1},{"amount":3,"createtime":"2020-11-27 15:16:44","orderstatus":"SUCCESS","serialnumber":"1332221766264827904","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":46,"id":68,"bizcompletetime":"2020-11-27 15:16:45","bizcreattime":"2020-11-27 15:16:35","updatetime":"2020-11-27 15:16:44","status":1},{"amount":1,"createtime":"2020-11-27 15:14:05","orderstatus":"SUCCESS","serialnumber":"1332221097936031744","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":44,"id":67,"bizcompletetime":"2020-11-27 15:14:05","bizcreattime":"2020-11-27 15:13:56","updatetime":"2020-11-27 15:14:05","status":1},{"amount":2,"createtime":"2020-11-27 15:12:40","orderstatus":"SUCCESS","serialnumber":"1332220745580953600","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":43,"id":66,"bizcompletetime":"2020-11-27 15:12:40","bizcreattime":"2020-11-27 15:12:32","updatetime":"2020-11-27 15:12:40","status":1},{"amount":2,"createtime":"2020-11-27 15:04:24","orderstatus":"SUCCESS","serialnumber":"1332218652992344064","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":39,"id":65,"bizcompletetime":"2020-11-27 15:04:24","bizcreattime":"2020-11-27 15:04:13","updatetime":"2020-11-27 15:04:24","status":1},{"amount":1,"createtime":"2020-11-27 14:34:51","orderstatus":"SUCCESS","serialnumber":"1332211228596113408","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":38,"id":64,"bizcompletetime":"2020-11-27 14:34:51","bizcreattime":"2020-11-27 14:34:43","updatetime":"2020-11-27 14:34:51","status":1},{"amount":1,"createtime":"2020-11-27 14:26:46","orderstatus":"SUCCESS","serialnumber":"1332209195696660480","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":37,"id":63,"bizcompletetime":"2020-11-27 14:26:46","bizcreattime":"2020-11-27 14:26:38","updatetime":"2020-11-27 14:26:46","status":1},{"amount":1,"createtime":"2020-11-27 14:19:52","orderstatus":"SUCCESS","serialnumber":"1332207464753541120","bizstr":"充值","remark":"","othercny":0,"coinflag":1,"mode":1,"uid":23436,"bizid":112,"id":62,"bizcompletetime":"2020-11-27 14:19:52","bizcreattime":"2020-11-27 14:19:45","updatetime":"2020-11-27 14:19:52","status":1},{"amount":1658600,"createtime":"2020-11-27 14:19:28","orderstatus":"FAIL","serialnumber":"1332207365122035712","bizstr":"充值","remark":"交易金额或次数超过限制","othercny":0,"coinflag":1,"mode":1,"uid":23436,"bizid":111,"id":61,"bizcompletetime":"2020-11-27 14:19:28","bizcreattime":"2020-11-27 14:19:22","updatetime":"2020-11-27 14:19:28","status":2},{"amount":1,"createtime":"2020-11-27 14:16:42","orderstatus":"SUCCESS","serialnumber":"1331073304844447744","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":20,"id":46,"bizcompletetime":"2020-11-24 11:13:09","bizcreattime":"2020-11-24 11:13:01","updatetime":"2020-11-27 14:16:42","status":1},{"amount":1,"createtime":"2020-11-27 14:16:42","orderstatus":"SUCCESS","serialnumber":"1331072814656143360","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":19,"id":45,"bizcompletetime":"2020-11-24 11:11:13","bizcreattime":"2020-11-24 11:11:04","updatetime":"2020-11-27 14:16:42","status":1},{"amount":1,"createtime":"2020-11-27 14:16:42","orderstatus":"SUCCESS","serialnumber":"1331072120784044032","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":18,"id":44,"bizcompletetime":"2020-11-24 11:08:27","bizcreattime":"2020-11-24 11:08:18","updatetime":"2020-11-27 14:16:42","status":1},{"amount":1,"createtime":"2020-11-27 14:16:42","orderstatus":"SUCCESS","serialnumber":"1331071634253164544","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":17,"id":43,"bizcompletetime":"2020-11-24 11:06:32","bizcreattime":"2020-11-24 11:06:22","updatetime":"2020-11-27 14:16:42","status":1},{"amount":1,"createtime":"2020-11-27 14:16:42","orderstatus":"SUCCESS","serialnumber":"1331071486391361536","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":16,"id":42,"bizcompletetime":"2020-11-24 11:05:57","bizcreattime":"2020-11-24 11:05:47","updatetime":"2020-11-27 14:16:42","status":1},{"amount":1,"createtime":"2020-11-27 14:16:42","orderstatus":"SUCCESS","serialnumber":"1331071149257408512","bizstr":"提现","remark":"","othercny":0,"coinflag":2,"mode":2,"uid":23436,"bizid":15,"id":41,"bizcompletetime":"2020-11-24 11:04:36","bizcreattime":"2020-11-24 11:04:27","updatetime":"2020-11-27 14:16:42","status":1}]
     * pageNumber : 1
     * pageSize : 16
     * totalPage : 3
     * totalRow : 39
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
        private Integer uid;
        private Integer walletid;
        private String createtime;
        private String updatetime;
        private Integer amount;
        private Integer amountall;
        private Integer coinflag;
        private Integer mode;
        private Integer bizid;
        private String biznumber;
        private String remark;
        private HashMap data;

        public HashMap getData() {
            return data;
        }

        public void setData(HashMap data) {
            this.data = data;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }

        public Integer getWalletid() {
            return walletid;
        }

        public void setWalletid(Integer walletid) {
            this.walletid = walletid;
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

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getAmountall() {
            return amountall;
        }

        public void setAmountall(Integer amountall) {
            this.amountall = amountall;
        }

        public Integer getCoinflag() {
            return coinflag;
        }

        public void setCoinflag(Integer coinflag) {
            this.coinflag = coinflag;
        }

        public Integer getMode() {
            return mode;
        }

        public void setMode(Integer mode) {
            this.mode = mode;
        }

        public Integer getBizid() {
            return bizid;
        }

        public void setBizid(Integer bizid) {
            this.bizid = bizid;
        }

        public String getBiznumber() {
            return biznumber;
        }

        public void setBiznumber(String biznumber) {
            this.biznumber = biznumber;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
