package com.tiocloud.chat.widget.emotion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.widget.alertdialog.DeleteEmotionDialog;
import com.watayouxiang.androidutils.widget.imageview.WtImageView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.CollectEmotionDeleteReq;
import com.watayouxiang.httpclient.model.response.CollectEmotionListResp;
import com.watayouxiang.httpclient.model.response.CommonResp;

import java.util.List;

import co.ceryle.fitgridview.FitGridAdapter;
import co.ceryle.fitgridview.FitGridView;

public class EmojiPager3Adapter extends PagerAdapter {
    // collections_gridview里的列数乘以两行，
    private final static int size = 10;
    private List<CollectEmotionListResp.CollectEmotion> collectionList;
    private EmoticonSelectedListener listener;
    private Context ctx;

    public EmojiPager3Adapter(Context ctx, List<CollectEmotionListResp.CollectEmotion> collectionList, EmoticonSelectedListener listener) {
        this.ctx = ctx;
        this.collectionList = collectionList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        // 编辑按钮已经加在list开头了，
        // 0舍1入，除以每页10个，
        return (collectionList.size() + (size - 1)) / size;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    static class CollectionAdapter extends FitGridAdapter {
        private final Context ctx;
        private final List<CollectEmotionListResp.CollectEmotion> collectionList;

        CollectionAdapter(Context ctx, List<CollectEmotionListResp.CollectEmotion> collectionList) {
            super(ctx, R.layout.item_face_collection);
            this.ctx = ctx;
            this.collectionList = collectionList;
        }

        @Override
        public int getCount() {
            return collectionList.size();
        }

        @Override
        public Object getItem(int position) {
            return collectionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindView(int position, View view) {
            WtImageView ivEmoji = (WtImageView) view;
            CollectEmotionListResp.CollectEmotion c = collectionList.get(position);
            String content = c.content;
            JSONObject jsonObject = JSONObject.parseObject(content);
            String url = jsonObject.getString("url");
            ivEmoji.load(url, true);
            // 保留旧代码，
//            if (c.getType() == 7) {
//                ivEmoji.setImageResource(R.mipmap.add_emoli_icon);
//            } else {
//                String url = c.getUrl();
//                if (url.endsWith(".gif")) {
//                    ImageLoadHelper.showGif(
//                            ctx,
//                            url,
//                            ivEmoji
//                    );
//                } else {
//                    ImageLoadHelper.showImageDontAnimateWithPlaceHolder(
//                            ctx,
//                            url,
//                            R.drawable.ffb,
//                            R.drawable.fez,
//                            ivEmoji
//                    );
//                }
//            }
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int pagePosition) {
        FitGridView gridView;
        List<CollectEmotionListResp.CollectEmotion> currentPageItemList = collectionList.subList(
                pagePosition * size,
                Math.min((pagePosition + 1) * size, collectionList.size())
        );
        final CollectionAdapter collectionAdapter = new CollectionAdapter(ctx, currentPageItemList);
        if (collectionList.size() > 0) {
            gridView = (FitGridView) LayoutInflater.from(ctx).inflate(R.layout.collections_gridview, container, false);
            gridView.setFitGridAdapter(collectionAdapter);
        } else {
            gridView = (FitGridView) LayoutInflater.from(ctx).inflate(R.layout.collections_gridview_init, container, false);
            gridView.setFitGridAdapter(new CollectionAdapterInit(ctx));
        }
        container.addView(gridView);
        gridView.setSelector(R.drawable.chat_face_bg);
        gridView.setOnItemClickListener((parent, view, itemPosition, id) -> {
            if (listener != null) {
//                listener.onCollectionClick(currentPageItemList.get(itemPosition));
                CollectEmotionListResp.CollectEmotion collectEmotion = currentPageItemList.get(itemPosition);
                String content = collectEmotion.content;
                JSONObject jsonObject = JSONObject.parseObject(content);
                listener.onCollectGifClick(jsonObject.getString("url"), jsonObject.getInteger("width"), jsonObject.getInteger("height"));
            }
        });
        gridView.setOnItemLongClickListener((parent, view, position, id) -> {
            new DeleteEmotionDialog(new DeleteEmotionDialog.OnBtnListener() {
                @Override
                public void onClickPositive(View view, DeleteEmotionDialog dialog) {
                    CollectEmotionListResp.CollectEmotion collectEmotion = currentPageItemList.get(position);

                    CollectEmotionDeleteReq collectEmotionDeleteReq = new CollectEmotionDeleteReq(collectEmotion.id);
                    collectEmotionDeleteReq.setCancelTag(this);
                    collectEmotionDeleteReq.post(new TioCallback<CommonResp>() {
                        @Override
                        public void onTioSuccess(CommonResp commonResp) {
                            dialog.dismiss();
                            ToastUtils.showShort("删除成功");
                            if (TioConfig.emotionList == null){
                                return;
                            }
                            for (CollectEmotionListResp.CollectEmotion collectEmotion1 : TioConfig.emotionList){
                                if (collectEmotion1.id == collectEmotion.id){
                                    TioConfig.emotionList.remove(collectEmotion1);
                                    break;
                                }
                            }
                            listener.emotionDelete();
//                            notifyDataSetChanged();
//                            if (collectionAdapter != null){
//                                collectionAdapter.notifyDataSetChanged();
//                            }
                        }

                        @Override
                        public void onTioError(String msg) {

                        }
                    });
                }

                @Override
                public void onClickNegative(View view, DeleteEmotionDialog dialog) {
                    dialog.dismiss();
                }
            }).show_canceledOnTouchOutside(ctx);
            return true;
        });
        return gridView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    static class CollectionAdapterInit extends FitGridAdapter {

        CollectionAdapterInit(Context ctx) {
            super(ctx, R.layout.item_face_collection_init);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindView(int position, View view) {
//            ImageView ivEmoji = view.findViewById(R.id.iv_collecton);
//            ivEmoji.setImageResource(R.mipmap.add_emoli_icon);
        }
    }
}
