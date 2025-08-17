package com.tiocloud.chat.feature.home.chat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseViewHolder;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.util.MoonUtil;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.util.TimeUtil;
import com.tiocloud.chat.widget.textview.ListUnreadTextView;
import com.watayouxiang.androidutils.util.HtmlUtils;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.imclient.model.MsgTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/13
 *     desc   : ui 处理
 * </pre>
 */
public class BaseUIAdapter extends BaseDataAdapter {

    // 当前用户昵称
    private final String currNick = CurrUserTableCrud.curr_getNick();
    // 当前用户 id
    private final String currUid = String.valueOf(TioDBPreferences.getCurrUid());

    private String getCurrUid() {
        return currUid;
    }

    private String getCurrNick() {
        return currNick;
    }

    public BaseUIAdapter(@NonNull RecyclerView recyclerView) {
        super(R.layout.tio_chat_list_item, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    private static JSONArray onlineArray;

    public static void updateOnlineArray(String uid, boolean onoff, String lastOnlineTime){
        if (onlineArray == null){
            onlineArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uid", uid);
            jsonObject.put("online", onoff);
            if (!onoff){
                jsonObject.put("lastOnlineTime", lastOnlineTime);
            }
            onlineArray.add(jsonObject);
            return;
        }
        boolean isupdate = false;
        for (int i = 0; i < onlineArray.size(); i++){
            JSONObject jsonObject = onlineArray.getJSONObject(i);
            if (jsonObject.getString("uid").equals(uid)){
                jsonObject.put("online", onoff);
                if (!onoff){
                    jsonObject.put("lastOnlineTime", lastOnlineTime);
                }
                isupdate = true;
                break;
            }
        }
        if (!isupdate){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uid", uid);
            jsonObject.put("online", onoff);
            if (!onoff){
                jsonObject.put("lastOnlineTime", lastOnlineTime);
            }
            onlineArray.add(jsonObject);
        }
        OnlineStatusListener onlineStatusListener = listenerMap.get(uid);
        if (onlineStatusListener != null){
            String lastTime = getLastOnlineTime(uid);
            Log.w("zlb", ""+uid+"\t"+lastTime);
            onlineStatusListener.back(uid, isOnline(uid), lastTime);
        }

        if (BaseUIAdapter.onlineStatusListener != null && listenerUid != null && listenerUid.equals(uid)){
            BaseUIAdapter.onlineStatusListener.back(listenerUid, isOnline(listenerUid), getLastOnlineTime(listenerUid));
        }
    }

    static OnlineStatusListener onlineStatusListener;
    static String listenerUid;

    public interface OnlineStatusListener{
        void back(String uid, boolean isOnline, String lastOnlineTime);
    }

    public static Map<String, OnlineStatusListener> listenerMap = new HashMap<>();

    public static void setListenerMap(String uid, OnlineStatusListener onlineStatusListener) {
        listenerMap.put(uid, onlineStatusListener);
        if (onlineArray != null){
            onlineStatusListener.back(uid, isOnline(uid), getLastOnlineTime(uid));
        }
    }

    public static void setOnlineStatusListener(OnlineStatusListener onlineStatusListener, String listenerUid) {
        BaseUIAdapter.onlineStatusListener = onlineStatusListener;
        BaseUIAdapter.listenerUid = listenerUid;
        if (BaseUIAdapter.onlineStatusListener != null && BaseUIAdapter.listenerUid != null){
            setOnlineArray(onlineArray);
        }
    }

    public static void setOnlineArray(JSONArray onlineArray) {
        BaseUIAdapter.onlineArray = onlineArray;
        if (onlineStatusListener != null && listenerUid != null){
            onlineStatusListener.back(listenerUid, isOnline(listenerUid), getLastOnlineTime(listenerUid));
        }
        for (String uid : listenerMap.keySet()){
            OnlineStatusListener onlineStatusListener = listenerMap.get(uid);
            if (onlineStatusListener != null){
                String lastTime = getLastOnlineTime(uid);
                Log.w("zlb", ""+uid+"\t"+lastTime);
                onlineStatusListener.back(uid, isOnline(uid), lastTime);
            }
        }
    }

    public static boolean isOnline(String uid){
        if (onlineArray == null || uid == null){
            return false;
        }
        for (int i = 0; i < onlineArray.size(); i++){
            JSONObject jsonObject = onlineArray.getJSONObject(i);
            String uid1 = jsonObject.getString("uid");
            if (uid.equals(uid1)){
                return jsonObject.getBoolean("online");
            }
        }
        return false;
    }

    public static String getLastOnlineTime(String uid){
        if (onlineArray == null || uid == null){
            return null;
        }
        for (int i = 0; i < onlineArray.size(); i++){
            JSONObject jsonObject = onlineArray.getJSONObject(i);
            String uid1 = jsonObject.getString("uid");
            if (uid.equals(uid1)){
                return jsonObject.getString("lastOnlineTime");
            }
        }
        return null;
    }



    @Override
    protected void convert(BaseViewHolder helper, ChatListResp.List item) {
        View cl_container = helper.getView(R.id.cl_container);
        TioImageView avatar = helper.getView(R.id.v_avatar);
        TextView name = helper.getView(R.id.v_name);
        View v_status = helper.getView(R.id.v_status);
        v_status.setEnabled(false);
        TextView recentMsg = helper.getView(R.id.v_recent_msg);
        TextView time = helper.getView(R.id.tv_time);
        ListUnreadTextView unreadMsgNum = helper.getView(R.id.tv_unreadMsgNum);
        View iv_topFlag = helper.getView(R.id.iv_topFlag);

        // 头像
        avatar.tio_roundAvatar(item.avatar);
        // 昵称
        name.setText(StringUtil.nonNull(item.name));
        // 发送时间
        initTime(time, item.sendtime);
        // 未读消息数
        if (TioConfig.isNoDisturb(item.chatmode, item.bizid != null, item.bizid == null ? item.id:item.bizid)){
            initNotReadMsgCount(unreadMsgNum, 0);
            if (item.notreadcount > 0){
                helper.setVisible(R.id.tv_notrub_msg, true);
            }else {
                helper.setVisible(R.id.tv_notrub_msg, false);
            }
        }else {
            initNotReadMsgCount(unreadMsgNum, item.notreadcount);
            helper.setVisible(R.id.tv_notrub_msg, false);
        }

        // 置顶标志显隐
        initTopFlag(iv_topFlag, item.topflag);
        // item 的背景
        initItemBg(cl_container, item.topflag);
        // 最新消息内容
        initMsg(recentMsg, item);
        if (item.chatmode == 1 && TioConfig.OpenCloseConfig.showOnlineStatus()){
            helper.setVisible(R.id.tv_online_status, false);
            boolean isOnline = isOnline(item.bizid);
//            helper.setBackgroundRes(R.id.tv_online_status, isOnline ? R.drawable.dot_online:R.drawable.dot_offline_bg);
            helper.setVisible(R.id.v_status, true);
            if (!isOnline){
//                if (TioConfig.OpenCloseConfig.showLastOnlineTime()){
//                    helper.setBackgroundRes(R.id.tv_online_status, R.drawable.dot_offline_bg);
//                    helper.setText(R.id.tv_online_status, getOfflineTime(getLastOnlineTime(item.bizid)));
//                    helper.setTextColor(R.id.tv_online_status, mContext.getResources().getColor(R.color.gray_c1c1c1));
//                }else {
//                }
                helper.setBackgroundRes(R.id.tv_online_status, R.drawable.dot_offline);
                helper.setText(R.id.tv_online_status, "");
                helper.setEnabled(R.id.v_status,false);

            }else {
                helper.setBackgroundRes(R.id.tv_online_status, R.drawable.dot_online);
                helper.setText(R.id.tv_online_status, "");
                helper.setEnabled(R.id.v_status,true);
            }
//            helper.setText(R.id.tv_online_status, isOnline?"[在线]":"[离线]");
//            helper.setTextColor(R.id.tv_online_status, mContext.getResources().getColor(isOnline?R.color.theme_color:R.color.gray_999999));
//            helper.setBackgroundRes(R.id.tv_online_status, isOnline ? R.drawable.shape_online_true:R.drawable.shape_online_false);
        }else {
            helper.setVisible(R.id.tv_online_status, false);
            helper.setVisible(R.id.v_status,false);
        }
    }

    /**
     * item 背景
     *
     * @param topflag 1 置顶，2 不置顶
     */
    private void initItemBg(View rootView, int topflag) {
        if (topflag == 1) {
            rootView.setBackgroundColor(Utils.getApp().getResources().getColor(R.color.grayf4f5f6));
        } else {
            rootView.setBackgroundColor(Utils.getApp().getResources().getColor(R.color.white));
        }
    }

    /**
     * 置顶消息
     *
     * @param topflag 1 置顶，2 不置顶
     */
    private void initTopFlag(View iv_topFlag, int topflag) {
        if (topflag == 1) {
            iv_topFlag.setVisibility(View.VISIBLE);
        } else {
            iv_topFlag.setVisibility(View.GONE);
        }
    }

    /**
     * 未读消息数
     */
    private void initNotReadMsgCount(ListUnreadTextView unreadMsgNum, int notreadcount) {
        unreadMsgNum.setUnread(notreadcount, true);
    }

    /**
     * 发送时间
     *
     * @param tvTime   控件
     * @param sendTime 发送时间
     */
    private void initTime(TextView tvTime, String sendTime) {
        Long _sendTime = TimeUtil.dateString2Long(sendTime);
        if (_sendTime != null) {
            tvTime.setVisibility(View.VISIBLE);
            String showTime = TimeUtil.getShowTime(_sendTime, true);
            tvTime.setText(StringUtil.nonNull(showTime));
        } else {
            tvTime.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化消息显示
     */
    private void initMsg(TextView recentMsg, ChatListResp.List item) {
        // 重置富文本
        SpanUtils.with(recentMsg)
                .append("").setForegroundColor(Color.parseColor("#888888"))
                .create();

        // 构建新的富文本
        SpanUtils utils = SpanUtils.with(recentMsg);

        /* 消息状态 */
        if (item.chatmode == 1) {
            if (TioConfig.OpenCloseConfig.showReadStatus() && String.valueOf(item.lastmsguid).equals(getCurrUid()) && item.sysflag != 1) {
                // 已读 / 未读
                // 私聊 && 最后一条消息的发送者是自己 && 非系统消息
                // 显示 "[未读]" / "[已读]"
                if (item.toreadflag == 1) {
                    utils.append(mContext.getString(R.string.readed)).setForegroundColor(Color.parseColor("#CCCCCC"));
                } else if (item.toreadflag == 2) {
                    utils.append(mContext.getString(R.string.unreaded)).setForegroundColor(recentMsg.getContext().getResources().getColor(R.color.red_nored_color))
                            .append("").setForegroundColor(Color.parseColor("#888888"));
                }
            }
        } else if (item.chatmode == 2) {
            if (item.atreadflag == 2) {
                // 艾特
                // 群聊 && 有未读的艾特消息
                // 显示 "[有人@你]"
                utils.append(mContext.getString(R.string.someone_you)).setForegroundColor(Color.parseColor("#3A88FB"))
                        .append("").setForegroundColor(Color.parseColor("#888888"));
            }
        }

        /* 消息内容 */
        boolean setContent = false;
        if (item.sysflag == 1) {
            if (!TextUtils.isEmpty(item.sysmsgkey)) {
                // 系统消息 && 系统消息key存在
                // 显示 "会话模版"
                String tipMsg = MsgTemplate.getTipMsg(item.sysmsgkey, item.opernick, item.tonicks, getCurrNick());
                if (tipMsg == null){
                    tipMsg = item.msgresume;
                }
                utils.append(StringUtil.nonNull(tipMsg));
                setContent = true;
            }
        } else {
            if (item.chatmode == 2) {
                // 不是系统消息 && 如果是群聊
                // 显示 "昵称: 消息内容"
                if (!TextUtils.isEmpty(item.fromnick) && !TextUtils.isEmpty(item.msgresume)) {
                    utils.append(String.format(Locale.getDefault(), "%s: %s", item.fromnick, item.msgresume));
                    setContent = true;
                } else if (!TextUtils.isEmpty(item.fromnick)) {
//                    utils.append(String.format(Locale.getDefault(), "%s: ", item.fromnick));
                    utils.append(String.format(Locale.getDefault(), ""));
                    setContent = true;
                }
            }
        }
        if (!setContent) {
            // 不是系统消息 && 如果是私聊
            // 显示 "消息内容"
            utils.append(StringUtil.nonNull(HtmlUtils.unescapeHtml(item.msgresume)));
        }
        if (!TioConfig.OpenCloseConfig.showInviteKickTips()){
            if (item.chatmode == 2 && item.sysflag == 1 && CacheTableCrud.getGroupRoleChatlinkMy(item.id) == 2){
                utils = SpanUtils.with(recentMsg).append("");
            }
        }
        // 富文本实现
        SpannableStringBuilder builder = utils.create();

        // 表情识别替换
        MoonUtil.identifyFaceExpression(recentMsg, builder, ImageSpan.ALIGN_BOTTOM, 0.35f);
    }

    private final static long HOUR = 60 * 60 * 1000;
    public static String getOfflineTime(Context mContext,String lastTimeStr){
        if (lastTimeStr == null){
            return mContext.getString(R.string.disonline);
        }
        Long lastTime = Long.parseLong(lastTimeStr);
        Long diffTime = System.currentTimeMillis() - lastTime;
        if (diffTime < HOUR){
            return (diffTime/(60*1000))+mContext.getString(R.string.minute_ago_online);
        }
        if (diffTime < 24 * HOUR){
            return (diffTime/(HOUR))+mContext.getString(R.string.hour_ago_online);
        }
        return mContext.getString(R.string.last_online_at)+TimeUtil.dateLong2String(lastTime,
                mContext.getString(R.string.yymmdd));
    }
}

