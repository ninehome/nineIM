package com.tiocloud.chat.feature.session.common.action.model;

import android.content.Intent;

import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseUploadAction;
import com.tiocloud.chat.feature.session.common.model.RequestCode;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.androidutils.engine.EasyPhotosEngine;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.util.UrlUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 图片操作
 */
public class ImageAction extends BaseUploadAction {

    public ImageAction() {
        super(R.drawable.icon_im_xiangce, R.string.photo_album);

    }

    @Override
    public void onClick() {
        EasyPhotos.createAlbum(fragment, false, true, EasyPhotosEngine.getInstance())
                .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                .setPuzzleMenu(false)
                .setCleanMenu(false)
                .setCount(9)
                .setVideo(true)
                .setGif(true)
                .start(RequestCode.PICK_IMAGE_GIF_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.PICK_IMAGE_GIF_VIDEO) {
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

            for (Photo photo : resultPhotos) {
                LogUtils.w("zlb图片大小"+new File(photo.path).length()/1024);
                // 判断类型
                if (UrlUtil.isImageSuffix(photo.path) || UrlUtil.isGifSuffix(photo.path)) {
                    // 上传图片
                    getUploadPresenter().uploadImg(fragment.getChatLinkIds(), photo.path);
                } else {
                    // 上传视频
                    getUploadPresenter().uploadVideo(fragment.getChatLinkIds(), photo.path);
                }
            }
        }
    }
}
