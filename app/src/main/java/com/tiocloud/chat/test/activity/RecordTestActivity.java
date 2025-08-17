package com.tiocloud.chat.test.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.tiocloud.chat.R;
import com.watayouxiang.audiorecord.TioAudioRecorder;
import com.watayouxiang.audiorecord.WtMediaPlayer;
import com.watayouxiang.audiorecord.WtMediaRecorder;
import com.watayouxiang.demoshell.DemoActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.androidutils.widget.TioToast;

import java.io.File;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/03
 *     desc   :
 * </pre>
 */
public class RecordTestActivity extends DemoActivity implements WtMediaPlayer.OnPlayerListener, WtMediaRecorder.OnRecorderListener, TioAudioRecorder.OnRecorderListener {

    private String testAudioUrl = "https://res.t-io.org/wx/upload/video/22/9010/1119563/88097616/74541310984/33/180013/1290950731423162368.m4a";

    private WtMediaRecorder mediaRecorder = new WtMediaRecorder();
    private WtMediaPlayer mediaPlayer = new WtMediaPlayer();
    private TioAudioRecorder tioRecorder = new TioAudioRecorder("-358");

    public RecordTestActivity() {
        mediaPlayer.setOnPlayerListener(this);
        mediaPlayer.setAudioFocusFlag(true);
//        mediaPlayer.setBackgroundStopFlag(true);
        mediaRecorder.setOnRecorderListener(this);
        tioRecorder.setOnRecorderListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        Button tv_btn = findViewById(R.id.tv_btn);
        tioRecorder.initRecordView(tv_btn);
    }

    @Override
    protected int getHolderViewId() {
        return R.layout.view_test_record;
    }

    @Override
    protected ListData getListData() {
        return new ListData()
                .addSection("文件录音")
                .addClick("获取权限", v -> mediaRecorder.requestPermissions(null))
                .addClick("是否同意所有权限", v -> {
                    boolean grantedAllPermission = mediaRecorder.isGrantedAllPermission();
                    TioToast.showShort(String.valueOf(grantedAllPermission));
                })
                .addClick("开始录音", v -> mediaRecorder.start())
                .addClick("停止录音", v -> mediaRecorder.stop())
                .addSection("播放文件录音")
                .addClick("初始化 url string", v -> mediaPlayer.initUrl(testAudioUrl))
                .addClick("开始播放", v -> mediaPlayer.start())
                .addClick("暂停播放", v -> mediaPlayer.pause())
                .addClick("停止播放", v -> mediaPlayer.stop())
                .addClick("重置", v -> mediaPlayer.reset())
                .addClick("音量 0.5", v -> mediaPlayer.setVolume(0.5f))
                .addClick("音量 1.0", v -> mediaPlayer.setVolume(1.0f))
                .addClick("开启 循环", v -> mediaPlayer.setLooping(true))
                .addClick("关闭 循环", v -> mediaPlayer.setLooping(false))
                .addClick("释放资源，不再使用", v -> mediaPlayer.release())
                ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaRecorder.release();
        mediaPlayer.release();
        tioRecorder.release();
    }

    // ====================================================================================
    // WtPlayer
    // ====================================================================================

    @Override
    public void onWtPlayerStart() {
        TioToast.showShort("开始播放");
    }

    @Override
    public void onWtPlayerPause() {
        TioToast.showShort("暂停播放");
    }

    @Override
    public void onWtPlayerStop() {
        TioToast.showShort("停止播放");
    }

    @Override
    public void onWtPlayerError(WtMediaPlayer.ErrorType type) {
        TioToast.showShort("播放出错：" + type);
    }

    @Override
    public void onWtPlayerCompletion() {
        TioToast.showShort("播放完成");
    }

    // ====================================================================================
    // WtRecorder
    // ====================================================================================

    @Override
    public void onWtRecorderStart() {

    }

    @Override
    public void onWtRecorderStop(boolean cancel) {

    }

    @Override
    public void onWtRecorderSuccess(long millisecond, @NonNull File audioFile) {
        TioToast.showShort("录音成功：" + audioFile.getAbsolutePath());
        mediaPlayer.initFile(audioFile);
    }

    @Override
    public void onWtRecorderError(int type) {
        TioToast.showShort("录音失败");
    }

    @Override
    public void onWtRecorderTick(int second) {

    }

    // ====================================================================================
    // TioRecorder
    // ====================================================================================

    @Override
    public void onShowToast(@NonNull String text) {

    }

    @Override
    public void onChanged(int type) {

    }

    @Override
    public void onRecorderStart() {

    }

    @Override
    public void onRecorderStop(boolean cancel) {

    }

    @Override
    public void onUploadAudioStart() {

    }

    @Override
    public void onUploadAudioFinish() {

    }
}
