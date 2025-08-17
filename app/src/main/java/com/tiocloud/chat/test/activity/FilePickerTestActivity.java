package com.tiocloud.chat.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.watayouxiang.demoshell.DemoActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.androidutils.tool.FilePicker;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/29
 *     desc   :
 * </pre>
 */
public class FilePickerTestActivity extends DemoActivity {
    private TextView filePath;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        filePath = findViewById(R.id.file_path);
    }

    @Override
    protected int getHolderViewId() {
        return R.layout.tio_test_file_picker_view;
    }

    @Override
    protected ListData getListData() {
        return new ListData()
                .addClick("请选择文件", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FilePicker.openSystemFile(FilePickerTestActivity.this);
                    }
                })
                ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = FilePicker.onActivityResult(requestCode, resultCode, data);
        if (result != null) {
            filePath.setText(result);
        }
    }
}
