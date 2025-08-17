package com.tiocloud.chat.yanxun.groupsend.select;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.tiocloud.chat.R;
import com.tiocloud.chat.TioApplication;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final String TAG = "ChatAdapter";
    private List<Item> data = new ArrayList<>();
    private OnItemSelectedChangeListener listener;
    private String userId;

    public ChatAdapter(OnItemSelectedChangeListener listener, String userId) {
        this.listener = listener;
        this.userId = userId;
        setHasStableIds(true);
    }

    public void setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void cancelAll() {
        selectAll(false);
    }

    public void selectAll() {
        selectAll(true);
    }

    private void selectAll(boolean isSelected) {
        for (Item item : data) {
            if (item.selected != isSelected) {
                item.selected = isSelected;
                if (listener != null) {
                    listener.onItemSelectedChange(item, isSelected);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_chat, parent, false);
        return new ViewHolder(view, listener, userId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Item item = data.get(i);
        viewHolder.apply(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemSelectedChangeListener {
        void onItemSelectedChange(Item item, boolean isSelected);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TioImageView hvHead = itemView.findViewById(R.id.hvHead);
        TextView tvNickName = itemView.findViewById(R.id.tvNickName);
        CheckBox cbSelect = itemView.findViewById(R.id.cbSelect);
        RelativeLayout item_friend_warp = itemView.findViewById(R.id.item_friend_warp);
        private OnItemSelectedChangeListener listener;
        private String userId;


        ViewHolder(@NonNull View itemView, OnItemSelectedChangeListener listener, String userId) {
            super(itemView);
            this.listener = listener;
            this.userId = userId;
        }

        void apply(Item item) {
//            AvatarHelper.getInstance().displayAvatar(userId, item.friend, hvHead);
            hvHead.tio_roundAvatar(item.friend.avatar);
            tvNickName.setText(item.getNickName());

//            ColorStateList tabColor = SkinUtils.getSkin(MyApplication.getInstance()).getTabColorState();
            cbSelect.setOnCheckedChangeListener(null);
            cbSelect.setChecked(item.selected);
            //不点击时 初始化的时候有效
            if (cbSelect.isChecked()) {
                Drawable drawable = itemView.getContext().getResources().getDrawable(R.drawable.icon_choosed02);
                drawable = DrawableCompat.wrap(drawable);
//                DrawableCompat.setTintList(drawable, tabColor);
                cbSelect.setButtonDrawable(drawable);
            } else {
                cbSelect.setChecked(false);
                cbSelect.setButtonDrawable(itemView.getContext().getResources().getDrawable(R.drawable.icon_choose02));
            }
            cbSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                item.selected = isChecked;
//                if (listener != null) {
//                    listener.onItemSelectedChange(item, isChecked);
//                }
//                //点击时生效
//                if (cbSelect.isChecked()) {
//                    Drawable drawable = itemView.getContext().getResources().getDrawable(R.drawable.icon_choosed02);
//                    drawable = DrawableCompat.wrap(drawable);
////                    DrawableCompat.setTintList(drawable, tabColor);
//                    cbSelect.setButtonDrawable(drawable);
//                } else {
//                    cbSelect.setChecked(false);
//                    cbSelect.setButtonDrawable(itemView.getContext().getResources().getDrawable(R.drawable.icon_choose02));
//                }
            });

            item_friend_warp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.selected = !cbSelect.isChecked();
                    if (listener != null) {
                        listener.onItemSelectedChange(item, item.selected);
                    }
                    //点击时生效
                    if (item.selected) {
                        Drawable drawable = itemView.getContext().getResources().getDrawable(R.drawable.icon_choosed02);
                        drawable = DrawableCompat.wrap(drawable);
//                    DrawableCompat.setTintList(drawable, tabColor);
                        cbSelect.setButtonDrawable(drawable);
                        cbSelect.setChecked(true);
                    } else {
                        cbSelect.setChecked(false);
                        cbSelect.setButtonDrawable(itemView.getContext().getResources().getDrawable(R.drawable.icon_choose02));
                    }
                }
            });
        }

    }


    public static class Item {
        public MailListResp.Group friend;
        public boolean selected;

        public static Item fromFriend(MailListResp.Group friend) {
            Item item = new Item();
            item.friend = friend;
            return item;
        }

        String getNickName() {
            return friend.name;
        }

        public String getUserId() {
            return friend.groupid;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "friend=" + JSON.toJSONString(friend) +
                    '}';
        }

    }
}
