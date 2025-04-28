package com.tiocloud.chat.feature.session.common.panel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.SessionActivity;
import com.tiocloud.chat.feature.session.common.action.ActionsPanel;
import com.tiocloud.chat.feature.session.common.model.SessionContainer;
import com.tiocloud.chat.feature.session.group.fragment.ait.AitTextChangeListener;
import com.tiocloud.chat.util.KeyboardUtil;
import com.tiocloud.chat.util.MoonUtil;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.emotion.EmojiGridViewAdapter;
import com.tiocloud.chat.widget.emotion.EmoticonPickerView;
import com.tiocloud.chat.widget.emotion.EmoticonSelectedListener;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.audiorecord.TioAudioLayout;
import com.watayouxiang.audiorecord.TioAudioRecorder;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.SendFaceEmotionReq;

/**
 * author : TaoWang
 * date : 2019-12-27
 * desc :
 */
public class MsgInputPanel implements AitTextChangeListener {
    private static final int SHOW_LAYOUT_DELAY = 200;
    // 最大输入长度
    private static final int maxInputTextLength = 1500;

    private final SessionContainer container;

    // 底部布局
    private LinearLayout messageActivityBottomLayout;
    // 更多功能按钮（加号）
    private View moreFunctionButtonInInputBar;
    // 表情选择按钮
    private View emojiButtonInInputBar;
    // 发送消息按钮
    private View sendMessageButtonInInputBar;
    // 文本消息编辑框
    private EditText messageEditText;
    // 贴图表情控件
    private EmoticonPickerView emoticonPickerView;
    // 语音消息选择按钮
    protected View switchToAudioButtonInInputBar;
    // 文本消息选择按钮
    protected View switchToTextButtonInInputBar;
    // 录音按钮
    protected TextView audioRecordBtn;
    // 录音动画布局
    private TioAudioLayout audioAnimLayout;

    // 更多功能布局，懒加载（点击加号，展开的功能布局）
    private ActionsPanel actionsPanel;
    // @ 功能的实现
    private TextWatcher aitTextWatcher;
    private TioAudioRecorder tioRecorder;

    public MsgInputPanel(SessionContainer container) {
        this.container = container;

        findViews();
        initViews();
        initTextEdit();
        initAudioRecordBtn(container.activity);
    }

    private void initAudioRecordBtn(Activity activity) {
        tioRecorder = new TioAudioRecorder(container.chatLinkIds);
        tioRecorder.setOnRecorderListener(new TioAudioRecorder.OnSimpleRecorderListener() {
            @Override
            public void onShowToast(@NonNull String text) {
                TioToast.showShort(text);
            }

            @Override
            public void onChanged(int type) {
                if (type == 1) {// 抬起
                    audioRecordBtn.setText(activity.getString(R.string.anzhushuohua));
                    audioRecordBtn.setBackground(ResourceUtils.getDrawable(R.drawable.bg_session_audio_btn_normal));
                    audioAnimLayout.stop();
                } else if (type == 2) {// 按下
                    audioRecordBtn.setText(activity.getString(R.string.songkaifasonong));
                    audioRecordBtn.setBackground(ResourceUtils.getDrawable(R.drawable.bg_session_audio_btn_selected));
                    audioAnimLayout.start(1000);
                } else if (type == 3) {// 取消
                    audioRecordBtn.setText(activity.getString(R.string.quxiaofasonong));
                    audioRecordBtn.setBackground(ResourceUtils.getDrawable(R.drawable.bg_session_audio_btn_selected));
                    audioAnimLayout.cancel();
                }
            }

            @Override
            public void onUploadAudioStart() {
                super.onUploadAudioStart();
                SingletonProgressDialog.show_unCancel(container.activity, container.activity.getString(R.string.shangchuanzhong));
            }

            @Override
            public void onUploadAudioFinish() {
                super.onUploadAudioFinish();
                SingletonProgressDialog.dismiss();
            }
        });
        tioRecorder.initRecordView(audioRecordBtn);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTextEdit() {
        messageEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        messageEditText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showInputMethod();
                }
                return false;
            }
        });

        messageEditText.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
                if (aitTextWatcher != null) {
                    aitTextWatcher.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (aitTextWatcher != null) {
                    aitTextWatcher.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 切换 "发送" / "加号"
                checkSendButtonEnable();

                // 替换 emoji 表情
                MoonUtil.replaceEmoticons(container.activity, s, start, count);

                // 限制输入文本长度
                int editEnd = messageEditText.getSelectionEnd();
                messageEditText.removeTextChangedListener(this);
                while (StringUtil.counterLength(s.toString()) > maxInputTextLength && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                messageEditText.setSelection(editEnd);
                messageEditText.addTextChangedListener(this);

                if (aitTextWatcher != null) {
                    aitTextWatcher.afterTextChanged(s);
                }
            }
        });
    }

    private void initViews() {
        hideEmojiLayout();
        restoreText(false);
        hideInputMethod();

        emojiButtonInInputBar.setOnClickListener(clickListener);
        sendMessageButtonInInputBar.setOnClickListener(clickListener);
        moreFunctionButtonInInputBar.setOnClickListener(clickListener);
        switchToAudioButtonInInputBar.setOnClickListener(clickListener);
        switchToTextButtonInInputBar.setOnClickListener(clickListener);

        // 点击效果
        ClickUtils.applyPressedBgDark(sendMessageButtonInInputBar);
        ClickUtils.applyPressedViewScale(sendMessageButtonInInputBar);
    }

    private void findViews() {
        View rootView = container.rootView;

        // input bar
        messageActivityBottomLayout = rootView.findViewById(R.id.messageActivityBottomLayout);
        moreFunctionButtonInInputBar = rootView.findViewById(R.id.buttonMoreFunctionInText);
        emojiButtonInInputBar = rootView.findViewById(R.id.emoji_button);
        sendMessageButtonInInputBar = rootView.findViewById(R.id.buttonSendMessage);
        messageEditText = rootView.findViewById(R.id.editTextMessage);

        // 表情
        emoticonPickerView = rootView.findViewById(R.id.emoticon_picker_view);

        // 语音
        switchToAudioButtonInInputBar = rootView.findViewById(R.id.buttonAudioMessage);
        switchToTextButtonInInputBar = rootView.findViewById(R.id.buttonTextMessage);
        audioRecordBtn = rootView.findViewById(R.id.audioRecord);
        audioAnimLayout = rootView.findViewById(R.id.layoutPlayAudio);
    }

    // ================================================================================
    // click
    // ================================================================================

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == emojiButtonInInputBar) {// 表情按钮
                toggleEmojiLayout();
            } else if (v == moreFunctionButtonInInputBar) {// 加号按钮
                toggleActionPanelLayout();
            } else if (v == sendMessageButtonInInputBar) {// 发送按钮
                onClickSendButton();
            } else if (v == switchToAudioButtonInInputBar) {// 语音消息
                onClickAudioButton();
            } else if (v == switchToTextButtonInInputBar) {// 文字消息
                onClickTextButton();
            }
        }
    };

    /**
     * 点击文字消息按钮
     */
    private void onClickTextButton() {
        showTextLayout();
        showInputMethod();
    }

    /**
     * 点击语音消息按钮
     */
    private void onClickAudioButton() {
        showAudioLayout();
        hideAllInputLayout(true);
    }

    /**
     * 点击发送按钮
     */
    private void onClickSendButton() {
        String text = messageEditText.getText().toString();
        if (container.proxy.sendMessage(text)) {
            restoreText(true);
            if (container.activity instanceof SessionActivity){
                ((SessionActivity)(container.activity)).sendComplete();
            }
        } else {
            TioToast.showShort(container.activity.getString(R.string.current_net_error));
        }
    }

    /**
     * 切换到键盘布局
     */
    private void toggleKeyboardButton() {
        if (!KeyboardUtil.isSoftInputVisible(container.activity)) {
            showInputMethod();
        } else {
            hideInputMethod();
        }
    }

    /**
     * 切换到表情布局
     */
    private void toggleEmojiLayout() {
        if (emoticonPickerView.getVisibility() == View.GONE) {
            showEmojiLayout();
        } else {
            hideEmojiLayout();
        }
    }

    /**
     * 切换更多功能布局
     */
    private void toggleActionPanelLayout() {
        if (actionsPanel == null) {
            actionsPanel = new ActionsPanel(container.activity);
            actionsPanel.init(container.actions);
            messageActivityBottomLayout.addView(actionsPanel);
        }
        if (actionsPanel.getVisibility() == View.GONE) {
            showActionPanelLayout();
        } else {
            hideActionPanelLayout();
        }
    }

    /**
     * 隐藏所有输入布局
     */
    private Runnable hideAllInputLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            hideInputMethod();
            hideActionPanelLayout();
            hideEmojiLayout();
        }
    };

    // ================================================================================
    // show hide
    // ================================================================================

    /**
     * 切换文字消息布局
     */
    private void showTextLayout() {
        audioRecordBtn.setVisibility(View.GONE);
        messageEditText.setVisibility(View.VISIBLE);
        switchToTextButtonInInputBar.setVisibility(View.GONE);
        switchToAudioButtonInInputBar.setVisibility(View.VISIBLE);
    }

    /**
     * 切换语音消息布局
     */
    private void showAudioLayout() {
        messageEditText.setVisibility(View.GONE);
        audioRecordBtn.setVisibility(View.VISIBLE);
        switchToAudioButtonInInputBar.setVisibility(View.GONE);
        switchToTextButtonInInputBar.setVisibility(View.VISIBLE);
    }

    /**
     * 显示多功能布局
     */
    private void showActionPanelLayout() {
        // 隐藏表情布局
        hideEmojiLayout();
        // 隐藏键盘
        hideInputMethod();
        // 显示文字布局
        showTextLayout();

        // 显示多功能布局
        ThreadUtils.getMainHandler().postDelayed(showMoreFuncRunnable, SHOW_LAYOUT_DELAY);
        // 选中actions按钮
        moreFunctionButtonInInputBar.setSelected(true);

        // 通知面板展开了
        container.proxy.onInputPanelExpand();
    }

    /**
     * 隐藏更多布局
     */
    private void hideActionPanelLayout() {
        // 移除显示多功能布局的任务
        ThreadUtils.getMainHandler().removeCallbacks(showMoreFuncRunnable);
        // 隐藏多功能布局
        if (actionsPanel != null) {
            actionsPanel.setVisibility(View.GONE);
        }
        // 取消选中actions按钮
        moreFunctionButtonInInputBar.setSelected(false);
    }

    /**
     * 显示表情布局
     */
    private void showEmojiLayout() {
        // 隐藏输入布局
        hideInputMethod();
        // 隐藏功能布局
        hideActionPanelLayout();
        // 显示文字布局
        showTextLayout();

        // 输入框获取焦点
        messageEditText.requestFocus();
        // 延时展开表情布局
        ThreadUtils.getMainHandler().postDelayed(showEmojiRunnable, SHOW_LAYOUT_DELAY);
        // 选中emoji按钮
        emojiButtonInInputBar.setSelected(true);

        // 通知输入面板展开
        container.proxy.onInputPanelExpand();
    }

    /**
     * 隐藏表情布局
     */
    private void hideEmojiLayout() {
        // 移除展开表情布局任务
        ThreadUtils.getMainHandler().removeCallbacks(showEmojiRunnable);
        // 隐藏表情布局
        if (emoticonPickerView != null) {
            emoticonPickerView.setVisibility(View.GONE);
        }
        // 取消选中emoji按钮
        emojiButtonInInputBar.setSelected(false);
        // 清除焦点
        messageEditText.clearFocus();
    }

    /**
     * 显示键盘布局
     */
    private void showInputMethod() {
        // 隐藏表情布局
        hideEmojiLayout();
        // 隐藏功能区布局
        hideActionPanelLayout();

        // 获取焦点
        messageEditText.requestFocus();
        // 如果已经显示，则继续操作时不需要把光标定位到最后
        if (!KeyboardUtil.isSoftInputVisible(container.activity)) {
            messageEditText.setSelection(messageEditText.getText().length());
        }
        // 展开键盘
        ThreadUtils.getMainHandler().postDelayed(showTextRunnable, SHOW_LAYOUT_DELAY);

        // 通知输入面板展开了
        container.proxy.onInputPanelExpand();
    }

    /**
     * 隐藏键盘布局
     */
    private void hideInputMethod() {
        // 移除展开键盘任务
        ThreadUtils.getMainHandler().removeCallbacks(showTextRunnable);
        // 隐藏键盘
        KeyboardUtil.hideSoftInput(messageEditText);
        // 清楚输入栏焦点
        messageEditText.clearFocus();
    }

    // ================================================================================
    // Runnable
    // ================================================================================

    private Runnable showTextRunnable = new Runnable() {
        @Override
        public void run() {
            KeyboardUtil.showSoftInput(messageEditText);
        }
    };

    private Runnable showMoreFuncRunnable = new Runnable() {
        @Override
        public void run() {
            if (actionsPanel != null) {
                actionsPanel.setVisibility(View.VISIBLE);
            }
        }
    };

    private Runnable showEmojiRunnable = new Runnable() {
        @Override
        public void run() {
            emoticonPickerView.setVisibility(View.VISIBLE);
            emoticonPickerView.show(emoticonSelectedListener);
        }
    };

    private EmoticonSelectedListener emoticonSelectedListener = new EmoticonSelectedListener() {
        @Override
        public void onEmojiSelected(String key) {
            Editable mEditable = messageEditText.getText();
            if (key.equals(EmojiGridViewAdapter.KEY_DEL)) {
                messageEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                int start = messageEditText.getSelectionStart();
                int end = messageEditText.getSelectionEnd();
                start = (start < 0 ? 0 : start);
                end = (start < 0 ? 0 : end);
                mEditable.replace(start, end, key);
            }
        }

        @Override
        public void onGifFaceClick(String resName) {
//            ToastUtils.showShort("发送表情："+resName);
//            hideAllInputLayout(true);
            for (String chatlink : container.chatLinkIds){
                SendFaceEmotionReq req = new SendFaceEmotionReq(resName, 0, chatlink);
                req.setCancelTag(this);
                req.post(new TioCallback<String>() {
                    @Override
                    public void onTioSuccess(String s) {
                        LogUtils.d("zlb", "ssss===>"+s);
                    }

                    @Override
                    public void onTioError(String msg) {
                        LogUtils.d("zlb", "msg===>"+msg);
                    }
                });
            }
        }

        @Override
        public void onCollectGifClick(String url, int width, int height) {
            for (String chatlink : container.chatLinkIds){
                SendFaceEmotionReq req = new SendFaceEmotionReq(url, 1, chatlink);
                req.setWidthHeight(width, height);
                req.setCancelTag(this);
                req.post(new TioCallback<String>() {
                    @Override
                    public void onTioSuccess(String s) {
                    }

                    @Override
                    public void onTioError(String msg) {
                    }
                });
            }
        }

        @Override
        public void emotionDelete() {
            hideEmojiLayout();
        }
    };

    // ================================================================================
    // operation
    // ================================================================================

    /**
     * 还原输入栏
     *
     * @param clearText 是否清空文字
     */
    private void restoreText(boolean clearText) {
        if (clearText) {
            messageEditText.setText("");
        }
        checkSendButtonEnable();
    }

    /**
     * 如果输入栏有文字，则显示"发送"，否则显示"加号"
     */
    private void checkSendButtonEnable() {
        String textMessage = messageEditText.getText().toString();
        if (!TextUtils.isEmpty(StringUtil.removeBlanks(textMessage)) && messageEditText.hasFocus()) {
            sendMessageButtonInInputBar.setVisibility(View.VISIBLE);
            moreFunctionButtonInInputBar.setVisibility(View.GONE);
        } else {
            moreFunctionButtonInInputBar.setVisibility(View.VISIBLE);
            sendMessageButtonInInputBar.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏所有输入布局
     *
     * @param immediately true 立即，false 延时
     */
    private void hideAllInputLayout(boolean immediately) {
        long delay = immediately ? 0 : ViewConfiguration.getDoubleTapTimeout();
        ThreadUtils.getMainHandler().postDelayed(hideAllInputLayoutRunnable, delay);
    }

    // ================================================================================
    // public
    // ================================================================================

    /**
     * 折叠输入栏
     *
     * @param immediately true 立即，false 延时
     * @return 是否需要折叠
     */
    public boolean collapse(boolean immediately) {
        boolean respond = (emoticonPickerView != null && emoticonPickerView.getVisibility() == View.VISIBLE
                || actionsPanel != null && actionsPanel.getVisibility() == View.VISIBLE)
                || KeyboardUtil.isSoftInputVisible(container.activity);

        hideAllInputLayout(immediately);

        return respond;
    }

    public void release() {
        if (tioRecorder != null) {
            tioRecorder.release();
            tioRecorder = null;
        }
    }

    // ====================================================================================
    // 艾特功能
    // ====================================================================================

    /**
     * 添加 @ 文本监视器
     */
    public void setAitTextWatcher(TextWatcher watcher) {
        aitTextWatcher = watcher;
    }

    @Override
    public void onAitTextAdd(String content, int start, int length) {
        // 显示输入栏
        showInputMethod();
        // 切换文字消息布局
        showTextLayout();
        // 插入文字
        messageEditText.getEditableText().insert(start, content);
    }

    @Override
    public void onAitTextDelete(int start, int length) {
        // 显示输入栏
        showInputMethod();
        // 删除文字
        int end = start + length - 1;
        messageEditText.getEditableText().replace(start, end, "");
    }
}
