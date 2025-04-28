package com.tiocloud.chat.feature.session.group.fragment.ait;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiocloud.chat.feature.group.at.AtActivity;
import com.tiocloud.chat.feature.group.at.TeamMember;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.util.StringUtil;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/24
 *     desc   :
 * </pre>
 */
public class AitManager implements TextWatcher {
    private final Fragment fragment;
    private final String groupId;
    private AitContactsModel aitContactsModel;
    private AitTextChangeListener listener;

    public AitManager(Fragment fragment, String groupId) {
        this.fragment = fragment;
        this.groupId = groupId;
        this.aitContactsModel = new AitContactsModel();
    }

    /**
     * "消息发送后" 重置
     */
    public void reset() {
        aitContactsModel.reset();
        ignoreTextChange = false;
        curPos = 0;
    }

    /**
     * 打开 "@成员列表页"
     */
    private void onActivityStart() {
        AtActivity.start(fragment, groupId);
    }

    /**
     * 关闭 "@成员列表页"，接收该页面的回调
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AtActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null) return;
            TeamMember member = (TeamMember) data.getSerializableExtra(AtActivity.RESULT_DATA);
            if (member == null) return;
            insertAitMemberInner(member.uid, member.name, AitContactType.TEAM_MEMBER, curPos, false);
        }
    }

    /**
     * 艾特插入、艾特删除 回调
     */
    public void setTextChangeListener(AitTextChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 获取 艾特人员的id
     */
    public List<String> getAitTeamMember() {
        List<String> aitTeamMember = aitContactsModel.getAitTeamMember();
        if (aitTeamMember != null && aitTeamMember.contains("-99999")){
            aitTeamMember.clear();
            aitTeamMember.add("all");
        }
        return aitTeamMember;
    }

    @Nullable
    public String getAitTeamMemberStr() {
        return StringUtil.list2String(getAitTeamMember());
    }

    // ====================================================================================
    // 输入栏监听
    // ====================================================================================

    private int editTextStart;
    private int editTextCount;
    private int editTextBefore;
    private boolean delete;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        delete = count > after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.editTextStart = start;
        this.editTextCount = count;
        this.editTextBefore = before;
    }

    @Override
    public void afterTextChanged(Editable s) {
        afterTextChanged(s, editTextStart, delete ? editTextBefore : editTextCount, delete);
    }

    // ====================================================================================
    // @插入、@删除 实现
    // ====================================================================================

    private int curPos;
    private boolean ignoreTextChange = false;

    /**
     * @param editable 变化后的Editable
     * @param start    text 变化区块的起始index
     * @param count    text 变化区块的大小
     * @param delete   是否是删除
     */
    private void afterTextChanged(Editable editable, int start, int count, boolean delete) {
        curPos = delete ? start : count + start;
        if (ignoreTextChange) {
            return;
        }
        if (delete) {
            int before = start + count;
            if (deleteSegment(before, count)) {
                return;
            }
            aitContactsModel.onDeleteText(before, count);
        } else {
            if (count <= 0 || editable.length() < start + count) {
                return;
            }
            CharSequence s = editable.subSequence(start, start + count);
            if (s == null) {
                return;
            }
            if (s.toString().equals("@")) {
                // 启动@联系人界面
                onActivityStart();
            }
            aitContactsModel.onInsertText(start, s.toString());
        }
    }

    // 当删除尾部空格时，删除一整个segment, 包含界面上也删除
    private boolean deleteSegment(int start, int count) {
        if (count != 1) {
            return false;
        }
        boolean result = false;
        AitBlock.AitSegment segment = aitContactsModel.findAitSegmentByEndPos(start);
        if (segment != null) {
            int length = start - segment.start;
            if (listener != null) {
                ignoreTextChange = true;
                listener.onAitTextDelete(segment.start, length);
                ignoreTextChange = false;
            }
            aitContactsModel.onDeleteText(start, length);
            result = true;
        }
        return result;
    }

    private void insertAitMemberInner(String account, String name, int type, int start, boolean needInsertAitInText) {
        name = name + " ";
        String content = needInsertAitInText ? "@" + name : name;
        if (listener != null) {
            // 关闭监听
            ignoreTextChange = true;
            // insert 文本到editText
            listener.onAitTextAdd(content, start, content.length());
            // 开启监听
            ignoreTextChange = false;
        }

        // update 已有的 aitBlock
        aitContactsModel.onInsertText(start, content);

        int index = needInsertAitInText ? start : start - 1;
        // 添加当前到 aitBlock
        aitContactsModel.addAitMember(account, name, type, index);
    }

    /**
     * 手动插入 "艾特块"
     *
     * @param account uid
     * @param name    昵称
     */
    public void insertAitMemberInner(String account, String name) {
        insertAitMemberInner(account, name, AitContactType.TEAM_MEMBER, curPos, true);
    }

    public boolean insertAitMemberInner(TioMsg msg) {
        if (msg != null) {
            String uid = msg.getUid();
            String nick = msg.getAitName();
            if (uid != null && nick != null) {
                insertAitMemberInner(uid, nick);
                return true;
            }
        }
        return false;
    }
}
