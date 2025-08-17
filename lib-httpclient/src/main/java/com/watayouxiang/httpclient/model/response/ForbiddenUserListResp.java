package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   :
 * </pre>
 */
public class ForbiddenUserListResp {

    /**
     * firstPage : true
     * lastPage : true
     * list : [{"nick":"文曲11","uid":32729,"groupid":"240","forbiddenflag":1,"forbiddenduration":86400,"id":"74204","avatar":"/user/avatar/6/6854/1128856/88106909/74541320277/21/163538/1259039310963941376_sm.jpg","grouprole":2,"srcnick":"文曲11"}]
     * pageNumber : 1
     * pageSize : 100
     * totalPage : 1
     * totalRow : 1
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
         * nick : 文曲11
         * uid : 32729
         * groupid : 240
         * forbiddenflag : 1
         * forbiddenduration : 86400
         * id : 74204
         * avatar : /user/avatar/6/6854/1128856/88106909/74541320277/21/163538/1259039310963941376_sm.jpg
         * grouprole : 2
         * srcnick : 文曲11
         */

        private String nick;
        private int uid;
        private String groupid;
        /**
         * 禁言标识：1：时长禁言；2：否；3：长久禁用
         */
        private int forbiddenflag;
        /**
         * 禁言时长，秒
         */
        private long forbiddenduration;
        private String id;
        private String avatar;
        private int grouprole;
        private String srcnick;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public int getForbiddenflag() {
            return forbiddenflag;
        }

        public void setForbiddenflag(int forbiddenflag) {
            this.forbiddenflag = forbiddenflag;
        }

        public long getForbiddenduration() {
            return forbiddenduration;
        }

        public void setForbiddenduration(long forbiddenduration) {
            this.forbiddenduration = forbiddenduration;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGrouprole() {
            return grouprole;
        }

        public void setGrouprole(int grouprole) {
            this.grouprole = grouprole;
        }

        public String getSrcnick() {
            return srcnick;
        }

        public void setSrcnick(String srcnick) {
            this.srcnick = srcnick;
        }

        /**
         * 禁言类型：1：用户时长禁言；3：用户长久禁言 ； 4：群禁言--------必填
         */
        public String getForbiddenMode() {
            if (forbiddenflag == 1 || forbiddenflag == 3) {
                return forbiddenflag + "";
            }
            return null;
        }
    }
}
