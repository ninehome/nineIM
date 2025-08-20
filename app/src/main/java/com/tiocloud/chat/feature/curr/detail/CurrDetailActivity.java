package com.tiocloud.chat.feature.curr.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tiocloud.account.TioAccount;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.databinding.TioCurrInfoActivityBinding;
import com.tiocloud.chat.feature.curr.detail.mvp.CurrInfoContract;
import com.tiocloud.chat.feature.curr.detail.mvp.CurrInfoPresenter;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.dialog.tio.PicSelectDialog;
import com.tiocloud.chat.widget.dialog.tio.SexSelectDialog;
import com.watayouxiang.androidutils.engine.EasyPhotosEngine;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.util.UrlUtil;
import com.watayouxiang.androidutils.widget.CommonTextInputDialog;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UpdateAvatarReq;
import com.watayouxiang.httpclient.model.request.UpdateNickReq;
import com.watayouxiang.httpclient.model.request.UpdateSexReq;
import com.watayouxiang.httpclient.model.request.UpdateSignReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.imclient.utils.DeviceUtils;
import com.watayouxiang.qrcode.feature.qrcode_my.MyQRCodeActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * author : TaoWang
 * date : 2020/3/12
 * desc : 个人信息页
 */
public class CurrDetailActivity extends TioActivity implements CurrInfoContract.View {
    private final static int REQ_CODE_IMAGE_GIF = 1333;// 选择图片、gif

    private TioCurrInfoActivityBinding binding;
    private CurrInfoPresenter presenter;
    private Uri mDestination;

    public static void start(Context context) {
        Intent starter = new Intent(context, CurrDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.tio_curr_info_activity);
        binding.titleBar.setTitle(getString(R.string.person_info));
        presenter = new CurrInfoPresenter(this);
        mDestination = Uri.fromFile(new File(this.getCacheDir(), "cropImage.jpeg"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_IMAGE_GIF) {
            // 容错处理
            if (data == null) return;
            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            // 容错处理
            if (resultPhotos == null || resultPhotos.isEmpty()) {
                return;
            }

            // 判断类型
            Photo photo = resultPhotos.get(0);
            if (UrlUtil.isImageSuffix(photo.path) || UrlUtil.isGifSuffix(photo.path)) {
                UCrop.Options options = new UCrop.Options();
                // 修改标题栏颜色
                options.setToolbarColor(this.getResources().getColor(R.color.white));
                options.setStatusBarColor(this.getResources().getColor(R.color.white));
                options.setToolbarWidgetColor(this.getResources().getColor(R.color.black));
                // 隐藏底部工具
                options.setHideBottomControls(true);
                // 图片格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                // 设置图片压缩质量
                options.setCompressionQuality(100);
                // 上传图片
                UCrop.of(photo.uri, mDestination)
                        // 长宽比
                        .withAspectRatio(1, 1)
                        // 图片大小
                        .withMaxResultSize(512, 512)
                        // 配置参数
                        .withOptions(options)
                        .start(this, UCrop.REQUEST_CROP);
//                uploadAvatar(photo.path);
            }
        }else if (requestCode == UCrop.REQUEST_CROP){
            handleCropResult(data);
        }
    }

    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        try {
            final Uri resultUri = UCrop.getOutput(result);
            Luban.with(this).load(resultUri).ignoreBy(100).setTargetDir(this.getCacheDir().getAbsolutePath()).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                    LogUtils.i("压缩开始");
                }

                @Override
                public void onSuccess(File file) {
                    LogUtils.i("压缩成功");
                    uploadAvatar(file.getPath());
                }

                @Override
                public void onError(Throwable e) {
                    LogUtils.e("压缩失败" + e.getMessage());
                    uploadAvatar(resultUri.getPath());
                }
            }).launch();
        }catch (Exception e){
            Toast.makeText(this, getString(R.string.cannot_crop_pic), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void uploadAvatar(String path) {
        UpdateAvatarReq req = new UpdateAvatarReq(path);
        req.setCancelTag(this);
        req.upload(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                ToastUtils.showShort(getString(R.string.repair_success));
                presenter.updateUIData();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.updateUIData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onUserCurrResp(UserCurrResp userCurr) {
        if (TioConfig.OpenCloseConfig.isMapEnable()){
            binding.rlLocation.setVisibility(View.VISIBLE);
        }else {
            binding.rlLocation.setVisibility(View.GONE);
        }
        binding.hivAvatar.tio_roundAvatar(userCurr.avatar);
        binding.tvNick.setText(StringUtil.nonNull(userCurr.nick));
        binding.tvGender.setText(StringUtil.nonNull(userCurr.getSex()));
        binding.tvSign.setText(StringUtil.nonNull(userCurr.sign));
        binding.tvRegion.setText(StringUtil.nonNull(userCurr.getRegion()));
        binding.tvEmail.setText(StringUtil.nonNull(userCurr.email));
        binding.tvPhone.setText(StringUtil.nonNull(userCurr.phone));
        binding.rlModifyPwd.setOnClickListener(view -> {
            if (TioConfig.OpenCloseConfig.modifyPwdByOldPwd()){
                com.tiocloud.chat.feature.account.pwd.ModifyPwdActivity.start(getActivity());
            }else {
                com.tiocloud.account.feature.modify_pwd.ModifyPwdActivity.start(getActivity());
            }
        });
        binding.rlNick.setOnClickListener(view -> {
//            ModifyActivity.start_curr(view.getContext(), ModifyType.CURR_NICK, userCurr.nick);
            new CommonTextInputDialog(this)
                    .setEditHeight(100)
                    .setTopTitle(getString(R.string.nick))
                    .setSubTitle(getString(R.string.good_nick))
                    .setPositiveText(getString(R.string.save))
                    .setMaxLimit(20)
                    .setEdittext(userCurr.nick)
                    .showClearButton(false)
                    .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
                        @SuppressLint("StringFormatInvalid")
                        @Override
                        public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
                            if (TextUtils.isEmpty(submitTxt)){
                                ToastUtils.showShort(getString(R.string.nick_not_empty));
                                return;
                            }
                            try {
                                int limit = TioAccount.isOA ? 4 : 1;
                                if (submitTxt.trim().getBytes("utf-8").length < limit) {
                                    TioToast.showShort(String.format(Locale.getDefault(), getString(R.string.nick_not_low),
                                            limit));
                                    return;
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                            UpdateNickReq updateNickReq = new UpdateNickReq(submitTxt);
                            updateNickReq.setCancelTag(this);
                            updateNickReq.post(new TioCallback<Void>() {
                                @Override
                                public void onTioSuccess(Void aVoid) {
//                                    proxy.onSuccess(null);
                                    ToastUtils.showShort(getString(R.string.repair_success));
                                    presenter.updateUIData();
                                }

                                @Override
                                public void onTioError(String msg) {
                                }
                            });
                        }

                        @Override
                        public void onClickNegative(View view, CommonTextInputDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        });
        binding.rlSign.setOnClickListener(view -> {
            //ModifyActivity.start_curr(view.getContext(), ModifyType.CURR_SIGN, userCurr.sign)
            new CommonTextInputDialog(this)
                    .setEditHeight(200)
                    .setTopTitle(getString(R.string.personal_sign))
                    .setSubTitle(getString(R.string.good_sign))
                    .setPositiveText(getString(R.string.save))
                    .setMaxLimit(50)
                    .setEdittext(userCurr.sign)
                    .showClearButton(false)
                    .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
                        @Override
                        public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
                            dialog.dismiss();
                            UpdateSignReq req = new UpdateSignReq(submitTxt);
                            req.setCancelTag(this);
                            req.post(new TioCallback<Void>() {
                                @Override
                                public void onTioSuccess(Void aVoid) {
//                                    proxy.onSuccess(null);
                                    ToastUtils.showShort(getString(R.string.save_success));
                                    presenter.updateUIData();
                                }

                                @Override
                                public void onTioError(String msg) {
//                                    proxy.onFailure(msg);
                                    presenter.updateUIData();
                                }
                            });
                        }

                        @Override
                        public void onClickNegative(View view, CommonTextInputDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();


        });
        binding.rlGender.setOnClickListener(view -> {
//            presenter.showGenderDialog(view.getContext(), userCurr.sex);
            new SexSelectDialog(CurrDetailActivity.this)
                    .setOnBtnListener(new SexSelectDialog.OnBtnListener() {
                        @Override
                        public void onClickPositive(View view, int sex, SexSelectDialog dialog) {
                            reqUpdateSex(String.valueOf(sex));
                            dialog.dismiss();
                        }

                        @Override
                        public void onClickNegative(View view, SexSelectDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        });
        binding.vgAvatar.setOnClickListener(view -> {
            /*presenter.getAvatarDialog().show()*/
            new PicSelectDialog(CurrDetailActivity.this)
                    .setOnBtnListener(new PicSelectDialog.OnBtnListener() {
                        @Override
                        public void onClickPositive(View view, int sex, PicSelectDialog dialog) {
                            if (sex == 0){
                                EasyPhotos.createCamera(CurrDetailActivity.this, true)
                                        .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                                        .start(REQ_CODE_IMAGE_GIF);
                            }else {
                                EasyPhotos.createAlbum(CurrDetailActivity.this, false, true, EasyPhotosEngine.getInstance())
                                        .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                                        .setPuzzleMenu(false)
                                        .setCleanMenu(false)
                                        .setCount(1)
                                        .setVideo(false)
                                        .setGif(true)
                                        .start(REQ_CODE_IMAGE_GIF);
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onClickNegative(View view, PicSelectDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        });

        if (TioConfig.OpenCloseConfig.showMyQrcode()){
            findViewById(R.id.rl_qrcode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyQRCodeActivity.start(getActivity());
                }
            });
        }else {
            findViewById(R.id.rl_qrcode).setVisibility(View.GONE);
        }

        binding.tvUuid.setText(String.valueOf(userCurr.id));
        binding.tvName.setText(userCurr.loginname);
    }

    private void reqUpdateSex(String sex) {
        UpdateSexReq req = new UpdateSexReq(sex);
        req.setCancelTag(this);
        req.post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                ToastUtils.showShort(getString(R.string.repair_success));
                presenter.updateUIData();
            }

            @Override
            public void onTioError(String msg) {

            }
        });
    }
}
