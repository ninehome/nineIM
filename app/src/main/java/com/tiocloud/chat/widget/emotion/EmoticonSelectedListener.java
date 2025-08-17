package com.tiocloud.chat.widget.emotion;

public interface EmoticonSelectedListener {
    /**
     * 选中了emoji
     *
     * @param key 关键字
     */
    void onEmojiSelected(String key);
    //内置表情
    void onGifFaceClick(String resName);
    //自定义表情
    void onCollectGifClick(String url, int width, int height);

    void emotionDelete();
}
