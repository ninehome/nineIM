package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.util.FileUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.imclient.model.body.wx.msg.FileIconType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFile;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc :
 */
public class MsgFileViewHolder extends MsgBaseViewHolder {

    private ImageView iv_fileIcon;
    private TextView tv_fileName;
    private TextView tv_fileSize;
    private InnerMsgFile msgFile;

    public MsgFileViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.tio_msg_item_file;
    }

    @Override
    protected void inflateContent() {
        iv_fileIcon = findViewById(R.id.iv_fileIcon);
        tv_fileName = findViewById(R.id.tv_fileName);
        tv_fileSize = findViewById(R.id.tv_fileSize);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        msgFile = (InnerMsgFile) getMessage().getContentObj();
        if (msgFile == null) {
            tv_fileName.setText("");
            tv_fileSize.setText("");
            iv_fileIcon.setImageResource(R.drawable.tio_file_icon_other);
            return;
        }
        tv_fileName.setText(StringUtil.nonNull(msgFile.filename));
        tv_fileSize.setText(FileUtil.formatFileSize(msgFile.size));
        iv_fileIcon.setImageResource(getFileIconId(msgFile.fileicontype));
    }

    @Override
    protected boolean isShowContentBg() {
        return true;
    }

    @Override
    protected void onContentClick(View view) {
        showDownloadDialog(view.getContext());
    }

    private void showDownloadDialog(Context context) {
        if (msgFile == null) return;
        new EasyOperDialog.Builder(String.format(Locale.getDefault(), getContext().getString(R.string.download_s), msgFile.filename))
                .setPositiveBtnTxt(getContext().getString(R.string.download))
                .setNegativeBtnTxt(getContext().getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        downloadFile();
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(context);
    }

    private void downloadFile() {
        if (msgFile == null) return;
        getAdapter().getDownloadPresenter().downloadWithTip(msgFile.url, getActivity());
    }

    @DrawableRes
    private int getFileIconId(int fileicontype) {
        int fileIconId;
        switch (fileicontype) {
            case FileIconType.PDF:
                fileIconId = R.drawable.tio_file_icon_pdf;
                break;
            case FileIconType.TXT:
                fileIconId = R.drawable.tio_file_icon_txt;
                break;
            case FileIconType.DOC:
                fileIconId = R.drawable.tio_file_icon_doc;
                break;
            case FileIconType.XLS:
                fileIconId = R.drawable.tio_file_icon_xls;
                break;
            case FileIconType.PPT:
                fileIconId = R.drawable.tio_file_icon_ppt;
                break;
            case FileIconType.APK:
                fileIconId = R.drawable.tio_file_icon_apk;
                break;
            case FileIconType.IMG:
                fileIconId = R.drawable.tio_file_icon_img;
                break;
            case FileIconType.ZIP:
                fileIconId = R.drawable.tio_file_icon_zip;
                break;
            case FileIconType.VIDEO:
                fileIconId = R.drawable.tio_file_icon_video;
                break;
            case FileIconType.AUDIO:
                fileIconId = R.drawable.tio_file_icon_audio;
                break;
            default:
                fileIconId = R.drawable.tio_file_icon_other;
                break;
        }
        return fileIconId;
    }
}
