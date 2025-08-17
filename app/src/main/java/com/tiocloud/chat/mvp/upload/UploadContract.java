package com.tiocloud.chat.mvp.upload;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/9
 * desc :
 */
public interface UploadContract {
    interface View extends BaseView {
        @Nullable
        Activity getActivity();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void uploadImg(List<String> chatlinkids, String filePath);

        public abstract void uploadVideo(List<String> chatlinkids, String filePath);

        public abstract void uploadFile(List<String> chatlinkids, String filePath);

        public abstract void uploadLocation(List<String> chatlinkids, String filePath, String lat, String lng, String address);
    }
}
