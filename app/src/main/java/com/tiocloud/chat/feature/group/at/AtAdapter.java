package com.tiocloud.chat.feature.group.at;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.httpclient.model.response.AtGroupUserListResp;
import com.watayouxiang.imclient.utils.ConstantUtils;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   :
 * </pre>
 */
class AtAdapter extends BaseQuickAdapter<AtGroupUserListResp.List, BaseViewHolder> {
    @Nullable
    private String mSearchKey;

    public AtAdapter(RecyclerView recyclerView) {
        super(R.layout.item_at);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        bindToRecyclerView(recyclerView);
    }

    @Override
    protected void convert(BaseViewHolder helper, AtGroupUserListResp.List item) {
        // 头像
        TioImageView tiv_avatar = helper.getView(R.id.tiv_avatar);
        if (item.uid == -99999){
            tiv_avatar.load(R.drawable.img_all, false);
        }else {
            tiv_avatar.tio_roundAvatar(item.avatar);
        }


        // 备注名、昵称
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_subTitle = helper.getView(R.id.tv_subTitle);
        String remarkname = item.remarkname;
        String nick = item.srcnick;
        if (!TextUtils.isEmpty(remarkname) && !TextUtils.isEmpty(nick)) {
            // 显示 备注名、昵称
            tv_title.setText(KeywordUtil.matcherSearchTitle(
                    Utils.getApp().getResources().getColor(R.color.blue_4c94ff),
                    remarkname,
                    mSearchKey));
            tv_subTitle.setVisibility(View.VISIBLE);
            tv_subTitle.setText(KeywordUtil.matcherSearchTitle(
                    Utils.getApp().getResources().getColor(R.color.blue_4c94ff),
                    String.format(mContext.getString(R.string.nick)+"：%s", nick),
                    mSearchKey));
        } else {
            // 显示 昵称
            tv_title.setText(KeywordUtil.matcherSearchTitle(
                    Utils.getApp().getResources().getColor(R.color.blue_4c94ff),
                    StringUtil.nonNull(nick),
                    mSearchKey));
            tv_subTitle.setVisibility(View.GONE);
        }
    }

    public void setNewData(@Nullable List<AtGroupUserListResp.List> data, @Nullable String searchkey) {
        mSearchKey = searchkey;
        if (mSearchKey == null){
            //当搜索为空，则在头位置增加@所有人
            data.add(0, new AtGroupUserListResp.List(null,"@"+ ConstantUtils.context.getString(R.string.all_people),  -99999));
        }
        setNewData(data);
    }
}
