package com.tiocloud.chat.widget.alertdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc :
 * <p>
 * https://github.com/watayouxiang/AlertDialogDemo
 */
public class AlertDialogDemo {

    public void showMyDialog(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        //取消外部点击隐藏
        alertDialog.setCanceledOnTouchOutside(false);
        //设置取消监听
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        //设置自定义布局
        View rootView = LayoutInflater.from(context).inflate(R.layout.tio_bottom_dialog_group_oper, null);
        rootView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.setView(rootView);
        alertDialog.show();
        //设置弹窗参数（注意：弹窗样式修改必须在 dialog.show() 方法后调用，否则不生效）
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            //dialog即使没有设置四周的margin也有margin，可以设置给dialog设置个background来解决
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //宽度为屏幕宽度的80%
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (context.getWindowManager().getDefaultDisplay().getWidth() * 0.8f);
            window.setAttributes(params);
        }
    }

}
