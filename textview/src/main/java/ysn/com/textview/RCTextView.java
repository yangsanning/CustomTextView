package ysn.com.textview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @Author yangsanning
 * @ClassName RTextView
 * @Description 圆角TextView(设置点击状态时要设置点击事件才能显示效果)
 * @Date 2019/6/21
 * @History 2019/6/21 author: description:
 */
public class RCTextView extends AppCompatTextView {

    public static final int ICON_DIRECTION_LEFT = 1, ICON_DIRECTION_TOP = 2,
            ICON_DIRECTION_RIGHT = 3, ICON_DIRECTION_BOTTOM = 4;

    /**
     * 圆角
     */
    private float roundCorner;
    private float roundCornerTopLeft;
    private float roundCornerTopRight;
    private float roundCornerBottomLeft;
    private float roundCornerBottomRight;

    /**
     * 边线
     */
    private float strokeDottedWidth = 0;
    private float strokeDottedSpace = 0;
    private int strokeWidthNormal = 0;
    private int strokeWidthPressed = 0;
    private int strokeWidthUnable = 0;
    private int strokeColorNormal;
    private int strokeColorPressed;
    private int strokeColorUnable;
    private float[] cornerRadii = new float[8];

    /**
     * 图标
     */
    private int iconHeight;
    private int iconWidth;
    private int iconDirection;
    private Drawable icon = null;
    private Drawable iconNormal;
    private Drawable iconPressed;
    private Drawable iconUnable;

    /**
     * text
     */
    private int textColorNormal;
    private int textColorPressed;
    private int textColorUnable;
    private String typefacePath;

    /**
     * 背景
     */
    private int bgColorNormal;
    private int bgColorPressed;
    private int bgColorUnable;
    private GradientDrawable bgGradientNormal;
    private GradientDrawable bgGradientPressed;
    private GradientDrawable bgGradientUnable;
    private int[][] states = new int[4][];
    private StateListDrawable stateBackground;

    private int touchSlop;
    private Context context;
    private GestureDetector gestureDetector;

    /**
     * 是否设置对应的属性
     */
    private boolean hasPressedBgColor = false;
    private boolean hasUnableBgColor = false;
    private boolean hasPressedStrokeColor = false;
    private boolean hasUnableStrokeColor = false;
    private boolean hasPressedStrokeWidth = false;
    private boolean hasUnableStrokeWidth = false;

    public RCTextView(Context context) {
        this(context, null);
    }

    public RCTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        gestureDetector = new GestureDetector(context, new SimpleOnGesture());
        initAttrs(context, attrs);
        initValue();
        initStyle();
    }

    /**
     * 初始化控件属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RCTextView);

        roundCorner = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_round_corner, -1);
        roundCornerTopLeft = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_round_corner_top_left, 0);
        roundCornerTopRight = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_round_corner_top_right, 0);
        roundCornerBottomLeft = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_round_corner_bottom_left, 0);
        roundCornerBottomRight = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_round_corner_bottom_right, 0);

        strokeDottedWidth = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_stroke_dotted_width, 0);
        strokeDottedSpace = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_stroke_dotted_space, 0);
        strokeWidthNormal = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_stroke_width_normal, 0);
        strokeWidthPressed = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_stroke_width_pressed, 0);
        strokeWidthUnable = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_stroke_width_unable, 0);
        strokeColorNormal = a.getColor(R.styleable.RCTextView_rctv_stroke_color_normal, Color.TRANSPARENT);
        strokeColorPressed = a.getColor(R.styleable.RCTextView_rctv_stroke_color_pressed, Color.TRANSPARENT);
        strokeColorUnable = a.getColor(R.styleable.RCTextView_rctv_stroke_color_unable, Color.TRANSPARENT);

        // Vector兼容处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iconNormal = a.getDrawable(R.styleable.RCTextView_rctv_icon_normal);
            iconPressed = a.getDrawable(R.styleable.RCTextView_rctv_icon_pressed);
            iconUnable = a.getDrawable(R.styleable.RCTextView_rctv_icon_unable);
        } else {
            int normalId = a.getResourceId(R.styleable.RCTextView_rctv_icon_normal, -1);
            int pressedId = a.getResourceId(R.styleable.RCTextView_rctv_icon_pressed, -1);
            int unableId = a.getResourceId(R.styleable.RCTextView_rctv_icon_unable, -1);

            if (normalId != -1) {
                iconNormal = AppCompatResources.getDrawable(context, normalId);
            }
            if (pressedId != -1) {
                iconPressed = AppCompatResources.getDrawable(context, pressedId);
            }
            if (unableId != -1) {
                iconUnable = AppCompatResources.getDrawable(context, unableId);
            }
        }
        iconWidth = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_icon_width, 0);
        iconHeight = a.getDimensionPixelSize(R.styleable.RCTextView_rctv_icon_height, 0);
        iconDirection = a.getInt(R.styleable.RCTextView_rctv_icon_direction, ICON_DIRECTION_LEFT);

        textColorNormal = a.getColor(R.styleable.RCTextView_rctv_text_color_normal, getCurrentTextColor());
        textColorPressed = a.getColor(R.styleable.RCTextView_rctv_text_color_pressed, getCurrentTextColor());
        textColorUnable = a.getColor(R.styleable.RCTextView_rctv_text_color_unable, getCurrentTextColor());
        typefacePath = a.getString(R.styleable.RCTextView_rctv_text_typeface);

        bgColorNormal = a.getColor(R.styleable.RCTextView_rctv_bg_normal, 0);
        bgColorPressed = a.getColor(R.styleable.RCTextView_rctv_bg_pressed, 0);
        bgColorUnable = a.getColor(R.styleable.RCTextView_rctv_bg_unable, 0);
        a.recycle();

        hasPressedBgColor = bgColorPressed != 0;
        hasUnableBgColor = bgColorUnable != 0;
        hasPressedStrokeColor = strokeColorPressed != 0;
        hasUnableStrokeColor = strokeColorUnable != 0;
        hasPressedStrokeWidth = strokeWidthPressed != 0;
        hasUnableStrokeWidth = strokeWidthUnable != 0;
    }

    /**
     * 初始化Value
     */
    private void initValue() {
        bgGradientNormal = new GradientDrawable();
        bgGradientPressed = new GradientDrawable();
        bgGradientUnable = new GradientDrawable();

        Drawable drawable = getBackground();
        if (drawable instanceof StateListDrawable) {
            stateBackground = (StateListDrawable) drawable;
        } else {
            stateBackground = new StateListDrawable();
        }

        /**
         * 设置背景默认值
         */
        if (!hasPressedBgColor) {
            bgColorPressed = bgColorNormal;
        }
        if (!hasUnableBgColor) {
            bgColorUnable = bgColorNormal;
        }

        bgGradientNormal.setColor(bgColorNormal);
        bgGradientPressed.setColor(bgColorPressed);
        bgGradientUnable.setColor(bgColorUnable);

        // pressed, focused, normal, unable
        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_enabled};
        stateBackground.addState(states[0], bgGradientPressed);
        stateBackground.addState(states[1], bgGradientPressed);
        stateBackground.addState(states[3], bgGradientUnable);
        stateBackground.addState(states[2], bgGradientNormal);

        icon = isEnabled() ? iconNormal : iconUnable;

        /**
         * 设置边框默认值
         */
        if (!hasPressedStrokeWidth) {
            strokeWidthPressed = strokeWidthNormal;
        }
        if (!hasUnableStrokeWidth) {
            strokeWidthUnable = strokeWidthNormal;
        }
        if (!hasPressedStrokeColor) {
            strokeColorPressed = strokeColorNormal;
        }
        if (!hasUnableStrokeColor) {
            strokeColorUnable = strokeColorNormal;
        }

        // 未设置自定义背景色
        setBackgroundState(bgColorNormal == 0 && bgColorUnable == 0 && bgColorPressed == 0);
    }

    /**
     * 初始化
     */
    private void initStyle() {
        // 初始化文本颜色
        initTextColor();

        // 初始化边框
        initStroke();

        // 初始化Icon
        initIcon();

        // 初始化圆角
        initRoundCorner();

        // 设置文本字体样式
        initTypeface();
    }

    /**
     * 初始化文本颜色
     */
    private void initTextColor() {
        setTextColor(new ColorStateList(states,
                new int[]{textColorPressed, textColorPressed, textColorNormal, textColorUnable}));
    }

    /**
     * 初始化边框
     */
    private void initStroke() {
        bgGradientNormal.setStroke(strokeWidthNormal, strokeColorNormal, strokeDottedWidth, strokeDottedSpace);
        bgGradientPressed.setStroke(strokeWidthPressed, strokeColorPressed, strokeDottedWidth, strokeDottedSpace);
        bgGradientUnable.setStroke(strokeWidthUnable, strokeColorUnable, strokeDottedWidth, strokeDottedSpace);
        setBackgroundState(false);
    }

    /**
     * 初始化Icon
     */
    private void initIcon() {
        if (icon == null) {
            return;
        }

        // 处理未设置图片大小的情况
        if (iconHeight == 0 && iconWidth == 0) {
            iconWidth = icon.getIntrinsicWidth();
            iconHeight = icon.getIntrinsicHeight();
        }

        if (iconWidth != 0 && iconHeight != 0) {
            icon.setBounds(0, 0, iconWidth, iconHeight);
        }
        switch (iconDirection) {
            case ICON_DIRECTION_LEFT:
                setCompoundDrawables(icon, null, null, null);
                break;
            case ICON_DIRECTION_TOP:
                setCompoundDrawables(null, icon, null, null);
                break;
            case ICON_DIRECTION_RIGHT:
                setCompoundDrawables(null, null, icon, null);
                break;
            case ICON_DIRECTION_BOTTOM:
                setCompoundDrawables(null, null, null, icon);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化圆角
     * 优先roundCorner
     */
    private void initRoundCorner() {
        if (roundCorner < 0) {
            cornerRadii[0] = roundCornerTopLeft;
            cornerRadii[1] = roundCornerTopLeft;
            cornerRadii[2] = roundCornerTopRight;
            cornerRadii[3] = roundCornerTopRight;
            cornerRadii[4] = roundCornerBottomRight;
            cornerRadii[5] = roundCornerBottomRight;
            cornerRadii[6] = roundCornerBottomLeft;
            cornerRadii[7] = roundCornerBottomLeft;
        } else {
            cornerRadii[0] = roundCorner;
            cornerRadii[1] = roundCorner;
            cornerRadii[2] = roundCorner;
            cornerRadii[3] = roundCorner;
            cornerRadii[4] = roundCorner;
            cornerRadii[5] = roundCorner;
            cornerRadii[6] = roundCorner;
            cornerRadii[7] = roundCorner;
        }
        setRadiusRadii();
    }

    /**
     * 初始化文本字体样式
     */
    private void initTypeface() {
        if (!TextUtils.isEmpty(typefacePath)) {
            setTypeface(Typeface.createFromAsset(context.getAssets(), typefacePath));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            if (iconNormal != null) {
                icon = iconNormal;
                initIcon();
            }
        } else {
            if (iconUnable != null) {
                icon = iconUnable;
                initIcon();
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }
        gestureDetector.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (iconNormal != null) {
                    icon = iconNormal;
                    initIcon();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (isOutsideView(x, y)) {
                    if (iconNormal != null) {
                        icon = iconNormal;
                        initIcon();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (iconNormal != null) {
                    icon = iconNormal;
                    initIcon();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否移出view
     */
    private boolean isOutsideView(int x, int y) {
        boolean flag = false;
        if ((x < 0 - touchSlop) || (x >= getWidth() + touchSlop) ||
                (y < 0 - touchSlop) || (y >= getHeight() + touchSlop)) {
            flag = true;
        }
        return flag;
    }

    public RCTextView setStateBackgroundColor(int normal, int pressed, int unable) {
        bgColorNormal = normal;
        bgColorPressed = pressed;
        bgColorUnable = unable;
        hasPressedBgColor = true;
        hasUnableBgColor = true;
        bgGradientNormal.setColor(bgColorNormal);
        bgGradientPressed.setColor(bgColorPressed);
        bgGradientUnable.setColor(bgColorUnable);
        setBackgroundState(false);
        return this;
    }

    public int getBackgroundColorNormal() {
        return bgColorNormal;
    }

    public RCTextView setBackgroundColorNormal(int colorNormal) {
        this.bgColorNormal = colorNormal;
        /**
         * 设置背景默认值
         */
        if (!hasPressedBgColor) {
            bgColorPressed = bgColorNormal;
            bgGradientPressed.setColor(bgColorPressed);
        }
        if (!hasUnableBgColor) {
            bgColorUnable = bgColorNormal;
            bgGradientUnable.setColor(bgColorUnable);
        }
        bgGradientNormal.setColor(bgColorNormal);
        setBackgroundState(false);
        return this;
    }

    public int getBackgroundColorPressed() {
        return bgColorPressed;
    }

    public RCTextView setBackgroundColorPressed(int colorPressed) {
        this.bgColorPressed = colorPressed;
        this.hasPressedBgColor = true;
        bgGradientPressed.setColor(bgColorPressed);
        setBackgroundState(false);
        return this;
    }

    public int getBackgroundColorUnable() {
        return bgColorUnable;
    }

    public RCTextView setBackgroundColorUnable(int colorUnable) {
        this.bgColorUnable = colorUnable;
        this.hasUnableBgColor = true;
        bgGradientUnable.setColor(bgColorUnable);
        setBackgroundState(false);
        return this;
    }

    private void setBackgroundState(boolean unset) {

        // 未设置自定义属性,并且设置背景颜色时
        Drawable drawable = getBackground();
        if (unset && drawable instanceof ColorDrawable) {
            int color = ((ColorDrawable) drawable).getColor();
            setStateBackgroundColor(color, color, color);
        }

        // 设置背景资源
        setBackground(unset ? drawable : stateBackground);
    }


    public RCTextView initTypeface(String typefacePath) {
        this.typefacePath = typefacePath;
        initTypeface();
        return this;
    }

    public String getTypefacePath() {
        return typefacePath;
    }

    public RCTextView setIconNormal(Drawable icon) {
        this.iconNormal = icon;
        this.icon = icon;
        initIcon();
        return this;
    }

    public Drawable getIconNormal() {
        return iconNormal;
    }

    public RCTextView setIconPressed(Drawable icon) {
        this.iconPressed = icon;
        this.icon = icon;
        initIcon();
        return this;
    }

    public Drawable getIconPressed() {
        return iconPressed;
    }

    public RCTextView setIconUnable(Drawable icon) {
        this.iconUnable = icon;
        this.icon = icon;
        initIcon();
        return this;
    }

    public Drawable getIconUnable() {
        return iconUnable;
    }

    public RCTextView setIconSize(int iconWidth, int iconHeight) {
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        initIcon();
        return this;
    }

    public RCTextView setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
        initIcon();
        return this;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public RCTextView setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
        initIcon();
        return this;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public RCTextView setIconDirection(int iconDirection) {
        this.iconDirection = iconDirection;
        initIcon();
        return this;
    }

    public int getIconDirection() {
        return iconDirection;
    }

    public RCTextView setTextColorNormal(int textColor) {
        this.textColorNormal = textColor;
        if (textColorPressed == 0) {
            textColorPressed = textColorNormal;
        }
        if (textColorUnable == 0) {
            textColorUnable = textColorNormal;
        }
        initTextColor();
        return this;
    }

    public int getTextColorNormal() {
        return textColorNormal;
    }

    public RCTextView setPressedTextColor(int textColor) {
        this.textColorPressed = textColor;
        initTextColor();
        return this;
    }

    public int getPressedTextColor() {
        return textColorPressed;
    }

    public RCTextView setTextColorUnable(int textColor) {
        this.textColorUnable = textColor;
        initTextColor();
        return this;
    }

    public int getTextColorUnable() {
        return textColorUnable;
    }

    public void initTextColor(int normal, int pressed, int unable) {
        this.textColorNormal = normal;
        this.textColorPressed = pressed;
        this.textColorUnable = unable;
        initTextColor();
    }

    public RCTextView setStrokeWidthNormal(int width) {
        this.strokeWidthNormal = width;
        if (!hasPressedStrokeWidth) {
            strokeWidthPressed = strokeWidthNormal;
            setStrokePressed();
        }
        if (!hasUnableStrokeWidth) {
            strokeWidthUnable = strokeWidthNormal;
            setStrokeUnable();
        }
        setStrokeNormal();
        return this;
    }

    public int getStrokeWidthNormal() {
        return strokeWidthNormal;
    }

    public RCTextView setStrokeColorNormal(int color) {
        this.strokeColorNormal = color;
        if (!hasPressedStrokeColor) {
            strokeColorPressed = strokeColorNormal;
            setStrokePressed();
        }
        if (!hasUnableStrokeColor) {
            strokeColorUnable = strokeColorNormal;
            setStrokeUnable();
        }
        setStrokeNormal();
        return this;
    }

    public int getStrokeColorNormal() {
        return strokeColorNormal;
    }

    public RCTextView setStrokeWidthPressed(int width) {
        this.strokeWidthPressed = width;
        this.hasPressedStrokeWidth = true;
        setStrokePressed();
        return this;
    }

    public int getStrokeWidthPressed() {
        return strokeWidthPressed;
    }

    public RCTextView setStrokeColorPressed(int color) {
        this.strokeColorPressed = color;
        this.hasPressedStrokeColor = true;
        setStrokePressed();
        return this;
    }

    public int getStrokeColorPressed() {
        return strokeColorPressed;
    }

    public RCTextView setStrokeWidthUnable(int width) {
        this.strokeWidthUnable = width;
        this.hasUnableStrokeWidth = true;
        setStrokeUnable();
        return this;
    }

    public int getStrokeWidthUnable() {
        return strokeWidthUnable;
    }

    public RCTextView setStrokeColorUnable(int color) {
        this.strokeColorUnable = color;
        this.hasUnableStrokeColor = true;
        setStrokeUnable();
        return this;
    }

    public int getStrokeColorUnable() {
        return strokeColorUnable;
    }

    public void setStrokeWidth(int normal, int pressed, int unable) {
        this.strokeWidthNormal = normal;
        this.strokeWidthPressed = pressed;
        this.strokeWidthUnable = unable;
        this.hasPressedStrokeWidth = true;
        this.hasUnableStrokeWidth = true;
        initStroke();
    }

    public void setStrokeColor(int normal, int pressed, int unable) {
        this.strokeColorNormal = normal;
        this.strokeColorPressed = pressed;
        this.strokeColorUnable = unable;
        this.hasPressedStrokeColor = true;
        this.hasUnableStrokeColor = true;
        initStroke();
    }

    public void setStrokeDottedWidth(float dashWidth) {
        this.strokeDottedWidth = dashWidth;
        initStroke();
    }

    public float getStrokeDottedWidth() {
        return strokeDottedWidth;
    }

    public void setStrokeDottedSpace(float dashGap) {
        this.strokeDottedSpace = dashGap;
        initStroke();
    }

    public float getStrokeDottedSpace() {
        return strokeDottedSpace;
    }

    public void setStrokeDotted(float dashWidth, float dashGap) {
        this.strokeDottedWidth = dashWidth;
        this.strokeDottedSpace = dashGap;
        initStroke();
    }

    private void setStrokeNormal() {
        bgGradientNormal.setStroke(strokeWidthNormal, strokeColorNormal, strokeDottedWidth, strokeDottedSpace);
        setBackgroundState(false);
    }

    private void setStrokePressed() {
        bgGradientPressed.setStroke(strokeWidthPressed, strokeColorPressed, strokeDottedWidth, strokeDottedSpace);
        setBackgroundState(false);
    }

    private void setStrokeUnable() {
        bgGradientUnable.setStroke(strokeWidthUnable, strokeColorUnable, strokeDottedWidth, strokeDottedSpace);
        setBackgroundState(false);
    }

    public void setRoundCorner(float roundCorner) {
        this.roundCorner = roundCorner;
        initRoundCorner();
    }

    public float getRoundCorner() {
        return roundCorner;
    }

    public RCTextView setRoundCornerTopLeft(float roundCornerTopLeft) {
        this.roundCorner = -1;
        this.roundCornerTopLeft = roundCornerTopLeft;
        initRoundCorner();
        return this;
    }

    public float getRoundCornerTopLeft() {
        return roundCornerTopLeft;
    }

    public RCTextView setRoundCornerTopRight(float roundCornerTopRight) {
        this.roundCorner = -1;
        this.roundCornerTopRight = roundCornerTopRight;
        initRoundCorner();
        return this;
    }

    public float getRoundCornerTopRight() {
        return roundCornerTopRight;
    }

    public RCTextView setRoundCornerBottomRight(float roundCornerBottomRight) {
        this.roundCorner = -1;
        this.roundCornerBottomRight = roundCornerBottomRight;
        initRoundCorner();
        return this;
    }

    public float getRoundCornerBottomRight() {
        return roundCornerBottomRight;
    }

    public RCTextView setRoundCornerBottomLeft(float roundCornerBottomLeft) {
        this.roundCorner = -1;
        this.roundCornerBottomLeft = roundCornerBottomLeft;
        initRoundCorner();
        return this;
    }

    public float getRoundCornerBottomLeft() {
        return roundCornerBottomLeft;
    }

    public void setRoundCorner(float roundCornerTopLeft, float roundCornerTopRight,
                               float roundCornerBottomRight, float roundCornerBottomLeft) {
        this.roundCorner = -1;
        this.roundCornerTopLeft = roundCornerTopLeft;
        this.roundCornerTopRight = roundCornerTopRight;
        this.roundCornerBottomRight = roundCornerBottomRight;
        this.roundCornerBottomLeft = roundCornerBottomLeft;
        initRoundCorner();
    }

    private void setRadiusRadii() {
        bgGradientNormal.setCornerRadii(cornerRadii);
        bgGradientPressed.setCornerRadii(cornerRadii);
        bgGradientUnable.setCornerRadii(cornerRadii);
        setBackgroundState(false);
    }

    /**
     * 手势处理
     */
    class SimpleOnGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onShowPress(MotionEvent e) {
            if (iconPressed != null) {
                icon = iconPressed;
                initIcon();
            }
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (iconNormal != null) {
                icon = iconNormal;
                initIcon();
            }
            return false;
        }
    }

}
