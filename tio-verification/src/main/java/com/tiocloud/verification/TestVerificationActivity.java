package com.tiocloud.verification;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tiocloud.verification.widget.BlockPuzzleDialog;
import com.tiocloud.verification.widget.TioBlockPuzzleDialog;
import com.tiocloud.verification.widget.WordCaptchaDialog;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 12/18/20
 *     desc   :
 *
 *     来自：https://gitee.com/anji-plus/captcha/tree/master/view
 *
 * </pre>
 */
public class TestVerificationActivity extends BaseActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestVerificationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vercode_activity_login);
        TextView bGo = findViewById(R.id.bGo);
        TextView bGo_word = findViewById(R.id.bGo_word);

        bGo.setOnClickListener(this);
        bGo_word.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bGo) {
            BlockPuzzleDialog blockPuzzleDialog = new BlockPuzzleDialog(this);
            blockPuzzleDialog.setOnResultsListener(result -> {
                //todo 二次校验回调结果
                String s = result;
            });
            blockPuzzleDialog.show();
        } else if (id == R.id.bGo_word) {
            WordCaptchaDialog wordCaptchaDialog = new WordCaptchaDialog(this);
            wordCaptchaDialog.setOnResultsListener(result -> {
                //todo 二次校验回调结果
                String s = result;
            });
            wordCaptchaDialog.show();
        }
    }

    public void onClick_tioBlockPuzzle(View view) {
        TioBlockPuzzleDialog blockPuzzleDialog = new TioBlockPuzzleDialog(this);
        blockPuzzleDialog.setOnResultsListener(result -> {
            //todo 二次校验回调结果
            String s = result;
        });
        blockPuzzleDialog.show();
    }
}
