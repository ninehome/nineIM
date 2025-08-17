package com.tiocloud.chat.feature.session.group.fragment.ait;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/7/24
 *     desc   :
 * </pre>
 */
public interface AitTextChangeListener {
    /**
     * 插入文本
     *
     * @param content
     * @param start
     * @param length
     */
    void onAitTextAdd(String content, int start, int length);

    /**
     * 删除文本
     *
     * @param start
     * @param length
     */
    void onAitTextDelete(int start, int length);
}
