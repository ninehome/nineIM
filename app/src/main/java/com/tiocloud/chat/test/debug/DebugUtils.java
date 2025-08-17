package com.tiocloud.chat.test.debug;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.ViewGroup;

import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/28
 *     desc  : utils about debug
 * </pre>
 */
public class DebugUtils {

    private DebugUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    static {
        getApp().registerActivityLifecycleCallbacks(ActivityLifecycleImpl.instance);
    }

    public static Application getApp() {
        return Utils.getApp();
    }

    static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

        private static ActivityLifecycleImpl instance = new ActivityLifecycleImpl();

        private int mConfigCount = 0;
        private ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (mConfigCount < 0) {
                ++mConfigCount;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            ((ViewGroup) activity.findViewById(android.R.id.content)).addView(DebugIcon.getInstance(), mParams);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            ((ViewGroup) activity.findViewById(android.R.id.content)).removeView(DebugIcon.getInstance());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
