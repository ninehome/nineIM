package com.watayouxiang.imclient.model;

import androidx.annotation.Nullable;

import com.watayouxiang.imclient.R;
import com.watayouxiang.imclient.utils.ConstantUtils;

/**
 * author : TaoWang
 * date : 2020/4/16
 * desc : 会话模板
 */
public enum MsgTemplate {
    // 创建群消息模板
    create("create", "%%% "+ ConstantUtils.context.getString(R.string.yaoqing) +" ### "+ConstantUtils.context.getString(R.string.jiaruqunliao)),
    // 进群消息模板
    join("join", "%%% "+ConstantUtils.context.getString(R.string.yaoqing)+" ### "+ConstantUtils.context.getString(R.string.jiaruqunliao)),
    // 群主退群模板
    ownerleave("ownerleave", "%%% "+ConstantUtils.context.getString(R.string.tuichuqunliao)+"，### "+ConstantUtils.context.getString(R.string.zidongqunzhu)),
    // 主动退群模板
    leave("leave", "%%% "+ConstantUtils.context.getString(R.string.tuichuqunliao)),
    // 踢人退群模板
    operkick("operkick", "%%% "+ConstantUtils.context.getString(R.string.jiang)+" ### "+ConstantUtils.context.getString(R.string.yichulequnliao)),
    // 被踢者接受到的模板
    tokick("tokick", "### "+ConstantUtils.context.getString(R.string.bei)+" %%% "+ConstantUtils.context.getString(R.string.yichulequnliao)),
    // 撤回消息模板
    msgback("msgback", "%%% "+ConstantUtils.context.getString(R.string.chehuilexiaoxi)),
    managermsgback("managermsgback", "%%% "+ConstantUtils.context.getString(R.string.chehuile)+" ### "+ConstantUtils.context.getString(R.string.dexiaoxi)),
    // 群转移模板
    ownerchange("ownerchange", "%%% "+ConstantUtils.context.getString(R.string.qunzhuanrang)+" ###"),

    setManager("setManager", "%%% "+ConstantUtils.context.getString(R.string.setting)+" ###"+ConstantUtils.context.getString(R.string.weiguanliyuan)),

    cancleManager("cancleManager", "%%% "+ConstantUtils.context.getString(R.string.quxiaole)+" ###"+ConstantUtils.context.getString(R.string.deguanliyuan)),
    // 开启群邀请信息开关
    applyopen("applyopen", "%%% "+ConstantUtils.context.getString(R.string.xiugaiqunshenhetishi4)),
    // 关闭群邀请信息开关
    applyclose("applyclose", "%%% "+ConstantUtils.context.getString(R.string.xiugaiqunshenhetishi3)),
    // 开启群审核开关
    reviewopen("reviewopen", "%%% "+ConstantUtils.context.getString(R.string.xiugaiqunshenhetishi2)),
    // 关闭群审核开关
    reviewclose("reviewclose", "%%% "+ConstantUtils.context.getString(R.string.xiugaiqunshenhetishi1)),
    // 修改群公告
    updatenotice("updatenotice", "%%% "+ConstantUtils.context.getString(R.string.xiugaiqungonggao)+"：###"),
    // 修改群名称
    updatename("updatename", "%%% "+ConstantUtils.context.getString(R.string.xiugaiqunming)+"：###"),
    // 解散群
    delgroup("delgroup", "%%% "+ConstantUtils.context.getString(R.string.jiesanqun)),
    // 禁言模板
    forbidden("forbidden", "### "+ConstantUtils.context.getString(R.string.beijinyan)),
    // 解除禁言
    cancelforbidden("cancelforbidden", "### "+ConstantUtils.context.getString(R.string.beijiechu)),
    // 抢红包模板
    grab("grab", "%%% "+ConstantUtils.context.getString(R.string.lingqule)+" ### "+ConstantUtils.context.getString(R.string.dehongbao)),

//    showTime("showTime", ""),
    ;

    String key;
    String value;

    public String getKey(){
        return this.key;
    }

    MsgTemplate(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static MsgTemplate getTemplate(String key) {
        for (MsgTemplate e : values()) {
            if (e.key.equals(key)) {
                return e;
            }
        }
        return null;
    }

    @Nullable
    public static String getTipMsg(String key, String operNick, String toNicks, String currNick) {
        if (key == null || operNick == null || toNicks == null || currNick == null) return null;

        operNick = operNick.equals(currNick) ? ConstantUtils.context.getString(R.string.you) : "\"" + operNick + "\"";
        toNicks = toNicks.equals(currNick) ? ConstantUtils.context.getString(R.string.you) : "\"" + toNicks + "\"";

        MsgTemplate template = getTemplate(key);
        if (template == null) return null;
        String value = template.value;

        value = value.replace("%%%", operNick);
        value = value.replace("###", toNicks);

        return value;
    }
}
