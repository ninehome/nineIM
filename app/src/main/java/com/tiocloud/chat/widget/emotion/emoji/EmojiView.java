package com.tiocloud.chat.widget.emotion.emoji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.widget.emotion.EmojiPager2Adapter;
import com.tiocloud.chat.widget.emotion.EmojiPager3Adapter;
import com.tiocloud.chat.widget.emotion.EmoticonSelectedListener;
import com.tiocloud.chat.widget.emotion.EmoticonViewPaperAdapter;
import com.tiocloud.chat.widget.emotion.EmotionType;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.CollectEmotionListReq;
import com.watayouxiang.httpclient.model.response.CollectEmotionListResp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EmojiView {
    private final ViewPager emotionPager;
    private final LinearLayout pageNumberLayout;
    private final EmoticonSelectedListener listener;

    private EmoticonViewPaperAdapter viewPaperAdapter;

    private RadioGroup mFaceRadioGroup;// 切换不同组表情的RadioGroup

    public EmojiView(View rootView, EmoticonSelectedListener listener) {
        emotionPager = rootView.findViewById(R.id.scrPlugin);
        pageNumberLayout = rootView.findViewById(R.id.layout_scr_bottom);
        mFaceRadioGroup = (RadioGroup) rootView.findViewById(R.id.face_btn_layout);
        if (TioConfig.OpenCloseConfig.collectFaceEmotionEnable()){
            mFaceRadioGroup.setVisibility(View.VISIBLE);
        }else {
            mFaceRadioGroup.setVisibility(View.GONE);
        }
        RadioButton rg1 = (RadioButton) rootView.findViewById(R.id.default_face);
        RadioButton rg2 = (RadioButton) rootView.findViewById(R.id.moya_face_gif);
        mFaceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                showRd(checkedId);
            }
        });
        this.listener = listener;

//        initEvents();
    }

    private void showRd(int checkedId){
        switch (checkedId) {
            case R.id.default_face:
                // 表情
                switchViewPager1();
                break;
            case R.id.moya_face_gif:
                // gif
                switchViewPager2();
                break;
            default:
                // 自定义表情
                switchViewPager3();
                break;
        }
    }

    public void switchViewPager3() {
        if (TioConfig.emotionList == null){
            CollectEmotionListReq listReq = new CollectEmotionListReq();
            listReq.setCancelTag(this);
            listReq.get(new TioCallback<CollectEmotionListResp>() {
                @Override
                public void onTioSuccess(CollectEmotionListResp collectEmotionListResp) {
                    if (collectEmotionListResp.data != null){
                        TioConfig.emotionList = collectEmotionListResp.data;
                        switchViewPager3();
                    }
//                    System.out.println("11111");
                }

                @Override
                public void onTioError(String msg) {
                    ToastUtils.showShort(msg);
                }
            });
            return;
        }
        showPage3();
    }

    private void showPage3() {
        EmojiPager3Adapter emojiPager3Adapter = new EmojiPager3Adapter(
                emotionPager.getContext(),
                TioConfig.emotionList,
                listener
        );
        emotionPager.setAdapter(emojiPager3Adapter);
        emotionPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setIndicator(position, emojiPager3Adapter.getCount());
            }
        });
        setIndicator(0, TioConfig.emotionList.size()/10+(TioConfig.emotionList.size()%10==0?0:1));
    }

    public void switchViewPager2() {
        String[][] strArray = Gifs.getTexts();
        int[][] pngId = Gifs.getPngIds();
        EmojiPager2Adapter emojiPager2Adapter = new EmojiPager2Adapter(
                emotionPager.getContext(),
                pngId,
                strArray,
                listener
//                text -> {
////                    mEmotionClickListener.onGifFaceClick(text);
//                }
        );
        emotionPager.setAdapter(emojiPager2Adapter);
        emotionPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setIndicator(position, emojiPager2Adapter.getCount());
            }
        });
        setIndicator(0, strArray.length);
    }

    private void switchViewPager1() {
        viewPaperAdapter = new EmoticonViewPaperAdapter(listener, emotionPager);
        emotionPager.setOffscreenPageLimit(1);
        emotionPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setIndicator(position, viewPaperAdapter.getCount());
            }
        });
        emotionPager.setAdapter(viewPaperAdapter);
        if (viewPaperAdapter.setEmotionType(EmotionType.EMOJI)) {
            setIndicator(0, viewPaperAdapter.getCount());
        }
    }

    private void initEvents() {
        viewPaperAdapter = new EmoticonViewPaperAdapter(listener, emotionPager);
        emotionPager.setOffscreenPageLimit(1);
        emotionPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setIndicator(position, viewPaperAdapter.getCount());
            }
        });
        emotionPager.setAdapter(viewPaperAdapter);
    }

    private void setIndicator(int position, int pageCount) {
        int hasCount = pageNumberLayout.getChildCount();

        if (pageCount > hasCount) {// 不够
            for (int i = 0; i < pageCount - hasCount; i++) {
                ImageView img = new ImageView(pageNumberLayout.getContext());
                img.setBackgroundResource(R.drawable.view_pager_indicator_selector);
                pageNumberLayout.addView(img);
            }
        } else if (pageCount < hasCount) {// 太多
            pageNumberLayout.removeViews(pageCount, hasCount - pageCount);
        }

        for (int i = 0; i < pageCount; i++) {
            ImageView imgCur = (ImageView) pageNumberLayout.getChildAt(i);
            imgCur.setSelected(i == position);
        }
    }

    // ======================================================================================
    // public
    // ======================================================================================

    public void show() {
        showRd(mFaceRadioGroup.getCheckedRadioButtonId());
    }

    public static class Gifs {
        private static final int[][] IDS = {/*
                {
                        R.drawable.gif_eight, R.drawable.gif_eighteen, R.drawable.gif_eleven, R.drawable.gif_fifity,
                        R.drawable.gif_fifity_four, R.drawable.gif_fifity_one, R.drawable.gif_fifity_three, R.drawable.gif_fifity_two
                        , R.drawable.gif_fifteen, R.drawable.gif_five
                },
                {
                        R.drawable.gif_forty, R.drawable.gif_forty_eight,
                        R.drawable.gif_forty_five, R.drawable.gif_forty_four, R.drawable.gif_forty_nine, R.drawable.gif_forty_one
                        , R.drawable.gif_forty_seven, R.drawable.gif_forty_three, R.drawable.gif_forty_two, R.drawable.gif_fourteen
                },
                {
                        R.drawable.gif_nine, R.drawable.gif_nineteen, R.drawable.gif_one, R.drawable.gif_seven,
                        R.drawable.gif_seventeen, R.drawable.gif_sixteen, R.drawable.gif_ten, R.drawable.gif_thirteen,
                        R.drawable.gif_thirty, R.drawable.gif_thirty_eight
                },
                {
                        R.drawable.gif_thirty_five, R.drawable.gif_thirty_four, R.drawable.gif_thirty_nine, R.drawable.gif_thirty_seven,
                        R.drawable.gif_thirty_six, R.drawable.gif_thirty_three, R.drawable.gif_thirty_two, R.drawable.gif_thirty_one,
                        R.drawable.gif_three, R.drawable.gif_twelve
                },
                {
                        R.drawable.gif_twenty, R.drawable.gif_twenty_eight, R.drawable.gif_twenty_five, R.drawable.gif_twenty_four,
                        R.drawable.gif_twenty_nine, R.drawable.gif_twenty_one, R.drawable.gif_twenty_seven, R.drawable.gif_twenty_six
                        , R.drawable.gif_twenty_three, R.drawable.gif_twenty_two
                }*/

        };
        private static final String[][] TEXTS = {/*
                {
                        "eight.gif", "eighteen.gif", "eleven.gif", "fifity.gif",
                        "fifity_four.gif", "fifity_one.gif", "fifity_three.gif", "fifity_two.gif"
                        , "fifteen.gif", "five.gif"
                },

                {
                        "forty.gif", "forty_eight.gif",
                        "forty_five.gif", "forty_four.gif", "forty_nine.gif", "forty_one.gif",
                        "forty_seven.gif", "forty_three.gif", "forty_two.gif", "fourteen.gif"
                },

                {

                        "nine.gif", "nineteen.gif", "one.gif", "seven.gif",
                        "seventeen.gif", "sixteen.gif", "ten.gif", "thirteen.gif",
                        "thirty.gif", "thirty_eight.gif",

                },

                {
                        "thirty_five.gif", "thirty_four.gif",
                        "thirty_nine.gif", "thirty_seven.gif", "thirty_six.gif", "thirty_three.gif",
                        "thirty_two.gif", "thirty-one.gif", "three.gif", "twelve.gif"
                },

                {
                        "twenty.gif", "twenty_eight.gif", "twenty_five.gif", "twenty_four.gif",
                        "twenty_nine.gif", "twenty_one.gif", "twenty_seven.gif", "twenty_six.gif"
                        , "twenty_three.gif", "twenty_two.gif"
                }*/

        };
        /**
         * gif
         */
        private static final int[][] PNGID = {/*
                {
                        R.drawable.gif_eight_png, R.drawable.gif_eighteen_png, R.drawable.gif_eleven_png, R.drawable.gif_fifity_png,
                        R.drawable.gif_fifity_four_png, R.drawable.gif_fifity_one_png, R.drawable.gif_fifity_three_png, R.drawable.gif_fifity_two_png
                        , R.drawable.gif_fifteen_png, R.drawable.gif_five_png
                },
                {
                        R.drawable.gif_forty_png, R.drawable.gif_forty_eight_png,
                        R.drawable.gif_forty_five_png, R.drawable.gif_forty_four_png, R.drawable.gif_forty_nine_png, R.drawable.gif_forty_one_png
                        , R.drawable.gif_forty_seven_png, R.drawable.gif_forty_three_png, R.drawable.gif_forty_two_png, R.drawable.gif_fourteen_png
                },
                {

                        R.drawable.gif_nine_png, R.drawable.gif_nineteen_png, R.drawable.gif_one_png, R.drawable.gif_seven_png,
                        R.drawable.gif_seventeen_png, R.drawable.gif_sixteen_png, R.drawable.gif_ten_png, R.drawable.gif_thirteen_png,
                        R.drawable.gif_thirty_png, R.drawable.gif_thirty_eight_png
                },
                {
                        R.drawable.gif_thirty_five_png, R.drawable.gif_thirty_four_png, R.drawable.gif_thirty_nine_png, R.drawable.gif_thirty_seven_png
                        , R.drawable.gif_thirty_six_png, R.drawable.gif_thirty_three_png, R.drawable.gif_thirty_two_png, R.drawable.gif_thirty_one_png
                        , R.drawable.gif_three_png, R.drawable.gif_twelve_png
                },
                {
                        R.drawable.gif_twenty_png, R.drawable.gif_twenty_eight_png, R.drawable.gif_twenty_five_png, R.drawable.gif_twenty_four_png,
                        R.drawable.gif_twenty_nine_png, R.drawable.gif_twenty_one_png, R.drawable.gif_twenty_seven_png, R.drawable.gif_twenty_six_png
                        , R.drawable.gif_twenty_three_png, R.drawable.gif_twenty_two_png
                }*/

        };
        private static final Map<String, Integer> MAPS = new HashMap<String, Integer>();
//        private static final Map<String, Integer> PNG_MAPS = new HashMap<String, Integer>();
        static {
            // 取最小的长度，防止长度不一致出错
            int length = IDS.length > TEXTS.length ? TEXTS.length : IDS.length;
            for (int i = 0; i < length; i++) {
                int[] subIds = IDS[i];
                int[] subPngs = PNGID[i];
                String[] subTexts = TEXTS[i];
                if (subIds == null || subTexts == null) {
                    continue;
                }
                int subLength = subIds.length > subTexts.length ? subTexts.length : subIds.length;
                for (int j = 0; j < subLength; j++) {
                    MAPS.put(TEXTS[i][j], IDS[i][j]);
//                    PNG_MAPS.put(TEXTS[i][j], PNGID[i][j]);
                }
            }
        }

        public static int[][] getIds() {
            return IDS;
        }

        public static String[][] getTexts() {
            return TEXTS;
        }

        public static int textMapId(String text) {
            if (MAPS.containsKey(text)) {
                return MAPS.get(text);
            } else {
                return -1;
            }
        }

//        public static int textPngMapId(String text) {
//            if (PNG_MAPS.containsKey(text)) {
//                return PNG_MAPS.get(text);
//            } else {
//                return -1;
//            }
//        }

        public static int[][] getPngIds() {
            return PNGID;
        }
    }
}
