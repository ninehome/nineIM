package com.tiocloud.chat.feature.player.mvp;

import android.content.pm.ActivityInfo;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/6/10
 *     desc   :
 * </pre>
 */
public class VideoPlayerPresenter extends VideoPlayerContract.Presenter {
    private StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;

    public VideoPlayerPresenter(VideoPlayerContract.View view) {
        super(new VideoPlayerModel(), view, false);
    }

    @Override
    public void initPlayer(StandardGSYVideoPlayer videoPlayer, String videoUrl) {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale((activity, shouldRequest) -> shouldRequest.again(true))
                .callback((isAllGranted, granted, deniedForever, denied) -> setupPlayer(videoPlayer, videoUrl))
                .request();
    }

    private void setupPlayer(StandardGSYVideoPlayer videoPlayer, String videoUrl) {
        this.videoPlayer = videoPlayer;
        videoPlayer.setUp(videoUrl, true, null);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(getView().getActivity(), videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(v -> orientationUtils.resolveByClick());
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(v -> getView().onBackPressed());
        videoPlayer.startPlayLogic();
    }

    @Override
    public void pauseVideo() {
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
        }
    }

    @Override
    public void resumeVideo() {
        if (videoPlayer != null) {
            videoPlayer.onVideoResume();
        }
    }

    @Override
    public boolean onBackPressed() {
        //先返回正常状态
        if (orientationUtils != null && orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && videoPlayer != null) {
            videoPlayer.getFullscreenButton().performClick();
            return true;
        }
        //释放所有
        if (videoPlayer != null) {
            videoPlayer.setVideoAllCallBack(null);
        }
        return false;
    }

    @Override
    public void detachView() {
        super.detachView();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }
}
