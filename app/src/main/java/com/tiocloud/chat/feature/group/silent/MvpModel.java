package com.tiocloud.chat.feature.group.silent;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;

import java.util.ArrayList;
import java.util.List;

class MvpModel extends MvpContract.Model {
    public MvpModel() {
        super(false);
    }

    @Override
    public List<ListModel> forbiddenUserList2Models(List<ForbiddenUserListResp.ListBean> list) {
        if (list == null) return null;
        ArrayList<ListModel> models = CollectionUtils.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            ForbiddenUserListResp.ListBean bean = list.get(i);

            ListNormalItem normalItem = new ListNormalItem();
            normalItem.setAvatar(bean.getAvatar());
            normalItem.setTitle(bean.getSrcnick());
            if (!StringUtils.equals(bean.getSrcnick(), bean.getNick())) {
                normalItem.setSubtitle("昵称：" + bean.getNick());
            }
            normalItem.setTagTxt(getTagTxt(bean.getForbiddenduration(), bean.getForbiddenflag()));
            normalItem.setOriginalData(bean);
            models.add(new ListModel(normalItem));
        }
        return models;
    }

    /**
     * @param forbiddenduration 禁言时长，秒
     * @param forbiddenflag     禁言标识：1：时长禁言；2：否；3：长久禁用
     * @return
     */
    private String getTagTxt(long forbiddenduration, int forbiddenflag) {
        if (forbiddenflag == 3) {
            return "长期禁言";
        } else if (forbiddenflag == 1) {
            if (forbiddenduration == 10 * 60) {
                return "10分钟";
            } else if (forbiddenduration == 60 * 60) {
                return "1小时";
            } else if (forbiddenduration == 24 * 60 * 60) {
                return "24小时";
            } else {
                int hour = (int) (((double) forbiddenduration) / (60 * 60));
                return hour + "小时";
            }
        } else {
            return "unknown";
        }
    }
}
