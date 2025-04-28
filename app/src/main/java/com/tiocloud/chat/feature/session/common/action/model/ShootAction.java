package com.tiocloud.chat.feature.session.common.action.model;

import android.content.Intent;

import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseUploadAction;
import com.tiocloud.chat.feature.session.common.model.RequestCode;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.androidutils.util.TioLogger;

import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc : 拍照
 */
public class ShootAction extends BaseUploadAction {
    public ShootAction() {
        super(R.drawable.icon_im_paishe, R.string.shoot);
    }

    @Override
    public void onClick() {
        EasyPhotos.createCamera(fragment, true)
                .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                .start(RequestCode.TAKE_PHOTO);

        // 取消 "App进入后台时，自动断开连接"
        TioIMClient.getInstance().setAutoDisconnectOnAppBackground(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.TAKE_PHOTO) {

            // 恢复 "App进入后台时，自动断开连接"
            TioIMClient.getInstance().setAutoDisconnectOnAppBackground(true);

            // 容错处理
            if (data == null) return;

            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            //返回图片地址集合时如果你需要知道用户选择图片时是否选择了原图选项，用如下方法获取
            boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
            TioLogger.i(String.valueOf(resultPhotos));

            // 容错处理
            if (resultPhotos == null || resultPhotos.size() == 0) {
                return;
            }
            if (fragment == null || fragment.getChatLinkIds() == null || fragment.getChatLinkIds().size() < 1) {
                return;
            }

            // 上传图片
            Photo photo = resultPhotos.get(0);
            getUploadPresenter().uploadImg(fragment.getChatLinkIds(), photo.path);

        }
    }
}
