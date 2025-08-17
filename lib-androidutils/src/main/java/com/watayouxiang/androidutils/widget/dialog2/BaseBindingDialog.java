package com.watayouxiang.androidutils.widget.dialog2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/12
 *     desc   :
 * </pre>
 */
public abstract class BaseBindingDialog<B extends ViewDataBinding> extends BaseDialog {

    // public final ObservableField<String> fromInfo = new ObservableField<>("小生的红包");

    protected B binding;

    public BaseBindingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutId(), null, false);
        setContentView(binding.getRoot());
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (binding != null) {
            binding.unbind();
        }
    }

    @LayoutRes
    public abstract int getLayoutId();

    public B getBinding() {
        return binding;
    }
}
