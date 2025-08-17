package com.tiocloud.chat.feature.curr.modify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.curr.modify.model.ModifyType;
import com.tiocloud.chat.feature.curr.modify.model.ModifyViewHolder;
import com.tiocloud.chat.feature.curr.modify.model.PageUiModel;
import com.tiocloud.chat.feature.curr.modify.mvp.ModifyContract;
import com.tiocloud.chat.feature.curr.modify.mvp.ModifyPresenter;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.TioActivity;
import com.tiocloud.chat.util.StringUtil;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc :
 */
public class ModifyActivity extends TioActivity implements ModifyContract.View {
    private ModifyViewHolder holder;
    private ModifyPresenter presenter;

    private boolean isEditStatus = true;

    //刚进来时能否编辑，考虑到兼容以前的，默认是可以编辑的
//    private boolean firstCanEditStatus = true;
//    //是否显示编辑按钮
//    private boolean firstShowEditBtn = false;
//    //非编辑状态时的提示
//    private String noEditTip;

    public static void start_curr(Context context, ModifyType type, String echo) {
        start(context, type, null, echo, null, null, null);
    }

    public static void start_group(Context context, ModifyType type, String groupId, String echo, boolean firstCanEditStatus, boolean firstShowEditBtn){
        start(context, type, groupId, echo, null, firstCanEditStatus, firstShowEditBtn);
    }

    public static void start_group(Context context, ModifyType type, String groupId, String echo) {
        start(context, type, groupId, echo, null, null, null);
    }

    public static void start_user(Context context, ModifyType type, String uid, String echo) {
        start(context, type, null, echo, uid, null, null);
    }

    public static void start(Context context, ModifyType type, String groupId, String echo, String uid, Boolean firstCanEdit, Boolean firstShowEdit) {
        Intent starter = new Intent(context, ModifyActivity.class);
        starter.putExtra(TioExtras.EXTRA_MODIFY_TYPE, type);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        starter.putExtra(TioExtras.EXTRA_ECHO, echo);
        starter.putExtra(TioExtras.EXTRA_USER_ID, uid);
        starter.putExtra(TioExtras.EXTRA_FIRST_CAN_EDIT, firstCanEdit);
        starter.putExtra(TioExtras.EXTRA_FIRST_SHOW_EDIT, firstShowEdit);
        context.startActivity(starter);
    }

    public ModifyType getModifyType() {
        return (ModifyType) getIntent().getSerializableExtra(TioExtras.EXTRA_MODIFY_TYPE);
    }

    public boolean getFirstCanEditStatus(){
        return getIntent().getBooleanExtra(TioExtras.EXTRA_FIRST_CAN_EDIT, true);
    }

    public boolean getFirstShowEditBtn(){
        return getIntent().getBooleanExtra(TioExtras.EXTRA_FIRST_SHOW_EDIT, false);
    }

    public String getUid() {
        return getIntent().getStringExtra(TioExtras.EXTRA_USER_ID);
    }

    public String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    public String getEcho() {
        return getIntent().getStringExtra(TioExtras.EXTRA_ECHO);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void resetUI() {
        // 设置页面标题
        holder.titleBar.setTitle("");
        // 菜单
        holder.tv_menuBtn.setEnabled(false);
        holder.tv_menuBtn.setText("未知");
        holder.tv_menuBtn.setOnClickListener(view -> {
            if (presenter != null) {
                presenter.onClickMenuBtn(view);
            }
        });
        // 最多字数
        holder.tv_wordCount.setText("0/00");
        // 输入框
        holder.et_content.setMinLines(1);
        holder.et_content.setMaxLines(1);
        InputFilter[] filters = {new InputFilter.LengthFilter(Integer.MAX_VALUE)};
        holder.et_content.setFilters(filters);
    }

    @Override
    public void setUIData(final PageUiModel model) {
        // 设置页面标题
        holder.titleBar.setTitle(model.page_title);
        // 菜单名称
        holder.tv_menuBtn.setText(model.menu_name);
        // 最多字数
        holder.tv_wordCount.setText(String.format(Locale.getDefault(), "0/%d", model.max_word));
        // 输入框
        holder.et_content.setEnabled(getFirstCanEditStatus());
        isEditStatus = getFirstCanEditStatus();
        if (getFirstShowEditBtn()){
            holder.titleBar.getTvRight().setVisibility(View.GONE);
            holder.titleBar.getIvRight().setVisibility(View.VISIBLE);
            holder.titleBar.getIvRight().setImageResource(R.drawable.btn_edit16);
        }else {
            if (getFirstCanEditStatus()){
                holder.titleBar.getTvRight().setVisibility(View.VISIBLE);
            }else {
                holder.titleBar.getTvRight().setVisibility(View.GONE);
            }
        }
        holder.et_content.setMinLines(model.min_line);
        holder.et_content.setMaxLines(model.max_line);
        InputFilter[] filters = {new InputFilter.LengthFilter(model.max_word)};
        holder.et_content.setFilters(filters);
        holder.et_content.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                int _count = !TextUtils.isEmpty(s) ? s.length() : 0;
                holder.tv_wordCount.setText(String.format(Locale.getDefault(), "%d/%d", _count, model.max_word));
                holder.tv_menuBtn.setEnabled(model.enableNullContent || _count > 0);
            }
        });
        holder.et_content.setText(StringUtil.nonNull(getEcho()));

        holder.titleBar.getIvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.titleBar.getIvRight().setVisibility(View.GONE);
                holder.titleBar.getTvRight().setVisibility(View.VISIBLE);
                holder.et_content.setEnabled(true);
                isEditStatus = true;
                refreshTip(model);
            }
        });
        if (!model.showLimit){
            holder.tv_wordCount.setVisibility(View.GONE);
        }
        refreshTip(model);
    }

    private void refreshTip(PageUiModel model){
        if (model.noEditTipStr == null || isEditStatus){
            holder.tipView.setVisibility(View.GONE);
        }else {
            holder.tipView.setVisibility(View.VISIBLE);
            holder.tipTv.setText(model.noEditTipStr);
        }
    }

    @Override
    public String getEtContent() {
        if (holder != null) {
            return holder.et_content.getText().toString();
        }
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holder = new ModifyViewHolder(this);
        setContentView(holder.rootView);

        presenter = new ModifyPresenter(this);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
