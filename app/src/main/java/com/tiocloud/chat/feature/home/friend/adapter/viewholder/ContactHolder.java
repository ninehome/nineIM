package com.tiocloud.chat.feature.home.friend.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.home.chat.adapter.BaseUIAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.BaseContactAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.model.IContact;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.ContactItem;
import com.tiocloud.chat.util.StringUtil;

import com.watayouxiang.androidutils.widget.imageview.TioImageView;

/**
 * author : TaoWang
 * date : 2020-01-16
 * desc : 联系人 ViewHolder
 */
public class ContactHolder extends BaseViewHolder<ContactItem> {

    @Override
    public int getLayoutId() {
        return R.layout.tio_contacts_item;
    }

    @Override
    public void convert(BaseContactAdapter adapter, int position, ContactItem item) {
        IContact contact = item.getContact();
        boolean lastPosition = item.isLastPosition();

        TextView name = findViewById(R.id.contacts_name);
        name.setText(StringUtil.nonNull(contact.getName()));

        TioImageView avatar = findViewById(R.id.contact_avatar);
        avatar.tio_roundAvatar(contact.getAvatar());

        View v_divider = findViewById(R.id.v_divider);
        View v_status = findViewById(R.id.v_status);
        v_divider.setVisibility(lastPosition ? View.INVISIBLE : View.VISIBLE);

        AppCompatTextView textView = findViewById(R.id.tv_online_status);
        if (TioConfig.OpenCloseConfig.showOnlineStatus()){
            textView.setVisibility(View.GONE);
            v_status.setVisibility(View.VISIBLE);
            BaseUIAdapter.setListenerMap(contact.getId(), new BaseUIAdapter.OnlineStatusListener() {
                @Override
                public void back(String uid, boolean isOnline, String lastOnlineTime) {
                    if (textView == null){
                        return;
                    }
                    if (!isOnline){
//                        if (TioConfig.OpenCloseConfig.showLastOnlineTime()){
//                            textView.setBackgroundResource(R.drawable.dot_offline_bg);
//                            String o = BaseUIAdapter.getOfflineTime(lastOnlineTime);
//                            Log.w("zlb", StringUtil.nonNull(contact.getId())+"\t"+o);
//                            textView.setText(o);
//                            textView.setTextColor(getContext().getResources().getColor(R.color.gray_c1c1c1));
//                        }else {
//                        }
                        textView.setBackgroundResource(R.drawable.dot_offline);
                        textView.setText("");
                        v_status.setEnabled(false);
                    }else {
                        textView.setBackgroundResource(R.drawable.dot_online);
                        textView.setText("");
                        v_status.setEnabled(true);
                    }
                }
            });
        }
    }
}
