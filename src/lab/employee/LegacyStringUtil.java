package lab.employee;

import java.lang.reflect.Field;

/**
 * 【罠4】リフレクションで String の内部フィールド "value" を直接読む。
 * 「toCharArray() はコピーが発生して遅いので、中身の配列をそのまま使う」
 * という昔の"高速化テクニック"。JDK の中身に依存している。
 */
public final class LegacyStringUtil {

    private static final Field VALUE;

    static {
        try {
            VALUE = String.class.getDeclaredField("value");
            VALUE.setAccessible(true); // private フィールドを無理やり読めるようにする
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private LegacyStringUtil() {
    }

    /** String の中身の char 配列を、コピーせずに取り出す */
    static char[] rawChars(String s) {
        try {
            return (char[]) VALUE.get(s);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /** 表示幅を数える(半角=1、全角=2)。一覧表の桁そろえに使う */
    public static int displayWidth(String s) {
        int width = 0;
        for (char c : rawChars(s)) {
            boolean halfWidth = c <= 0x7F || (c >= 0xFF61 && c <= 0xFF9F);
            width += halfWidth ? 1 : 2;
        }
        return width;
    }
}
