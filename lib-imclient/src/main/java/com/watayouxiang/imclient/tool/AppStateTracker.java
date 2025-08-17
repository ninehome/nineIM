package com.watayouxiang.imclient.tool;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * author : TaoWang
 * date : 2020/3/23
 * desc : 监听app前后台切换
 */
class AppStateTracker {
    public enum AppState {
        FOREGROUND, BACKGROUND
    }

    public interface OnAppStateChangeListener {
        void onAppStateChanged(@NonNull AppState state);
    }

    private AppState mAppState;
    private Application mApplication;
    private OnAppStateChangeListener mOnAppStateChangeListener;
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;

    /**
     * 获取当前状态
     *
     * @return {@link AppState}
     */
    public AppState getAppState() {
        return mAppState;
    }

    /**
     * 设置前后台切换监听
     *
     * @param listener 前后台切换监听器
     */
    public void setOnAppStateChangeListener(OnAppStateChangeListener listener) {
        mOnAppStateChangeListener = listener;
    }

    /**
     * 务必在 {@link Application#onCreate()} 中注册
     *
     * @param application Application
     */
    public void registerTracker(Application application) {
        this.mApplication = application;
        if (mActivityLifecycleCallbacks == null) {
            mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
                private int resumeActivityCount = 0;

                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {

                }

                @Override
                public void onActivityStarted(Activity activity) {
                    if (resumeActivityCount == 0) {
                        mAppState = AppState.FOREGROUND;
                        if (mOnAppStateChangeListener != null) {
                            mOnAppStateChangeListener.onAppStateChanged(AppState.FOREGROUND);
                        }
                    }
                    resumeActivityCount++;
                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {
                    resumeActivityCount--;
                    if (resumeActivityCount == 0) {
                        mAppState = AppState.BACKGROUND;
                        if (mOnAppStateChangeListener != null) {
                            mOnAppStateChangeListener.onAppStateChanged(AppState.BACKGROUND);
                        }
                    }
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            };
        }
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    /**
     * 注销
     */
    public void unregisterTracker() {
        if (mApplication != null && mActivityLifecycleCallbacks != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
            mApplication = null;
            mActivityLifecycleCallbacks = null;
        }
    }
}


