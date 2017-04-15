package yuxue.tagmanager.util;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

public class TinyPY {
    /**
     * 字符串转拼音
     * @param name 原始字符串
     * @return 字符串对应的拼音
     */
    public static String toPinYin(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        name = name.trim();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            stringBuilder.append(Pinyin.toPinyin(name.charAt(i)));
        }
        return stringBuilder.toString();
    }

    /**
     * 截取首字符
     * @param name 原始字符串
     * @return 首字符
     */
    public static String firstPinYin(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        name = name.trim();
        return name.substring(0, 1).toUpperCase();
    }
}