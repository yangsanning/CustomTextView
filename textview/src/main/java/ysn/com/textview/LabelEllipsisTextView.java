package ysn.com.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import ysn.com.textview.utils.MyTextUtils;

/**
 * @Author yangsanning
 * @ClassName LabelEllipsisTextView
 * @Description 一句话概括作用
 * @Date 2019/4/26
 * @History 2019/4/26 author: description:
 */
public class LabelEllipsisTextView extends View {

    private int paddingTop, paddingBottom;

    private String text, startTag, endTag;
    private int textColor;
    private int textSize;

    private TextPaint textPaint;
    private Rect textRect;
    private Paint.FontMetricsInt fontMetricsInt;

    private String ellipsisText;
    private int startTagWidth, endTagWidth, ellipsisTextWidth;

    private int viewWidth, viewHeight;
    private int textWidth;

    private String drawText;

    public LabelEllipsisTextView(Context context) {
        this(context, null);
    }

    public LabelEllipsisTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelEllipsisTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LabelEllipsisTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint();
        initData();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelEllipsisTextView);

        paddingTop = typedArray.getDimensionPixelSize(R.styleable.LabelEllipsisTextView_letv_padding_top, 0);
        paddingBottom = typedArray.getDimensionPixelSize(R.styleable.LabelEllipsisTextView_letv_padding_bottom, 0);

        text = typedArray.getString(R.styleable.LabelEllipsisTextView_letv_text);
        textColor = typedArray.getColor(R.styleable.LabelEllipsisTextView_letv_text_color, Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.LabelEllipsisTextView_letv_text_size, 50);

        startTag = typedArray.getString(R.styleable.LabelEllipsisTextView_letv_start_tag);
        endTag = typedArray.getString(R.styleable.LabelEllipsisTextView_letv_end_tag);

        ellipsisText = typedArray.getString(R.styleable.LabelEllipsisTextView_letv_ellipsis);

        typedArray.recycle();
    }

    private void initPaint() {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);

        textRect = new Rect();
        fontMetricsInt = textPaint.getFontMetricsInt();
    }

    private void initData() {
        text = MyTextUtils.emptyIfNull(text);
        startTag = MyTextUtils.ifNullOrEmpty(startTag, "《");
        endTag = MyTextUtils.ifNullOrEmpty(endTag, "》");
        ellipsisText = MyTextUtils.ifNullOrEmpty(ellipsisText, "...");

        startTagWidth = (int) textPaint.measureText(startTag);
        endTagWidth = (int) textPaint.measureText(endTag);
        ellipsisTextWidth = (int) textPaint.measureText(ellipsisText);

        setText(text);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth = getMeasuredWidth();
        int textMaxWidth = viewWidth - startTagWidth - endTagWidth;
        int currentTextWidth = 0;
        StringBuilder textStringBuilder = new StringBuilder();
        textStringBuilder.append(startTag);
        if (textWidth > textMaxWidth) {
            textMaxWidth -= ellipsisTextWidth;
            for (char c : text.toCharArray()) {
                currentTextWidth += MyTextUtils.getSingleCharWidth(textPaint, c);
                if (currentTextWidth > textMaxWidth) {
                    break;
                }
                textStringBuilder.append(c);
            }
            textStringBuilder.append(ellipsisText);
        } else {
            textStringBuilder.append(text);
        }
        textStringBuilder.append(endTag);
        drawText = textStringBuilder.toString();
        textPaint.getTextBounds(drawText, 0, drawText.length(), textRect);

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            viewHeight = paddingTop + textRect.height() + paddingBottom;
        } else {
            viewHeight = getMeasuredHeight();
        }

        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawText == null) {
            return;
        }

        int startX = (int) (getWidth() / 2 - textPaint.measureText(drawText) / 2);
        int startY = getHeight() / 2 - fontMetricsInt.descent + (fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        canvas.drawText(drawText, startX, startY, textPaint);
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        this.text = text;
        this.textWidth = (int) textPaint.measureText(this.text);
        requestLayout();
        invalidate();
    }
}
