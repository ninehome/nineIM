package com.tiocloud.chat.widget.fragmentDialog;

import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc : 解决 DialogFragment 的多次调用 show 的 bug
 */
public class FixDialogFragment extends DialogFragment {

    private boolean mIsShow = false;

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!mIsShow) {
            super.show(manager, tag);
            mIsShow = true;
        }
    }

    @Override
    public void dismiss() {
        if (mIsShow) {
            super.dismiss();
            mIsShow = false;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mIsShow) {
            super.onCancel(dialog);
            mIsShow = false;
        }
    }
}
