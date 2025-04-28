package com.tiocloud.chat.feature.curr.modify.model;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.httpclient.utils.Constants;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc :
 */
public class PageUiModel {
    public int max_word;
    public int min_line;
    public int max_line;
    public String menu_name;
    public String page_title;
    public boolean enableNullContent;
    public boolean showLimit = true;
    public String noEditTipStr;

    private PageUiModel() {
    }

    public static PageUiModel getInstance(ModifyType type) {
        PageUiModel model = null;
        if (type == ModifyType.GROUP_NAME) {
            model = new PageUiModel();
            model.max_word = 30;
            model.min_line = 1;
            model.max_line = 5;
            model.menu_name = ConstantUtils.context.getString(R.string.tijiao);
            model.page_title = ConstantUtils.context.getString(R.string.group_name);
            model.enableNullContent = false;
        } else if (type == ModifyType.GROUP_INTRO) {
            model = new PageUiModel();
            model.max_word = 500;
            model.min_line = 2;
            model.max_line = 20;
            model.menu_name = ConstantUtils.context.getString(R.string.tijiao);
            model.page_title = ConstantUtils.context.getString(R.string.group_intro);
            model.enableNullContent = true;
            model.showLimit = false;
            model.noEditTipStr = ConstantUtils.context.getString(R.string.nomodidytip);
        } else if (type == ModifyType.GROUP_NOTICE) {
            model = new PageUiModel();
            model.max_word = 500;
            model.min_line = 10;
            model.max_line = 20;
            model.menu_name = ConstantUtils.context.getString(R.string.tijiao);
            model.page_title = ConstantUtils.context.getString(R.string.group_notice);
            model.enableNullContent = true;
        } else if (type == ModifyType.GROUP_NICK) {
            model = new PageUiModel();
            model.max_word = 30;
            model.min_line = 1;
            model.max_line = 5;
            model.menu_name = ConstantUtils.context.getString(R.string.tijiao);
            model.page_title = ConstantUtils.context.getString(R.string.my_group_nick);
            model.enableNullContent = true;
        } else if (type == ModifyType.CURR_NICK) {
            model = new PageUiModel();
            model.max_word = 30;
            model.min_line = 1;
            model.max_line = 5;
            model.menu_name = ConstantUtils.context.getString(R.string.tijiao);
            model.page_title = ConstantUtils.context.getString(R.string.nick);
            model.enableNullContent = false;
        } else if (type == ModifyType.CURR_SIGN) {
            model = new PageUiModel();
            model.max_word = 50;
            model.min_line = 5;
            model.max_line = 10;
            model.menu_name = ConstantUtils.context.getString(R.string.tijiao);
            model.page_title = ConstantUtils.context.getString(R.string.sign);
            model.enableNullContent = false;
        } else if (type == ModifyType.USER_REMARK_NAME) {
            model = new PageUiModel();
            model.max_word = 30;
            model.min_line = 1;
            model.max_line = 5;
            model.menu_name = ConstantUtils.context.getString(R.string.tijiao);
            model.page_title = ConstantUtils.context.getString(R.string.beizhu_name);
            model.enableNullContent = true;
        }
        return model;
    }

}
