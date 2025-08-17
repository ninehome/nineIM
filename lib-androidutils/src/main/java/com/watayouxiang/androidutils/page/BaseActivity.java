package com.watayouxiang.androidutils.page;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.BarUtils;
import com.watayouxiang.androidutils.util.ReflectionUtil;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.httpclient.TioHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc :
 */
public abstract class BaseActivity extends AppCompatActivity {

    // ====================================================================================
    // toolbar
    // ====================================================================================

    private Toolbar toolbar;
    private OptionsMenu optionsMenu;

    /**
     * 设置菜单
     *
     * @param menu 菜单
     */
    public void setOptionsMenu(OptionsMenu menu) {
        optionsMenu = menu;
        // 通知系统更新菜单
        supportInvalidateOptionsMenu();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolBar(int toolBarId) {
        setToolBar(toolBarId, null);
    }

    /**
     * 设置 Toolbar
     *
     * @param toolBarId ToolbarId
     * @param options   配置
     */
    public void setToolBar(int toolBarId, @Nullable ToolBarOptions options) {
        toolbar = findViewById(toolBarId);
        if (options != null) {
            // 标题
            toolbar.setTitle(options.getTitle());
            // 副标题
            toolbar.setSubtitle(options.getSubTitle());
            // logo
            toolbar.setLogo(options.getLogo());
            // 返回键
            Drawable navigationIcon = options.getNavigationIcon();
            if (navigationIcon != null) {
                toolbar.setNavigationIcon(navigationIcon);
                toolbar.setContentInsetStartWithNavigation(0);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        }
        setSupportActionBar(toolbar);
    }

    /**
     * 显隐原生返回键
     *
     * @param showHomeAsUp 显隐
     */
    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (toolbar != null) {
            toolbar.setTitle(titleId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (optionsMenu != null) {
            getMenuInflater().inflate(optionsMenu.getMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (optionsMenu != null) {
            optionsMenu.onOptionsItemSelected(this, item);
        }
        return super.onOptionsItemSelected(item);
    }

    // ====================================================================================
    // fragment
    // ====================================================================================

    public @Nullable
    <T extends Fragment> Fragment findFragmentByClass(Class<T> tClass) {
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragments = manager.getFragments();
        for (Fragment f : fragments) {
            if (f.getClass() == tClass) {
                return f;
            }
        }
        return null;
    }

    public <T extends BaseFragment> void replaceFragment(T fragment) {
        replaceFragment(fragment, false);
    }

    /**
     * 替换fragment
     *
     * @param fragment       {@link BaseFragment}
     * @param addToBackStack 是否添加到回退栈
     */
    public <T extends BaseFragment> void replaceFragment(T fragment, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(fragment.getContainerId(), fragment);

        if (addToBackStack) {
            /*
            方法作用：将此事务添加到回退栈
            参数说明：name是这次回退操作的一个名称（或标识），不需要可以传null。
            1、按下手机的back键，Activity会回调onBackPressed()方法，移除一个Fragment，直至回退栈清空。
            2、当前回退栈已空，程序才会退出。
             */
            transaction.addToBackStack(null);
        }

        // 安排一个事务的提交，但是允许Activity的状态保存之后提交。
        transaction.commitAllowingStateLoss();
    }

    public <T extends BaseFragment> T addFragment(T fragment) {
        List<T> fragments = new ArrayList<>(1);
        fragments.add(fragment);
        return addFragments(fragments).get(0);
    }

    /**
     * 添加fragments
     *
     * @param fragments {@link BaseFragment}集合
     * @param <T>       {@link BaseFragment}的子类
     * @return 堆栈中的fragment集合
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseFragment> List<T> addFragments(List<T> fragments) {
        List<T> stackFragments = new ArrayList<>(fragments.size());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        boolean commit = false;

        for (int i = 0; i < fragments.size(); i++) {
            T fragment = fragments.get(i);
            int id = fragment.getContainerId();

            // 堆栈中不存在才需要提交
            T stackFragment = (T) manager.findFragmentById(id);
            if (stackFragment == null) {
                transaction.add(id, fragment);
                commit = true;
                stackFragment = fragment;
            }

            stackFragments.add(i, stackFragment);
        }

        // 安排一个事务的提交，但是允许Activity的状态保存之后提交。
        if (commit) {
            transaction.commitAllowingStateLoss();
        }

        return stackFragments;
    }

    public <T extends BaseFragment> void hideFragment(T fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }

    public <T extends BaseFragment> void showFragment(T fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    public <T extends BaseFragment> void removeFragment(T fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        FragmentManager fm = getSupportFragmentManager();
        ReflectionUtil.invokeMethod(fm, "noteStateNotSaved", null);
    }

    // ====================================================================================
    // life method
    // ====================================================================================

    /**
     * 创建
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TioLogger.i(String.format(Locale.getDefault(), "%s#onCreate", getClass().getSimpleName()));
        // 设置状态栏黑色
        if (getNeedSetStatusBar()){
            BarUtils.setStatusBarLightMode(this, true);
        }

    }

    public boolean getNeedSetStatusBar(){
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        TioLogger.i(String.format(Locale.getDefault(), "%s#onNewIntent", getClass().getSimpleName()));
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TioLogger.i(String.format(Locale.getDefault(), "%s#onDestroy", getClass().getSimpleName()));
        // 取消网络请求
        TioHttpClient.cancel(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TioLogger.i(String.format(Locale.getDefault(), "%s#onActivityResult(requestCode=%d, resultCode=%d)", getClass().getSimpleName(), requestCode, resultCode));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TioLogger.i(String.format(Locale.getDefault(), "%s#onRequestPermissionsResult(requestCode=%d)", getClass().getSimpleName(), requestCode));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        invokeFragmentManagerNoteStateNotSaved();
        super.onBackPressed();
    }

    @Override
    public Resources getResources() {
        // app 字体不跟随系统字体改变
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    private int onStartCount = 0;
    private int onResumeCount = 0;
    private boolean isForeground = false;

    /**
     * 可见
     */
    @Override
    protected void onStart() {
        super.onStart();
        onStart(++onStartCount);
    }

    public void onStart(int count) {

    }

    /**
     * 不可见
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 在前台
     */
    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        onResume(++onResumeCount);
    }

    public void onResume(int count) {

    }

    /**
     * 不在前台
     */
    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    public boolean isForeground() {
        return isForeground;
    }

    // ====================================================================================
    // extension
    // ====================================================================================

    public BaseActivity getActivity() {
        return this;
    }

    public void setBackgroundColor(@ColorInt int color) {
        getWindow().setBackgroundDrawable(new ColorDrawable(color));
    }

    // ====================================================================================
    // statusBar
    // ====================================================================================

    public void addMarginTopEqualStatusBarHeight(View view) {
        BarUtils.addMarginTopEqualStatusBarHeight(view);
    }

    public void hideStatusBar() {
        BarUtils.transparentStatusBar(this);
    }

    /**
     * 设置状态栏模式
     *
     * @param isLightMode true 黑色, false 白色
     */
    public void setStatusBarLightMode(boolean isLightMode) {
        BarUtils.setStatusBarLightMode(this, isLightMode);
    }

    /**
     * 设置状态栏的颜色
     * <p>
     * 注意：该调用，会取消状态栏的高度
     *
     * @param color 颜色
     */
    public void setStatusBarColor(@ColorInt final int color) {
        BarUtils.setStatusBarColor(this, color);
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
