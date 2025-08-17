package com.watayouxiang.imclient.tool;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

/**
 * 网络广播接收者
 */
class NetworkBroadcastReceiver extends BroadcastReceiver {

    public enum NetworkState {
        DISCONNECTED, WIFI, MOBILE
    }

    public interface OnNetworkChangeListener {
        void onNetworkChanged(@NonNull NetworkState networkState);
    }

    private Context mContext;
    private ConnectivityManager mConnectivityManager;
    private OnNetworkChangeListener mOnNetworkChangeListener;

    /**
     * 需要权限：{@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     *
     * @param context Context
     * @param intent  Intent
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (mOnNetworkChangeListener == null) return;
        if (mConnectivityManager == null) return;

        NetworkInfo wifiNetworkInfo = null;
        try {
            wifiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } catch (Exception ignored) {
        }
        if (wifiNetworkInfo == null) return;

        NetworkInfo mobileNetworkInfo = null;
        try {
            mobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        } catch (Exception ignored) {
        }
        if (mobileNetworkInfo == null) return;

        NetworkInfo.State wifiState = wifiNetworkInfo.getState();
        NetworkInfo.State mobileState = mobileNetworkInfo.getState();

        if (mobileState == NetworkInfo.State.CONNECTED && wifiState != NetworkInfo.State.CONNECTED) {
            mOnNetworkChangeListener.onNetworkChanged(NetworkState.MOBILE);
        } else if (wifiState == NetworkInfo.State.CONNECTED && mobileState != NetworkInfo.State.CONNECTED) {
            mOnNetworkChangeListener.onNetworkChanged(NetworkState.WIFI);
        } else if (wifiState != NetworkInfo.State.CONNECTED && mobileState != NetworkInfo.State.CONNECTED) {
            mOnNetworkChangeListener.onNetworkChanged(NetworkState.DISCONNECTED);
        }
    }

    /**
     * 设置网络变化监听器
     *
     * @param listener 网络变化监听器
     */
    public void setOnNetworkChangeListener(OnNetworkChangeListener listener) {
        mOnNetworkChangeListener = listener;
    }

    /**
     * 注册接收网络广播
     *
     * @param context Context
     */
    public void register(Context context) {
        if (mConnectivityManager != null) return;// 避免多次调用
        mContext = context;
        mConnectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, intentFilter);
    }

    /**
     * 注销接收网络广播
     */
    public void unregister() {
        if (mConnectivityManager == null) return;// 避免多次调用
        mContext.getApplicationContext().unregisterReceiver(this);
        mConnectivityManager = null;
    }
}
