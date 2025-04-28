package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.text.Html;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.fragment.Nav1Fragment;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.util.MoonUtil;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 文本类型消息
 */
public class MsgTextViewHolder extends MsgBaseViewHolder {
    private TextView bodyTextView;
    private String content;

    public MsgTextViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.message_item_text;
    }

    @Override
    protected void inflateContent() {
        bodyTextView = findViewById(R.id.tv_message);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        content = getMessage().getContent();
//        content = content + "\t"+getMessage().getId();
        if (content == null) content = "";

        // 表情识别
        bodyTextView.setGravity(Gravity.LEFT);
//        bodyTextView.setText(content);
        if (TioConfig.OpenCloseConfig.isUliao() && content.startsWith("uliao://")){
            bodyTextView.setText(Html.fromHtml("<u>"+content+"</u>"));
        }else {
            MoonUtil.identifyFaceExpression(bodyTextView, content, ImageSpan.ALIGN_BOTTOM);
        }
    }

    @Override
    protected View.OnLongClickListener onContentLongClick() {
        return view -> {
            showAttachView(view, getCopyText());
            return true;
        };
    }

    @Override
    protected void onContentClick(View view) {

        if (content == null) content = "";
        if (TioConfig.OpenCloseConfig.isUliao() && content.startsWith("uliao://")){
            Nav1Fragment.toDecode(getActivity(), content.substring(8));
        }
    }

    private String getCopyText() {
        if (bodyTextView != null) {
            return bodyTextView.getText().toString();
        }
        return null;
    }

    @Override
    protected boolean isShowContentBg() {
        return true;
    }
}
