package com.tiocloud.chat.widget.fragmentDialog;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc : 解决 DialogFragment 的多次调用 show 的 bug
 */
public class FixBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private boolean mIsShow = false;

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
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
    public void onCancel(@NonNull DialogInterface dialog) {
        if (mIsShow) {
            super.onCancel(dialog);
            mIsShow = false;
        }
    }
}
