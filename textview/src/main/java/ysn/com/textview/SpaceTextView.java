package ysn.com.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;

import ysn.com.textview.utils.MyTextUtils;

/**
 * @Author yangsanning
 * @ClassName SpaceTextView
 * @Description 可调整字间距的 TextView
 * @Date 2020/5/30
 */

public class SpaceTextView extends AppCompatTextView {

    private float space;

    private CharSequence originalText = "";

    public SpaceTextView(Context context) {
        this(context, null);
    }

    public SpaceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpaceTextView);

        space = a.getDimensionPixelSize(R.styleable.SpaceTextView_letv_space, 0);

        a.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        setSpacing();
    }

    @Override
    public CharSequence getText() {
        return originalText;
    }

    /**
     * 调整字间距
     */
    private void setSpacing() {
        if (this.originalText == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            builder.append(originalText.charAt(i));
            if (i + 1 < originalText.length()) {
                // 如果前后都是英文，则不添加空格，防止英文空格太大
                if (MyTextUtils.isEnglish(originalText.charAt(i) + "")
                        && MyTextUtils.isEnglish(originalText.charAt(i + 1) + "")) {
                } else {
                    // \u00A0 不间断空格 碰见文字追加空格
                    builder.append("\u00A0");
                }
            }
        }

        // 通过SpannableString类，去设置空格
        SpannableString finalText = new SpannableString(builder.toString());

        // 如果当前TextView内容长度大于1，则进行空格添加
        if (builder.toString().length() > 1) {
            for (int i = 1; i < builder.toString().length(); i += 2) {
                // ScaleXSpan 基于x轴缩放，按照x轴等比例进行缩放，通过字间距+1除以10进行等比缩放
                finalText.setSpan(new ScaleXSpan((space + 1) / 10), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        super.setText(finalText, BufferType.SPANNABLE);
    }

    /**
     * 获取字间距
     */
    public float getSpace() {
        return this.space;
    }

    /**
     * 设置间距
     */
    public void setSpace(float space) {
        this.space = space;
        setSpacing();
    }
}
