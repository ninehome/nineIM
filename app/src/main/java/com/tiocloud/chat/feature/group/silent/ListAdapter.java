package com.tiocloud.chat.feature.group.silent;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   :
 * </pre>
 */
class ListAdapter extends BaseMultiItemQuickAdapter<ListModel, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ListAdapter(List<ListModel> data) {
        super(data);
        addItemType(ListModel.ITEM_TYPE_NORMAL, R.layout.tio_silent_list_item_normal);
    }

    @Override
    public void setNewData(@Nullable List<ListModel> data) {
        super.setNewData(data);
        // 空白页
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            View notDataView = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.tio_list_empty, recyclerView, false);
            setEmptyView(notDataView);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ListModel item) {
        int itemType = item.getItemType();
        switch (itemType) {
            case ListModel.ITEM_TYPE_NORMAL:
                convertNormal(helper, item.getNormalItem());
                break;
        }
    }

    private void convertNormal(BaseViewHolder helper, ListNormalItem item) {
        ImageView iv_delete = helper.getView(R.id.iv_delete);
        TioImageView iv_avatar = helper.getView(R.id.iv_avatar);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        TextView tv_tag = helper.getView(R.id.tv_tag);

        iv_delete.setVisibility(ListNormalItem.isShowDelete() ? View.VISIBLE : View.GONE);
        iv_avatar.tio_roundAvatar(item.getAvatar());
        tv_title.setText(item.getTitle());
        tv_tag.setText(item.getTagTxt());

        String subtitle = item.getSubtitle();
        if (StringUtils.isEmpty(subtitle)) {
            tv_subtitle.setVisibility(View.GONE);
        } else {
            tv_subtitle.setVisibility(View.VISIBLE);
            tv_subtitle.setText(subtitle);
        }

        helper.addOnClickListener(iv_delete.getId());
    }
}
