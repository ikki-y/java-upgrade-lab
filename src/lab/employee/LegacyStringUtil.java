package lab.employee;

/**
 * 文字列の表示幅を数える。
 *
 * 【実験Eで修正】リフレクションで String の内部フィールド "value" を読むのをやめた。
 *   - Java 16 以降、JDK の内部(private)へのアクセスは既定で禁止
 *   - そもそも Java 9 で String の中身が char[] から byte[] に変わっていて、
 *     仮にアクセスできても (char[]) への型変換で落ちる
 * 昔の「コピーを避ける高速化」は、今の JVM では効果がほぼないので、普通に charAt() で読む。
 */
public final class LegacyStringUtil {

    private LegacyStringUtil() {
    }

    /** 表示幅を数える(半角=1、全角=2)。一覧表の桁そろえに使う */
    public static int displayWidth(String s) {
        int width = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean halfWidth = c <= 0x7F || (c >= 0xFF61 && c <= 0xFF9F);
            width += halfWidth ? 1 : 2;
        }
        return width;
    }
}
