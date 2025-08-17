package com.watayouxiang.androidutils.engine;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;
import com.watayouxiang.androidutils.R;

import java.util.ArrayList;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/17
 *     desc   :
 * </pre>
 */
public abstract class MNImageBrowserUtils {

    /**
     * 获取资源地址
     */
    public abstract String getResUrl(String imgUrl);

    public void showPic(@NonNull View view, int currentPosition, @NonNull String... imgUrls) {
        /* 处理数据 */
        String imgUrl = null;
        ArrayList<String> imgUrlList = null;
        if (imgUrls.length == 0) {
            return;
        } else if (imgUrls.length == 1) {
            imgUrl = getResUrl(imgUrls[0]);
            currentPosition = 0;
        } else {
            imgUrlList = new ArrayList<>();
            for (String url : imgUrls) {
                imgUrlList.add(getResUrl(url));
            }
            if (currentPosition < 0 || currentPosition > imgUrlList.size() - 1) {
                currentPosition = 0;
            }
        }

        /* 显示图片预览 */
        MNImageBrowser imageBrowser = MNImageBrowser.with(view.getContext())
                // 图片加载器
                .setImageEngine(new MNImageBrowserEngine())
                // 手势下拉缩小效果是否开启
                .setOpenPullDownGestureEffect(true)
                // 自定义ProgressView，不设置默认默认没有
                .setCustomProgressViewLayoutID(R.layout.pic_viewer_progress)
                .setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public void onLongClick(FragmentActivity activity, ImageView view, int position, String url) {
                        //TODO 保存图片
                        Drawable drawable = view.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        PermissionUtils.permission(PermissionConstants.STORAGE)
                                .callback(new PermissionUtils.SimpleCallback() {
                                    @Override
                                    public void onGranted() {
                                        showBotomDialog(activity,bitmap);
                                    }

                                    @Override
                                    public void onDenied() {

                                    }
                                }).request();

                    }
                })
                // 设置当前位置
                .setCurrentPosition(currentPosition);

        if (imgUrl != null) {
            // 必须（setImageList和setImageUrl二选一，会覆盖）-设置单张图片
            imageBrowser.setImageUrl(getResUrl(imgUrl));
        } else if (imgUrlList != null) {
            // 必须（setImageList和setImageUrl二选一，会覆盖）-图片集合
            imageBrowser.setImageList(imgUrlList);
        }

        imageBrowser.show(view);
    }

    public void showBotomDialog(Context context,Bitmap bitmap){
        Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.save_pic_bottom_dialog, null);
        TextView tv_save = contentView.findViewById(R.id.tv_save);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        bottomDialog.setContentView(contentView);
        bottomDialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth();
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.tio_bottom_dialog_anim);
        bottomDialog.getWindow().setDimAmount(0);
        bottomDialog.show();
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.save2Album(bitmap,Bitmap.CompressFormat.JPEG);
                bottomDialog.dismiss();
            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
    }

    public void showPic(@NonNull View view, @NonNull String imgUrl) {
        showPic(view, 0, imgUrl);
    }

    public void clickViewShowPic(@NonNull View view, int currentPosition, @NonNull String... imgUrls) {
        if (!view.isClickable()) {
            view.setClickable(true);
        }
        view.setOnClickListener(v -> showPic(v, currentPosition, imgUrls));
    }

    public void clickViewShowPic(@NonNull View view, @NonNull String imgUrl) {
        clickViewShowPic(view, 0, imgUrl);
    }

}
