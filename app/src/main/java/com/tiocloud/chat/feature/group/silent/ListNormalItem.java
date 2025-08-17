package com.tiocloud.chat.feature.group.silent;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   :
 * </pre>
 */
class ListNormalItem {
    private String avatar = null;
    private String title = null;
    private String subtitle = null;
    private String tagTxt = null;
    private ForbiddenUserListResp.ListBean originalData;

    public String getAvatar() {
        return HttpCache.getResUrl(avatar);
    }

    public String getTitle() {
        return StringUtils.null2Length0(title);
    }

    public String getSubtitle() {
        return StringUtils.null2Length0(subtitle);
    }

    public String getTagTxt() {
        return StringUtils.null2Length0(tagTxt);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTagTxt(String tagTxt) {
        this.tagTxt = tagTxt;
    }

    public void setOriginalData(ForbiddenUserListResp.ListBean bean) {
        originalData = bean;
    }

    public ForbiddenUserListResp.ListBean getOriginalData() {
        return originalData;
    }

    // ====================================================================================
    // 是否显示删除按钮
    // ====================================================================================

    private static boolean isShowDelete = false;

    public static boolean isShowDelete() {
        return isShowDelete;
    }

    public static void setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
    }
}
