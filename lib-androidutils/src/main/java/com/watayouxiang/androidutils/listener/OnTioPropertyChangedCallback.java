package com.watayouxiang.androidutils.listener;

import androidx.databinding.Observable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/19
 *     desc   :
 * </pre>
 */
public abstract class OnTioPropertyChangedCallback<T extends Observable> extends Observable.OnPropertyChangedCallback {
    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
        onTioPropertyChanged((T) sender, propertyId);
    }

    public abstract void onTioPropertyChanged(T sender, int propertyId);
}
