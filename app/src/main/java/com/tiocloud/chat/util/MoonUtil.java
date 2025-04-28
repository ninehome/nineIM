package com.tiocloud.chat.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.regex.Matcher;

public class MoonUtil {

    // ====================================================================================
    // 替换输入框的 emoji
    // ====================================================================================

    private static final float SMALL_SCALE = 0.4F;

    public static void replaceEmoticons(Context context, Editable editable, int start, int count) {
        if (count <= 0 || editable.length() < start + count)
            return;

        CharSequence s = editable.subSequence(start, start + count);
        Matcher matcher = EmojiManager.getPattern().matcher(s);
        while (matcher.find()) {
            int from = start + matcher.start();
            int to = start + matcher.end();
            String emot = editable.subSequence(from, to).toString();
            Drawable d = getEmotDrawable(context, emot, SMALL_SCALE);
            if (d != null) {
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                editable.setSpan(span, from, to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private static Drawable getEmotDrawable(Context context, String text, float scale) {
        Drawable drawable = EmojiManager.getDrawable(context, text);

        // scale
        if (drawable != null) {
            int width = (int) (drawable.getIntrinsicWidth() * scale);
            int height = (int) (drawable.getIntrinsicHeight() * scale);
            drawable.setBounds(0, 0, width, height);
        }

        return drawable;
    }

    // ====================================================================================
    // 替换 TextView 中 emoji
    // ====================================================================================

    private static final float DEF_SCALE = 0.45F;

    public static void identifyFaceExpression(TextView textView, CharSequence value, int align) {
        identifyFaceExpression(textView, value, align, DEF_SCALE);
    }

    public static void identifyFaceExpression(TextView textView, CharSequence value, int align, float scale) {
        SpannableString spannableString = replaceEmoticons(textView.getContext(), value, scale, align);
        // android 10手机上具有很多ReplacementSpans时，在view.onMeasure中花费很多时间，因此发生了ANR
        // https://www.soinside.com/question/aw6fPiL6ecbEoeNRdkQtcm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.setBreakStrategy(LineBreaker.BREAK_STRATEGY_SIMPLE);
        }
        textView.setText(spannableString);
    }

    private static SpannableString replaceEmoticons(Context context, CharSequence value, float scale, int align) {
        if (TextUtils.isEmpty(value)) {
            value = "";
        }

        SpannableString spannableString = new SpannableString(value);
        Matcher matcher = EmojiManager.getPattern().matcher(value);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String emot = value.toString().substring(start, end);
            Drawable d = getEmotDrawable(context, emot, scale);
            if (d != null) {
                ImageSpan span = new ImageSpan(d, align);
                spannableString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

}
