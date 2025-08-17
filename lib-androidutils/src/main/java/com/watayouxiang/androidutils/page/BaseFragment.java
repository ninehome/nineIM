package com.watayouxiang.androidutils.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.BarUtils;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.imclient.TioIMClient;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc :
 * Fragment lifecycle see: https://developer.android.google.cn/guide/components/fragments
 * FragmentTransaction see: https://www.jianshu.com/p/5761ee2d3ea1
 * <p>
 * 1、保存 fragment 所在的容器id（fragmentContainerId）
 * 2、拓展方法
 */
public abstract class BaseFragment extends Fragment {

    // ====================================================================================
    // container
    // ====================================================================================

    // 容器id
    private int containerId;

    /**
     * 获取容器id
     *
     * @return 容器id
     */
    public int getContainerId() {
        return containerId;
    }

    /**
     * 设置容器id
     *
     * @param containerId 容器id
     */
    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    // ====================================================================================
    // statusBar
    // ====================================================================================

    /**
     * 设置状态栏的颜色
     * <p>
     * 注意：该调用，会取消状态栏的高度
     *
     * @param color 颜色
     */
    public void setStatusBarColor(@ColorInt final int color) {
        BarUtils.setStatusBarColor(getActivity(), color);
    }

    public void addMarginTopEqualStatusBarHeight(View view) {
        BarUtils.addMarginTopEqualStatusBarHeight(view);
    }

    /**
     * 设置自定义状态栏
     *
     * @param fakeStatusBar 自定义状态栏
     */
    public void setStatusBarCustom(@NonNull ViewGroup fakeStatusBar) {
        BarUtils.setStatusBarCustom(fakeStatusBar);
    }

    /**
     * 设置状态栏模式
     *
     * @param isLightMode true 黑色, false 白色
     */
    public void setStatusBarLightMode(boolean isLightMode) {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        BarUtils.setStatusBarLightMode(activity, isLightMode);
    }

    // ====================================================================================
    // extension
    // ====================================================================================

    /**
     * @return 是否注册事件
     */
    public boolean isRegisterEvent() {
        return false;
    }

    public final <T extends View> T findViewById(@IdRes int id) {
        View view = getView();
        if (view == null)
            throw new NullPointerException("rootView is null");
        return view.findViewById(id);
    }

    protected void replaceRootView(int layoutId) {
        ViewGroup root = (ViewGroup) getView();
        if (root == null)
            throw new NullPointerException("rootView is null");
        root.removeAllViewsInLayout();
        View.inflate(root.getContext(), layoutId, root);
    }

    public BaseFragment getFragment() {
        return this;
    }

    // ====================================================================================
    // fragment life
    // ====================================================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisterEvent()) {
            TioIMClient.getInstance().getEventEngine().register(this);
            TioLogger.d(getClass().getSimpleName() + ": register event");
        }
    }

    /**
     * 创建视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 初始化视图
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 销毁视图
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEvent()) {
            TioIMClient.getInstance().getEventEngine().unregister(this);
            TioLogger.d(getClass().getSimpleName() + ": unregister event");
        }
        // 取消网络请求
        TioHttpClient.cancel(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TioLogger.i(String.format(Locale.getDefault(), "%s#onActivityResult(requestCode=%d, resultCode=%d)", getClass().getSimpleName(), requestCode, resultCode));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TioLogger.i(String.format(Locale.getDefault(), "%s#onRequestPermissionsResult(requestCode=%d)", getClass().getSimpleName(), requestCode));
    }

    private int onStartCount = 0;
    private int onResumeCount = 0;
    private boolean isForeground = false;

    @Override
    public void onStart() {
        super.onStart();
        isForeground = true;
        onStart(++onStartCount);
    }

    public void onStart(int count) {

    }

    @Override
    public void onResume() {
        super.onResume();
        onResume(++onResumeCount);
    }

    public void onResume(int count) {

    }

    @Override
    public void onStop() {
        super.onStop();
        isForeground = false;
    }

    public boolean isForeground() {
        return isForeground;
    }

    // ====================================================================================
    // 使用ViewModelProvider简单的创建ViewModel
    // https://www.cnblogs.com/guanxinjing/p/13442423.html
    //
    // ViewModelProvider.Factory
    // https://www.cnblogs.com/guanxinjing/p/12198971.html
    // https://blog.csdn.net/qq_43377749/article/details/100856599
    //
    // ViewModel是什么
    // https://www.jianshu.com/p/35d143e84d42
    //
    // 注意事项：由于 ViewModel 生命周期可能比 activity 长，所以为了避免内存泄漏，
    //          禁止在 ViewModel 中持有 Context 或 activity 引用
    // ====================================================================================

    public <VM extends ViewModel> VM newViewModel(@NonNull Class<VM> viewModelClass) {
        return new ViewModelProvider(this).get(viewModelClass);
    }
}
