package com.watayouxiang.httpclient.model.response;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc : 会话列表
 */
public class ChatListResp extends LinkedList<ChatListResp.List> implements Serializable {
    public static class List implements Serializable, Cloneable {
        /**
         * chatmode : 1
         * linkflag : 1
         * readflag : 1
         * uid : 23436
         * sysflag : 2
         * linkid : 611
         * msgresume :
         * topflag : 2
         * fromnick :
         * notreadcount : 0
         * id : 10
         * atreadflag : 1
         * viewflag : 1
         * createtime : 2020-02-07 21:32:41
         * avatar : /user/avatar/50/8931/1119484/88097537/74541310905/91/170020/t-io-前白背蓝-正方.png_sm.jpg
         * bizid : 23357
         * name : talent
         * startmsgid : 0
         * fidkey : 23436_23357
         * toreadflag : 1
         * updatetime : 2020-02-07 21:32:41
         * atnotreadcount : 0
         * lastmsgid : 371931
         * lastmsguid : 23436
         * sendtime : 2020-02-11 09:13:56
         */

        /**
         * 聊天会话的模型：1：私聊；2：群聊
         */
        public int chatmode;
        public int linkflag;
        public int readflag;
        /**
         * 自己的uid
         */
        public int uid;
        /**
         * 最后一条消息系统标识：1：是系统消息；2：是正常消息
         */
        public int sysflag;
        public String linkid;
        /**
         * 最后一条消息
         */
        public String msgresume;
        /**
         * 置顶标记
         * 1 置顶，2 非置顶
         */
        public int topflag;
        /**
         * 发送者昵称
         */
        public String fromnick;
        /**
         * 自己未读的消息条数
         */
        public int notreadcount;
        /**
         * 聊天会话id-全局为chatlinkid
         */
        public String id;
        /**
         * 别人艾特我，我是否已读
         * <p>
         * 1 已读艾特，2 未读艾特
         */
        public int atreadflag;
        public int viewflag;
        /**
         * 头像
         */
        public String avatar;
        /**
         * 好友的uid或者群的groupid
         */
        public String bizid;
        /**
         * 昵称
         */
        public String name;
        /**
         * 我发的消息，对方是否已读
         * <p>
         * 1已读 2未读
         */
        public int toreadflag;
        /**
         * 未读的艾特数量
         */
        public int atnotreadcount;
        /**
         * 最后一条消息的id
         */
        public String lastmsgid;
        /**
         * 最后一条消息的发送者的 uid
         */
        public int lastmsguid;
        /**
         * 最后一条消息发送的时间
         */
        public String sendtime;
        /**
         * 系统消息key
         * <p>
         * ex: join
         */
        public String sysmsgkey;
        /**
         * 操作者 昵称
         */
        public String opernick;
        /**
         * 被操作者 昵称
         */
        public String tonicks;
        /**
         * 聊天更新时间（必定存在的时间，排序依据）
         */
        public String chatuptime;
        /**
         * 群聊激活时的群人数
         */
        public int joinnum;

        public boolean isSelected;

        @Nullable
        @Override
        public ChatListResp.List clone() {
            try {
                return (List) super.clone();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
