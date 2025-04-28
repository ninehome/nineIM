package com.tiocloud.chat.feature.session.common.action.model;

import android.content.Intent;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseUploadAction;
import com.watayouxiang.androidutils.tool.FilePicker;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 文件操作
 */
public class FileAction extends BaseUploadAction {
    public FileAction() {
        super(R.drawable.icon_im_wenjian, R.string.file);
    }

    @Override
    public void onClick() {
        FilePicker.openSystemFile(fragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filPath = FilePicker.onActivityResult(requestCode, resultCode, data);
        if (filPath != null) {
            getUploadPresenter().uploadFile(fragment.getChatLinkIds(), filPath);
        }
    }
}
