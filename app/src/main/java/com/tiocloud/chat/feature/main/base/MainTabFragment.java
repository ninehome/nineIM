package com.tiocloud.chat.feature.main.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.main.fragment.Nav1Fragment;
import com.tiocloud.chat.feature.main.model.MainTab;


public abstract class MainTabFragment extends BaseTabFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainTab tabData = getTabData();
        if (tabData == null){
            Log.e("zlb", "tabData=====>"+getClass().getName().toString());
            if (this instanceof Nav1Fragment){
                return inflater.inflate(R.layout.fragment_find, container, false);
            }
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return inflater.inflate(tabData.layoutId, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInit();
    }

    /**
     * 初始化
     */
    protected abstract void onInit();
}
