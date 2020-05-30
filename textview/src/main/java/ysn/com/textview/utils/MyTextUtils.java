package ysn.com.textview.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;

/**
 * @Author yangsanning
 * @ClassName MyTextUtils
 * @Description 一句话概括作用
 * @Date 2019/4/26
 * @History 2019/4/26 author: description:
 */
public class MyTextUtils {

    /**
     * 得到单个char的宽度
     */
    public static float getSingleCharWidth(TextPaint textPaint, char textChar) {
        float[] width = new float[1];
        textPaint.getTextWidths(new char[]{textChar}, 0, 1, width);
        return width[0];
    }

    /**
     * 为null 返回 ""
     */
    public static String emptyIfNull(@Nullable String str) {
        return str == null ? "" : str;
    }

    /**
     * 为null或"" 返回 defaultStr
     */
    public static String ifNullOrEmpty(@Nullable String str, @NonNull String defaultStr) {
        return str == null || str.length() == 0 ? defaultStr : str;
    }

    /**
     *  判断是否是英语
     */
    public static boolean isEnglish(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }
}
