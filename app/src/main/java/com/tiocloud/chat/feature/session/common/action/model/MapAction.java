package com.tiocloud.chat.feature.session.common.action.model;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseUploadAction;
import com.tiocloud.chat.yanxun.map.MapPickerActivity;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.util.UrlUtil;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UploadCommonFileReq;
import com.watayouxiang.httpclient.model.response.UploadCommonFileResp;
import com.watayouxiang.permission.dialog.AppSettingsDialog;

import java.util.ArrayList;

public class MapAction extends BaseUploadAction {
    private static final int REQUEST_CODE_SELECT_Locate = 54;
//    public transient Activity activity;

    public MapAction() {
        super(R.drawable.icon_im_weizhi, R.string.chat_loc);
    }

    @Override
    public void onClick() {
        reqStoragePermission(() -> toMap());
    }

    private void toMap(){
        Intent intent = new Intent(fragment.getActivity(), MapPickerActivity.class);
        intent.putExtra(MapPickerActivity.EXTRA_FORM_CAHT_ACTIVITY, true);
        fragment.getActivity().startActivityForResult(intent, REQUEST_CODE_SELECT_Locate);
    }


    private static void reqStoragePermission(Runnable runnable) {
        //PermissionUtil.requestLocationPermissions(this, 0x01);
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale((activity2, shouldRequest) -> shouldRequest.again(true))
                .callback((isAllGranted, granted, deniedForever, denied) -> {
                    if (isAllGranted) {
                        runnable.run();
                    } else if (!deniedForever.isEmpty()) {
                        ToastUtils.showShort("存储权限被禁用，请打开权限！");
                        PermissionUtils.launchAppDetailsSettings();
                    }
                })
                .request();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_Locate && resultCode == Activity.RESULT_OK){
            if (data == null){
                return;
            }
            double lat = data.getDoubleExtra(MapPickerActivity.EXTRA_LATITUDE, 0);
            double lng = data.getDoubleExtra(MapPickerActivity.EXTRA_LONGITUDE, 0);
            String address = data.getStringExtra(MapPickerActivity.EXTRA_ADDRESS);
            String snapshot = data.getStringExtra(MapPickerActivity.EXTRA_SNAPSHOT);

            if (fragment == null || fragment.getChatLinkIds() == null || fragment.getChatLinkIds().size() < 1) {
                return;
            }
            getUploadPresenter().uploadLocation(fragment.getChatLinkIds(), snapshot, String.valueOf(lat), String.valueOf(lng), address);
        }
    }
}
