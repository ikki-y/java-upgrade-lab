package lab.employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日付をいろいろな書式で表示する。
 *
 * 【実験Eで修正】書式をロケール任せ(DateFormat.getDateInstance(FULL など))にしていた所を、
 * 書式を自分で指定する形(SimpleDateFormat("yyyy/MM/dd") など)に変えた。
 *   - ロケール任せの書式は、Java 9 で元データが CLDR(国際的な共通データ)に切り替わり、
 *     Java 8 と表示が変わった(曜日が付く、年が4桁になる、空白が特殊文字になる 等)
 *   - 書式を自分で指定したものは、Java 8 と 21 で同じ結果だった(実験Bで確認)
 * パターンは「Java 8 のときの表示」(results/java8.txt)に合わせてある。
 *
 * 例外: 和暦の FULL だけはロケール任せのまま。
 *   自分で "GGGGy年" と指定すると「令和1年」になり、Java 8 の「令和元年」と合わないため。
 *   new Locale("ja","JP","JP") は Java 19 で非推奨になったので Locale.of(...) に変えた。
 */
public class DateFormatDemo {

    public static void print(Date date) {
        Locale ja = Locale.JAPAN;
        Locale jaImperial = Locale.of("ja", "JP", "JP"); // 和暦

        show("FULL   (ja)       ", new SimpleDateFormat("yyyy'年'M'月'd'日'", ja).format(date));
        show("LONG   (ja)       ", new SimpleDateFormat("yyyy/MM/dd", ja).format(date));
        show("MEDIUM (ja)       ", new SimpleDateFormat("yyyy/MM/dd", ja).format(date));
        show("SHORT  (ja)       ", new SimpleDateFormat("yy/MM/dd", ja).format(date));
        show("FULL   (ja 和暦)  ", DateFormat.getDateInstance(DateFormat.FULL, jaImperial).format(date));
        show("日時 MEDIUM (ja)  ", new SimpleDateFormat("yyyy/MM/dd H:mm:ss", ja).format(date));
        show("FULL   (en_US)    ", new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US).format(date));
        show("時刻 SHORT (en_US)", new SimpleDateFormat("h:mm a", Locale.US).format(date));
        show("書式指定 (ja)     ", new SimpleDateFormat("yyyy/MM/dd(E) a h:mm", ja).format(date));
        show("書式指定 (en_US)  ", new SimpleDateFormat("EEE, MMM d, yyyy h:mm a", Locale.US).format(date));
    }

    /** 見た目では区別できない特殊な空白があれば \\uXXXX の形で見えるようにして表示する */
    static void show(String label, String value) {
        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (Character.isSpaceChar(c) && c != ' ') {
                sb.append(String.format("\\u%04X", (int) c));
            } else {
                sb.append(c);
            }
        }
        System.out.println(label + " : " + sb);
    }
}
