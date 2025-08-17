package com.tiocloud.chat.feature.player.mvp;

import android.app.Activity;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/6/10
 *     desc   :
 * </pre>
 */
public interface VideoPlayerContract {
    interface View extends BaseView {
        Activity getActivity();

        void onBackPressed();
    }

    abstract class Model extends BaseModel {
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void initPlayer(StandardGSYVideoPlayer videoPlayer, String videoUrl);

        public abstract void pauseVideo();

        public abstract void resumeVideo();

        public abstract boolean onBackPressed();
    }
}
