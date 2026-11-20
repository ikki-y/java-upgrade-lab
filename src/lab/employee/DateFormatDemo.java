package lab.employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 【罠5】DateFormat / SimpleDateFormat で日付を文字列にする。
 * 書式を自分で指定した場合と、ロケール(言語・地域)任せにした場合を並べて表示する。
 * Java 8 と Java 21 で同じ結果になるか、出力を見比べるためのもの。
 */
public class DateFormatDemo {

    public static void print(Date date) {
        Locale ja = Locale.JAPAN;
        Locale jaImperial = new Locale("ja", "JP", "JP"); // 和暦

        show("FULL   (ja)       ", DateFormat.getDateInstance(DateFormat.FULL, ja).format(date));
        show("LONG   (ja)       ", DateFormat.getDateInstance(DateFormat.LONG, ja).format(date));
        show("MEDIUM (ja)       ", DateFormat.getDateInstance(DateFormat.MEDIUM, ja).format(date));
        show("SHORT  (ja)       ", DateFormat.getDateInstance(DateFormat.SHORT, ja).format(date));
        show("FULL   (ja 和暦)  ", DateFormat.getDateInstance(DateFormat.FULL, jaImperial).format(date));
        show("日時 MEDIUM (ja)  ", DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, ja).format(date));
        show("FULL   (en_US)    ", DateFormat.getDateInstance(DateFormat.FULL, Locale.US).format(date));
        show("時刻 SHORT (en_US)", DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US).format(date));
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
